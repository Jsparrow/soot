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
 * @production ParMethodAccess : {@link MethodAccess} ::= <span class="component">TypeArgument:{@link Access}*</span>;
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/GenericMethods.ast:13
 */
public class ParMethodAccess extends MethodAccess {
  private static final Logger logger = LoggerFactory.getLogger(ParMethodAccess.class);
protected java.util.Map typeArguments_MethodDecl_values;
/**
   * @ast method 
   * 
   */
  public ParMethodAccess() {


  }
/**
   * @ast method 
   * 
   */
  public ParMethodAccess(String p0, List<Expr> p1, List<Access> p2) {
    setID(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
/**
   * @ast method 
   * 
   */
  public ParMethodAccess(beaver.Symbol p0, List<Expr> p1, List<Access> p2) {
    setID(p0);
    setChild(p1, 0);
    setChild(p2, 1);
  }
/**
   * @apilevel low-level
   */
  @Override
public void flushCache() {
    super.flushCache();
    typeArguments_MethodDecl_values = null;
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
  public ParMethodAccess clone() throws CloneNotSupportedException {
    ParMethodAccess node = (ParMethodAccess)super.clone();
    node.typeArguments_MethodDecl_values = null;
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public ParMethodAccess copy() {
    try {
      ParMethodAccess node = (ParMethodAccess) clone();
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
  public ParMethodAccess fullCopy() {
    ParMethodAccess tree = (ParMethodAccess) copy();
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
   * @aspect GenericMethods
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/GenericMethods.jrag:11
   */
  @Override
public void typeCheck() {
    super.typeCheck();
    if(!decl().hostType().isUnknown()) {
      if(!(decl() instanceof ParMethodDecl)) {
		error("can not have type parameters on a non generic method");
	} else {
        ParMethodDecl m = (ParMethodDecl)decl();
        if(!(m instanceof RawMethodDecl) && m.numTypeParameter() != getNumTypeArgument()) {
			error(new StringBuilder().append("generic method ").append(m.signature()).append(" requires ").append(m.numTypeParameter()).append(" type arguments").toString());
		} else {
        }
      }
    }
  }
/**
   * @ast method 
   * @aspect GenericMethodsPrettyPrint
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/GenericMethods.jrag:179
   */
  @Override
public void toString(StringBuffer s) {
    s.append("<");
    for(int i = 0; i < getNumTypeArgument(); i++) {
      if(i != 0) {
		s.append(", ");
	}
      getTypeArgument(i).toString(s);
    }
    s.append(">");
    super.toString(s);
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
    children = new ASTNode[2];
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
    return 2;
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
   * Replaces the lexeme ID.
   * @param value The new value for the lexeme ID.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setID(String value) {
    tokenString_ID = value;
  }
/**
   * JastAdd-internal setter for lexeme ID using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
  @Override
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
  @Override
public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
/**
   * Replaces the Arg list.
   * @param list The new list node to be used as the Arg list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setArgList(List<Expr> list) {
    setChild(list, 0);
  }
/**
   * Retrieves the number of children in the Arg list.
   * @return Number of children in the Arg list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public int getNumArg() {
    return getArgList().getNumChild();
  }
/**
   * Retrieves the number of children in the Arg list.
   * Calling this method will not trigger rewrites..
   * @return Number of children in the Arg list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public int getNumArgNoTransform() {
    return getArgListNoTransform().getNumChildNoTransform();
  }
/**
   * Retrieves the element at index {@code i} in the Arg list..
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the Arg list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public Expr getArg(int i) {
    return (Expr)getArgList().getChild(i);
  }
/**
   * Append an element to the Arg list.
   * @param node The element to append to the Arg list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void addArg(Expr node) {
    List<Expr> list = (parent == null || state == null) ? getArgListNoTransform() : getArgList();
    list.addChild(node);
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public void addArgNoTransform(Expr node) {
    List<Expr> list = getArgListNoTransform();
    list.addChild(node);
  }
/**
   * Replaces the Arg list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setArg(Expr node, int i) {
    List<Expr> list = getArgList();
    list.setChild(node, i);
  }
/**
   * Retrieves the Arg list.
   * @return The node representing the Arg list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public List<Expr> getArgs() {
    return getArgList();
  }
/**
   * Retrieves the Arg list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Arg list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
public List<Expr> getArgsNoTransform() {
    return getArgListNoTransform();
  }
/**
   * Retrieves the Arg list.
   * @return The node representing the Arg list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public List<Expr> getArgList() {
    List<Expr> list = (List<Expr>)getChild(0);
    list.getNumChild();
    return list;
  }
/**
   * Retrieves the Arg list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the Arg list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public List<Expr> getArgListNoTransform() {
    return (List<Expr>)getChildNoTransform(0);
  }
/**
   * Replaces the TypeArgument list.
   * @param list The new list node to be used as the TypeArgument list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setTypeArgumentList(List<Access> list) {
    setChild(list, 1);
  }
/**
   * Retrieves the number of children in the TypeArgument list.
   * @return Number of children in the TypeArgument list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public int getNumTypeArgument() {
    return getTypeArgumentList().getNumChild();
  }
/**
   * Retrieves the number of children in the TypeArgument list.
   * Calling this method will not trigger rewrites..
   * @return Number of children in the TypeArgument list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  public int getNumTypeArgumentNoTransform() {
    return getTypeArgumentListNoTransform().getNumChildNoTransform();
  }
/**
   * Retrieves the element at index {@code i} in the TypeArgument list..
   * @param i Index of the element to return.
   * @return The element at position {@code i} in the TypeArgument list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Access getTypeArgument(int i) {
    return (Access)getTypeArgumentList().getChild(i);
  }
/**
   * Append an element to the TypeArgument list.
   * @param node The element to append to the TypeArgument list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void addTypeArgument(Access node) {
    List<Access> list = (parent == null || state == null) ? getTypeArgumentListNoTransform() : getTypeArgumentList();
    list.addChild(node);
  }
/**
   * @apilevel low-level
   * @ast method 
   * 
   */
  public void addTypeArgumentNoTransform(Access node) {
    List<Access> list = getTypeArgumentListNoTransform();
    list.addChild(node);
  }
/**
   * Replaces the TypeArgument list element at index {@code i} with the new node {@code node}.
   * @param node The new node to replace the old list element.
   * @param i The list index of the node to be replaced.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public void setTypeArgument(Access node, int i) {
    List<Access> list = getTypeArgumentList();
    list.setChild(node, i);
  }
/**
   * Retrieves the TypeArgument list.
   * @return The node representing the TypeArgument list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  public List<Access> getTypeArguments() {
    return getTypeArgumentList();
  }
/**
   * Retrieves the TypeArgument list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeArgument list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  public List<Access> getTypeArgumentsNoTransform() {
    return getTypeArgumentListNoTransform();
  }
/**
   * Retrieves the TypeArgument list.
   * @return The node representing the TypeArgument list.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Access> getTypeArgumentList() {
    List<Access> list = (List<Access>)getChild(1);
    list.getNumChild();
    return list;
  }
/**
   * Retrieves the TypeArgument list.
   * <p><em>This method does not invoke AST transformations.</em></p>
   * @return The node representing the TypeArgument list.
   * @apilevel low-level
   * @ast method 
   * 
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Access> getTypeArgumentListNoTransform() {
    return (List<Access>)getChildNoTransform(1);
  }
/**
   * @attribute syn
   * @aspect MethodSignature15
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/MethodSignature.jrag:311
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public ArrayList typeArguments(MethodDecl m) {
    Object _parameters = m;
    if(typeArguments_MethodDecl_values == null) {
		typeArguments_MethodDecl_values = new java.util.HashMap(4);
	}
    if(typeArguments_MethodDecl_values.containsKey(_parameters)) {
      return (ArrayList)typeArguments_MethodDecl_values.get(_parameters);
    }
    ASTNode$State state = state();
  int num = state.boundariesCrossed;
  boolean isFinal = this.is$Final();
    ArrayList typeArguments_MethodDecl_value = typeArguments_compute();
      if(isFinal && num == state().boundariesCrossed) {
		typeArguments_MethodDecl_values.put(_parameters, typeArguments_MethodDecl_value);
	}
    return typeArguments_MethodDecl_value;
  }
/**
   * @apilevel internal
   */
  private ArrayList typeArguments_compute() {
    ArrayList typeArguments = new ArrayList();
    for(int i = 0; i < getNumTypeArgument(); i++) {
		typeArguments.add(getTypeArgument(i).type());
	}
    return typeArguments;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/GenericMethods.jrag:142
   * @apilevel internal
   */
  @Override
public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
    if(caller == getTypeArgumentListNoTransform())  {
    int childIndex = caller.getIndexOfChild(child);
    return NameType.TYPE_NAME;
  }
    else {      return super.Define_NameType_nameType(caller, child);
    }
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/GenericMethods.jrag:143
   * @apilevel internal
   */
  @Override
public SimpleSet Define_SimpleSet_lookupType(ASTNode caller, ASTNode child, String name) {
    if(caller == getTypeArgumentListNoTransform())  {
    int childIndex = caller.getIndexOfChild(child);
    return unqualifiedScope().lookupType(name);
  }
    else {      return super.Define_SimpleSet_lookupType(caller, child, name);
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
