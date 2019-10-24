package soot.toolkits.graph;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 1999 Raja Vallee-Rai, Patrick Lam
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import soot.G;
import soot.Singletons;

/**
 * Provide the pseudo topological order of a graph's nodes. It has same functionality as PseudoTopologicalOrderer; however,
 * this class considers the order of successors. It runs slower but more precise. Currently it was only used by
 * ArrayBoundsCheckerAnalysis to reduce the iteration numbers.
 *
 * @see: PseudoTopologicalOrderer
 */
public class SlowPseudoTopologicalOrderer<N> implements Orderer<N> {

  private static final int WHITE = 0;

private static final int GRAY = 1;

private static final int BLACK = 2;

	private Map<N, Integer> stmtToColor;

	private LinkedList<N> order;

	private boolean mIsReversed = false;

	private DirectedGraph<N> graph;

	private List<N> reverseOrder;

	private final HashMap<N, List<N>> succsMap = new HashMap<>();

	public SlowPseudoTopologicalOrderer(Singletons.Global g) {
	  }

	public SlowPseudoTopologicalOrderer() {
	  }

	public SlowPseudoTopologicalOrderer(boolean isReversed) {
	    mIsReversed = isReversed;
	  }

	public static SlowPseudoTopologicalOrderer v() {
	    return G.v().soot_toolkits_graph_SlowPseudoTopologicalOrderer();
	  }

	/**
	   * {@inheritDoc}
	   */
	  @Override
	public List<N> newList(DirectedGraph<N> g, boolean reverse) {
	    mIsReversed = reverse;
	    return computeOrder(g);
	  }

	/**
	   * Orders in pseudo-topological order.
	   *
	   * @param g
	   *          a DirectedGraph instance we want to order the nodes for.
	   * @return an ordered list of the graph's nodes.
	   */
	  LinkedList<N> computeOrder(DirectedGraph<N> g) {
	    stmtToColor = new HashMap<>();
	
	    order = new LinkedList<>();
	    graph = g;
	
	    PseudoTopologicalReverseOrderer<N> orderer = new PseudoTopologicalReverseOrderer<>();
	
	    reverseOrder = orderer.newList(g);
	
	    // Color all nodes white
	    {
	
	      for (N s : g) {
	        stmtToColor.put(s, WHITE);
	      }
	    }
	
	    // Visit each node
	    {
	      for (N s : g) {
	        if (stmtToColor.get(s) == WHITE) {
	          visitNode(s);
	        }
	      }
	    }
	
	    return order;
	  }

	// Unfortunately, the nice recursive solution fails because of stack
	  // overflows. Fill in the 'order' list with a pseudo topological order
	  // (possibly reversed) list of statements starting at s.
	  // Simulates recursion with a stack.
	  private void visitNode(N startStmt) {
	    LinkedList<N> stmtStack = new LinkedList<>();
	    LinkedList<Integer> indexStack = new LinkedList<>();
	
	    stmtToColor.put(startStmt, GRAY);
	
	    stmtStack.addLast(startStmt);
	    indexStack.addLast(-1);
	
	    while (!stmtStack.isEmpty()) {
	      int toVisitIndex = indexStack.removeLast();
	      N toVisitNode = stmtStack.getLast();
	
	      toVisitIndex++;
	
	      indexStack.addLast(toVisitIndex);
	
	      if (toVisitIndex >= graph.getSuccsOf(toVisitNode).size()) {
	        // Visit this node now that we ran out of children
	        if (mIsReversed) {
	          order.addLast(toVisitNode);
	        } else {
	          order.addFirst(toVisitNode);
	        }
	
	        stmtToColor.put(toVisitNode, BLACK);
	
	        // Pop this node off
	        stmtStack.removeLast();
	        indexStack.removeLast();
	      } else {
	        List<N> orderedSuccs = succsMap.get(toVisitNode);
	        if (orderedSuccs == null) {
	          orderedSuccs = new LinkedList<>();
	          succsMap.put(toVisitNode, orderedSuccs);
	          /* make ordered succs */
	
	          List<N> allsuccs = graph.getSuccsOf(toVisitNode);
	
	          for (N cur : allsuccs) {
	            int j = 0;
	
	            for (; j < orderedSuccs.size(); j++) {
	              N comp = orderedSuccs.get(j);
	
	              int idx1 = reverseOrder.indexOf(cur);
	              int idx2 = reverseOrder.indexOf(comp);
	
	              if (idx1 < idx2) {
	                break;
	              }
	            }
	
	            orderedSuccs.add(j, cur);
	          }
	        }
	
	        N childNode = orderedSuccs.get(toVisitIndex);
	
	        // Visit this child next if not already visited (or on stack)
	        if (stmtToColor.get(childNode) == WHITE) {
	          stmtToColor.put(childNode, GRAY);
	
	          stmtStack.addLast(childNode);
	          indexStack.addLast(-1);
	        }
	      }
	    }
	  }

	// deprecated methods follow
	  /**
	   * @param g
	   *          a DirectedGraph instance whose nodes we wish to order.
	   * @return a pseudo-topologically ordered list of the graph's nodes.
	   * @deprecated use {@link #newList(DirectedGraph, boolean))} instead
	   */
	  @Deprecated
	  public List<N> newList(DirectedGraph<N> g) {
	    return computeOrder(g);
	  }

	/**
	   * Set the ordering for the orderer.
	   *
	   * @param isReverse
	   *          specify if we want reverse pseudo-topological ordering, or not.
	   * @deprecated use {@link #newList(DirectedGraph, boolean))} instead
	   */
	  @Deprecated
	  public void setReverseOrder(boolean isReversed) {
	    mIsReversed = isReversed;
	  }

	/**
	   * Check the ordering for the orderer.
	   *
	   * @return true if we have reverse pseudo-topological ordering, false otherwise.
	   * @deprecated use {@link #newList(DirectedGraph, boolean))} instead
	   */
	  @Deprecated
	  public boolean isReverseOrder() {
	    return mIsReversed;
	  }

private class PseudoTopologicalReverseOrderer<N> {

    private static final int WHITE = 0;

	private static final int GRAY = 1;

	private static final int BLACK = 2;

	private Map<N, Integer> stmtToColor;

	private LinkedList<N> order;

	private final boolean mIsReversed = false;

	private DirectedGraph<N> graph;

	/**
     * @param g
     *          a DirectedGraph instance whose nodes we which to order.
     * @return a pseudo-topologically ordered list of the graph's nodes.
     */
    List<N> newList(DirectedGraph<N> g) {
      return computeOrder(g);
    }

	/**
     * Orders in pseudo-topological order.
     *
     * @param g
     *          a DirectedGraph instance we want to order the nodes for.
     * @return an ordered list of the graph's nodes.
     */
    LinkedList<N> computeOrder(DirectedGraph<N> g) {
      stmtToColor = new HashMap<>();

      order = new LinkedList<>();
      graph = g;

      // Color all nodes white
      {
        for (N s : g) {
          stmtToColor.put(s, WHITE);
        }
      }

      // Visit each node
      {
        for (N s : g) {
          if (stmtToColor.get(s) == WHITE) {
            visitNode(s);
          }
        }
      }

      return order;
    }

	private void visitNode(N startStmt) {
      LinkedList<N> stmtStack = new LinkedList<>();
      LinkedList<Integer> indexStack = new LinkedList<>();

      stmtToColor.put(startStmt, GRAY);

      stmtStack.addLast(startStmt);
      indexStack.addLast(-1);

      while (!stmtStack.isEmpty()) {
        int toVisitIndex = indexStack.removeLast();
        N toVisitNode = stmtStack.getLast();

        toVisitIndex++;

        indexStack.addLast(toVisitIndex);

        if (toVisitIndex >= graph.getPredsOf(toVisitNode).size()) {
          // Visit this node now that we ran out of children
          if (mIsReversed) {
            order.addLast(toVisitNode);
          } else {
            order.addFirst(toVisitNode);
          }

          stmtToColor.put(toVisitNode, BLACK);

          // Pop this node off
          stmtStack.removeLast();
          indexStack.removeLast();
        } else {
          N childNode = graph.getPredsOf(toVisitNode).get(toVisitIndex);

          // Visit this child next if not already visited (or on stack)
          if (stmtToColor.get(childNode) == WHITE) {
            stmtToColor.put(childNode, GRAY);

            stmtStack.addLast(childNode);
            indexStack.addLast(-1);
          }
        }
      }
    }

  }

}
