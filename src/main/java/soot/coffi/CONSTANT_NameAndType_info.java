package soot.coffi;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 Clark Verbrugge
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import soot.Value;

/**
 * A constant pool entry of type CONSTANT_NameAndType
 * 
 * @see cp_info
 * @author Clark Verbrugge
 */
class CONSTANT_NameAndType_info extends cp_info {
  /**
   * Constant pool index of the CONSTANT_Utf8 object for the name.
   * 
   * @see CONSTANT_Utf8_info
   */
  public int name_index;
  /**
   * Constant pool index of the CONSTANT_Utf8 object for the descriptor.
   * 
   * @see CONSTANT_Utf8_info
   */
  public int descriptor_index;

  /**
   * Returns the size of this cp_info object.
   * 
   * @return number of bytes occupied by this object.
   * @see cp_info#size
   */
  @Override
public int size() {
    return 5;
  }

  /**
   * Returns a String representation of this entry.
   * 
   * @param constant_pool
   *          constant pool of ClassFile.
   * @return String representation of this entry.
   * @see cp_info#toString
   */
  @Override
public String toString(cp_info constant_pool[]) {
    CONSTANT_Utf8_info ci = (CONSTANT_Utf8_info) (constant_pool[name_index]);
    // CONSTANT_Utf8_info di = (CONSTANT_Utf8_info)(constant_pool[descriptor_index]);
    return ci.convert(); // + "/" + di.convert();
  }

  /**
   * Returns a String description of what kind of entry this is.
   * 
   * @return the String "nameandtype".
   * @see cp_info#typeName
   */
  @Override
public String typeName() {
    return "nameandtype";
  }

  /**
   * Compares this entry with another cp_info object (which may reside in a different constant pool).
   * 
   * @param constant_pool
   *          constant pool of ClassFile for this.
   * @param cp
   *          constant pool entry to compare against.
   * @param cp_constant_pool
   *          constant pool of ClassFile for cp.
   * @return a value <0, 0, or >0 indicating whether this is smaller, the same or larger than cp.
   * @see cp_info#compareTo
   */
  @Override
public int compareTo(cp_info constant_pool[], cp_info cp, cp_info cp_constant_pool[]) {
    int i;
    if (tag != cp.tag) {
      return tag - cp.tag;
    }
    CONSTANT_NameAndType_info cu = (CONSTANT_NameAndType_info) cp;
    i = ((CONSTANT_Utf8_info) (constant_pool[name_index])).compareTo(cp_constant_pool[cu.name_index]);
    if (i != 0) {
      return i;
    }
    return ((CONSTANT_Utf8_info) (constant_pool[descriptor_index])).compareTo(cp_constant_pool[cu.descriptor_index]);
  }

  @Override
public Value createJimpleConstantValue(cp_info[] constant_pool) {
    throw new UnsupportedOperationException("cannot convert to Jimple: " + typeName());
  }

}
