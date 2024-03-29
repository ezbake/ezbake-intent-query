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
package com.cloudera.impala.extdatasource.thrift;

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

public class TTableSchema implements org.apache.thrift.TBase<TTableSchema, TTableSchema._Fields>, java.io.Serializable, Cloneable, Comparable<TTableSchema> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TTableSchema");

  private static final org.apache.thrift.protocol.TField COLS_FIELD_DESC = new org.apache.thrift.protocol.TField("cols", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TTableSchemaStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TTableSchemaTupleSchemeFactory());
  }

  public List<TColumnDesc> cols; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COLS((short)1, "cols");

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
        case 1: // COLS
          return COLS;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COLS, new org.apache.thrift.meta_data.FieldMetaData("cols", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TColumnDesc.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TTableSchema.class, metaDataMap);
  }

  public TTableSchema() {
  }

  public TTableSchema(
    List<TColumnDesc> cols)
  {
    this();
    this.cols = cols;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TTableSchema(TTableSchema other) {
    if (other.isSetCols()) {
      List<TColumnDesc> __this__cols = new ArrayList<TColumnDesc>(other.cols.size());
      for (TColumnDesc other_element : other.cols) {
        __this__cols.add(new TColumnDesc(other_element));
      }
      this.cols = __this__cols;
    }
  }

  public TTableSchema deepCopy() {
    return new TTableSchema(this);
  }

  @Override
  public void clear() {
    this.cols = null;
  }

  public int getColsSize() {
    return (this.cols == null) ? 0 : this.cols.size();
  }

  public java.util.Iterator<TColumnDesc> getColsIterator() {
    return (this.cols == null) ? null : this.cols.iterator();
  }

  public void addToCols(TColumnDesc elem) {
    if (this.cols == null) {
      this.cols = new ArrayList<TColumnDesc>();
    }
    this.cols.add(elem);
  }

  public List<TColumnDesc> getCols() {
    return this.cols;
  }

  public TTableSchema setCols(List<TColumnDesc> cols) {
    this.cols = cols;
    return this;
  }

  public void unsetCols() {
    this.cols = null;
  }

  /** Returns true if field cols is set (has been assigned a value) and false otherwise */
  public boolean isSetCols() {
    return this.cols != null;
  }

  public void setColsIsSet(boolean value) {
    if (!value) {
      this.cols = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COLS:
      if (value == null) {
        unsetCols();
      } else {
        setCols((List<TColumnDesc>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COLS:
      return getCols();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COLS:
      return isSetCols();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TTableSchema)
      return this.equals((TTableSchema)that);
    return false;
  }

  public boolean equals(TTableSchema that) {
    if (that == null)
      return false;

    boolean this_present_cols = true && this.isSetCols();
    boolean that_present_cols = true && that.isSetCols();
    if (this_present_cols || that_present_cols) {
      if (!(this_present_cols && that_present_cols))
        return false;
      if (!this.cols.equals(that.cols))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TTableSchema other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCols()).compareTo(other.isSetCols());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCols()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cols, other.cols);
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
    StringBuilder sb = new StringBuilder("TTableSchema(");
    boolean first = true;

    sb.append("cols:");
    if (this.cols == null) {
      sb.append("null");
    } else {
      sb.append(this.cols);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (cols == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'cols' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
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

  private static class TTableSchemaStandardSchemeFactory implements SchemeFactory {
    public TTableSchemaStandardScheme getScheme() {
      return new TTableSchemaStandardScheme();
    }
  }

  private static class TTableSchemaStandardScheme extends StandardScheme<TTableSchema> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TTableSchema struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COLS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.cols = new ArrayList<TColumnDesc>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  TColumnDesc _elem2;
                  _elem2 = new TColumnDesc();
                  _elem2.read(iprot);
                  struct.cols.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setColsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TTableSchema struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.cols != null) {
        oprot.writeFieldBegin(COLS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.cols.size()));
          for (TColumnDesc _iter3 : struct.cols)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TTableSchemaTupleSchemeFactory implements SchemeFactory {
    public TTableSchemaTupleScheme getScheme() {
      return new TTableSchemaTupleScheme();
    }
  }

  private static class TTableSchemaTupleScheme extends TupleScheme<TTableSchema> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TTableSchema struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.cols.size());
        for (TColumnDesc _iter4 : struct.cols)
        {
          _iter4.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TTableSchema struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.cols = new ArrayList<TColumnDesc>(_list5.size);
        for (int _i6 = 0; _i6 < _list5.size; ++_i6)
        {
          TColumnDesc _elem7;
          _elem7 = new TColumnDesc();
          _elem7.read(iprot);
          struct.cols.add(_elem7);
        }
      }
      struct.setColsIsSet(true);
    }
  }

}

