package soot.toolkits.graph.pdg;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 - 2010 Hossein Sadat-Mohtasham
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.options.Options;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.UnitGraph;

/**
 * This represents a region of control dependence obtained by constructing a program dependence graph. A PDGRegion is
 * slightly different than a weak or strong region; the loops and conditional relations between regions are explicitly
 * represented in the PDGRegion.
 *
 * @author Hossein Sadat-Mohtasham Sep 2009
 */

public class PDGRegion implements IRegion, Iterable<PDGNode> {
  private static final Logger logger = LoggerFactory.getLogger(PDGRegion.class);

  private SootClass mClass = null;
  private SootMethod mMethod = null;
  private List<PDGNode> mNodes = null;
  private List<Unit> mUnits = null;
  private LinkedHashMap<Unit, PDGNode> mUnit2pdgnode = null;
  private int mId = -1;
  private UnitGraph mUnitGraph = null;
  private PDGNode mCorrspondingPdgNode = null;
  // The following are needed to create a tree of regions based on the containment (dependency)
  // relation between regions.
  private IRegion mParent = null;
  // The following keeps the child regions
  private List<IRegion> mChildren = new ArrayList<>();

  public PDGRegion(int id, SootMethod m, SootClass c, UnitGraph ug, PDGNode node) {
    this(id, new ArrayList<PDGNode>(), m, c, ug, node);

  }

  public PDGRegion(int id, List<PDGNode> nodes, SootMethod m, SootClass c, UnitGraph ug, PDGNode node) {

    this.mNodes = nodes;
    this.mId = id;
    this.mMethod = m;
    this.mClass = c;
    this.mUnitGraph = ug;
    this.mUnits = null;
    this.mCorrspondingPdgNode = node;

    if (Options.v().verbose()) {
      logger.debug("New pdg region create: " + id);
    }

  }

  public PDGRegion(PDGNode node) {
    this(((IRegion) node.getNode()).getID(), (List<PDGNode>) new ArrayList<PDGNode>(),
        ((IRegion) node.getNode()).getSootMethod(), ((IRegion) node.getNode()).getSootClass(),
        ((IRegion) node.getNode()).getUnitGraph(), node);

  }

  public PDGNode getCorrespondingPDGNode() {
    return this.mCorrspondingPdgNode;
  }

  @Override
@SuppressWarnings("unchecked")
  public Object clone() {
    PDGRegion r = new PDGRegion(this.mId, this.mMethod, this.mClass, this.mUnitGraph, mCorrspondingPdgNode);
    r.mNodes = (List<PDGNode>) ((ArrayList<PDGNode>) this.mNodes).clone();

    return r;

  }

  @Override
public SootMethod getSootMethod() {
    return this.mMethod;
  }

  @Override
public SootClass getSootClass() {
    return this.mClass;
  }

  public List<PDGNode> getNodes() {
    return this.mNodes;
  }

  @Override
public UnitGraph getUnitGraph() {
    return this.mUnitGraph;
  }

  /**
   * return an iterator that know how to follow the control flow in a region. This actually returns a ChildPDGFlowIterator
   * that only iterates through the dependent nodes that contribute to the units that belong to a region as defined by a weak
   * region.
   *
   */
  @Override
public Iterator<PDGNode> iterator() {
    return new ChildPDGFlowIterator(this.mNodes);
  }

@Override
public List<Unit> getUnits() {
    if (this.mUnits == null) {
      this.mUnits = new LinkedList<>();
      this.mUnit2pdgnode = new LinkedHashMap<>();

      for (Iterator<PDGNode> itr = this.iterator(); itr.hasNext();) {
        PDGNode node = itr.next();

        if (node.getType() == PDGNode.Type.REGION) {
          // Actually, we should only get here if a loop header region is in this region's children list.
          // Or if the PDG is based on an ExceptionalUnitGraph, then this could be the region corresponding
          // to a handler, in which case it's ignored.
          // if(node.getAttrib() == PDGNode.Attribute.LOOPHEADER)
          if (node instanceof LoopedPDGNode) {
            LoopedPDGNode n = (LoopedPDGNode) node;
            PDGNode header = n.getHeader();
            Block headerBlock = (Block) header.getNode();
            for (Unit u : headerBlock) {
              ((LinkedList<Unit>) this.mUnits).addLast(u);
              this.mUnit2pdgnode.put(u, header);

            }
          }

        } else if (node.getType() == PDGNode.Type.CFGNODE) {
          Block b = (Block) node.getNode();
          for (Unit u : b) {
            ((LinkedList<Unit>) this.mUnits).addLast(u);
            this.mUnit2pdgnode.put(u, node);

          }

        } else {
          throw new RuntimeException("Exception in PDGRegion.getUnits: PDGNode's type is undefined!");
        }

      }

    }
    return this.mUnits;
  }

/**
   *
   * @param a
   *          Statement within the region
   *
   * @return The PDGNode that contains that unit, if this unit is in this region.
   */
  public PDGNode unit2PDGNode(Unit u) {
    if (this.mUnit2pdgnode.containsKey(u)) {
      return this.mUnit2pdgnode.get(u);
    } else {
      return null;
    }
  }

@Override
public List<Unit> getUnits(Unit from, Unit to) {

    return mUnits.subList(mUnits.indexOf(from), mUnits.indexOf(to));

  }

@Override
public Unit getLast() {
    boolean condition = this.mUnits != null && this.mUnits.size() > 0;
	if (condition) {
        return ((LinkedList<Unit>) this.mUnits).getLast();
      }

    return null;
  }

@Override
public Unit getFirst() {
    boolean condition = this.mUnits != null && this.mUnits.size() > 0;
	if (condition) {
        return ((LinkedList<Unit>) this.mUnits).getFirst();
      }

    return null;
  }

// FIXME: return the real list of blocks
  @Override
public List<Block> getBlocks() {
    return new ArrayList<>();
  }

public void addPDGNode(PDGNode node) {
    this.mNodes.add(node);
  }

@Override
public int getID() {
    return this.mId;
  }

@Override
public boolean occursBefore(Unit u1, Unit u2) {
    int i = this.mUnits.lastIndexOf(u1);
    int j = this.mUnits.lastIndexOf(u2);

    if (i == -1 || j == -1) {
      throw new RuntimeException("These units don't exist in the region!");
    }

    return i < j;
  }

@Override
public void setParent(IRegion pr) {
    this.mParent = pr;
  }

@Override
public IRegion getParent() {
    return this.mParent;
  }

@Override
public void addChildRegion(IRegion chr) {
    if (!this.mChildren.contains(chr)) {
      this.mChildren.add(chr);
    }
  }

@Override
public List<IRegion> getChildRegions() {
    return this.mChildren;
  }

@Override
public String toString() {
    String str = "";
    str += new StringBuilder().append("Begin-----------PDGRegion:  ").append(this.mId).append("-------------\n").toString();
    if (this.mParent != null) {
      str += new StringBuilder().append("Parent is: ").append(this.mParent.getID()).append("----\n").toString();
    }
    str += "Children Regions are: ";

    for (Iterator<IRegion> ritr = this.mChildren.iterator(); ritr.hasNext();) {
      str += ((IRegion) ritr.next()).getID() + ", ";
    }

    str += "\nUnits are: \n";

    List<Unit> regionUnits = this.getUnits();
    for (Unit u : regionUnits) {
      str += u + "\n";

    }
    str += new StringBuilder().append("End of PDG Region ").append(this.mId).append(" -----------------------------\n").toString();

    return str;

  }

/**
   * This is an iterator that knows how to follow the control flow in a region. It only iterates through the dependent nodes
   * that contribute to the list of units in a region as defined by a weak region.
   *
   */
  class ChildPDGFlowIterator implements Iterator<PDGNode> {
    List<PDGNode> m_list = null;
    PDGNode m_current = null;
    boolean beginning = true;

    public ChildPDGFlowIterator(List<PDGNode> list) {
      m_list = list;
    }

    @Override
	public boolean hasNext() {
      boolean condition = beginning && m_list.size() > 0;
	if (condition) {
	  return true;
	}

      return (m_current != null && m_current.getNext() != null);
    }

    @Override
	public PDGNode next() {

      if (beginning) {
        beginning = false;
        m_current = m_list.get(0);
        // Find the first node in the control flow

        /*
         * There cannot be more than one CFG node in a region unless there is a control flow edge between them. However,
         * there could be a CFG node and other region nodes (back dependency, or other.) In such cases, the one CFG node
         * should be found and returned, and other region nodes should be ignored. Unless it's a LoopedPDGNode (in which case
         * control flow edge should still exist if there are other sibling Looped PDGNodes or CFG nodes.)
         *
         */
        while (m_current.getPrev() != null) {
          m_current = m_current.getPrev();
        }

        if (m_current.getType() != PDGNode.Type.CFGNODE && m_current.getAttrib() != PDGNode.Attribute.LOOPHEADER) {
          /*
           * Look for useful dependence whose units are considered to be part of this region (loop header or CFG block
           * nodes.)
           *
           */

          for (PDGNode dep : m_list) {
            if (dep.getType() == PDGNode.Type.CFGNODE || dep.getAttrib() == PDGNode.Attribute.LOOPHEADER) {
              m_current = dep;
              // go to the beginning of the flow
              while (m_current.getPrev() != null) {
                m_current = m_current.getPrev();
              }
              break;
            }
          }
        }
        return m_current;
      }

      if (!hasNext()) {
        throw new RuntimeException("No more nodes!");
      }
      m_current = m_current.getNext();
      return m_current;
    }

    @Override
	public void remove() {

    }

  }

}
