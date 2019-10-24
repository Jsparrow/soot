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
/**
 * @production AssignBitwiseExpr : {@link AssignExpr};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:124
 */
public abstract class AssignBitwiseExpr extends AssignExpr {
  /**
	   * @ast method 
	   * 
	   */
	  public AssignBitwiseExpr() {
	
	
	  }
	/**
	   * @ast method 
	   * 
	   */
	  public AssignBitwiseExpr(Expr p0, Expr p1) {
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
	  public AssignBitwiseExpr clone() throws CloneNotSupportedException {
	    AssignBitwiseExpr node = (AssignBitwiseExpr)super.clone();
	    node.in$Circle(false);
	    node.is$Final(false);
	    return node;
	  }
	/**
	   * @ast method 
	   * @aspect TypeCheck
	   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeCheck.jrag:98
	   */
	  @Override
	public void typeCheck() {
	    TypeDecl source = sourceType();
	    TypeDecl dest = getDest().type();
	    if(source.isIntegralType() && dest.isIntegralType()) {
			super.typeCheck();
		} else if(source.isBoolean() && dest.isBoolean()) {
			super.typeCheck();
		} else {
			error("Operator only operates on integral and boolean types");
		}
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
	   * Replaces the Dest child.
	   * @param node The new node to replace the Dest child.
	   * @apilevel high-level
	   * @ast method 
	   * 
	   */
	  @Override
	public void setDest(Expr node) {
	    setChild(node, 0);
	  }
	/**
	   * Retrieves the Dest child.
	   * @return The current node used as the Dest child.
	   * @apilevel high-level
	   * @ast method 
	   * 
	   */
	  @Override
	public Expr getDest() {
	    return (Expr)getChild(0);
	  }
	/**
	   * Retrieves the Dest child.
	   * <p><em>This method does not invoke AST transformations.</em></p>
	   * @return The current node used as the Dest child.
	   * @apilevel low-level
	   * @ast method 
	   * 
	   */
	  @Override
	public Expr getDestNoTransform() {
	    return (Expr)getChildNoTransform(0);
	  }
	/**
	   * Replaces the Source child.
	   * @param node The new node to replace the Source child.
	   * @apilevel high-level
	   * @ast method 
	   * 
	   */
	  @Override
	public void setSource(Expr node) {
	    setChild(node, 1);
	  }
	/**
	   * Retrieves the Source child.
	   * @return The current node used as the Source child.
	   * @apilevel high-level
	   * @ast method 
	   * 
	   */
	  @Override
	public Expr getSource() {
	    return (Expr)getChild(1);
	  }
	/**
	   * Retrieves the Source child.
	   * <p><em>This method does not invoke AST transformations.</em></p>
	   * @return The current node used as the Source child.
	   * @apilevel low-level
	   * @ast method 
	   * 
	   */
	  @Override
	public Expr getSourceNoTransform() {
	    return (Expr)getChildNoTransform(1);
	  }
	/**
	   * @apilevel internal
	   */
	  @Override
	public ASTNode rewriteTo() {
	    return super.rewriteTo();
	  }
}
