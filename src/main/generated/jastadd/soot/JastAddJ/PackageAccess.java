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
 * @production PackageAccess : {@link Access} ::= <span class="component">&lt;Package:String&gt;</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:29
 */
public class PackageAccess extends Access {
  private static final Logger logger = LoggerFactory.getLogger(PackageAccess.class);
/**
   * @apilevel internal
   * @ast method 
   * 
   */
  
  /**
   * @apilevel internal
   */
  protected String tokenString_Package;
/**
   * @ast method 
   * 
   */
  
  public int Packagestart;
/**
   * @ast method 
   * 
   */
  
  public int Packageend;
/**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:18
   */
  public PackageAccess(String name, int start, int end) {
    this(name);
    this.start = this.Packagestart = start;
    this.end = this.Packageend = end;
  }
/**
   * @ast method 
   * 
   */
  public PackageAccess() {


  }
/**
   * @ast method 
   * 
   */
  public PackageAccess(String p0) {
    setPackage(p0);
  }
/**
   * @ast method 
   * 
   */
  public PackageAccess(beaver.Symbol p0) {
    setPackage(p0);
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
  public PackageAccess clone() throws CloneNotSupportedException {
    PackageAccess node = (PackageAccess)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public PackageAccess copy() {
    try {
      PackageAccess node = (PackageAccess) clone();
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
  public PackageAccess fullCopy() {
    PackageAccess tree = (PackageAccess) copy();
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
   * @aspect NameCheck
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NameCheck.jrag:50
   */
  @Override
public void nameCheck() {
    if(!hasPackage(packageName())) {
      error(packageName() + " not found");
    }
  }
/**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:510
   */
  @Override
public void toString(StringBuffer s) {
    s.append(getPackage());
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
  public void setPackage(String value) {
    tokenString_Package = value;
  }
/**
   * JastAdd-internal setter for lexeme Package using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
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
  public String getPackage() {
    return tokenString_Package != null ? tokenString_Package : "";
  }
/**
   * @attribute syn
   * @aspect LookupFullyQualifiedTypes
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupType.jrag:83
   */
  @Override
public boolean hasQualifiedPackage(String packageName) {
    ASTNode$State state = state();
    try {  return hasPackage(new StringBuilder().append(packageName()).append(".").append(packageName).toString());  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupType.jrag:430
   */
  @Override
public SimpleSet qualifiedLookupType(String name) {
    ASTNode$State state = state();
    try {
    SimpleSet c = SimpleSet.emptySet;
    TypeDecl typeDecl = lookupType(packageName(), name);
    if(nextAccess() instanceof ClassInstanceExpr) {
      if(typeDecl != null && typeDecl.accessibleFrom(hostType())) {
		c = c.add(typeDecl);
	}
      return c;
    }
    else {
      if(typeDecl != null) {
        if(hostType() != null && typeDecl.accessibleFrom(hostType())) {
			c = c.add(typeDecl);
		} else if(hostType() == null && typeDecl.accessibleFromPackage(hostPackage())) {
			c = c.add(typeDecl);
		}
      }
      return c;
    }
  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupVariable.jrag:148
   */
  @Override
public SimpleSet qualifiedLookupVariable(String name) {
    ASTNode$State state = state();
    try {  return SimpleSet.emptySet;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:800
   */
  @Override
public String dumpString() {
    ASTNode$State state = state();
    try {  return new StringBuilder().append(getClass().getName()).append(" [").append(getPackage()).append("]").toString();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Names
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/QualifiedNames.jrag:23
   */
  public String name() {
    ASTNode$State state = state();
    try {  return getPackage();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Names
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/QualifiedNames.jrag:25
   */
  @Override
public String packageName() {
    ASTNode$State state = state();
    try {
    StringBuilder s = new StringBuilder();
    if(hasPrevExpr()) {
      s.append(prevExpr().packageName());
      s.append(".");
    }
    s.append(getPackage());
    return s.toString();
  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect AccessTypes
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ResolveAmbiguousNames.jrag:37
   */
  @Override
public boolean isPackageAccess() {
    ASTNode$State state = state();
    try {  return true;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect SyntacticClassification
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:56
   */
  @Override
public NameType predNameType() {
    ASTNode$State state = state();
    try {  return NameType.PACKAGE_NAME;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect TypeHierarchyCheck
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeHierarchyCheck.jrag:20
   */
  @Override
public boolean isUnknown() {
    ASTNode$State state = state();
    try {  return !hasPackage(packageName());  }
    finally {
    }
  }
/**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NameCheck.jrag:243
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public boolean hasPackage(String packageName) {
    ASTNode$State state = state();
    boolean hasPackage_String_value = getParent().Define_boolean_hasPackage(this, null, packageName);
    return hasPackage_String_value;
  }
/**
   * @apilevel internal
   */
  @Override
public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
