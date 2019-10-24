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
 * @production Dot : {@link AbstractDot};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:17
 */
public class Dot extends AbstractDot {
  private static final Logger logger = LoggerFactory.getLogger(Dot.class);
/**
   * @ast method 
   * 
   */
  public Dot() {


  }
/**
   * @ast method 
   * 
   */
  public Dot(Expr p0, Access p1) {
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
  public Dot clone() throws CloneNotSupportedException {
    Dot node = (Dot)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public Dot copy() {
    try {
      Dot node = (Dot) clone();
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
  public Dot fullCopy() {
    Dot tree = (Dot) copy();
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
   * @aspect QualifiedNames
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:99
   */
  public Dot lastDot() {
    Dot node = this;
    while(node.getRightNoTransform() instanceof Dot) {
		node = (Dot)node.getRightNoTransform();
	}
    return node;
  }
/**
   * @ast method 
   * @aspect QualifiedNames
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:113
   */
  @Override
public Dot qualifiesAccess(Access access) {
	  Dot lastDot = lastDot();
	  Expr l = lastDot.getRightNoTransform();
	  Dot dot = new Dot(lastDot.getRightNoTransform(), access);
	  dot.setStart(l.getStart());
	  dot.setEnd(access.getEnd());
	  lastDot.setRight(dot);
	  return this;
  }
/**
   * @ast method 
   * @aspect QualifiedNames
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:124
   */
  private Access qualifyTailWith(Access expr) {
    if (!(getRight/*NoTransform*/() instanceof AbstractDot)) {
		return expr;
	}
	AbstractDot dot = (AbstractDot)getRight/*NoTransform*/();
	return expr.qualifiesAccess(dot.getRight/*NoTransform*/());
  }
/**
   * @ast method 
   * @aspect QualifiedNames
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:141
   */
  @Override
public Access extractLast() {
    return lastDot().getRightNoTransform();
  }
/**
   * @ast method 
   * @aspect QualifiedNames
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:144
   */
  @Override
public void replaceLast(Access access) {
    lastDot().setRight(access);
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
    return true;
  }
/**
   * Replaces the Left child.
   * @param node The new node to replace the Left child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setLeft(Expr node) {
    setChild(node, 0);
  }
/**
   * Retrieves the Left child.
   * @return The current node used as the Left child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public Expr getLeft() {
    return (Expr)getChild(0);
  }
/**
   * Retrieves the Left child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Left child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public Expr getLeftNoTransform() {
    return (Expr)getChildNoTransform(0);
  }
/**
   * Replaces the Right child.
   * @param node The new node to replace the Right child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setRight(Access node) {
    setChild(node, 1);
  }
/**
   * Retrieves the Right child.
   * @return The current node used as the Right child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public Access getRight() {
    return (Access)getChild(1);
  }
/**
   * Retrieves the Right child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Right child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public Access getRightNoTransform() {
    return (Access)getChildNoTransform(1);
  }
/**
   * @apilevel internal
   */
  @Override
public ASTNode rewriteTo() {
    // Declared in /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag at line 210
    if(!duringSyntacticClassification() && leftSide().isPackageAccess() && rightSide().isPackageAccess()) {
      state().duringNameResolution++;
      ASTNode result = rewriteRule0();
      state().duringNameResolution--;
      return result;
    }

    // Declared in /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag at line 222
	if (!(!duringSyntacticClassification() && leftSide().isPackageAccess() && !((Access)leftSide()).hasPrevExpr() && rightSide() instanceof TypeAccess)) {
		return super.rewriteTo();
	}
	state().duringNameResolution++;
	ASTNode result = rewriteRule1();
	state().duringNameResolution--;
	return result;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:210
   * @apilevel internal
   */  private Access rewriteRule0() {
{
      PackageAccess left = (PackageAccess)leftSide();
      PackageAccess right = (PackageAccess)rightSide();
      left.setPackage(new StringBuilder().append(left.getPackage()).append(".").append(right.getPackage()).toString());
      left.setEnd(right.end());
      return qualifyTailWith(left);
    }  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:222
   * @apilevel internal
   */  private Access rewriteRule1() {
{
      PackageAccess left = (PackageAccess)leftSide();
      TypeAccess right = (TypeAccess)rightSide();
      right.setPackage(left.getPackage());
      right.setStart(left.start());
      return qualifyTailWith(right);
    }  }
}
