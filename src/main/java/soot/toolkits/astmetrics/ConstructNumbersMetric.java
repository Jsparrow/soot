package soot.toolkits.astmetrics;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2006 Nomair A. Naeem
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

import polyglot.ast.Block;
import polyglot.ast.Do;
import polyglot.ast.For;
import polyglot.ast.If;
import polyglot.ast.Labeled;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.ast.While;
import polyglot.visit.NodeVisitor;

/*
 * Calculate the number of different Java Constructs present in the
 * code
 */
public class ConstructNumbersMetric extends ASTMetric {

  private int numIf;

private int numIfElse;

  private int numLabeledBlocks;

  private int doLoop;

private int forLoop;

private int whileLoop;

private int whileTrue;

  public ConstructNumbersMetric(Node node) {
    super(node);
  }

  @Override
public void reset() {
    numIf = numIfElse = 0;
    numLabeledBlocks = 0;
    doLoop = forLoop = whileLoop = whileTrue = 0;
  }

  @Override
public void addMetrics(ClassData data) {
    // TODO Auto-generated method stub
    // conditionals
    data.addMetric(new MetricData("If", Integer.valueOf(numIf)));
    data.addMetric(new MetricData("IfElse", Integer.valueOf(numIfElse)));
    data.addMetric(new MetricData("Total-Conditionals", Integer.valueOf(numIf + numIfElse)));

    // labels
    data.addMetric(new MetricData("LabelBlock", Integer.valueOf(numLabeledBlocks)));

    // loops
    data.addMetric(new MetricData("Do", Integer.valueOf(doLoop)));
    data.addMetric(new MetricData("For", Integer.valueOf(forLoop)));
    data.addMetric(new MetricData("While", Integer.valueOf(whileLoop)));
    data.addMetric(new MetricData("UnConditional", Integer.valueOf(whileTrue)));
    data.addMetric(new MetricData("Total Loops", Integer.valueOf(whileTrue + whileLoop + forLoop + doLoop)));
  }

  @Override
public NodeVisitor enter(Node parent, Node n) {

    /*
     * Num if and ifelse
     */
    if (n instanceof If) {
      // check if there is the "optional" else branch present
      If ifNode = (If) n;
      Stmt temp = ifNode.alternative();
      if (temp == null) {
        // else branch is empty
        // System.out.println("This was an if stmt"+n);
        numIf++;
      } else {
        // else branch has something
        // System.out.println("This was an ifElse stmt"+n);
        numIfElse++;
      }
    }

    /*
     * Num Labeled Blocks
     */
    if (n instanceof Labeled) {
      Stmt s = ((Labeled) n).statement();
      // System.out.println("labeled"+((Labeled)n).label());
      if (s instanceof Block) {
        // System.out.println("labeled block with label"+((Labeled)n).label());
        numLabeledBlocks++;
      }
    }

    /*
     * Do
     */
    if (n instanceof Do) {
      // System.out.println((Do)n);
      doLoop++;
    }
    /*
     * For
     */
    if (n instanceof For) {
      // System.out.println((For)n);
      forLoop++;
    }

    /*
     * While and While True loop
     */
    if (n instanceof While) {
      // System.out.println((While)n);
      if (((While) n).condIsConstantTrue()) {
        whileTrue++;
      } else {
        whileLoop++;
      }
    }

    return enter(n);
  }

}
