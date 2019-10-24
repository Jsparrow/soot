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
 * @production VariableDeclaration : {@link Stmt} ::= <span class="component">{@link Modifiers}</span> <span class="component">TypeAccess:{@link Access}</span> <span class="component">&lt;ID:String&gt;</span> <span class="component">[Init:{@link Expr}]</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/java.ast:83
 */
public class VariableDeclaration extends Stmt implements SimpleSet, Iterator, Variable {
  private static final Logger logger = LoggerFactory.getLogger(VariableDeclaration.class);
/**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:107
   */
  
  private VariableDeclaration iterElem;
/**
   * @ast method 
   * @aspect EmitJimple
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/EmitJimple.jrag:395
   */
  
  public Local local;
/**
   * @apilevel internal
   * @ast method 
   * 
   */
  
  /**
   * @apilevel internal
   */
  protected String tokenString_ID;
/**
   * @ast method 
   * 
   */
  
  public int IDstart;
/**
   * @ast method 
   * 
   */
  
  public int IDend;
protected java.util.Map isDAafter_Variable_values;
protected java.util.Map isDUafter_Variable_values;
/**
   * @apilevel internal
   */
  protected boolean constant_computed = false;
/**
   * @apilevel internal
   */
  protected Constant constant_value;
/**
   * @apilevel internal
   */
  protected boolean sourceVariableDecl_computed = false;
/**
   * @apilevel internal
   */
  protected Variable sourceVariableDecl_value;
/**
   * @apilevel internal
   */
  protected boolean throwTypes_computed = false;
/**
   * @apilevel internal
   */
  protected Collection<TypeDecl> throwTypes_value;
/**
   * @apilevel internal
   */
  protected boolean localNum_computed = false;
/**
   * @apilevel internal
   */
  protected int localNum_value;
/**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:74
   */
  public VariableDeclaration(Access type, String name, Expr init) {
    this(new Modifiers(new List()), type, name, new Opt(init));
  }
/**
   * @ast method 
   * @aspect NodeConstructors
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NodeConstructors.jrag:78
   */
  public VariableDeclaration(Access type, String name) {
    this(new Modifiers(new List()), type, name, new Opt());
  }
/**
   * @ast method 
   * 
   */
  public VariableDeclaration() {


  }
/**
   * @ast method 
   * 
   */
  public VariableDeclaration(Modifiers p0, Access p1, String p2, Opt<Expr> p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
    setChild(p3, 2);
  }
/**
   * @ast method 
   * 
   */
  public VariableDeclaration(Modifiers p0, Access p1, beaver.Symbol p2, Opt<Expr> p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setID(p2);
    setChild(p3, 2);
  }
/**
   * @apilevel low-level
   */
  @Override
public void flushCache() {
    super.flushCache();
    isDAafter_Variable_values = null;
    isDUafter_Variable_values = null;
    constant_computed = false;
    constant_value = null;
    sourceVariableDecl_computed = false;
    sourceVariableDecl_value = null;
    throwTypes_computed = false;
    throwTypes_value = null;
    localNum_computed = false;
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
  public VariableDeclaration clone() throws CloneNotSupportedException {
    VariableDeclaration node = (VariableDeclaration)super.clone();
    node.isDAafter_Variable_values = null;
    node.isDUafter_Variable_values = null;
    node.constant_computed = false;
    node.constant_value = null;
    node.sourceVariableDecl_computed = false;
    node.sourceVariableDecl_value = null;
    node.throwTypes_computed = false;
    node.throwTypes_value = null;
    node.localNum_computed = false;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public VariableDeclaration copy() {
    try {
      VariableDeclaration node = (VariableDeclaration) clone();
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
  public VariableDeclaration fullCopy() {
    VariableDeclaration tree = (VariableDeclaration) copy();
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
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:99
   */
  @Override
public SimpleSet add(Object o) {
    return new SimpleSetImpl().add(this).add(o);
  }
/**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:103
   */
  @Override
public boolean isSingleton() { return true; }
/**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:104
   */
  @Override
public boolean isSingleton(Object o) { return contains(o); }
/**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:108
   */
  @Override
public Iterator iterator() { iterElem = this; return this; }
/**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:109
   */
  @Override
public boolean hasNext() { return iterElem != null; }
/**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:110
   */
  @Override
public Object next() { Object o = iterElem; iterElem = null; return o; }
/**
   * @ast method 
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:111
   */
  @Override
public void remove() { throw new UnsupportedOperationException(); }
/**
   * @ast method 
   * @aspect PrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/PrettyPrint.jadd:164
   */
  @Override
public void toString(StringBuffer s) {
    s.append(indent());
    getModifiers().toString(s);
    getTypeAccess().toString(s);
    s.append(" " + name());
    if(hasInit()) {
      s.append(" = ");
      getInit().toString(s);
    }
    s.append(";");
  }
/**
   * @ast method 
   * @aspect TypeCheck
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeCheck.jrag:22
   */
  @Override
public void typeCheck() {
    if (!hasInit()) {
		return;
	}
	TypeDecl source = getInit().type();
	TypeDecl dest = type();
	if(!source.assignConversionTo(dest, getInit())) {
		error(new StringBuilder().append("can not assign variable ").append(name()).append(" of type ").append(dest.typeName()).append(" a value of type ").append(source.typeName())
				.toString());
	}
  }
/**
   * @ast method 
   * @aspect EmitJimple
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/EmitJimple.jrag:377
   */
  @Override
public void jimplify2(Body b) {
    b.setLine(this);
    local = b.newLocal(name(), type().getSootType());
    if(hasInit()) {
      b.add(
        b.newAssignStmt(
          local,
          asRValue(b,
            getInit().type().emitCastTo(b, // Assign conversion
              getInit(),
              type()
            )
          ),
          this
        )
      );
    }
  }
/**
   * @ast method 
   * @aspect UncheckedConversion
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/UncheckedConversion.jrag:20
   */
  @Override
public void checkWarnings() {
    if (hasInit() && !suppressWarnings("unchecked")) {
		checkUncheckedConversion(getInit().type(), type());
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
    setChild(new Opt(), 2);
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
   * Replaces the Modifiers child.
   * @param node The new node to replace the Modifiers child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setModifiers(Modifiers node) {
    setChild(node, 0);
  }
/**
   * Retrieves the Modifiers child.
   * @return The current node used as the Modifiers child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public Modifiers getModifiers() {
    return (Modifiers)getChild(0);
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
    return (Modifiers)getChildNoTransform(0);
  }
/**
   * Replaces the TypeAccess child.
   * @param node The new node to replace the TypeAccess child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setTypeAccess(Access node) {
    setChild(node, 1);
  }
/**
   * Retrieves the TypeAccess child.
   * @return The current node used as the TypeAccess child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public Access getTypeAccess() {
    return (Access)getChild(1);
  }
/**
   * Retrieves the TypeAccess child.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The current node used as the TypeAccess child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  public Access getTypeAccessNoTransform() {
    return (Access)getChildNoTransform(1);
  }
/**
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
/**
   * JastAdd-internal setter for lexeme ID using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
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
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
/**
   * Replaces the optional node for the Init child. This is the {@code Opt} node containing the child Init, not the actual child!
   * @param opt The new node to be used as the optional node for the Init child.
   * @apilevel low-level
   * @ast method 
   * 
   */
  public void setInitOpt(Opt<Expr> opt) {
    setChild(opt, 2);
  }
/**
   * Check whether the optional Init child exists.
   * @return {@code true} if the optional Init child exists, {@code false} if it does not.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public boolean hasInit() {
    return getInitOpt().getNumChild() != 0;
  }
/**
   * Retrieves the (optional) Init child.
   * @return The Init child, if it exists. Returns {@code null} otherwise.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public Expr getInit() {
    return (Expr)getInitOpt().getChild(0);
  }
/**
   * Replaces the (optional) Init child.
   * @param node The new node to be used as the Init child.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setInit(Expr node) {
    getInitOpt().setChild(node, 0);
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Expr> getInitOpt() {
    return (Opt<Expr>)getChild(2);
  }
/**
   * Retrieves the optional node for child Init. This is the {@code Opt} node containing the child Init, not the actual child!
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The optional node for child Init.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Expr> getInitOptNoTransform() {
    return (Opt<Expr>)getChildNoTransform(2);
  }
/**
   * @ast method 
   * @aspect MultiCatch
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/MultiCatch.jrag:240
   */
   
	@Override
	public void nameCheck() {
		SimpleSet decls = outerScope().lookupVariable(name());
		for(Iterator iter = decls.iterator(); iter.hasNext(); ) {
			Variable var = (Variable)iter.next();
			if(var instanceof VariableDeclaration) {
				VariableDeclaration decl = (VariableDeclaration)var;
				if(decl != this && decl.enclosingBodyDecl() == enclosingBodyDecl()) {
					error("duplicate declaration of local variable " + name());
				}
			}
			// 8.4.1
			else if(var instanceof ParameterDeclaration) {
				ParameterDeclaration decl = (ParameterDeclaration)var;
				if(decl.enclosingBodyDecl() == enclosingBodyDecl()) {
					error("duplicate declaration of local variable " + name());
				}
			} else if(var instanceof CatchParameterDeclaration) {
				CatchParameterDeclaration decl = (CatchParameterDeclaration)var;
				if(decl.enclosingBodyDecl() == enclosingBodyDecl()) {
					error("duplicate declaration of local variable " + name());
				}
			}
		}
		if (!(getParent().getParent() instanceof Block)) {
			return;
		}
		Block block = (Block)getParent().getParent();
		for(int i = 0; i < block.getNumStmt(); i++) {
			if(block.getStmt(i) instanceof Variable) {
				Variable v = (Variable)block.getStmt(i);
				if(v.name().equals(name()) && v != this) {
					error("duplicate declaration of local variable " + name());
				}
			}
		}
	}
/**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:97
   */
  @Override
public int size() {
    ASTNode$State state = state();
    try {  return 1;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:98
   */
  @Override
public boolean isEmpty() {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DataStructures
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DataStructures.jrag:102
   */
  @Override
public boolean contains(Object o) {
    ASTNode$State state = state();
    try {  return this == o;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:91
   */
  public boolean isBlankFinal() {
    ASTNode$State state = state();
    try {  return isFinal() && (!hasInit() || !getInit().isConstant());  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DefiniteAssignment
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:92
   */
  public boolean isValue() {
    ASTNode$State state = state();
    try {  return isFinal() && hasInit() && getInit().isConstant();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect DA
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:492
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
    boolean isDAafter_Variable_value = isDAafter_compute(v);
      if(isFinal && num == state().boundariesCrossed) {
		isDAafter_Variable_values.put(_parameters, Boolean.valueOf(isDAafter_Variable_value));
	}
    return isDAafter_Variable_value;
  }
/**
   * @apilevel internal
   */
  private boolean isDAafter_compute(Variable v) {
    if(v == this) {
		return hasInit();
	}
    return hasInit() ? getInit().isDAafter(v) : isDAbefore(v);
  }
/**
   * @attribute syn
   * @aspect DU
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:875
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
    boolean isDUafter_Variable_value = isDUafter_compute(v);
      if(isFinal && num == state().boundariesCrossed) {
		isDUafter_Variable_values.put(_parameters, Boolean.valueOf(isDUafter_Variable_value));
	}
    return isDUafter_Variable_value;
  }
/**
   * @apilevel internal
   */
  private boolean isDUafter_compute(Variable v) {
    if(v == this) {
		return !hasInit();
	}
    return hasInit() ? getInit().isDUafter(v) : isDUbefore(v);
  }
/**
   * @attribute syn
   * @aspect VariableScope
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupVariable.jrag:129
   */
  @Override
public boolean declaresVariable(String name) {
    ASTNode$State state = state();
    try {  return name().equals(name);  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Modifiers
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/Modifiers.jrag:219
   */
  @Override
public boolean isSynthetic() {
    ASTNode$State state = state();
    try {  return getModifiers().isSynthetic();  }
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
    try {  return new StringBuilder().append(getClass().getName()).append(" [").append(getID()).append("]").toString();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect TypeAnalysis
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:252
   */
  @Override
public TypeDecl type() {
    ASTNode$State state = state();
    try {  return getTypeAccess().type();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:13
   */
  @Override
public boolean isParameter() {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:15
   */
  @Override
public boolean isClassVariable() {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:16
   */
  @Override
public boolean isInstanceVariable() {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:17
   */
  @Override
public boolean isMethodParameter() {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:18
   */
  @Override
public boolean isConstructorParameter() {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:19
   */
  @Override
public boolean isExceptionHandlerParameter() {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:20
   */
  @Override
public boolean isLocalVariable() {
    ASTNode$State state = state();
    try {  return true;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:22
   */
  @Override
public boolean isFinal() {
    ASTNode$State state = state();
    try {  return getModifiers().isFinal();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:23
   */
  @Override
public boolean isVolatile() {
    ASTNode$State state = state();
    try {  return getModifiers().isVolatile();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:24
   */
  @Override
public boolean isBlank() {
    ASTNode$State state = state();
    try {  return !hasInit();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:25
   */
  @Override
public boolean isStatic() {
    ASTNode$State state = state();
    try {  return false;  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:27
   */
  @Override
public String name() {
    ASTNode$State state = state();
    try {  return getID();  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect Variables
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/VariableDeclaration.jrag:29
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
  private Constant constant_compute() {  return type().cast(getInit().constant());  }
/**
   * @attribute syn
   * @aspect SourceDeclarations
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.jrag:1520
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public Variable sourceVariableDecl() {
    if(sourceVariableDecl_computed) {
      return sourceVariableDecl_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    sourceVariableDecl_value = sourceVariableDecl_compute();
      if(isFinal && num == state().boundariesCrossed) {
		sourceVariableDecl_computed = true;
	}
    return sourceVariableDecl_value;
  }
/**
   * @apilevel internal
   */
  private Variable sourceVariableDecl_compute() {  return this;  }
/**
   * @attribute syn
   * @aspect PreciseRethrow
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/PreciseRethrow.jrag:17
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public Collection<TypeDecl> throwTypes() {
    if(throwTypes_computed) {
      return throwTypes_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    throwTypes_value = throwTypes_compute();
      if(isFinal && num == state().boundariesCrossed) {
		throwTypes_computed = true;
	}
    return throwTypes_value;
  }
/**
   * @apilevel internal
   */
  private Collection<TypeDecl> throwTypes_compute() {
		Collection<TypeDecl> tts = new LinkedList<>();
		tts.add(type());
		return tts;
	}
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
   * @attribute syn
   * @aspect SuppressWarnings
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/SuppressWarnings.jrag:10
   */
  public boolean hasAnnotationSuppressWarnings(String s) {
    ASTNode$State state = state();
    try {  return getModifiers().hasAnnotationSuppressWarnings(s);  }
    finally {
    }
  }
/**
   * @attribute syn
   * @aspect SuppressWarnings
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/SuppressWarnings.jrag:20
   */
  public boolean suppressWarnings(String type) {
    ASTNode$State state = state();
    try {  return hasAnnotationSuppressWarnings(type) || withinSuppressWarnings(type);  }
    finally {
    }
  }
/**
   * @attribute inh
   * @aspect VariableScope
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/LookupVariable.jrag:21
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public SimpleSet lookupVariable(String name) {
    ASTNode$State state = state();
    SimpleSet lookupVariable_String_value = getParent().Define_SimpleSet_lookupVariable(this, null, name);
    return lookupVariable_String_value;
  }
/**
   * @attribute inh
   * @aspect NameCheck
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/NameCheck.jrag:294
   */
  @SuppressWarnings({"unchecked", "cast"})
  public VariableScope outerScope() {
    ASTNode$State state = state();
    VariableScope outerScope_value = getParent().Define_VariableScope_outerScope(this, null);
    return outerScope_value;
  }
/**
   * @attribute inh
   * @aspect NestedTypes
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:588
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public TypeDecl hostType() {
    ASTNode$State state = state();
    TypeDecl hostType_value = getParent().Define_TypeDecl_hostType(this, null);
    return hostType_value;
  }
/**
   * @attribute inh
   * @aspect LocalNum
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddExtensions/JimpleBackend/LocalNum.jrag:11
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public int localNum() {
    if(localNum_computed) {
      return localNum_value;
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    localNum_value = getParent().Define_int_localNum(this, null);
      if(isFinal && num == state().boundariesCrossed) {
		localNum_computed = true;
	}
    return localNum_value;
  }
/**
   * @attribute inh
   * @aspect SuppressWarnings
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/SuppressWarnings.jrag:13
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean withinSuppressWarnings(String s) {
    ASTNode$State state = state();
    boolean withinSuppressWarnings_String_value = getParent().Define_boolean_withinSuppressWarnings(this, null, s);
    return withinSuppressWarnings_String_value;
  }
/**
   * @attribute inh
   * @aspect TryWithResources
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java7Frontend/TryWithResources.jrag:144
   */
  @SuppressWarnings({"unchecked", "cast"})
  public boolean resourcePreviouslyDeclared(String name) {
    ASTNode$State state = state();
    boolean resourcePreviouslyDeclared_String_value = getParent().Define_boolean_resourcePreviouslyDeclared(this, null, name);
    return resourcePreviouslyDeclared_String_value;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:40
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_isSource(ASTNode caller, ASTNode child) {
    if(caller == getInitOptNoTransform()) {
      return true;
    }
    else {      return getParent().Define_boolean_isSource(this, caller);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:497
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getInitOptNoTransform()) {
      return isDAbefore(v);
    }
    else {      return getParent().Define_boolean_isDAbefore(this, caller, v);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/DefiniteAssignment.jrag:880
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
    if(caller == getInitOptNoTransform()) {
      return isDUbefore(v);
    }
    else {      return getParent().Define_boolean_isDUbefore(this, caller, v);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/Modifiers.jrag:286
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_mayBeFinal(ASTNode caller, ASTNode child) {
    if(caller == getModifiersNoTransform()) {
      return true;
    }
    else {      return getParent().Define_boolean_mayBeFinal(this, caller);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/SyntacticClassification.jrag:85
   * @apilevel internal
   */
  @Override
public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if(caller == getTypeAccessNoTransform()) {
      return NameType.TYPE_NAME;
    }
    else {      return getParent().Define_NameType_nameType(this, caller);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/TypeAnalysis.jrag:261
   * @apilevel internal
   */
  @Override
public TypeDecl Define_TypeDecl_declType(ASTNode caller, ASTNode child) {
    if(caller == getInitOptNoTransform()) {
      return type();
    }
    else {      return getParent().Define_TypeDecl_declType(this, caller);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Annotations.jrag:92
   * @apilevel internal
   */
  @Override
public boolean Define_boolean_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
    if(caller == getModifiersNoTransform()) {
      return "LOCAL_VARIABLE".equals(name);
    }
    else {      return getParent().Define_boolean_mayUseAnnotationTarget(this, caller, name);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/GenericMethodsInference.jrag:34
   * @apilevel internal
   */
  @Override
public TypeDecl Define_TypeDecl_assignConvertedType(ASTNode caller, ASTNode child) {
    if(caller == getInitOptNoTransform()) {
      return type();
    }
    else {      return getParent().Define_TypeDecl_assignConvertedType(this, caller);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Backend/InnerClasses.jrag:68
   * @apilevel internal
   */
  @Override
public TypeDecl Define_TypeDecl_expectedType(ASTNode caller, ASTNode child) {
    if(caller == getInitOptNoTransform()) {
      return type().componentType();
    }
    else {      return getParent().Define_TypeDecl_expectedType(this, caller);
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
