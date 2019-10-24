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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.Body;
import soot.G;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.options.Options;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.BlockGraph;
import soot.toolkits.graph.BriefBlockGraph;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.DominatorNode;
import soot.toolkits.graph.DominatorTree;
import soot.toolkits.graph.ExceptionalBlockGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.MHGDominatorsFinder;
import soot.toolkits.graph.MHGPostDominatorsFinder;
import soot.toolkits.graph.UnitGraph;

/**
 * This class computes the set of weak regions for a given method. It is based on the algorithm given in the following paper:
 *
 * Ball, T. 1993. What's in a region?: or computing control dependence regions in near-linear time for reducible control
 * flow. ACM Lett. Program. Lang. Syst. 2, 1-4 (Mar. 1993), 1-16. DOI= http://doi.acm.org/10.1145/176454.176456
 *
 * @author Hossein Sadat-Mohtasham Jan 2009
 */

public class RegionAnalysis {
  private static final Logger logger = LoggerFactory.getLogger(RegionAnalysis.class);

  protected SootClass m_class = null;
  protected SootMethod m_method = null;
  protected Body m_methodBody;
  protected UnitGraph m_cfg;
  protected UnitGraph m_reverseCFG;
  protected BlockGraph m_blockCFG;
  protected BlockGraph m_reverseBlockCFG;
  protected Hashtable<Integer, Region> m_regions = new Hashtable<>();
  protected List<Region> m_regionsList = null;
  private int mRegCount = 0;
  private MHGDominatorTree<Block> mDom;
  // this would actually be the postdominator tree in the original CFG
  private MHGDominatorTree<Block> mPdom;
  protected Region m_topLevelRegion = null;
  protected Hashtable<Block, Region> m_block2region = null;

  public RegionAnalysis(UnitGraph cfg, SootMethod m, SootClass c) {
    this.m_methodBody = cfg.getBody();
    this.m_cfg = cfg;
    this.m_method = m;
    this.m_class = c;

    if (Options.v().verbose()) {
      logger.debug(
          new StringBuilder().append("[RegionAnalysis]~~~~~~~~~~~~~~~ Begin Region Analsis for method: ").append(m.getName()).append(" ~~~~~~~~~~~~~~~~~~~~").toString());
    }
    this.findWeakRegions();
    if (Options.v().verbose()) {
      logger.debug(new StringBuilder().append("[RegionAnalysis]~~~~~~~~~~~~~~~ End:").append(m.getName()).append(" ~~~~~~~~~~~~~~~~~~~~").toString());
    }
  }

  private void findWeakRegions() {

    /*
     * Check to see what kind of CFG has been passed in and create the appropriate block CFG. Note that almost all of the
     * processing is done on the block CFG.
     */

    if (this.m_cfg instanceof ExceptionalUnitGraph) {
      this.m_blockCFG = new ExceptionalBlockGraph((ExceptionalUnitGraph) this.m_cfg);
    } else if (this.m_cfg instanceof EnhancedUnitGraph) {
      this.m_blockCFG = new EnhancedBlockGraph((EnhancedUnitGraph) this.m_cfg);
    } else if (this.m_cfg instanceof BriefUnitGraph) {
      this.m_blockCFG = new BriefBlockGraph((BriefUnitGraph) this.m_cfg);
    } else {
      throw new RuntimeException("Unsupported CFG passed into the RegionAnalyis constructor!");
    }

    this.mDom = new MHGDominatorTree<>(new MHGDominatorsFinder<Block>(this.m_blockCFG));

    try {

      this.mPdom = new MHGDominatorTree<>(new MHGPostDominatorsFinder<Block>(m_blockCFG));

      if (Options.v().verbose()) {
        logger.debug("[RegionAnalysis] PostDominator tree: ");
      }

      this.mRegCount = -1;

      /*
       * If a Brief graph or Exceptional graph is used, the CFG might be multi-headed and/or multi-tailed, which in turn,
       * could result in a post-dominator forest instead of tree. If the post-dominator tree has multiple heads, the
       * weakRegionDFS does not work correctly because it is designed based on the assumption that there is an auxiliary STOP
       * node in the CFG that post-dominates all other nodes. In fact, most of the CFG algorithms augment the control flow
       * graph with two nodes: ENTRY and EXIT (or START and STOP) nodes. We have not added these nodes since the CFG here is
       * created from the Jimple code and to be consistent we'd have to transform the code to reflect these nodes. Instead,
       * we implemted the EnhancedUnitGraph (EnhancedBlockGraph) which is guaranteed to be single-headed single-tailed. But
       * note that EnhancedUnitGraph represents exceptional flow differently.
       *
       *
       */

      if (this.m_blockCFG.getHeads().size() == 1) {
        this.mRegCount++;
        this.m_regions.put(this.mRegCount, this.createRegion(this.mRegCount));
        this.weakRegionDFS2(this.m_blockCFG.getHeads().get(0), this.mRegCount);
      } else if (this.m_blockCFG.getTails().size() == 1) {
        this.mRegCount++;
        this.m_regions.put(this.mRegCount, this.createRegion(this.mRegCount));
        this.weakRegionDFS(this.m_blockCFG.getTails().get(0), this.mRegCount);

      } else {
        if (Options.v().verbose()) {
          logger.warn(
              "RegionAnalysis: the CFG is multi-headed and tailed, so, the results of this analysis might not be reliable!");
        }

        for (int i = 0; i < this.m_blockCFG.getTails().size(); i++) {
          this.mRegCount++;
          this.m_regions.put(this.mRegCount, this.createRegion(this.mRegCount));
          this.weakRegionDFS(this.m_blockCFG.getTails().get(i), this.mRegCount);

        }
        // throw new RuntimeException("RegionAnalysis: cannot properly deal with multi-headed and tailed CFG!");
      }

    } catch (RuntimeException e) {
      logger.debug("[RegionAnalysis] Exception in findWeakRegions: " + e);
    }

  }

  /**
   * This algorithms assumes that the first time it's called with a tail of the CFG. Then for each child node w of v in the
   * post-dominator tree, it compares the parent of v in the dominator tree with w and if they are the same, that means w
   * belongs to the same region as v, so weakRegionDFS is recursively called with w and the same region id as v. If the
   * comparison fails, then a new region is created and weakRegionDFS is called recursively with w but this time with the
   * newly created region id.
   *
   * @param v
   * @param r
   */
  private void weakRegionDFS(Block v, int r) {
    try {
      // System.out.println("##entered weakRegionDFS for region " + r);
      this.m_regions.get(r).add(v);

      DominatorNode<Block> parentOfV = this.mDom.getParentOf(this.mDom.getDode(v));
      Block u2 = (parentOfV == null) ? null : parentOfV.getGode();

      List<DominatorNode<Block>> children = this.mPdom.getChildrenOf(this.mPdom.getDode(v));
      children.forEach(w -> {
        Block u1 = w.getGode();

        if (u2 != null && u1.equals(u2)) {
          this.weakRegionDFS(w.getGode(), r);
        } else {
          this.mRegCount++;
          this.m_regions.put(this.mRegCount, this.createRegion(this.mRegCount));
          this.weakRegionDFS(w.getGode(), this.mRegCount);
        }
      });
    } catch (RuntimeException e) {
      logger.debug("[RegionAnalysis] Exception in weakRegionDFS: ", e);
      logger.debug(new StringBuilder().append("v is  ").append(v.toShortString()).append(" in region ").append(r).toString());
      G.v().out.flush();
    }
  }

  /**
   * This algorithm starts from a head node in the CFG and is exactly the same as the above with the difference that post
   * dominator and dominator trees switch positions.
   *
   * @param v
   * @param r
   */
  private void weakRegionDFS2(Block v, int r) {
    // regions keep an implicit order of the contained blocks so it matters where blocks are added
    // below.
    this.m_regions.get(r).add2Back(v);

    DominatorNode<Block> parentOfV = this.mPdom.getParentOf(this.mPdom.getDode(v));
    Block u2 = (parentOfV == null) ? null : parentOfV.getGode();

    List<DominatorNode<Block>> children = this.mDom.getChildrenOf(this.mDom.getDode(v));
    children.forEach(w -> {
      Block u1 = w.getGode();

      if (u2 != null && u1.equals(u2)) {
        this.weakRegionDFS2(w.getGode(), r);
      } else {
        this.mRegCount++;
        this.m_regions.put(this.mRegCount, this.createRegion(this.mRegCount));
        this.weakRegionDFS2(w.getGode(), this.mRegCount);
      }
    });

  }

  public List<Region> getRegions() {
    if (this.m_regionsList == null) {
      this.m_regionsList = new ArrayList<>(this.m_regions.values());
    }

    return this.m_regionsList;

  }

  public Hashtable<Unit, Region> getUnit2RegionMap() {
    Hashtable<Unit, Region> unit2region = new Hashtable<>();
    List<Region> regions = this.getRegions();

    regions.forEach(r -> {
      List<Unit> units = r.getUnits();
      units.forEach(u -> unit2region.put(u, r));
    });

    return unit2region;
  }

  public Hashtable<Block, Region> getBlock2RegionMap() {
    if (this.m_block2region == null) {
      this.m_block2region = new Hashtable<>();

      List<Region> regions = this.getRegions();

      regions.forEach(r -> {
        List<Block> blocks = r.getBlocks();
        blocks.forEach(u -> m_block2region.put(u, r));
      });
    }
    return this.m_block2region;

  }

  public BlockGraph getBlockCFG() {
    return this.m_blockCFG;
  }

  public DominatorTree<Block> getPostDominatorTree() {
    return this.mPdom;
  }

  public DominatorTree<Block> getDominatorTree() {
    return this.mDom;
  }

  public void reset() {
    this.m_regions.clear();
    this.m_regionsList.clear();
    this.m_regionsList = null;
    this.m_block2region.clear();
    this.m_block2region = null;

    mRegCount = 0;
  }

  /**
   * Create a region
   */

  protected Region createRegion(int id) {
    Region region = new Region(id, this.m_method, this.m_class, this.m_cfg);
    if (id == 0) {
      this.m_topLevelRegion = region;
    }

    return region;
  }

  public Region getTopLevelRegion() {
    return this.m_topLevelRegion;
  }

  public static String CFGtoString(DirectedGraph<Block> cfg, boolean blockDetail) {
    String s = "";
    s += new StringBuilder().append("Headers: ").append(cfg.getHeads().size()).append(" ").append(cfg.getHeads()).toString();
    for (Block node : cfg) {
      s += new StringBuilder().append("Node = ").append(node.toShortString()).append("\n").toString();
      s += "Preds:\n";
      for (Iterator<Block> predsIt = cfg.getPredsOf(node).iterator(); predsIt.hasNext();) {
        s += "     ";
        s += predsIt.next().toShortString() + "\n";
      }
      s += "Succs:\n";
      for (Iterator<Block> succsIt = cfg.getSuccsOf(node).iterator(); succsIt.hasNext();) {
        s += "     ";
        s += succsIt.next().toShortString() + "\n";
      }
    }

    if (blockDetail) {
      s += "Blocks Detail:";
      for (Block node : cfg) {
        s += node + "\n";
      }

    }

    return s;
  }

}
