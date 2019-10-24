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

import soot.Type;
import soot.Value;
import soot.baf.Baf;
import soot.jimple.CmpgExpr;
import soot.jimple.ExprSwitch;
import soot.jimple.Jimple;
import soot.util.Switch;

public class JCmpgExpr extends AbstractJimpleIntBinopExpr implements CmpgExpr {
  public JCmpgExpr(Value op1, Value op2) {
    super(op1, op2);
  }

  @Override
public final String getSymbol() {
    return new StringBuilder().append(" ").append(Jimple.CMPG).append(" ").toString();
  }

  @Override
public void apply(Switch sw) {
    ((ExprSwitch) sw).caseCmpgExpr(this);
  }

  @Override
Object makeBafInst(Type opType) {
    return Baf.v().newCmpgInst(this.getOp1().getType());
  }

  @Override
public Object clone() {
    return new JCmpgExpr(Jimple.cloneIfNecessary(getOp1()), Jimple.cloneIfNecessary(getOp2()));
  }

}
