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
/**
 * @production AbstractWildcard : {@link Access};
 * @ast node
 * @declaredat /Users/eric/Documents/workspaces/clara-soot/JastAddJ/Java1.5Frontend/Generics.ast:20
 */
public abstract class AbstractWildcard extends Access {
  /**
	   * @ast method 
	   * 
	   */
	  public AbstractWildcard() {
	
	
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
	  public AbstractWildcard clone() throws CloneNotSupportedException {
	    AbstractWildcard node = (AbstractWildcard)super.clone();
	    node.in$Circle(false);
	    node.is$Final(false);
	    return node;
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
	   * @apilevel internal
	   */
	  @Override
	public ASTNode rewriteTo() {
	    return super.rewriteTo();
	  }
}
