package soot.jimple.spark.sets;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2002 Ondrej Lhotak
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

import java.util.Collections;
import java.util.Set;

import soot.G;
import soot.PointsToSet;
import soot.Singletons;
import soot.Type;
import soot.jimple.ClassConstant;
import soot.jimple.spark.pag.Node;

/**
 * Implementation of an empty, immutable points-to set.
 * 
 * @author Ondrej Lhotak
 */
public class EmptyPointsToSet extends PointsToSetInternal {
  public EmptyPointsToSet(Singletons.Global g) {
    super(null);
  }

  public EmptyPointsToSet(Singletons.Global g, Type type) {
    super(type);
  }

public static EmptyPointsToSet v() {
    return G.v().soot_jimple_spark_sets_EmptyPointsToSet();
  }

/** Returns true if this set contains no run-time objects. */
  @Override
public boolean isEmpty() {
    return true;
  }

/** Returns true if this set shares some objects with other. */
  @Override
public boolean hasNonEmptyIntersection(PointsToSet other) {
    return false;
  }

/** Set of all possible run-time types of objects in the set. */
  @Override
public Set<Type> possibleTypes() {
    return Collections.emptySet();
  }

/**
   * Adds contents of other into this set, returns true if this set changed.
   */
  @Override
public boolean addAll(PointsToSetInternal other, PointsToSetInternal exclude) {
    throw new RuntimeException("can't add into empty immutable set");
  }

/** Calls v's visit method on all nodes in this set. */
  @Override
public boolean forall(P2SetVisitor v) {
    return false;
  }

/** Adds n to this set, returns true if n was not already in this set. */
  @Override
public boolean add(Node n) {
    throw new RuntimeException("can't add into empty immutable set");
  }

/** Returns true iff the set contains n. */
  @Override
public boolean contains(Node n) {
    return false;
  }

@Override
public Set<String> possibleStringConstants() {
    return Collections.emptySet();
  }

@Override
public Set<ClassConstant> possibleClassConstants() {
    return Collections.emptySet();
  }

/**
   * {@inheritDoc}
   */
  @Override
public String toString() {
    return "{}";
  }

}
