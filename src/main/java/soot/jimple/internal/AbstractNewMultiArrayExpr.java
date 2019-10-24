package soot.jimple.internal;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 Patrick Lam
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import soot.ArrayType;
import soot.Type;
import soot.Unit;
import soot.UnitPrinter;
import soot.Value;
import soot.ValueBox;
import soot.baf.Baf;
import soot.jimple.ConvertToBaf;
import soot.jimple.ExprSwitch;
import soot.jimple.Jimple;
import soot.jimple.JimpleToBafContext;
import soot.jimple.NewMultiArrayExpr;
import soot.util.Switch;

@SuppressWarnings("serial")
public abstract class AbstractNewMultiArrayExpr implements NewMultiArrayExpr, ConvertToBaf {
  ArrayType baseType;
  protected final ValueBox[] sizeBoxes;

  protected AbstractNewMultiArrayExpr(ArrayType type, ValueBox[] sizeBoxes) {
    this.baseType = type;
    this.sizeBoxes = sizeBoxes;
  }

@Override
public abstract Object clone();

@Override
public boolean equivTo(Object o) {
    if (!(o instanceof AbstractNewMultiArrayExpr)) {
		return false;
	}
	AbstractNewMultiArrayExpr ae = (AbstractNewMultiArrayExpr) o;
	if (!baseType.equals(ae.baseType) || sizeBoxes.length != ae.sizeBoxes.length) {
        return false;
      }
	return true;
  }

/** Returns a hash code for this object, consistent with structural equality. */
  @Override
public int equivHashCode() {
    return baseType.hashCode();
  }

@Override
public String toString() {
    StringBuilder buffer = new StringBuilder();

    Type t = baseType.baseType;
    buffer.append(new StringBuilder().append(Jimple.NEWMULTIARRAY).append(" (").append(t.toString()).append(")").toString());

    for (ValueBox element : sizeBoxes) {
      buffer.append(new StringBuilder().append("[").append(element.getValue().toString()).append("]").toString());
    }

    for (int i = 0; i < baseType.numDimensions - sizeBoxes.length; i++) {
      buffer.append("[]");
    }

    return buffer.toString();
  }

@Override
public void toString(UnitPrinter up) {
    Type t = baseType.baseType;

    up.literal(Jimple.NEWMULTIARRAY);
    up.literal(" (");
    up.type(t);
    up.literal(")");

    for (ValueBox element : sizeBoxes) {
      up.literal("[");
      element.toString(up);
      up.literal("]");
    }

    for (int i = 0; i < baseType.numDimensions - sizeBoxes.length; i++) {
      up.literal("[]");
    }
  }

@Override
public ArrayType getBaseType() {
    return baseType;
  }

@Override
public void setBaseType(ArrayType baseType) {
    this.baseType = baseType;
  }

@Override
public ValueBox getSizeBox(int index) {
    return sizeBoxes[index];
  }

@Override
public int getSizeCount() {
    return sizeBoxes.length;
  }

@Override
public Value getSize(int index) {
    return sizeBoxes[index].getValue();
  }

@Override
public List<Value> getSizes() {
    List<Value> toReturn = new ArrayList<>();

    for (ValueBox element : sizeBoxes) {
      toReturn.add(element.getValue());
    }

    return toReturn;
  }

@Override
public void setSize(int index, Value size) {
    sizeBoxes[index].setValue(size);
  }

@Override
  public final List<ValueBox> getUseBoxes() {
    List<ValueBox> list = new ArrayList<>();
    Collections.addAll(list, sizeBoxes);

    for (ValueBox element : sizeBoxes) {
      list.addAll(element.getValue().getUseBoxes());
    }

    return list;
  }

@Override
public Type getType() {
    return baseType;
  }

@Override
public void apply(Switch sw) {
    ((ExprSwitch) sw).caseNewMultiArrayExpr(this);
  }

@Override
public void convertToBaf(JimpleToBafContext context, List<Unit> out) {
    List<Value> sizes = getSizes();

    sizes.forEach(size -> ((ConvertToBaf) (size)).convertToBaf(context, out));

    Unit u = Baf.v().newNewMultiArrayInst(getBaseType(), sizes.size());
    out.add(u);
    u.addAllTagsOf(context.getCurrentUnit());
  }
}
