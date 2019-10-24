package soot.jimple.toolkits.annotation.parity;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2003 Jennifer Lhotak
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

import static soot.jimple.toolkits.annotation.parity.ParityAnalysis.Parity.BOTTOM;
import static soot.jimple.toolkits.annotation.parity.ParityAnalysis.Parity.EVEN;
import static soot.jimple.toolkits.annotation.parity.ParityAnalysis.Parity.ODD;
import static soot.jimple.toolkits.annotation.parity.ParityAnalysis.Parity.TOP;
import static soot.jimple.toolkits.annotation.parity.ParityAnalysis.Parity.valueOf;

import java.util.HashMap;
import java.util.Map;

import soot.IntegerType;
import soot.Local;
import soot.LongType;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AddExpr;
import soot.jimple.ArithmeticConstant;
import soot.jimple.BinopExpr;
import soot.jimple.DefinitionStmt;
import soot.jimple.IntConstant;
import soot.jimple.LongConstant;
import soot.jimple.MulExpr;
import soot.jimple.SubExpr;
import soot.options.Options;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;
import soot.toolkits.scalar.LiveLocals;

// STEP 1: What are we computing?
// SETS OF PAIRS of form (X, T) => Use ArraySparseSet.
//
// STEP 2: Precisely define what we are computing.
// For each statement compute the parity of all variables
// in the program.
//
// STEP 3: Decide whether it is a backwards or forwards analysis.
// FORWARDS
//
//
public class ParityAnalysis extends ForwardFlowAnalysis<Unit, Map<Value, ParityAnalysis.Parity>> {
  private UnitGraph g;

	private LiveLocals filter;

	public ParityAnalysis(UnitGraph g, LiveLocals filter) {
	    super(g);
	    this.g = g;
	
	    this.filter = filter;
	
	    filterUnitToBeforeFlow = new HashMap<>();
	    buildBeforeFilterMap();
	
	    filterUnitToAfterFlow = new HashMap<>();
	
	    doAnalysis();
	
	  }

	public ParityAnalysis(UnitGraph g) {
	    super(g);
	    this.g = g;
	
	    doAnalysis();
	  }

	private void buildBeforeFilterMap() {
	
	    g.getBody().getUnits().forEach(s -> {
	      // if (!(s instanceof DefinitionStmt)) continue;
	      // Value left = ((DefinitionStmt)s).getLeftOp();
	      // if (!(left instanceof Local)) continue;
	
	      // if (!((left.getType() instanceof IntegerType) || (left.getType() instanceof LongType))) continue;
	
	      Map<Value, Parity> map = new HashMap<>();
	      filter.getLiveLocalsBefore(s).forEach(l -> map.put(l, BOTTOM));
	
	      filterUnitToBeforeFlow.put(s, map);
	    }
	    // System.out.println("init filtBeforeMap: "+filterUnitToBeforeFlow);
);
	  }

	// STEP 4: Is the merge operator union or intersection?
	  //
	  // merge | bottom | even | odd | top
	  // -------+--------+--------+-------+--------
	  // bottom | bottom | even | odd | top
	  // -------+--------+--------+-------+--------
	  // even | even | even | top | top
	  // -------+--------+--------+-------+--------
	  // odd | odd | top | odd | top
	  // -------+--------+--------+-------+--------
	  // top | top | top | top | top
	  //
	
	  @Override
	  protected void merge(Map<Value, Parity> inMap1, Map<Value, Parity> inMap2, Map<Value, Parity> outMap) {
	    inMap1.keySet().forEach(var1 -> {
	      // System.out.println(var1);
	      Parity inVal1 = inMap1.get(var1);
	      // System.out.println(inVal1);
	      Parity inVal2 = inMap2.get(var1);
	      // System.out.println(inVal2);
	      // System.out.println("before out "+outMap.get(var1));
	
	      if (inVal2 == null) {
	        outMap.put(var1, inVal1);
	      } else if (inVal1 == BOTTOM) {
	        outMap.put(var1, inVal2);
	      } else if (inVal2 == BOTTOM) {
	        outMap.put(var1, inVal1);
	      } else if ((inVal1 == EVEN) && (inVal2 == EVEN)) {
	        outMap.put(var1, EVEN);
	      } else if ((inVal1 == ODD) && (inVal2 == ODD)) {
	        outMap.put(var1, ODD);
	      } else {
	        outMap.put(var1, TOP);
	      }
	    });
	
	  }

	// STEP 5: Define flow equations.
	  // in(s) = ( out(s) minus defs(s) ) union uses(s)
	  //
	
	  @Override
	  protected void copy(Map<Value, Parity> sourceIn, Map<Value, Parity> destOut) {
	    destOut.clear();
	    destOut.putAll(sourceIn);
	  }

	// Parity Tests: even + even = even
	  // even + odd = odd
	  // odd + odd = even
	  //
	  // even * even = even
	  // even * odd = even
	  // odd * odd = odd
	  //
	  // constants are tested mod 2
	  //
	
	  private Parity getParity(Map<Value, Parity> in, Value val) {
	    // System.out.println("get Parity in: "+in);
	    if ((val instanceof AddExpr) | (val instanceof SubExpr)) {
	      Parity resVal1 = getParity(in, ((BinopExpr) val).getOp1());
	      Parity resVal2 = getParity(in, ((BinopExpr) val).getOp2());
	
	      if (resVal1 == TOP | resVal2 == TOP) {
	        return TOP;
	      }
	      if (resVal1 == BOTTOM | resVal2 == BOTTOM) {
	        return BOTTOM;
	      }
	      if (resVal1 == resVal2) {
	        return EVEN;
	      }
	
	      return ODD;
	    }
	
	    if (val instanceof MulExpr) {
	      Parity resVal1 = getParity(in, ((BinopExpr) val).getOp1());
	      Parity resVal2 = getParity(in, ((BinopExpr) val).getOp2());
	      if (resVal1 == TOP | resVal2 == TOP) {
	        return TOP;
	      }
	      if (resVal1 == BOTTOM | resVal2 == BOTTOM) {
	        return BOTTOM;
	      }
	      if (resVal1 == resVal2) {
	        return resVal1;
	      }
	
	      return EVEN;
	    }
	    if (val instanceof IntConstant) {
	      int value = ((IntConstant) val).value;
	      return valueOf(value);
	    }
	    if (val instanceof LongConstant) {
	      long value = ((LongConstant) val).value;
	      return valueOf(value);
	    }
	
	    Parity p = in.get(val);
	    if (p == null) {
	      return TOP;
	    }
	    return p;
	  }

	@Override
	  protected void flowThrough(Map<Value, Parity> in, Unit s, Map<Value, Parity> out) {
	
	    // copy in to out
	    out.putAll(in);
	
	    // for each stmt where leftOp is defintionStmt find the parity
	    // of rightOp and update parity to EVEN, ODD or TOP
	
	    // boolean useS = false;
	
	    if (s instanceof DefinitionStmt) {
	      Value left = ((DefinitionStmt) s).getLeftOp();
	      boolean condition = left instanceof Local && ((left.getType() instanceof IntegerType) || (left.getType() instanceof LongType));
		if (condition) {
		  // useS = true;
		  Value right = ((DefinitionStmt) s).getRightOp();
		  out.put(left, getParity(out, right));
		}
	    }
	
	    // get all use and def boxes of s
		// if use or def is int or long constant add their parity
		s.getUseAndDefBoxes().stream().map(ValueBox::getValue).forEach(val -> {
			// System.out.println("val: "+val.getClass());
			  if (val instanceof ArithmeticConstant) {
			    out.put(val, getParity(out, val));
			    // System.out.println("out map: "+out);
			  }
		});
	
	    // if (useS){
		// }
		if (!Options.v().interactive_mode()) {
			return;
		}
		buildAfterFilterMap(s);
		updateAfterFilterMap(s);
	  }

	private void buildAfterFilterMap(Unit s) {
	
	    Map<Value, Parity> map = new HashMap<>();
	    filter.getLiveLocalsAfter(s).forEach(local -> map.put(local, BOTTOM));
	    filterUnitToAfterFlow.put(s, map);
	    // System.out.println("built afterflow filter map: "+filterUnitToAfterFlow);
	  }

	// STEP 6: Determine value for start/end node, and
	  // initial approximation.
	  //
	  // start node: locals with BOTTOM
	  // initial approximation: locals with BOTTOM
	  @Override
	  protected Map<Value, Parity> entryInitialFlow() {
	    /*
	     * HashMap initMap = new HashMap();
	     *
	     * Chain locals = g.getBody().getLocals(); Iterator it = locals.iterator(); while (it.hasNext()) { initMap.put(it.next(),
	     * BOTTOM); } return initMap;
	     */
	
	    return newInitialFlow();
	  }

	private void updateBeforeFilterMap() {
	    filterUnitToBeforeFlow.keySet().forEach(s -> {
	      Map<Value, Parity> allData = getFlowBefore(s);
	      Map<Value, Parity> filterData = filterUnitToBeforeFlow.get(s);
	      filterUnitToBeforeFlow.put(s, updateFilter(allData, filterData));
	    });
	  }

	private void updateAfterFilterMap(Unit s) {
	    Map<Value, Parity> allData = getFlowAfter(s);
	    Map<Value, Parity> filterData = filterUnitToAfterFlow.get(s);
	    filterUnitToAfterFlow.put(s, updateFilter(allData, filterData));
	  }

	private Map<Value, Parity> updateFilter(Map<Value, Parity> allData, Map<Value, Parity> filterData) {
	
	    if (allData == null) {
	      return filterData;
	    }
	
	    filterData.keySet().forEach(v -> {
	      Parity d = allData.get(v);
	      if (d == null) {
	        filterData.remove(v);
	      } else {
	        filterData.put(v, d);
	      }
	    });
	
	    return filterData;
	  }

	@Override
	  protected Map<Value, Parity> newInitialFlow() {
	    Map<Value, Parity> initMap = new HashMap<>();
	
	    g.getBody().getLocals().forEach(l -> {
	      Type t = l.getType();
	      // System.out.println("next local: "+next);
	      if ((t instanceof IntegerType) || (t instanceof LongType)) {
	        initMap.put(l, BOTTOM);
	      }
	    });
	
	    g.getBody().getUseAndDefBoxes().stream().map(ValueBox::getValue).forEach(val -> {
			if (val instanceof ArithmeticConstant) {
			    initMap.put(val, getParity(initMap, val));
			  }
		});
	
	    if (Options.v().interactive_mode()) {
	      updateBeforeFilterMap();
	    }
	
	    return initMap;
	
	  }

	public enum Parity {
	    TOP, BOTTOM, EVEN, ODD;
	
	    static Parity valueOf(int v) {
	      return (v % 2) == 0 ? EVEN : ODD;
	    }
	
	    static Parity valueOf(long v) {
	      return (v % 2) == 0 ? EVEN : ODD;
	    }
	  }

}
