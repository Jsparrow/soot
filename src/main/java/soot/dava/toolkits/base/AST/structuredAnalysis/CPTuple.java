package soot.dava.toolkits.base.AST.structuredAnalysis;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2018 Raja Vallée-Rai and others
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

import soot.dava.DavaFlowAnalysisException;

/********** START LOCAL CLASS DECLARATION *******************/
public class CPTuple {
  private String sootClass; // hold the name of the class to which the val belongs .... needed for interprocedural constant
                            // Fields info

  /*
   *
   */
  private CPVariable variable;

  // Double Float Long Boolean Integer
  private Object constant; // the known constant value for the local or field

  /*
   * false means not top true mean TOP
   */
  private Boolean TOP = Boolean.valueOf(false);

  public CPTuple(String sootClass, CPVariable variable, Object constant) {

    if (!(constant instanceof Float || constant instanceof Double || constant instanceof Long || constant instanceof Boolean
        || constant instanceof Integer)) {
      throw new DavaFlowAnalysisException(
          "Third argument of VariableValuePair not an acceptable constant value...report to developer");
    }

    this.sootClass = sootClass;
    this.variable = variable;
    this.constant = constant;
    TOP = Boolean.valueOf(false);
  }

public CPTuple(String sootClass, CPVariable variable, boolean top) {
    this.sootClass = sootClass;
    this.variable = variable;

    // notice we dont really care whether the argument top was true or false
    setTop();
  }

/*
   * Dont care about className and variable but the CONSTANT VALUE HAS TO BE A NEW ONE otherwise the clone of the flowset
   * keeps pointing to the same bloody constant value
   */
  @Override
public CPTuple clone() {
    if (isTop()) {
      return new CPTuple(sootClass, variable, true);
    } else if (isValueADouble()) {
      return new CPTuple(sootClass, variable, Double.valueOf(((Double) constant).doubleValue()));
    } else if (isValueAFloat()) {
      return new CPTuple(sootClass, variable, Float.valueOf(((Float) constant).floatValue()));
    } else if (isValueALong()) {
      return new CPTuple(sootClass, variable, Long.valueOf(((Long) constant).longValue()));
    } else if (isValueABoolean()) {
      return new CPTuple(sootClass, variable, Boolean.valueOf(((Boolean) constant).booleanValue()));
    } else if (isValueAInteger()) {
      return new CPTuple(sootClass, variable, Integer.valueOf(((Integer) constant).intValue()));
    } else {
      throw new RuntimeException("illegal Constant Type...report to developer" + constant);
    }
  }

public boolean containsLocal() {
    return variable.containsLocal();
  }

public boolean containsField() {
    return variable.containsSootField();
  }

/*
   * If TOP is non null then that means it is set to TOP
   */
  public boolean isTop() {
    return TOP.booleanValue();
  }

public void setTop() {
    constant = null;
    TOP = Boolean.valueOf(true);
  }

public boolean isValueADouble() {
    return (constant instanceof Double);
  }

public boolean isValueAFloat() {
    return (constant instanceof Float);
  }

public boolean isValueALong() {
    return (constant instanceof Long);
  }

public boolean isValueABoolean() {
    return (constant instanceof Boolean);
  }

public boolean isValueAInteger() {
    return (constant instanceof Integer);
  }

public Object getValue() {
    return constant;
  }

public void setValue(Object constant) {
    // System.out.println("here currently valued as"+this.constant);
    if (!(constant instanceof Float || constant instanceof Double || constant instanceof Long || constant instanceof Boolean
        || constant instanceof Integer)) {
      throw new DavaFlowAnalysisException("argument to setValue not an acceptable constant value...report to developer");
    }

    this.constant = constant;
    TOP = Boolean.valueOf(false);
  }

public String getSootClassName() {
    return sootClass;
  }

public CPVariable getVariable() {
    return variable;
  }

@Override
public boolean equals(Object other) {
    if (other instanceof CPTuple) {
      CPTuple var = (CPTuple) other;

      // if both are top thats all right
      if (sootClass.equals(var.getSootClassName()) && variable.equals(var.getVariable()) && isTop() & var.isTop()) {
        return true;
      }

      // if any one is top thats no good
      if (isTop() || var.isTop()) {
        return false;
      }

      if (sootClass.equals(var.getSootClassName()) && variable.equals(var.getVariable())
          && constant.equals(var.getValue())) {
        // System.out.println("constant value "+constant.toString() + " is equal to "+ var.toString());
        return true;
      }
    }
    return false;
  }

@Override
public String toString() {
    StringBuilder b = new StringBuilder();
    if (isTop()) {
      b.append(new StringBuilder().append("<").append(sootClass).append(", ").append(variable.toString()).append(", TOP>").toString());
    } else {
      b.append(new StringBuilder().append("<").append(sootClass).append(", ").append(variable.toString()).append(",").append(constant.toString()).append(">")
			.toString());
    }
    return b.toString();
  }
}

/********** END LOCAL CLASS DECLARATION *******************/
