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

package ezbake.query.basequeryableprocedure.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;
import ezbake.thrift.ThriftClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.impala.extdatasource.thrift.TBinaryPredicate;
import com.cloudera.impala.extdatasource.thrift.TColumnDesc;
import com.cloudera.impala.extdatasource.thrift.TTableSchema;
import com.cloudera.impala.thrift.TColumnValue;

import ezbake.base.thrift.EzSecurityToken;
import ezbake.configuration.EzConfiguration;
import ezbake.configuration.constants.EzBakePropertyConstants;
import ezbake.ins.thrift.gen.AppService;
import ezbake.ins.thrift.gen.InternalNameService;
import ezbake.intents.IntentType;
import ezbake.intents.common.ImpalaAppToken;
import ezbake.intents.common.RedisUtils;
import ezbake.query.basequeryableprocedure.BaseQueryableProcedure;
import ezbake.query.basequeryableprocedure.BaseQueryableProcedureException;
import ezbake.query.basequeryableprocedure.GetPageResult;
import ezbake.query.basequeryableprocedure.Page;
import ezbake.query.basequeryableprocedure.PrepareStats;
import ezbake.query.intents.BinaryOperator;
import ezbake.query.intents.BinaryPredicate;
import ezbake.query.intents.ColumnValue;

public class EzbakeAppHandler {
	private static Logger appLog = LoggerFactory.getLogger(EzbakeAppHandler.class);
	
	// Cached EZConfiguration object
	private EzConfiguration configuration = null;
	
	private ThriftClientPool pool;
	
	private RedisUtils redisUtils;
	
	/**
	 * 
	 * @param config
	 */
	public EzbakeAppHandler(EzConfiguration config) {
		configuration = config;
		
		appLog.info("EzbakeAppHandler has started initializing");
		appLog.info("config: " + config.getProperties().toString());
		
		if (pool == null) {
			pool = new ThriftClientPool(config.getProperties());
		}
		
		appLog.info("EzbakeAppHandler created client pool, initializing redis utils");
		redisUtils = new RedisUtils(config);
		appLog.info("EzbakeAppHandler created client pool, finished initializing redis utils");
		appLog.info("EzbakeAppHandler has finished initializing");
	}
	
	/**
	 * 
	 * @param table_name
	 * @param init_string
	 * @param predicates
	 * @param securityToken
	 * @return
	 * @throws EzbakeAppHandlerException
	 */
	public PrepareStats prepare(String table_name, String init_string, List<List<TBinaryPredicate>> predicates, EzSecurityToken securityToken) throws EzbakeAppHandlerException {
		appLog.info("prepare called with tablename({}) init_string({}) predicates({}), securityToken({})", table_name, init_string, predicates, securityToken);

		Set<AppService> apps = getAppsForIntent(table_name);
		BaseQueryableProcedure.Client c = getClient(apps.iterator().next());
		PrepareStats stats = null;
		
		if (c == null) {
			String msg = "ERROR: Failed to retrieve Client";
			throw new EzbakeAppHandlerException(msg);
		}
		
		try {
			stats = c.prepare(table_name, init_string, convertPredicates(predicates), securityToken);
		} catch (Exception e) {
			throw new EzbakeAppHandlerException(e);
		} finally {
			if (c != null) {
				pool.returnToPool(c);
				c = null;
			}
		}
		
		return stats;
	}
	
	/**
	 * 
	 * @param scanhandle
	 * @return
	 */
	public GetPageResult getNext(String scanhandle) {
		BaseQueryableProcedure.Client c = null;
		ImpalaAppToken token = redisUtils.getImpalaAppToken(scanhandle);
		GetPageResult pageResult = null;
		
		appLog.info("calling getNext with the following scanhandle: {}", scanhandle);
		appLog.info("token is : {}", token.toString());
		appLog.info("security token: {}", token.getUserToken());
		
		try {
			// TODO: we could retrieve all of the apps that support our intent and
			//       "load balance" the request across all of the instances of the app. 
			//       For now, we just use the first one
			Set<AppService> apps = getAppsForIntent(token.getTableName());
			c = getClient(apps.iterator().next());
			Page page = new Page();
			page.setOffset(token.getOffset());
			page.setPagesize(token.getBatchsize());
			
			IntentType intent = IntentType.valueOf(token.getIntent().toUpperCase());
			appLog.info("Intent is : {}", intent.toString());
			
			List<List<TBinaryPredicate>> predicates = removeSecUuidPredicate(token.getPredicates());
			List<List<BinaryPredicate>> converted = convertPredicates(predicates);
			
			appLog.info("Predicates with the secuuid: {}", token.getPredicates());
			appLog.info("Predicates : {}", predicates);
			appLog.info("Converted to : {}", converted);
			
			List<String> columnnames = new ArrayList<String>();
			TTableSchema schema = token.getTableSchema();
			List<TColumnDesc> list =  schema.getCols();
			
			for (TColumnDesc columnDesc : list) {
				columnnames.add(columnDesc.getName());
			}
			
			pageResult = c.getPage(intent, page, columnnames, converted, token.getUserToken());
			
		} catch (BaseQueryableProcedureException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				pool.returnToPool(c);
				c = null;
			}
		}
		
		return pageResult;
	}
	
	/**
	 * 
	 * @param predicates
	 * @return
	 */
	private List<List<TBinaryPredicate>> removeSecUuidPredicate(List<List<TBinaryPredicate>> predicates) {
		List<List<TBinaryPredicate>> list = new ArrayList<List<TBinaryPredicate>>();
		boolean hasSecUuid = false;
		
		Iterator<List<TBinaryPredicate>> iter = predicates.iterator();
		while (iter.hasNext()) {
			List<TBinaryPredicate> predicateList = iter.next();
			Iterator<TBinaryPredicate> predicateIter = predicateList.iterator();
			
			while (predicateIter.hasNext()) {
				TBinaryPredicate binaryPredicate = predicateIter.next();	
				if ("secUuid".equalsIgnoreCase(binaryPredicate.getCol().getName())) {
					hasSecUuid = true;
					break;
				}
			}
			
			if (!hasSecUuid) {
				list.add(predicateList);
			}
			
			hasSecUuid = false;
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param tablename
	 * @return
	 */
	private Set<AppService> getAppsForIntent(String tablename) {
		Set<AppService> apps = new HashSet<AppService>();
		
		InternalNameService.Client client = null;
		String intentName = tablename.substring(tablename.lastIndexOf("_") + 1);
		appLog.info("Using intent({}) to retrieve INS client", intentName);
		
		try {
			client = getInsClient();
			apps = client.appsThatSupportIntent(intentName.toUpperCase());
			appLog.info("Apps : {}", apps.toString());
 
		} catch (Exception ex) {
			// TODO: exception out or return an empty set?????
			ex.printStackTrace();
			apps = new HashSet<AppService>();
		} finally {
			if (client != null) {
				pool.returnToPool(client);
			}
		}

		return apps;
	}
	
	/**
	 * 
	 * @param table_name
	 * @return
	 */
	private BaseQueryableProcedure.Client getClient(AppService appService) {
		BaseQueryableProcedure.Client c = null;
		
		try {
			c = pool.getClient(appService.getApplicationName(), 
					appService.getServiceName(), BaseQueryableProcedure.Client.class);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
	/**
	 * 
	 * @return
	 */
	private InternalNameService.Client getInsClient() {
		InternalNameService.Client c = null;
		
		try {
			c = pool.getClient(EzBakePropertyConstants.INTERNAL_NAME_SERVICE, InternalNameService.Client.class);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
	/**
	 * 
	 * @param predicates
	 * @return
	 */
	private List<List<BinaryPredicate>> convertPredicates(List<List<TBinaryPredicate>> predicates) {
		List<List<BinaryPredicate>> converted = new ArrayList<List<BinaryPredicate>>();
		
		for (Iterator<List<TBinaryPredicate>> itr = predicates.iterator(); itr.hasNext();) {
			List<TBinaryPredicate> innerList = itr.next();
			List<BinaryPredicate> newInner = new ArrayList<BinaryPredicate>();
			for (Iterator<TBinaryPredicate> predicateItr = innerList.iterator(); predicateItr.hasNext();) {
				newInner.add(convertPredicate(predicateItr.next()));
			}
			
			converted.add(newInner);
		}
		
		return converted;
	}
	
	/**
	 * 
	 * @param oldPredicate
	 * @return
	 */
	private BinaryPredicate convertPredicate(TBinaryPredicate oldPredicate) {
		BinaryPredicate newPredicate = new BinaryPredicate();
		
		newPredicate.setColumnName(oldPredicate.getCol().getName());
		
		switch(oldPredicate.getOp()) {
			case EQ:
				newPredicate.setOperator(BinaryOperator.EQ);
				break;
			case NE:
				newPredicate.setOperator(BinaryOperator.NE);
				break;
			case GE:
				newPredicate.setOperator(BinaryOperator.GE);
				break;
			case GT:
				newPredicate.setOperator(BinaryOperator.GT);
				break;
			case LE:
				newPredicate.setOperator(BinaryOperator.LE);
				break;
			case LT:
				newPredicate.setOperator(BinaryOperator.LT);
				break;
			default:
				break;
		}
		
		ColumnValue value = new ColumnValue();
		TColumnValue oldValue = oldPredicate.getValue();
		
		if (oldValue.isSetString_val()) {
			value.setStringValue(oldPredicate.getValue().getString_val());
		} else if (oldValue.isSetInt_val()) {
			value.setIntegerValue(oldPredicate.getValue().getInt_val());
		} else if (oldValue.isSetBool_val()) {
			value.setBoolValue(oldPredicate.getValue().isBool_val());
		} else if (oldValue.isSetBigint_val()) {
			value.setLongValue(oldPredicate.getValue().getBigint_val());
		} else if (oldValue.isSetSmallint_val()) {
			// TODO: do we need a corresponding 'short' method???
			value.setIntegerValue(oldPredicate.getValue().getSmallint_val());
		} else if (oldValue.isSetDouble_val()) {
			value.setDoubleValue(oldPredicate.getValue().getDouble_val());
		} else {
			// Missing one????
		}
		
		newPredicate.setValue(value);
		
		return newPredicate;
	}
}
