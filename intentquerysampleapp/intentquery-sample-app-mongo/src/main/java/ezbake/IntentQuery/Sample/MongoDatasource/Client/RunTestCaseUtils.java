/*   Copyright (C) 2013-2014 Computer Sciences Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */

package ezbake.IntentQuery.Sample.MongoDatasource.Client;

import java.util.List;

import org.apache.thrift.TException;

import com.cloudera.impala.extdatasource.thrift.ExternalDataSourceService;
import com.cloudera.impala.extdatasource.thrift.TBinaryPredicate;
import com.cloudera.impala.extdatasource.thrift.TCloseParams;
import com.cloudera.impala.extdatasource.thrift.TCloseResult;
import com.cloudera.impala.extdatasource.thrift.TGetNextParams;
import com.cloudera.impala.extdatasource.thrift.TGetNextResult;
import com.cloudera.impala.extdatasource.thrift.TGetStatsParams;
import com.cloudera.impala.extdatasource.thrift.TGetStatsResult;
import com.cloudera.impala.extdatasource.thrift.TOpenParams;
import com.cloudera.impala.extdatasource.thrift.TOpenResult;
import com.cloudera.impala.extdatasource.thrift.TRow;
import com.cloudera.impala.extdatasource.thrift.TRowBatch;
import com.cloudera.impala.thrift.TColumnValue;
import com.cloudera.impala.thrift.TStatusCode;

import ezbake.IntentQuery.Sample.MongoDatasource.Common.ParametersBuilder;

public class RunTestCaseUtils {
	public static void RunTestCase(
		ExternalDataSourceService.Client 	client,
		List<List<TBinaryPredicate>>		predicate
	) throws TException
	{
		List<List<TBinaryPredicate>>	accepted_predicate;
	    TGetStatsParams 				getStatsParams;
	    TGetStatsResult					getStatsResult; 

	    TOpenParams						openParams;
	    TOpenResult						openResult;
	    
	    TGetNextParams					getNextParams;
	    TGetNextResult					getNextResult;
	    boolean							eos = false;
	    TStatusCode						statusCode;
	    
	    TCloseParams					closeParams;
	    TCloseResult    				closeResult;
	    
	    /******************************************************************
		// Parameters to getStats().
		struct TGetStatsParams {
		  // The name of the table.
		  1: required string table_name
	
		  // A string specified for the table that is passed to the external data source.
		  2: optional string init_string
	
		  // A list of conjunctive (AND) clauses, each of which contains a list of
		  // disjunctive (OR) binary predicates.
		  3: required list<list<TBinaryPredicate>> predicates
		}
	    struct TGetStatsResult {
	  	  1: required ImpalaTypes.TStatus status

	  	  // Estimate of the total number of rows returned when applying the 
	  	  // predicates indicated by accepted_conjuncts. Not set if the data 
	  	  // source does not support providing this statistic.
	  	  2: optional i64 num_rows_estimate

	  	  // Accepted conjuncts. References the 'predicates' parameter in the 
	  	  // getStats() call. It contains the 0-based indices of the top-level 
	  	  // list elements (the AND elements) that the library will be able to 
	  	  // apply during the scan. Those elements that aren't referenced in 
	  	  // accepted_conjuncts will be evaluated by Impala itself.
	  	  3: list<i32> accepted_conjuncts
	  	}
	  	******************************************************************/
	    // step 1 -- getStats()
	    getStatsParams = ParametersBuilder.ConstructGetStatsParams(predicate);
	    getStatsResult = client.getStats(getStatsParams);
	    
	    System.out.println("client.getStats() returned : \n" + getStatsResult);
	    
	    // construct accepted predicates from getStatsResult
	    accepted_predicate = ParametersBuilder.ConstructAcceptedPredicate(
	    	predicate, 
	    	getStatsResult.getAccepted_conjuncts()
	    );
	    
	    /*******************************************************
		struct TOpenParams {
		  // A unique identifier for the query.
		  1: required ImpalaTypes.TUniqueId query_id

		  // The name of the table.
		  2: required string table_name

		  // A string specified for the table that is passed to the external data source.
		  3: required string init_string

		  // The authenticated user name.
		  4: required string authenticated_user_name

		  // The schema of the rows that the scan needs to return.
		  5: required TTableSchema row_schema

		  // The expected size of the row batches it returns in the subsequent getNext() calls.
		  6: required i32 batch_size

		  // A representation of the scan predicates that were accepted in the preceding
		  // getStats() call.
		  7: required list<list<TBinaryPredicate>> predicates
		}

	    struct TOpenResult {
	  	  1: required ImpalaTypes.TStatus status
	  	
	  	  // An opaque handle used in subsequent getNext()/close() calls.
	  	  2: optional string scan_handle
	  	}
	    ***********************************************************************/
	    
	    // step 2 -- open()
	    openParams = ParametersBuilder.ConstructOpenParams(accepted_predicate);
	    openResult = client.open(openParams);
    	System.out.println("client.open() returned :\n" + openResult);

    	
    	/****************************************************************
    	// Parameters to getNext()
    	struct TGetNextParams {
    	  // The opaque handle returned by the previous open() call.
    	  1: required string scan_handle
    	}

    	// Returned by getNext().
    	struct TGetNextResult {
    	  1: required ImpalaTypes.TStatus status

    	  // If true, reached the end of the result stream; subsequent calls to
    	  // getNext() won’t return any more results
    	  2: required bool eos

    	  // A batch of rows to return, if any exist. The number of rows in the batch
    	  // should be less than or equal to the batch_size specified in TOpenParams.
    	  3: optional TRowBatch rows
    	}
    	****************************************************************/
	    // step 3 -- getNext() until the end of table
	    getNextParams = ParametersBuilder.ConstructGetNextParams(openResult);
	    do {
	    	getNextResult = client.getNext(getNextParams);
	    	System.out.println("client.getNext() returned :\n" + getNextResult);
	    	statusCode = getNextResult.getStatus().getStatus_code();
	    	if (TStatusCode.OK != statusCode) {
	    		break;
	    	}
	    	eos = getNextResult.isEos();
	    	TRowBatch	rowBatch = getNextResult.getRows();
	    	List<TRow>	rowList = rowBatch.getRows();
	    	
	    	System.out.println("client.getNext() returned Rows:");
	    	for (TRow row : rowList) {
	    		List<TColumnValue>	valueList = row.getCol_vals();
	    		//for (TColumnValue value)
	    		System.out.println(valueList);
	    	}
	    } while (false == eos);
	    
	    /***************************************************************
		struct TCloseParams {
  			// The opaque handle returned by the previous open() call.
  			1: required string scan_handle
		}

		// Returned by close().
		struct TCloseResult {
  			1: required ImpalaTypes.TStatus status
			}
	    ***************************************************************/
	     
	    // step 4 -- close()
	    closeParams = ParametersBuilder.ConstructCloseParams(openResult);
	    closeResult = client.close(closeParams);
    	System.out.println("client.close() returned :\n" + closeResult);
	}
}
