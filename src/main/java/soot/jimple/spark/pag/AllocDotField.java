package soot.jimple.spark.pag;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2002 Ondrej Lhotak
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
 * Represents an alloc-site-dot-field node (Yellow) in the pointer assignment graph.
 * 
 * @author Ondrej Lhotak
 */
public class AllocDotField extends Node {
  /* End of package methods. */
	
	  protected AllocNode base;
	protected SparkField field;
	/* End of public methods. */
	
	  AllocDotField(PAG pag, AllocNode base, SparkField field) {
	    super(pag, null);
	    if (field == null) {
	      throw new RuntimeException("null field");
	    }
	    this.base = base;
	    this.field = field;
	    base.addField(this, field);
	    pag.getAllocDotFieldNodeNumberer().add(this);
	  }

	/** Returns the base AllocNode. */
	  public AllocNode getBase() {
	    return base;
	  }

	/** Returns the field of this node. */
	  public SparkField getField() {
	    return field;
	  }

	@Override
	public String toString() {
	    return new StringBuilder().append("AllocDotField ").append(getNumber()).append(" ").append(base).append(".").append(field)
				.toString();
	  }
}
