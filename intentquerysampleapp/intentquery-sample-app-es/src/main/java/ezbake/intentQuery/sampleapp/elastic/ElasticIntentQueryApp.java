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

import ezbake.base.thrift.EzBakeBaseThriftService;
import ezbake.base.thrift.EzSecurityToken;
import ezbake.base.thrift.SSR;
import ezbake.configuration.EzConfiguration;
import ezbake.ezconfiguration.helpers.ApplicationConfiguration;
import ezbake.intents.IntentType;
import ezbake.query.basequeryableprocedure.BaseQueryableProcedure;
import ezbake.query.basequeryableprocedure.BaseQueryableProcedureException;
import ezbake.query.basequeryableprocedure.ColumnData;
import ezbake.query.basequeryableprocedure.GetPageResult;
import ezbake.query.basequeryableprocedure.Page;
import ezbake.query.basequeryableprocedure.PrepareStats;
import ezbake.query.basequeryableprocedure.RowBatch;
import ezbake.query.intents.BinaryPredicate;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class ElasticIntentQueryApp extends EzBakeBaseThriftService implements BaseQueryableProcedure.Iface {
	
	
	private Logger appLog = LoggerFactory.getLogger(ElasticIntentQueryApp.class);
	private Logger secLog = LoggerFactory.getLogger("SECURITY." + ElasticIntentQueryApp.class);
	
	private DataAccess dao = null;
	
	private String applicationName;
	
	public ElasticIntentQueryApp() throws Exception {
		appLog.info("ElasticIntentQueryApp instantiated");
	}
	
	private void initApp() throws Exception {
		final EzConfiguration configuration = new EzConfiguration();
		applicationName = configuration.getProperties().getProperty(ApplicationConfiguration.APPLICATION_NAME_KEY);
		appLog.info("Application name is {}", applicationName);
		
		if (dao == null) {
			dao = new DataAccessImpl();
		
			try {
				dao.init(configuration);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	@Override
	public void shutdown() {
		
		appLog.info("Shutting down the DAO");
		if (dao != null) {
			dao.close();
			dao = null;
		}
		
		super.shutdown();
	}
	
	//
	// EzBake Required Methods
	//

	@Override
	public boolean ping() {
		return dao.ping();
	}

	@Override
	public TProcessor getThriftProcessor() {
		try {
			if (appLog.isDebugEnabled()) {
				appLog.debug("Returning a Thrift Processor");
			}
			initApp();
			return new BaseQueryableProcedure.Processor(this);
		} catch (Exception e) {
			if (appLog.isDebugEnabled()) {
				appLog.debug("Encountered an exception");
				e.printStackTrace();
			}
			return null;
		}
	}
	
	@Override
	public PrepareStats prepare(String table_name, String init_string,
			List<List<BinaryPredicate>> predicates, EzSecurityToken securityToken)
			throws BaseQueryableProcedureException, TException {
		appLog.info("prepare called");
		
		PrepareStats stats = null;
		
		try {
			stats = dao.getPrepareStats(table_name, init_string, predicates, securityToken);
		} catch (DataAccessException e) {
			// TODO: would exception'ing out be better? right now just return
			// an empty stats object
			e.printStackTrace();
			throw new BaseQueryableProcedureException(e.getMessage());
		}
		
		appLog.info("Finished with the prepare call: {}", stats.toString());
		
		return stats;
	}
	
	// BaseProcedure.Iface implementations
	@Override
	public SSR getStandardSearchResultForURI(String arg0, EzSecurityToken arg1)
			throws TException {
		// Temp stubs
		return null;
	}

	@Override
	public SSR getStandardSearchResultForURIs(List<String> arg0,
			EzSecurityToken arg1) throws TException {
		// Temp stubs
		return null;
	}
	
	@Override
	public GetPageResult getPage(IntentType intenttype, Page page, List<String> columnNames,
			List<List<BinaryPredicate>> predicates, EzSecurityToken securityToken)
			throws BaseQueryableProcedureException, TException {
		appLog.info("getPage called");
		appLog.info("page: {}", page.toString());
		appLog.info("columnNames: {}", columnNames.toString());
		appLog.info("predicates: {}", predicates.toString());
		appLog.info("securityToken: {}", securityToken);
		
		GetPageResult result = null;
		
		try {
			result = dao.getPage(intenttype, page.getOffset(), page.getPagesize(), columnNames, predicates, securityToken);
		} catch (DataAccessException e) {
			throw new BaseQueryableProcedureException(e.getMessage());
		}

		
		// TODO for testing/debugging
		appLog.info("Result is");
		RowBatch rb = result.getRows();
		List<ColumnData> cols = rb.getCols();
		for (ColumnData col : cols) {
			appLog.info(col.toString());
		}
		appLog.info("Finished iterating through rowbatch");
		
		return result;
	}
}
