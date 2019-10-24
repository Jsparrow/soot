package soot.grimp.internal;

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

import soot.UnitPrinter;
import soot.Value;
import soot.grimp.Grimp;
import soot.grimp.Precedence;
import soot.grimp.PrecedenceTest;
import soot.jimple.internal.JArrayRef;

public class GArrayRef extends JArrayRef implements Precedence {
  public GArrayRef(Value base, Value index) {
    super(Grimp.v().newObjExprBox(base), Grimp.v().newExprBox(index));
  }

  @Override
public int getPrecedence() {
    return 950;
  }

  private String toString(Value op1, String leftOp, String rightOp) {
    if (op1 instanceof Precedence && ((Precedence) op1).getPrecedence() < getPrecedence()) {
      leftOp = new StringBuilder().append("(").append(leftOp).append(")").toString();
    }

    return new StringBuilder().append(leftOp).append("[").append(rightOp).append("]").toString();
  }

  @Override
public void toString(UnitPrinter up) {
    if (PrecedenceTest.needsBrackets(baseBox, this)) {
      up.literal("(");
    }
    baseBox.toString(up);
    if (PrecedenceTest.needsBrackets(baseBox, this)) {
      up.literal(")");
    }
    up.literal("[");
    indexBox.toString(up);
    up.literal("]");
  }

  @Override
public String toString() {
    Value op1 = getBase();
	Value op2 = getIndex();
    String leftOp = op1.toString();
	String rightOp = op2.toString();

    return toString(op1, leftOp, rightOp);
  }

  @Override
public Object clone() {
    return new GArrayRef(Grimp.cloneIfNecessary(getBase()), Grimp.cloneIfNecessary(getIndex()));
  }

}
