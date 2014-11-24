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

package ezbake.intents.common;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.impala.extdatasource.thrift.TBinaryPredicate;
import com.cloudera.impala.extdatasource.thrift.TBinaryPredicateList;
import com.cloudera.impala.extdatasource.thrift.TColumnDesc;
import com.cloudera.impala.extdatasource.thrift.TTableSchema;

import redis.clients.jedis.Jedis;
import ezbake.base.thrift.EzSecurityToken;
import ezbake.configuration.EzConfiguration;
import ezbake.configuration.constants.EzBakePropertyConstants;


public class RedisUtils {
	private static Logger appLog = LoggerFactory.getLogger(RedisUtils.class);
	
	private Jedis jedis = null;
	
	private TDeserializer deserializer;

        /**
         * Constructor
         *
         * @param configuration - EZConfiguration object which has Redis configuration
         */	
	public RedisUtils(EzConfiguration configuration) {
		String redisHost = configuration.getProperties().getProperty(EzBakePropertyConstants.REDIS_HOST);
		int redisPort = Integer.parseInt(configuration.getProperties().getProperty(EzBakePropertyConstants.REDIS_PORT));
		appLog.info("Redis Host ({})  Redis Port({})", redisHost, redisPort);
		
		jedis = new Jedis(redisHost, redisPort);	
		deserializer = new TDeserializer();
	}

        /**
         * put
         *
         * Store into Redis the value byte array using the key byte array
         *
         * @param key - byte array representing key
         * @param value - byte array representing value
         */	
	public void put(byte[] key, byte[] value) throws Exception {
		jedis.set(key, value);
	}

        /**
         * put
         *
         * Store into Redis the String value using the String key 
         *
         * @param key - String representing key
         * @param value - String representing value
         */	
	public void put(String key, String value) throws Exception {
		put(key.getBytes(), value.getBytes());
	}

        /**
         * get
         *
         * Retrieve from Redis the value associated with the byte array representing the key
         *
         * @param key - byte array representing key
         * @return byte array representing value, null if key doesn't exist
         */	
	public byte[] get(byte[] key) throws Exception {
		byte[] result = jedis.get(key);
		
		return result;
	}

        /**
         * get
         *
         * Retrieve from Redis the String value associated with the String key
         *
         * @param key - String representing key
         * @return String representing value, null if key doesn't exist
         */	
	public String get(String key) throws Exception {
                String resultString = null;
                byte[] result = get(key.getBytes());

                if (result != null) {
                        resultString = new String(result);
                } 

                return resultString;
	}

        /**
         * retrieveSecurityToken
         *
         * Retrieve the EzSecurityToken from Redis stored under the secUuid key
         *
         * @param secUuid - String key value EzSecurityToken is stored under
         * @return EzSecurityToken or null if not found
         */	
	public EzSecurityToken retrieveSecurityToken(String secUuid) {
		EzSecurityToken securityToken = null;
		
		byte[] result = jedis.get(secUuid.getBytes());
		
		if (result != null) {
			securityToken = new EzSecurityToken();
			try {
				deserializer.deserialize(securityToken,result);
				appLog.info("EzSecurityToken: {}", securityToken.toString());
			} catch (TException e) {
				e.printStackTrace();
			}
		}
				
		return securityToken;
	}
	
	public boolean deleteSecurityToken(String secUuid) {
		boolean result = true;
		
		if (jedis.exists(secUuid)) {
			long count = jedis.del(secUuid);
			if (count < 0) {
				result = false;
			}
		}
		
		return result;
	}

        /**
         * checkTableSchemaForSecUuid
         *
         * check the TTableSchema object to see if the secUUID column is present
         *
         * @param tableschema - TTableSchema object sent by Impala
         * @return True if present, else false
         */	
	private boolean checkTableSchemaForSecUuid(TTableSchema tableschema) {
		boolean result = false;
		
		for (TColumnDesc columnDesc : tableschema.getCols()) {	
			if ("secUUID".equalsIgnoreCase(columnDesc.getName())) {
				result = true;
				break;
			}
		}
		
		return result;
	}

        /**
         * openImpalaAppToken
         *	
         * Create an entry into Redis to hold pertinent info about an Impala call that an app will need to service
         *
         * @param tablename - The name of the table Impala is querying
         * @param batchsize - Number of results each Impala call is expecting per result set
         * @param tableschema - TTableSchema object representing the requested columns from Impala
         * @param predicates - The predicates to filter search results 
         * @param securityToken -EzSecurityToken for authorization purposes
         * @return ImpalaAppToken
         */	
	public ImpalaAppToken openImpalaAppToken(String tablename, int batchsize, TTableSchema tableschema, List<List<TBinaryPredicate>> predicates, String secUuid) {
		appLog.info("Opening an Impala App Token");
		
		ImpalaAppToken appToken = new ImpalaAppToken();
		String scanHandle = UUID.randomUUID().toString();
		
		// if the secUUID is in the table schema then we need to send a ColumnData object
		// back with NULL values to appease Impala
		boolean secUuidColumnDataInResultSet = checkTableSchemaForSecUuid(tableschema);
		EzSecurityToken securityToken = retrieveSecurityToken(secUuid);
		deleteSecurityToken(secUuid);
		
		appToken.setScanHandle(scanHandle);
		appToken.setTableName(tablename);
		appToken.setTableSchema(tableschema);
		appToken.setBatchsize(batchsize);
		appToken.setSecUuidInResultSet(secUuidColumnDataInResultSet);
		appToken.setPredicates(predicates);
		appToken.setUserToken(securityToken);
		
		jedis.hset(scanHandle, Constants.TABLE_NAME_STR, tablename);
		jedis.hset(scanHandle, Constants.BATCHSIZE_STR, new Integer(batchsize).toString());
		jedis.hset(scanHandle, Constants.CLOSED_STR,  Boolean.FALSE.toString());
		jedis.hset(scanHandle, Constants.SECUUID_IN_RESULT_SET_STRING, Boolean.toString(secUuidColumnDataInResultSet));
		
		try {
			TSerializer serializer = new TSerializer();
			byte[] tableSchemaBytes = serializer.serialize(tableschema);
			jedis.hset(scanHandle.getBytes(), Constants.TABLESCHEMA_STR.getBytes(), tableSchemaBytes);
			
			byte[] securityTokenBytes = serializer.serialize(securityToken);
			jedis.hset(scanHandle.getBytes(), Constants.USER_AUTHS_STR.getBytes(), securityTokenBytes);
			
			TBinaryPredicateList wrapper = new TBinaryPredicateList();
			wrapper.setPredicates(predicates);
			
			byte[] predicateBytes = serializer.serialize(wrapper);
			jedis.hset(scanHandle.getBytes(),  Constants.PREDICATES_STR.getBytes(), predicateBytes);

		} catch (TException ex) {
			ex.printStackTrace();
		} 

		return appToken;
	}

        /**
         * closeImpalaAppToken
         *
         * @param scanhandle - used as the key in Redis where the app token is stored
         * @return true if token was successfuly closed	
         */	
	public boolean closeImpalaAppToken(String scanhandle) {
		appLog.info("Closing the ImpalaAppToken");
		boolean result = true;
		
		if (jedis.exists(scanhandle)) {
			jedis.del(scanhandle.getBytes());
		} else {
			result = false;
		}
		
		return result;
	}

        /**
         * getImpalaAppToken
         *	
         * Retrieve the app token from Redis using the scanhandle key
         *
         * @param scanhandle - key into Redis where token is stored
         * @return ImpalaAppToken
         */	
	public ImpalaAppToken getImpalaAppToken(String scanhandle) {
		appLog.info("getImpalaAppToken using scanhandle: {}", scanhandle);
		ImpalaAppToken appToken = new ImpalaAppToken();
		appToken.setScanHandle(scanhandle);
		
		Map<byte[],byte[]> m = jedis.hgetAll(scanhandle.getBytes());
		
		String tablename = new String(m.get(Constants.TABLE_NAME_STR.getBytes()));
		appToken.setTableName(tablename);
		appToken.setClosed(convertBytesToBoolean(m.get(Constants.CLOSED_STR.getBytes())));
		appToken.setBatchsize(convertBytesToInteger(m.get(Constants.BATCHSIZE_STR.getBytes())));
		//appToken.setOffset(convertBytesToInteger(m.get(Constants.OFFSET_STR.getBytes())));
		appToken.setPredicates(convertBytesToPredicates(m.get(Constants.PREDICATES_STR.getBytes())));
		appToken.setTableSchema(convertBytesToTableSchema(m.get(Constants.TABLESCHEMA_STR.getBytes())));
		appToken.setUserToken(convertBytesToEzSecurityToken(m.get(Constants.USER_AUTHS_STR.getBytes())));
		appToken.setSecUuidInResultSet(convertBytesToBoolean(m.get(Constants.SECUUID_IN_RESULT_SET_STRING.getBytes())));
		
		return appToken;
	}
	
	/**
	 * Helper method to convert byte array into Boolean
	 * @param bytes
	 * @return Boolean
	 */
	private Boolean convertBytesToBoolean(byte[] bytes) {
		return Boolean.parseBoolean(new String(bytes));
	}
	
	/**
	 * Helper method to convert byte array into Integer 
	 * @param bytes
	 * @return Integer
	 */
	private Integer convertBytesToInteger(byte[] bytes) {
		return Integer.parseInt(new String(bytes));
	}
	
	/**
	 * Helper method to convert byte array into List<List<TBinaryPredicate>>
	 * @param bytes
	 * @return List<List<TBinaryPredicate>>
	 */
	private List<List<TBinaryPredicate>> convertBytesToPredicates(byte[] bytes) {
		TBinaryPredicateList afterWrapper = new TBinaryPredicateList();
		List<List<TBinaryPredicate>> result = null;
		
		try {
			deserializer.deserialize(afterWrapper, bytes);
			result = afterWrapper.getPredicates();
		} catch (TException e) {
			e.printStackTrace();
			result = Collections.<List<TBinaryPredicate>>emptyList();
		}
		
		return result;
	}
	
	/**
	 * Helper method to convert byte array into TTableschema
	 * @param bytes
	 * @return TTableSchema
	 */
	private TTableSchema convertBytesToTableSchema(byte[] bytes) {
		TTableSchema tableSchema = new TTableSchema();
		
		try {
			deserializer.deserialize(tableSchema,bytes);
		} catch (TException e) {
			e.printStackTrace();
		}
		
		return tableSchema;
	}
	
	/**
	 * Helper method to convert byte array into EzSecurityToken 
	 * @param bytes
	 * @return EzSecurityToken
	 */
	private  EzSecurityToken convertBytesToEzSecurityToken(byte[] bytes) {
		EzSecurityToken token = new EzSecurityToken();
		
		try {
			deserializer.deserialize(token,bytes);
		} catch (TException e) {
			e.printStackTrace();
		}
		
		return token;
	}
}
