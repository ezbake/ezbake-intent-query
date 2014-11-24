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

package ezbake.intentquerythrift;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ezbake.intent.query.processor.QueryGeneratorImpl;
import ezbake.intent.query.utils.Conversions;
import ezbake.intents.IntentType;
import ezbake.query.intents.BinaryOperator;
import ezbake.query.intents.BinaryPredicate;
import ezbake.query.intents.ColumnValue;
import ezbake.query.intents.Predicate;
import ezbake.query.intents.Query;
import ezbake.query.intents.QueryAtom;
/**
 * Unit test for simple App.
 */
public class QueryGeneratorTest
{
	
	//EzSecurityToken securityToken;
	QueryGeneratorImpl qgImpl = null;
	
	@Before
	public void setUp() throws Exception {
		
        qgImpl = new QueryGeneratorImpl(null);
		//token = createTestToken("somesid", "TS", Arrays.asList("TS"), Arrays.asList("TS"), "mockAppSecId");
        //System.out.println("Generating Security Token");
        //securityToken = ThriftTestUtils.generateTestSecurityToken(
				//"client", "client", "U");
		//securityToken.getValidity().setIssuedTo("client");
		//securityToken.getValidity().setIssuedFor("client");
	}
	
	
	@Test
	public void returncolumnstest()
    {
		Query q = new Query();
		List<String> reqCols = new ArrayList<String>();
		reqCols.add("ABC");
		reqCols.add("DEF");
		reqCols.add("GHI");
		reqCols.add("JKL");
		q.setRequestedColumns(reqCols);
		
		QueryAtom maintable = new QueryAtom();
		maintable.setIntent(IntentType.PERSON);
		
		q.setPrimaryQuery(maintable);
		String uuid = Conversions.generateUUID();
		
		String res = qgImpl.generateSQLString(q,uuid );
		
		assertEquals(res,"SELECT ABC,DEF,GHI,JKL FROM testapp_PERSON WHERE secuuid = '" + uuid + "'");
		
    }
	
	@Test
	public void binarypredicatestest()
    {
		Query q = new Query();
		List<String> reqCols = new ArrayList<String>();
		reqCols.add("ABC");
		reqCols.add("DEF");
		reqCols.add("GHI");
		reqCols.add("JKL");
		q.setRequestedColumns(reqCols);
		
		QueryAtom maintable = new QueryAtom();
		maintable.setIntent(IntentType.PERSON);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Predicate predicate = new Predicate();
		BinaryPredicate bpredicate = new BinaryPredicate();
		bpredicate.setColumnName("PRED1");
		bpredicate.setOperator(BinaryOperator.EQ);
		
		ColumnValue cv = new ColumnValue();
		cv.setStringValue("somestring");
		bpredicate.setValue(cv);
		predicate.setBinaryPredicate(bpredicate);
		
		Predicate predicate1 = new Predicate();
		BinaryPredicate bpredicate1 = new BinaryPredicate();
		bpredicate1.setColumnName("PRED2");
		bpredicate1.setOperator(BinaryOperator.GT);
		
		ColumnValue cv1 = new ColumnValue();
		cv1.setDoubleValue(1000000);
		bpredicate1.setValue(cv1);
		
		predicate1.setBinaryPredicate(bpredicate1);
		
		Predicate predicate2 = new Predicate();
		BinaryPredicate bpredicate2 = new BinaryPredicate();
		bpredicate2.setColumnName("PRED3");
		bpredicate2.setOperator(BinaryOperator.NE);
		
		ColumnValue cv2 = new ColumnValue();
		cv2.setBoolValue(false);
		bpredicate2.setValue(cv2);
		
		predicate2.setBinaryPredicate(bpredicate2);
		
		predicates.add(predicate);
		predicates.add(predicate1);
		predicates.add(predicate2);
		maintable.setPredicates(predicates);
		
		q.setPrimaryQuery(maintable);
		String uuid = Conversions.generateUUID();
		
		String res = qgImpl.generateSQLString(q,uuid );
		
		assertEquals(res,"SELECT ABC,DEF,GHI,JKL FROM testapp_PERSON WHERE testapp_PERSON.PRED1 = somestring AND testapp_PERSON.PRED2 > 1000000.0 AND testapp_PERSON.PRED3 != false AND secuuid = '" + uuid + "'");
    }
}
