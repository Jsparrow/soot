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

import soot.Unit;
import soot.UnitPrinter;
import soot.jimple.internal.AbstractStmt;

public class PlaceholderStmt extends AbstractStmt {
  private Unit source;

  PlaceholderStmt(Unit source) {
    this.source = source;
  }

@Override
public String toString() {
    return new StringBuilder().append("<placeholder: ").append(source.toString()).append(">").toString();
  }

@Override
public void toString(UnitPrinter up) {
    up.literal("<placeholder: ");
    source.toString(up);
    up.literal(">");
  }

public Unit getSource() {
    return source;
  }

@Override
public boolean fallsThrough() {
    throw new RuntimeException();
  }

@Override
public boolean branches() {
    throw new RuntimeException();
  }

@Override
public Object clone() {
    throw new RuntimeException();
  }

}
