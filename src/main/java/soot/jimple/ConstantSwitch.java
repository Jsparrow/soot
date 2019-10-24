package soot.jimple;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 1999 Raja Vallee-Rai
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

public interface ConstantSwitch extends soot.util.Switch {
  abstract void caseDoubleConstant(DoubleConstant v);

  abstract void caseFloatConstant(FloatConstant v);

  abstract void caseIntConstant(IntConstant v);

  abstract void caseLongConstant(LongConstant v);

  abstract void caseNullConstant(NullConstant v);

  abstract void caseStringConstant(StringConstant v);

  abstract void caseClassConstant(ClassConstant v);

  abstract void caseMethodHandle(MethodHandle handle);
  
  abstract void caseMethodType(MethodType type);

  abstract void defaultCase(Object object);
}
