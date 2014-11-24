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

namespace * ezbake.intents

include 'activity.thrift'
include 'equipment.thrift'
include 'event.thrift'
include 'facility.thrift'
include 'image.thrift'
include 'issue.thrift'
include 'location.thrift'
include 'person.thrift'
include 'relationship.thrift'
include 'report.thrift'
include 'state.thrift'
include 'unit.thrift'

enum IntentType {
    ACTIVITY,
    EQUIPMENT,
    EVENT,
    FACILITY,
    IMAGE,
    ISSUE,
    LOCATION,
    PERSON,
    REPORT,
    RELATIONSHIP,
    STATE,
    UNIT
}

union Intent {
    1: activity.Activity activity,
    2: equipment.Equipment equipment,
    3: event.Event event,
    4: facility.Facility facility,
    5: image.Image image,
    6: issue.Issue issue,
    7: location.Location location,
    8: person.Person person,
    9: relationship.Relationship relationship,
    10: report.Report report,
    11: state.State state,
    12: unit.Unit unit
}