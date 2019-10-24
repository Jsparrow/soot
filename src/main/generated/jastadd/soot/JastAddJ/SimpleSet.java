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
  * @ast interface
 * 
 */
public interface SimpleSet {

     
    SimpleSet emptySet = new SimpleSet() {
      @Override
	public int size() { return 0; }
      @Override
	public boolean isEmpty() { return true; }
      @Override
	public SimpleSet add(Object o) {
        if(o instanceof SimpleSet) {
			return (SimpleSet)o;
		}
        return new SimpleSetImpl().add(o);
      }
      @Override
	public boolean contains(Object o) { return false; }
      @Override
	public Iterator iterator() { return Collections.EMPTY_LIST.iterator(); }
      @Override
	public boolean isSingleton() { return false; }
      @Override
	public boolean isSingleton(Object o) { return false; }
    };


	SimpleSet fullSet = new SimpleSet() {
      @Override
	public int size() { throw new Error("Operation size not supported on the full set"); }
      @Override
	public boolean isEmpty() { return false; }
      @Override
	public SimpleSet add(Object o) { return this; }
      @Override
	public boolean contains(Object o) { return true; }
      @Override
	public Iterator iterator() { throw new Error("Operation iterator not support on the full set"); }
      @Override
	public boolean isSingleton() { return false; }
      @Override
	public boolean isSingleton(Object o) { return false; }
    };


	int size();


	boolean isEmpty();


	SimpleSet add(Object o);


	Iterator iterator();


	boolean contains(Object o);


	boolean isSingleton();


	boolean isSingleton(Object o);


	class SimpleSetImpl implements SimpleSet {
      private HashSet internalSet;
      public SimpleSetImpl() {
        internalSet = new HashSet(4);
      }
      public SimpleSetImpl(java.util.Collection c) {
        internalSet = new HashSet(c.size());
	internalSet.addAll(c);
      }
      private SimpleSetImpl(SimpleSetImpl set) {
        this.internalSet = new HashSet(set.internalSet);
      }
      @Override
	public int size() {
        return internalSet.size();
      }
      @Override
	public boolean isEmpty() {
        return internalSet.isEmpty();
      }
      @Override
	public SimpleSet add(Object o) {
        if(internalSet.contains(o)) {
			return this;
		}
        SimpleSetImpl set = new SimpleSetImpl(this);
        set.internalSet.add(o);
        return set;
      }
      @Override
	public Iterator iterator() {
        return internalSet.iterator();
      }
      @Override
	public boolean contains(Object o) {
        return internalSet.contains(o);
      }
      @Override
	public boolean isSingleton() { return internalSet.size() == 1; }
      @Override
	public boolean isSingleton(Object o) { return isSingleton() && contains(o); }
    }
}
