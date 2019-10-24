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
 * @production BitNotExpr : {@link Unary};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:141
 */
public class BitNotExpr extends Unary {
  private static final Logger logger = LoggerFactory.getLogger(BitNotExpr.class);
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
  public BitNotExpr() {


  }
/**
   * @ast method 
   * 
   */
  public BitNotExpr(Expr p0) {
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
  public BitNotExpr clone() throws CloneNotSupportedException {
    BitNotExpr node = (BitNotExpr)super.clone();
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
  public BitNotExpr copy() {
    try {
      BitNotExpr node = (BitNotExpr) clone();
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
  public BitNotExpr fullCopy() {
    BitNotExpr tree = (BitNotExpr) copy();
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
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeCheck.jrag:281
   */
  @Override
public void typeCheck() {
    if(!getOperand().type().isIntegralType()) {
		error("unary ~ only operates on integral types");
	}
  }
/**
   * @ast method 
   * @aspect Expressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/Expressions.jrag:696
   */
  @Override
public soot.Value eval(Body b) {
    soot.Value v = IntType.emitConstant(-1);
    soot.Local result = asLocal(b,
      b.newXorExpr(
        asImmediate(b, typeInt().emitCastTo(b, v, type(), this)),
        asImmediate(b, getOperand().eval(b)),
        this
      )
    );
    return result;
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
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/ConstantExpression.jrag:91
   */
  @Override
public Constant constant() {
    ASTNode$State state = state();
    try {  return type().bitNot(getOperand().constant());  }
    finally {
    }
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
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:376
   */
  @Override
public String printPreOp() {
    ASTNode$State state = state();
    try {  return "~";  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:317
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
  private TypeDecl type_compute() {  return getOperand().type().unaryNumericPromotion();  }
/**
   * @apilevel internal
   */
  @Override
public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
