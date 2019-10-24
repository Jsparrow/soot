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
 * @production TypeImportOnDemandDecl : {@link ImportDecl};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:12
 */
public class TypeImportOnDemandDecl extends ImportDecl {
  private static final Logger logger = LoggerFactory.getLogger(TypeImportOnDemandDecl.class);
protected java.util.Map importedTypes_String_values;
/**
   * @ast method 
   * 
   */
  public TypeImportOnDemandDecl() {


  }
/**
   * @ast method 
   * 
   */
  public TypeImportOnDemandDecl(Access p0) {
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
  public TypeImportOnDemandDecl clone() throws CloneNotSupportedException {
    TypeImportOnDemandDecl node = (TypeImportOnDemandDecl)super.clone();
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
  public TypeImportOnDemandDecl copy() {
    try {
      TypeImportOnDemandDecl node = (TypeImportOnDemandDecl) clone();
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
  public TypeImportOnDemandDecl fullCopy() {
    TypeImportOnDemandDecl tree = (TypeImportOnDemandDecl) copy();
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
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NameCheck.jrag:30
   */
  @Override
public void nameCheck() {
    if(getAccess().lastAccess().isTypeAccess() && !getAccess().type().typeName().equals(typeName())) {
		error(new StringBuilder().append("On demand type import ").append(typeName()).append(".* is not the canonical name of type ").append(getAccess().type().typeName()).toString());
	}
  }
/**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:56
   */
  @Override
public void toString(StringBuffer s) {
    s.append("import ");
    getAccess().toString(s);
    s.append(".*;\n");
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
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupType.jrag:329
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
    if(getAccess() instanceof PackageAccess) {
      String packageName = ((PackageAccess)getAccess()).getPackage();
      TypeDecl typeDecl = lookupType(packageName, name);
      if(typeDecl != null && typeDecl.accessibleFromPackage(packageName()) &&
         typeDecl.typeName().equals(packageName + "." + name)) {
		set = set.add(typeDecl);
	}
    }
    else {
      for(Iterator iter = getAccess().type().memberTypes(name).iterator(); iter.hasNext(); ) {
        TypeDecl decl = (TypeDecl)iter.next();
        if(decl.accessibleFromPackage(packageName()) &&
           decl.typeName().equals(getAccess().typeName() + "." + name)) {
			set = set.add(decl);
		}
      }
    }
    return set;
  }
/**
   * @attribute syn
   * @aspect TypeScopePropagation
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupType.jrag:351
   */
  @Override
public boolean isOnDemand() {
    ASTNode$State state = state();
    try {  return true;  }
    finally {
    }
  }
/**
   * @attribute inh
   * @aspect TypeScopePropagation
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupType.jrag:348
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl lookupType(String packageName, String typeName) {
    ASTNode$State state = state();
    TypeDecl lookupType_String_String_value = getParent().Define_TypeDecl_lookupType(this, null, packageName, typeName);
    return lookupType_String_String_value;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:107
   * @apilevel internal
   */
  @Override
public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if(caller == getAccessNoTransform()) {
      return NameType.PACKAGE_OR_TYPE_NAME;
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
