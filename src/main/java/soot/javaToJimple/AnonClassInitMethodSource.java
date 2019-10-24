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

import soot.PackManager;

public class AnonClassInitMethodSource extends soot.javaToJimple.PolyglotMethodSource {

  private boolean hasOuterRef;
private boolean hasQualifier;
private boolean inStaticMethod;
private boolean isSubType = false;
private soot.Type superOuterType = null;
private soot.Type thisOuterType = null;
private polyglot.types.ClassType polyglotType;
private polyglot.types.ClassType anonType;
private soot.Type outerClassType;

public void hasOuterRef(boolean b) {
    hasOuterRef = b;
  }

public boolean hasOuterRef() {
    return hasOuterRef;
  }

public void hasQualifier(boolean b) {
    hasQualifier = b;
  }

public boolean hasQualifier() {
    return hasQualifier;
  }

public void inStaticMethod(boolean b) {
    inStaticMethod = b;
  }

public boolean inStaticMethod() {
    return inStaticMethod;
  }

public void isSubType(boolean b) {
    isSubType = b;
  }

public boolean isSubType() {
    return isSubType;
  }

public void superOuterType(soot.Type t) {
    superOuterType = t;
  }

public soot.Type superOuterType() {
    return superOuterType;
  }

public void thisOuterType(soot.Type t) {
    thisOuterType = t;
  }

public soot.Type thisOuterType() {
    return thisOuterType;
  }

public void polyglotType(polyglot.types.ClassType type) {
    polyglotType = type;
  }

public polyglot.types.ClassType polyglotType() {
    return polyglotType;
  }

public void anonType(polyglot.types.ClassType type) {
    anonType = type;
  }

public polyglot.types.ClassType anonType() {
    return anonType;
  }

@Override
public soot.Body getBody(soot.SootMethod sootMethod, String phaseName) {
    AnonInitBodyBuilder aibb = new AnonInitBodyBuilder();
    soot.jimple.JimpleBody body = aibb.createBody(sootMethod);

    PackManager.v().getPack("jj").apply(body);

    return body;
  }

public soot.Type outerClassType() {
    return outerClassType;
  }

public void outerClassType(soot.Type type) {
    outerClassType = type;
  }
}
