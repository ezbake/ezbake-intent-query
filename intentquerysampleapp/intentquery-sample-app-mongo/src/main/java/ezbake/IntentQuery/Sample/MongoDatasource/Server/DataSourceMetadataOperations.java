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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cloudera.impala.extdatasource.thrift.*;
import com.cloudera.impala.thrift.*;


/*******************************************************************************
 * 
 * @author rding
 * 
 * Utility Class, used to evaluate predicates
 * List<List<TBinaryPredicate>> predicates
 * 
 ******************************************************************************/
public class DataSourceMetadataOperations {
	
	// evaluate a single TBinaryPredicate, against the table metadata defined
	// in configuration file (impalaExternalDsConfig.xml)
	// (1) the col_name is found
	// (2) the col_type is matched
	// (3) the OP is supported
	private static boolean EvaluateCurrentPredicate(
		TBinaryPredicate predicate,
		String table_name, 
		String init_string, 
		Map<String, TableMetadata> table_metadata_map
	) 
	{
		boolean	result = true;
		boolean col_found = false;
		
		String	col_name_to_check = predicate.getCol().getName();
		
		// (1) given table_name, find TableMetadata
		TableMetadata	metadata = table_metadata_map.get(table_name);
		List<TableColumnDesc>	descs = metadata.getColumns();
		
		TableColumnDesc	desc = null;
		System.out.println(String.format("### DataSourceMetadataOperations.EvaluateCurrentPredicate() -- First trying to find columon definition in metadata: (%s) ###", col_name_to_check));
		for (int i = 0; i < metadata.getNum_columns(); i++) {
			desc = descs.get(i);
			if (desc.getCol_name().equals(col_name_to_check)) {
				// find the column
				col_found = true;
				break;
			}
		}
		
		if (false == col_found) {
			System.out.println(String.format("### Column: (%s) not found in table defintion. Return false. ###", col_name_to_check));
			return false;
		}

		String	cur_type = desc.getPrimitive_type();
		//TColumnType col_type_to_check = predicate.getCol().getType();
		TPrimitiveType	col_type_to_check = predicate.getCol().getType().getType();
				
		// check (1) type match, compare cur_type with col_type_to_check
		if (TPrimitiveType.valueOf(cur_type) != col_type_to_check) {
			System.out.println(String.format("Type mis-match, expect (%s), but input is (%s)", cur_type, col_type_to_check));
			result = false;
		}
		else {
			System.out.println(String.format("Type (%s) matched, next check OP:", cur_type));
			// check (2) OP supported
			TComparisonOp	op = predicate.getOp();
			
			result = desc.getOps().contains(op.toString());
			System.out.println(String.format("OP checked done: (%s) in %s return %s", op.toString(), desc.getOps(), result));
		}
		
		System.out.println(String.format("### Done with checking column: (%s) ###\n", col_name_to_check));

		return result;
	}

	// relations between each element in List<TBinaryPredicate> predicateList
	// is "OR"
	private static boolean EvaluateCurrentPredicateList(
		List<TBinaryPredicate> predicateList,
		String table_name, 
		String init_string, 
		Map<String, TableMetadata> table_metadata_map
	) 
	{
		boolean		result = true;
		
		int		numOfEvaluation = predicateList.size();
		
		for (int i = 0; i < numOfEvaluation; i++) {
			TBinaryPredicate predicate = predicateList.get(i);
			result = EvaluateCurrentPredicate(
				predicate, 
				table_name, 
				init_string, 
				table_metadata_map
			);
			
			if (false == result) {
				break;
			}
		}

		return result;
	}


	// relations between each element in List<List<TBinaryPredicate>> predicates
	// is "AND"
	public static List<Integer> EvaluateAcceptedPredicates(
		List<List<TBinaryPredicate>> predicates, 
		String table_name, 
		String init_string, 
		Map<String, TableMetadata> table_metadata_map
	) 
	{
		List<Integer>	result = new ArrayList<Integer>();
		
		int		numOfEvaluation = predicates.size();
		
		boolean supported = false;
		for (int i = 0; i < numOfEvaluation; i++) {
			List<TBinaryPredicate>	curList = predicates.get(i);
			supported = EvaluateCurrentPredicateList(
				curList, 
				table_name, 
				init_string, 
				table_metadata_map
			);
			if (supported) {
				result.add(i);
			}
		}
		
		return result;
	}

}
