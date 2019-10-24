package soot.tagkit;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2003 Archie L. Cobbs
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

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InnerClassTag implements Tag {
  private static final Logger logger = LoggerFactory.getLogger(InnerClassTag.class);
String innerClass;
  String outerClass;
  String name;
  int accessFlags;

  public InnerClassTag(String innerClass, String outerClass, String name, int accessFlags) {
    this.innerClass = innerClass;
    this.outerClass = outerClass;
    this.name = name;
    this.accessFlags = accessFlags;
    if (innerClass != null && (innerClass.startsWith("L") && innerClass.endsWith(";"))) {
      throw new RuntimeException(
          new StringBuilder().append("InnerClass annotation type string must ").append("be of the form a/b/ClassName not '").append(innerClass).append("'").toString());
    }
    if (outerClass != null && (outerClass.startsWith("L") && outerClass.endsWith(";"))) {
      throw new RuntimeException(
          new StringBuilder().append("OuterType annotation type string must ").append("be of the form a/b/ClassName not '").append(innerClass).append("'").toString());
    }
    if (name != null && name.endsWith(";")) {
      throw new RuntimeException(new StringBuilder().append("InnerClass name cannot end with ';', got '").append(name).append("'").toString());
    }
  }

  @Override
  public String getName() {
    return "InnerClassTag";
  }

  /**
   * Returns the inner class name (only) encoded in UTF8. There is no obvious standalone byte[] encoding for this attribute
   * because it contains embedded constant pool indicies.
   */
  @Override
  public byte[] getValue() {
    try {
      return innerClass.getBytes("UTF8");
    } catch (UnsupportedEncodingException e) {
      logger.error(e.getMessage(), e);
	return new byte[0];
    }
  }

  public String getInnerClass() {
    return innerClass;
  }

  public String getOuterClass() {
    return outerClass;
  }

  public String getShortName() {
    return name;
  }

  public int getAccessFlags() {
    return accessFlags;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("[inner=").append(innerClass).append(", outer=").append(outerClass).append(", name=").append(name).append(",flags=")
			.append(accessFlags).append("]").toString();
  }
}
