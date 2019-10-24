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
 * @production BoundTypeAccess : {@link TypeAccess} ::= <span class="component">&lt;TypeDecl:TypeDecl&gt;</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BoundNames.ast:8
 */
public class BoundTypeAccess extends TypeAccess {
  private static final Logger logger = LoggerFactory.getLogger(BoundTypeAccess.class);
/**
   * @apilevel internal
   * @ast method 
   * 
   */
  
  /**
   * @apilevel internal
   */
  protected TypeDecl tokenTypeDecl_TypeDecl;
/**
   * @apilevel internal
   */
  protected boolean decls_computed = false;
/**
   * @apilevel internal
   */
  protected SimpleSet decls_value;
/**
   * @ast method 
   * 
   */
  public BoundTypeAccess() {


  }
/**
   * @ast method 
   * 
   */
  public BoundTypeAccess(String p0, String p1, TypeDecl p2) {
    setPackage(p0);
    setID(p1);
    setTypeDecl(p2);
  }
/**
   * @ast method 
   * 
   */
  public BoundTypeAccess(beaver.Symbol p0, beaver.Symbol p1, TypeDecl p2) {
    setPackage(p0);
    setID(p1);
    setTypeDecl(p2);
  }
/**
   * @apilevel low-level
   */
  @Override
public void flushCache() {
    super.flushCache();
    decls_computed = false;
    decls_value = null;
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
  public BoundTypeAccess clone() throws CloneNotSupportedException {
    BoundTypeAccess node = (BoundTypeAccess)super.clone();
    node.decls_computed = false;
    node.decls_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public BoundTypeAccess copy() {
    try {
      BoundTypeAccess node = (BoundTypeAccess) clone();
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
  public BoundTypeAccess fullCopy() {
    BoundTypeAccess tree = (BoundTypeAccess) copy();
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
   * @aspect GenericsTypeAnalysis
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:333
   */
  @Override
public boolean isRaw() {
    return getTypeDecl().isRawType();
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
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
protected int numChildren() {
    return 0;
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
   * Replaces the lexeme Package.
   * @param value The new value for the lexeme Package.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setPackage(String value) {
    tokenString_Package = value;
  }
/**
   * JastAdd-internal setter for lexeme Package using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
  @Override
public void setPackage(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String)) {
		throw new UnsupportedOperationException("setPackage is only valid for String lexemes");
	}
    tokenString_Package = (String)symbol.value;
    Packagestart = symbol.getStart();
    Packageend = symbol.getEnd();
  }
/**
   * Retrieves the value for the lexeme Package.
   * @return The value for the lexeme Package.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public String getPackage() {
    return tokenString_Package != null ? tokenString_Package : "";
  }
/**
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setID(String value) {
    tokenString_ID = value;
  }
/**
   * JastAdd-internal setter for lexeme ID using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
  @Override
public void setID(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String)) {
		throw new UnsupportedOperationException("setID is only valid for String lexemes");
	}
    tokenString_ID = (String)symbol.value;
    IDstart = symbol.getStart();
    IDend = symbol.getEnd();
  }
/**
   * Retrieves the value for the lexeme ID.
   * @return The value for the lexeme ID.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
/**
   * Replaces the lexeme TypeDecl.
   * @param value The new value for the lexeme TypeDecl.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setTypeDecl(TypeDecl value) {
    tokenTypeDecl_TypeDecl = value;
  }
/**
   * Retrieves the value for the lexeme TypeDecl.
   * @return The value for the lexeme TypeDecl.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public TypeDecl getTypeDecl() {
    return tokenTypeDecl_TypeDecl;
  }
/**
   * @attribute syn
   * @aspect BoundNames
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BoundNames.jrag:93
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public SimpleSet decls() {
    if(decls_computed) {
      return decls_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    decls_value = decls_compute();
      if(isFinal && num == state().boundariesCrossed) {
		decls_computed = true;
	}
    return decls_value;
  }
/**
   * @apilevel internal
   */
  private SimpleSet decls_compute() {  return SimpleSet.emptySet.add(getTypeDecl());  }
/**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:800
   */
  @Override
public String dumpString() {
    ASTNode$State state = state();
    try {  return new StringBuilder().append(getClass().getName()).append(" [").append(getTypeDecl().fullName()).append("]").toString();  }
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
