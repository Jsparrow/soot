package soot.grimp;

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

import soot.jimple.AddExpr;
import soot.jimple.AndExpr;
import soot.jimple.CastExpr;
import soot.jimple.CmpExpr;
import soot.jimple.CmpgExpr;
import soot.jimple.CmplExpr;
import soot.jimple.DivExpr;
import soot.jimple.EqExpr;
import soot.jimple.ExprSwitch;
import soot.jimple.GeExpr;
import soot.jimple.GtExpr;
import soot.jimple.InstanceOfExpr;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.LeExpr;
import soot.jimple.LengthExpr;
import soot.jimple.LtExpr;
import soot.jimple.MulExpr;
import soot.jimple.NeExpr;
import soot.jimple.NegExpr;
import soot.jimple.NewArrayExpr;
import soot.jimple.NewExpr;
import soot.jimple.NewMultiArrayExpr;
import soot.jimple.OrExpr;
import soot.jimple.RemExpr;
import soot.jimple.ShlExpr;
import soot.jimple.ShrExpr;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.SubExpr;
import soot.jimple.UshrExpr;
import soot.jimple.VirtualInvokeExpr;
import soot.jimple.XorExpr;

public interface GrimpExprSwitch extends ExprSwitch {
  @Override
abstract void caseAddExpr(AddExpr v);

  @Override
abstract void caseAndExpr(AndExpr v);

  @Override
abstract void caseCmpExpr(CmpExpr v);

  @Override
abstract void caseCmpgExpr(CmpgExpr v);

  @Override
abstract void caseCmplExpr(CmplExpr v);

  @Override
abstract void caseDivExpr(DivExpr v);

  @Override
abstract void caseEqExpr(EqExpr v);

  @Override
abstract void caseNeExpr(NeExpr v);

  @Override
abstract void caseGeExpr(GeExpr v);

  @Override
abstract void caseGtExpr(GtExpr v);

  @Override
abstract void caseLeExpr(LeExpr v);

  @Override
abstract void caseLtExpr(LtExpr v);

  @Override
abstract void caseMulExpr(MulExpr v);

  @Override
abstract void caseOrExpr(OrExpr v);

  @Override
abstract void caseRemExpr(RemExpr v);

  @Override
abstract void caseShlExpr(ShlExpr v);

  @Override
abstract void caseShrExpr(ShrExpr v);

  @Override
abstract void caseUshrExpr(UshrExpr v);

  @Override
abstract void caseSubExpr(SubExpr v);

  @Override
abstract void caseXorExpr(XorExpr v);

  @Override
abstract void caseInterfaceInvokeExpr(InterfaceInvokeExpr v);

  @Override
abstract void caseSpecialInvokeExpr(SpecialInvokeExpr v);

  @Override
abstract void caseStaticInvokeExpr(StaticInvokeExpr v);

  @Override
abstract void caseVirtualInvokeExpr(VirtualInvokeExpr v);

  abstract void caseNewInvokeExpr(NewInvokeExpr v);

  @Override
abstract void caseCastExpr(CastExpr v);

  @Override
abstract void caseInstanceOfExpr(InstanceOfExpr v);

  @Override
abstract void caseNewArrayExpr(NewArrayExpr v);

  @Override
abstract void caseNewMultiArrayExpr(NewMultiArrayExpr v);

  @Override
abstract void caseNewExpr(NewExpr v);

  @Override
abstract void caseLengthExpr(LengthExpr v);

  @Override
abstract void caseNegExpr(NegExpr v);

  @Override
abstract void defaultCase(Object obj);
}
