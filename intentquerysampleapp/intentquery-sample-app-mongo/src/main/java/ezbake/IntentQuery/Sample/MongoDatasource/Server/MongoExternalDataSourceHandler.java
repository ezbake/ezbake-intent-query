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

package ezbake.IntentQuery.Sample.MongoDatasource.Server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.cloudera.impala.extdatasource.thrift.*;
import com.cloudera.impala.thrift.*;
import com.mongodb.MongoClient;

import ezbake.IntentQuery.Sample.MongoDatasource.Common.ParametersBuilder;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


public class MongoExternalDataSourceHandler implements ExternalDataSourceService.Iface {
	public static String	impalaExternalDsConfigFile = "impalaExternalDsConfig.xml";

	private String						dbHost;
	private int		    				dbPort;
	private String						dbName;
	private MongoClient					mongoClient;
	private DB							mongoDb;

	private Map<String, TableMetadata>		table_metadata_map;	// read from xml config file;
	private Map<String, TableScanStatus>	table_scan_status_map;
	

	/* "impalaTest_users" collection
		> db.impalaTest_users.findOne()
		{
			"_id" : ObjectId("53597c53b4a99bbaa3b2b8d5"),
			"user_id" : 0,						// int
			"last_name" : "last_0000",			// String
			"first_name" : "first_0000",		// String
			"age" : 26,							// int
			"gender" : "M",						// String
			"employer" : "Cloudera"				// String
		}	 
	*/
	
	/***********
	<tablesMetaData>
		<num_table>1</num_table>
		<tables>
			<table>
				<name>impalaTest_users</name>
				<num_columns>6</num_columns>
				<init_string></init_string>
				<columns>
					<column>
						<name>user_id</name>
						<primitiveType>INT</primitiveType>
						<len></len>
						<precision></precision>
						<scale></scale>
						<ops>LT,LE,EQ,NEQ,GE,GT</ops>
					</column>
					...
					<column>
					</column>
				<columns>
			</table>
			<table>
			</table>
		</tables>
	</tablesMetaData>
	***********/
	
	private void parseDataSourceMetadata() {
		
		table_metadata_map = new HashMap<String, TableMetadata>();
		
		try {
			Configuration	xmlConfig = new XMLConfiguration(impalaExternalDsConfigFile);
			
			dbHost = xmlConfig.getString("mongodb.host");
			dbPort = xmlConfig.getInt("mongodb.port");
			dbName = xmlConfig.getString("mongodb.database_name");
			
			String	key = null;
			String	table_name = null;
			int		num_columns= -1;
			String	init_str = null;
			List<TableColumnDesc>	table_col_desc_list = new ArrayList<TableColumnDesc>();

			int total_tables = xmlConfig.getInt("tablesMetaData.num_table");
			for (int i = 0; i < total_tables; i++) {
				// get table name
				key = String.format("tablesMetaData.tables.table(%d).name", i);
				table_name = xmlConfig.getString(key);
				// get table number of columns
				key = String.format("tablesMetaData.tables.table(%d).num_columns", i);
				num_columns = xmlConfig.getInt(key);
				// get table init_string
				key = String.format("tablesMetaData.tables.table(%d).init_string", i);
				init_str = xmlConfig.getString(key);
				
				// get columns
				String	col_name = null;
				String  col_type = null;
				int		col_len = 0;
				int		col_precision = 0;
				int		col_scale = 0;
				Set<String>	col_ops = null;
				
				for (int j = 0; j < num_columns; j++) {
					key = String.format("tablesMetaData.tables.table(%d).columns.column(%d).name", i, j);
					col_name = xmlConfig.getString(key);
					key = String.format("tablesMetaData.tables.table(%d).columns.column(%d).primitiveType", i, j);
					col_type = xmlConfig.getString(key);
					if (col_type.equals("CHAR")) {
						key = String.format("tablesMetaData.tables.table(%d).columns.column(%d).len", i, j);
						col_len = xmlConfig.getInt(key);
					} 
					else if (col_type.equals("DECIMAL")) {
						key = String.format("tablesMetaData.tables.table(%d).columns.column(%d).precision", i, j);
						col_precision = xmlConfig.getInt(key);
						key = String.format("tablesMetaData.tables.table(%d).columns.column(%d).scale", i, j);
						col_scale = xmlConfig.getInt(key);
					}
					
					key = String.format("tablesMetaData.tables.table(%d).columns.column(%d).ops", i, j);
					//List<String>	opsList = xmlConfig.getList(key);
					String[]	opsArray = xmlConfig.getStringArray(key);
					col_ops = new HashSet<String>(Arrays.asList(opsArray));

					TableColumnDesc		tableColumnDesc = new TableColumnDesc(col_name, col_type, col_len, col_precision, col_scale, col_ops);
					
					table_col_desc_list.add(tableColumnDesc);
				}
				
				TableMetadata	tableMetadata = new TableMetadata(table_name, num_columns, init_str, table_col_desc_list);
				
				System.out.println(tableMetadata);
				
				table_metadata_map.put(table_name, tableMetadata);
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public MongoExternalDataSourceHandler() {
		//tableOpenIdentity_to_handle_Map = new HashMap<TableOpenIdentity, String>();
		//handle_to_offset_Map = new HashMap<String, Integer>();
		
		table_scan_status_map = new HashMap<String, TableScanStatus>();
		
		// read table metadata into private Map<String, TableMetadata>	table_metadata_map;
		parseDataSourceMetadata();
		System.out.println(String.format("Total %d tables metadata loaded.", table_metadata_map.size()));
		
		try {
			/*
			Configuration	propertiesConfig = new PropertiesConfiguration(impalaDsPropertiesFile);
			
			dbHost = propertiesConfig.getString("mongodb.host.name");
			dbPort = propertiesConfig.getInt("mongodb.port");
			dbName = propertiesConfig.getString("mongodb.database.name");
			*/

			mongoClient = new MongoClient(dbHost, dbPort);
			mongoDb = mongoClient.getDB(dbName);
			
			System.out.println("dbHost = " + dbHost);
			System.out.println("dbPort = " + dbPort);
			System.out.println("dbName = " + dbName);
						
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public TGetStatsResult getStats(TGetStatsParams params) throws TException {
		String			table_name = params.getTable_name();
		String			init_string = params.getInit_string();
		List<List<TBinaryPredicate>>	predicates = params.getPredicates();
		
		List<Integer>	accepted_conjuncts = DataSourceMetadataOperations.EvaluateAcceptedPredicates(
			predicates, 
			table_name, 
			init_string, 
			table_metadata_map
		);
		
		List<List<TBinaryPredicate>> supported_predicates = 
			ParametersBuilder.ConstructAcceptedPredicate(
				predicates, 
				accepted_conjuncts
			);
		
		/*
		// build query object from supported predicates
		List<List<TBinaryPredicate>>	supported_predicates = new ArrayList<List<TBinaryPredicate>>();
		for (Integer index : accepted_conjuncts) {
			supported_predicates.add(predicates.get(index));
		}
		*/
		
        BasicDBObject	query = BuildQueryObject(supported_predicates);

        // now get the count of query
        DBCollection collection = mongoDb.getCollection(table_name);
        int	num_rows_estimate = collection.find(query).count();
		
		TGetStatsResult	result = new TGetStatsResult();
		
		result.setStatus(new TStatus(TStatusCode.OK, null));
		result.setAccepted_conjuncts(accepted_conjuncts);
		result.setNum_rows_estimate(num_rows_estimate);
		
		return result;
	}

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
	******************/
	@Override
	public TOpenResult open(TOpenParams params) throws TException {
		TUniqueId						query_id = params.getQuery_id();
		String							table_name = params.getTable_name();
		TTableSchema					row_schema = params.getRow_schema();
		int								batch_size = params.getBatch_size();
		List<List<TBinaryPredicate>>	predicates = params.getPredicates();

		System.out.println("query_id = " + query_id);
		System.out.println("Open table = " + table_name);
		System.out.println("row schema = " + row_schema);
		System.out.println("batch_size = " + batch_size);

		TOpenResult openResult = new TOpenResult();
		
		String	handle = "Handle_" + table_name + "_" + query_id.getHi() + "_" + query_id.getLo();
		
		//TableOpenIdentity	tableOpenIdentity = new TableOpenIdentity(query_id, table_name);
		//tableOpenIdentity_to_handle_Map.put(tableOpenIdentity, handle);
		//handle_to_offset_Map.put(handle, 0);

		// build keys from row_schema
		BasicDBObject keys = new BasicDBObject();
        keys.put("_id", 0);
        List<TColumnDesc>	columns = row_schema.getCols();
        for (TColumnDesc col: columns) {
        	String colName = col.getName();
        	keys.put(colName, 1);
        }
        
        // build query object from predicates
        BasicDBObject	query = BuildQueryObject(predicates);
        
        // now get the cursor
        DBCollection collection = mongoDb.getCollection(table_name);
        DBCursor	cursor = collection.find(query, keys);
        
        // map handle to cursor, offset
        //
        table_scan_status_map.put(handle, new TableScanStatus(cursor, batch_size, row_schema));
        /*
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
              cursor.close();
        }
        */
        
		openResult.setStatus(new TStatus(TStatusCode.OK, null));
		openResult.setScan_handle(handle);
		
		return openResult;
	}

	
	private BasicDBObject BuildQueryObject(List<List<TBinaryPredicate>> predicates) {
		BasicDBObject	queryObj = new BasicDBObject();
		
		int	num_list = predicates.size();
		
        List<BasicDBObject> andClauses = new ArrayList<BasicDBObject>();

		for (int i = 0; i < num_list; i++) {
			List<TBinaryPredicate>	list = predicates.get(i);
			TBinaryPredicate	p = null;
			BasicDBObject		queryObjFromOneList = new BasicDBObject();

			// In the list, each TBinaryPredicate are "OR" relation
			int	num_predicate = list.size();
			if (num_predicate > 1) {
				List<BasicDBObject>	orClauses = new ArrayList<BasicDBObject>();
				
				for (int j = 0; j < num_predicate; j++) {
					p = list.get(j);
					orClauses.add(BuildDBObjectFromBinaryPredicate(p));
				}
				
				queryObjFromOneList.put("$or", orClauses);
				
			}
			else if (num_predicate == 1) {
				queryObjFromOneList = BuildDBObjectFromBinaryPredicate(list.get(0));
			}
			
			// now add queryObjFromOneList into andClauses
			andClauses.add(queryObjFromOneList);
		}
		
		queryObj.put("$and", andClauses);

		return queryObj;
	}

	
	/************************************************
	struct TBinaryPredicate {
		  // Column on which the predicate is applied.
		  1: required TColumnDesc col

		  // Comparison operator.
		  2: required TComparisonOp op

		  // Value on the right side of the binary predicate.
		  3: required ImpalaTypes.TColumnValue value
		}
	*************************************************/
	private BasicDBObject BuildDBObjectFromBinaryPredicate(TBinaryPredicate p) {
		TColumnDesc	col_desc = p.getCol();
		
		String	col_name = col_desc.getName();
		TColumnType col_type = col_desc.getType();
		TPrimitiveType col_primitive_type = col_type.getType();

		TComparisonOp	op = p.getOp();	// need translation
		String	opString = MapOpEnumToString(op);
		
		TColumnValue	value = p.getValue();
		
        BasicDBObject queryObject = new BasicDBObject();
        
        if (opString.isEmpty()) {
        	switch (col_primitive_type) {
        	  case BOOLEAN:
        		  queryObject.append(col_name, value.boolVal);
        		  break;
        	  case TINYINT:
        	  case SMALLINT:
        	  case INT:
        		  queryObject.append(col_name, value.intVal);
        		  break;
        	  case BIGINT:
        		  queryObject.append(col_name, value.longVal);
        		  break;
        	  case FLOAT:
        	  case DOUBLE:
        	  case DECIMAL:
        		  queryObject.append(col_name, value.doubleVal);
        		  break;
           	  case STRING:
        		  queryObject.append(col_name, value.stringVal);
        		  break;
        	  default:
        		  break;
        	}  
        }
        else {		// opString not empty
        	switch (col_primitive_type) {
        	  case BOOLEAN:
        		  queryObject.append(col_name, new BasicDBObject(opString, value.boolVal));
        		  break;
        	  case TINYINT:
        	  case SMALLINT:
        	  case INT:
        		  queryObject.append(col_name, new BasicDBObject(opString, value.intVal));
        		  break;
        	  case BIGINT:
        		  queryObject.append(col_name, new BasicDBObject(opString, value.longVal));
        		  break;
        	  case FLOAT:
        	  case DOUBLE:
        	  case DECIMAL:
        		  queryObject.append(col_name, new BasicDBObject(opString, value.doubleVal));
        		  break;
           	  case STRING:
        		  queryObject.append(col_name, new BasicDBObject(opString, value.stringVal));
        		  break;
        	  default:
        		  break;
        	}  
        }
        
		return queryObject;
	}


	private String MapOpEnumToString(TComparisonOp op) {
		// LT(0), LE(1), EQ(2), NE(3), GE(4), GT(5);
		switch (op) {
	      case LT:
	        return "$lt";
	      case LE:
	        return "lte";
	      case EQ:
	        return "";
	      case NE:
	        return "$ne";
	      case GE:
	        return "$gte";
	      case GT:
	        return "$gt";
	      default:
	        return null;
	    }
	}


	@Override
	public TGetNextResult getNext(TGetNextParams params) throws TException {
		/***
		 * struct TGetNextResult {
			  1: required ImpalaTypes.TStatus status
			
			  // If true, reached the end of the result stream; subsequent calls to
			  // getNext() won’t return any more results
			  2: required bool eos
			
			  // A batch of rows to return, if any exist. The number of rows in the batch
			  // should be less than or equal to the batch_size specified in TOpenParams.
			  3: optional TRowBatch rows
			}
		 */
		System.out.println("getNext() called");
		TGetNextResult	result = new TGetNextResult();
		
		TableScanStatus	scan_status = table_scan_status_map.get(params.getScan_handle());
		if (null == scan_status) {
			List<String>	errMsg = new ArrayList<String>();
			errMsg.add("Can not find the scan_handle");
			result.setStatus(new TStatus(TStatusCode.ANALYSIS_ERROR, errMsg));
		}
		else {
			DBCursor		cursor = scan_status.getCursor();
			int				batch_size = scan_status.getBatchsize();
			TTableSchema	schema = scan_status.getSchema();
			
			int			count = 0;
			List<TRow>	rows = new ArrayList<TRow>();
			
			while ( (count < batch_size) && cursor.hasNext() ) {
		        DBObject cur = cursor.next();
		        System.out.println(cur);
		        // now convert DBObject to TRow
		        TRow	row = generateTRowFromDBObject(schema, cur);
		        rows.add(row);
		        count++;
			}
			TRowBatch	rowBatch = new TRowBatch(rows);
			
			result.setStatus(new TStatus(TStatusCode.OK, null));
			result.setEos((count < batch_size) ? true : false);
			result.setRows(rowBatch);
		}
		
		System.out.println(result);
		return result;
	}

	private TRow generateTRowFromDBObject(TTableSchema schema, DBObject cur) {
		List<TColumnValue>	col_value_list = new ArrayList<TColumnValue>();

		List<TColumnDesc>	col_desc_list = schema.getCols();
		
		String col_name = null;
		//TColumnType col_type;
		TPrimitiveType col_primitive_type;
		Object	col_value;

		// TODO: fixing code so that it compiles; need to investigate lines 529,535,538,543 
 		//       to make sure it is correct
		for (TColumnDesc col_desc : col_desc_list) {
			col_name = col_desc.getName();
			col_primitive_type = col_desc.getType().getType();
			
			col_value = cur.get(col_name);
			TColumnValue	value = new TColumnValue();
			// based on col_primitive_type, set value
			switch (col_primitive_type) {
            	case BOOLEAN:
	                value.setBoolVal((Boolean)col_value);
	                break;
	            case TINYINT:
	            case SMALLINT:
	            case INT:
	                value.setIntVal((Integer)col_value);
	                break;
	            case BIGINT:
	                value.setLongVal((Long)col_value);
	                break;
	            case FLOAT:
	            case DOUBLE:
	            case DECIMAL:
	                value.setDoubleVal((Double)col_value);
	                break;
	            case STRING:
	                value.setStringVal((String)col_value);
	                break;
	            default:
	                break;
	        }  
			col_value_list.add(value);
		}
		
		return new TRow(col_value_list);
	}

	/**********
	struct TCloseResult {
	  1: required ImpalaTypes.TStatus status
	}
	**********/
	@Override
	public TCloseResult close(TCloseParams params) throws TException {
		System.out.println("close() called");
		TCloseResult	result = new TCloseResult();
		
		TableScanStatus	scan_status = table_scan_status_map.get(params.getScan_handle());
		if (null == scan_status) {
			List<String>	errMsg = new ArrayList<String>();
			errMsg.add("Can not find the scan_handle");
			result.setStatus(new TStatus(TStatusCode.ANALYSIS_ERROR, errMsg));
		}
		else {
			scan_status.getCursor().close();
			// now remove TableScanStatus from table_scan_status_map
			table_scan_status_map.remove(params.getScan_handle());
			result.setStatus(new TStatus(TStatusCode.OK, null));
		}
		return result;
	}

}
