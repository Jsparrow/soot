package soot.toolkits.exceptions.targets;

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


public class MethodThrowableSetClass {
	private static final Logger logger = LoggerFactory.getLogger(MethodThrowableSetClass.class);
	public static  target c;

	public void recursion(){
		try{
			int a = 0;
			int b = 1;
			int c = 0;
			recursion();
			c = a/b;
		}catch(OutOfMemoryError | ArithmeticException e){
			logger.error(e.getMessage(), e);
		}
	}

	public void nestedTry() {
		try{
			int array[] = new int[10];
			int b = 0;
			int c = array[9]/b;
			try{
			     c = 3/b;
			}catch(ArithmeticException e){
				logger.error(e.getMessage(), e);
			}
		}catch(NegativeArraySizeException e){
			logger.error(e.getMessage(), e);
		}
	}

	public void unitInCatchBlock(){
		try{
			int a = 0;
			int b = 0; 
			int c = a/b;
		}catch(ArithmeticException e){
			int a0 = 0;
			int b0 = 0; 
			int c0 = a0/b0;
			logger.error(e.getMessage(), e);
		}
	}

	public void foo(){
		try{
			bar();
		}catch(ThreadDeath | StackOverflowError e){
			logger.error(e.getMessage(), e);
		}
		
	}

	private void bar(){
		try{
			tool();
		}catch(ArrayIndexOutOfBoundsException e){
			logger.error(e.getMessage(), e);
		}
		
	}

	public void tool(){
		try{
			int array[] = new int[10];
			int d = 0;
			int c = array[0]/d;
		}catch(NegativeArraySizeException e){
			logger.error(e.getMessage(), e);
		}
	}

	public void getAllException(){
		try{
			tool();
		}catch(RuntimeException | Error e){
			logger.error(e.getMessage(), e);
		}
	}

	public void getMyException() {
		try{
			throw new MyException();
		}catch(MyException e){
			logger.error(e.getMessage(), e);
		}
	}

	class target{
		private final Logger logger = LoggerFactory.getLogger(target.class);
		public target(){
			
		}
		public int foo(int a, int b){
			try{
				a = 0;
				int c = b/a;
				return a + b;
			}catch(ArithmeticException e){
				logger.error(e.getMessage(), e);
				return 0;
			}
		}
	}
}
