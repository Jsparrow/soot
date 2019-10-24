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
 * @production AnnotatedCompilationUnit : {@link CompilationUnit} ::= <span class="component">{@link Modifiers}</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.ast:16
 */
public class AnnotatedCompilationUnit extends CompilationUnit {
  private static final Logger logger = LoggerFactory.getLogger(AnnotatedCompilationUnit.class);
/**
   * @ast method 
   * 
   */
  public AnnotatedCompilationUnit() {


  }
/**
   * @ast method 
   * 
   */
  public AnnotatedCompilationUnit(java.lang.String p0, List<ImportDecl> p1, List<TypeDecl> p2, Modifiers p3) {
    setPackageDecl(p0);
    setChild(p1, 0);
    setChild(p2, 1);
    setChild(p3, 2);
  }
/**
   * @ast method 
   * 
   */
  public AnnotatedCompilationUnit(beaver.Symbol p0, List<ImportDecl> p1, List<TypeDecl> p2, Modifiers p3) {
    setPackageDecl(p0);
    setChild(p1, 0);
    setChild(p2, 1);
    setChild(p3, 2);
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
  public AnnotatedCompilationUnit clone() throws CloneNotSupportedException {
    AnnotatedCompilationUnit node = (AnnotatedCompilationUnit)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public AnnotatedCompilationUnit copy() {
    try {
      AnnotatedCompilationUnit node = (AnnotatedCompilationUnit) clone();
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
  public AnnotatedCompilationUnit fullCopy() {
    AnnotatedCompilationUnit tree = (AnnotatedCompilationUnit) copy();
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
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:21
   */
  @Override
public void nameCheck() {
    super.nameCheck();
    if(!relativeName().endsWith("package-info.java")) {
		error("package annotations should be in a file package-info.java");
	}
  }
/**
   * @ast method 
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:557
   */
  @Override
public void toString(StringBuffer s) {
      getModifiers().toString(s);
      super.toString(s);
  }
/**
   * @ast method 
   * @aspect AnnotationsCodegen
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/Jimple1.5Backend/AnnotationsCodegen.jrag:11
   */
  @Override
public void jimplify1phase2() {
    super.jimplify1phase2();
    ArrayList c = new ArrayList();
    getModifiers().addAllAnnotations(c);
    for(Iterator iter = c.iterator(); iter.hasNext(); ) {
      soot.tagkit.Tag tag = (soot.tagkit.Tag)iter.next();
      //host.addTag(tag);
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
    children = new ASTNode[3];
    setChild(new List(), 0);
    setChild(new List(), 1);
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
protected int numChildren() {
    return 3;
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
   * Replaces the lexeme PackageDecl.
   * @param value The new value for the lexeme PackageDecl.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setPackageDecl(java.lang.String value) {
    tokenjava_lang_String_PackageDecl = value;
  }
/**
   * JastAdd-internal setter for lexeme PackageDecl using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
  @Override
public void setPackageDecl(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String)) {
		throw new UnsupportedOperationException("setPackageDecl is only valid for String lexemes");
	}
    tokenjava_lang_String_PackageDecl = (String)symbol.value;
    PackageDeclstart = symbol.getStart();
    PackageDeclend = symbol.getEnd();
  }
/**
   * Retrieves the value for the lexeme PackageDecl.
   * @return The value for the lexeme PackageDecl.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public java.lang.String getPackageDecl() {
    return tokenjava_lang_String_PackageDecl != null ? tokenjava_lang_String_PackageDecl : "";
  }
/**
   * Replaces the ImportDecl list.
   * @param list The new list node to be used as the ImportDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setImportDeclList(List<ImportDecl> list) {
    setChild(list, 0);
  }
/**
   * Retrieves the number of children in the ImportDecl list.
   * @return Number of children in the ImportDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public int getNumImportDecl() {
    return getImportDeclList().getNumChild();
  }
/**
   * Retrieves the number of children in the ImportDecl list.
   * Calling this method will not trigger rewrites..
   * @return Number of children in the ImportDecl list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public int getNumImportDeclNoTransform() {
    return getImportDeclListNoTransform().getNumChildNoTransform();
  }
/**
   * Retrieves the element at index {@code i} in the ImportDecl list..
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the ImportDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public ImportDecl getImportDecl(int i) {
    return (ImportDecl)getImportDeclList().getChild(i);
  }
/**
   * Append an element to the ImportDecl list.
   * @param node The element to append to the ImportDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void addImportDecl(ImportDecl node) {
    List<ImportDecl> list = (parent == null || state == null) ? getImportDeclListNoTransform() : getImportDeclList();
    list.addChild(node);
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public void addImportDeclNoTransform(ImportDecl node) {
    List<ImportDecl> list = getImportDeclListNoTransform();
    list.addChild(node);
  }
/**
   * Replaces the ImportDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setImportDecl(ImportDecl node, int i) {
    List<ImportDecl> list = getImportDeclList();
    list.setChild(node, i);
  }
/**
   * Retrieves the ImportDecl list.
   * @return The node representing the ImportDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public List<ImportDecl> getImportDecls() {
    return getImportDeclList();
  }
/**
   * Retrieves the ImportDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ImportDecl list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public List<ImportDecl> getImportDeclsNoTransform() {
    return getImportDeclListNoTransform();
  }
/**
   * Retrieves the ImportDecl list.
   * @return The node representing the ImportDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public List<ImportDecl> getImportDeclList() {
    List<ImportDecl> list = (List<ImportDecl>)getChild(0);
    list.getNumChild();
    return list;
  }
/**
   * Retrieves the ImportDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the ImportDecl list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public List<ImportDecl> getImportDeclListNoTransform() {
    return (List<ImportDecl>)getChildNoTransform(0);
  }
/**
   * Replaces the TypeDecl list.
   * @param list The new list node to be used as the TypeDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setTypeDeclList(List<TypeDecl> list) {
    setChild(list, 1);
  }
/**
   * Retrieves the number of children in the TypeDecl list.
   * @return Number of children in the TypeDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public int getNumTypeDecl() {
    return getTypeDeclList().getNumChild();
  }
/**
   * Retrieves the number of children in the TypeDecl list.
   * Calling this method will not trigger rewrites..
   * @return Number of children in the TypeDecl list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public int getNumTypeDeclNoTransform() {
    return getTypeDeclListNoTransform().getNumChildNoTransform();
  }
/**
   * Retrieves the element at index {@code i} in the TypeDecl list..
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public TypeDecl getTypeDecl(int i) {
    return (TypeDecl)getTypeDeclList().getChild(i);
  }
/**
   * Append an element to the TypeDecl list.
   * @param node The element to append to the TypeDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void addTypeDecl(TypeDecl node) {
    List<TypeDecl> list = (parent == null || state == null) ? getTypeDeclListNoTransform() : getTypeDeclList();
    list.addChild(node);
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public void addTypeDeclNoTransform(TypeDecl node) {
    List<TypeDecl> list = getTypeDeclListNoTransform();
    list.addChild(node);
  }
/**
   * Replaces the TypeDecl list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setTypeDecl(TypeDecl node, int i) {
    List<TypeDecl> list = getTypeDeclList();
    list.setChild(node, i);
  }
/**
   * Retrieves the TypeDecl list.
   * @return The node representing the TypeDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public List<TypeDecl> getTypeDecls() {
    return getTypeDeclList();
  }
/**
   * Retrieves the TypeDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeDecl list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public List<TypeDecl> getTypeDeclsNoTransform() {
    return getTypeDeclListNoTransform();
  }
/**
   * Retrieves the TypeDecl list.
   * @return The node representing the TypeDecl list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public List<TypeDecl> getTypeDeclList() {
    List<TypeDecl> list = (List<TypeDecl>)getChild(1);
    list.getNumChild();
    return list;
  }
/**
   * Retrieves the TypeDecl list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeDecl list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public List<TypeDecl> getTypeDeclListNoTransform() {
    return (List<TypeDecl>)getChildNoTransform(1);
  }
/**
   * Replaces the Modifiers child.
   * @param node The new node to replace the Modifiers child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setModifiers(Modifiers node) {
    setChild(node, 2);
  }
/**
   * Retrieves the Modifiers child.
   * @return The current node used as the Modifiers child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public Modifiers getModifiers() {
    return (Modifiers)getChild(2);
  }
/**
   * Retrieves the Modifiers child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Modifiers child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  public Modifiers getModifiersNoTransform() {
    return (Modifiers)getChildNoTransform(2);
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:71
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    if(caller == getModifiersNoTransform()) {
      return "PACKAGE".equals(name);
    }
    else {      return getParent().Define_boolean_mayUseAnnotationTarget(this, caller, name);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:552
   * @apilevel internal
   */
  @Override
public String Define_String_hostPackage(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return packageName();
    }
    else {      return super.Define_String_hostPackage(caller, child);
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
