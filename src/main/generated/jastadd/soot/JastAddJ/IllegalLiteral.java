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
 * Literal produced when the compiler tries to parse
 * a malformatted NumericLiteral.
 * This literal kind has an associated error message.
 * @production IllegalLiteral : {@link Literal};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.ast:11
 */
public class IllegalLiteral extends Literal {
  private static final Logger logger = LoggerFactory.getLogger(IllegalLiteral.class);
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
  public IllegalLiteral() {


  }
/**
   * @ast method 
   * 
   */
  public IllegalLiteral(String p0) {
    setLITERAL(p0);
  }
/**
   * @ast method 
   * 
   */
  public IllegalLiteral(beaver.Symbol p0) {
    setLITERAL(p0);
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
  public IllegalLiteral clone() throws CloneNotSupportedException {
    IllegalLiteral node = (IllegalLiteral)super.clone();
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
  public IllegalLiteral copy() {
    try {
      IllegalLiteral node = (IllegalLiteral) clone();
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
  public IllegalLiteral fullCopy() {
    IllegalLiteral tree = (IllegalLiteral) copy();
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
	 * Error processing for literals.
	 * Include the token range from parsing.
	 * @ast method 
   * @aspect Literals
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag:469
   */
  @Override
public void collectErrors() {
		int line = getLine(LITERALstart);
		int column = getColumn(LITERALstart);
		int endLine = getLine(LITERALend);
		int endColumn = getColumn(LITERALend);
		compilationUnit().errors.add(new Problem(sourceFile(),
					getLITERAL(), line, column, endLine, endColumn,
					Problem.Severity.ERROR, Problem.Kind.LEXICAL));
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
   * Replaces the lexeme LITERAL.
   * @param value The new value for the lexeme LITERAL.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setLITERAL(String value) {
    tokenString_LITERAL = value;
  }
/**
   * JastAdd-internal setter for lexeme LITERAL using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
  @Override
public void setLITERAL(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String)) {
		throw new UnsupportedOperationException("setLITERAL is only valid for String lexemes");
	}
    tokenString_LITERAL = (String)symbol.value;
    LITERALstart = symbol.getStart();
    LITERALend = symbol.getEnd();
  }
/**
   * Retrieves the value for the lexeme LITERAL.
   * @return The value for the lexeme LITERAL.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public String getLITERAL() {
    return tokenString_LITERAL != null ? tokenString_LITERAL : "";
  }
/**
	 * The type of an IllegalLiteral does not matter,
	 * as it is only a placeholder literal for error messages.
	 * @attribute syn
   * @aspect Literals
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag:457
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
  private TypeDecl type_compute() {  return unknownType();  }
/**
   * @apilevel internal
   */
  @Override
public ASTNode rewriteTo() {
    return super.rewriteTo();
  }
}
