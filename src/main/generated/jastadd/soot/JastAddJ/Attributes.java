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
  * @ast class
 * 
 */
public class Attributes extends java.lang.Object {

    protected BytecodeParser p;


    protected boolean isSynthetic;



    protected Attributes(BytecodeParser parser) {
      p = parser;
      isSynthetic = false;
    }



    protected void processAttribute(String attribute_name, int attribute_length) {
      if("Synthetic".equals(attribute_name)) {
        isSynthetic = true;
      } else {
        this.p.skip(attribute_length);
      }
    }



    protected void attributes() {
      int attributes_count = p.u2();
      if(BytecodeParser.VERBOSE) {
		p.println(new StringBuilder().append("    ").append(attributes_count).append(" attributes:").toString());
	}
      for (int j = 0; j < attributes_count; j++) {
        int attribute_name_index = p.u2();
        int attribute_length = p.u4();
        String attribute_name = p.getCONSTANT_Utf8_Info(attribute_name_index).string();
        if(BytecodeParser.VERBOSE) {
			p.println(new StringBuilder().append("    Attribute: ").append(attribute_name).append(", length: ").append(attribute_length).toString());
		}
        processAttribute(attribute_name, attribute_length);
      }
    }



    public boolean isSynthetic() {
      return isSynthetic;
    }



    // 4.8.15.1
    protected ElementValue readElementValue() {
      char c = (char)p.u1();
      switch (c) {
        case 'e':
          int type_name_index = this.p.u2();
          String type_name = this.p.getCONSTANT_Utf8_Info(type_name_index).string();
          // remove inital L and trailing ;
          Access typeAccess = this.p.fromClassName(type_name.substring(1, type_name.length() - 1));
          int const_name_index = this.p.u2();
          String const_name = this.p.getCONSTANT_Utf8_Info(const_name_index).string();
          return new ElementConstantValue(typeAccess.qualifiesAccess(new VarAccess(const_name)));
        case 'B': case 'C': case 'D': case 'F': case 'I': case 'J': case 'S': case 'Z': case 's':
          int const_value_index = p.u2();
          Expr e = this.p.getCONSTANT_Info(const_value_index).expr();
          return new ElementConstantValue(e);
        case 'c':
          int class_info_index = p.u2();
          String descriptor = this.p.getCONSTANT_Utf8_Info(class_info_index).string();
          e = new TypeDescriptor(p, descriptor).type();
          return new ElementConstantValue(e);
        case '@':
          return new ElementAnnotationValue(readAnnotation());
        case '[':
          int index = p.u2();
          List list = new List();
          for(int i = 0; i < index; i++) {
			list.add(readElementValue());
		}
          return new ElementArrayValue(list);
        default:
          throw new Error(new StringBuilder().append("AnnotationDefault tag ").append(c).append(" not supported").toString());
      }
    }



    // 4.8.15
    protected Annotation readAnnotation() {
      Access typeAccess = new FieldDescriptor(p, "").type();
      int num_element_value_pairs = p.u2();
      List list = new List();
      for(int i = 0; i < num_element_value_pairs; i++) {
        int element_name_index = p.u2();
        String element_name = p.getCONSTANT_Utf8_Info(element_name_index).string();
        ElementValue element_value = readElementValue();
        list.add(new ElementValuePair(element_name, element_value));
      }
      return new Annotation("Annotation", typeAccess, list);
    }



    public static class FieldAttributes extends Attributes {
      protected CONSTANT_Info constantValue;
      public java.util.ArrayList annotations;
      public Signatures.FieldSignature fieldSignature;
      public FieldAttributes(BytecodeParser p) {
        super(p);
        attributes();
      }

      @Override
	protected void processAttribute(String attribute_name, int attribute_length) {
        if("ConstantValue".equals(attribute_name) && attribute_length == 2) {
          int constantvalue_index = p.u2();
          constantValue = p.getCONSTANT_Info(constantvalue_index);
        }
        else if("RuntimeVisibleAnnotations".equals(attribute_name)) {
          int num_annotations = p.u2();
          if(annotations == null) {
			annotations = new java.util.ArrayList();
		}
          for(int j = 0; j < num_annotations; j++) {
			annotations.add(readAnnotation());
		}
        }
        else if("RuntimeInvisibleAnnotations".equals(attribute_name)) {
          int num_annotations = p.u2();
          if(annotations == null) {
			annotations = new java.util.ArrayList();
		}
          for(int j = 0; j < num_annotations; j++) {
			annotations.add(readAnnotation());
		}
        }
        else if("Signature".equals(attribute_name)) {
          int signature_index = p.u2();
          String s = p.getCONSTANT_Utf8_Info(signature_index).string();
          fieldSignature = new Signatures.FieldSignature(s);
        }
        else {
          super.processAttribute(attribute_name, attribute_length);
        }
      }

      public CONSTANT_Info constantValue() {
        return constantValue;
      }
    }



    public static class MethodAttributes extends Attributes {
      protected List exceptionList;
      protected ElementValue elementValue;
      public Signatures.MethodSignature methodSignature;
      public java.util.ArrayList annotations;
      public java.util.ArrayList[] parameterAnnotations;

      public MethodAttributes(BytecodeParser p) {
        super(p);
        attributes();
      }

      @Override
	protected void processAttribute(String attribute_name, int attribute_length) {
        if("Exceptions".equals(attribute_name)) {
          parseExceptions();
        } 
        else if("AnnotationDefault".equals(attribute_name)) {
          annotationDefault();
        }
        else if("Signature".equals(attribute_name)) {
          int signature_index = p.u2();
          String s = p.getCONSTANT_Utf8_Info(signature_index).string();
          methodSignature = new Signatures.MethodSignature(s);
        }
        else if("RuntimeVisibleAnnotations".equals(attribute_name)) {
          int num_annotations = p.u2();
          if(annotations == null) {
			annotations = new java.util.ArrayList();
		}
          for(int j = 0; j < num_annotations; j++) {
			annotations.add(readAnnotation());
		}
        }
        else if("RuntimeInvisibleAnnotations".equals(attribute_name)) {
          int num_annotations = p.u2();
          if(annotations == null) {
			annotations = new java.util.ArrayList();
		}
          for(int j = 0; j < num_annotations; j++) {
			annotations.add(readAnnotation());
		}
        }
        else if("RuntimeVisibleParameterAnnotations".equals(attribute_name)) {
          int num_parameters = p.u1();
          if(parameterAnnotations == null) {
			parameterAnnotations = new java.util.ArrayList[num_parameters];
		}
          for(int i = 0; i < num_parameters; i++) {
            if(parameterAnnotations[i] == null) {
				parameterAnnotations[i] = new java.util.ArrayList();
			}
            int num_annotations = p.u2();
            for(int j = 0; j < num_annotations; j++) {
				parameterAnnotations[i].add(readAnnotation());
			}
          }
        }
        else if("RuntimeInvisibleParameterAnnotations".equals(attribute_name)) {
          int num_parameters = p.u1();
          if(parameterAnnotations == null) {
			parameterAnnotations = new java.util.ArrayList[num_parameters];
		}
          for(int i = 0; i < num_parameters; i++) {
            if(parameterAnnotations[i] == null) {
				parameterAnnotations[i] = new java.util.ArrayList();
			}
            int num_annotations = p.u2();
            for(int j = 0; j < num_annotations; j++) {
				parameterAnnotations[i].add(readAnnotation());
			}
          }
        }
        /*
        else if(attribute_name.equals("RuntimeVisibleExtendedAnnotations")) {
          int num_annotations = p.u2();
          java.util.ArrayList extendedAnnotations = new java.util.ArrayList();
          for(int j = 0; j < num_annotations; j++) {
            extendedAnnotations.add(readAnnotation());
          }
          int kind = p.u1();
          if(kind == 0x06)
            System.out.println("Found receiver annotation");
          if(kind >= 0x00 && kind <= 0x05) // typecast, instaceof, new
            p.u2(); // skip 2
          else if(kind == 0x08 || kind == 0x09) { // local variable
            int table_length = p.u2();
            for(int i = 0; i < table_length; i++)
              p.skip(6);
          }
          else if(kind >= 0x10 && kind <= 0x13) // bound
            p.skip(2);
          else if(kind >= 0x14 && kind <= 0x15) // extends, implements
            p.skip(1);
          else if(kind >= 0x16 && kind <= 0x17) // throws
            p.skip(1);


          this.p.skip(attribute_length);
        }
        */
        else {
          super.processAttribute(attribute_name, attribute_length);
        }
      }

      private void parseExceptions() {
        int number_of_exceptions = p.u2();
        exceptionList = new List();
        if(BytecodeParser.VERBOSE) {
			p.println(new StringBuilder().append("      ").append(number_of_exceptions).append(" exceptions:").toString());
		}
        for (int i = 0; i < number_of_exceptions; i++) {
          CONSTANT_Class_Info exception = p.getCONSTANT_Class_Info(p.u2());
          if(BytecodeParser.VERBOSE) {
			p.println("        exception " + exception.name());
		}
          exceptionList.add(exception.access());
        }
      }

      public List exceptionList() {
        return exceptionList != null ? exceptionList : new List();
      }

      public ElementValue elementValue() {
        return elementValue;
      }

      // 4.8.19
      private void annotationDefault() {
        elementValue = readElementValue();
      }

    }



    public static class TypeAttributes extends Attributes {
      private final Logger logger = LoggerFactory.getLogger(TypeAttributes.class);
	TypeDecl typeDecl;
      TypeDecl outerTypeDecl;
      Program classPath;
      
      private boolean isInnerClass;
	
      public TypeAttributes(BytecodeParser p, TypeDecl typeDecl, TypeDecl outerTypeDecl, Program classPath) {
        super(p);
        this.typeDecl = typeDecl;
        this.outerTypeDecl = outerTypeDecl;
        this.classPath = classPath;
        attributes();
      }

	  public boolean isInnerClass() {
		  return isInnerClass;
  	  }
  	  
      @Override
	protected void processAttribute(String attribute_name, int attribute_length) {
        if("InnerClasses".equals(attribute_name)) {
          innerClasses();
        }
        else if("Signature".equals(attribute_name)) {
          int signature_index = p.u2();
          String s = p.getCONSTANT_Utf8_Info(signature_index).string();
          Signatures.ClassSignature classSignature = new Signatures.ClassSignature(s);
          typeDecl = typeDecl.makeGeneric(classSignature);
        }
        else if("RuntimeVisibleAnnotations".equals(attribute_name)) {
          int num_annotations = p.u2();
          //System.out.println("RuntimeVisibleAnnotations: " + num_annotations);
          for(int j = 0; j < num_annotations; j++) {
            Annotation a = readAnnotation();
            typeDecl.getModifiers().addModifier(a);
          }
        }
        else if("RuntimeInvisibleAnnotations".equals(attribute_name)) {
          int num_annotations = p.u2();
          //System.out.println("RuntimeInvisibleAnnotations: " + num_annotations);
          for(int j = 0; j < num_annotations; j++) {
            Annotation a = readAnnotation();
            typeDecl.getModifiers().addModifier(a);
          }
        }
        else {
          super.processAttribute(attribute_name, attribute_length);
        }
      }

    protected void innerClasses() {
    	  int number_of_classes = this.p.u2();
    	  if(BytecodeParser.VERBOSE) {
			p.println("    Number of classes: " + number_of_classes);
		}
    	  for (int i = 0; i < number_of_classes; i++) {
    		  if(BytecodeParser.VERBOSE) {
				p.print(new StringBuilder().append("      ").append(i).append("(").append(number_of_classes).append(")").append(":")
						.toString());
			}
    		  int inner_class_info_index = this.p.u2();
    		  int outer_class_info_index = this.p.u2();
    		  int inner_name_index = this.p.u2();
    		  int inner_class_access_flags = this.p.u2();
    		  if(inner_class_info_index > 0) {
    			  CONSTANT_Class_Info inner_class_info = this.p.getCONSTANT_Class_Info(inner_class_info_index);
    			  String inner_class_name = inner_class_info.name();
   				  String inner_name = inner_class_name.substring(inner_class_name.lastIndexOf("$")+1);
    			  String outer_class_name;
    			  if (outer_class_info_index > 0) {
    				  CONSTANT_Class_Info outer_class_info = this.p.getCONSTANT_Class_Info(outer_class_info_index);
    				  if(inner_class_info == null || outer_class_info == null) {
    					  logger.info("Null");
    				  }
    				  outer_class_name = outer_class_info.name();

    				  if(BytecodeParser.VERBOSE) {
						this.p.println(new StringBuilder().append("      inner: ").append(inner_class_name).append(", outer: ").append(outer_class_name).toString());
					}

    			  } else {
    			  	  //anonymous inner class; need to infer outer_class_name from inner_class_name
    				  outer_class_name = inner_class_name.substring(0,inner_class_name.lastIndexOf("$"));
    			  }
    			  if (inner_class_info.name().equals(p.classInfo.name())) {
    				  if(BytecodeParser.VERBOSE) {
						p.println(new StringBuilder().append("      Class ").append(inner_class_name).append(" is inner (").append(inner_name).append(")").toString());
					}
    				  typeDecl.setID(inner_name);
    				  typeDecl.setModifiers(BytecodeParser.modifiers(inner_class_access_flags & 0x041f));
    				  if (this.p.outerClassName != null && this.p.outerClassName.equals(outer_class_name)) {
    					  MemberTypeDecl m = null;
    					  if (typeDecl instanceof ClassDecl) {
    						  m = new MemberClassDecl((ClassDecl)typeDecl);
    						  outerTypeDecl.addBodyDecl(m);
    					  } else if (typeDecl instanceof InterfaceDecl) {
    						  m = new MemberInterfaceDecl((InterfaceDecl)typeDecl);
    						  outerTypeDecl.addBodyDecl(m);
    					  }
    				  } else {
    					  isInnerClass = true;
    				  }
    			  }
    			  if (outer_class_name.equals(this.p.classInfo.name())) {
    				  if(BytecodeParser.VERBOSE) {
						p.println(new StringBuilder().append("      Class ").append(this.p.classInfo.name()).append(" has inner class: ").append(inner_class_name).toString());
					}
    				  if(BytecodeParser.VERBOSE) {
						p.println("Begin processing: " + inner_class_name);
					}
    				  try {
    					  java.io.InputStream is=null;
    					  try{
    						  is = classPath.getInputStream(inner_class_name);
    						  if(is != null) {
        						  BytecodeParser p = new BytecodeParser(is, this.p.name);
        						  p.parse(typeDecl, outer_class_name, classPath, (inner_class_access_flags & Flags.ACC_STATIC) == 0);
        					  } else {
        						  p.println(new StringBuilder().append("Error: ClassFile ").append(inner_class_name).append(" not found").toString());
          					  }
    					  } catch(Error e) {
    						  if(!e.getMessage().startsWith("Could not find nested type")) {
    							 throw e;
    						  }
    					  } finally {
    						  if(is != null){
    							  is.close();
    							  is = null;
    						  }
    					  }
    				  } catch (FileNotFoundException e) {
    					  logger.error(e.getMessage(), e);
						logger.info(new StringBuilder().append("Error: ").append(inner_class_name).append(" not found").toString());
    				  } catch (Exception e) {
    					  throw new RuntimeException(e);
    				  }
    				  if(BytecodeParser.VERBOSE) {
						p.println("End processing: " + inner_class_name);
					}
    			  }
    		  }

    	  }
    	  if(BytecodeParser.VERBOSE) {
			p.println("    end");
		}
      }
    }


}
