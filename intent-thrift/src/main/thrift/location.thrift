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

struct Location {
    1: required baseIntents.BaseContext base;
    2: required double latitude;
    3: required double longitude;
    4: optional double altitude;
    5: optional double lineOfBearing;       // Degrees CW
    6: optional double lobLatitude;         // Lat of collectors position
    7: optional double lobLongitude;        // Lon of collectors position
    8: optional string placeName;           // Possibly BE number
    9: optional double semimajorErrorAxis; // Error ellipse semi-major axis (nmi)
    10: optional double semiminorErrorAxis; // Error ellipse semi-minor axis (nmi)
    11: optional double circularErrorProb;  // Circular error for error ellipse
    12: optional double orientationAngle;   // Degrees CW from true north of error ellipse
    13: optional double course;             // Course angle in degrees CW from true north (extrapolated)
    14: optional double speed;              // Speed
    15: optional string serialNumber;
    16: optional string collectionType;
    17: optional string locationConfidence;
    18: optional string description;
}