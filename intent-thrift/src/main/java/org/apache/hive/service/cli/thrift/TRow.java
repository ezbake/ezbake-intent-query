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
package org.apache.hive.service.cli.thrift;

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

public class TRow implements org.apache.thrift.TBase<TRow, TRow._Fields>, java.io.Serializable, Cloneable, Comparable<TRow> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TRow");

  private static final org.apache.thrift.protocol.TField COL_VALS_FIELD_DESC = new org.apache.thrift.protocol.TField("colVals", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TRowStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TRowTupleSchemeFactory());
  }

  public List<TColumnValue> colVals; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COL_VALS((short)1, "colVals");

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
        case 1: // COL_VALS
          return COL_VALS;
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
    tmpMap.put(_Fields.COL_VALS, new org.apache.thrift.meta_data.FieldMetaData("colVals", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TColumnValue.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TRow.class, metaDataMap);
  }

  public TRow() {
  }

  public TRow(
    List<TColumnValue> colVals)
  {
    this();
    this.colVals = colVals;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TRow(TRow other) {
    if (other.isSetColVals()) {
      List<TColumnValue> __this__colVals = new ArrayList<TColumnValue>(other.colVals.size());
      for (TColumnValue other_element : other.colVals) {
        __this__colVals.add(new TColumnValue(other_element));
      }
      this.colVals = __this__colVals;
    }
  }

  public TRow deepCopy() {
    return new TRow(this);
  }

  @Override
  public void clear() {
    this.colVals = null;
  }

  public int getColValsSize() {
    return (this.colVals == null) ? 0 : this.colVals.size();
  }

  public java.util.Iterator<TColumnValue> getColValsIterator() {
    return (this.colVals == null) ? null : this.colVals.iterator();
  }

  public void addToColVals(TColumnValue elem) {
    if (this.colVals == null) {
      this.colVals = new ArrayList<TColumnValue>();
    }
    this.colVals.add(elem);
  }

  public List<TColumnValue> getColVals() {
    return this.colVals;
  }

  public TRow setColVals(List<TColumnValue> colVals) {
    this.colVals = colVals;
    return this;
  }

  public void unsetColVals() {
    this.colVals = null;
  }

  /** Returns true if field colVals is set (has been assigned a value) and false otherwise */
  public boolean isSetColVals() {
    return this.colVals != null;
  }

  public void setColValsIsSet(boolean value) {
    if (!value) {
      this.colVals = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COL_VALS:
      if (value == null) {
        unsetColVals();
      } else {
        setColVals((List<TColumnValue>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COL_VALS:
      return getColVals();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COL_VALS:
      return isSetColVals();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TRow)
      return this.equals((TRow)that);
    return false;
  }

  public boolean equals(TRow that) {
    if (that == null)
      return false;

    boolean this_present_colVals = true && this.isSetColVals();
    boolean that_present_colVals = true && that.isSetColVals();
    if (this_present_colVals || that_present_colVals) {
      if (!(this_present_colVals && that_present_colVals))
        return false;
      if (!this.colVals.equals(that.colVals))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TRow other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetColVals()).compareTo(other.isSetColVals());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetColVals()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.colVals, other.colVals);
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
    StringBuilder sb = new StringBuilder("TRow(");
    boolean first = true;

    sb.append("colVals:");
    if (this.colVals == null) {
      sb.append("null");
    } else {
      sb.append(this.colVals);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (colVals == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'colVals' was not present! Struct: " + toString());
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

  private static class TRowStandardSchemeFactory implements SchemeFactory {
    public TRowStandardScheme getScheme() {
      return new TRowStandardScheme();
    }
  }

  private static class TRowStandardScheme extends StandardScheme<TRow> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TRow struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COL_VALS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list92 = iprot.readListBegin();
                struct.colVals = new ArrayList<TColumnValue>(_list92.size);
                for (int _i93 = 0; _i93 < _list92.size; ++_i93)
                {
                  TColumnValue _elem94;
                  _elem94 = new TColumnValue();
                  _elem94.read(iprot);
                  struct.colVals.add(_elem94);
                }
                iprot.readListEnd();
              }
              struct.setColValsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TRow struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.colVals != null) {
        oprot.writeFieldBegin(COL_VALS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.colVals.size()));
          for (TColumnValue _iter95 : struct.colVals)
          {
            _iter95.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TRowTupleSchemeFactory implements SchemeFactory {
    public TRowTupleScheme getScheme() {
      return new TRowTupleScheme();
    }
  }

  private static class TRowTupleScheme extends TupleScheme<TRow> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TRow struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.colVals.size());
        for (TColumnValue _iter96 : struct.colVals)
        {
          _iter96.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TRow struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list97 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.colVals = new ArrayList<TColumnValue>(_list97.size);
        for (int _i98 = 0; _i98 < _list97.size; ++_i98)
        {
          TColumnValue _elem99;
          _elem99 = new TColumnValue();
          _elem99.read(iprot);
          struct.colVals.add(_elem99);
        }
      }
      struct.setColValsIsSet(true);
    }
  }

}
