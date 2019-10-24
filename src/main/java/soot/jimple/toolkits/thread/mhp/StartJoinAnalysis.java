package soot.jimple.toolkits.thread.mhp;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2018 Raja Vallée-Rai and others
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.Hierarchy;
import soot.Local;
import soot.MethodOrMethodContext;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Value;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.spark.pag.AllocNode;
import soot.jimple.spark.pag.Node;
import soot.jimple.spark.pag.PAG;
import soot.jimple.spark.sets.P2SetVisitor;
import soot.jimple.spark.sets.PointsToSetInternal;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Filter;
import soot.jimple.toolkits.callgraph.TransitiveTargets;
import soot.jimple.toolkits.pointer.LocalMustAliasAnalysis;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.MHGPostDominatorsFinder;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.FlowSet;
import soot.toolkits.scalar.ForwardFlowAnalysis;

// StartJoinFinder written by Richard L. Halpert, 2006-12-04
// This can be used as an alternative to PegGraph and PegChain
// if only thread start, join, and type information is needed

// This is implemented as a real flow analysis so that, in the future,
// flow information can be used to match starts with joins

public class StartJoinAnalysis extends ForwardFlowAnalysis {
  private static final Logger logger = LoggerFactory.getLogger(StartJoinAnalysis.class);
  Set<Stmt> startStatements;
  Set<Stmt> joinStatements;

  Hierarchy hierarchy;

  Map<Stmt, List<SootMethod>> startToRunMethods;
  Map<Stmt, List<AllocNode>> startToAllocNodes;
  Map<Stmt, Stmt> startToJoin;

  public StartJoinAnalysis(UnitGraph g, SootMethod sm, CallGraph callGraph, PAG pag) {
    super(g);

    startStatements = new HashSet<>();
    joinStatements = new HashSet<>();

    hierarchy = Scene.v().getActiveHierarchy();

    startToRunMethods = new HashMap<>();
    startToAllocNodes = new HashMap<>();
    startToJoin = new HashMap<>();

    // Get lists of start and join statements
    doFlowInsensitiveSingleIterationAnalysis();

    if (!startStatements.isEmpty()) {
      // Get supporting info and analyses
      MHGPostDominatorsFinder pd = new MHGPostDominatorsFinder(new BriefUnitGraph(sm.getActiveBody()));
      // EqualUsesAnalysis lif = new EqualUsesAnalysis(g);
      LocalMustAliasAnalysis lma = new LocalMustAliasAnalysis(g);
      TransitiveTargets runMethodTargets = new TransitiveTargets(callGraph, new Filter(new RunMethodsPred()));

      for (Stmt start : startStatements) {
        List<SootMethod> runMethodsList = new ArrayList<>(); // will be a list of possible run methods called by
                                                                       // this start stmt
        List<AllocNode> allocNodesList = new ArrayList<>(); // will be a list of possible allocation nodes for the
                                                                     // thread object that's
                                                                     // getting started

        // Get possible thread objects (may alias)
        Value startObject = ((InstanceInvokeExpr) (start).getInvokeExpr()).getBase();
        PointsToSetInternal pts = (PointsToSetInternal) pag.reachingObjects((Local) startObject);
        List<AllocNode> mayAlias = getMayAliasList(pts);
        if (mayAlias.size() < 1) {
          continue; // If the may alias is empty, this must be dead code
        }

        // For each possible thread object, get run method
        Iterator<MethodOrMethodContext> mayRunIt = runMethodTargets.iterator(start); // fails for some call graphs
        while (mayRunIt.hasNext()) {
          SootMethod runMethod = (SootMethod) mayRunIt.next();
          if ("void run()".equals(runMethod.getSubSignature())) {
            runMethodsList.add(runMethod);
          }
        }

        // If haven't found any run methods, then use the type of the startObject,
        // and add run from it and all subclasses
        if (runMethodsList.isEmpty() && ((RefType) startObject.getType()).getSootClass().isApplicationClass()) {
          List<SootClass> threadClasses
              = hierarchy.getSubclassesOfIncluding(((RefType) startObject.getType()).getSootClass());
          threadClasses.stream().map(currentClass -> currentClass.getMethodUnsafe("void run()")).forEach(currentMethod -> {
			if (currentMethod != null) {
              runMethodsList.add(currentMethod);
            }
		});
        }

        for (AllocNode allocNode : mayAlias) {
          allocNodesList.add(allocNode);
          if (runMethodsList.isEmpty()) {
            throw new RuntimeException("Can't find run method for: " + startObject);
            /*
             * if( allocNode.getType() instanceof RefType ) { List threadClasses = hierarchy.getSubclassesOf(((RefType)
             * allocNode.getType()).getSootClass()); Iterator threadClassesIt = threadClasses.iterator();
             * while(threadClassesIt.hasNext()) { SootClass currentClass = (SootClass) threadClassesIt.next(); if(
             * currentClass.declaresMethod("void run()") ) { runMethodsList.add(currentClass.getMethod("void run()")); } } }
             */
          }
        }

        // Add this start stmt to both maps
        startToRunMethods.put(start, runMethodsList);
        startToAllocNodes.put(start, allocNodesList);

        joinStatements.forEach(join -> {
          Value joinObject = ((InstanceInvokeExpr) (join).getInvokeExpr()).getBase();

          // If startObject and joinObject MUST be the same, and if join post-dominates start
          List barriers = new ArrayList();
          barriers.addAll(g.getSuccsOf(join)); // definitions of the start variable are tracked until they pass a join
          // does join post-dominate start?
		// if( lif.areEqualUses( start, (Local) startObject, join, (Local) joinObject, barriers) )
          if (lma.mustAlias((Local) startObject, start, (Local) joinObject, join) && (pd.getDominators(start)).contains(join)) {
		  // logger.debug("START-JOIN PAIR: " + start + ", " + join);
		  startToJoin.put(start, join); // then this join always joins this start's thread
		}
        });
      }
    }
  }

  private List<AllocNode> getMayAliasList(PointsToSetInternal pts) {
    List<AllocNode> list = new ArrayList<>();
    final HashSet<AllocNode> ret = new HashSet<>();
    pts.forall(new P2SetVisitor() {
      @Override
	public void visit(Node n) {

        ret.add((AllocNode) n);
      }
    });
    ret.forEach(list::add);
    return list;
  }

  public Set<Stmt> getStartStatements() {
    return startStatements;
  }

  public Set<Stmt> getJoinStatements() {
    return joinStatements;
  }

  public Map<Stmt, List<SootMethod>> getStartToRunMethods() {
    return startToRunMethods;
  }

  public Map<Stmt, List<AllocNode>> getStartToAllocNodes() {
    return startToAllocNodes;
  }

  public Map<Stmt, Stmt> getStartToJoin() {
    return startToJoin;
  }

  public void doFlowInsensitiveSingleIterationAnalysis() {
    FlowSet fs = (FlowSet) newInitialFlow();
    Iterator stmtIt = graph.iterator();
    while (stmtIt.hasNext()) {
      Stmt s = (Stmt) stmtIt.next();
      flowThrough(fs, s, fs);
    }
  }

  @Override
protected void merge(Object in1, Object in2, Object out) {
    FlowSet inSet1 = (FlowSet) in1;
    FlowSet inSet2 = (FlowSet) in2;
    FlowSet outSet = (FlowSet) out;

    inSet1.intersection(inSet2, outSet);
  }

  @Override
protected void flowThrough(Object inValue, Object unit, Object outValue) {
    Stmt stmt = (Stmt) unit;

    /*
	     * in.copy(out);
	     *
	     * // get list of definitions at this unit List newDefs = new ArrayList(); if(stmt instanceof DefinitionStmt) { Value
	     * leftOp = ((DefinitionStmt)stmt).getLeftOp(); if(leftOp instanceof Local) newDefs.add((Local) leftOp); }
	     *
	     * // kill any start stmt whose base has been redefined Iterator outIt = out.iterator(); while(outIt.hasNext()) { Stmt
	     * outStmt = (Stmt) outIt.next(); if(newDefs.contains((Local) ((InstanceInvokeExpr)
	     * (outStmt).getInvokeExpr()).getBase())) out.remove(outStmt); }
	     */
	// Search for start/join invoke expressions
	if (!stmt.containsInvokeExpr()) {
		return;
	}
	// If this is a start stmt, add it to startStatements
      InvokeExpr ie = stmt.getInvokeExpr();
	if (ie instanceof InstanceInvokeExpr) {
        InstanceInvokeExpr iie = (InstanceInvokeExpr) ie;
        SootMethod invokeMethod = ie.getMethod();
        if ("start".equals(invokeMethod.getName())) {
          RefType baseType = (RefType) iie.getBase().getType();
          if (!baseType.getSootClass().isInterface()) // the start method we're looking for is NOT an interface method
          {
            List<SootClass> superClasses = hierarchy.getSuperclassesOfIncluding(baseType.getSootClass());
            superClasses.stream().map(superClasse -> "java.lang.Thread".equals(superClasse.getName()) && !startStatements.contains(stmt)).forEach(condition -> {
				// This is a Thread.start()
					// Flow this Thread.start() down
					// out.add(stmt);
				if (condition) {
				  startStatements.add(stmt);
				}
			});
          }
        }

        // If this is a join stmt, add it to joinStatements
        if ("join".equals(invokeMethod.getName())) // the join method we're looking for is NOT an interface method
        {
          RefType baseType = (RefType) iie.getBase().getType();
          if (!baseType.getSootClass().isInterface()) {
            List<SootClass> superClasses = hierarchy.getSuperclassesOfIncluding(baseType.getSootClass());
            superClasses.stream().map(superClasse -> "java.lang.Thread".equals(superClasse.getName()) && !joinStatements.contains(stmt)).forEach(condition1 -> {
				// This is a Thread.join()
				if (condition1) {
				  joinStatements.add(stmt);
				}
			});
          }
        }
      }
  }

  @Override
protected void copy(Object source, Object dest) {

    FlowSet sourceSet = (FlowSet) source;
    FlowSet destSet = (FlowSet) dest;

    sourceSet.copy(destSet);

  }

  @Override
protected Object entryInitialFlow() {
    return new ArraySparseSet();
  }

  @Override
protected Object newInitialFlow() {
    return new ArraySparseSet();
  }
}
