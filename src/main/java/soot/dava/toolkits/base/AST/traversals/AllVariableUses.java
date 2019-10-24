package soot.dava.toolkits.base.AST.traversals;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2006 Nomair A. Naeem (nomair.naeem@mail.mcgill.ca)
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
import java.util.Iterator;
import java.util.List;

import soot.Local;
import soot.SootField;
import soot.Value;
import soot.ValueBox;
import soot.dava.internal.AST.ASTAggregatedCondition;
import soot.dava.internal.AST.ASTBinaryCondition;
import soot.dava.internal.AST.ASTCondition;
import soot.dava.internal.AST.ASTDoWhileNode;
import soot.dava.internal.AST.ASTForLoopNode;
import soot.dava.internal.AST.ASTIfElseNode;
import soot.dava.internal.AST.ASTIfNode;
import soot.dava.internal.AST.ASTMethodNode;
import soot.dava.internal.AST.ASTNode;
import soot.dava.internal.AST.ASTStatementSequenceNode;
import soot.dava.internal.AST.ASTSwitchNode;
import soot.dava.internal.AST.ASTSynchronizedBlockNode;
import soot.dava.internal.AST.ASTUnaryCondition;
import soot.dava.internal.AST.ASTWhileNode;
import soot.dava.internal.asg.AugmentedStmt;
import soot.dava.toolkits.base.AST.analysis.DepthFirstAdapter;
import soot.jimple.FieldRef;
import soot.jimple.Stmt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Creates a mapping of locals and all places where they might be used
 * creates a mapping of fields and all places where they might be used
 *    Notice that the mapping is for SootField to uses not for FieldRef to uses
 */
public class AllVariableUses extends DepthFirstAdapter {
  private static final Logger logger = LoggerFactory.getLogger(AllVariableUses.class);

ASTMethodNode methodNode;

  HashMap<Local, List> localsToUses;
  HashMap<SootField, List> fieldsToUses;

  public AllVariableUses(ASTMethodNode node) {
    this.methodNode = node;
    init();
  }

  public AllVariableUses(boolean verbose, ASTMethodNode node) {
    super(verbose);
    this.methodNode = node;
    init();
  }

  public void init() {
    localsToUses = new HashMap<>();
    fieldsToUses = new HashMap<>();
  }

  /*
   * Notice as things stand synchblocks cant have the use of a SootField
   */
  @Override
public void inASTSynchronizedBlockNode(ASTSynchronizedBlockNode node) {
    Local local = node.getLocal();
    addLocalUse(local, node);
  }

  /*
   * The key in a switch stmt can be a local or a SootField or a value which can contain Locals or SootFields
   *
   * Hence the some what indirect approach
   */
  @Override
public void inASTSwitchNode(ASTSwitchNode node) {
    Value val = node.get_Key();
    List<Value> localUses = new ArrayList<>();
    List<Value> fieldUses = new ArrayList<>();

    if (val instanceof Local) {
      localUses.add(val);
      logger.info(new StringBuilder().append("Added ").append(val).append(" to local uses for switch").toString());
    } else if (val instanceof FieldRef) {
      fieldUses.add(val);
      logger.info(new StringBuilder().append("Added ").append(val).append(" to field uses for switch").toString());
    } else {
      List useBoxes = val.getUseBoxes();

      List<Value> localsOrFieldRefs = getUsesFromBoxes(useBoxes);
      localsOrFieldRefs.forEach(temp -> {
        if (temp instanceof Local) {
          localUses.add(temp);
          logger.info(new StringBuilder().append("Added ").append(temp).append(" to local uses for switch").toString());
        } else if (temp instanceof FieldRef) {
          fieldUses.add(temp);
          logger.info(new StringBuilder().append("Added ").append(temp).append(" to field uses for switch").toString());
        }
      });
    }

    // localuses stores Locals used
    Iterator<Value> it = localUses.iterator();
    while (it.hasNext()) {
      Local local = (Local) it.next();

      addLocalUse(local, node);
    } // end of going through all locals uses in switch key

    // fieldUses stores FieldRef
    it = fieldUses.iterator();
    while (it.hasNext()) {
      FieldRef field = (FieldRef) it.next();
      SootField sootField = field.getField();

      addFieldUse(sootField, node);
    } // end of going through all FieldRef uses in switch key
  }

  @Override
public void inASTStatementSequenceNode(ASTStatementSequenceNode node) {
    // in the case of stmtts in a stmtt sequence each stmt is considered
	// an entity
	// compared to the case where these stmts occur within other
	// constructs
	// where the node is the entity
	node.getStatements().stream().map(AugmentedStmt::get_Stmt).forEach(s -> checkStatementUses(s, s));
  }

  /*
   * The init of a for loop can use a local/Sootfield The condition of a for node can use a local/SootField The update in a
   * for loop can use a local/SootField
   */
  @Override
public void inASTForLoopNode(ASTForLoopNode node) {

    // checking uses in init
	node.getInit().stream().map(AugmentedStmt::get_Stmt).forEach(s -> checkStatementUses(s, node));

    // checking uses in condition
    ASTCondition cond = node.get_Condition();
    checkConditionalUses(cond, node);

    // checking uses in update
	node.getUpdate().stream().map(AugmentedStmt::get_Stmt).forEach(s -> checkStatementUses(s, node));
  }

  public void checkStatementUses(Stmt s, Object useNodeOrStatement) {
    List useBoxes = s.getUseBoxes();

    // remeber getUsesFromBoxes returns both Locals and FieldRefs
    List<Value> uses = getUsesFromBoxes(useBoxes);

    uses.forEach(temp -> {
      if (temp instanceof Local) {
        addLocalUse((Local) temp, useNodeOrStatement);
      } else if (temp instanceof FieldRef) {
        FieldRef field = (FieldRef) temp;
        SootField sootField = field.getField();
        addFieldUse(sootField, useNodeOrStatement);
      }
    });
  }

  /*
   * This method gets a list of all uses of locals/Sootfield in the condition and stores a use by this node
   */
  public void checkConditionalUses(ASTCondition cond, ASTNode node) {
    List<Value> useList = getUseList(cond);

    // System.out.println("FOR NODE with condition:"+cond+"USE list is:"+useList);

    useList.forEach(temp -> {
      if (temp instanceof Local) {
        addLocalUse((Local) temp, node);
      } else if (temp instanceof FieldRef) {
        FieldRef field = (FieldRef) temp;
        SootField sootField = field.getField();
        addFieldUse(sootField, node);
      }
    } // end of going through all locals uses in condition
);
  }

  /*
   * The condition of an if node can use a local
   */
  @Override
public void inASTIfNode(ASTIfNode node) {
    ASTCondition cond = node.get_Condition();
    checkConditionalUses(cond, node);
  }

  /*
   * The condition of an ifElse node can use a local
   */
  @Override
public void inASTIfElseNode(ASTIfElseNode node) {
    ASTCondition cond = node.get_Condition();
    checkConditionalUses(cond, node);
  }

  /*
   * The condition of a while node can use a local
   */
  @Override
public void inASTWhileNode(ASTWhileNode node) {
    ASTCondition cond = node.get_Condition();
    checkConditionalUses(cond, node);
  }

  /*
   * The condition of a doWhile node can use a local
   */
  @Override
public void inASTDoWhileNode(ASTDoWhileNode node) {
    ASTCondition cond = node.get_Condition();
    checkConditionalUses(cond, node);
  }

  /*
   * Given a unary/binary or aggregated condition this method is used to find the locals/SootFields used in the condition
   *
   * @param The condition to be checked for Local or FieldRef uses
   *
   * @return a list containing all Locals and FieldRefs used in this condition
   */
  public List<Value> getUseList(ASTCondition cond) {
    ArrayList<Value> useList = new ArrayList<>();
    if (cond instanceof ASTAggregatedCondition) {
      useList.addAll(getUseList(((ASTAggregatedCondition) cond).getLeftOp()));
      useList.addAll(getUseList(((ASTAggregatedCondition) cond).getRightOp()));
      return useList;
    } else if (cond instanceof ASTUnaryCondition) {
      // get uses from unary condition
      List<Value> uses = new ArrayList<>();

      Value val = ((ASTUnaryCondition) cond).getValue();
      if (val instanceof Local || val instanceof FieldRef) {
        uses.add(val);
      } else {
        List useBoxes = val.getUseBoxes();
        uses = getUsesFromBoxes(useBoxes);
      }
      return uses;
    } else if (cond instanceof ASTBinaryCondition) {
      // get uses from binaryCondition
      Value val = ((ASTBinaryCondition) cond).getConditionExpr();
      List useBoxes = val.getUseBoxes();
      return getUsesFromBoxes(useBoxes);
    } else {
      throw new RuntimeException("Method getUseList in ASTUsesAndDefs encountered unknown condition type");
    }
  }

  private void addLocalUse(Local local, Object obj) {
    Object temp = localsToUses.get(local);
    List<Object> uses;
    if (temp == null) {
      uses = new ArrayList<>();
    } else {
      uses = (ArrayList<Object>) temp;
    }

    // add local to useList
    uses.add(obj);

    // update mapping
    localsToUses.put(local, uses);
  }

  private void addFieldUse(SootField field, Object obj) {

    Object temp = fieldsToUses.get(field);
    List<Object> uses;
    if (temp == null) {
      uses = new ArrayList<>();
    } else {
      uses = (ArrayList<Object>) temp;
    }

    // add field to useList
    uses.add(obj);

    // update mapping
    fieldsToUses.put(field, uses);
  }

  /*
   * Method is used to strip away boxes from the actual values only those are returned which are locals or FieldRefs
   */
  private List<Value> getUsesFromBoxes(List useBoxes) {
    ArrayList<Value> toReturn = new ArrayList<>();
    Iterator it = useBoxes.iterator();
    while (it.hasNext()) {
      Value val = ((ValueBox) it.next()).getValue();
      if (val instanceof Local || val instanceof FieldRef) {
        toReturn.add(val);
      }
    }
    // System.out.println("VALUES:"+toReturn);
    return toReturn;
  }

  public List getUsesForField(SootField field) {
    Object temp = fieldsToUses.get(field);
    if (temp == null) {
      return null;
    } else {
      return (List) temp;
    }
  }

  public List getUsesForLocal(Local local) {
    Object temp = localsToUses.get(local);
    if (temp == null) {
      return null;
    } else {
      return (List) temp;
    }
  }

}
