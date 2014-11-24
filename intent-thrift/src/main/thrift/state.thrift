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

enum ObjectState {
    ACTIVE,
    AT_AIRFIELD,
    DECOMMISSIONED,
    DEPLOYED,
    FITTING_OUT,
    IN_FLIGHT,
    IN_GARRISON,
    IN_PORT,
    INACTIVE,
    OUT_OF_GARRISON,
    OUT_OF_PORT,
    UNDERWAY
}

struct State {
    1: required baseIntents.BaseContext base;
    2: required ObjectState state;
}