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

/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ezbake.query.intents;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query implements org.apache.thrift.TBase<Query, Query._Fields>, java.io.Serializable, Cloneable, Comparable<Query> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Query");

  private static final org.apache.thrift.protocol.TField REQUESTED_COLUMNS_FIELD_DESC = new org.apache.thrift.protocol.TField("requestedColumns", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField PRIMARY_QUERY_FIELD_DESC = new org.apache.thrift.protocol.TField("primaryQuery", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField JOINED_QUERIES_FIELD_DESC = new org.apache.thrift.protocol.TField("joinedQueries", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryTupleSchemeFactory());
  }

  public List<String> requestedColumns; // required
  public QueryAtom primaryQuery; // required
  public List<QueryJoin> joinedQueries; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    REQUESTED_COLUMNS((short)1, "requestedColumns"),
    PRIMARY_QUERY((short)2, "primaryQuery"),
    JOINED_QUERIES((short)3, "joinedQueries");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // REQUESTED_COLUMNS
          return REQUESTED_COLUMNS;
        case 2: // PRIMARY_QUERY
          return PRIMARY_QUERY;
        case 3: // JOINED_QUERIES
          return JOINED_QUERIES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private _Fields optionals[] = {_Fields.JOINED_QUERIES};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.REQUESTED_COLUMNS, new org.apache.thrift.meta_data.FieldMetaData("requestedColumns", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , "Column"))));
    tmpMap.put(_Fields.PRIMARY_QUERY, new org.apache.thrift.meta_data.FieldMetaData("primaryQuery", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, QueryAtom.class)));
    tmpMap.put(_Fields.JOINED_QUERIES, new org.apache.thrift.meta_data.FieldMetaData("joinedQueries", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, QueryJoin.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Query.class, metaDataMap);
  }

  public Query() {
  }

  public Query(
    List<String> requestedColumns,
    QueryAtom primaryQuery)
  {
    this();
    this.requestedColumns = requestedColumns;
    this.primaryQuery = primaryQuery;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Query(Query other) {
    if (other.isSetRequestedColumns()) {
      List<String> __this__requestedColumns = new ArrayList<String>(other.requestedColumns.size());
      for (String other_element : other.requestedColumns) {
        __this__requestedColumns.add(other_element);
      }
      this.requestedColumns = __this__requestedColumns;
    }
    if (other.isSetPrimaryQuery()) {
      this.primaryQuery = new QueryAtom(other.primaryQuery);
    }
    if (other.isSetJoinedQueries()) {
      List<QueryJoin> __this__joinedQueries = new ArrayList<QueryJoin>(other.joinedQueries.size());
      for (QueryJoin other_element : other.joinedQueries) {
        __this__joinedQueries.add(new QueryJoin(other_element));
      }
      this.joinedQueries = __this__joinedQueries;
    }
  }

  public Query deepCopy() {
    return new Query(this);
  }

  @Override
  public void clear() {
    this.requestedColumns = null;
    this.primaryQuery = null;
    this.joinedQueries = null;
  }

  public int getRequestedColumnsSize() {
    return (this.requestedColumns == null) ? 0 : this.requestedColumns.size();
  }

  public java.util.Iterator<String> getRequestedColumnsIterator() {
    return (this.requestedColumns == null) ? null : this.requestedColumns.iterator();
  }

  public void addToRequestedColumns(String elem) {
    if (this.requestedColumns == null) {
      this.requestedColumns = new ArrayList<String>();
    }
    this.requestedColumns.add(elem);
  }

  public List<String> getRequestedColumns() {
    return this.requestedColumns;
  }

  public Query setRequestedColumns(List<String> requestedColumns) {
    this.requestedColumns = requestedColumns;
    return this;
  }

  public void unsetRequestedColumns() {
    this.requestedColumns = null;
  }

  /** Returns true if field requestedColumns is set (has been assigned a value) and false otherwise */
  public boolean isSetRequestedColumns() {
    return this.requestedColumns != null;
  }

  public void setRequestedColumnsIsSet(boolean value) {
    if (!value) {
      this.requestedColumns = null;
    }
  }

  public QueryAtom getPrimaryQuery() {
    return this.primaryQuery;
  }

  public Query setPrimaryQuery(QueryAtom primaryQuery) {
    this.primaryQuery = primaryQuery;
    return this;
  }

  public void unsetPrimaryQuery() {
    this.primaryQuery = null;
  }

  /** Returns true if field primaryQuery is set (has been assigned a value) and false otherwise */
  public boolean isSetPrimaryQuery() {
    return this.primaryQuery != null;
  }

  public void setPrimaryQueryIsSet(boolean value) {
    if (!value) {
      this.primaryQuery = null;
    }
  }

  public int getJoinedQueriesSize() {
    return (this.joinedQueries == null) ? 0 : this.joinedQueries.size();
  }

  public java.util.Iterator<QueryJoin> getJoinedQueriesIterator() {
    return (this.joinedQueries == null) ? null : this.joinedQueries.iterator();
  }

  public void addToJoinedQueries(QueryJoin elem) {
    if (this.joinedQueries == null) {
      this.joinedQueries = new ArrayList<QueryJoin>();
    }
    this.joinedQueries.add(elem);
  }

  public List<QueryJoin> getJoinedQueries() {
    return this.joinedQueries;
  }

  public Query setJoinedQueries(List<QueryJoin> joinedQueries) {
    this.joinedQueries = joinedQueries;
    return this;
  }

  public void unsetJoinedQueries() {
    this.joinedQueries = null;
  }

  /** Returns true if field joinedQueries is set (has been assigned a value) and false otherwise */
  public boolean isSetJoinedQueries() {
    return this.joinedQueries != null;
  }

  public void setJoinedQueriesIsSet(boolean value) {
    if (!value) {
      this.joinedQueries = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case REQUESTED_COLUMNS:
      if (value == null) {
        unsetRequestedColumns();
      } else {
        setRequestedColumns((List<String>)value);
      }
      break;

    case PRIMARY_QUERY:
      if (value == null) {
        unsetPrimaryQuery();
      } else {
        setPrimaryQuery((QueryAtom)value);
      }
      break;

    case JOINED_QUERIES:
      if (value == null) {
        unsetJoinedQueries();
      } else {
        setJoinedQueries((List<QueryJoin>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case REQUESTED_COLUMNS:
      return getRequestedColumns();

    case PRIMARY_QUERY:
      return getPrimaryQuery();

    case JOINED_QUERIES:
      return getJoinedQueries();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case REQUESTED_COLUMNS:
      return isSetRequestedColumns();
    case PRIMARY_QUERY:
      return isSetPrimaryQuery();
    case JOINED_QUERIES:
      return isSetJoinedQueries();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Query)
      return this.equals((Query)that);
    return false;
  }

  public boolean equals(Query that) {
    if (that == null)
      return false;

    boolean this_present_requestedColumns = true && this.isSetRequestedColumns();
    boolean that_present_requestedColumns = true && that.isSetRequestedColumns();
    if (this_present_requestedColumns || that_present_requestedColumns) {
      if (!(this_present_requestedColumns && that_present_requestedColumns))
        return false;
      if (!this.requestedColumns.equals(that.requestedColumns))
        return false;
    }

    boolean this_present_primaryQuery = true && this.isSetPrimaryQuery();
    boolean that_present_primaryQuery = true && that.isSetPrimaryQuery();
    if (this_present_primaryQuery || that_present_primaryQuery) {
      if (!(this_present_primaryQuery && that_present_primaryQuery))
        return false;
      if (!this.primaryQuery.equals(that.primaryQuery))
        return false;
    }

    boolean this_present_joinedQueries = true && this.isSetJoinedQueries();
    boolean that_present_joinedQueries = true && that.isSetJoinedQueries();
    if (this_present_joinedQueries || that_present_joinedQueries) {
      if (!(this_present_joinedQueries && that_present_joinedQueries))
        return false;
      if (!this.joinedQueries.equals(that.joinedQueries))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Query other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetRequestedColumns()).compareTo(other.isSetRequestedColumns());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRequestedColumns()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.requestedColumns, other.requestedColumns);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPrimaryQuery()).compareTo(other.isSetPrimaryQuery());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrimaryQuery()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.primaryQuery, other.primaryQuery);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetJoinedQueries()).compareTo(other.isSetJoinedQueries());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJoinedQueries()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.joinedQueries, other.joinedQueries);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Query(");
    boolean first = true;

    sb.append("requestedColumns:");
    if (this.requestedColumns == null) {
      sb.append("null");
    } else {
      sb.append(this.requestedColumns);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("primaryQuery:");
    if (this.primaryQuery == null) {
      sb.append("null");
    } else {
      sb.append(this.primaryQuery);
    }
    first = false;
    if (isSetJoinedQueries()) {
      if (!first) sb.append(", ");
      sb.append("joinedQueries:");
      if (this.joinedQueries == null) {
        sb.append("null");
      } else {
        sb.append(this.joinedQueries);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (requestedColumns == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'requestedColumns' was not present! Struct: " + toString());
    }
    if (primaryQuery == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'primaryQuery' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (primaryQuery != null) {
      primaryQuery.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class QueryStandardSchemeFactory implements SchemeFactory {
    public QueryStandardScheme getScheme() {
      return new QueryStandardScheme();
    }
  }

  private static class QueryStandardScheme extends StandardScheme<Query> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Query struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // REQUESTED_COLUMNS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.requestedColumns = new ArrayList<String>(_list16.size);
                for (int _i17 = 0; _i17 < _list16.size; ++_i17)
                {
                  String _elem18;
                  _elem18 = iprot.readString();
                  struct.requestedColumns.add(_elem18);
                }
                iprot.readListEnd();
              }
              struct.setRequestedColumnsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRIMARY_QUERY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.primaryQuery = new QueryAtom();
              struct.primaryQuery.read(iprot);
              struct.setPrimaryQueryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // JOINED_QUERIES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list19 = iprot.readListBegin();
                struct.joinedQueries = new ArrayList<QueryJoin>(_list19.size);
                for (int _i20 = 0; _i20 < _list19.size; ++_i20)
                {
                  QueryJoin _elem21;
                  _elem21 = new QueryJoin();
                  _elem21.read(iprot);
                  struct.joinedQueries.add(_elem21);
                }
                iprot.readListEnd();
              }
              struct.setJoinedQueriesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Query struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.requestedColumns != null) {
        oprot.writeFieldBegin(REQUESTED_COLUMNS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.requestedColumns.size()));
          for (String _iter22 : struct.requestedColumns)
          {
            oprot.writeString(_iter22);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.primaryQuery != null) {
        oprot.writeFieldBegin(PRIMARY_QUERY_FIELD_DESC);
        struct.primaryQuery.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.joinedQueries != null) {
        if (struct.isSetJoinedQueries()) {
          oprot.writeFieldBegin(JOINED_QUERIES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.joinedQueries.size()));
            for (QueryJoin _iter23 : struct.joinedQueries)
            {
              _iter23.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QueryTupleSchemeFactory implements SchemeFactory {
    public QueryTupleScheme getScheme() {
      return new QueryTupleScheme();
    }
  }

  private static class QueryTupleScheme extends TupleScheme<Query> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Query struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.requestedColumns.size());
        for (String _iter24 : struct.requestedColumns)
        {
          oprot.writeString(_iter24);
        }
      }
      struct.primaryQuery.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetJoinedQueries()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetJoinedQueries()) {
        {
          oprot.writeI32(struct.joinedQueries.size());
          for (QueryJoin _iter25 : struct.joinedQueries)
          {
            _iter25.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Query struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list26 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
        struct.requestedColumns = new ArrayList<String>(_list26.size);
        for (int _i27 = 0; _i27 < _list26.size; ++_i27)
        {
          String _elem28;
          _elem28 = iprot.readString();
          struct.requestedColumns.add(_elem28);
        }
      }
      struct.setRequestedColumnsIsSet(true);
      struct.primaryQuery = new QueryAtom();
      struct.primaryQuery.read(iprot);
      struct.setPrimaryQueryIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.joinedQueries = new ArrayList<QueryJoin>(_list29.size);
          for (int _i30 = 0; _i30 < _list29.size; ++_i30)
          {
            QueryJoin _elem31;
            _elem31 = new QueryJoin();
            _elem31.read(iprot);
            struct.joinedQueries.add(_elem31);
          }
        }
        struct.setJoinedQueriesIsSet(true);
      }
    }
  }

}

