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

import java.util.ArrayList;
import java.util.List;

import org.apache.hive.service.cli.thrift.TCloseOperationReq;
import org.apache.hive.service.cli.thrift.TCloseSessionReq;
import org.apache.hive.service.cli.thrift.TColumnValue;
import org.apache.hive.service.cli.thrift.TExecuteStatementReq;
import org.apache.hive.service.cli.thrift.TExecuteStatementResp;
import org.apache.hive.service.cli.thrift.TFetchResultsReq;
import org.apache.hive.service.cli.thrift.TFetchResultsResp;
import org.apache.hive.service.cli.thrift.TOpenSessionReq;
import org.apache.hive.service.cli.thrift.TOpenSessionResp;
import org.apache.hive.service.cli.thrift.TOperationHandle;
import org.apache.hive.service.cli.thrift.TProtocolVersion;
import org.apache.hive.service.cli.thrift.TRow;
import org.apache.hive.service.cli.thrift.TRowSet;
import org.apache.hive.service.cli.thrift.TSessionHandle;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.cloudera.impala.thrift.ImpalaHiveServer2Service;
import com.cloudera.impala.thrift.ImpalaService;

public class IntentImpalaClient {
	
	private static String host="localhost";
    private static int port=21050;
    

    ImpalaService.Client client;
	
	public IntentImpalaClient() {
    }
	
	public List<TRow> queryImpala(String qryString) {
		
		List<TRow> resultRows = new ArrayList<TRow>();
		
		try {
			TSocket transport = new TSocket(host,port);
	        
	        transport.setTimeout(60000);
	        TBinaryProtocol protocol = new TBinaryProtocol(transport);
	        ImpalaHiveServer2Service.Client client = new ImpalaHiveServer2Service.Client(protocol);  
	        
	        transport.open();
	        
	        TOpenSessionReq openReq = new TOpenSessionReq();
	        openReq.setClient_protocol(TProtocolVersion.HIVE_CLI_SERVICE_PROTOCOL_V1);
	        //openReq.setUsername(username);
	        //openReq.setPassword(password);

	        TOpenSessionResp openResp = client.OpenSession(openReq);
	        org.apache.hive.service.cli.thrift.TStatus status = openResp.getStatus();
	        if (status.getStatusCode() == org.apache.hive.service.cli.thrift.TStatusCode.ERROR_STATUS) {
	            String msg = status.getErrorMessage();
	            System.out.println(msg);
	            closeConnection(transport,client,null,null);
	            return null;
	        }
	        if(status.getStatusCode() != org.apache.hive.service.cli.thrift.TStatusCode.SUCCESS_STATUS) {
	            System.out.println("No success");
	            closeConnection(transport,client,null,null);
	            return null;
	        }
	        TSessionHandle sessHandle = openResp.getSessionHandle();
	        
	        TExecuteStatementReq execReq = new TExecuteStatementReq(sessHandle, qryString);
	        TExecuteStatementResp execResp = client.ExecuteStatement(execReq);
	        status = execResp.getStatus();
	        if (status.getStatusCode() == org.apache.hive.service.cli.thrift.TStatusCode.ERROR_STATUS) {
	            String msg = status.getErrorMessage();
	            System.out.println(msg + "," + status.getSqlState() + "," + Integer.toString(status.getErrorCode()) + "," + status.isSetInfoMessages());
	            System.out.println("After ExecuteStatement: " + qryString);
	            closeConnection(transport,client,null,sessHandle);
	            return null;
	        }

	        TOperationHandle stmtHandle = execResp.getOperationHandle();

	        if (stmtHandle == null) {
	            System.out.println("Empty operation handle");
	            closeConnection(transport,client,stmtHandle,sessHandle);
	            return null;
	        }

	        TFetchResultsReq fetchReq = new TFetchResultsReq();
	        fetchReq.setOperationHandle(stmtHandle);
	        fetchReq.setMaxRows(100);
	        //org.apache.hive.service.cli.thrift.TFetchOrientation.FETCH_NEXT
	        TFetchResultsResp resultsResp = client.FetchResults(fetchReq);

	        status = resultsResp.getStatus();
	        if (status.getStatusCode() == org.apache.hive.service.cli.thrift.TStatusCode.ERROR_STATUS) {
	            String msg = status.getErrorMessage();
	            System.out.println(msg + "," + status.getSqlState() + "," + Integer.toString(status.getErrorCode()) + "," + status.isSetInfoMessages());
	            System.out.println("After FetchResults: " + qryString);
	            closeConnection(transport,client,stmtHandle,sessHandle);
	            return null;
	        }

	        TRowSet resultsSet = resultsResp.getResults();
	        resultRows = resultsSet.getRows();
	        System.out.println(String.format("Total rows returned = %d", resultRows.size()) );
	        
	        /*int rowIndex = 0;
	        for (TRow resultRow : resultRows){
	        	List<TColumnValue> 	colValues = resultRow.getColVals();
	        	for (TColumnValue colValue : colValues) {
	                //System.out.println(colValue.toString());
	        	}
	        	
	        	System.out.println(String.format("Row %d has %d columns.", rowIndex++, resultRow.getColValsSize()));

	            System.out.println(resultRow.toString());
	        }*/
	        
	        closeConnection(transport,client,stmtHandle,sessHandle);
		} catch(Exception e) {
            e.printStackTrace();
        }
		return resultRows;
		
	}
	
	public void closeConnection( TSocket transport, ImpalaHiveServer2Service.Client client, TOperationHandle stmtHandle, TSessionHandle sessHandle ) {
		try {
			if (stmtHandle != null && sessHandle != null && client != null) {
				TCloseOperationReq closeReq = new TCloseOperationReq();
		        closeReq.setOperationHandle(stmtHandle);
		        client.CloseOperation(closeReq);
		        TCloseSessionReq closeConnectionReq = new TCloseSessionReq(sessHandle);
		        client.CloseSession(closeConnectionReq);
			}
			
	        transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
