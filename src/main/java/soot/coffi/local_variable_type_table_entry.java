package soot.coffi;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2005 Jennifer Lhotak
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

/**
 * An entry in a local variable type table.
 *
 * @see LocalVariableTypeTable_attribute
 * @author Jennifer Lhotak based on local_variabl_table_entry
 */
class local_variable_type_table_entry {
  /** Code offset of start of code wherein this entry applies. */
  public int start_pc;
  /** Length of code sequence in which this name applies. */
  public int length;
  /**
   * Constant pool index of string giving this local variable's name.
   *
   * @see CONSTANT_Utf8_info
   */
  public int name_index;
  /**
   * Constant pool index of string giving this local variable's signature
   *
   * @see CONSTANT_Utf8_info
   */
  public int signature_index;
  /** The index in the local variable array of this local variable. */
  public int index;

  @Override
public String toString() {
    return new StringBuilder().append("start: ").append(start_pc).append("length: ").append(length).append("name_index: ").append(name_index).append("signature_index: ")
			.append(signature_index).append("index: ").append(index).toString();

  }
}
