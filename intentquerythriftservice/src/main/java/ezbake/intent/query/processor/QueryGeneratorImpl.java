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

package ezbake.intent.query.processor;

import java.util.List;

import ezbake.configuration.EzConfiguration;
import ezbake.intent.query.utils.Conversions;
import ezbake.intents.IntentType;
import ezbake.query.intents.BinaryPredicate;
import ezbake.query.intents.Predicate;
import ezbake.query.intents.Query;
import ezbake.query.intents.QueryJoin;
import ezbake.query.intents.UnaryPredicate;

public class QueryGeneratorImpl implements QueryGenerator {
	
	private final InsService insservice;
	
	public QueryGeneratorImpl(final EzConfiguration configuration) {
		super();
		if ( configuration != null ) {
			insservice = new InsService(configuration);
		} else {
			insservice = null;
		}
		
	}

	@Override
	public String generateSQLString(Query qry, String uuid) {
		IntentType primaryTable = qry.getPrimaryQuery().getIntent();
		List<QueryJoin> joinedqueries = qry.getJoinedQueries();
		String tableName = "";
		if ( insservice != null ) {
			tableName = insservice.getAppName(primaryTable.name());
		} else {
			tableName = "testapp";
		}
		
		//process qry object and create SQL string
		String str = "SELECT ";
		String predicatesStr = "";
		//add requested column names to the select string
		int iter = 0;
		int reqcolumnssize = qry.getRequestedColumns().size();
		while ( reqcolumnssize > iter) {
			str+= qry.getRequestedColumns().get(iter);
			if ( (reqcolumnssize - 1) > iter ) {
				str+= ",";
			}
			iter++;
		}
		//add tables to the select string
		str+= " FROM ";
		str+= tableName + "_" + primaryTable.name();
		//prepare predicate string for main table
		if (qry.getPrimaryQuery().getPredicates() != null) {
			predicatesStr = generatePredicatesString( tableName + "_" + primaryTable.name(),qry.getPrimaryQuery().getPredicates());
		}
		
		
		if ( joinedqueries != null ) {
			for (QueryJoin qj : joinedqueries) {
				str+=",";
				str+= qj.getQuery().getIntent().name();
				predicatesStr+= generatePredicatesString(qj.getQuery().getIntent().name(),qj.getQuery().getPredicates());
			}
		}
		//add security token as a predicate
		predicatesStr+= "secuuid = " + "'" + uuid + "'";
		str+= " WHERE " + predicatesStr;
		
		return str;
	}
	
	public String generatePredicatesString(String tablename, List<Predicate> predicates) {
		String str = "";
		for ( Predicate predicate : predicates) {
			if ( predicate.isSet(1)) {
				BinaryPredicate binpredicate = predicate.getBinaryPredicate();
				str+= tablename + "." + binpredicate.getColumnName() + " " + 
						Conversions.convertOperatorToString(binpredicate.getOperator()) 
						+ " " + Conversions.convertColValueToObject(binpredicate.getValue());
			} else if (predicate.isSet(2)) {
				UnaryPredicate unarypredicate = predicate.getUnaryPredicate();
				//str+= tablename + "." + unarypredicate.getPredicateName() + "(" + unarypredicate. + ")";
			}
			str+= " AND ";
		}
		
		return str;
	}

}
