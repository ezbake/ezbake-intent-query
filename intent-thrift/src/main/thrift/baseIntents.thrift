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

namespace * ezbake.query.intents.base

include 'ezbakeBaseTypes.thrift'
include 'classification.thrift'

typedef ezbakeBaseTypes.DateTime DateTime
typedef classification.Classification Classification

struct SecurityLabel {
    1: required Classification classification;
    2: optional Classification discoveryClassification;
}

struct TemporalRange {
    1: optional DateTime startDate;
    2: optional DateTime endDate;
}

// Base for all standard object intents
struct StandardObject {
    1: required string uuid;                // System generated UUID
    2: required SecurityLabel security;
    3: optional TemporalRange startEnd;
    4: optional string dataSource;
    5: optional string description;
}

// Base for all object context intents
struct BaseContext {
    1: required string objectUUID;          // Points at a standard object UUID
    2: required SecurityLabel security;
    3: optional TemporalRange startEnd;
    4: optional string dataSource;
    5: optional string confidence;
}