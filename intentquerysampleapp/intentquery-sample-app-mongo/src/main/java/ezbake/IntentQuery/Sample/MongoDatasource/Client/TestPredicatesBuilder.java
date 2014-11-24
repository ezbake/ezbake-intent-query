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

import java.util.ArrayList;
import java.util.List;

import com.cloudera.impala.extdatasource.thrift.TBinaryPredicate;
import com.cloudera.impala.extdatasource.thrift.TColumnDesc;
import com.cloudera.impala.extdatasource.thrift.TComparisonOp;
import com.cloudera.impala.thrift.TColumnType;
import com.cloudera.impala.thrift.TColumnValue;
import com.cloudera.impala.thrift.TPrimitiveType;

public class TestPredicatesBuilder {

	// (employer = "Cloudera") 
	public static List<List<TBinaryPredicate>> 	ConstructPredicates_01() {
		List<List<TBinaryPredicate>>	predicates = new ArrayList<List<TBinaryPredicate>>();
		List<TBinaryPredicate>	binaryPredicateList1 = new ArrayList<TBinaryPredicate>();
		
		TColumnDesc			colDesc_1 = new TColumnDesc("employer", new TColumnType(TPrimitiveType.STRING));
		TColumnValue		colValue_1 = new TColumnValue();
		colValue_1.setStringVal("Cloudera");
		TBinaryPredicate	p_1 = new TBinaryPredicate(colDesc_1, TComparisonOp.EQ, colValue_1);

		binaryPredicateList1.add(p_1);
		predicates.add(binaryPredicateList1);

		return predicates;
	}

	
	// (employer = "Cloudera") and (gender = "M")
	public static List<List<TBinaryPredicate>> 	ConstructPredicates_02() {

		List<List<TBinaryPredicate>>	predicates = new ArrayList<List<TBinaryPredicate>>();
		List<TBinaryPredicate>	binaryPredicateList_1 = new ArrayList<TBinaryPredicate>();
		
		TColumnDesc			colDesc = null;
		TColumnValue		colValue = null;
		TBinaryPredicate	binary_p = null;
		
		colDesc = new TColumnDesc("employer", new TColumnType(TPrimitiveType.STRING));
		colValue = new TColumnValue();
		colValue.setStringVal("Cloudera");
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.EQ, colValue);

		binaryPredicateList_1.add(binary_p);

		
		List<TBinaryPredicate>	binaryPredicateList_2 = new ArrayList<TBinaryPredicate>();
		
		colDesc = new TColumnDesc("gender", new TColumnType(TPrimitiveType.STRING));
		colValue = new TColumnValue();
		colValue.setStringVal("M");
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.EQ, colValue);

		binaryPredicateList_2.add(binary_p);

		
		predicates.add(binaryPredicateList_1);
		predicates.add(binaryPredicateList_2);
				
		return predicates;
	}

	
	// (employer2 = "Cloudera") and (employer = "Cloudera") and (age > 40 or gender = "F") 
	public static List<List<TBinaryPredicate>> 	ConstructPredicates_03() {
		List<List<TBinaryPredicate>>	predicates = new ArrayList<List<TBinaryPredicate>>();
		
		TColumnDesc			colDesc = null;
		TColumnValue		colValue = null;
		TBinaryPredicate	binary_p = null;
		
		// a wrong predicate
		List<TBinaryPredicate>	binaryPredicateList_0 = new ArrayList<TBinaryPredicate>();
		colDesc = new TColumnDesc("employer2", new TColumnType(TPrimitiveType.STRING));
		colValue = new TColumnValue();
		colValue.setStringVal("Cloudera");
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.EQ, colValue);
	
		binaryPredicateList_0.add(binary_p);
		
		// employer = Cloudera
		List<TBinaryPredicate>	binaryPredicateList_1 = new ArrayList<TBinaryPredicate>();
		colDesc = new TColumnDesc("employer", new TColumnType(TPrimitiveType.STRING));
		colValue = new TColumnValue();
		colValue.setStringVal("Cloudera");
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.EQ, colValue);
	
		binaryPredicateList_1.add(binary_p);
	
		// age > 30 or gender = "F"
		List<TBinaryPredicate>	binaryPredicateList_2 = new ArrayList<TBinaryPredicate>();
		
		colDesc = new TColumnDesc("age", new TColumnType(TPrimitiveType.INT));
		colValue = new TColumnValue();
		colValue.setIntVal(40);
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.GT, colValue);
	
		binaryPredicateList_2.add(binary_p);
	
		colDesc = new TColumnDesc("gender", new TColumnType(TPrimitiveType.STRING));
		colValue = new TColumnValue();
		colValue.setStringVal("F");
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.EQ, colValue);
		
		binaryPredicateList_2.add(binary_p);
	
		predicates.add(binaryPredicateList_0);
		predicates.add(binaryPredicateList_1);
		predicates.add(binaryPredicateList_2);
		
		return predicates;
	}
	
	// employer = "Cloudera" and (age > 30) and (age < 50) 
	public static List<List<TBinaryPredicate>> 	ConstructPredicates_04() {
		List<List<TBinaryPredicate>>	predicates = new ArrayList<List<TBinaryPredicate>>();
		List<TBinaryPredicate>	binaryPredicateList_1 = new ArrayList<TBinaryPredicate>();
		
		TColumnDesc			colDesc = null;
		TColumnValue		colValue = null;
		TBinaryPredicate	binary_p = null;
		
		// employer = Cloudera
		colDesc = new TColumnDesc("employer", new TColumnType(TPrimitiveType.STRING));
		colValue = new TColumnValue();
		colValue.setStringVal("Cloudera");
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.EQ, colValue);
	
		binaryPredicateList_1.add(binary_p);
	
		// age > 30 
		List<TBinaryPredicate>	binaryPredicateList_2 = new ArrayList<TBinaryPredicate>();
		
		colDesc = new TColumnDesc("age", new TColumnType(TPrimitiveType.INT));
		colValue = new TColumnValue();
		colValue.setIntVal(30);
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.GT, colValue);
	
		binaryPredicateList_2.add(binary_p);
	
		// age > 30 
		List<TBinaryPredicate>	binaryPredicateList_3 = new ArrayList<TBinaryPredicate>();
		
		colDesc = new TColumnDesc("age", new TColumnType(TPrimitiveType.INT));
		colValue = new TColumnValue();
		colValue.setIntVal(50);
		binary_p = new TBinaryPredicate(colDesc, TComparisonOp.LT, colValue);
	
		binaryPredicateList_3.add(binary_p);
	
		predicates.add(binaryPredicateList_1);
		predicates.add(binaryPredicateList_2);
		predicates.add(binaryPredicateList_3);
		
		return predicates;
	}
}
