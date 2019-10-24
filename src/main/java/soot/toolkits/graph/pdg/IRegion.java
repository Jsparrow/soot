package soot.toolkits.graph.pdg;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 - 2010 Hossein Sadat-Mohtasham
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

import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.UnitGraph;

/**
 * This interface represents a region of control dependence in the control flow graph. There are different kinds of region
 * representations that may slightly differ in definition or implementation; here is an interface that is expected to be
 * supported by all these different regions.
 *
 *
 * @author Hossein Sadat-Mohtasham Jan 2009
 */

public interface IRegion {

  SootMethod getSootMethod();

  SootClass getSootClass();

  UnitGraph getUnitGraph();

  List<Unit> getUnits();

  List<Unit> getUnits(Unit from, Unit to);

  List<Block> getBlocks();

  Unit getLast();

  Unit getFirst();

  int getID();

  boolean occursBefore(Unit u1, Unit u2);

  void setParent(IRegion pr);

  IRegion getParent();

  void addChildRegion(IRegion chr);

  List<IRegion> getChildRegions();

}
