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

namespace * ezbake.query.intents

include 'baseIntents.thrift'
include 'ezbakeBaseTypes.thrift'

typedef ezbakeBaseTypes.DateTime DateTime

struct Person {
    1: required baseIntents.StandardObject base;
    2: optional string firstName;
    3: optional string middleName;
    4: optional string lastName;
    5: optional list<string> aliases;
    6: optional string country;
    7: optional string type;
    8: optional string tkbId;
    9: optional string tideId;
    10: optional DateTime birthDate;
}