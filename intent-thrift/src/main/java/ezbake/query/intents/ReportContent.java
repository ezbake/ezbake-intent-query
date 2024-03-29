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

public class ReportContent extends org.apache.thrift.TUnion<ReportContent, ReportContent._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReportContent");
  private static final org.apache.thrift.protocol.TField FULL_TEXT_FIELD_DESC = new org.apache.thrift.protocol.TField("fullText", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField URI_FIELD_DESC = new org.apache.thrift.protocol.TField("uri", org.apache.thrift.protocol.TType.STRING, (short)2);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FULL_TEXT((short)1, "fullText"),
    URI((short)2, "uri");

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
        case 1: // FULL_TEXT
          return FULL_TEXT;
        case 2: // URI
          return URI;
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

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FULL_TEXT, new org.apache.thrift.meta_data.FieldMetaData("fullText", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.URI, new org.apache.thrift.meta_data.FieldMetaData("uri", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReportContent.class, metaDataMap);
  }

  public ReportContent() {
    super();
  }

  public ReportContent(_Fields setField, Object value) {
    super(setField, value);
  }

  public ReportContent(ReportContent other) {
    super(other);
  }
  public ReportContent deepCopy() {
    return new ReportContent(this);
  }

  public static ReportContent fullText(ByteBuffer value) {
    ReportContent x = new ReportContent();
    x.setFullText(value);
    return x;
  }

  public static ReportContent fullText(byte[] value) {
    ReportContent x = new ReportContent();
    x.setFullText(ByteBuffer.wrap(value));
    return x;
  }

  public static ReportContent uri(String value) {
    ReportContent x = new ReportContent();
    x.setUri(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, Object value) throws ClassCastException {
    switch (setField) {
      case FULL_TEXT:
        if (value instanceof ByteBuffer) {
          break;
        }
        throw new ClassCastException("Was expecting value of type ByteBuffer for field 'fullText', but got " + value.getClass().getSimpleName());
      case URI:
        if (value instanceof String) {
          break;
        }
        throw new ClassCastException("Was expecting value of type String for field 'uri', but got " + value.getClass().getSimpleName());
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case FULL_TEXT:
          if (field.type == FULL_TEXT_FIELD_DESC.type) {
            ByteBuffer fullText;
            fullText = iprot.readBinary();
            return fullText;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case URI:
          if (field.type == URI_FIELD_DESC.type) {
            String uri;
            uri = iprot.readString();
            return uri;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      return null;
    }
  }

  @Override
  protected void standardSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case FULL_TEXT:
        ByteBuffer fullText = (ByteBuffer)value_;
        oprot.writeBinary(fullText);
        return;
      case URI:
        String uri = (String)value_;
        oprot.writeString(uri);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected Object tupleSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, short fieldID) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(fieldID);
    if (setField != null) {
      switch (setField) {
        case FULL_TEXT:
          ByteBuffer fullText;
          fullText = iprot.readBinary();
          return fullText;
        case URI:
          String uri;
          uri = iprot.readString();
          return uri;
        default:
          throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      throw new TProtocolException("Couldn't find a field with field id " + fieldID);
    }
  }

  @Override
  protected void tupleSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case FULL_TEXT:
        ByteBuffer fullText = (ByteBuffer)value_;
        oprot.writeBinary(fullText);
        return;
      case URI:
        String uri = (String)value_;
        oprot.writeString(uri);
        return;
      default:
        throw new IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case FULL_TEXT:
        return FULL_TEXT_FIELD_DESC;
      case URI:
        return URI_FIELD_DESC;
      default:
        throw new IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TStruct getStructDesc() {
    return STRUCT_DESC;
  }

  @Override
  protected _Fields enumForId(short id) {
    return _Fields.findByThriftIdOrThrow(id);
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public byte[] getFullText() {
    setFullText(org.apache.thrift.TBaseHelper.rightSize(bufferForFullText()));
    ByteBuffer b = bufferForFullText();
    return b == null ? null : b.array();
  }

  public ByteBuffer bufferForFullText() {
    if (getSetField() == _Fields.FULL_TEXT) {
      return (ByteBuffer)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'fullText' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setFullText(byte[] value) {
    setFullText(ByteBuffer.wrap(value));
  }

  public void setFullText(ByteBuffer value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.FULL_TEXT;
    value_ = value;
  }

  public String getUri() {
    if (getSetField() == _Fields.URI) {
      return (String)getFieldValue();
    } else {
      throw new RuntimeException("Cannot get field 'uri' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setUri(String value) {
    if (value == null) throw new NullPointerException();
    setField_ = _Fields.URI;
    value_ = value;
  }

  public boolean isSetFullText() {
    return setField_ == _Fields.FULL_TEXT;
  }


  public boolean isSetUri() {
    return setField_ == _Fields.URI;
  }


  public boolean equals(Object other) {
    if (other instanceof ReportContent) {
      return equals((ReportContent)other);
    } else {
      return false;
    }
  }

  public boolean equals(ReportContent other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(ReportContent other) {
    int lastComparison = org.apache.thrift.TBaseHelper.compareTo(getSetField(), other.getSetField());
    if (lastComparison == 0) {
      return org.apache.thrift.TBaseHelper.compareTo(getFieldValue(), other.getFieldValue());
    }
    return lastComparison;
  }


  /**
   * If you'd like this to perform more respectably, use the hashcode generator option.
   */
  @Override
  public int hashCode() {
    return 0;
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


}
