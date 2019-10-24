package soot.jimple.spark.ondemand.genericutil;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2007 Manu Sridharan
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArraySet<T> extends AbstractSet<T> {

  private static final ArraySet EMPTY = new ArraySet<Object>(0, true) {
    @Override
	public boolean add(Object obj_) {
      throw new RuntimeException();
    }
  };

private T[] elems;

private int curIndex = 0;

private final boolean checkDupes;

@SuppressWarnings("all")
  public ArraySet(int numElems_, boolean checkDupes) {
    elems = (T[]) new Object[numElems_];
    this.checkDupes = checkDupes;
  }

public ArraySet() {
    this(1, true);
  }

@SuppressWarnings("all")
  public ArraySet(ArraySet<T> other) {
    int size = other.curIndex;
    this.elems = (T[]) new Object[size];
    this.checkDupes = other.checkDupes;
    this.curIndex = size;
    System.arraycopy(other.elems, 0, elems, 0, size);
  }

public ArraySet(Collection<T> other) {
    this(other.size(), true);
    addAll(other);
  }

@SuppressWarnings("all")
  public static final <T> ArraySet<T> empty() {
    return (ArraySet<T>) EMPTY;
  }

/*
   * (non-Javadoc)
   * 
   * @see AAA.util.AAASet#add(java.lang.Object)
   */
  @Override
@SuppressWarnings("all")
  public boolean add(T obj_) {
    assert obj_ != null;
    if (checkDupes && this.contains(obj_)) {
      return false;
    }
    if (curIndex == elems.length) {
      // lengthen array
      Object[] tmp = elems;
      elems = (T[]) new Object[tmp.length * 2];
      System.arraycopy(tmp, 0, elems, 0, tmp.length);
    }
    elems[curIndex] = obj_;
    curIndex++;
    return true;
  }

public boolean addAll(ArraySet<T> other) {
    boolean ret = false;
    for (T anOther : other) {
      boolean added = add(anOther);
      ret = ret || added;
    }
    return ret;
  }

/*
   * (non-Javadoc)
   * 
   * @see AAA.util.AAASet#contains(java.lang.Object)
   */
  @Override
public boolean contains(Object obj_) {
    for (int i = 0; i < curIndex; i++) {
      if (elems[i].equals(obj_)) {
        return true;
      }
    }
    return false;
  }

public boolean intersects(ArraySet<T> other) {
    return other.stream().anyMatch(anOther -> contains(anOther));
  }

/*
   * (non-Javadoc)
   * 
   * @see AAA.util.AAASet#forall(AAA.util.ObjectVisitor)
   */
  public void forall(ObjectVisitor<T> visitor_) {
    for (int i = 0; i < curIndex; i++) {
      visitor_.visit(elems[i]);
    }
  }

@Override
public int size() {
    return curIndex;
  }

public T get(int i) {
    return elems[i];
  }

@Override
public boolean remove(Object obj_) {
    int ind;
    for (ind = 0; ind < curIndex && !elems[ind].equals(obj_); ind++) {
    }
    // check if object was never there
    if (ind == curIndex) {
      return false;
    }
    return remove(ind);
  }

/**
   * @param ind
   * @return
   */
  public boolean remove(int ind) {
    // hope i got this right...
    System.arraycopy(elems, ind + 1, elems, ind, curIndex - (ind + 1));
    curIndex--;
    return true;
  }

@Override
public void clear() {
    curIndex = 0;
  }

@Override
public boolean isEmpty() {
    return size() == 0;
  }

@Override
public String toString() {
    StringBuilder ret = new StringBuilder();
    ret.append('[');
    for (int i = 0; i < size(); i++) {
      ret.append(get(i).toString());
      if (i + 1 < size()) {
        ret.append(", ");
      }
    }
    ret.append(']');
    return ret.toString();
  }

/*
   * (non-Javadoc)
   * 
   * @see java.util.Set#toArray()
   */
  @Override
public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

/*
   * (non-Javadoc)
   * 
   * @see java.util.Set#addAll(java.util.Collection)
   */
  @Override
public boolean addAll(Collection<? extends T> c) {
    boolean ret = false;
    for (T element : c) {
      boolean added = add(element);
      ret = ret || added;
    }
    return ret;
  }

/*
   * (non-Javadoc)
   * 
   * @see java.util.Set#iterator()
   */
  @Override
public Iterator<T> iterator() {
    return new ArraySetIterator();
  }

/*
   * (non-Javadoc)
   * 
   * @see java.util.Set#toArray(java.lang.Object[])
   */
  @Override
@SuppressWarnings("unchecked")
  public <U> U[] toArray(U[] a) {
    for (int i = 0; i < curIndex; i++) {
      T t = elems[i];
      a[i] = (U) t;
    }
    return a;
  }

  /**
   * @author manu
   */
  public class ArraySetIterator implements Iterator<T> {

    int ind = 0;

    final int setSize = size();

    /**
     * 
     */
    public ArraySetIterator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#remove()
     */
    @Override
	public void remove() {
      throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#hasNext()
     */
    @Override
	public boolean hasNext() {
      return ind < setSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#next()
     */
    @Override
	public T next() {
      if (ind >= setSize) {
        throw new NoSuchElementException();
      }
      return get(ind++);
    }

  }

}
