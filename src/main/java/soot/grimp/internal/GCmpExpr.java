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

import soot.Value;
import soot.grimp.Grimp;
import soot.jimple.CmpExpr;
import soot.jimple.ExprSwitch;
import soot.util.Switch;

public class GCmpExpr extends AbstractGrimpIntBinopExpr implements CmpExpr {
  public GCmpExpr(Value op1, Value op2) {
    super(op1, op2);
  }

  @Override
public final String getSymbol() {
    return " cmp ";
  }

  @Override
public final int getPrecedence() {
    return 550;
  }

  @Override
public void apply(Switch sw) {
    ((ExprSwitch) sw).caseCmpExpr(this);
  }

  @Override
public Object clone() {
    return new GCmpExpr(Grimp.cloneIfNecessary(getOp1()), Grimp.cloneIfNecessary(getOp2()));
  }

}
