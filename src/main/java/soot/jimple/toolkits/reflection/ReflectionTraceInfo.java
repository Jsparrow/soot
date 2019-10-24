package soot.jimple.toolkits.reflection;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2010 Eric Bodden
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.tagkit.Host;
import soot.tagkit.LineNumberTag;
import soot.tagkit.SourceLnPosTag;

public class ReflectionTraceInfo {
  private static final Logger logger = LoggerFactory.getLogger(ReflectionTraceInfo.class);

protected Map<SootMethod, Set<String>> classForNameReceivers;

protected Map<SootMethod, Set<String>> classNewInstanceReceivers;

protected Map<SootMethod, Set<String>> constructorNewInstanceReceivers;

protected Map<SootMethod, Set<String>> methodInvokeReceivers;

protected Map<SootMethod, Set<String>> fieldSetReceivers;

protected Map<SootMethod, Set<String>> fieldGetReceivers;

public ReflectionTraceInfo(String logFile) {
    classForNameReceivers = new LinkedHashMap<>();
    classNewInstanceReceivers = new LinkedHashMap<>();
    constructorNewInstanceReceivers = new LinkedHashMap<>();
    methodInvokeReceivers = new LinkedHashMap<>();
    fieldSetReceivers = new LinkedHashMap<>();
    fieldGetReceivers = new LinkedHashMap<>();

    if (logFile == null) {
      throw new InternalError("Trace based refection model enabled but no trace file given!?");
    } else {
      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
        String line;
        int lines = 0;
        Set<String> ignoredKinds = new HashSet<>();
        while ((line = reader.readLine()) != null) {
          if (line.isEmpty()) {
            continue;
          }
          String[] portions = line.split(";", -1);
          String kind = portions[0];
          String target = portions[1];
          String source = portions[2];
          int lineNumber = portions[3].isEmpty() ? -1 : Integer.parseInt(portions[3]);

          Set<SootMethod> possibleSourceMethods = inferSource(source, lineNumber);
          for (SootMethod sourceMethod : possibleSourceMethods) {
            if ("Class.forName".equals(kind)) {
              Set<String> receiverNames;
              if ((receiverNames = classForNameReceivers.get(sourceMethod)) == null) {
                classForNameReceivers.put(sourceMethod, receiverNames = new LinkedHashSet<>());
              }
              receiverNames.add(target);
            } else if ("Class.newInstance".equals(kind)) {
              Set<String> receiverNames;
              if ((receiverNames = classNewInstanceReceivers.get(sourceMethod)) == null) {
                classNewInstanceReceivers.put(sourceMethod, receiverNames = new LinkedHashSet<>());
              }
              receiverNames.add(target);
            } else if ("Method.invoke".equals(kind)) {
              if (!Scene.v().containsMethod(target)) {
                throw new RuntimeException("Unknown method for signature: " + target);
              }

              Set<String> receiverNames;
              if ((receiverNames = methodInvokeReceivers.get(sourceMethod)) == null) {
                methodInvokeReceivers.put(sourceMethod, receiverNames = new LinkedHashSet<>());
              }
              receiverNames.add(target);
            } else if ("Constructor.newInstance".equals(kind)) {
              if (!Scene.v().containsMethod(target)) {
                throw new RuntimeException("Unknown method for signature: " + target);
              }

              Set<String> receiverNames;
              if ((receiverNames = constructorNewInstanceReceivers.get(sourceMethod)) == null) {
                constructorNewInstanceReceivers.put(sourceMethod, receiverNames = new LinkedHashSet<>());
              }
              receiverNames.add(target);
            } else if ("Field.set*".equals(kind)) {
              if (!Scene.v().containsField(target)) {
                throw new RuntimeException("Unknown method for signature: " + target);
              }

              Set<String> receiverNames;
              if ((receiverNames = fieldSetReceivers.get(sourceMethod)) == null) {
                fieldSetReceivers.put(sourceMethod, receiverNames = new LinkedHashSet<>());
              }
              receiverNames.add(target);
            } else if ("Field.get*".equals(kind)) {
              if (!Scene.v().containsField(target)) {
                throw new RuntimeException("Unknown method for signature: " + target);
              }

              Set<String> receiverNames;
              if ((receiverNames = fieldGetReceivers.get(sourceMethod)) == null) {
                fieldGetReceivers.put(sourceMethod, receiverNames = new LinkedHashSet<>());
              }
              receiverNames.add(target);
            } else {
              ignoredKinds.add(kind);
            }
          }
          lines++;
        }
        if (!ignoredKinds.isEmpty()) {
          logger
              .debug("Encountered reflective calls entries of the following kinds that\n" + "cannot currently be handled:");
          ignoredKinds.forEach(kind -> logger.debug("" + kind));
        }
      } catch (FileNotFoundException e) {
        throw new RuntimeException("Trace file not found.", e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

private Set<SootMethod> inferSource(String source, int lineNumber) {
    String className = source.substring(0, source.lastIndexOf("."));
    String methodName = source.substring(source.lastIndexOf(".") + 1);
    if (!Scene.v().containsClass(className)) {
      Scene.v().addBasicClass(className, SootClass.BODIES);
      Scene.v().loadBasicClasses();
      if (!Scene.v().containsClass(className)) {
        throw new RuntimeException("Trace file refers to unknown class: " + className);
      }
    }

    SootClass sootClass = Scene.v().getSootClass(className);
    Set<SootMethod> methodsWithRightName = new LinkedHashSet<>();
    sootClass.getMethods().stream().filter(m -> m.isConcrete() && m.getName().equals(methodName)).forEach(methodsWithRightName::add);

    if (methodsWithRightName.isEmpty()) {
      throw new RuntimeException(new StringBuilder().append("Trace file refers to unknown method with name ").append(methodName).append(" in Class ").append(className).toString());
    } else if (methodsWithRightName.size() == 1) {
      return Collections.singleton(methodsWithRightName.iterator().next());
    } else {
      // more than one method with that name
      for (SootMethod sootMethod : methodsWithRightName) {
        if (coversLineNumber(lineNumber, sootMethod)) {
          return Collections.singleton(sootMethod);
        }
        if (sootMethod.isConcrete()) {
          if (!sootMethod.hasActiveBody()) {
            sootMethod.retrieveActiveBody();
          }
          Body body = sootMethod.getActiveBody();
          if (coversLineNumber(lineNumber, body)) {
            return Collections.singleton(sootMethod);
          }
          for (Unit u : body.getUnits()) {
            if (coversLineNumber(lineNumber, u)) {
              return Collections.singleton(sootMethod);
            }
          }
        }
      }

      // if we get here then we found no method with the right line number information;
      // be conservative and return all method that we found
      return methodsWithRightName;
    }
  }

private boolean coversLineNumber(int lineNumber, Host host) {
    {
      SourceLnPosTag tag = (SourceLnPosTag) host.getTag("SourceLnPosTag");
      boolean condition = tag != null && tag.startLn() <= lineNumber && tag.endLn() >= lineNumber;
	if (condition) {
	  return true;
	}
    }
    {
      LineNumberTag tag = (LineNumberTag) host.getTag("LineNumberTag");
      boolean condition1 = tag != null && tag.getLineNumber() == lineNumber;
	if (condition1) {
	  return true;
	}
    }
    return false;
  }

public Set<String> classForNameClassNames(SootMethod container) {
    if (!classForNameReceivers.containsKey(container)) {
      return Collections.emptySet();
    }
    return classForNameReceivers.get(container);
  }

public Set<SootClass> classForNameClasses(SootMethod container) {
    Set<SootClass> result = new LinkedHashSet<>();
    classForNameClassNames(container).forEach(className -> result.add(Scene.v().getSootClass(className)));
    return result;
  }

public Set<String> classNewInstanceClassNames(SootMethod container) {
    if (!classNewInstanceReceivers.containsKey(container)) {
      return Collections.emptySet();
    }
    return classNewInstanceReceivers.get(container);
  }

public Set<SootClass> classNewInstanceClasses(SootMethod container) {
    Set<SootClass> result = new LinkedHashSet<>();
    classNewInstanceClassNames(container).forEach(className -> result.add(Scene.v().getSootClass(className)));
    return result;
  }

public Set<String> constructorNewInstanceSignatures(SootMethod container) {
    if (!constructorNewInstanceReceivers.containsKey(container)) {
      return Collections.emptySet();
    }
    return constructorNewInstanceReceivers.get(container);
  }

public Set<SootMethod> constructorNewInstanceConstructors(SootMethod container) {
    Set<SootMethod> result = new LinkedHashSet<>();
    constructorNewInstanceSignatures(container).forEach(signature -> result.add(Scene.v().getMethod(signature)));
    return result;
  }

public Set<String> methodInvokeSignatures(SootMethod container) {
    if (!methodInvokeReceivers.containsKey(container)) {
      return Collections.emptySet();
    }
    return methodInvokeReceivers.get(container);
  }

public Set<SootMethod> methodInvokeMethods(SootMethod container) {
    Set<SootMethod> result = new LinkedHashSet<>();
    methodInvokeSignatures(container).forEach(signature -> result.add(Scene.v().getMethod(signature)));
    return result;
  }

public Set<SootMethod> methodsContainingReflectiveCalls() {
    Set<SootMethod> res = new LinkedHashSet<>();
    res.addAll(classForNameReceivers.keySet());
    res.addAll(classNewInstanceReceivers.keySet());
    res.addAll(constructorNewInstanceReceivers.keySet());
    res.addAll(methodInvokeReceivers.keySet());
    return res;
  }

public Set<String> fieldSetSignatures(SootMethod container) {
    if (!fieldSetReceivers.containsKey(container)) {
      return Collections.emptySet();
    }
    return fieldSetReceivers.get(container);
  }

public Set<String> fieldGetSignatures(SootMethod container) {
    if (!fieldGetReceivers.containsKey(container)) {
      return Collections.emptySet();
    }
    return fieldGetReceivers.get(container);
  }

public enum Kind {
    ClassForName, ClassNewInstance, ConstructorNewInstance, MethodInvoke, FieldSet, FieldGet
  }
}
