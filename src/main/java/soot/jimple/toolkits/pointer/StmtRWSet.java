package soot.jimple.toolkits.pointer;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2003 Ondrej Lhotak
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
import java.util.HashSet;
import java.util.Set;

import soot.PointsToSet;
import soot.SootField;

/** Represents the read or write set of a statement. */
public class StmtRWSet extends RWSet {
  protected Object field;
  protected PointsToSet base;
  protected boolean callsNative = false;

  @Override
public String toString() {
    return new StringBuilder().append("[Field: ").append(field).append(base).append("]\n").toString();
  }

  @Override
public int size() {
    Set globals = getGlobals();
    Set fields = getFields();
    if (globals == null) {
      if (fields == null) {
        return 0;
      } else {
        return fields.size();
      }
    } else {
      if (fields == null) {
        return globals.size();
      } else {
        return globals.size() + fields.size();
      }
    }
  }

  @Override
public boolean getCallsNative() {
    return callsNative;
  }

  @Override
public boolean setCallsNative() {
    boolean ret = !callsNative;
    callsNative = true;
    return ret;
  }

  /** Returns an iterator over any globals read/written. */
  @Override
public Set getGlobals() {
    if (base != null) {
		return Collections.EMPTY_SET;
	}
	HashSet ret = new HashSet();
	ret.add(field);
	return ret;
  }

  /** Returns an iterator over any fields read/written. */
  @Override
public Set getFields() {
    if (base == null) {
		return Collections.EMPTY_SET;
	}
	HashSet ret = new HashSet();
	ret.add(field);
	return ret;
  }

  /** Returns a set of base objects whose field f is read/written. */
  @Override
public PointsToSet getBaseForField(Object f) {
    if (field.equals(f)) {
      return base;
    }
    return null;
  }

  @Override
public boolean hasNonEmptyIntersection(RWSet other) {
    if (field == null) {
      return false;
    }
    if (other instanceof StmtRWSet) {
      StmtRWSet o = (StmtRWSet) other;
      if (!field.equals(o.field)) {
        return false;
      }
      if (base == null) {
        return o.base == null;
      }
      return Union.hasNonEmptyIntersection(base, o.base);
    } else if (other instanceof MethodRWSet) {
      if (base == null) {
        return other.getGlobals().contains(field);
      }
      return Union.hasNonEmptyIntersection(base, other.getBaseForField(field));
    } else {
      return other.hasNonEmptyIntersection(this);
    }
  }

  /** Adds the RWSet other into this set. */
  @Override
public boolean union(RWSet other) {
    throw new RuntimeException("Can't do that");
  }

  @Override
public boolean addGlobal(SootField global) {
    if (field != null || base != null) {
      throw new RuntimeException("Can't do that");
    }
    field = global;
    return true;
  }

  @Override
public boolean addFieldRef(PointsToSet otherBase, Object field) {
    if (this.field != null || base != null) {
      throw new RuntimeException("Can't do that");
    }
    this.field = field;
    base = otherBase;
    return true;
  }

  @Override
public boolean isEquivTo(RWSet other) {
    if (!(other instanceof StmtRWSet)) {
      return false;
    }
    StmtRWSet o = (StmtRWSet) other;
    if (callsNative != o.callsNative) {
      return false;
    }
    if (!field.equals(o.field)) {
      return false;
    }
    if (base instanceof FullObjectSet && o.base instanceof FullObjectSet) {
      return true;
    }
    if (base != o.base) {
      return false;
    }
    return true;
  }
}
