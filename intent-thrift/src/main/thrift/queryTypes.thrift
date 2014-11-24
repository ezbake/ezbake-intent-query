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

include 'intents.thrift'

typedef string Column

enum BinaryOperator {
    LT, LE, EQ, NE, GE, GT
}

union ColumnValue{
    1: bool boolValue,
    2: i32 integerValue,
    3: i64 longValue,
    4: double doubleValue,
    5: string stringValue
}

union PredicateValue {
    1: Column column,
    2: ColumnValue value
}

struct BinaryPredicate {
    1: required Column columnName,
    2: required BinaryOperator operator,
    3: required ColumnValue value
}

struct UnaryPredicate {
    1: required string predicateName,
    2: required list<PredicateValue> values
}

union Predicate {
    1: BinaryPredicate binaryPredicate,
    2: UnaryPredicate unaryPredicate
}

struct QueryAtom {
    1: required intents.IntentType intent,
    2: required list<Predicate> predicates
}

struct QueryJoin {
    1: required QueryAtom query,
    2: required Column joinColumn
}

struct Query {
    1: required list<Column> requestedColumns,
    2: required QueryAtom primaryQuery,
    3: optional list<QueryJoin> joinedQueries
}