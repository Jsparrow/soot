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
import java.util.Collections;
import java.util.List;

import soot.Unit;
import soot.UnitBox;
import soot.UnitPrinter;
import soot.baf.Baf;
import soot.baf.InstSwitch;
import soot.baf.LookupSwitchInst;
import soot.jimple.Constant;
import soot.util.Switch;

public class BLookupSwitchInst extends AbstractInst implements LookupSwitchInst {
  UnitBox defaultTargetBox;
  List lookupValues;
  UnitBox[] targetBoxes;
  List unitBoxes;

  public BLookupSwitchInst(Unit defaultTarget, List lookupValues, List targets) {
    this.defaultTargetBox = Baf.v().newInstBox(defaultTarget);

    this.targetBoxes = new UnitBox[targets.size()];

    for (int i = 0; i < targetBoxes.length; i++) {
      this.targetBoxes[i] = Baf.v().newInstBox((Unit) targets.get(i));
    }

    this.lookupValues = new ArrayList();
    this.lookupValues.addAll(lookupValues);

    // Build up unitBoxes
    {
      unitBoxes = new ArrayList();

      for (UnitBox element : targetBoxes) {
        unitBoxes.add(element);
      }

      unitBoxes.add(defaultTargetBox);
      unitBoxes = Collections.unmodifiableList(unitBoxes);
    }
  }

  @Override
public Object clone() {
    List list = new ArrayList();
    for (UnitBox element : targetBoxes) {

      list.add(element.getUnit());
    }

    return new BLookupSwitchInst(defaultTargetBox.getUnit(), lookupValues, list);
  }

  @Override
public int getInCount() {
    return 1;
  }

  @Override
public int getInMachineCount() {
    return 1;
  }

  @Override
public int getOutCount() {
    return 0;
  }

  @Override
public int getOutMachineCount() {
    return 0;
  }

  @Override
public Unit getDefaultTarget() {
    return defaultTargetBox.getUnit();
  }

  @Override
public void setDefaultTarget(Unit defaultTarget) {
    defaultTargetBox.setUnit(defaultTarget);
  }

  @Override
public UnitBox getDefaultTargetBox() {
    return defaultTargetBox;
  }

  @Override
public void setLookupValues(List lookupValues) {
    this.lookupValues = new ArrayList();
    this.lookupValues.addAll(lookupValues);
  }

  @Override
public void setLookupValue(int index, int value) {
    this.lookupValues.set(index, Integer.valueOf(value));
  }

  @Override
public int getLookupValue(int index) {
    return ((Integer) lookupValues.get(index)).intValue();
  }

  @Override
public List getLookupValues() {
    return Collections.unmodifiableList(lookupValues);
  }

  @Override
public int getTargetCount() {
    return targetBoxes.length;
  }

  @Override
public Unit getTarget(int index) {
    return targetBoxes[index].getUnit();
  }

  @Override
public void setTarget(int index, Unit target) {
    targetBoxes[index].setUnit(target);
  }

  @Override
public void setTargets(List<Unit> targets) {
    for (int i = 0; i < targets.size(); i++) {
      targetBoxes[i].setUnit(targets.get(i));
    }
  }

  @Override
public UnitBox getTargetBox(int index) {
    return targetBoxes[index];
  }

  @Override
public List getTargets() {
    List targets = new ArrayList();

    for (UnitBox element : targetBoxes) {
      targets.add(element.getUnit());
    }

    return targets;
  }

  @Override
public String getName() {
    return "lookupswitch";
  }

  @Override
public String toString() {
    StringBuilder buffer = new StringBuilder();
    String endOfLine = " ";

    buffer.append("lookupswitch" + endOfLine);

    buffer.append("{" + endOfLine);

    for (int i = 0; i < lookupValues.size(); i++) {
      buffer.append(new StringBuilder().append("    case ").append(lookupValues.get(i)).append(": goto ").append(getTarget(i)).append(";").append(endOfLine).toString());
    }

    buffer.append(new StringBuilder().append("    default: goto ").append(getDefaultTarget()).append(";").append(endOfLine).toString());
    buffer.append("}");

    return buffer.toString();
  }

  @Override
public void toString(UnitPrinter up) {
    up.literal("lookupswitch");
    up.newline();
    up.literal("{");
    up.newline();

    for (int i = 0; i < lookupValues.size(); i++) {
      up.literal("    case ");
      up.constant((Constant) lookupValues.get(i));
      up.literal(": goto ");
      targetBoxes[i].toString(up);
      up.literal(";");
      up.newline();
    }

    up.literal("    default: goto ");
    defaultTargetBox.toString(up);
    up.literal(";");
    up.newline();
    up.literal("}");
  }

  @Override
public List getUnitBoxes() {
    return unitBoxes;
  }

  @Override
public void apply(Switch sw) {
    ((InstSwitch) sw).caseLookupSwitchInst(this);
  }

  @Override
public boolean fallsThrough() {
    return false;
  }

  @Override
public boolean branches() {
    return true;
  }

}
