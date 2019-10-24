package soot.util;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2002 Sable Research Group
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

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class IterableMap implements Map {
  private HashMap<Object, Object> contentMap;
private HashMap<Object, Object> backMap;
  private HashChain keyChain;
private HashChain valueChain;
private transient Set<Object> keySet = null;
private transient Set<Object> valueSet = null;
private transient Collection<Object> values = null;

public IterableMap() {
    this(7, 0.7f);
  }

public IterableMap(int initialCapacity) {
    this(initialCapacity, 0.7f);
  }

public IterableMap(int initialCapacity, float loadFactor) {
    contentMap = new HashMap<>(initialCapacity, loadFactor);
    backMap = new HashMap<>(initialCapacity, loadFactor);
    keyChain = new HashChain();
    valueChain = new HashChain();
  }

@Override
public void clear() {
    Iterator kcit = keyChain.iterator();
    while (kcit.hasNext()) {
      contentMap.remove(kcit.next());
    }

    Iterator vcit = valueChain.iterator();
    while (vcit.hasNext()) {
      backMap.remove(vcit.next());
    }

    keyChain.clear();
    valueChain.clear();
  }

public Iterator iterator() {
    return keyChain.iterator();
  }

@Override
public boolean containsKey(Object key) {
    return keyChain.contains(key);
  }

@Override
public boolean containsValue(Object value) {
    return valueChain.contains(value);
  }

@Override
public Set entrySet() {
    return contentMap.entrySet();
  }

@Override
public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if ((o instanceof IterableMap) == false) {
      return false;
    }

    IterableMap other = (IterableMap) o;

    if (keyChain.equals(other.keyChain) == false) {
      return false;
    }

    // check that the other has our mapping
    Iterator kcit = keyChain.iterator();
    while (kcit.hasNext()) {
      Object ko = kcit.next();

      if (other.contentMap.get(ko) != contentMap.get(ko)) {
        return false;
      }
    }

    return true;
  }

@Override
public Object get(Object key) {
    return contentMap.get(key);
  }

@Override
public int hashCode() {
    return contentMap.hashCode();
  }

@Override
public boolean isEmpty() {
    return keyChain.isEmpty();
  }

@Override
public Set<Object> keySet() {
    if (keySet == null) {
      keySet = new AbstractSet() {
        @Override
		public Iterator iterator() {
          return keyChain.iterator();
        }

        @Override
		public int size() {
          return keyChain.size();
        }

        @Override
		public boolean contains(Object o) {
          return keyChain.contains(o);
        }

        @Override
		public boolean remove(Object o) {
          if (keyChain.contains(o) == false) {
            return false;
          }

          if (IterableMap.this.contentMap.get(o) != null) {
			return (IterableMap.this.remove(o) != null);
		}
		IterableMap.this.remove(o);
		return true;
        }

        @Override
		public void clear() {
          IterableMap.this.clear();
        }
      };
    }
    return keySet;
  }

public Set<Object> valueSet() {
    if (valueSet == null) {
      valueSet = new AbstractSet() {
        @Override
		public Iterator iterator() {
          return valueChain.iterator();
        }

        @Override
		public int size() {
          return valueChain.size();
        }

        @Override
		public boolean contains(Object o) {
          return valueChain.contains(o);
        }

        @Override
		public boolean remove(Object o) {
          if (valueChain.contains(o) == false) {
            return false;
          }

          HashChain c = (HashChain) IterableMap.this.backMap.get(o);
          Iterator it = c.snapshotIterator();
          while (it.hasNext()) {
            Object ko = it.next();

            if (IterableMap.this.contentMap.get(o) == null) {
              IterableMap.this.remove(ko);
            } else if (IterableMap.this.remove(ko) == null) {
              return false;
            }
          }
          return true;
        }

        @Override
		public void clear() {
          IterableMap.this.clear();
        }
      };
    }
    return valueSet;
  }

@Override
public Object put(Object key, Object value) {
    if (keyChain.contains(key)) {

      Object old_value = contentMap.get(key);

      if (old_value == value) {
        return value;
      }

      HashChain kc = (HashChain) backMap.get(old_value);
      kc.remove(key);

      if (kc.isEmpty()) {
        valueChain.remove(old_value);
        backMap.remove(old_value);
      }

      kc = (HashChain) backMap.get(value);
      if (kc == null) {
        kc = new HashChain();
        backMap.put(value, kc);
        valueChain.add(value);
      }
      kc.add(key);

      return old_value;

    } else {

      keyChain.add(key);
      contentMap.put(key, value);

      HashChain kc = (HashChain) backMap.get(value);
      if (kc == null) {
        kc = new HashChain();
        backMap.put(value, kc);
        valueChain.add(value);
      }
      kc.add(key);

      return null;
    }
  }

@Override
public void putAll(Map t) {
    Iterator kit = (t instanceof IterableMap) ? ((IterableMap) t).keyChain.iterator() : t.keySet().iterator();

    while (kit.hasNext()) {
      Object key = kit.next();
      put(key, t.get(key));
    }
  }

@Override
public Object remove(Object key) {
    if (keyChain.contains(key) == false) {
      return null;
    }

    keyChain.remove(key);
    Object value = contentMap.remove(key);
    HashChain c = (HashChain) backMap.get(value);
    c.remove(key);
    if (c.size() == 0) {
      backMap.remove(value);
    }

    return value;
  }

@Override
public int size() {
    return keyChain.size();
  }

@Override
public Collection<Object> values() {
    if (values == null) {
      values = new AbstractCollection() {
        @Override
		public Iterator iterator() {
          return new Mapping_Iterator(IterableMap.this.keyChain, IterableMap.this.contentMap);
        }

        @Override
		public int size() {
          return keyChain.size();
        }

        @Override
		public boolean contains(Object o) {
          return valueChain.contains(o);
        }

        @Override
		public void clear() {
          IterableMap.this.clear();
        }
      };
    }
    return values;
  }

  public class Mapping_Iterator implements Iterator {
    private final Iterator it;
    private HashMap<Object, Object> m;

    public Mapping_Iterator(HashChain c, HashMap<Object, Object> m) {
      it = c.iterator();
      this.m = m;
    }

    @Override
	public boolean hasNext() {
      return it.hasNext();
    }

    @Override
	public Object next() {
      return m.get(it.next());
    }

    @Override
	public void remove() {
      throw new UnsupportedOperationException("You cannot remove from an Iterator on the values() for an IterableMap.");
    }
  }

}
