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

import java.util.ArrayList;
import java.util.List;

import soot.Type;
import soot.baf.Baf;
import soot.baf.Dup1_x2Inst;
import soot.baf.InstSwitch;
import soot.util.Switch;

public class BDup1_x2Inst extends BDupInst implements Dup1_x2Inst {
  private final Type mOpType;
  private final Type mUnder1Type;
  private final Type mUnder2Type;

  public BDup1_x2Inst(Type aOpType, Type aUnder1Type, Type aUnder2Type) {
    mOpType = Baf.getDescriptorTypeOf(aOpType);
    mUnder1Type = Baf.getDescriptorTypeOf(aUnder1Type);
    mUnder2Type = Baf.getDescriptorTypeOf(aUnder2Type);
  }

  @Override
public Type getOp1Type() {
    return mOpType;
  }

  @Override
public Type getUnder1Type() {
    return mUnder1Type;
  }

  @Override
public Type getUnder2Type() {
    return mUnder2Type;
  }

  @Override
public List<Type> getOpTypes() {
    List<Type> res = new ArrayList<>();
    res.add(mOpType);
    return res;
  }

  @Override
public List<Type> getUnderTypes() {
    List<Type> res = new ArrayList<>();
    res.add(mUnder1Type);
    res.add(mUnder2Type);
    return res;
  }

  @Override
public final String getName() {
    return "dup1_x2";
  }

  @Override
public void apply(Switch sw) {
    ((InstSwitch) sw).caseDup1_x2Inst(this);
  }

  @Override
public String toString() {
    return new StringBuilder().append("dup1_x2.").append(Baf.bafDescriptorOf(mOpType)).append("_").append(Baf.bafDescriptorOf(mUnder1Type)).append(".").append(Baf.bafDescriptorOf(mUnder2Type))
			.toString();
  }

}
