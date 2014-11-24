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

import org.codehaus.jackson.annotate.JsonProperty;

public class Person {
	
	@JsonProperty("UUID")
	private String UUID;
	
	@JsonProperty("firstname")
	private String firstname;
	
	@JsonProperty("middlename")
	private String middlename;
	
	@JsonProperty("lastname")
	private String lastname;
	
	@JsonProperty("aliases")
	private List<String> aliases;
	
	@JsonProperty("country")
	private String country;
	
	// private String type;
	// private String tkbId;
	// private String tideId;
	// private ezbake.base.thrift.DateTime birthDate;
	
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uuid) {
		this.UUID = uuid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middleName) {
		this.middlename = middleName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastName) {
		this.lastname = lastName;
	}
	public List<String> getAliases() {
		return aliases;
	}
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return "Person [UUID=" + UUID + ", firstname=" + firstname
				+ ", middlename=" + middlename + ", lastname=" + lastname
				+ ", aliases=" + aliases + ", country=" + country + "]";
	}
}