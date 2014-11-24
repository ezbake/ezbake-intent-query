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


import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import com.cloudera.impala.extdatasource.thrift.*;
import com.cloudera.impala.thrift.*;

import ezbake.IntentQuery.Sample.MongoDatasource.Common.ParametersBuilder;


public class MongoExternalDataSourceClient {
	
    public static String   	DBNAME = "testapp";
    public static String   	TABLENAME = "impalaTest_users";
    
    public static String   	SERVER = "localhost"; // 192.168.50.104";

    public static int		SIMPLE_PORT=9003;
	public static int		SECURE_PORT=9091;
    
	//private static Map<String, TOpenState>	scanHandle_to_OpenState_Map = new HashMap<String, TOpenState>();

	public static void main(String [] args) {

		if (args.length != 1) {
			System.out.println("Please enter 'simple' or 'secure'");
		    System.exit(0);
		}

		try {
			TTransport transport;
		    if (args[0].contains("simple")) {
		        transport = new TSocket(SERVER, SIMPLE_PORT);
		        transport.open();
		    }
		    else {
		        /*
		         * Similar to the server, you can use the parameters to setup client parameters or
		         * use the default settings. On the client side, you will need a TrustStore which
		         * contains the trusted certificate along with the public key. 
		         * For this example it's a self-signed cert. 
		         */
		        TSSLTransportParameters params = new TSSLTransportParameters();
		        params.setTrustStore("/home/training/ezBake/thrift-0.9.1/lib/java/test/.truststore", "thrift", "SunX509", "JKS");
		        /*
		         * Get a client transport instead of a server transport. The connection is opened on
		         * invocation of the factory method, no need to specifically call open()
		         */
		        transport = TSSLTransportFactory.getClientSocket("192.168.184.138", 9091, 0, params);
		    }

		    TProtocol protocol = new  TBinaryProtocol(transport);
		    ExternalDataSourceService.Client client = new ExternalDataSourceService.Client(protocol);

		    
		    List<List<TBinaryPredicate>>	test_predicate;

		    ///////////////////////////////////////////////////////////////////
		    //  TEST CASE 1
		    //  select ... from impalaTest_users where employer = "Cloudera"
		    ///////////////////////////////////////////////////////////////////
		    System.out.println("Test case 1: \nselect user_id, last_name, age, gender, emplouer from impalaTest_users");
		    System.out.println("where employer = \"Cloudera\";");

		    test_predicate = TestPredicatesBuilder.ConstructPredicates_01();
		    RunTestCaseUtils.RunTestCase(client, test_predicate);
		    System.out.println("\n");
		    
		    ///////////////////////////////////////////////////////////////////
		    //  TEST CASE 2
		    //  select ... from impalaTest_users where
		    //  (employer = "Cloudera") and (gender = "M")
		    ///////////////////////////////////////////////////////////////////
		    System.out.println("Test case 2: \nselect user_id, last_name, age, gender, emplouer from impalaTest_users");
		    System.out.println("where (employer = \"Cloudera\") and (gender = \"M\");");

		    test_predicate = TestPredicatesBuilder.ConstructPredicates_02();
		    RunTestCaseUtils.RunTestCase(client, test_predicate);
		    System.out.println("\n");

		    ///////////////////////////////////////////////////////////////////
		    //  TEST CASE 3
		    //  select ... from impalaTest_users where 
		    //  (employer2 = "Cloudera") and (employer = "Cloudera") and (age > 40 or gender = "F") 
		    ///////////////////////////////////////////////////////////////////
		    System.out.println("Test case 3: \nselect user_id, last_name, age, gender, emplouer from impalaTest_users");
		    System.out.println("where (employer2 = \"Cloudera\") and (employer = \"Cloudera\") and (age > 40 or gender = \"F\");");

		    test_predicate = TestPredicatesBuilder.ConstructPredicates_03();
		    RunTestCaseUtils.RunTestCase(client, test_predicate);
		    System.out.println("\n");

		    ///////////////////////////////////////////////////////////////////
		    //  TEST CASE 4
		    //  select ... from impalaTest_users where 
			//  (employer = "Cloudera") and (age > 30) and (age < 50) 
		    ///////////////////////////////////////////////////////////////////
		    System.out.println("Test case 4: \nselect user_id, last_name, age, gender, emplouer from impalaTest_users");
		    System.out.println("where (employer = \"Cloudera\") and (age > 30) and (age < 50);");

		    test_predicate = TestPredicatesBuilder.ConstructPredicates_04();
		    RunTestCaseUtils.RunTestCase(client, test_predicate);
		    System.out.println("\n");

		    transport.close();
		} catch (TException x) {
		    x.printStackTrace();
		} 
	}
	
}
