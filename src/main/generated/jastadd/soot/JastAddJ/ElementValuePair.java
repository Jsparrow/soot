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
 * @production ElementValuePair : {@link ASTNode} ::= <span class="component">&lt;Name:String&gt;</span> <span class="component">{@link ElementValue}</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.ast:8
 */
public class ElementValuePair extends ASTNode<ASTNode> {
  private static final Logger logger = LoggerFactory.getLogger(ElementValuePair.class);
/**
   * @apilevel internal
   * @ast method 
   * 
   */
  
  /**
   * @apilevel internal
   */
  protected String tokenString_Name;
/**
   * @ast method 
   * 
   */
  
  public int Namestart;
/**
   * @ast method 
   * 
   */
  
  public int Nameend;
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
  public ElementValuePair() {


  }
/**
   * @ast method 
   * 
   */
  public ElementValuePair(String p0, ElementValue p1) {
    setName(p0);
    setChild(p1, 0);
  }
/**
   * @ast method 
   * 
   */
  public ElementValuePair(beaver.Symbol p0, ElementValue p1) {
    setName(p0);
    setChild(p1, 0);
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
  public ElementValuePair clone() throws CloneNotSupportedException {
    ElementValuePair node = (ElementValuePair)super.clone();
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
  public ElementValuePair copy() {
    try {
      ElementValuePair node = (ElementValuePair) clone();
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
  public ElementValuePair fullCopy() {
    ElementValuePair tree = (ElementValuePair) copy();
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
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:504
   */
  @Override
public void typeCheck() {
    if(!type().commensurateWith(getElementValue())) {
		error(new StringBuilder().append("can not construct annotation with ").append(getName()).append(" = ").append(getElementValue().toString()).append("; ").append(type().typeName())
				.append(" is not commensurate with ").append(getElementValue().type().typeName()).toString());
	}
  }
/**
   * @ast method 
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:593
   */
  @Override
public void toString(StringBuffer s) {
    s.append(getName() + " = ");
    getElementValue().toString(s);
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
    return true;
  }
/**
   * Replaces the lexeme Name.
   * @param value The new value for the lexeme Name.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setName(String value) {
    tokenString_Name = value;
  }
/**
   * JastAdd-internal setter for lexeme Name using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
  public void setName(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String)) {
		throw new UnsupportedOperationException("setName is only valid for String lexemes");
	}
    tokenString_Name = (String)symbol.value;
    Namestart = symbol.getStart();
    Nameend = symbol.getEnd();
  }
/**
   * Retrieves the value for the lexeme Name.
   * @return The value for the lexeme Name.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public String getName() {
    return tokenString_Name != null ? tokenString_Name : "";
  }
/**
   * Replaces the ElementValue child.
   * @param node The new node to replace the ElementValue child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setElementValue(ElementValue node) {
    setChild(node, 0);
  }
/**
   * Retrieves the ElementValue child.
   * @return The current node used as the ElementValue child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public ElementValue getElementValue() {
    return (ElementValue)getChild(0);
  }
/**
   * Retrieves the ElementValue child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the ElementValue child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  public ElementValue getElementValueNoTransform() {
    return (ElementValue)getChildNoTransform(0);
  }
/* The annotation type named by an annotation must be accessible (\ufffd6.6) at the
  point where the annotation is used, or a compile-time error occurs.
  Comment: This is done by the access control framework* @attribute syn
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:450
   */
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
  private TypeDecl type_compute() {
    Map methodMap = enclosingAnnotationDecl().localMethodsSignatureMap();
    MethodDecl method = (MethodDecl) methodMap.get(getName()+"()");
    if (method != null) {
		return method.type();
	} else {
		return unknownType();
	}
  }
/**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:458
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl unknownType() {
    ASTNode$State state = state();
    TypeDecl unknownType_value = getParent().Define_TypeDecl_unknownType(this, null);
    return unknownType_value;
  }
/**
   * @attribute inh
   * @aspect Annotations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:460
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl enclosingAnnotationDecl() {
    ASTNode$State state = state();
    TypeDecl enclosingAnnotationDecl_value = getParent().Define_TypeDecl_enclosingAnnotationDecl(this, null);
    return enclosingAnnotationDecl_value;
  }
/**
   * @apilevel internal
   */
  @Override
public ASTNode rewriteTo() {
    // Declared in /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag at line 527
	if (!(type().isArrayDecl() && getElementValue() instanceof ElementConstantValue)) {
		return super.rewriteTo();
	}
	state().duringAnnotations++;
	ASTNode result = rewriteRule0();
	state().duringAnnotations--;
	return result;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:527
   * @apilevel internal
   */  private ElementValuePair rewriteRule0() {
{
      setElementValue(new ElementArrayValue(new List().add(getElementValue())));
      return this;
    }  }
}
