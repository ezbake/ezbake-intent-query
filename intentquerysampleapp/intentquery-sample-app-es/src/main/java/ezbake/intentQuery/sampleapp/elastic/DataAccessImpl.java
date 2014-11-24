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

package ezbake.intentQuery.sampleapp.elastic;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import ezbake.base.thrift.Classification;
import ezbake.base.thrift.EzSecurityToken;
import ezbake.base.thrift.Visibility;
import ezbake.data.elastic.thrift.Document;
import ezbake.data.elastic.thrift.IndexResponse;
import ezbake.data.elastic.thrift.Page;
import ezbake.data.elastic.thrift.Query;
import ezbake.data.elastic.thrift.SearchResult;
import ezbake.data.elastic.thrift.EzElastic;
import ezbake.data.elastic.thrift.EzElastic.Client;
import ezbake.configuration.EzConfiguration;
import ezbake.ezconfiguration.helpers.ApplicationConfiguration;
import ezbake.intents.IntentType;
import ezbake.query.basequeryableprocedure.ColumnData;
import ezbake.query.basequeryableprocedure.ColumnDataValues;
import ezbake.query.basequeryableprocedure.GetPageResult;
import ezbake.query.basequeryableprocedure.PrepareStats;
import ezbake.query.basequeryableprocedure.RowBatch;
import ezbake.query.intents.BinaryPredicate;
import ezbake.query.intents.Person;
import ezbake.query.intents.base.SecurityLabel;
import ezbake.query.intents.base.StandardObject;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.thrift.TException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.common.xcontent.XContentBuilder;
import ezbake.thrift.ThriftClientPool;
import ezbake.thrift.ThriftTestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 
 */
public class DataAccessImpl implements DataAccess {
	private static Logger appLog = LoggerFactory.getLogger(DataAccessImpl.class);
	private static Logger secLog = LoggerFactory.getLogger("SECURITY." + DataAccessImpl.class);
	private ThriftClientPool pool;
	private static final String APP_TYPE = "impala_testapp";
	private static EzSecurityToken fakeToken;
	private boolean initialized = false;
	

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public boolean init(EzConfiguration config) throws Exception {		
		pool = new ThriftClientPool(config.getProperties());

		fakeToken = ThriftTestUtils.generateTestSecurityToken(
				config.getProperties().getProperty((ApplicationConfiguration.SECURITY_ID_KEY)),
				config.getProperties().getProperty((ApplicationConfiguration.SECURITY_ID_KEY)),
				Arrays.asList("U"));
		
		if (!dataExists()) {
			insertData();
		}
		
		initialized = true;
		
		appLog.info("DataAccessImpl object has been initialized");
	
		return initialized;
	}
	

	/**
	 * 
	 * @param columnnames
	 * @param document
	 * @return
	 */
	private Person translateColumnName(List<String> columnnames, Document document) {
		ObjectMapper objectMapper = new ObjectMapper();
		Person p = new Person();
		try {
			ezbake.intentQuery.sampleapp.elastic.Person documentPerson = 
					objectMapper.readValue(document.get_jsonObject(), ezbake.intentQuery.sampleapp.elastic.Person.class);

			for (String columnname : columnnames) {	
				if (columnname.equalsIgnoreCase(Person._Fields.ALIASES.getFieldName())) {
					p.setFieldValue(Person._Fields.ALIASES, documentPerson.getAliases());
				} else if (columnname.equalsIgnoreCase(Person._Fields.BASE.getFieldName())) {
					// TODO: not supported
					p.setFieldValue(Person._Fields.BASE, null);
				} else if (columnname.equalsIgnoreCase(Person._Fields.BIRTH_DATE.getFieldName())) {
					// TODO: not supported 
					p.setFieldValue(Person._Fields.BIRTH_DATE, null);
				} else if (columnname.equalsIgnoreCase(Person._Fields.COUNTRY.getFieldName())) {
					p.setFieldValue(Person._Fields.COUNTRY, documentPerson.getCountry());
				} else if (columnname.equalsIgnoreCase(Person._Fields.FIRST_NAME.getFieldName())) {
					p.setFieldValue(Person._Fields.FIRST_NAME, documentPerson.getFirstname());
				} else if (columnname.equalsIgnoreCase(Person._Fields.LAST_NAME.getFieldName())) {
					p.setFieldValue(Person._Fields.LAST_NAME, documentPerson.getLastname());
				} else if (columnname.equalsIgnoreCase(Person._Fields.MIDDLE_NAME.getFieldName())) {
					p.setFieldValue(Person._Fields.MIDDLE_NAME, documentPerson.getMiddlename());
				} else if (columnname.equalsIgnoreCase(Person._Fields.TIDE_ID.getFieldName())) {
					// TODO: not supported
					p.setFieldValue(Person._Fields.TIDE_ID, null);
				} else if (columnname.equalsIgnoreCase(Person._Fields.TKB_ID.getFieldName())) {
					// TODO: not supported
					p.setFieldValue(Person._Fields.TKB_ID, null);
				} else if (columnname.equalsIgnoreCase(Person._Fields.TYPE.getFieldName())) {
					// TODO: not supported
					p.setFieldValue(Person._Fields.TYPE, null);
				}
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
	}
	
	@Override
	public GetPageResult getPage(IntentType intenttype, int offset, int pagesize, List<String> columnnames, List<List<BinaryPredicate>> predicates, EzSecurityToken securityToken) throws DataAccessException {
		GetPageResult result = new GetPageResult();
		Client c = null;
		List<Person> people = new ArrayList<Person>();
		
		if (intenttype != IntentType.PERSON) {
			throw new DataAccessException("Unsupported Intent: " + intenttype.name());
		}
		
		secLog.info("getPage called with usertoken({}) predicates({})", securityToken, predicates);
		
		try {
			c = getClient();
			
			Page p = new Page();
			p.setOffset(offset);
			p.setPageSize((short) pagesize);
			
			Query q = new Query();
			q.setPage(p);
			
			if (predicates.size() == 0) {
				q.setSearchString(new MatchAllDocsQuery().toString());
			} else {
				q.setSearchString(generateQueryFromPredicates(predicates).toString());
			}
			
			appLog.info("Generated query: {}", q.getSearchString());
			
			SearchResult searchResult = c.query(q, securityToken);
			
			int startingIndex = offset;
			long endingIndex = startingIndex + pagesize;
			if (endingIndex > searchResult.getTotalHits()) {
				result.setEos(true);
			}
			
			for (Document d : searchResult.getMatchingDocuments()) {
				people.add(translateColumnName(columnnames, d));
			}
			
			RowBatch rb = convertPeopleToRowBatch(columnnames, people);
			
			result.setRows(rb);
			
		} catch (Exception e) {
			appLog.error("Encountered an exception: " + e);
			e.printStackTrace();
		} finally {
			pool.returnToPool(c);
		}
		
		return result;

	}
	
	@Override
	public PrepareStats getPrepareStats(String tableName, String initString,
			List<List<BinaryPredicate>> predicates, EzSecurityToken securityToken) throws DataAccessException {
		PrepareStats ps = new PrepareStats();
		List<Integer> accepted = new ArrayList<Integer>();
		
		// TODO: need logic to determine which predicates we handle
		// Must handle ALL of the predicates in the inner list in order to be
		// accepted; right now just default to accepting all of the predicates
		for (int i = 0; i < predicates.size(); i++) {
			accepted.add(i);
		}
		
		ps.setAcceptedConjuncts(accepted);
		
		Client c = null;
		try {
			c = getClient();
			Set<String> types = new HashSet<String>();
			types.add(APP_TYPE);
			long estimate = c.countByType(types, securityToken);
			
			ps.setNumberOfRowsEstimate((int) estimate);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			ps.setNumberOfRowsEstimate(10);
			
		} finally {
			pool.returnToPool(c);
		}
		
		return ps;
	}
	
	@Override
	public boolean ping() {
		Client c = null;
		
		try {
			c = getClient();
			return c.ping();
		} catch (Exception ex) {
			return false;
		} finally {
			pool.returnToPool(c);
		}
	}
	
	@Override
	public void close() {
		if (pool != null) {
			appLog.info("Closing the DataAccessImpl object");
			pool.close();
			pool = null;
		}
	}
	
	/**
	 * 
	 * @param documents
	 * @return
	 */
	private RowBatch convertPeopleToRowBatch(List<String> columnnames, List<Person> people) {
		appLog.info("Converting {} people objects into a RowBatch object", people.size());
		RowBatch rowBatch = new RowBatch();
		
		List<Boolean> firstNameIsNull = new ArrayList<Boolean>();
		List<String> firstNameValueList = new ArrayList<String>();
		
		List<Boolean> middleNameIsNull = new ArrayList<Boolean>();
		List<String> middleNameValueList = new ArrayList<String>();
		
		List<Boolean> lastNameIsNull = new ArrayList<Boolean>();
		List<String> lastNameValueList = new ArrayList<String>();
		
		List<Boolean> countryIsNull = new ArrayList<Boolean>();
		List<String> countryValueList = new ArrayList<String>();
		
		// Ignoring these for right now
		List<Boolean> aliasesIsNull = new ArrayList<Boolean>();
		List<Boolean> birthdateIsNull = new ArrayList<Boolean>();
		List<Boolean> tideIdIsNull = new ArrayList<Boolean>();
		List<Boolean> tkbIdIsNull = new ArrayList<Boolean>();
		List<Boolean> typeIsNull = new ArrayList<Boolean>();
		
		for (Person p : people) {
			if (p.getFirstName() == null) {
				firstNameIsNull.add(true);
			} else {
				firstNameIsNull.add(false);
				firstNameValueList.add(p.getFirstName());
			}
			
			if (p.getMiddleName() == null) {
				middleNameIsNull.add(true);
			} else {
				middleNameIsNull.add(false);
				middleNameValueList.add(p.getMiddleName());
			}
			
			if (p.getLastName() == null) {
				lastNameIsNull.add(true);
			} else {
				lastNameIsNull.add(false);
				lastNameValueList.add(p.getLastName());
			}
			
			if (p.getCountry() == null) {
				countryIsNull.add(true);
			} else {
				countryIsNull.add(false);
				countryValueList.add(p.getCountry());
			}
			
			// keep things simple; ignore these right now
			aliasesIsNull.add(true);
			birthdateIsNull.add(true);
			tideIdIsNull.add(true);
			tkbIdIsNull.add(true);
			typeIsNull.add(true);
		}
		
		// First name
		ColumnData firstNameColumnData = new ColumnData();
		ColumnDataValues firstNameValues = new ColumnDataValues();
		firstNameValues.setString_vals(firstNameValueList);
		firstNameColumnData.setIs_null(firstNameIsNull);
		firstNameColumnData.setValues(firstNameValues);
		
		// Middle name
		ColumnData middleNameColumnData = new ColumnData();
		ColumnDataValues middleNameValues = new ColumnDataValues();
		middleNameValues.setString_vals(middleNameValueList);
		middleNameColumnData.setIs_null(middleNameIsNull);
		middleNameColumnData.setValues(middleNameValues);
		
		// Last name
		ColumnData lastNameColumnData = new ColumnData();
		ColumnDataValues lastNameValues = new ColumnDataValues();
		lastNameValues.setString_vals(lastNameValueList);
		lastNameColumnData.setIs_null(lastNameIsNull);
		lastNameColumnData.setValues(lastNameValues);
		
		// country
		ColumnData countryColumnData = new ColumnData();
		ColumnDataValues countryValues = new ColumnDataValues();
		countryValues.setString_vals(countryValueList);
		countryColumnData.setIs_null(countryIsNull);
		countryColumnData.setValues(countryValues);
		
		// the ignored things
		ColumnData aliasColumnData = new ColumnData();
		aliasColumnData.setIs_null(aliasesIsNull);
		ColumnData birthDateColumnData = new ColumnData();
		birthDateColumnData.setIs_null(birthdateIsNull);
		ColumnData tideIdColumnData = new ColumnData();
		tideIdColumnData.setIs_null(tideIdIsNull);
		ColumnData tkbIdColumnData = new ColumnData();
		tkbIdColumnData.setIs_null(tkbIdIsNull);
		ColumnData typeColumnData = new ColumnData();
		typeColumnData.setIs_null(typeIsNull);
		
		List<ColumnData> columnData = new ArrayList<ColumnData>();
		
		for (String name : columnnames) {
			if (name.equalsIgnoreCase(Person._Fields.FIRST_NAME.getFieldName())) {
				columnData.add(firstNameColumnData);
			} else if (name.equalsIgnoreCase(Person._Fields.MIDDLE_NAME.getFieldName())) {
				columnData.add(middleNameColumnData);
			} else if (name.equalsIgnoreCase(Person._Fields.LAST_NAME.getFieldName())) {
				columnData.add(lastNameColumnData);
			} else if (name.equalsIgnoreCase(Person._Fields.COUNTRY.getFieldName())) {
				columnData.add(countryColumnData);
			} else if (name.equalsIgnoreCase(Person._Fields.ALIASES.getFieldName())) {
				columnData.add(aliasColumnData);
			} else if (name.equalsIgnoreCase(Person._Fields.BIRTH_DATE.getFieldName())) {
				columnData.add(birthDateColumnData);
			} else if (name.equalsIgnoreCase(Person._Fields.TIDE_ID.getFieldName())) {
				columnData.add(tideIdColumnData);
			} else if (name.equalsIgnoreCase(Person._Fields.TKB_ID.getFieldName())) {
				columnData.add(tkbIdColumnData);
			} else if (name.equalsIgnoreCase(Person._Fields.TYPE.getFieldName())) {
				columnData.add(typeColumnData);
			} else {
				// TODO: need to throw an exception or something to indicate that this column name isn't valid????
				appLog.info("Didn't add any column data for name({})", name);
			}
		}
		
		rowBatch.setCols(columnData);
		
		return rowBatch;
	}
	
	/**
	 * Helper method to determine if a particular ES index exists
	 * 
	 * @param name - Index name
	 * @param type - Index type
	 * @return true if the index of name and type exists; else false
	 */
	private boolean dataExists() {
		Client c = null;
		boolean result = true;
		
		appLog.info("Checking to see if data is pre-existing...");
		
		
		try {
			c = getClient();
			Set<String> types = new HashSet<String>();
			types.add(APP_TYPE);
			long typesCount = c.countByType(types, fakeToken);
			
			appLog.info("typesCount: {}", typesCount);
			
			if (typesCount <= 0){
			  result = false;
			}	
		} catch (Exception ex) {
			appLog.info("Encountered an exception checking for data: {}", ex.getMessage());
			ex.printStackTrace();
			result = false;
		} finally {
		  pool.returnToPool(c);
		}
		
		appLog.info("Data exists: {}", result);
		
		return result;
	}
	
	/**
	 * 
	 * @param type
	 * @param uuid
	 * @param jsonObject
	 * @param auth
	 * @return
	 */
	private Document generateDocument(String type, String uuid, String jsonObject, String auth) {
      Document document = new Document();
      document.set_id(uuid);
      document.set_type(type);
      document.set_jsonObject(jsonObject);
      
      document.setVisibility(new Visibility().setFormalVisibility(auth));
      
      return document;
	}
	
	/**
	 * Helper method to generate the Boolean Query that represents the predicates OR'ed then AND'ed together.
	 * (inner list is OR'ed; outer is AND'ed)
	 * 
	 * @param predicates - the predicates to perform the search
	 * @return BooleanQuery
	 * @throws DataAccessException
	 */
	private BooleanQuery generateQueryFromPredicates(List<List<BinaryPredicate>> predicates) throws DataAccessException {
		Iterator<List<BinaryPredicate>> i1 = predicates.iterator();
		
		// create the query to AND the term lists into
		BooleanQuery andQuery = new BooleanQuery();
		
		while (i1.hasNext()) {
			List<BinaryPredicate> l = i1.next();
			Iterator<BinaryPredicate> i2 = l.iterator();

			// TODO: this is using the Lucene way to create the query; can we do it using ES API???
			BooleanQuery orQuery = new BooleanQuery();
			
			// create our query to OR the terms into
			while (i2.hasNext()) {
				BinaryPredicate predicate = i2.next();
				// get the comparison operator
				switch (predicate.getOperator()) {
				
				case EQ: // equals
					// TODO: what happens if someone sends down 'foo' EQ 1? we
					// shouldn't presume that they are always string values...
					BooleanQuery equalQuery = new BooleanQuery();
					equalQuery.add(new TermQuery(new Term(predicate.getColumnName()
							, predicate.getValue().getStringValue())),
							BooleanClause.Occur.MUST);
					orQuery.add(equalQuery, null);
					break;
				case NE: // not equal
					BooleanQuery notEqualQuery = new BooleanQuery();
					notEqualQuery.add(new TermQuery(new Term(predicate.getColumnName()
							, predicate.getValue().getStringValue())),
							BooleanClause.Occur.MUST_NOT);
					orQuery.add(notEqualQuery, null);
					break;
				case LT: // less than
					BooleanQuery lessThanQuery = new BooleanQuery();
					lessThanQuery.add(NumericRangeQuery.newLongRange(predicate
							.getColumnName(), Long.MIN_VALUE, predicate
							.getValue().getLongValue(), true, false),
							BooleanClause.Occur.MUST);
					orQuery.add(lessThanQuery, null);
					break;
				case LE: // less than or equals
					BooleanQuery lessThanOrEqualQuery = new BooleanQuery();
					lessThanOrEqualQuery.add(NumericRangeQuery.newLongRange(predicate
							.getColumnName(), Long.MIN_VALUE, predicate
							.getValue().getLongValue(), true, true),
							BooleanClause.Occur.MUST);
					orQuery.add(lessThanOrEqualQuery, null);
					break;
				case GE: // greater than or equals
					BooleanQuery greaterThanOrEqualsQuery = new BooleanQuery();
					greaterThanOrEqualsQuery.add(NumericRangeQuery.newLongRange(predicate
							.getColumnName(), predicate.getValue().getLongValue(),
							Long.MAX_VALUE, true, true),
							BooleanClause.Occur.MUST);
					orQuery.add(greaterThanOrEqualsQuery, null);
					break;
				case GT: // greater than
					BooleanQuery greaterThanQuery = new BooleanQuery();
					greaterThanQuery.add(NumericRangeQuery.newLongRange(predicate
							.getColumnName(), predicate.getValue().getLongValue(),
							Long.MAX_VALUE, false, true),
							BooleanClause.Occur.MUST);
					orQuery.add(greaterThanQuery, null);
					break;
				default:
					String message = "This is not a valid predicate comparison operator: "
							+ predicate.getOperator().name();
					throw new DataAccessException(message);
				}
			}
			
			andQuery.add(orQuery, BooleanClause.Occur.MUST);
		}
		
		return andQuery;
	}
	
	/**
	 * 
	 * @return
	 * @throws TException
	 */
	private Client getClient() throws TException {
		return pool.getClient("ssrIndexing", "ezelastic", EzElastic.Client.class);
	}
	
	/**
	 * Helper method to prepopulate some person data into Document Dataset
	 * 
	 * @throws IOException
	 */
	private void insertData() throws IOException {
		Client c = null;
		List<Document> documents = new ArrayList<Document>();
		
		for (int i = 0; i < 10; i++) {
			Person p = new Person();
			StandardObject base = new StandardObject();
			base.setUuid(UUID.randomUUID().toString());
			SecurityLabel sl = new SecurityLabel();
			Classification classification = new Classification();
			classification.setCAPCOString("U");
			sl.setClassification(classification);
			base.setSecurity(sl);
			
			p.setFirstName("firstname" + i);
			p.setLastName("lastname" + i);
			
			if ((i % 3) == 0) {
				p.setCountry("australia");
			} else {
				p.setCountry("jamaica");
			}
			
			p.setBase(base);
			
			String json = personToJson(p);
			documents.add(generateDocument(APP_TYPE, p.getBase().getUuid(), json, "U"));
		}
		
		try {
			c = getClient();	
			List<IndexResponse> responses = c.bulkPut(documents, fakeToken);
			appLog.info("Inserted documents into the Document DataStore");
			for (IndexResponse ir : responses) {
				appLog.info("Response id is {}", ir.get_id());
			}
		} catch (Exception tex) {
			tex.printStackTrace();
		} finally {
			pool.returnToPool(c);
		}
	}
	
	/**
	 * Given a Thrift person object, turn it into a JSON string to insert into 
     * the Document Dataset
	 * 
	 * @param p - A Thrift person object
	 * @return JSON string
	 */
	private String personToJson(Person p) {
		try {
			XContentBuilder builder = jsonBuilder().startObject()
					.field("UUID", p.getBase().getUuid()) 
					.field("firstname", p.getFirstName())
					.field("lastname", p.getLastName())
					.field("country", p.getCountry())
					.endObject();
			
			String result = builder.string();
			appLog.info("personToJson result is {}", result);
			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
