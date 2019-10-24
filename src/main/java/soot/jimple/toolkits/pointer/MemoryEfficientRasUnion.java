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

public class MemoryEfficientRasUnion extends Union {
  HashSet<PointsToSet> subsets;

  @Override
public boolean isEmpty() {
    if (subsets == null) {
      return true;
    }
    return subsets.stream().allMatch(PointsToSet::isEmpty);
  }

  @Override
public boolean hasNonEmptyIntersection(PointsToSet other) {
    if (subsets == null) {
      return true;
    }
    for (PointsToSet subset : subsets) {
      if (other instanceof Union) {
        if (other.hasNonEmptyIntersection(subset)) {
          return true;
        }
      } else {
        if (subset.hasNonEmptyIntersection(other)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
public boolean addAll(PointsToSet s) {
    boolean result;

    if (subsets == null) {
      subsets = new HashSet<>();
    }
    if (s instanceof MemoryEfficientRasUnion) {
      MemoryEfficientRasUnion meru = (MemoryEfficientRasUnion) s;
      if (meru.subsets == null || subsets.containsAll(meru.subsets)) {
        return false;
      }
      result = subsets.addAll(meru.subsets);
    } else {
      result = subsets.add(s);
    }

    return result;
  }

  @Override
public Object clone() {
    MemoryEfficientRasUnion ret = new MemoryEfficientRasUnion();
    ret.addAll(this);
    return ret;
  }

  @Override
public Set possibleTypes() {
    if (subsets == null) {
      return Collections.EMPTY_SET;
    }
    HashSet ret = new HashSet();
    subsets.forEach(subset -> ret.addAll(subset.possibleTypes()));
    return ret;
  }

  /**
   * {@inheritDoc}
   */
  @Override
public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((subsets == null) ? 0 : subsets.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final MemoryEfficientRasUnion other = (MemoryEfficientRasUnion) obj;
    if (subsets == null) {
      if (other.subsets != null) {
        return false;
      }
    } else if (!subsets.equals(other.subsets)) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
public String toString() {
    if (subsets == null) {
      return "[]";
    } else {
      return subsets.toString();
    }
  }

}
