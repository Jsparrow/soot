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
  * @ast class
 * 
 */
public class Constant extends java.lang.Object {

    public boolean error = false;



	protected Constant() {
    }


	int intValue() { throw new UnsupportedOperationException(); }


	long longValue() { throw new UnsupportedOperationException(); }


	float floatValue() { throw new UnsupportedOperationException(); }


	double doubleValue() { throw new UnsupportedOperationException(); }


	boolean booleanValue() { throw new UnsupportedOperationException(getClass().getName()); }


	String stringValue() { throw new UnsupportedOperationException(); }


	static Constant create(int i) { return new ConstantInt(i); }


	static Constant create(long l) { return new ConstantLong(l); }


	static Constant create(float f) { return new ConstantFloat(f); }


	static Constant create(double d) { return new ConstantDouble(d); }


	static Constant create(boolean b) { return new ConstantBoolean(b); }


	static Constant create(char c) { return new ConstantChar(c); }


	static Constant create(String s) { return new ConstantString(s); }


	static class ConstantInt extends Constant {
      private int value;
      public ConstantInt(int i) { this.value = i; }
      @Override
	int intValue() { return value; }
      @Override
	long longValue() { return value; }
      @Override
	float floatValue() { return value; }
      @Override
	double doubleValue() { return value; }
      @Override
	String stringValue() { return Integer.toString(value); }
    }


    static class ConstantLong extends Constant {
      private long value;
      public ConstantLong(long l) { this.value = l; }
      @Override
	int intValue() { return (int)value; }
      @Override
	long longValue() { return value; }
      @Override
	float floatValue() { return value; }
      @Override
	double doubleValue() { return value; }
      @Override
	String stringValue() { return Long.toString(value); }
    }


    static class ConstantFloat extends Constant {
      private float value;
      public ConstantFloat(float f) { this.value = f; }
      @Override
	int intValue() { return (int)value; }
      @Override
	long longValue() { return (long)value; }
      @Override
	float floatValue() { return value; }
      @Override
	double doubleValue() { return value; }
      @Override
	String stringValue() { return Float.toString(value); }
    }


    static class ConstantDouble extends Constant {
      private double value;
      public ConstantDouble(double d) { this.value = d; }
      @Override
	int intValue() { return (int)value; }
      @Override
	long longValue() { return (long)value; }
      @Override
	float floatValue() { return (float)value; }
      @Override
	double doubleValue() { return value; }
      @Override
	String stringValue() { return Double.toString(value); }
    }


    static class ConstantChar extends Constant {
      private char value;
      public ConstantChar(char c) { this.value = c; }
      @Override
	int intValue() { return value; }
      @Override
	long longValue() { return value; }
      @Override
	float floatValue() { return value; }
      @Override
	double doubleValue() { return value; }
      @Override
	String stringValue() { return Character.valueOf(value).toString(); }
    }


    static class ConstantBoolean extends Constant {
      private boolean value;
      public ConstantBoolean(boolean b) { this.value = b; }
      @Override
	boolean booleanValue() { return value; }
      @Override
	String stringValue() { return Boolean.valueOf(value).toString(); }
    }


    static class ConstantString extends Constant {
      private String value;
      public ConstantString(String s) { this.value = s; }
      @Override
	String stringValue() { return value; }
    }


}
