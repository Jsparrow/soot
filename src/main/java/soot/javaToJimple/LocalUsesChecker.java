package soot.javaToJimple;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2004 Jennifer Lhotak
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

import polyglot.ast.Node;
import polyglot.util.IdentityKey;

public class LocalUsesChecker extends polyglot.visit.NodeVisitor {

  private final ArrayList<IdentityKey> locals;
  private final ArrayList<IdentityKey> localDecls;
  private final ArrayList<Node> news;

  public LocalUsesChecker() {
    locals = new ArrayList<>();
    localDecls = new ArrayList<>();
    news = new ArrayList<>();
  }

public ArrayList<IdentityKey> getLocals() {
    return locals;
  }

public ArrayList<Node> getNews() {
    return news;
  }

public ArrayList<IdentityKey> getLocalDecls() {
    return localDecls;
  }

@Override
public polyglot.ast.Node leave(polyglot.ast.Node old, polyglot.ast.Node n, polyglot.visit.NodeVisitor visitor) {

    boolean condition = n instanceof polyglot.ast.Local && !(locals.contains(new polyglot.util.IdentityKey(((polyglot.ast.Local) n).localInstance()))) && !((polyglot.ast.Local) n).isConstant();
	if (condition) {
	  locals.add(new polyglot.util.IdentityKey(((polyglot.ast.Local) n).localInstance()));
	}

    if (n instanceof polyglot.ast.LocalDecl) {
      localDecls.add(new polyglot.util.IdentityKey(((polyglot.ast.LocalDecl) n).localInstance()));
    }

    if (n instanceof polyglot.ast.Formal) {
      localDecls.add(new polyglot.util.IdentityKey(((polyglot.ast.Formal) n).localInstance()));
    }

    if (n instanceof polyglot.ast.New) {
      news.add(n);
    }
    return n;
  }
}
