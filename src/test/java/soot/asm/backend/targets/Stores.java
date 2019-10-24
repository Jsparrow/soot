package soot.asm.backend.targets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2018 Raja Vallée-Rai and others
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

public class Stores {

	private static final Logger logger = LoggerFactory.getLogger(Stores.class);

	public int doSth(){
		int i;
		double d;
		float f;
		short s;
		boolean b;
		byte bb;
		long l;
		char c;
		Object o;
		int[] a;
		
		
		i = 2343249;
		d = 3.14324;
		f = 3.143f;
		s = 4636;
		b = System.currentTimeMillis() > 0;
		bb = (byte) i;
		l = 314435665;
		c = 123;
		o = new Object();
		a = new int[3];

		a[1] = 24355764;
		
		logger.info(d + f + s + Integer.toString(i) +b + bb + l + c + " " + o);
		
		return i;
	}
}
