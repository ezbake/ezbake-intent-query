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

enum RelationshipType {
    // Heirarchy
    IN_COMMAND_OF,
    IS_SUBORDINATE_TO,
    SISTER_AIRFIELD,
    // Collaborators
    DEPLOYS_WITH,
    TRAINS_WITH,
    WORKS_WITH,
    TRANSMITS_TO,
    RECEIVES_FROM,
    // Locations
    DEPLOYS_FROM,
    DEPLOYS_FROM_HERE,
    DEPLOYS_TO,
    DEPLOYS_TO_HERE,
    GARRISONED_AT,
    GARRISONED_HERE,
    HOMEPORTED_AT,
    HOMEPORTED_HERE,
    PATROLS_AREA,
    PATROLLED_BY,
    TRAINS_AT,
    TRAINS_HERE,
    LOCATED_NEAR,
    LOCATED_NEARBY,
    // Equipment
    COMPONENT_OF,
    COMPONENTS_INCLUDE,
    USES,
    USED_BY,
    IS_USED_IN,
    IS_USED_FOR,
    TESTED_AT,
    TESTED_HERE,
    REPAIRED_AT,
    REPAIRED_HERE,
    // Actions/events
    AFFECTS,
    REACTS_TO,
    ATTENDS,
    ATTENDED_BY,
    PARTICIPATES_IN,
    PARTICIPANTS,
    SUPPORTS,
    SUPPORTED_BY
}

struct Relationship {
    1: required baseIntents.BaseContext base;
    2: required string relatedId;
    3: required RelationshipType type;
}