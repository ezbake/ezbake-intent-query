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


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum TFetchOrientation implements org.apache.thrift.TEnum {
  FETCH_NEXT(0),
  FETCH_PRIOR(1),
  FETCH_RELATIVE(2),
  FETCH_ABSOLUTE(3),
  FETCH_FIRST(4),
  FETCH_LAST(5);

  private final int value;

  private TFetchOrientation(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static TFetchOrientation findByValue(int value) { 
    switch (value) {
      case 0:
        return FETCH_NEXT;
      case 1:
        return FETCH_PRIOR;
      case 2:
        return FETCH_RELATIVE;
      case 3:
        return FETCH_ABSOLUTE;
      case 4:
        return FETCH_FIRST;
      case 5:
        return FETCH_LAST;
      default:
        return null;
    }
  }
}