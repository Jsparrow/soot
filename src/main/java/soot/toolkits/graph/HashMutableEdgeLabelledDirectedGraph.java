package soot.toolkits.graph;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 Patrice Pominville, Raja Vallee-Rai, Patrick Lam
 * Copyright (C) 2007 Richard L. Halpert
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HashMap based implementation of a MutableEdgeLabelledDirectedGraph.
 *
 * @param <N>
 * @param <L>
 */
public class HashMutableEdgeLabelledDirectedGraph<N, L> implements MutableEdgeLabelledDirectedGraph<N, L> {
  private static final Logger logger = LoggerFactory.getLogger(HashMutableEdgeLabelledDirectedGraph.class);
protected Map<N, List<N>> nodeToPreds;
protected Map<N, List<N>> nodeToSuccs;
protected Map<DGEdge<N>, List<L>> edgeToLabels;
protected Map<L, List<DGEdge<N>>> labelToEdges;
protected Set<N> heads;
protected Set<N> tails;

public HashMutableEdgeLabelledDirectedGraph() {
    nodeToPreds = new HashMap<>();
    nodeToSuccs = new HashMap<>();
    edgeToLabels = new HashMap<>();
    labelToEdges = new HashMap<>();
    heads = new HashSet<>();
    tails = new HashSet<>();
  }

private static <T> List<T> getCopy(Collection<? extends T> c) {
    return Collections.unmodifiableList(new ArrayList<T>(c));
  }

/**
   * Removes all nodes and edges.
   */
  public void clearAll() {
    nodeToPreds.clear();
    nodeToSuccs.clear();
    edgeToLabels.clear();
    labelToEdges.clear();
    heads.clear();
    tails.clear();
  }

@Override
  public HashMutableEdgeLabelledDirectedGraph<N, L> clone() {
    HashMutableEdgeLabelledDirectedGraph<N, L> g = new HashMutableEdgeLabelledDirectedGraph<>();
    g.nodeToPreds.putAll(nodeToPreds);
    g.nodeToSuccs.putAll(nodeToSuccs);
    g.edgeToLabels.putAll(edgeToLabels);
    g.labelToEdges.putAll(labelToEdges);
    g.heads.addAll(heads);
    g.tails.addAll(tails);
    return g;
  }

/* Returns an unbacked list of heads for this graph. */
  @Override
  public List<N> getHeads() {
    return getCopy(heads);
  }

/* Returns an unbacked list of tails for this graph. */
  @Override
  public List<N> getTails() {
    return getCopy(tails);
  }

@Override
  public List<N> getPredsOf(N s) {
    List<N> preds = nodeToPreds.get(s);
    if (preds != null) {
      return Collections.unmodifiableList(preds);
    }

    throw new RuntimeException(s + "not in graph!");
  }

@Override
  public List<N> getSuccsOf(N s) {
    List<N> succs = nodeToSuccs.get(s);
    if (succs != null) {
      return Collections.unmodifiableList(succs);
    }

    throw new RuntimeException(s + "not in graph!");
  }

@Override
  public int size() {
    return nodeToPreds.keySet().size();
  }

@Override
  public Iterator<N> iterator() {
    return nodeToPreds.keySet().iterator();
  }

@Override
  public void addEdge(N from, N to, L label) {
    if (from == null || to == null) {
      throw new RuntimeException("edge from or to null");
    }

    if (label == null) {
      throw new RuntimeException("edge with null label");
    }

    if (containsEdge(from, to, label)) {
      return;
    }

    List<N> succsList = nodeToSuccs.get(from);
    if (succsList == null) {
      throw new RuntimeException(from + " not in graph!");
    }

    List<N> predsList = nodeToPreds.get(to);
    if (predsList == null) {
      throw new RuntimeException(to + " not in graph!");
    }

    heads.remove(to);
    tails.remove(from);

    if (!succsList.contains(to)) {
      succsList.add(to);
    }
    if (!predsList.contains(from)) {
      predsList.add(from);
    }

    DGEdge<N> edge = new DGEdge<>(from, to);
    edgeToLabels.putIfAbsent(edge, new ArrayList<>());
    List<L> labels = edgeToLabels.get(edge);

    labelToEdges.putIfAbsent(label, new ArrayList<>());
    List<DGEdge<N>> edges = labelToEdges.get(label);

    // if(!labels.contains(label))
    labels.add(label);
    // if(!edges.contains(edge))
    edges.add(edge);
  }

@Override
  public List<L> getLabelsForEdges(N from, N to) {
    DGEdge<N> edge = new DGEdge<>(from, to);
    return edgeToLabels.get(edge);
  }

@Override
  public MutableDirectedGraph<N> getEdgesForLabel(L label) {
    List<DGEdge<N>> edges = labelToEdges.get(label);
    MutableDirectedGraph<N> ret = new HashMutableDirectedGraph<>();
    if (edges == null) {
      return ret;
    }
    edges.forEach(edge -> {
      if (!ret.containsNode(edge.from())) {
        ret.addNode(edge.from());
      }
      if (!ret.containsNode(edge.to())) {
        ret.addNode(edge.to());
      }
      ret.addEdge(edge.from(), edge.to());
    });
    return ret;
  }

@Override
  public void removeEdge(N from, N to, L label) {
    if (!containsEdge(from, to, label)) {
      return;
    }

    DGEdge<N> edge = new DGEdge<>(from, to);
    List<L> labels = edgeToLabels.get(edge);
    if (labels == null) {
      throw new RuntimeException(new StringBuilder().append("edge ").append(edge).append(" not in graph!").toString());
    }

    List<DGEdge<N>> edges = labelToEdges.get(label);
    if (edges == null) {
      throw new RuntimeException(new StringBuilder().append("label ").append(label).append(" not in graph!").toString());
    }

    labels.remove(label);
    edges.remove(edge);

    // if this edge has no more labels, then it's gone!
    if (labels.isEmpty()) {
      edgeToLabels.remove(edge);

      List<N> succsList = nodeToSuccs.get(from);
      if (succsList == null) {
        throw new RuntimeException(from + " not in graph!");
      }

      List<N> predsList = nodeToPreds.get(to);
      if (predsList == null) {
        throw new RuntimeException(to + " not in graph!");
      }

      succsList.remove(to);
      predsList.remove(from);

      if (succsList.isEmpty()) {
        tails.add(from);
      }

      if (predsList.isEmpty()) {
        heads.add(to);
      }
    }

    // if this label has no more edges, then who cares?
    if (edges.isEmpty()) {
      labelToEdges.remove(label);
    }
  }

@Override
  public void removeAllEdges(N from, N to) {
    if (!containsAnyEdge(from, to)) {
      return;
    }

    DGEdge<N> edge = new DGEdge<>(from, to);
    List<L> labels = edgeToLabels.get(edge);
    if (labels == null) {
      throw new RuntimeException(new StringBuilder().append("edge ").append(edge).append(" not in graph!").toString());
    }

    getCopy(labels).forEach(label -> removeEdge(from, to, label));
  }

@Override
  public void removeAllEdges(L label) {
    if (!containsAnyEdge(label)) {
      return;
    }

    List<DGEdge<N>> edges = labelToEdges.get(label);
    if (edges == null) {
      throw new RuntimeException(new StringBuilder().append("label ").append(label).append(" not in graph!").toString());
    }

    getCopy(edges).forEach(edge -> removeEdge(edge.from(), edge.to(), label));
  }

@Override

  public boolean containsEdge(N from, N to, L label) {
    List<L> labels = edgeToLabels.get(new DGEdge<>(from, to));
    return labels != null && labels.contains(label);
  }

@Override

  public boolean containsAnyEdge(N from, N to) {
    List<L> labels = edgeToLabels.get(new DGEdge<>(from, to));
    return labels != null && !labels.isEmpty();
  }

@Override

  public boolean containsAnyEdge(L label) {
    List<DGEdge<N>> edges = labelToEdges.get(label);
    return edges != null && !edges.isEmpty();
  }

@Override
  public boolean containsNode(N node) {
    return nodeToPreds.keySet().contains(node);
  }

@Override
  public void addNode(N node) {
    if (containsNode(node)) {
      throw new RuntimeException("Node already in graph");
    }

    nodeToSuccs.put(node, new ArrayList<>());
    nodeToPreds.put(node, new ArrayList<>());
    heads.add(node);
    tails.add(node);
  }

@Override
  public void removeNode(N node) {
    new ArrayList<N>(nodeToSuccs.get(node)).forEach(n -> removeAllEdges(node, n));
    nodeToSuccs.remove(node);

    new ArrayList<N>(nodeToPreds.get(node)).forEach(n -> removeAllEdges(n, node));
    nodeToPreds.remove(node);

    heads.remove(node);
    tails.remove(node);
  }

public void printGraph() {
    for (N node : this) {
      logger.debug("Node = " + node);

      logger.debug("Preds:");
      getPredsOf(node).forEach(pred -> {
        DGEdge<N> edge = new DGEdge<>(pred, node);
        List<L> labels = edgeToLabels.get(edge);
        logger.debug(new StringBuilder().append("     ").append(pred).append(" [").append(labels).append("]").toString());
      });

      logger.debug("Succs:");
      getSuccsOf(node).forEach(succ -> {
        DGEdge<N> edge = new DGEdge<>(node, succ);
        List<L> labels = edgeToLabels.get(edge);
        logger.debug(new StringBuilder().append("     ").append(succ).append(" [").append(labels).append("]").toString());
      });
    }
  }

  private static class DGEdge<N> {

    N from;
    N to;

    public DGEdge(N from, N to) {
      this.from = from;
      this.to = to;
    }

    public N from() {
      return from;
    }

    public N to() {
      return to;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof DGEdge)) {
		return false;
	}
	DGEdge<?> other = (DGEdge<?>) o;
	return from.equals(other.from) && to.equals(other.to);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(new Object[] { from, to });
    }
  }
}
