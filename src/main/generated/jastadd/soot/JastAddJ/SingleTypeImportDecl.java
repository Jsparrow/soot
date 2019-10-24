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
 * @production SingleTypeImportDecl : {@link ImportDecl};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:11
 */
public class SingleTypeImportDecl extends ImportDecl {
  private static final Logger logger = LoggerFactory.getLogger(SingleTypeImportDecl.class);
protected java.util.Map importedTypes_String_values;
/**
   * @ast method 
   * 
   */
  public SingleTypeImportDecl() {


  }
/**
   * @ast method 
   * 
   */
  public SingleTypeImportDecl(Access p0) {
    setChild(p0, 0);
  }
/**
   * @apilevel low-level
   */
  @Override
public void flushCache() {
    super.flushCache();
    importedTypes_String_values = null;
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
  public SingleTypeImportDecl clone() throws CloneNotSupportedException {
    SingleTypeImportDecl node = (SingleTypeImportDecl)super.clone();
    node.importedTypes_String_values = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public SingleTypeImportDecl copy() {
    try {
      SingleTypeImportDecl node = (SingleTypeImportDecl) clone();
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
  public SingleTypeImportDecl fullCopy() {
    SingleTypeImportDecl tree = (SingleTypeImportDecl) copy();
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
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NameCheck.jrag:20
   */
  @Override
public void nameCheck() {
    if(!getAccess().type().typeName().equals(typeName()) && !getAccess().type().isUnknown()) {
		error(new StringBuilder().append("Single-type import ").append(typeName()).append(" is not the canonical name of type ").append(getAccess().type().typeName()).toString());
	} else if(allImportedTypes(getAccess().type().name()).size() > 1) {
		error(getAccess().type().name() + " is imported multiple times");
	}
  }
/**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:50
   */
  @Override
public void toString(StringBuffer s) {
    s.append("import ");
    getAccess().toString(s);
    s.append(";\n");
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
   * Replaces the Access child.
   * @param node The new node to replace the Access child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setAccess(Access node) {
    setChild(node, 0);
  }
/**
   * Retrieves the Access child.
   * @return The current node used as the Access child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public Access getAccess() {
    return (Access)getChild(0);
  }
/**
   * Retrieves the Access child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Access child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public Access getAccessNoTransform() {
    return (Access)getChildNoTransform(0);
  }
/**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupType.jrag:323
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public SimpleSet importedTypes(String name) {
    Object _parameters = name;
    if(importedTypes_String_values == null) {
		importedTypes_String_values = new java.util.HashMap(4);
	}
    if(importedTypes_String_values.containsKey(_parameters)) {
      return (SimpleSet)importedTypes_String_values.get(_parameters);
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    SimpleSet importedTypes_String_value = importedTypes_compute(name);
      if(isFinal && num == state().boundariesCrossed) {
		importedTypes_String_values.put(_parameters, importedTypes_String_value);
	}
    return importedTypes_String_value;
  }
/**
   * @apilevel internal
   */
  private SimpleSet importedTypes_compute(String name) {
    SimpleSet set = SimpleSet.emptySet;
    if(getAccess().type().name().equals(name)) {
		set = set.add(getAccess().type());
	}
    return set;
  }
/**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NameCheck.jrag:26
   */
  @SuppressWarnings({"unchecked", "cast"})
  public SimpleSet allImportedTypes(String name) {
    ASTNode$State state = state();
    SimpleSet allImportedTypes_String_value = getParent().Define_SimpleSet_allImportedTypes(this, null, name);
    return allImportedTypes_String_value;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:72
   * @apilevel internal
   */
  @Override
public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if(caller == getAccessNoTransform()) {
      return NameType.TYPE_NAME;
    }
    else {      return getParent().Define_NameType_nameType(this, caller);
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
