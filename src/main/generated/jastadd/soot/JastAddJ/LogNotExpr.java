/* This file was generated with JastAdd2 (http://jastadd.org) version R20130212 (r1031) */
package soot.JastAddJ;

import java.util.HashSet;
import java.io.File;
import java.util.*;
import beaver.*;
import java.util.ArrayList;
import java.util.zip.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Collection;
import soot.*;
import soot.util.*;
import soot.jimple.*;
import soot.coffi.ClassFile;
import soot.coffi.method_info;
import soot.coffi.CONSTANT_Utf8_info;
import soot.tagkit.SourceFileTag;
import soot.coffi.CoffiMethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @production LogNotExpr : {@link Unary};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:142
 */
public class LogNotExpr extends Unary {
  private static final Logger logger = LoggerFactory.getLogger(LogNotExpr.class);
/**
   * @apilevel internal
   */
  protected boolean type_computed = false;
/**
   * @apilevel internal
   */
  protected TypeDecl type_value;
/**
   * @ast method 
   * 
   */
  public LogNotExpr() {


  }
/**
   * @ast method 
   * 
   */
  public LogNotExpr(Expr p0) {
    setChild(p0, 0);
  }
/**
   * @apilevel low-level
   */
  @Override
public void flushCache() {
    super.flushCache();
    type_computed = false;
    type_value = null;
  }
/**
   * @apilevel internal
   */
  @Override
public void flushCollectionCache() {
    super.flushCollectionCache();
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public LogNotExpr clone() throws CloneNotSupportedException {
    LogNotExpr node = (LogNotExpr)super.clone();
    node.type_computed = false;
    node.type_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public LogNotExpr copy() {
    try {
      LogNotExpr node = (LogNotExpr) clone();
      node.parent = null;
      if(children != null) {
		node.children = (ASTNode[]) children.clone();
	}
      return node;
    } catch (CloneNotSupportedException e) {
      logger.error(e.getMessage(), e);
	throw new Error("Error: clone not supported for " +
        getClass().getName());
    }
  }
/**
   * Create a deep copy of the AST subtree at this node.
   * The copy is dangling, i.e. has no parent.
   * @return dangling copy of the subtree at this node
   * @apilevel low-level
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public LogNotExpr fullCopy() {
    LogNotExpr tree = (LogNotExpr) copy();
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        ASTNode child = (ASTNode) children[i];
        if(child != null) {
          child = child.fullCopy();
          tree.setChild(child, i);
        }
      }
    }
    return tree;
  }
/**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeCheck.jrag:287
   */
  @Override
public void typeCheck() {
    if(!getOperand().type().isBoolean()) {
		error("unary ! only operates on boolean types");
	}
  }
/**
   * @ast method 
   * @aspect BooleanExpressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:103
   */
  @Override
public soot.Value eval(Body b) { return emitBooleanCondition(b); }
/**
   * @ast method 
   * @aspect BooleanExpressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:182
   */
  @Override
public void emitEvalBranch(Body b)  { getOperand().emitEvalBranch(b); }
/**
   * Initializes the child array to the correct size.
   * Initializes List and Opt nta children.
   * @apilevel internal
   * @ast method
   * @ast method 
   * 
   */
  @Override
public void init$Children() {
    children = new ASTNode[1];
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
protected int numChildren() {
    return 1;
  }
/**
   * @apilevel internal
   * @ast method 
   * 
   */
  @Override
public boolean mayHaveRewrite() {
    return false;
  }
/**
   * Replaces the Operand child.
   * @param node The new node to replace the Operand child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setOperand(Expr node) {
    setChild(node, 0);
  }
/**
   * Retrieves the Operand child.
   * @return The current node used as the Operand child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public Expr getOperand() {
    return (Expr)getChild(0);
  }
/**
   * Retrieves the Operand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Operand child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public Expr getOperandNoTransform() {
    return (Expr)getChildNoTransform(0);
  }
/**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/ConstantExpression.jrag:336
   */
  @Override
public boolean isConstant() {
    ASTNode$State state = state();
    try {  return getOperand().isConstant();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/ConstantExpression.jrag:91
   */
  @Override
public Constant constant() {
    ASTNode$State state = state();
    try {  return Constant.create(!getOperand().constant().booleanValue());  }
    finally {
    }
  }
/*eq Stmt.isDAafter(Variable v) {
    //System.out.println("### isDAafter reached in " + getClass().getName());
    //throw new NullPointerException();
    throw new Error("Can not compute isDAafter for " + getClass().getName() + " at " + errorPrefix());
  }* @attribute syn
   * @aspect DA
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:332
   */
  @Override
public boolean isDAafterTrue(Variable v) {
    ASTNode$State state = state();
    try {  return getOperand().isDAafterFalse(v) || isFalse();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DA
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:333
   */
  @Override
public boolean isDAafterFalse(Variable v) {
    ASTNode$State state = state();
    try {  return getOperand().isDAafterTrue(v) || isTrue();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DA
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:235
   */
  @Override
public boolean isDAafter(Variable v) {
    ASTNode$State state = state();
    try {  return isDAafterTrue(v) && isDAafterFalse(v);  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DU
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:702
   */
  @Override
public boolean isDUafterTrue(Variable v) {
    ASTNode$State state = state();
    try {  return getOperand().isDUafterFalse(v);  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DU
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:703
   */
  @Override
public boolean isDUafterFalse(Variable v) {
    ASTNode$State state = state();
    try {  return getOperand().isDUafterTrue(v);  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DU
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:694
   */
  @Override
public boolean isDUafter(Variable v) {
    ASTNode$State state = state();
    try {  return isDUafterTrue(v) && isDUafterFalse(v);  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:376
   */
  @Override
public String printPreOp() {
    ASTNode$State state = state();
    try {  return "!";  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:318
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public TypeDecl type() {
    if(type_computed) {
      return type_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    type_value = type_compute();
      if(isFinal && num == state().boundariesCrossed) {
		type_computed = true;
	}
    return type_value;
  }
/**
   * @apilevel internal
   */
  private TypeDecl type_compute() {  return typeBoolean();  }
/**
   * @attribute syn
   * @aspect BooleanExpressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:21
   */
  @Override
public boolean definesLabel() {
    ASTNode$State state = state();
    try {  return true;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect BooleanExpressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:82
   */
  @Override
public boolean canBeTrue() {
    ASTNode$State state = state();
    try {  return getOperand().canBeFalse();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect BooleanExpressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:92
   */
  @Override
public boolean canBeFalse() {
    ASTNode$State state = state();
    try {  return getOperand().canBeTrue();  }
    finally {
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:380
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getOperandNoTransform()) {
      return isDAbefore(v);
    }
    else {      return getParent().Define_boolean_isDAbefore(this, caller, v);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:816
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getOperandNoTransform()) {
      return isDUbefore(v);
    }
    else {      return getParent().Define_boolean_isDUbefore(this, caller, v);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:57
   * @apilevel internal
   */
  @Override
public soot.jimple.Stmt Define_soot_jimple_Stmt_condition_false_label(ASTNode caller, ASTNode child) {
    if(caller == getOperandNoTransform()) {
      return true_label();
    }
    else {      return getParent().Define_soot_jimple_Stmt_condition_false_label(this, caller);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:58
   * @apilevel internal
   */
  @Override
public soot.jimple.Stmt Define_soot_jimple_Stmt_condition_true_label(ASTNode caller, ASTNode child) {
    if(caller == getOperandNoTransform()) {
      return false_label();
    }
    else {      return getParent().Define_soot_jimple_Stmt_condition_true_label(this, caller);
    }
  }
/**
   * @apilevel internal
   */
  @Override
public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
