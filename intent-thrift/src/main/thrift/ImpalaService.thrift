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

// Copyright 2012 Cloudera Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

namespace cpp impala
namespace java com.cloudera.impala.thrift
namespace rb impala.protocol

include "beeswax.thrift"
include "impalaTypes.thrift"
include "cli_service.thrift"

// ImpalaService accepts query execution options through beeswax.Query.configuration in
// key:value form. For example, the list of strings could be:
//     "num_nodes:1", "abort_on_error:false"
// The valid keys are listed in this enum. They map to TQueryOptions.
// Note: If you add an option or change the default, you also need to update:
// - ImpalaInternalService.thrift: TQueryOptions
// - ImpaladClientExecutor.getBeeswaxQueryConfigurations()
// - ImpalaServer::SetQueryOptions()
// - ImpalaServer::TQueryOptionsToMap()
enum TImpalaQueryOptions {
  // if true, abort execution on the first error
  ABORT_ON_ERROR,

  // maximum # of errors to be reported; Unspecified or 0 indicates backend default
  MAX_ERRORS,

  // if true, disable llvm codegen
  DISABLE_CODEGEN,

  // batch size to be used by backend; Unspecified or a size of 0 indicates backend
  // default
  BATCH_SIZE,

  // a per-machine approximate limit on the memory consumption of this query;
  // unspecified or a limit of 0 means no limit;
  // otherwise specified either as:
  // a) an int (= number of bytes);
  // b) a float followed by "M" (MB) or "G" (GB)
  MEM_LIMIT,

  // specifies the degree of parallelism with which to execute the query;
  // 1: single-node execution
  // NUM_NODES_ALL: executes on all nodes that contain relevant data
  // NUM_NODES_ALL_RACKS: executes on one node per rack that holds relevant data
  // > 1: executes on at most that many nodes at any point in time (ie, there can be
  //      more nodes than numNodes with plan fragments for this query, but at most
  //      numNodes would be active at any point in time)
  // Constants (NUM_NODES_ALL, NUM_NODES_ALL_RACKS) are defined in JavaConstants.thrift.
  NUM_NODES,

  // maximum length of the scan range; only applicable to HDFS scan range; Unspecified or
  // a length of 0 indicates backend default;
  MAX_SCAN_RANGE_LENGTH,

  // Maximum number of io buffers (per disk)
  MAX_IO_BUFFERS,

  // Number of scanner threads.
  NUM_SCANNER_THREADS,

  // If true, Impala will try to execute on file formats that are not fully supported yet
  ALLOW_UNSUPPORTED_FORMATS,

  // if set and > -1, specifies the default limit applied to a top-level SELECT statement
  // with an ORDER BY but without a LIMIT clause (ie, if the SELECT statement also has
  // a LIMIT clause, this default is ignored)
  DEFAULT_ORDER_BY_LIMIT,

  // DEBUG ONLY:
  // If set to
  //   "[<backend number>:]<node id>:<TExecNodePhase>:<TDebugAction>",
  // the exec node with the given id will perform the specified action in the given
  // phase. If the optional backend number (starting from 0) is specified, only that
  // backend instance will perform the debug action, otherwise all backends will behave
  // in that way.
  // If the string doesn't have the required format or if any of its components is
  // invalid, the option is ignored.
  DEBUG_ACTION,

  // If true, raise an error when the DEFAULT_ORDER_BY_LIMIT has been reached.
  ABORT_ON_DEFAULT_LIMIT_EXCEEDED,

  // Compression codec for parquet when inserting into parquet tables.
  // Valid values are "snappy", "gzip" and "none"
  // Leave blank to use default.
  PARQUET_COMPRESSION_CODEC,

  // HBase scan query option. If set and > 0, HBASE_CACHING is the value for
  // "hbase.client.Scan.setCaching()" when querying HBase table. Otherwise, use backend
  // default.
  // If the value is too high, then the hbase region server will have a hard time (GC
  // pressure and long response times). If the value is too small, then there will be
  // extra trips to the hbase region server.
  HBASE_CACHING,

  // HBase scan query option. If set, HBase scan will always set
  // "hbase.client.setCacheBlocks" to CACHE_BLOCKS. Default is false.
  // If the table is large and the query is doing big scan, set it to false to
  // avoid polluting the cache in the hbase region server.
  // If the table is small and the table is used several time, set it to true to improve
  // performance.
  HBASE_CACHE_BLOCKS,
}

// The summary of an insert.
struct TInsertResult {
  // Number of appended rows per modified partition. Only applies to HDFS tables.
  // The keys represent partitions to create, coded as k1=v1/k2=v2/k3=v3..., with the
  // root in an unpartitioned table being the empty string.
  1: required map<string, i64> rows_appended
}

// Response from a call to PingImpalaService
struct TPingImpalaServiceResp {
  // The Impala service's version string.
  1: string version
}

// Parameters for a ResetTable request which will invalidate a table's metadata.
// DEPRECATED.
struct TResetTableReq {
  // Name of the table's parent database.
  1: required string db_name

  // Name of the table.
  2: required string table_name
}

// For all rpc that return a TStatus as part of their result type,
// if the status_code field is set to anything other than OK, the contents
// of the remainder of the result type is undefined (typically not set)
service ImpalaService extends beeswax.BeeswaxService {
  // Cancel execution of query. Returns RUNTIME_ERROR if query_id
  // unknown.
  // This terminates all threads running on behalf of this query at
  // all nodes that were involved in the execution.
  // Throws BeeswaxException if the query handle is invalid (this doesn't
  // necessarily indicate an error: the query might have finished).
  impalaTypes.TStatus Cancel(1:beeswax.QueryHandle query_id)
      throws(1:beeswax.BeeswaxException error);

  // Invalidates all catalog metadata, forcing a reload
  // DEPRECATED; execute query "invalidate metadata" to refresh metadata
  impalaTypes.TStatus ResetCatalog();

  // Invalidates a specific table's catalog metadata, forcing a reload on the next access
  // DEPRECATED; execute query "refresh <table>" to refresh metadata
  impalaTypes.TStatus ResetTable(1:TResetTableReq request)

  // Returns the runtime profile string for the given query handle.
  string GetRuntimeProfile(1:beeswax.QueryHandle query_id)
      throws(1:beeswax.BeeswaxException error);

  // Closes the query handle and return the result summary of the insert.
  TInsertResult CloseInsert(1:beeswax.QueryHandle handle)
      throws(1:beeswax.QueryNotFoundException error, 2:beeswax.BeeswaxException error2);

  // Client calls this RPC to verify that the server is an ImpalaService. Returns the
  // server version.
  TPingImpalaServiceResp PingImpalaService();
}

// Impala HiveServer2 service
service ImpalaHiveServer2Service extends cli_service.TCLIService {
}
