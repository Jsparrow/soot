package soot.jimple.toolkits.typing;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2000 Etienne Gagnon.  All rights reserved.
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.ArrayType;
import soot.DoubleType;
import soot.FloatType;
import soot.IntType;
import soot.Local;
import soot.LongType;
import soot.NullType;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.Type;
import soot.Unit;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.NewExpr;
import soot.jimple.SpecialInvokeExpr;
import soot.jimple.Stmt;
import soot.options.Options;
import soot.toolkits.scalar.LocalDefs;

/**
 * This class resolves the type of local variables.
 *
 * <b>NOTE:</b> This class has been superseded by {@link soot.jimple.toolkits.typing.fast.TypeResolver}.
 **/
public class TypeResolver {
  private static final Logger logger = LoggerFactory.getLogger(TypeResolver.class);
private static final boolean DEBUG = false;
private static final boolean IMPERFORMANT_TYPE_CHECK = false;
/** Reference to the class hierarchy **/
  private final ClassHierarchy hierarchy;
/** All type variable instances **/
  private final List<TypeVariable> typeVariableList = new ArrayList<>();
/** Hashtable: [TypeNode or Local] -> TypeVariable **/
  private final Map<Object, TypeVariable> typeVariableMap = new HashMap<>();
private final JimpleBody stmtBody;
final TypeNode NULL;
private final TypeNode object;
// categories for type variables (solved = hard, unsolved = soft)
  private Collection<TypeVariable> unsolved;
private Collection<TypeVariable> solved;
// parent categories for unsolved type variables
  private List<TypeVariable> singleSoftParent;
private List<TypeVariable> singleHardParent;
private List<TypeVariable> multipleParents;
// child categories for unsolved type variables
  private List<TypeVariable> singleChildNotNull;
private List<TypeVariable> singleNullChild;
private List<TypeVariable> multipleChildren;

private TypeResolver(JimpleBody stmtBody, Scene scene) {
    this.stmtBody = stmtBody;
    hierarchy = ClassHierarchy.classHierarchy(scene);

    object = hierarchy.OBJECT;
    NULL = hierarchy.NULL;
    typeVariable(object);
    typeVariable(NULL);

    // hack for J2ME library, reported by Stephen Cheng
    if (!Options.v().j2me()) {
      typeVariable(hierarchy.CLONEABLE);
      typeVariable(hierarchy.SERIALIZABLE);
    }
  }

public ClassHierarchy hierarchy() {
    return hierarchy;
  }

public TypeNode typeNode(Type type) {
    return hierarchy.typeNode(type);
  }

/** Get type variable for the given local. **/
  TypeVariable typeVariable(Local local) {
    TypeVariable result = typeVariableMap.get(local);

    if (result == null) {
      int id = typeVariableList.size();
      typeVariableList.add(null);

      result = new TypeVariable(id, this);

      typeVariableList.set(id, result);
      typeVariableMap.put(local, result);

      if (DEBUG) {
        logger.debug(new StringBuilder().append("[LOCAL VARIABLE \"").append(local).append("\" -> ").append(id).append("]").toString());
      }
    }

    return result;
  }

/** Get type variable for the given type node. **/
  public TypeVariable typeVariable(TypeNode typeNode) {
    TypeVariable result = typeVariableMap.get(typeNode);

    if (result == null) {
      int id = typeVariableList.size();
      typeVariableList.add(null);

      result = new TypeVariable(id, this, typeNode);

      typeVariableList.set(id, result);
      typeVariableMap.put(typeNode, result);
    }

    return result;
  }

/** Get type variable for the given soot class. **/
  public TypeVariable typeVariable(SootClass sootClass) {
    return typeVariable(hierarchy.typeNode(sootClass.getType()));
  }

/** Get type variable for the given type. **/
  public TypeVariable typeVariable(Type type) {
    return typeVariable(hierarchy.typeNode(type));
  }

/** Get new type variable **/
  public TypeVariable typeVariable() {
    int id = typeVariableList.size();
    typeVariableList.add(null);

    TypeVariable result = new TypeVariable(id, this);

    typeVariableList.set(id, result);

    return result;
  }

public static void resolve(JimpleBody stmtBody, Scene scene) {
    if (DEBUG) {
      logger.debug("" + stmtBody.getMethod());
    }

    try {
      TypeResolver resolver = new TypeResolver(stmtBody, scene);
      resolver.resolve_step_1();
    } catch (TypeException e1) {
      if (DEBUG) {
        logger.error(e1.getMessage(), e1);
        logger.debug("Step 1 Exception-->" + e1.getMessage());
      }

      try {
        TypeResolver resolver = new TypeResolver(stmtBody, scene);
        resolver.resolve_step_2();
      } catch (TypeException e2) {
        if (DEBUG) {
          logger.error(e2.getMessage(), e2);
          logger.debug("Step 2 Exception-->" + e2.getMessage());
        }

        try {
          TypeResolver resolver = new TypeResolver(stmtBody, scene);
          resolver.resolve_step_3();
        } catch (TypeException e3) {
          StringWriter st = new StringWriter();
          PrintWriter pw = new PrintWriter(st);
          logger.error(e3.getMessage(), e3);
          pw.close();
          throw new RuntimeException(st.toString());
        }
      }
    }
    soot.jimple.toolkits.typing.integer.TypeResolver.resolve(stmtBody);
  }

private void debug_vars(String message) {
    if (!DEBUG) {
		return;
	}
	int count = 0;
	logger.debug("**** START:" + message);
	for (TypeVariable var : typeVariableList) {
        logger.debug(new StringBuilder().append(Integer.toString(count++)).append(" ").append(var).toString());
      }
	logger.debug("**** END:" + message);
  }

private void debug_body() {
    if (!DEBUG) {
		return;
	}
	logger.debug("-- Body Start --");
	for (Iterator<Unit> stmtIt = stmtBody.getUnits().iterator(); stmtIt.hasNext();) {
        final Stmt stmt = (Stmt) stmtIt.next();
        logger.debug("" + stmt);
      }
	logger.debug("-- Body End --");
  }

private void resolve_step_1() throws TypeException {
    // remove_spurious_locals();

    collect_constraints_1_2();
    debug_vars("constraints");

    compute_array_depth();
    propagate_array_constraints();
    debug_vars("arrays");

    merge_primitive_types();
    debug_vars("primitive");

    merge_connected_components();
    debug_vars("components");

    remove_transitive_constraints();
    debug_vars("transitive");

    merge_single_constraints();
    debug_vars("single");

    assign_types_1_2();
    debug_vars("assign");

    check_constraints();
  }

private void resolve_step_2() throws TypeException {
    debug_body();
    split_new();
    debug_body();

    collect_constraints_1_2();
    debug_vars("constraints");

    compute_array_depth();
    propagate_array_constraints();
    debug_vars("arrays");

    merge_primitive_types();
    debug_vars("primitive");

    merge_connected_components();
    debug_vars("components");

    remove_transitive_constraints();
    debug_vars("transitive");

    merge_single_constraints();
    debug_vars("single");

    assign_types_1_2();
    debug_vars("assign");

    check_constraints();
  }

private void resolve_step_3() throws TypeException {
    collect_constraints_3();
    compute_approximate_types();
    assign_types_3();
    check_and_fix_constraints();
  }

private void collect_constraints_1_2() {
    ConstraintCollector collector = new ConstraintCollector(this, true);

    for (Iterator<Unit> stmtIt = stmtBody.getUnits().iterator(); stmtIt.hasNext();) {

      final Stmt stmt = (Stmt) stmtIt.next();
      if (DEBUG) {
        logger.debug("stmt: ");
      }
      collector.collect(stmt, stmtBody);
      if (DEBUG) {
        logger.debug("" + stmt);
      }
    }
  }

private void collect_constraints_3() {
    ConstraintCollector collector = new ConstraintCollector(this, false);

    for (Iterator<Unit> stmtIt = stmtBody.getUnits().iterator(); stmtIt.hasNext();) {

      final Stmt stmt = (Stmt) stmtIt.next();
      if (DEBUG) {
        logger.debug("stmt: ");
      }
      collector.collect(stmt, stmtBody);
      if (DEBUG) {
        logger.debug("" + stmt);
      }
    }
  }

private void compute_array_depth() throws TypeException {
    compute_approximate_types();

    TypeVariable[] vars = new TypeVariable[typeVariableList.size()];
    vars = typeVariableList.toArray(vars);

    for (TypeVariable element : vars) {
      element.fixDepth();
    }
  }

private void propagate_array_constraints() {
    // find max depth
    int max = 0;
    for (TypeVariable var : typeVariableList) {
      int depth = var.depth();

      if (depth > max) {
        max = depth;
      }
    }

    boolean condition = max > 1 && !Options.v().j2me();
	// hack for J2ME library, reported by Stephen Cheng
    if (condition) {
        typeVariable(ArrayType.v(RefType.v("java.lang.Cloneable"), max - 1));
        typeVariable(ArrayType.v(RefType.v("java.io.Serializable"), max - 1));
      }

    // propagate constraints, starting with highest depth
    for (int i = max; i >= 0; i--) {
      typeVariableList.forEach(TypeVariable::propagate);
    }
  }

private void merge_primitive_types() throws TypeException {
    // merge primitive types with all parents/children
    compute_solved();

    for (TypeVariable var : solved) {
      if (var.type().type() instanceof IntType || var.type().type() instanceof LongType
          || var.type().type() instanceof FloatType || var.type().type() instanceof DoubleType) {
        List<TypeVariable> parents;
        List<TypeVariable> children;
        boolean finished;

        do {
          finished = true;

          parents = var.parents();
          if (parents.size() != 0) {
            finished = false;
            for (TypeVariable parent : parents) {
              if (DEBUG) {
                logger.debug(".");
              }

              var = var.union(parent);
            }
          }

          children = var.children();
          if (children.size() != 0) {
            finished = false;
            for (TypeVariable child : children) {
              if (DEBUG) {
                logger.debug(".");
              }

              var = var.union(child);
            }
          }
        } while (!finished);
      }
    }
  }

private void merge_connected_components() throws TypeException {
    refresh_solved();
    if (!IMPERFORMANT_TYPE_CHECK) {
		return;
	}
	List<TypeVariable> list = new ArrayList<>(solved.size() + unsolved.size());
	list.addAll(solved);
	list.addAll(unsolved);
	// MMI: This method does not perform any changing effect
      // on the list, just a bit error checking, if
      // I see this correctly.
      StronglyConnectedComponents.merge(list);
  }

private void remove_transitive_constraints() throws TypeException {
    refresh_solved();

    solved.forEach(TypeVariable::removeIndirectRelations);
    unsolved.forEach(TypeVariable::removeIndirectRelations);
  }

private void merge_single_constraints() throws TypeException {
    boolean finished = false;
    boolean modified = false;
    while (true) {
      categorize();

      if (singleChildNotNull.size() != 0) {
        finished = false;
        modified = true;

        for (TypeVariable var : singleChildNotNull) {
          if (singleChildNotNull.contains(var)) {
            TypeVariable child = var.children().get(0);

            var = var.union(child);
          }
        }
      }

      if (finished) {
        if (singleSoftParent.size() != 0) {
          finished = false;
          modified = true;

          for (TypeVariable var : singleSoftParent) {
            if (singleSoftParent.contains(var)) {
              TypeVariable parent = var.parents().get(0);

              var = var.union(parent);
            }
          }
        }

        if (singleHardParent.size() != 0) {
          finished = false;
          modified = true;

          for (TypeVariable var : singleHardParent) {
            if (singleHardParent.contains(var)) {
              TypeVariable parent = var.parents().get(0);

              debug_vars(new StringBuilder().append("union single parent\n ").append(var).append("\n ").append(parent).toString());
              var = var.union(parent);
            }
          }
        }

        if (singleNullChild.size() != 0) {
          finished = false;
          modified = true;

          for (TypeVariable var : singleNullChild) {
            if (singleNullChild.contains(var)) {
              TypeVariable child = var.children().get(0);

              var = var.union(child);
            }
          }
        }

        if (finished) {
          break;
        }

        continue;
      }

      if (modified) {
        modified = false;
        continue;
      }

      finished = true;

      multiple_children: for (TypeVariable var : multipleChildren) {
        TypeNode lca = null;
        List<TypeVariable> children_to_remove = new LinkedList<>();

        var.fixChildren();

        for (TypeVariable child : var.children()) {

          TypeNode type = child.type();

          if (type != null && type.isNull()) {
            var.removeChild(child);
          } else if (type != null && type.isClass()) {
            children_to_remove.add(child);

            if (lca == null) {
              lca = type;
            } else {
              lca = lca.lcaIfUnique(type);

              if (lca == null) {
                if (DEBUG) {
                  logger.debug(
                      new StringBuilder().append("==++==").append(stmtBody.getMethod().getDeclaringClass().getName()).append(".").append(stmtBody.getMethod().getName()).toString());
                }

                continue multiple_children;
              }
            }
          }
        }

        if (lca != null) {
          children_to_remove.forEach(var::removeChild);

          var.addChild(typeVariable(lca));
        }
      }

      for (TypeVariable var : multipleParents) {

        List<TypeVariable> hp = new ArrayList<>(); // hard parents

        var.fixParents();

        for (TypeVariable parent : var.parents()) {

          TypeNode type = parent.type();

          if (type != null) {
            Iterator<TypeVariable> k = hp.iterator();
            while (k.hasNext()) {
              TypeVariable otherparent = k.next();
              TypeNode othertype = otherparent.type();

              if (type.hasDescendant(othertype)) {
                var.removeParent(parent);
                type = null;
                break;
              }

              if (type.hasAncestor(othertype)) {
                var.removeParent(otherparent);
                k.remove();
              }
            }

            if (type != null) {
              hp.add(parent);
            }
          }
        }
      }
    }
  }

private void assign_types_1_2() throws TypeException {
    for (Iterator<Local> localIt = stmtBody.getLocals().iterator(); localIt.hasNext();) {
      final Local local = localIt.next();
      TypeVariable var = typeVariable(local);

      if (var == null) {
        local.setType(RefType.v("java.lang.Object"));
      } else if (var.depth() == 0) {
        if (var.type() == null) {
          TypeVariable.error("Type Error(5):  Variable without type");
        } else {
          local.setType(var.type().type());
        }
      } else {
        TypeVariable element = var.element();

        for (int j = 1; j < var.depth(); j++) {
          element = element.element();
        }

        if (element.type() == null) {
          TypeVariable.error("Type Error(6):  Array variable without base type");
        } else if (element.type().type() instanceof NullType) {
          local.setType(NullType.v());
        } else {
          Type t = element.type().type();
          if (t instanceof IntType) {
            local.setType(var.approx().type());
          } else {
            local.setType(ArrayType.v(t, var.depth()));
          }
        }
      }

      boolean condition = DEBUG && (var != null) && (var.approx() != null) && (var.approx().type() != null) && (local != null)
	    && (local.getType() != null) && !local.getType().equals(var.approx().type());
	if (condition) {
        logger.debug(new StringBuilder().append("local: ").append(local).append(", type: ").append(local.getType()).append(", approx: ").append(var.approx().type())
				.toString());
      }
    }
  }

private void assign_types_3() throws TypeException {
    for (Iterator<Local> localIt = stmtBody.getLocals().iterator(); localIt.hasNext();) {
      final Local local = localIt.next();
      TypeVariable var = typeVariable(local);

      if (var == null || var.approx() == null || var.approx().type() == null) {
        local.setType(RefType.v("java.lang.Object"));
      } else {
        local.setType(var.approx().type());
      }
    }
  }

private void check_constraints() throws TypeException {
    ConstraintChecker checker = new ConstraintChecker(this, false);
    StringBuilder s = null;

    if (DEBUG) {
      s = new StringBuilder("Checking:\n");
    }

    for (Iterator<Unit> stmtIt = stmtBody.getUnits().iterator(); stmtIt.hasNext();) {

      final Stmt stmt = (Stmt) stmtIt.next();
      if (DEBUG) {
        s.append(new StringBuilder().append(" ").append(stmt).append("\n").toString());
      }
      try {
        checker.check(stmt, stmtBody);
      } catch (TypeException e) {
        if (DEBUG) {
          logger.debug("" + s);
        }
        throw e;
      }
    }
  }

private void check_and_fix_constraints() throws TypeException {
    ConstraintChecker checker = new ConstraintChecker(this, true);
    StringBuilder s = null;
    PatchingChain<Unit> units = stmtBody.getUnits();
    Stmt[] stmts = new Stmt[units.size()];
    units.toArray(stmts);

    if (DEBUG) {
      s = new StringBuilder("Checking:\n");
    }

    for (Stmt stmt : stmts) {
      if (DEBUG) {
        s.append(new StringBuilder().append(" ").append(stmt).append("\n").toString());
      }
      try {
        checker.check(stmt, stmtBody);
      } catch (TypeException e) {
        if (DEBUG) {
          logger.debug("" + s);
        }
        throw e;
      }
    }
  }

private void compute_approximate_types() throws TypeException {
    TreeSet<TypeVariable> workList = new TreeSet<>();

    typeVariableList.stream().filter(var -> var.type() != null).forEach(workList::add);

    TypeVariable.computeApprox(workList);

    for (TypeVariable var : typeVariableList) {

      if (var.approx() == NULL) {
        var.union(typeVariable(NULL));
      } else if (var.approx() == null) {
        var.union(typeVariable(NULL));
      }
    }
  }

private void compute_solved() {
    Set<TypeVariable> unsolved_set = new TreeSet<>();
    Set<TypeVariable> solved_set = new TreeSet<>();

    typeVariableList.stream().filter(var -> var.depth() == 0).forEach(var -> {
        if (var.type() == null) {
          unsolved_set.add(var);
        } else {
          solved_set.add(var);
        }
      });

    solved = solved_set;
    unsolved = unsolved_set;
  }

private void refresh_solved() throws TypeException {
    Set<TypeVariable> unsolved_set = new TreeSet<>();
    Set<TypeVariable> solved_set = new TreeSet<>(solved);

    unsolved.stream().filter(var -> var.depth() == 0).forEach(var -> {
        if (var.type() == null) {
          unsolved_set.add(var);
        } else {
          solved_set.add(var);
        }
      });

    solved = solved_set;
    unsolved = unsolved_set;

    // validate();
  }

private void categorize() throws TypeException {
    refresh_solved();

    singleSoftParent = new LinkedList<>();
    singleHardParent = new LinkedList<>();
    multipleParents = new LinkedList<>();
    singleChildNotNull = new LinkedList<>();
    singleNullChild = new LinkedList<>();
    multipleChildren = new LinkedList<>();

    unsolved.forEach(var -> {
      // parent category
      {
        List<TypeVariable> parents = var.parents();
        int size = parents.size();

        if (size == 0) {
          var.addParent(typeVariable(object));
          singleSoftParent.add(var);
        } else if (size == 1) {
          TypeVariable parent = parents.get(0);

          if (parent.type() == null) {
            singleSoftParent.add(var);
          } else {
            singleHardParent.add(var);
          }
        } else {
          multipleParents.add(var);
        }
      }

      // child category
      {
        List<TypeVariable> children = var.children();
        int size = children.size();

        if (size == 0) {
          var.addChild(typeVariable(NULL));
          singleNullChild.add(var);
        } else if (size == 1) {
          TypeVariable child = children.get(0);

          if (child.type() == NULL) {
            singleNullChild.add(var);
          } else {
            singleChildNotNull.add(var);
          }
        } else {
          multipleChildren.add(var);
        }
      }
    });
  }

private void split_new() {
    LocalDefs defs = LocalDefs.Factory.newLocalDefs(stmtBody);
    PatchingChain<Unit> units = stmtBody.getUnits();
    Stmt[] stmts = new Stmt[units.size()];

    units.toArray(stmts);

    for (Stmt stmt : stmts) {
      if (stmt instanceof InvokeStmt) {
        InvokeStmt invoke = (InvokeStmt) stmt;

        if (invoke.getInvokeExpr() instanceof SpecialInvokeExpr) {
          SpecialInvokeExpr special = (SpecialInvokeExpr) invoke.getInvokeExpr();

          if ("<init>".equals(special.getMethodRef().name())) {
            List<Unit> deflist = defs.getDefsOfAt((Local) special.getBase(), invoke);

            while (deflist.size() == 1) {
              Stmt stmt2 = (Stmt) deflist.get(0);

              if (stmt2 instanceof AssignStmt) {
                AssignStmt assign = (AssignStmt) stmt2;

                if (assign.getRightOp() instanceof Local) {
                  deflist = defs.getDefsOfAt((Local) assign.getRightOp(), assign);
                  continue;
                } else if (assign.getRightOp() instanceof NewExpr) {
                  // We split the local.
                  // logger.debug("split: [" + assign + "] and [" + stmt + "]");
                  Local newlocal = Jimple.v().newLocal("tmp", null);
                  stmtBody.getLocals().add(newlocal);

                  special.setBase(newlocal);

                  units.insertAfter(Jimple.v().newAssignStmt(assign.getLeftOp(), newlocal), assign);
                  assign.setLeftOp(newlocal);
                }
              }
              break;
            }
          }
        }
      }
    }
  }
}
