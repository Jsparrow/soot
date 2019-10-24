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
 * @production ElementAnnotationValue : {@link ElementValue} ::= <span class="component">{@link Annotation}</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.ast:12
 */
public class ElementAnnotationValue extends ElementValue {
  private static final Logger logger = LoggerFactory.getLogger(ElementAnnotationValue.class);
/**
   * @ast method 
   * 
   */
  public ElementAnnotationValue() {


  }
/**
   * @ast method 
   * 
   */
  public ElementAnnotationValue(Annotation p0) {
    setChild(p0, 0);
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
  public ElementAnnotationValue clone() throws CloneNotSupportedException {
    ElementAnnotationValue node = (ElementAnnotationValue)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public ElementAnnotationValue copy() {
    try {
      ElementAnnotationValue node = (ElementAnnotationValue) clone();
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
  public ElementAnnotationValue fullCopy() {
    ElementAnnotationValue tree = (ElementAnnotationValue) copy();
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
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:600
   */
  @Override
public void toString(StringBuffer s) {
    getAnnotation().toString(s);
  }
/**
   * @ast method 
   * @aspect AnnotationsCodegen
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/Jimple1.5Backend/AnnotationsCodegen.jrag:354
   */
  @Override
public void appendAsAttributeTo(Collection list, String name) {
    ArrayList elemVals = new ArrayList();
    getAnnotation().appendAsAttributeTo(elemVals);
    list.add(new soot.tagkit.AnnotationAnnotationElem((soot.tagkit.AnnotationTag)elemVals.get(0), '@', name));
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
   * Replaces the Annotation child.
   * @param node The new node to replace the Annotation child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setAnnotation(Annotation node) {
    setChild(node, 0);
  }
/**
   * Retrieves the Annotation child.
   * @return The current node used as the Annotation child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public Annotation getAnnotation() {
    return (Annotation)getChild(0);
  }
/**
   * Retrieves the Annotation child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Annotation child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  public Annotation getAnnotationNoTransform() {
    return (Annotation)getChildNoTransform(0);
  }
/**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:475
   */
  @Override
public boolean commensurateWithTypeDecl(TypeDecl type) {
    ASTNode$State state = state();
    try {
    return type() == type;
  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:510
   */
  @Override
public TypeDecl type() {
    ASTNode$State state = state();
    try {  return getAnnotation().type();  }
    finally {
    }
  }
/**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:423
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Annotation lookupAnnotation(TypeDecl typeDecl) {
    ASTNode$State state = state();
    Annotation lookupAnnotation_TypeDecl_value = getParent().Define_Annotation_lookupAnnotation(this, null, typeDecl);
    return lookupAnnotation_TypeDecl_value;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:95
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    if(caller == getAnnotationNoTransform()) {
      return true;
    }
    else {      return getParent().Define_boolean_mayUseAnnotationTarget(this, caller, name);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:427
   * @apilevel internal
   */
  @Override
public Annotation Define_Annotation_lookupAnnotation(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
    if(caller == getAnnotationNoTransform()) {
      return getAnnotation().type() == typeDecl ? getAnnotation() : lookupAnnotation(typeDecl);
    }
    else {      return getParent().Define_Annotation_lookupAnnotation(this, caller, typeDecl);
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
