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
 * @production ThrowStmt : {@link Stmt} ::= <span class="component">{@link Expr}</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:215
 */
public class ThrowStmt extends Stmt {
  private static final Logger logger = LoggerFactory.getLogger(ThrowStmt.class);
protected java.util.Map isDAafter_Variable_values;
protected java.util.Map isDUafter_Variable_values;
/**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_computed = false;
/**
   * @apilevel internal
   */
  protected boolean canCompleteNormally_value;
/**
   * @apilevel internal
   */
  protected boolean typeNullPointerException_computed = false;
/**
   * @apilevel internal
   */
  protected TypeDecl typeNullPointerException_value;
protected java.util.Map handlesException_TypeDecl_values;
/**
   * @apilevel internal
   */
  protected boolean typeThrowable_computed = false;
/**
   * @apilevel internal
   */
  protected TypeDecl typeThrowable_value;
/**
   * @apilevel internal
   */
  protected boolean typeNull_computed = false;
/**
   * @apilevel internal
   */
  protected TypeDecl typeNull_value;
/**
   * @ast method 
   * 
   */
  public ThrowStmt() {


  }
/**
   * @ast method 
   * 
   */
  public ThrowStmt(Expr p0) {
    setChild(p0, 0);
  }
/**
   * @apilevel low-level
   */
  @Override
public void flushCache() {
    super.flushCache();
    isDAafter_Variable_values = null;
    isDUafter_Variable_values = null;
    canCompleteNormally_computed = false;
    typeNullPointerException_computed = false;
    typeNullPointerException_value = null;
    handlesException_TypeDecl_values = null;
    typeThrowable_computed = false;
    typeThrowable_value = null;
    typeNull_computed = false;
    typeNull_value = null;
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
  public ThrowStmt clone() throws CloneNotSupportedException {
    ThrowStmt node = (ThrowStmt)super.clone();
    node.isDAafter_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.canCompleteNormally_computed = false;
    node.typeNullPointerException_computed = false;
    node.typeNullPointerException_value = null;
    node.handlesException_TypeDecl_values = null;
    node.typeThrowable_computed = false;
    node.typeThrowable_value = null;
    node.typeNull_computed = false;
    node.typeNull_value = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public ThrowStmt copy() {
    try {
      ThrowStmt node = (ThrowStmt) clone();
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
  public ThrowStmt fullCopy() {
    ThrowStmt tree = (ThrowStmt) copy();
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
   * @aspect AnonymousClasses
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/AnonymousClasses.jrag:207
   */
  @Override
protected void collectExceptions(Collection c, ASTNode target) {
    super.collectExceptions(c, target);
    TypeDecl exceptionType = getExpr().type();
    if(exceptionType == typeNull()) {
		exceptionType = typeNullPointerException();
	}
    c.add(exceptionType);
  }
/**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:692
   */
  @Override
public void toString(StringBuffer s) {
    s.append(indent());
    s.append("throw ");
    getExpr().toString(s);
    s.append(";");
  }
/**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeCheck.jrag:373
   */
  @Override
public void typeCheck() {
    if(!getExpr().type().instanceOf(typeThrowable())) {
		error("*** The thrown expression must extend Throwable");
	}
  }
/**
   * @ast method 
   * @aspect Statements
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/Statements.jrag:312
   */
  @Override
public void jimplify2(Body b) {
    b.setLine(this);
    b.add(b.newThrowStmt(
      asImmediate(b, getExpr().eval(b)),
      this
    ));
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
   * Replaces the Expr child.
   * @param node The new node to replace the Expr child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setExpr(Expr node) {
    setChild(node, 0);
  }
/**
   * Retrieves the Expr child.
   * @return The current node used as the Expr child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public Expr getExpr() {
    return (Expr)getChild(0);
  }
/**
   * Retrieves the Expr child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the Expr child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  public Expr getExprNoTransform() {
    return (Expr)getChildNoTransform(0);
  }
/**
   * @ast method 
   * @aspect PreciseRethrow
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/PreciseRethrow.jrag:163
   */
   
	@Override
	public void exceptionHandling() {
		Collection<TypeDecl> exceptionTypes = getExpr().throwTypes();
		exceptionTypes.forEach(exceptionType -> {
			if (exceptionType == typeNull()) {
				exceptionType = typeNullPointerException();
			}
			// 8.4.4
			if (!handlesException(exceptionType)) {
				error(new StringBuilder().append("").append(this).append(" throws uncaught exception ").append(exceptionType.fullName()).toString());
			}
		});
	}
/**
   * @ast method 
   * @aspect PreciseRethrow
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/PreciseRethrow.jrag:176
   */
   
	@Override
	protected boolean reachedException(TypeDecl catchType) {
		Collection<TypeDecl> exceptionTypes = getExpr().throwTypes();
		boolean reached = false;
		for (TypeDecl exceptionType : exceptionTypes) {
			if(exceptionType == typeNull()) {
				exceptionType = typeNullPointerException();
			}
			if(catchType.mayCatch(exceptionType)) {
				reached = true;
				break;
			}
			if (super.reachedException(catchType)) {
				reached = true;
				break;
			}
		}
		return reached;
	}
/**
   * @attribute syn
   * @aspect DA
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:650
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public boolean isDAafter(Variable v) {
    Object _parameters = v;
    if(isDAafter_Variable_values == null) {
		isDAafter_Variable_values = new java.util.HashMap(4);
	}
    if(isDAafter_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDAafter_Variable_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean isDAafter_Variable_value = isDAafter_compute();
      if(isFinal && num == state().boundariesCrossed) {
		isDAafter_Variable_values.put(_parameters, Boolean.valueOf(isDAafter_Variable_value));
	}
    return isDAafter_Variable_value;
  }
/**
   * @apilevel internal
   */
  private boolean isDAafter_compute() {  return true;  }
/**
   * @attribute syn
   * @aspect DU
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1173
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public boolean isDUafter(Variable v) {
    Object _parameters = v;
    if(isDUafter_Variable_values == null) {
		isDUafter_Variable_values = new java.util.HashMap(4);
	}
    if(isDUafter_Variable_values.containsKey(_parameters)) {
      return ((Boolean)isDUafter_Variable_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean isDUafter_Variable_value = isDUafter_compute();
      if(isFinal && num == state().boundariesCrossed) {
		isDUafter_Variable_values.put(_parameters, Boolean.valueOf(isDUafter_Variable_value));
	}
    return isDUafter_Variable_value;
  }
/**
   * @apilevel internal
   */
  private boolean isDUafter_compute() {  return true;  }
/**
   * @attribute syn
   * @aspect UnreachableStatements
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/UnreachableStatements.jrag:108
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public boolean canCompleteNormally() {
    if(canCompleteNormally_computed) {
      return canCompleteNormally_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    canCompleteNormally_value = canCompleteNormally_compute();
      if(isFinal && num == state().boundariesCrossed) {
		canCompleteNormally_computed = true;
	}
    return canCompleteNormally_value;
  }
/**
   * @apilevel internal
   */
  private boolean canCompleteNormally_compute() {  return false;  }
/**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/PreciseRethrow.jrag:55
   */
  @Override
public boolean modifiedInScope(Variable var) {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:20
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeNullPointerException() {
    if(typeNullPointerException_computed) {
      return typeNullPointerException_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    typeNullPointerException_value = getParent().Define_TypeDecl_typeNullPointerException(this, null);
      if(isFinal && num == state().boundariesCrossed) {
		typeNullPointerException_computed = true;
	}
    return typeNullPointerException_value;
  }
/**
   * @attribute inh
   * @aspect ExceptionHandling
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/ExceptionHandling.jrag:45
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean handlesException(TypeDecl exceptionType) {
    Object _parameters = exceptionType;
    if(handlesException_TypeDecl_values == null) {
		handlesException_TypeDecl_values = new java.util.HashMap(4);
	}
    if(handlesException_TypeDecl_values.containsKey(_parameters)) {
      return ((Boolean)handlesException_TypeDecl_values.get(_parameters)).booleanValue();
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    boolean handlesException_TypeDecl_value = getParent().Define_boolean_handlesException(this, null, exceptionType);
      if(isFinal && num == state().boundariesCrossed) {
		handlesException_TypeDecl_values.put(_parameters, Boolean.valueOf(handlesException_TypeDecl_value));
	}
    return handlesException_TypeDecl_value;
  }
/**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupType.jrag:67
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeThrowable() {
    if(typeThrowable_computed) {
      return typeThrowable_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    typeThrowable_value = getParent().Define_TypeDecl_typeThrowable(this, null);
      if(isFinal && num == state().boundariesCrossed) {
		typeThrowable_computed = true;
	}
    return typeThrowable_value;
  }
/**
   * @attribute inh
   * @aspect SpecialClasses
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupType.jrag:70
   */
  @SuppressWarnings({"unchecked", "cast"})
  public TypeDecl typeNull() {
    if(typeNull_computed) {
      return typeNull_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    typeNull_value = getParent().Define_TypeDecl_typeNull(this, null);
      if(isFinal && num == state().boundariesCrossed) {
		typeNull_computed = true;
	}
    return typeNull_value;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:653
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getExprNoTransform()) {
      return isDAbefore(v);
    }
    else {      return getParent().Define_boolean_isDAbefore(this, caller, v);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:1176
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getExprNoTransform()) {
      return isDUbefore(v);
    }
    else {      return getParent().Define_boolean_isDUbefore(this, caller, v);
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
