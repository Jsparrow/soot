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
 * @production BytecodeTypeAccess : {@link TypeAccess};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BoundNames.ast:10
 */
public class BytecodeTypeAccess extends TypeAccess {
  private static final Logger logger = LoggerFactory.getLogger(BytecodeTypeAccess.class);
/**
   * @ast method 
   * 
   */
  public BytecodeTypeAccess() {


  }
/**
   * @ast method 
   * 
   */
  public BytecodeTypeAccess(String p0, String p1) {
    setPackage(p0);
    setID(p1);
  }
/**
   * @ast method 
   * 
   */
  public BytecodeTypeAccess(beaver.Symbol p0, beaver.Symbol p1) {
    setPackage(p0);
    setID(p1);
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
  public BytecodeTypeAccess clone() throws CloneNotSupportedException {
    BytecodeTypeAccess node = (BytecodeTypeAccess)super.clone();
    node.in$Circle(false);
    node.is$Final(false);
    return node;
  }
/**
   * @apilevel internal
   */
  @Override
@SuppressWarnings({"unchecked", "cast"})
  public BytecodeTypeAccess copy() {
    try {
      BytecodeTypeAccess node = (BytecodeTypeAccess) clone();
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
  public BytecodeTypeAccess fullCopy() {
    BytecodeTypeAccess tree = (BytecodeTypeAccess) copy();
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
   * Replaces the lexeme Package.
   * @param value The new value for the lexeme Package.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public void setPackage(String value) {
    tokenString_Package = value;
  }
/**
   * JastAdd-internal setter for lexeme Package using the Beaver parser.
   * @apilevel internal
   * @ast method 
   * 
   */
  @Override
public void setPackage(beaver.Symbol symbol) {
    if(symbol.value != null && !(symbol.value instanceof String)) {
		throw new UnsupportedOperationException("setPackage is only valid for String lexemes");
	}
    tokenString_Package = (String)symbol.value;
    Packagestart = symbol.getStart();
    Packageend = symbol.getEnd();
  }
/**
   * Retrieves the value for the lexeme Package.
   * @return The value for the lexeme Package.
   * @apilevel high-level
   * @ast method 
   * 
   */
  @Override
public String getPackage() {
    return tokenString_Package != null ? tokenString_Package : "";
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
   * @apilevel internal
   */
  @Override
public ASTNode rewriteTo() {
    // Declared in /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BoundNames.jrag at line 95
    state().duringBoundNames++;
    ASTNode result = rewriteRule0();
    state().duringBoundNames--;
    return result;
  }
/**
   * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.4Frontend/BoundNames.jrag:95
   * @apilevel internal
   */  private Access rewriteRule0() {
{
      if(!name().contains("$")) {
		return new TypeAccess(packageName(), name());
	} else {
        String[] names = name().split("\\$");
        Access a = null; // the resulting access
        String newName = null; // the subname to try
        TypeDecl type = null; // qualifying type if one
        for (String name : names) {
          newName = newName == null ? name : (new StringBuilder().append(newName).append("$").append(name).toString());
          SimpleSet set;
          if(type != null) {
			set = type.memberTypes(newName);
		} else if("".equals(packageName())) {
			set = lookupType(newName);
		} else {
            TypeDecl typeDecl = lookupType(packageName(), newName);
            set = SimpleSet.emptySet;
            if(typeDecl != null) {
				set = set.add(typeDecl);
			}
          }
          if(!set.isEmpty()) {
            a = a == null ? (Access)new TypeAccess(packageName(), newName) : (Access)a.qualifiesAccess(new TypeAccess(newName));
            type = (TypeDecl)set.iterator().next();
            newName = null; // reset subname
          }
        }
        if(a == null) {
          a = new TypeAccess(packageName(), name());
        }
        return a;
      }
    }  }
}
