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

package ezbake.intent.query.service;

import java.util.List;

import org.apache.hive.service.cli.thrift.TRow;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ezbake.base.thrift.EzBakeBaseThriftService;
import ezbake.base.thrift.EzSecurityToken;
import ezbake.configuration.EzConfiguration;
import ezbake.intent.query.processor.IntentImpalaClient;
import ezbake.intent.query.processor.QueryGeneratorImpl;
import ezbake.intent.query.thrift.IntentQueryService;
import ezbake.intent.query.utils.Conversions;
import ezbake.intents.common.RedisUtils;
import ezbake.query.intents.Query;

public class QueryService extends EzBakeBaseThriftService implements IntentQueryService.Iface {
	
	private Logger appLog = LoggerFactory.getLogger(QueryService.class);
	
	private QueryGeneratorImpl qrygenerator = null;
	private IntentImpalaClient impalacli = null;
	private RedisUtils redisobj = null;
	
	@Override
	public List<TRow> query(Query qry, EzSecurityToken sectoken) {
		appLog.info("query API called, executing");
		List<TRow> qryresults = null;
		try {
			//get a uuid
			String uuid = Conversions.generateUUID();
			//generate SQL string
			String sqlstr = qrygenerator.generateSQLString(qry,uuid);
			appLog.info("Generated SQL String: " + sqlstr);
			//store token and guuid into redis
			redisobj.put(uuid.getBytes(),new TSerializer().serialize(sectoken));
			//call impala client with the query to hand over query to impala for processing
			qryresults = impalacli.queryImpala(sqlstr);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qryresults;
	}
	
	public TProcessor getThriftProcessor() {
		try {
            init();
            return new IntentQueryService.Processor<QueryService>(this);
        } catch (Exception e) {
            //swallow
        	appLog.info("Error Occured Initializing QueryService");
            return null;
        }
	}

	private void init() throws Exception {
		final EzConfiguration configuration = new EzConfiguration();
        System.out.println("\nEzConfiguration:\n" + configuration);
        redisobj = new RedisUtils(configuration);
		qrygenerator = new QueryGeneratorImpl(configuration);
		impalacli = new IntentImpalaClient();
		
		appLog.info("Successfully Initialized Query Service");
	}
	
}
