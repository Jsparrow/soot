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
 * Default Java integer literal. Should only be used for numbers
 * that can be stored in 32 bits binary.
 * @production IntegerLiteral : {@link NumericLiteral};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.ast:48
 */
public class IntegerLiteral extends NumericLiteral {
  private static final Logger logger = LoggerFactory.getLogger(IntegerLiteral.class);
/**
   * @apilevel internal
   */
  protected boolean type_computed = false;
/**
   * @apilevel internal
   */
  protected TypeDecl type_value;
/**
   * @apilevel internal
   */
  protected boolean constant_computed = false;
/**
   * @apilevel internal
   */
  protected Constant constant_value;
/**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:48
   */
  public IntegerLiteral(int i) {
    this(Integer.toString(i));
  }
/**
   * @ast method 
   * 
   */
  public IntegerLiteral() {


  }
/**
   * @ast method 
   * 
   */
  public IntegerLiteral(String p0) {
    setLITERAL(p0);
  }
/**
   * @ast method 
   * 
   */
  public IntegerLiteral(beaver.Symbol p0) {
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
    constant_computed = false;
    constant_value = null;
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
  public IntegerLiteral clone() throws CloneNotSupportedException {
    IntegerLiteral node = (IntegerLiteral)super.clone();
    node.type_computed = false;
    node.type_value = null;
    node.constant_computed = false;
    node.constant_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public IntegerLiteral copy() {
    try {
      IntegerLiteral node = (IntegerLiteral) clone();
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
  public IntegerLiteral fullCopy() {
    IntegerLiteral tree = (IntegerLiteral) copy();
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
   * @aspect Expressions
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/Expressions.jrag:17
   */
  @Override
public soot.Value eval(Body b) {
    return IntType.emitConstant(constant().intValue());
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
    return true;
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
	 * Check for and report literal-out-of-bounds error.
	 * If the constant is error-marked, there exists a literal out of bounds error.
	 * @ast method 
   * @aspect Literals
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag:120
   */
    @Override
	public void typeCheck() {
		if (constant().error) {
			error(new StringBuilder().append("The integer literal \"").append(getLITERAL()).append("\" is too large for type int.").toString());
		}

	}
/*syn lazy boolean FloatingPointLiteral.isZero() {
    String s = getLITERAL();
    for(int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if(c == 'E'  || c == 'e')
        break;
      if(Character.isDigit(c) && c != '0') {
        return false;
      }
    }
    return true;
  }
  syn lazy boolean DoubleLiteral.isZero() {
    String s = getLITERAL();
    for(int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if(c == 'E'  || c == 'e')
        break;
      if(Character.isDigit(c) && c != '0') {
        return false;
      }
    }
    return true;
  }* @attribute syn
   * @aspect ConstantExpression
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/ConstantExpression.jrag:152
   */
  @Override
public boolean isPositive() {
    ASTNode$State state = state();
    try {  return !getLITERAL().startsWith("-");  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:300
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
  private TypeDecl type_compute() {  return typeInt();  }
/**
	 * Parse this literal and return a fresh Constant.
	 * @return a fresh Constant representing this IntegerLiteral
	 * @attribute syn
   * @aspect Literals
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag:139
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public Constant constant() {
    if(constant_computed) {
      return constant_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    constant_value = constant_compute();
      if(isFinal && num == state().boundariesCrossed) {
		constant_computed = true;
	}
    return constant_value;
  }
/**
   * @apilevel internal
   */
  private Constant constant_compute() {
		long l = 0;
		try {
			l = parseLong();
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			Constant c = Constant.create(0L);
			c.error = true;
			return c;
		}
		Constant c = Constant.create((int)l);
		if (l != (0xFFFFFFFFL & ((int) l)) &&
				l != ((long) ((int) l)) ) {
			c.error = true;
			//System.err.println("Can not cast to integer: "+l+" ("+((int)l)+")");
		}
		return c;
	}
/**
	 * Utility attribute for literal rewriting.
	 * Any of the NumericLiteral subclasses have already
	 * been rewritten and/or parsed, and should not be
	 * rewritten again.
	 *
	 * @return true if this literal is a "raw", not-yet-parsed NumericLiteral
	 * @attribute syn
   * @aspect Literals
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/Literals.jrag:334
   */
  @Override
public boolean needsRewrite() {
    ASTNode$State state = state();
    try {  return false;  }
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
