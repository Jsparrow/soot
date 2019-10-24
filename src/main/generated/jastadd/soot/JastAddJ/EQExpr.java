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
 * @production EQExpr : {@link EqualityExpr};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:182
 */
public class EQExpr extends EqualityExpr {
  private static final Logger logger = LoggerFactory.getLogger(EQExpr.class);
/**
   * @ast method 
   * 
   */
  public EQExpr() {


  }
/**
   * @ast method 
   * 
   */
  public EQExpr(Expr p0, Expr p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
/**
   * @apilevel low-level
   */
  @Override
public void flushCache() {
    super.flushCache();
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
  public EQExpr clone() throws CloneNotSupportedException {
    EQExpr node = (EQExpr)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public EQExpr copy() {
    try {
      EQExpr node = (EQExpr) clone();
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
  public EQExpr fullCopy() {
    EQExpr tree = (EQExpr) copy();
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
   * @aspect BooleanExpressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:294
   */
  @Override
public soot.Value comparison(Body b, soot.Value left, soot.Value right) {
    return b.newEqExpr(asImmediate(b, left), asImmediate(b, right), this);
  }
/**
   * @ast method 
   * @aspect BooleanExpressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/BooleanExpressions.jrag:316
   */
  @Override
public soot.Value comparisonInv(Body b, soot.Value left, soot.Value right) {
    return b.newNeExpr(asImmediate(b, left), asImmediate(b, right), this);
  }
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
    children = new ASTNode[2];
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
protected int numChildren() {
    return 2;
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
   * Replaces the LeftOperand child.
   * @param node The new node to replace the LeftOperand child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setLeftOperand(Expr node) {
    setChild(node, 0);
  }
/**
   * Retrieves the LeftOperand child.
   * @return The current node used as the LeftOperand child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public Expr getLeftOperand() {
    return (Expr)getChild(0);
  }
/**
   * Retrieves the LeftOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the LeftOperand child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public Expr getLeftOperandNoTransform() {
    return (Expr)getChildNoTransform(0);
  }
/**
   * Replaces the RightOperand child.
   * @param node The new node to replace the RightOperand child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setRightOperand(Expr node) {
    setChild(node, 1);
  }
/**
   * Retrieves the RightOperand child.
   * @return The current node used as the RightOperand child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public Expr getRightOperand() {
    return (Expr)getChild(1);
  }
/**
   * Retrieves the RightOperand child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the RightOperand child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public Expr getRightOperandNoTransform() {
    return (Expr)getChildNoTransform(1);
  }
/**
   * @attribute syn
   * @aspect ConstantExpression
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/ConstantExpression.jrag:91
   */
  @Override
public Constant constant() {
    ASTNode$State state = state();
    try {  return Constant.create(binaryNumericPromotedType().eqIsTrue(left(), right()));  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:400
   */
  @Override
public String printOp() {
    ASTNode$State state = state();
    try {  return " == ";  }
    finally {
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
