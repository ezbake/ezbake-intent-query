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
package ezbake.query.intents.base;

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

public class SecurityLabel implements org.apache.thrift.TBase<SecurityLabel, SecurityLabel._Fields>, java.io.Serializable, Cloneable, Comparable<SecurityLabel> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SecurityLabel");

  private static final org.apache.thrift.protocol.TField CLASSIFICATION_FIELD_DESC = new org.apache.thrift.protocol.TField("classification", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField DISCOVERY_CLASSIFICATION_FIELD_DESC = new org.apache.thrift.protocol.TField("discoveryClassification", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SecurityLabelStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SecurityLabelTupleSchemeFactory());
  }

  public ezbake.base.thrift.Classification classification; // required
  public ezbake.base.thrift.Classification discoveryClassification; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CLASSIFICATION((short)1, "classification"),
    DISCOVERY_CLASSIFICATION((short)2, "discoveryClassification");

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
        case 1: // CLASSIFICATION
          return CLASSIFICATION;
        case 2: // DISCOVERY_CLASSIFICATION
          return DISCOVERY_CLASSIFICATION;
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
  private _Fields optionals[] = {_Fields.DISCOVERY_CLASSIFICATION};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLASSIFICATION, new org.apache.thrift.meta_data.FieldMetaData("classification", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "Classification")));
    tmpMap.put(_Fields.DISCOVERY_CLASSIFICATION, new org.apache.thrift.meta_data.FieldMetaData("discoveryClassification", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT        , "Classification")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SecurityLabel.class, metaDataMap);
  }

  public SecurityLabel() {
  }

  public SecurityLabel(
    ezbake.base.thrift.Classification classification)
  {
    this();
    this.classification = classification;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SecurityLabel(SecurityLabel other) {
    if (other.isSetClassification()) {
      this.classification = other.classification;
    }
    if (other.isSetDiscoveryClassification()) {
      this.discoveryClassification = other.discoveryClassification;
    }
  }

  public SecurityLabel deepCopy() {
    return new SecurityLabel(this);
  }

  @Override
  public void clear() {
    this.classification = null;
    this.discoveryClassification = null;
  }

  public ezbake.base.thrift.Classification getClassification() {
    return this.classification;
  }

  public SecurityLabel setClassification(ezbake.base.thrift.Classification classification) {
    this.classification = classification;
    return this;
  }

  public void unsetClassification() {
    this.classification = null;
  }

  /** Returns true if field classification is set (has been assigned a value) and false otherwise */
  public boolean isSetClassification() {
    return this.classification != null;
  }

  public void setClassificationIsSet(boolean value) {
    if (!value) {
      this.classification = null;
    }
  }

  public ezbake.base.thrift.Classification getDiscoveryClassification() {
    return this.discoveryClassification;
  }

  public SecurityLabel setDiscoveryClassification(ezbake.base.thrift.Classification discoveryClassification) {
    this.discoveryClassification = discoveryClassification;
    return this;
  }

  public void unsetDiscoveryClassification() {
    this.discoveryClassification = null;
  }

  /** Returns true if field discoveryClassification is set (has been assigned a value) and false otherwise */
  public boolean isSetDiscoveryClassification() {
    return this.discoveryClassification != null;
  }

  public void setDiscoveryClassificationIsSet(boolean value) {
    if (!value) {
      this.discoveryClassification = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CLASSIFICATION:
      if (value == null) {
        unsetClassification();
      } else {
        setClassification((ezbake.base.thrift.Classification)value);
      }
      break;

    case DISCOVERY_CLASSIFICATION:
      if (value == null) {
        unsetDiscoveryClassification();
      } else {
        setDiscoveryClassification((ezbake.base.thrift.Classification)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLASSIFICATION:
      return getClassification();

    case DISCOVERY_CLASSIFICATION:
      return getDiscoveryClassification();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CLASSIFICATION:
      return isSetClassification();
    case DISCOVERY_CLASSIFICATION:
      return isSetDiscoveryClassification();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SecurityLabel)
      return this.equals((SecurityLabel)that);
    return false;
  }

  public boolean equals(SecurityLabel that) {
    if (that == null)
      return false;

    boolean this_present_classification = true && this.isSetClassification();
    boolean that_present_classification = true && that.isSetClassification();
    if (this_present_classification || that_present_classification) {
      if (!(this_present_classification && that_present_classification))
        return false;
      if (!this.classification.equals(that.classification))
        return false;
    }

    boolean this_present_discoveryClassification = true && this.isSetDiscoveryClassification();
    boolean that_present_discoveryClassification = true && that.isSetDiscoveryClassification();
    if (this_present_discoveryClassification || that_present_discoveryClassification) {
      if (!(this_present_discoveryClassification && that_present_discoveryClassification))
        return false;
      if (!this.discoveryClassification.equals(that.discoveryClassification))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(SecurityLabel other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetClassification()).compareTo(other.isSetClassification());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClassification()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.classification, other.classification);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDiscoveryClassification()).compareTo(other.isSetDiscoveryClassification());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDiscoveryClassification()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.discoveryClassification, other.discoveryClassification);
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
    StringBuilder sb = new StringBuilder("SecurityLabel(");
    boolean first = true;

    sb.append("classification:");
    if (this.classification == null) {
      sb.append("null");
    } else {
      sb.append(this.classification);
    }
    first = false;
    if (isSetDiscoveryClassification()) {
      if (!first) sb.append(", ");
      sb.append("discoveryClassification:");
      if (this.discoveryClassification == null) {
        sb.append("null");
      } else {
        sb.append(this.discoveryClassification);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (classification == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'classification' was not present! Struct: " + toString());
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

  private static class SecurityLabelStandardSchemeFactory implements SchemeFactory {
    public SecurityLabelStandardScheme getScheme() {
      return new SecurityLabelStandardScheme();
    }
  }

  private static class SecurityLabelStandardScheme extends StandardScheme<SecurityLabel> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SecurityLabel struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CLASSIFICATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.classification = new ezbake.base.thrift.Classification();
              struct.classification.read(iprot);
              struct.setClassificationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DISCOVERY_CLASSIFICATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.discoveryClassification = new ezbake.base.thrift.Classification();
              struct.discoveryClassification.read(iprot);
              struct.setDiscoveryClassificationIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SecurityLabel struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.classification != null) {
        oprot.writeFieldBegin(CLASSIFICATION_FIELD_DESC);
        struct.classification.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.discoveryClassification != null) {
        if (struct.isSetDiscoveryClassification()) {
          oprot.writeFieldBegin(DISCOVERY_CLASSIFICATION_FIELD_DESC);
          struct.discoveryClassification.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SecurityLabelTupleSchemeFactory implements SchemeFactory {
    public SecurityLabelTupleScheme getScheme() {
      return new SecurityLabelTupleScheme();
    }
  }

  private static class SecurityLabelTupleScheme extends TupleScheme<SecurityLabel> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SecurityLabel struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.classification.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetDiscoveryClassification()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetDiscoveryClassification()) {
        struct.discoveryClassification.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SecurityLabel struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.classification = new ezbake.base.thrift.Classification();
      struct.classification.read(iprot);
      struct.setClassificationIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.discoveryClassification = new ezbake.base.thrift.Classification();
        struct.discoveryClassification.read(iprot);
        struct.setDiscoveryClassificationIsSet(true);
      }
    }
  }

}
