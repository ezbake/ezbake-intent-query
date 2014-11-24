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

import java.util.List;
import java.util.Set;

/**********************
	<column>
		<name>user_id</name>
		<primitiveType>int</primitiveType>
		<len></len>
		<precision></precision>
		<scale></scale>
		<ops>LT,LE,EQ,NEQ,GE,GT</ops>
	</column>
**********************/
/*******************************************************************************
 * 
 * @author rding
 *
 * Class to hold a table definition
 * The definition is read from configuration file (impalaExternalDsConfig.xml)
 * in the Processor(MongoExternalDataSourceHandler) Constructor.
 ******************************************************************************/
public class TableMetadata {
	private	String			table_name;
	private int				num_columns;
	private String			init_string;
	List<TableColumnDesc> 	columns;
	
	
	public TableMetadata(String	name, int num_col, String init_str, List<TableColumnDesc> cols) {
		this.table_name = name;
		this.num_columns = num_col;
		this.init_string = init_str;
		this.columns = cols;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder("TableMetadata(");
	    
	    sb.append("name = ");
	    sb.append(table_name);

	    sb.append("; init_string = ");
	    sb.append(init_string);
	    
	    sb.append("; num_columns = ");
	    sb.append(num_columns);
	    
	    sb.append("; List<TableColumnDesc> columns = [ ");
	    for (int i = 0; i < columns.size(); i++) {
	    	sb.append("Column " + i + ": ");
	    	sb.append(columns.get(i).toString());
	    	sb.append("  ");
	    }
	    
	    sb.append(" ])");
	    return sb.toString();
	}
	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	
	public int getNum_columns() {
		return num_columns;
	}
	public void setNum_columns(int num_columns) {
		this.num_columns = num_columns;
	}

	
	public String getInit_string() {
		return init_string;
	}
	public void setInit_string(String init_string) {
		this.init_string = init_string;
	}

	
	public List<TableColumnDesc> getColumns() {
		return columns;
	}
	public void setColumns(List<TableColumnDesc> columns) {
		this.columns = columns;
	}
}
