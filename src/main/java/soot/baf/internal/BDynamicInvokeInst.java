package soot.baf.internal;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 Patrick Lam, Patrick Pominville and Raja Vallee-Rai
 * Copyright (C) 2004 Ondrej Lhotak
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

import java.util.List;

import soot.SootMethod;
import soot.SootMethodRef;
import soot.UnitPrinter;
import soot.Value;
import soot.VoidType;
import soot.baf.DynamicInvokeInst;
import soot.baf.InstSwitch;
import soot.jimple.Jimple;
import soot.util.Switch;

@SuppressWarnings({ "serial", "unchecked" })
public class BDynamicInvokeInst extends AbstractInvokeInst implements DynamicInvokeInst {
  protected final SootMethodRef bsmRef;
  private final List<Value> bsmArgs;
  protected int tag;

  public BDynamicInvokeInst(SootMethodRef bsmMethodRef, List<Value> bsmArgs, SootMethodRef methodRef, int tag) {
    this.bsmRef = bsmMethodRef;
    this.bsmArgs = bsmArgs;
    this.methodRef = methodRef;
    this.tag = tag;
  }

  @Override
public int getInCount() {
    return methodRef.parameterTypes().size();

  }

  @Override
public Object clone() {
    return new BDynamicInvokeInst(bsmRef, bsmArgs, methodRef, tag);
  }

  @Override
public int getOutCount() {
    if (methodRef.returnType() instanceof VoidType) {
      return 0;
    } else {
      return 1;
    }
  }

  @Override
public SootMethodRef getBootstrapMethodRef() {
    return bsmRef;
  }

  @Override
public List<Value> getBootstrapArgs() {
    return bsmArgs;
  }

  @Override
public String getName() {
    return "dynamicinvoke";
  }

  @Override
public void apply(Switch sw) {
    ((InstSwitch) sw).caseDynamicInvokeInst(this);
  }

  @Override
public String toString() {
    StringBuilder buffer = new StringBuilder();

    buffer.append(Jimple.DYNAMICINVOKE);
    buffer.append(" \"");
    buffer.append(methodRef.name()); // quoted method name (can be any UTF8 string)
    buffer.append("\" <");
    buffer
        .append(SootMethod.getSubSignature(""/* no method name here */, methodRef.parameterTypes(), methodRef.returnType()));
    buffer.append(">");
    buffer.append(bsmRef.getSignature());
    buffer.append("(");
    for (int i = 0; i < bsmArgs.size(); i++) {
      if (i != 0) {
        buffer.append(", ");
      }

      buffer.append(bsmArgs.get(i).toString());
    }
    buffer.append(")");

    return buffer.toString();
  }

  @Override
public void toString(UnitPrinter up) {
    up.literal(Jimple.DYNAMICINVOKE);
    up.literal(new StringBuilder().append(" \"").append(methodRef.name()).append("\" <").append(SootMethod.getSubSignature(""/* no method name here */, methodRef.parameterTypes(), methodRef.returnType())).append("> ").toString());
    up.methodRef(bsmRef);
    up.literal("(");

    for (int i = 0; i < bsmArgs.size(); i++) {
      if (i != 0) {
        up.literal(", ");
      }

      bsmArgs.get(i).toString(up);
    }

    up.literal(")");
  }

  @Override
  public int getHandleTag() {
    return tag;
  }
}
