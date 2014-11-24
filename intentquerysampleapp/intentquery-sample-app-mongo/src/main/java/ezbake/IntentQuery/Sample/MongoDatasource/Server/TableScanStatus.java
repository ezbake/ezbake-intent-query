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

package ezbake.IntentQuery.Sample.MongoDatasource.Server;

import com.cloudera.impala.extdatasource.thrift.TTableSchema;
import com.mongodb.DBCursor;

public class TableScanStatus {
	private DBCursor		cursor;
	private int				batchsize;
	private TTableSchema	schema;
	private int				offset;

	public TableScanStatus(DBCursor cursor, int batchsize, TTableSchema schema) {
		this.cursor = cursor;
		this.batchsize = batchsize;
		this.schema = schema;
		this.offset = 0;
	}
	
	public DBCursor getCursor() {
		return cursor;
	}
	public void setCursor(DBCursor cursor) {
		this.cursor = cursor;
	}
	public int getBatchsize() {
		return batchsize;
	}
	public void setBatchsize(int batchsize) {
		this.batchsize = batchsize;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public TTableSchema getSchema() {
		return schema;
	}
	public void setSchema(TTableSchema schema) {
		this.schema = schema;
	}
}
