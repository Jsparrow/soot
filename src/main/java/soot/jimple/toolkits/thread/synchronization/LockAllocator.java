package soot.jimple.toolkits.thread.synchronization;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.Body;
import soot.EquivalentValue;
import soot.G;
import soot.Local;
import soot.PhaseOptions;
import soot.PointsToAnalysis;
import soot.RefType;
import soot.Scene;
import soot.SceneTransformer;
import soot.Singletons;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.DefinitionStmt;
import soot.jimple.FieldRef;
import soot.jimple.InstanceFieldRef;
import soot.jimple.Ref;
import soot.jimple.StaticFieldRef;
import soot.jimple.Stmt;
import soot.jimple.spark.pag.PAG;
import soot.jimple.spark.sets.HashPointsToSet;
import soot.jimple.spark.sets.PointsToSetInternal;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import soot.jimple.toolkits.infoflow.ClassInfoFlowAnalysis;
import soot.jimple.toolkits.infoflow.FakeJimpleLocal;
import soot.jimple.toolkits.infoflow.SmartMethodInfoFlowAnalysis;
import soot.jimple.toolkits.pointer.RWSet;
import soot.jimple.toolkits.thread.ThreadLocalObjectsAnalysis;
import soot.jimple.toolkits.thread.mhp.MhpTester;
import soot.jimple.toolkits.thread.mhp.SynchObliviousMhpAnalysis;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.HashMutableEdgeLabelledDirectedGraph;
import soot.toolkits.scalar.FlowSet;
import soot.toolkits.scalar.LocalDefs;
import soot.util.Chain;

public class LockAllocator extends SceneTransformer {
  private static final Logger logger = LoggerFactory.getLogger(LockAllocator.class);
List<CriticalSection> criticalSections = null;
CriticalSectionInterferenceGraph interferenceGraph = null;
DirectedGraph deadlockGraph = null;
// Lock options
  boolean optionOneGlobalLock = false;
boolean optionStaticLocks = false;
boolean optionUseLocksets = false;
boolean optionLeaveOriginalLocks = false;
boolean optionIncludeEmptyPossibleEdges = false;
// Semantic options
  boolean optionAvoidDeadlock = true;
boolean optionOpenNesting = true;
// Analysis options
  boolean optionDoMHP = false;
boolean optionDoTLO = false;
boolean optionOnFlyTLO = false; // not a CLI option yet // on-fly is more efficient, but harder to measure in time
// Output options
  boolean optionPrintMhpSummary = true; // not a CLI option yet
boolean optionPrintGraph = false;
boolean optionPrintTable = false;
boolean optionPrintDebug = false;

public LockAllocator(Singletons.Global g) {
  }

public static LockAllocator v() {
    return G.v().soot_jimple_toolkits_thread_synchronization_LockAllocator();
  }

@Override
protected void internalTransform(String phaseName, Map<String, String> options) {
    // Get phase options
    String lockingScheme = PhaseOptions.getString(options, "locking-scheme");
    if ("fine-grained".equals(lockingScheme)) {
      optionOneGlobalLock = false;
      optionStaticLocks = false;
      optionUseLocksets = true;
      optionLeaveOriginalLocks = false;
    }
    // if(lockingScheme.equals("fine-static"))
    // {
    // optionOneGlobalLock = false;
    // optionStaticLocks = true;
    // optionUseLocksets = true;
    // optionLeaveOriginalLocks = false;
    // }
    if ("medium-grained".equals(lockingScheme)) // rename to coarse-grained
    {
      optionOneGlobalLock = false;
      optionStaticLocks = false;
      optionUseLocksets = false;
      optionLeaveOriginalLocks = false;
    }
    if ("coarse-grained".equals(lockingScheme)) // rename to coarse-static
    {
      optionOneGlobalLock = false;
      optionStaticLocks = true;
      optionUseLocksets = false;
      optionLeaveOriginalLocks = false;
    }
    if ("single-static".equals(lockingScheme)) {
      optionOneGlobalLock = true;
      optionStaticLocks = true;
      optionUseLocksets = false;
      optionLeaveOriginalLocks = false;
    }
    if ("leave-original".equals(lockingScheme)) {
      optionOneGlobalLock = false;
      optionStaticLocks = false;
      optionUseLocksets = false;
      optionLeaveOriginalLocks = true;
    }

    optionAvoidDeadlock = PhaseOptions.getBoolean(options, "avoid-deadlock");
    optionOpenNesting = PhaseOptions.getBoolean(options, "open-nesting");

    optionDoMHP = PhaseOptions.getBoolean(options, "do-mhp");
    optionDoTLO = PhaseOptions.getBoolean(options, "do-tlo");
    // optionOnFlyTLO = PhaseOptions.getBoolean( options, "on-fly-tlo" ); // not a real option yet

    // optionPrintMhpSummary = PhaseOptions.getBoolean( options, "print-mhp" ); // not a real option yet
    optionPrintGraph = PhaseOptions.getBoolean(options, "print-graph");
    optionPrintTable = PhaseOptions.getBoolean(options, "print-table");
    optionPrintDebug = PhaseOptions.getBoolean(options, "print-debug");

    // optionIncludeEmptyPossibleEdges = PhaseOptions.getBoolean( options, "include-empty-edges" ); // not a real option yet

    // *** Build May Happen In Parallel Info ***
    MhpTester mhp = null;
    if (optionDoMHP && Scene.v().getPointsToAnalysis() instanceof PAG) {
      logger.debug("[wjtp.tn] *** Build May-Happen-in-Parallel Info *** " + (new Date()));
      mhp = new SynchObliviousMhpAnalysis();
      if (optionPrintMhpSummary) {
        mhp.printMhpSummary();
      }
    }

    // *** Find Thread-Local Objects ***
    ThreadLocalObjectsAnalysis tlo = null;
    if (optionDoTLO) {
      logger.debug("[wjtp.tn] *** Find Thread-Local Objects *** " + (new Date()));
      if (mhp != null) {
        tlo = new ThreadLocalObjectsAnalysis(mhp);
      } else {
        tlo = new ThreadLocalObjectsAnalysis(new SynchObliviousMhpAnalysis());
      }
      if (!optionOnFlyTLO) {
        tlo.precompute();
        logger.debug(new StringBuilder().append("[wjtp.tn] TLO totals (#analyzed/#encountered): ").append(SmartMethodInfoFlowAnalysis.counter).append("/").append(ClassInfoFlowAnalysis.methodCount).toString());
      } else {
        logger.debug(new StringBuilder().append("[wjtp.tn] TLO so far (#analyzed/#encountered): ").append(SmartMethodInfoFlowAnalysis.counter).append("/").append(ClassInfoFlowAnalysis.methodCount).toString());
      }
    }

    // *** Find and Name Transactions ***
    // The transaction finder finds the start, end, and preparatory statements
    // for each transaction. It also calculates the non-transitive read/write
    // sets for each transaction.
    // For all methods, run the intraprocedural analysis (TransactionAnalysis)
    Date start = new Date();
    logger.debug("[wjtp.tn] *** Find and Name Transactions *** " + start);
    Map<SootMethod, FlowSet> methodToFlowSet = new HashMap<>();
    Map<SootMethod, ExceptionalUnitGraph> methodToExcUnitGraph = new HashMap<>();
    Iterator<SootClass> runAnalysisClassesIt = Scene.v().getApplicationClasses().iterator();
    while (runAnalysisClassesIt.hasNext()) {
      SootClass appClass = runAnalysisClassesIt.next();
      Iterator<SootMethod> methodsIt = appClass.getMethods().iterator();
      while (methodsIt.hasNext()) {
        SootMethod method = methodsIt.next();
        if (method.isConcrete()) {
          Body b = method.retrieveActiveBody();
          ExceptionalUnitGraph eug = new ExceptionalUnitGraph(b);
          methodToExcUnitGraph.put(method, eug);

          // run the intraprocedural analysis
          SynchronizedRegionFinder ta = new SynchronizedRegionFinder(eug, b, optionPrintDebug, optionOpenNesting, tlo);
          Chain<Unit> units = b.getUnits();
          Unit lastUnit = units.getLast();
          FlowSet fs = (FlowSet) ta.getFlowBefore(lastUnit);

          // add the results to the list of results
          methodToFlowSet.put(method, fs);
        }
      }
    }

    // Create a composite list of all transactions
    criticalSections = new Vector<>();
    for (FlowSet fs : methodToFlowSet.values()) {
      List fList = fs.toList();
      for (int i = 0; i < fList.size(); i++) {
        criticalSections.add(((SynchronizedRegionFlowPair) fList.get(i)).tn);
      }
    }

    // Assign Names To Transactions
    assignNamesToTransactions(criticalSections);

    if (optionOnFlyTLO) {
      logger.debug(new StringBuilder().append("[wjtp.tn] TLO so far (#analyzed/#encountered): ").append(SmartMethodInfoFlowAnalysis.counter).append("/").append(ClassInfoFlowAnalysis.methodCount).toString());
    }

    // *** Find Transitive Read/Write Sets ***
    // Finds the transitive read/write set for each transaction using a given
    // nesting model.
    logger.debug("[wjtp.tn] *** Find Transitive Read/Write Sets *** " + (new Date()));
    PointsToAnalysis pta = Scene.v().getPointsToAnalysis();
    CriticalSectionAwareSideEffectAnalysis tasea = null;
    tasea = new CriticalSectionAwareSideEffectAnalysis(pta, Scene.v().getCallGraph(),
        (optionOpenNesting ? criticalSections : null), tlo);
    for (CriticalSection tn : criticalSections) {
      for (Unit unit : tn.invokes) {
        Stmt stmt = (Stmt) unit;
        HashSet uses = new HashSet();
        RWSet stmtRead = tasea.readSet(tn.method, stmt, tn, uses);
        if (stmtRead != null) {
          tn.read.union(stmtRead);
        }

        RWSet stmtWrite = tasea.writeSet(tn.method, stmt, tn, uses);
        if (stmtWrite != null) {
          tn.write.union(stmtWrite);
        }
      }
    }
    long longTime = ((new Date()).getTime() - start.getTime()) / 100;
    float time = ((float) longTime) / 10.0f;
    if (optionOnFlyTLO) {
      logger.debug(new StringBuilder().append("[wjtp.tn] TLO totals (#analyzed/#encountered): ").append(SmartMethodInfoFlowAnalysis.counter).append("/").append(ClassInfoFlowAnalysis.methodCount).toString());
      logger.debug(new StringBuilder().append("[wjtp.tn] Time for stages utilizing on-fly TLO: ").append(time).append("s").toString());
    }

    // *** Find Stray Reads/Writes *** (DISABLED)
    // add external data races as one-line transactions
    // note that finding them isn't that hard (though it is time consuming)
    // For all methods, run the intraprocedural analysis (transaction finder)
    // Note that these will only be transformed if they are either added to
    // methodToFlowSet or if a loop and new body transformer are used for methodToStrayRWSet
    /*
     * Map methodToStrayRWSet = new HashMap(); Iterator runRWFinderClassesIt = Scene.v().getApplicationClasses().iterator();
     * while (runRWFinderClassesIt.hasNext()) { SootClass appClass = (SootClass) runRWFinderClassesIt.next(); Iterator
     * methodsIt = appClass.getMethods().iterator(); while (methodsIt.hasNext()) { SootMethod method = (SootMethod)
     * methodsIt.next(); Body b = method.retrieveActiveBody(); UnitGraph g = (UnitGraph) methodToExcUnitGraph.get(method);
     *
     * // run the interprocedural analysis // PTFindStrayRW ptfrw = new PTFindStrayRW(new ExceptionalUnitGraph(b), b,
     * AllTransactions); PTFindStrayRW ptfrw = new PTFindStrayRW(g, b, AllTransactions); Chain units = b.getUnits(); Unit
     * firstUnit = (Unit) units.iterator().next(); FlowSet fs = (FlowSet) ptfrw.getFlowBefore(firstUnit);
     *
     * // add the results to the list of results methodToStrayRWSet.put(method, fs); } } //
     */

    // *** Calculate Locking Groups ***
    // Search for data dependencies between transactions, and split them into disjoint sets
    logger.debug("[wjtp.tn] *** Calculate Locking Groups *** " + (new Date()));
    CriticalSectionInterferenceGraph ig = new CriticalSectionInterferenceGraph(criticalSections, mhp, optionOneGlobalLock,
        optionLeaveOriginalLocks, optionIncludeEmptyPossibleEdges);
    interferenceGraph = ig; // save in field for later retrieval

    // *** Detect the Possibility of Deadlock ***
    logger.debug("[wjtp.tn] *** Detect the Possibility of Deadlock *** " + (new Date()));
    DeadlockDetector dd = new DeadlockDetector(optionPrintDebug, optionAvoidDeadlock, true, criticalSections);
    if (!optionUseLocksets) {
      deadlockGraph = dd.detectComponentBasedDeadlock();
    }

    // *** Calculate Locking Objects ***
    // Get a list of all dependencies for each group
    logger.debug("[wjtp.tn] *** Calculate Locking Objects *** " + (new Date()));
    if (!optionStaticLocks) {
      // Calculate per-group contributing RWSet
      // (Might be preferable to use per-transaction contributing RWSet)
      for (CriticalSection tn : criticalSections) {
        if (tn.setNumber <= 0) {
          continue;
        }
        tn.edges.forEach(tdd -> tn.group.rwSet.union(tdd.rw));
      }
    }

    // Inspect each group's RW dependencies to determine if there's a possibility
    // of a shared lock object (if all dependencies are fields/localobjs of the same object)
    Map<Value, Integer> lockToLockNum = null;
    List<PointsToSetInternal> lockPTSets = null;
    if (optionLeaveOriginalLocks) {
      analyzeExistingLocks(criticalSections, ig);
    } else if (optionStaticLocks) {
      setFlagsForStaticAllocations(ig);
    } else // for locksets and dynamic locks
    {
      setFlagsForDynamicAllocations(ig);

      // Data structures for determining lock numbers
      lockPTSets = new ArrayList<>();
      lockToLockNum = new HashMap<>();

      findLockableReferences(criticalSections, pta, tasea, lockToLockNum, lockPTSets);

      // print out locksets
      if (optionUseLocksets) {
        for (CriticalSection tn : criticalSections) {
          if (tn.group != null) {
            logger.debug(new StringBuilder().append("[wjtp.tn] ").append(tn.name).append(" lockset: ").append(locksetToLockNumString(tn.lockset, lockToLockNum))
					.append(tn.group.useLocksets ? "" : " (placeholder)").toString());
          }
        }
      }
    }

    // *** Detect the Possibility of Deadlock for Locksets ***
    if (optionUseLocksets) // deadlock detection and lock ordering for lockset allocations
    {
      logger.debug(new StringBuilder().append("[wjtp.tn] *** Detect ").append(optionAvoidDeadlock ? "and Correct " : "").append("the Possibility of Deadlock for Locksets *** ").append(new Date()).toString());
      deadlockGraph = dd.detectLocksetDeadlock(lockToLockNum, lockPTSets);
      if (optionPrintDebug) {
        ((HashMutableEdgeLabelledDirectedGraph) deadlockGraph).printGraph();
      }

      logger.debug("[wjtp.tn] *** Reorder Locksets to Avoid Deadlock *** " + (new Date()));
      dd.reorderLocksets(lockToLockNum, (HashMutableEdgeLabelledDirectedGraph) deadlockGraph);
    }

    // *** Print Output and Transform Program ***
    logger.debug("[wjtp.tn] *** Print Output and Transform Program *** " + (new Date()));

    // Print topological graph in graphviz format
    if (optionPrintGraph) {
      printGraph(criticalSections, ig, lockToLockNum);
    }

    // Print table of transaction information
    if (optionPrintTable) {
      printTable(criticalSections, mhp);
      printGroups(criticalSections, ig);
    }

    // For all methods, run the lock transformer
	if (optionLeaveOriginalLocks) {
		return;
	}
	// Create an array of booleans to keep track of which global locks have been inserted into the program
      boolean[] insertedGlobalLock = new boolean[ig.groupCount()];
	insertedGlobalLock[0] = false;
	for (int i = 1; i < ig.groupCount(); i++) {
        CriticalSectionGroup tnGroup = ig.groups().get(i);
        insertedGlobalLock[i] = (!optionOneGlobalLock) && (tnGroup.useDynamicLock || tnGroup.useLocksets);
      }
	Scene.v().getApplicationClasses().forEach(appClass -> appClass.getMethods().stream().filter(SootMethod::isConcrete).forEach(method -> {
		FlowSet fs = methodToFlowSet.get(method);
		if (fs != null) {
			LockAllocationBodyTransformer.v().internalTransform(method.getActiveBody(), fs, ig.groups(),
					insertedGlobalLock);
		}
	}));
  }

protected void findLockableReferences(List<CriticalSection> AllTransactions, PointsToAnalysis pta,
      CriticalSectionAwareSideEffectAnalysis tasea, Map<Value, Integer> lockToLockNum,
      List<PointsToSetInternal> lockPTSets) {
    for (CriticalSection tn : AllTransactions) {
      int group = tn.setNumber - 1;
      if (group < 0) {
        continue;
      }

      if (tn.group.useDynamicLock || tn.group.useLocksets) // if attempting to use a dynamic lock or locksets
      {

        // Get list of objects (FieldRef or Local) to be locked (lockset analysis)
        logger.debug(new StringBuilder().append("[wjtp.tn] * ").append(tn.name).append(" *").toString());
        LockableReferenceAnalysis la = new LockableReferenceAnalysis(new BriefUnitGraph(tn.method.retrieveActiveBody()));
        tn.lockset = la.getLocksetOf(tasea, tn.group.rwSet, tn);

        // Determine if list is suitable for the selected locking scheme
        // TODO check for nullness
        if (optionUseLocksets) {
          // Post-process the locksets
          if (tn.lockset == null || tn.lockset.size() <= 0) {
            // If the lockset is invalid, revert the entire group to static locks:
            tn.group.useLocksets = false;

            // Create a lockset containing a single placeholder static lock for each tn in the group
            Value newStaticLock = new NewStaticLock(tn.method.getDeclaringClass());
            EquivalentValue newStaticLockEqVal = new EquivalentValue(newStaticLock);
            for (CriticalSection groupTn : tn.group) {
              groupTn.lockset = new ArrayList<>();
              groupTn.lockset.add(newStaticLockEqVal);
            }

            // Assign a lock number to the placeholder
            Integer lockNum = Integer.valueOf(-lockPTSets.size()); // negative indicates a static lock
            logger.debug(new StringBuilder().append("[wjtp.tn] Lock: num ").append(lockNum).append(" type ").append(newStaticLock.getType()).append(" obj ").append(newStaticLock)
					.toString());
            lockToLockNum.put(newStaticLockEqVal, lockNum);
            lockToLockNum.put(newStaticLock, lockNum);
            PointsToSetInternal dummyLockPT = new HashPointsToSet(newStaticLock.getType(), (PAG) pta); // KILLS CHA-BASED
                                                                                                       // ANALYSIS (pointer
                                                                                                       // exception)
            lockPTSets.add(dummyLockPT);
          } else {
            // If the lockset is valid
            // Assign a lock number for each lock in the lockset
            for (EquivalentValue lockEqVal : tn.lockset) {
              Value lock = lockEqVal.getValue();

              // Get reaching objects for this lock
              PointsToSetInternal lockPT;
              if (lock instanceof Local) {
                lockPT = (PointsToSetInternal) pta.reachingObjects((Local) lock);
              } else if (lock instanceof StaticFieldRef) {
                lockPT = null;
              } else if (lock instanceof InstanceFieldRef) {
                Local base = (Local) ((InstanceFieldRef) lock).getBase();
                if (base instanceof FakeJimpleLocal) {
                  lockPT = (PointsToSetInternal) pta.reachingObjects(((FakeJimpleLocal) base).getRealLocal(),
                      ((FieldRef) lock).getField());
                } else {
                  lockPT = (PointsToSetInternal) pta.reachingObjects(base, ((FieldRef) lock).getField());
                }
              } else if (lock instanceof NewStaticLock) {
                lockPT = null;
              } else {
                lockPT = null;
              }

              if (lockPT != null) {
                // Assign an existing lock number if possible
                boolean foundLock = false;
                for (int i = 0; i < lockPTSets.size(); i++) {
                  PointsToSetInternal otherLockPT = lockPTSets.get(i);
                  if (lockPT.hasNonEmptyIntersection(otherLockPT)) // will never happen for empty, negative numbered sets
                  {
                    logger.debug(new StringBuilder().append("[wjtp.tn] Lock: num ").append(i).append(" type ").append(lock.getType()).append(" obj ")
							.append(lock).toString());
                    lockToLockNum.put(lock, Integer.valueOf(i));
                    otherLockPT.addAll(lockPT, null);
                    foundLock = true;
                    break;
                  }
                }

                // Assign a brand new lock number otherwise
                if (!foundLock) {
                  logger.debug(new StringBuilder().append("[wjtp.tn] Lock: num ").append(lockPTSets.size()).append(" type ").append(lock.getType()).append(" obj ").append(lock)
						.toString());
                  lockToLockNum.put(lock, Integer.valueOf(lockPTSets.size()));
                  PointsToSetInternal otherLockPT = new HashPointsToSet(lockPT.getType(), (PAG) pta);
                  lockPTSets.add(otherLockPT);
                  otherLockPT.addAll(lockPT, null);
                }
              } else // static field locks and pathological cases...
              {
                // Assign an existing lock number if possible
                if (lockToLockNum.get(lockEqVal) != null) {
                  Integer lockNum = lockToLockNum.get(lockEqVal);
                  logger.debug(new StringBuilder().append("[wjtp.tn] Lock: num ").append(lockNum).append(" type ").append(lock.getType()).append(" obj ").append(lock)
						.toString());
                  lockToLockNum.put(lock, lockNum);
                } else {
                  Integer lockNum = Integer.valueOf(-lockPTSets.size()); // negative indicates a static lock
                  logger.debug(new StringBuilder().append("[wjtp.tn] Lock: num ").append(lockNum).append(" type ").append(lock.getType()).append(" obj ").append(lock)
						.toString());
                  lockToLockNum.put(lockEqVal, lockNum);
                  lockToLockNum.put(lock, lockNum);
                  PointsToSetInternal dummyLockPT = new HashPointsToSet(lock.getType(), (PAG) pta);
                  lockPTSets.add(dummyLockPT);
                }
              }
            }

          }
        } else {
          if (tn.lockset == null || tn.lockset.size() != 1) { // Found too few or too many locks
                                                              // So use a static lock instead
            tn.lockObject = null;
            tn.group.useDynamicLock = false;
            tn.group.lockObject = null;
          } else { // Found exactly one lock
                   // Use it!
            tn.lockObject = (Value) tn.lockset.get(0);

            // If it's the best lock we've found in the group yet, use it for display
            if (tn.group.lockObject == null || tn.lockObject instanceof Ref) {
              tn.group.lockObject = tn.lockObject;
            }
          }
        }
      }
    }
    if (optionUseLocksets) {
      // If any lock has only a singleton reaching object, treat it like a static lock
      for (int i = 0; i < lockPTSets.size(); i++) {
        PointsToSetInternal pts = lockPTSets.get(i);
        if (pts.size() == 1 && false) // isSingleton(pts)) // It's NOT easy to find a singleton: single alloc node must be
                                      // run just once
        {
          for (Object e : lockToLockNum.entrySet()) {
            Map.Entry entry = (Map.Entry) e;
            Integer value = (Integer) entry.getValue();
            if (value == i) {
              entry.setValue(Integer.valueOf(-i));
            }
          }
        }
      }
    }
  }

public void setFlagsForDynamicAllocations(CriticalSectionInterferenceGraph ig) {
    for (int group = 0; group < ig.groupCount() - 1; group++) {
      CriticalSectionGroup tnGroup = ig.groups().get(group + 1);

      if (optionUseLocksets) {
        tnGroup.useLocksets = true; // initially, guess that this is true
      } else {
        tnGroup.isDynamicLock = (tnGroup.rwSet.getGlobals().size() == 0);
        tnGroup.useDynamicLock = true;
        tnGroup.lockObject = null;
      }

      // empty groups don't get locks
      if (tnGroup.rwSet.size() <= 0) // There are no edges in this group
      {
        if (optionUseLocksets) {
          tnGroup.useLocksets = false;
        } else {
          tnGroup.isDynamicLock = false;
          tnGroup.useDynamicLock = false;
        }
        continue;
      }
    }
  }

public void setFlagsForStaticAllocations(CriticalSectionInterferenceGraph ig) {
    // Allocate one new static lock for each group.
    for (int group = 0; group < ig.groupCount() - 1; group++) {
      CriticalSectionGroup tnGroup = ig.groups().get(group + 1);
      tnGroup.isDynamicLock = false;
      tnGroup.useDynamicLock = false;
      tnGroup.lockObject = null;
    }
  }

private void analyzeExistingLocks(List<CriticalSection> AllTransactions, CriticalSectionInterferenceGraph ig) {
    setFlagsForStaticAllocations(ig);

    for (CriticalSection tn : AllTransactions) {
      if (tn.setNumber <= 0) {
        continue;
      }

      LocalDefs ld = LocalDefs.Factory.newLocalDefs(tn.method.retrieveActiveBody());

      if (tn.origLock == null || !(tn.origLock instanceof Local)) {
        continue;
      }
      List<Unit> rDefs = ld.getDefsOfAt((Local) tn.origLock, tn.entermonitor);
      if (rDefs == null) {
        continue;
      }
      rDefs.stream().map(u -> (Stmt) u).forEach(next -> {
		if (next instanceof DefinitionStmt) {
          Value rightOp = ((DefinitionStmt) next).getRightOp();
          if (rightOp instanceof FieldRef) {
            if (((FieldRef) rightOp).getField().isStatic()) {
              // lock may be static
              tn.group.lockObject = rightOp;
            } else {
              // this lock must be dynamic
              tn.group.isDynamicLock = true;
              tn.group.useDynamicLock = true;
              tn.group.lockObject = tn.origLock;
            }
          } else {
            // this lock is probably dynamic (but it's hard to tell for sure)
            tn.group.isDynamicLock = true;
            tn.group.useDynamicLock = true;
            tn.group.lockObject = tn.origLock;
          }
        } else {
          // this lock is probably dynamic (but it's hard to tell for sure)
          tn.group.isDynamicLock = true;
          tn.group.useDynamicLock = true;
          tn.group.lockObject = tn.origLock;
        }
	});
    }
  }

public static String locksetToLockNumString(List<EquivalentValue> lockset, Map<Value, Integer> lockToLockNum) {
    if (lockset == null) {
      return "null";
    }
    String ret = "[";
    boolean first = true;
    for (EquivalentValue lockEqVal : lockset) {
      if (!first) {
        ret = ret + " ";
      }
      first = false;
      ret = ret + lockToLockNum.get(lockEqVal.getValue());
    }
    return ret + "]";
  }

public void assignNamesToTransactions(List<CriticalSection> AllTransactions) {
    // Give each method a unique, deterministic identifier
    // Sort transactions into bins... one for each method name

    // Get list of method names
    List<String> methodNamesTemp = new ArrayList<>();
    // tn1.method.getSignature() + "." + tn1.method.getName();
	AllTransactions.stream().map(tn1 -> tn1.method.getSignature()).forEach(mname -> {
		if (!methodNamesTemp.contains(mname)) {
		    methodNamesTemp.add(mname);
		  }
	});
    String methodNames[] = new String[1];
    methodNames = methodNamesTemp.toArray(methodNames);
    Arrays.sort(methodNames);

    // Initialize method-named bins
    // this matrix is <# method names> wide and <max txns possible in one method> + 1 tall
    int identMatrix[][] = new int[methodNames.length][CriticalSection.nextIDNum - methodNames.length + 2];
    for (int i = 0; i < methodNames.length; i++) {
      identMatrix[i][0] = 0;
      for (int j = 1; j < CriticalSection.nextIDNum - methodNames.length + 1; j++) {
        identMatrix[i][j] = 50000;
      }
    }

    for (CriticalSection tn1 : AllTransactions) {
      int methodNum = Arrays.binarySearch(methodNames, tn1.method.getSignature());
      identMatrix[methodNum][0]++;
      identMatrix[methodNum][identMatrix[methodNum][0]] = tn1.IDNum;
    }

    // Sort bins by transaction IDNum
    // IDNums vary, but always increase in code-order within a method
    for (int j = 0; j < methodNames.length; j++) {
      identMatrix[j][0] = 0; // set the counter to 0 so it sorts out (into slot 0).
      Arrays.sort(identMatrix[j]); // sort this subarray
    }

    for (CriticalSection tn1 : AllTransactions) {
      int methodNum = Arrays.binarySearch(methodNames, tn1.method.getSignature());
      int tnNum = Arrays.binarySearch(identMatrix[methodNum], tn1.IDNum) - 1;
      tn1.name
          = new StringBuilder().append("m").append(methodNum < 10 ? "00" : (methodNum < 100 ? "0" : "")).append(methodNum).append("n")
				.append(tnNum < 10 ? "0" : "").append(tnNum).toString();
    }
  }

public void printGraph(Collection<CriticalSection> AllTransactions, CriticalSectionInterferenceGraph ig,
      Map<Value, Integer> lockToLockNum) {
    final String[] colors = { "black", "blue", "blueviolet", "chartreuse", "crimson", "darkgoldenrod1", "darkseagreen",
        "darkslategray", "deeppink", "deepskyblue1", "firebrick1", "forestgreen", "gold", "gray80", "navy", "pink", "red",
        "sienna", "turquoise1", "yellow" };
    Map<Integer, String> lockColors = new HashMap<>();
    int colorNum = 0;
    HashSet<CriticalSection> visited = new HashSet<>();

    logger.debug(new StringBuilder().append("[transaction-graph]").append(optionUseLocksets ? "" : " strict").append(" graph transactions {").toString());
    // "\n[transaction-graph]
    // start=1;");

    for (int group = 0; group < ig.groups().size(); group++) {
      boolean printedHeading = false;
      for (CriticalSection tn : AllTransactions) {
        if (tn.setNumber == group + 1) {
          if (!printedHeading) {
            // if(localLock[group] && lockObject[group] != null)
            if (tn.group.useDynamicLock && tn.group.lockObject != null) {
              String typeString = "";
              // if(lockObject[group].getType() instanceof RefType)
              // typeString = ((RefType) lockObject[group].getType()).getSootClass().getShortName();
              // else
              // typeString = lockObject[group].getType().toString();
              if (tn.group.lockObject.getType() instanceof RefType) {
                typeString = ((RefType) tn.group.lockObject.getType()).getSootClass().getShortName();
              } else {
                typeString = tn.group.lockObject.getType().toString();
              }
              logger.debug(new StringBuilder().append("[transaction-graph] subgraph cluster_").append(group + 1).append(" {\n[transaction-graph] color=blue;\n[transaction-graph] label=\"Lock: a \\n").append(typeString).append(" object\";").toString());
            } else if (tn.group.useLocksets) {
              logger.debug(new StringBuilder().append("[transaction-graph] subgraph cluster_").append(group + 1).append(" {\n[transaction-graph] color=blue;\n[transaction-graph] label=\"Locksets\";").toString());
            } else {
              String objString = "";
              // if(lockObject[group] == null)
              if (tn.group.lockObject == null) {
                objString = "lockObj" + (group + 1);
              }
              // else if(lockObject[group] instanceof FieldRef)
              else if (tn.group.lockObject instanceof FieldRef) {
                // SootField field = ((FieldRef) lockObject[group]).getField();
                SootField field = ((FieldRef) tn.group.lockObject).getField();
                objString = new StringBuilder().append(field.getDeclaringClass().getShortName()).append(".").append(field.getName()).toString();
              } else {
                objString = tn.group.lockObject.toString();
              }
              // objString = lockObject[group].toString();
              logger.debug(new StringBuilder().append("[transaction-graph] subgraph cluster_").append(group + 1).append(" {\n[transaction-graph] color=blue;\n[transaction-graph] label=\"Lock: \\n").append(objString).append("\";").toString());
            }
            printedHeading = true;
          }
          if (Scene.v().getReachableMethods().contains(tn.method)) {
            logger.debug(
                new StringBuilder().append("[transaction-graph] ").append(tn.name).append(" [name=\"").append(tn.method.toString()).append("\" style=\"setlinewidth(3)\"];")
						.toString());
          } else {
            logger.debug(new StringBuilder().append("[transaction-graph] ").append(tn.name).append(" [name=\"").append(tn.method.toString()).append("\" color=cadetblue1 style=\"setlinewidth(1)\"];").toString());
          }

          if (tn.group.useLocksets) // print locks instead of dependence edges
          {
            for (EquivalentValue lockEqVal : tn.lockset) {
              Integer lockNum = lockToLockNum.get(lockEqVal.getValue());
              for (CriticalSection tn2 : tn.group) {
                if (!visited.contains(tn2) && ig.mayHappenInParallel(tn, tn2)) {
                  for (EquivalentValue lock2EqVal : tn2.lockset) {
                    Integer lock2Num = lockToLockNum.get(lock2EqVal.getValue());
                    if (lockNum.intValue() == lock2Num.intValue()) {
                      // Get the color for this lock
                      if (!lockColors.containsKey(lockNum)) {
                        lockColors.put(lockNum, colors[colorNum % colors.length]);
                        colorNum++;
                      }
                      String color = lockColors.get(lockNum);

                      // Draw an edge for this lock
                      logger.debug(new StringBuilder().append("[transaction-graph] ").append(tn.name).append(" -- ").append(tn2.name).append(" [color=")
							.append(color).append(" style=").append(lockNum >= 0 ? "dashed" : "solid").append(" exactsize=1 style=\"setlinewidth(3)\"];").toString());
                    }
                  }
                }
              }
              visited.add(tn);
            }
          } else {
            for (CriticalSectionDataDependency edge : tn.edges) {
              CriticalSection tnedge = edge.other;
              if (tnedge.setNumber == group + 1) {
                logger.debug(new StringBuilder().append("[transaction-graph] ").append(tn.name).append(" -- ").append(tnedge.name).append(" [color=")
						.append(edge.size > 0 ? "black" : "cadetblue1").append(" style=").append(tn.setNumber > 0 && tn.group.useDynamicLock ? "dashed" : "solid").append(" exactsize=")
						.append(edge.size).append(" style=\"setlinewidth(3)\"];").toString());
              }
            }
          }
        }

      }
      if (printedHeading) {
        logger.debug("[transaction-graph] }");
      }
    }

    // Print nodes with no group
    {
      boolean printedHeading = false;
      for (CriticalSection tn : AllTransactions) {
        if (tn.setNumber == -1) {
          if (!printedHeading) {
            // putting these nodes in a "source" ranked subgraph makes them appear above all the clusters
            logger.debug("[transaction-graph] subgraph lone {\n[transaction-graph] rank=source;");
            printedHeading = true;
          }
          if (Scene.v().getReachableMethods().contains(tn.method)) {
            logger.debug(
                new StringBuilder().append("[transaction-graph] ").append(tn.name).append(" [name=\"").append(tn.method.toString()).append("\" style=\"setlinewidth(3)\"];")
						.toString());
          } else {
            logger.debug(new StringBuilder().append("[transaction-graph] ").append(tn.name).append(" [name=\"").append(tn.method.toString()).append("\" color=cadetblue1 style=\"setlinewidth(1)\"];").toString());
          }

          tn.edges.forEach(edge -> {
            CriticalSection tnedge = edge.other;
            if (tnedge.setNumber != tn.setNumber || tnedge.setNumber == -1) {
              logger.debug(new StringBuilder().append("[transaction-graph] ").append(tn.name).append(" -- ").append(tnedge.name).append(" [color=")
					.append(edge.size > 0 ? "black" : "cadetblue1").append(" style=").append(tn.setNumber > 0 && tn.group.useDynamicLock ? "dashed" : "solid").append(" exactsize=")
					.append(edge.size).append(" style=\"setlinewidth(1)\"];").toString());
            }
          });
        }
      }
      if (printedHeading) {
        logger.debug("[transaction-graph] }");
      }
    }

    logger.debug("[transaction-graph] }");
  }

public void printTable(Collection<CriticalSection> AllTransactions, MhpTester mhp) {
    logger.debug("[transaction-table] ");
    AllTransactions.forEach(tn -> {
      // Figure out if it's reachable, and if it MHP itself
      boolean reachable = false;
      boolean mhpself = false;
      {
        ReachableMethods rm = Scene.v().getReachableMethods();
        reachable = rm.contains(tn.method);
        if (mhp != null) {
          mhpself = mhp.mayHappenInParallel(tn.method, tn.method);
        }
      }
      logger.debug(new StringBuilder().append("[transaction-table] Transaction ").append(tn.name).append(reachable ? " reachable" : " dead")
			.append(mhpself ? " [called from >= 2 threads]" : " [called from <= 1 thread]").toString());
      logger.debug(
          new StringBuilder().append("[transaction-table] Where: ").append(tn.method.getDeclaringClass().toString()).append(":").append(tn.method.toString()).append(":  ").toString());
      logger.debug("[transaction-table] Orig : " + tn.origLock);
      logger.debug("[transaction-table] Prep : " + tn.prepStmt);
      logger.debug("[transaction-table] Begin: " + tn.entermonitor);
      logger.debug(new StringBuilder().append("[transaction-table] End  : early:").append(tn.earlyEnds.toString()).append(" exc:").append(tn.exceptionalEnd).append(" through:")
			.append(tn.end).append(" \n").toString());
      logger.debug("[transaction-table] Size : " + tn.units.size());
      if (tn.read.size() < 100) {
        logger.debug(new StringBuilder().append("[transaction-table] Read : ").append(tn.read.size()).append("\n[transaction-table] ").append(tn.read.toString().replaceAll("\\[", "     : [").replaceAll("\n", "\n[transaction-table] ")).toString());
      } else {
        logger.debug(new StringBuilder().append("[transaction-table] Read : ").append(tn.read.size()).append("  \n[transaction-table] ").toString());
      }
      if (tn.write.size() < 100) {
        logger.debug(new StringBuilder().append("Write: ").append(tn.write.size()).append("\n[transaction-table] ").append(tn.write.toString().replaceAll("\\[", "     : [").replaceAll("\n", "\n[transaction-table] ")).toString()); // label
                                                                                                             // provided by
                                                                                                             // previous
                                                                                                             // print
                                                                                                             // statement
      } else {
        logger.debug(new StringBuilder().append("Write: ").append(tn.write.size()).append("\n[transaction-table] ").toString()); // label provided by previous print statement
      }
      logger.debug(new StringBuilder().append("Edges: (").append(tn.edges.size()).append(") ").toString()); // label provided by previous print statement
      tn.edges.forEach(edge -> logger.debug(new StringBuilder().append("").append(edge.other.name).append(" ").toString()));
      if (tn.group != null && tn.group.useLocksets) {
        logger.debug("\n[transaction-table] Locks: " + tn.lockset);

      } else {
        logger.debug("\n[transaction-table] Lock : " + (tn.setNumber == -1 ? "-"
            : (tn.lockObject == null ? "Global"
                : (tn.lockObject.toString()
                    + (tn.lockObjectArrayIndex == null ? "" : "[" + tn.lockObjectArrayIndex + "]")))));
      }
      logger.debug(new StringBuilder().append("[transaction-table] Group: ").append(tn.setNumber).append("\n[transaction-table] ").toString());
    });
  }

public void printGroups(Collection<CriticalSection> AllTransactions, CriticalSectionInterferenceGraph ig) {
    logger.debug("[transaction-groups] Group Summaries\n[transaction-groups] ");
    for (int group = 0; group < ig.groupCount() - 1; group++) {
      CriticalSectionGroup tnGroup = ig.groups().get(group + 1);
      if (tnGroup.size() > 0) {
        logger.debug(new StringBuilder().append("Group ").append(group + 1).append(" ").toString());
        logger.debug(new StringBuilder().append("Locking: ").append(tnGroup.useLocksets ? "using "
		    : (tnGroup.isDynamicLock && tnGroup.useDynamicLock ? "Dynamic on " : "Static on ")).append(tnGroup.useLocksets ? "locksets" : (tnGroup.lockObject == null ? "null" : tnGroup.lockObject.toString()))
				.toString());
        logger.debug("\n[transaction-groups]      : ");
        for (CriticalSection tn : AllTransactions) {
          if (tn.setNumber == group + 1) {
            logger.debug(new StringBuilder().append("").append(tn.name).append(" ").toString());
          }
        }
        logger.debug("\n[transaction-groups] "
            + tnGroup.rwSet.toString().replaceAll("\\[", "     : [").replaceAll("\n", "\n[transaction-groups] "));
      }
    }
    logger.debug("Erasing \n[transaction-groups]      : ");
    Iterator<CriticalSection> tnIt = AllTransactions.iterator();
    while (tnIt.hasNext()) {
      CriticalSection tn = tnIt.next();
      if (tn.setNumber == -1) {
        logger.debug(new StringBuilder().append("").append(tn.name).append(" ").toString());
      }
    }
    logger.debug("\n[transaction-groups] ");
  }

public CriticalSectionInterferenceGraph getInterferenceGraph() {
    return interferenceGraph;
  }

public DirectedGraph getDeadlockGraph() {
    return deadlockGraph;
  }

public List<CriticalSection> getCriticalSections() {
    return criticalSections;
  }
}
