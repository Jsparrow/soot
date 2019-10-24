package soot.dava.internal.asg;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2003 Jerome Miecznikowski
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import soot.Unit;
import soot.dava.Dava;
import soot.jimple.IfStmt;
import soot.jimple.LookupSwitchStmt;
import soot.jimple.Stmt;
import soot.jimple.TableSwitchStmt;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.PseudoTopologicalOrderer;
import soot.toolkits.graph.TrapUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.IterableSet;

public class AugmentedStmtGraph implements DirectedGraph<AugmentedStmt> {
  private HashMap<Stmt, AugmentedStmt> binding;
  private HashMap<AugmentedStmt, AugmentedStmt> original2clone;
  private IterableSet<AugmentedStmt> augList;
  private IterableSet<Stmt> stmtList;
  private List<AugmentedStmt> bheads;
private List<AugmentedStmt> btails;
private List<AugmentedStmt> cheads;
private List<AugmentedStmt> ctails;

  public AugmentedStmtGraph(AugmentedStmtGraph other) {
    this();

    HashMap<AugmentedStmt, AugmentedStmt> old2new = new HashMap<>();

    other.augList.forEach(oas -> {
      Stmt s = oas.get_Stmt();

      AugmentedStmt nas = new AugmentedStmt(s);
      augList.add(nas);
      stmtList.add(s);
      binding.put(s, nas);

      old2new.put(oas, nas);
    });

    other.augList.forEach(oas -> {
      AugmentedStmt nas = (AugmentedStmt) old2new.get(oas);

      oas.bpreds.forEach(aug -> nas.bpreds.add(old2new.get(aug)));
      if (nas.bpreds.isEmpty()) {
        bheads.add(nas);
      }

      oas.cpreds.forEach(aug -> nas.cpreds.add(old2new.get(aug)));
      if (nas.cpreds.isEmpty()) {
        cheads.add(nas);
      }

      oas.bsuccs.forEach(aug -> nas.bsuccs.add(old2new.get(aug)));
      if (nas.bsuccs.isEmpty()) {
        btails.add(nas);
      }

      oas.csuccs.forEach(aug -> nas.csuccs.add(old2new.get(aug)));
      if (nas.csuccs.isEmpty()) {
        ctails.add(nas);
      }
    });

    find_Dominators();
  }

  public AugmentedStmtGraph(BriefUnitGraph bug, TrapUnitGraph cug) {
    this();

    Dava.v().log("AugmentedStmtGraph::AugmentedStmtGraph() - cug.size() = " + cug.size());

    // make the augmented statements
    for (Unit u : cug) {
      Stmt s = (Stmt) u;
      add_StmtBinding(s, new AugmentedStmt(s));
    }

    // make the list of augmented statements in pseudo topological order!
    List<Unit> cugList = (new PseudoTopologicalOrderer<Unit>()).newList(cug, false);
    cugList.stream().map(u -> (Stmt) u).forEach(s -> {
		augList.add(get_AugStmt(s));
		stmtList.add(s);
	});

    // now that we've got all the augmented statements, mirror the statement
	// graph
	augList.forEach(as -> {
      mirror_PredsSuccs(as, bug);
      mirror_PredsSuccs(as, cug);
    });

    find_Dominators();
  }

  public AugmentedStmtGraph() {
    binding = new HashMap<>();
    original2clone = new HashMap<>();
    augList = new IterableSet<>();
    stmtList = new IterableSet<>();

    bheads = new LinkedList<>();
    btails = new LinkedList<>();
    cheads = new LinkedList<>();
    ctails = new LinkedList<>();
  }

  public void add_AugmentedStmt(AugmentedStmt as) {
    Stmt s = as.get_Stmt();

    augList.add(as);
    stmtList.add(s);

    add_StmtBinding(s, as);

    if (as.bpreds.isEmpty()) {
      bheads.add(as);
    }

    if (as.cpreds.isEmpty()) {
      cheads.add(as);
    }

    if (as.bsuccs.isEmpty()) {
      btails.add(as);
    }

    if (as.csuccs.isEmpty()) {
      ctails.add(as);
    }

    check_List(as.bpreds, btails);
    check_List(as.bsuccs, bheads);
    check_List(as.cpreds, ctails);
    check_List(as.csuccs, cheads);
  }

  public boolean contains(Object o) {
    return augList.contains(o);
  }

  public AugmentedStmt get_CloneOf(AugmentedStmt as) {
    return original2clone.get(as);
  }

  @Override
public int size() {
    return augList.size();
  }

  private <T> void check_List(List<T> psList, List<T> htList) {
    psList.stream().filter(htList::contains).forEach(htList::remove);
  }

  public void calculate_Reachability(AugmentedStmt source, Set<AugmentedStmt> blockers, AugmentedStmt dominator) {
    if (blockers == null) {
      throw new RuntimeException("Tried to call AugmentedStmtGraph:calculate_Reachability() with null blockers.");
    }

    if (source == null) {
      return;
    }

    LinkedList<AugmentedStmt> worklist = new LinkedList<>();
    HashSet<AugmentedStmt> touchSet = new HashSet<>();

    worklist.addLast(source);
    touchSet.add(source);

    while (worklist.isEmpty() == false) {
      AugmentedStmt as = worklist.removeFirst();

      for (AugmentedStmt sas : as.csuccs) {
        if ((touchSet.contains(sas)) || (sas.get_Dominators().contains(dominator) == false)) {
          continue;
        }

        touchSet.add(sas);

        IterableSet<AugmentedStmt> reachers = sas.get_Reachers();

        if (reachers.contains(source) == false) {
          reachers.add(source);
        }

        if (blockers.contains(sas) == false) {
          worklist.addLast(sas);
        }
      }
    }
  }

  public void calculate_Reachability(Collection<AugmentedStmt> sources, Set<AugmentedStmt> blockers,
      AugmentedStmt dominator) {
    sources.forEach(source -> calculate_Reachability(source, blockers, dominator));
  }

  public void calculate_Reachability(AugmentedStmt source, AugmentedStmt blocker, AugmentedStmt dominator) {
    HashSet<AugmentedStmt> h = new HashSet<>();

    h.add(blocker);

    calculate_Reachability(source, h, dominator);
  }

  public void calculate_Reachability(Collection<AugmentedStmt> sources, AugmentedStmt blocker, AugmentedStmt dominator) {
    HashSet<AugmentedStmt> h = new HashSet<>();

    h.add(blocker);

    calculate_Reachability(sources, h, dominator);
  }

  public void calculate_Reachability(AugmentedStmt source, AugmentedStmt dominator) {
    calculate_Reachability(source, Collections.<AugmentedStmt>emptySet(), dominator);
  }

  public void calculate_Reachability(Collection<AugmentedStmt> sources, AugmentedStmt dominator) {
    calculate_Reachability(sources, Collections.<AugmentedStmt>emptySet(), dominator);
  }

  public void calculate_Reachability(AugmentedStmt source) {
    calculate_Reachability(source, null);
  }

  public void calculate_Reachability(Collection<AugmentedStmt> sources) {
    calculate_Reachability(sources, null);
  }

  public void add_StmtBinding(Stmt s, AugmentedStmt as) {
    binding.put(s, as);
  }

  public AugmentedStmt get_AugStmt(Stmt s) {
    AugmentedStmt as = (AugmentedStmt) binding.get(s);
    if (as == null) {
      throw new RuntimeException("Could not find augmented statement for: " + s.toString());
    }

    return as;
  }

  // now put in the methods to satisfy the DirectedGraph interface

  @Override
  public List<AugmentedStmt> getHeads() {
    return cheads;
  }

  @Override
  public List<AugmentedStmt> getTails() {
    return ctails;
  }

  @Override
  public Iterator<AugmentedStmt> iterator() {
    return augList.iterator();
  }

  @Override
  public List<AugmentedStmt> getPredsOf(AugmentedStmt s) {
    return s.cpreds;
  }

  public List<AugmentedStmt> getPredsOf(Stmt s) {
    return get_AugStmt(s).cpreds;
  }

  @Override
  public List<AugmentedStmt> getSuccsOf(AugmentedStmt s) {
    return s.csuccs;
  }

  public List<AugmentedStmt> getSuccsOf(Stmt s) {
    return get_AugStmt(s).csuccs;
  }

  // end of methods satisfying DirectedGraph

  public List<AugmentedStmt> get_BriefHeads() {
    return bheads;
  }

  public List<AugmentedStmt> get_BriefTails() {
    return btails;
  }

  public IterableSet<AugmentedStmt> get_ChainView() {
    return new IterableSet<>(augList);
  }

  @Override
public Object clone() {
    return new AugmentedStmtGraph(this);
  }

  public boolean remove_AugmentedStmt(AugmentedStmt toRemove) {
    if (augList.contains(toRemove) == false) {
      return false;
    }

    toRemove.bpreds.stream().filter(pas -> pas.bsuccs.contains(toRemove)).forEach(pas -> pas.bsuccs.remove(toRemove));

    toRemove.cpreds.stream().filter(pas -> pas.csuccs.contains(toRemove)).forEach(pas -> pas.csuccs.remove(toRemove));

    toRemove.bsuccs.stream().filter(sas -> sas.bpreds.contains(toRemove)).forEach(sas -> sas.bpreds.remove(toRemove));

    toRemove.csuccs.stream().filter(sas -> sas.cpreds.contains(toRemove)).forEach(sas -> sas.cpreds.remove(toRemove));

    augList.remove(toRemove);
    stmtList.remove(toRemove.get_Stmt());

    bheads.remove(toRemove);
    btails.remove(toRemove);
    cheads.remove(toRemove);
    ctails.remove(toRemove);

    binding.remove(toRemove.get_Stmt());

    return true;

    // NOTE: we do *NOT* touch the underlying unit graphs.
  }

  @Override
public String toString() {
    StringBuilder b = new StringBuilder();
    String cr = "\n";

    b.append(new StringBuilder().append("AugmentedStmtGraph (size: ").append(size()).append(" stmts)").append(cr).toString());

    augList.forEach(as -> {
      b.append(new StringBuilder().append("| .---").append(cr).append("| | AugmentedStmt ").append(as.toString()).append(cr).append("| |")
			.append(cr).append("| |  preds:").toString());

      as.cpreds.forEach(pas -> b.append(" " + pas.toString()));

      b.append(new StringBuilder().append(cr).append("| |").append(cr).append("| |  succs:").toString());
      as.csuccs.forEach(sas -> b.append(" " + sas.toString()));

      b.append(new StringBuilder().append(cr).append("| |").append(cr).append("| |  doms:").toString());
      as.get_Dominators().forEach(das -> b.append(" " + das.toString()));

      b.append(new StringBuilder().append(cr).append("| `---").append(cr).toString());
    });

    b.append("-" + cr);
    return b.toString();
  }

  private void mirror_PredsSuccs(AugmentedStmt as, UnitGraph ug) {
    Stmt s = as.get_Stmt();

    LinkedList<AugmentedStmt> preds = new LinkedList<>();
	LinkedList<AugmentedStmt> succs = new LinkedList<>();

    // mirror the predecessors
	ug.getPredsOf(s).stream().map(u -> get_AugStmt((Stmt) u)).forEach(po -> {
		if (preds.contains(po) == false) {
		    preds.add(po);
		  }
	});

    // mirror the successors
	ug.getSuccsOf(s).stream().map(u -> get_AugStmt((Stmt) u)).forEach(so -> {
		if (succs.contains(so) == false) {
		    succs.add(so);
		  }
	});

    // attach the mirrors properly to the AugmentedStmt
    if (ug instanceof BriefUnitGraph) {
      as.bpreds = preds;
      as.bsuccs = succs;

      if (preds.size() == 0) {
        bheads.add(as);
      }
      if (succs.size() == 0) {
        btails.add(as);
      }
    } else if (ug instanceof TrapUnitGraph) {
      as.cpreds = preds;
      as.csuccs = succs;

      if (preds.size() == 0) {
        cheads.add(as);
      }
      if (succs.size() == 0) {
        ctails.add(as);
      }
    } else {
      throw new RuntimeException("Unknown UnitGraph type: " + ug.getClass());
    }
  }

  public IterableSet<AugmentedStmt> clone_Body(IterableSet<AugmentedStmt> oldBody) {
    HashMap<AugmentedStmt, AugmentedStmt> old2new = new HashMap<>();
	HashMap<AugmentedStmt, AugmentedStmt> new2old = new HashMap<>();

    IterableSet<AugmentedStmt> newBody = new IterableSet<>();

    oldBody.forEach(as -> {
      AugmentedStmt clone = (AugmentedStmt) as.clone();

      original2clone.put(as, clone);

      old2new.put(as, clone);
      new2old.put(clone, as);
      newBody.add(clone);
    });

    newBody.forEach(newAs -> {
      AugmentedStmt oldAs = (AugmentedStmt) new2old.get(newAs);

      mirror_PredsSuccs(oldAs, oldAs.bpreds, newAs.bpreds, old2new);
      mirror_PredsSuccs(oldAs, oldAs.cpreds, newAs.cpreds, old2new);
      mirror_PredsSuccs(oldAs, oldAs.bsuccs, newAs.bsuccs, old2new);
      mirror_PredsSuccs(oldAs, oldAs.csuccs, newAs.csuccs, old2new);
    });

    newBody.forEach(this::add_AugmentedStmt);

    HashMap<Stmt, Stmt> so2n = new HashMap<>();

    oldBody.forEach(as -> {
      Stmt os = as.get_Stmt();
      Stmt ns = old2new.get(as).get_Stmt();

      so2n.put(os, ns);
    });

    newBody.forEach(nas -> {
      AugmentedStmt oas = (AugmentedStmt) new2old.get(nas);

      Stmt ns = nas.get_Stmt();
	Stmt os = oas.get_Stmt();

      if (os instanceof IfStmt) {
        Unit target = ((IfStmt) os).getTarget();
		Unit newTgt = null;

        if ((newTgt = so2n.get(target)) != null) {
          ((IfStmt) ns).setTarget(newTgt);
        } else {
          ((IfStmt) ns).setTarget(target);
        }
      }

      else if (os instanceof TableSwitchStmt) {
        TableSwitchStmt otss = (TableSwitchStmt) os;
		TableSwitchStmt ntss = (TableSwitchStmt) ns;

        Unit target = otss.getDefaultTarget();
		Unit newTgt = null;

        if ((newTgt = so2n.get(target)) != null) {
          ntss.setDefaultTarget(newTgt);
        } else {
          ntss.setDefaultTarget(target);
        }

        LinkedList<Unit> new_target_list = new LinkedList<>();

        int target_count = otss.getHighIndex() - otss.getLowIndex() + 1;
        for (int i = 0; i < target_count; i++) {
          target = otss.getTarget(i);
          newTgt = null;

          if ((newTgt = so2n.get(target)) != null) {
            new_target_list.add(newTgt);
          } else {
            new_target_list.add(target);
          }
        }
        ntss.setTargets(new_target_list);
      }

      else if (os instanceof LookupSwitchStmt) {
        LookupSwitchStmt olss = (LookupSwitchStmt) os;
		LookupSwitchStmt nlss = (LookupSwitchStmt) ns;

        Unit target = olss.getDefaultTarget();
		Unit newTgt = null;

        if ((newTgt = so2n.get(target)) != null) {
          nlss.setDefaultTarget(newTgt);
        } else {
          nlss.setDefaultTarget(target);
        }

        Unit[] new_target_list = new Unit[olss.getTargetCount()];

        for (int i = 0; i < new_target_list.length; i++) {
          target = olss.getTarget(i);
          newTgt = null;

          if ((newTgt = so2n.get(target)) != null) {
            new_target_list[i] = newTgt;
          } else {
            new_target_list[i] = target;
          }
        }
        nlss.setTargets(new_target_list);
        nlss.setLookupValues(olss.getLookupValues());
      }
    });

    return newBody;
  }

  private void mirror_PredsSuccs(AugmentedStmt originalAs, List<AugmentedStmt> oldList, List<AugmentedStmt> newList,
      Map<AugmentedStmt, AugmentedStmt> old2new) {
    for (AugmentedStmt oldAs : oldList) {
      AugmentedStmt newAs = (AugmentedStmt) old2new.get(oldAs);

      if (newAs != null) {
        newList.add(newAs);
      } else {
        newList.add(oldAs);

        AugmentedStmt clonedAs = (AugmentedStmt) old2new.get(originalAs);

        if (oldList == originalAs.bpreds) {
          oldAs.bsuccs.add(clonedAs);
        } else if (oldList == originalAs.cpreds) {
          oldAs.csuccs.add(clonedAs);
        } else if (oldList == originalAs.bsuccs) {
          oldAs.bpreds.add(clonedAs);
        } else if (oldList == originalAs.csuccs) {
          oldAs.cpreds.add(clonedAs);
        } else {
          throw new RuntimeException("Error mirroring preds and succs in Try block splitting.");
        }
      }
    }
  }

  public void find_Dominators() {
    // set up the dominator sets for all the nodes in the graph
	augList.forEach(as -> {
      // Dominators:
      // safe starting approximation for S(0) ... empty set
      // unsafe starting approximation for S(i) .. full set
      if (as.cpreds.size() != 0) {

        if (as.get_Dominators().isEmpty() == false) {
          as.get_Dominators().clear();
        }

        as.get_Dominators().addAll(augList);
      } else {
        as.get_Dominators().clear();
      }
    });

    // build the worklist
    IterableSet<AugmentedStmt> worklist = new IterableSet<>();
    worklist.addAll(augList);

    // keep going until the worklist is empty
    while (!worklist.isEmpty()) {
      AugmentedStmt as = worklist.getFirst();
      worklist.removeFirst();

      IterableSet<AugmentedStmt> pred_intersection = new IterableSet<>();
      boolean first_pred = true;

      // run through all the predecessors and get their dominance
      // intersection
      for (AugmentedStmt pas : as.cpreds) {
        // for the first predecessor just take all his dominators
        if (first_pred) {
          pred_intersection.addAll(pas.get_Dominators());
          if (pred_intersection.contains(pas) == false) {
            pred_intersection.add(pas);
          }
          first_pred = false;
        }

        // for the subsequent predecessors remove the ones they do not
        // have from the intersection
        else {
          Iterator<AugmentedStmt> piit = pred_intersection.snapshotIterator();
          while (piit.hasNext()) {
            AugmentedStmt pid = piit.next();

            if ((pas.get_Dominators().contains(pid) == false) && (pas != pid)) {
              pred_intersection.remove(pid);
            }
          }
        }
      }

      // update dominance if we have a change
      if (as.get_Dominators().equals(pred_intersection) == false) {
        as.csuccs.stream().filter(o -> worklist.contains(o) == false).forEach(worklist::add);

        as.get_Dominators().clear();
        as.get_Dominators().addAll(pred_intersection);
      }
    }
  }
}
