package soot.tools;

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

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.PackManager;
import soot.PrimType;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.FieldRef;
import soot.jimple.InvokeExpr;
import soot.jimple.StaticFieldRef;
import soot.jimple.Stmt;

public class BadFields extends SceneTransformer {
  private static final Logger logger = LoggerFactory.getLogger(BadFields.class);
private SootClass lastClass;
private SootClass currentClass;

public static void main(String[] args) {
    PackManager.v().getPack("cg").add(new Transform("cg.badfields", new BadFields()));
    soot.Main.main(args);
  }

@Override
protected void internalTransform(String phaseName, Map<String, String> options) {
    lastClass = null;

    for (Iterator<SootClass> clIt = Scene.v().getApplicationClasses().iterator(); clIt.hasNext();) {

      final SootClass cl = clIt.next();
      currentClass = cl;
      handleClass(cl);
      for (Iterator<SootMethod> it = cl.methodIterator(); it.hasNext();) {
        handleMethod(it.next());
      }
    }
    Scene.v().setCallGraph(Scene.v().internalMakeCallGraph());
  }

private void handleClass(SootClass cl) {
    for (Iterator<SootField> fIt = cl.getFields().iterator(); fIt.hasNext();) {
      final SootField f = fIt.next();
      if (!f.isStatic()) {
        continue;
      }
      String typeName = f.getType().toString();
      if ("java.lang.Class".equals(typeName)) {
        continue;
      }
      if (f.isFinal()) {
        if (f.getType() instanceof PrimType) {
          continue;
        }
        if ("java.io.PrintStream".equals(typeName)) {
          continue;
        }
        if ("java.lang.String".equals(typeName)) {
          continue;
        }
        if ("java.lang.Object".equals(typeName)) {
          continue;
        }
        if ("java.lang.Integer".equals(typeName)) {
          continue;
        }
        if ("java.lang.Boolean".equals(typeName)) {
          continue;
        }
      }
      warn("Bad field " + f);
    }
  }

private void warn(String warning) {
    if (lastClass != currentClass) {
      logger.debug(new StringBuilder().append("").append("In class ").append(currentClass).toString());
    }
    lastClass = currentClass;
    logger.debug(new StringBuilder().append("").append("  ").append(warning).toString());
  }

private void handleMethod(SootMethod m) {
    if (!m.isConcrete()) {
      return;
    }
    for (Iterator<ValueBox> bIt = m.retrieveActiveBody().getUseAndDefBoxes().iterator(); bIt.hasNext();) {
      final ValueBox b = bIt.next();
      Value v = b.getValue();
      if (!(v instanceof StaticFieldRef)) {
        continue;
      }
      StaticFieldRef sfr = (StaticFieldRef) v;
      SootField f = sfr.getField();
      if (!"java.lang.System".equals(f.getDeclaringClass().getName())) {
        continue;
      }
      if ("err".equals(f.getName())) {
        logger.debug(new StringBuilder().append("").append("Use of System.err in ").append(m).toString());
      }
      if ("out".equals(f.getName())) {
        logger.debug(new StringBuilder().append("").append("Use of System.out in ").append(m).toString());
      }
    }
    for (Iterator<Unit> sIt = m.getActiveBody().getUnits().iterator(); sIt.hasNext();) {
      final Stmt s = (Stmt) sIt.next();
      if (!s.containsInvokeExpr()) {
        continue;
      }
      InvokeExpr ie = s.getInvokeExpr();
      SootMethod target = ie.getMethod();
      if ("java.lang.System".equals(target.getDeclaringClass().getName()) && "exit".equals(target.getName())) {
        warn(new StringBuilder().append("").append(m).append(" calls System.exit").toString());
      }
    }
    if ("<clinit>".equals(m.getName())) {
      for (Iterator<Unit> sIt = m.getActiveBody().getUnits().iterator(); sIt.hasNext();) {
        final Stmt s = (Stmt) sIt.next();
        for (Iterator<ValueBox> bIt = s.getUseBoxes().iterator(); bIt.hasNext();) {
          final ValueBox b = bIt.next();
          Value v = b.getValue();
          if (v instanceof FieldRef) {
            warn(new StringBuilder().append(m.getName()).append(" reads field ").append(v).toString());
          }
        }
        if (!s.containsInvokeExpr()) {
          continue;
        }
        InvokeExpr ie = s.getInvokeExpr();
        SootMethod target = ie.getMethod();
        calls(target);
      }
    }
  }

private void calls(SootMethod target) {
    if ("<init>".equals(target.getName())) {
      if ("java.io.PrintStream".equals(target.getDeclaringClass().getName())) {
        return;
      }
      if ("java.lang.Boolean".equals(target.getDeclaringClass().getName())) {
        return;
      }
      if ("java.lang.Integer".equals(target.getDeclaringClass().getName())) {
        return;
      }
      if ("java.lang.String".equals(target.getDeclaringClass().getName())) {
        return;
      }
      if ("java.lang.Object".equals(target.getDeclaringClass().getName())) {
        return;
      }
    }
    if ("getProperty".equals(target.getName()) && "java.lang.System".equals(target.getDeclaringClass().getName())) {
        return;
      }
    if ("charAt".equals(target.getName()) && "java.lang.String".equals(target.getDeclaringClass().getName())) {
        return;
      }
    warn("<clinit> invokes " + target);
  }
}
