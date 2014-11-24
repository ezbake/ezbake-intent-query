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
* Class to hold a column definition
* The definition is read from configuration file (impalaExternalDsConfig.xml)
* in the Processor(MongoExternalDataSourceHandler) Constructor.
* Each field is map to a field defined in XML.  See example above.
******************************************************************************/
public class TableColumnDesc {
	private String	col_name;
	private String	primitive_type;
	private int		len;		// used only if primitive_type == CHAR
	private int		precision;	// used only if primitive_type == DECIMAL
	private int		scale;		// used only if primitive_type == DECIMAL
	Set<String>		ops;

	public TableColumnDesc() {}
	
	public TableColumnDesc(String name, String col_type, int col_len,
			int col_precision, int col_scale, Set<String> col_ops) {
		
		this.col_name =name;
		this.primitive_type = col_type;
		this.len = col_len;
		this.precision = col_precision;
		this.scale = col_scale;
		this.ops = col_ops;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder("TableColumnDesc(");
	    
	    sb.append("name = ");
	    sb.append(col_name);

	    sb.append("; type = ");
	    sb.append(primitive_type);
	    
	    if (primitive_type.equals("CHAR")) {
	    	sb.append("; CHAR len = ");
		    sb.append(len);
		} 
		else if (primitive_type.equals("DECIMAL")) {
			sb.append("; DECIMAL precision = ");
		    sb.append(precision);
		    sb.append("; DECIMAL scale = ");
		    sb.append(scale);
		}
	    
	    sb.append("; ops = ");
	    sb.append(ops);
	    /*
	    Object[]	opsArray = ops.toArray();
	    for (int i = 0; i < opsArray.length; i++) {
	    	sb.append((String)opsArray[i]);
	    	if (i < opsArray.length - 1) {
	    		sb.append(",");
	    	}
	    }
	    */
	    
	    sb.append(")");
	    return sb.toString();
	}
	
	public String getCol_name() {
		return col_name;
	}

	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

	public String getPrimitive_type() {
		return primitive_type;
	}

	public void setPrimitive_type(String primitive_type) {
		this.primitive_type = primitive_type;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public Set<String> getOps() {
		return ops;
	}

	public void setOps(Set<String> ops) {
		this.ops = ops;
	}
}
