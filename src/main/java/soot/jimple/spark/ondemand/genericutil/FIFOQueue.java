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

/**
 * A FIFO queue of objects, implemented as a circular buffer. NOTE: elements stored in the buffer should be non-null; this is
 * not checked for performance reasons.
 *
 * @author Manu Sridharan
 */
public final class FIFOQueue {

  /**
   * the buffer.
   */
  private Object[] buf;

  /**
   * pointer to current top of buffer
   */
  private int top;

  /**
   * point to current bottom of buffer, where things will be added invariant: after call to add / remove, should always point
   * to an empty slot in the buffer
   */
  private int bottom;

  /**
   * @param initialSize_
   *          the initial size of the queue
   */
  public FIFOQueue(int initialSize_) {
    buf = new Object[initialSize_];
  }

  public FIFOQueue() {
    this(10);
  }

  public boolean push(Object obj_) {
    return add(obj_);
  }

  /**
   * add an element to the bottom of the queue
   */
  public boolean add(Object obj_) {
    // Assert.chk(obj_ != null);
    // add the element
    buf[bottom] = obj_;
    // increment bottom, wrapping around if necessary
    bottom = (bottom == buf.length - 1) ? 0 : bottom + 1;
    // see if we need to increase the queue size
	if (bottom != top) {
		return false;
	}
	// allocate a new array and copy
      int oldLen = buf.length;
	int newLen = oldLen * 2;
	// System.out.println("growing buffer to size " + newLen);
      Object[] newBuf = new Object[newLen];
	int topToEnd = oldLen - top;
	int newTop = newLen - topToEnd;
	// copy from 0 to _top to beginning of new buffer,
      // _top to _buf.length to the end of the new buffer
      System.arraycopy(buf, 0, newBuf, 0, top);
	System.arraycopy(buf, top, newBuf, newTop, topToEnd);
	buf = newBuf;
	top = newTop;
	return true;
  }

  public Object pop() {
    return remove();
  }

  /**
   * remove the top element from the buffer
   */
  public Object remove() {
    // check if buffer is empty
    if (bottom == top) {
      return null;
    }
    Object ret = buf[top];
    // increment top, wrapping if necessary
    top = (top == buf.length - 1) ? 0 : top + 1;
    return ret;
  }

  public boolean isEmpty() {
    return bottom == top;
  }

  @Override
public String toString() {
    return new StringBuilder().append(bottom).append(" ").append(top).toString();
  }

  public void clear() {
    bottom = 0;
    top = 0;
  }
}
