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

import java.util.List;

import ezbake.base.thrift.EzSecurityToken;
import ezbake.configuration.EzConfiguration;
import ezbake.intents.IntentType;
import ezbake.query.basequeryableprocedure.GetPageResult;
import ezbake.query.basequeryableprocedure.PrepareStats;
import ezbake.query.intents.BinaryPredicate;

public interface DataAccess {
	boolean ping();

	boolean isInitialized();

	boolean init(EzConfiguration configuration) throws Exception;

	GetPageResult getPage(IntentType intenttype, int offset, int pagesize,
			List<String> columnnames, List<List<BinaryPredicate>> predicates,
			EzSecurityToken securityToken) throws DataAccessException;

	PrepareStats getPrepareStats(String tableName, String initString,
			List<List<BinaryPredicate>> predicates,
			EzSecurityToken securityToken) throws DataAccessException;

	void close();
}
