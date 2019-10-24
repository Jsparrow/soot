package soot;

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

import java.util.Map;

/**
 * Represents a pair of a method and a context.
 * 
 * @author Ondrej Lhotak
 */
public final class MethodContext implements MethodOrMethodContext {
  private SootMethod method;
private Context context;

private MethodContext(SootMethod method, Context context) {
    this.method = method;
    this.context = context;
  }

@Override
public SootMethod method() {
    return method;
  }

@Override
public Context context() {
    return context;
  }

@Override
public int hashCode() {
    return method.hashCode() + context.hashCode();
  }

@Override
public boolean equals(Object o) {
    if (!(o instanceof MethodContext)) {
		return false;
	}
	MethodContext other = (MethodContext) o;
	return method.equals(other.method) && context.equals(other.context);
  }

public static MethodOrMethodContext v(SootMethod method, Context context) {
    if (context == null) {
      return method;
    }
    MethodContext probe = new MethodContext(method, context);
    Map<MethodContext, MethodContext> map = G.v().MethodContext_map;
    MethodContext ret = map.get(probe);
    if (ret != null) {
		return ret;
	}
	map.put(probe, probe);
	return probe;
  }

@Override
public String toString() {
    return new StringBuilder().append("Method ").append(method).append(" in context ").append(context).toString();
  }
}
