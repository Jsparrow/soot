package soot.baf.internal;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 Patrick Lam, Patrick Pominville and Raja Vallee-Rai
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

import java.util.Iterator;

import soot.AbstractJasminClass;
import soot.SootMethod;
import soot.SootMethodRef;
import soot.Type;
import soot.UnitPrinter;
import soot.VoidType;

abstract class AbstractInvokeInst extends AbstractInst {
  SootMethodRef methodRef;

  public SootMethodRef getMethodRef() {
    return methodRef;
  }

  public SootMethod getMethod() {
    return methodRef.resolve();
  }

  public Type getType() {
    return methodRef.returnType();
  }

  @Override
public String toString() {
    return getName() + getParameters();
  }

  @Override
public abstract String getName();

  @Override
String getParameters() {
    return " " + methodRef.getSignature();
  }

  @Override
protected void getParameters(UnitPrinter up) {
    up.literal(" ");
    up.methodRef(methodRef);
  }

  @Override
public int getInCount() {
    return getMethodRef().parameterTypes().size();
  }

  @Override
public int getOutCount() {
    if (getMethodRef().returnType() instanceof VoidType) {
      return 0;
    } else {
      return 1;
    }
  }

  @Override
public int getInMachineCount() {
    int count = 0;

    Iterator it = getMethodRef().parameterTypes().iterator();
    while (it.hasNext()) {
      count += AbstractJasminClass.sizeOfType((Type) it.next());
    }
    return count;
  }

  @Override
public int getOutMachineCount() {
    if (getMethodRef().returnType() instanceof VoidType) {
      return 0;
    } else {
      return AbstractJasminClass.sizeOfType(getMethodRef().returnType());
    }
  }

  @Override
public boolean containsInvokeExpr() {
    return true;
  }

}
