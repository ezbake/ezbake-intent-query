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

import java.util.Set;

import org.apache.thrift.TException;

import ezbake.configuration.EzConfiguration;
import ezbake.ins.thrift.gen.AppService;
import ezbake.ins.thrift.gen.Application;
import ezbake.ins.thrift.gen.ApplicationNotFoundException;
import ezbake.ins.thrift.gen.InternalNameService;

public class InsService {

	private InsClient inscli = null;
	
	public InsService(final EzConfiguration configuration) {
		inscli = new InsClient(configuration);
	}
	
	public String getAppName(String intentname) {
		String appName = null;
		InternalNameService.Client cli = inscli.getThriftClient();
		try {
			Set<AppService> apps = cli.appsThatSupportIntent(intentname);
			
			for (AppService appservice : apps) {
				appName = appservice.getApplicationName();
			}
			
		} catch (ApplicationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			inscli.returnToPool(cli);
		}
		
		return appName;
	}

}
