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

public interface ExprSwitch extends soot.util.Switch {
  abstract void caseAddExpr(AddExpr v);

  abstract void caseAndExpr(AndExpr v);

  abstract void caseCmpExpr(CmpExpr v);

  abstract void caseCmpgExpr(CmpgExpr v);

  abstract void caseCmplExpr(CmplExpr v);

  abstract void caseDivExpr(DivExpr v);

  abstract void caseEqExpr(EqExpr v);

  abstract void caseNeExpr(NeExpr v);

  abstract void caseGeExpr(GeExpr v);

  abstract void caseGtExpr(GtExpr v);

  abstract void caseLeExpr(LeExpr v);

  abstract void caseLtExpr(LtExpr v);

  abstract void caseMulExpr(MulExpr v);

  abstract void caseOrExpr(OrExpr v);

  abstract void caseRemExpr(RemExpr v);

  abstract void caseShlExpr(ShlExpr v);

  abstract void caseShrExpr(ShrExpr v);

  abstract void caseUshrExpr(UshrExpr v);

  abstract void caseSubExpr(SubExpr v);

  abstract void caseXorExpr(XorExpr v);

  abstract void caseInterfaceInvokeExpr(InterfaceInvokeExpr v);

  abstract void caseSpecialInvokeExpr(SpecialInvokeExpr v);

  abstract void caseStaticInvokeExpr(StaticInvokeExpr v);

  abstract void caseVirtualInvokeExpr(VirtualInvokeExpr v);

  abstract void caseDynamicInvokeExpr(DynamicInvokeExpr v);

  abstract void caseCastExpr(CastExpr v);

  abstract void caseInstanceOfExpr(InstanceOfExpr v);

  abstract void caseNewArrayExpr(NewArrayExpr v);

  abstract void caseNewMultiArrayExpr(NewMultiArrayExpr v);

  abstract void caseNewExpr(NewExpr v);

  abstract void caseLengthExpr(LengthExpr v);

  abstract void caseNegExpr(NegExpr v);

  abstract void defaultCase(Object obj);
}
