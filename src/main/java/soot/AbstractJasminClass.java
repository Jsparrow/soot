package soot;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 Patrick Lam, Patrick Pominville and Raja Vallee-Rai
 * Copyright (C) 2004 Jennifer Lhotak, Ondrej Lhotak
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

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.baf.DoubleWordType;
import soot.jimple.DoubleConstant;
import soot.jimple.FloatConstant;
import soot.jimple.IdentityStmt;
import soot.jimple.Stmt;
import soot.options.Options;
import soot.tagkit.AnnotationAnnotationElem;
import soot.tagkit.AnnotationArrayElem;
import soot.tagkit.AnnotationBooleanElem;
import soot.tagkit.AnnotationClassElem;
import soot.tagkit.AnnotationConstants;
import soot.tagkit.AnnotationDefaultTag;
import soot.tagkit.AnnotationDoubleElem;
import soot.tagkit.AnnotationElem;
import soot.tagkit.AnnotationEnumElem;
import soot.tagkit.AnnotationFloatElem;
import soot.tagkit.AnnotationIntElem;
import soot.tagkit.AnnotationLongElem;
import soot.tagkit.AnnotationStringElem;
import soot.tagkit.AnnotationTag;
import soot.tagkit.Attribute;
import soot.tagkit.Base64;
import soot.tagkit.DoubleConstantValueTag;
import soot.tagkit.EnclosingMethodTag;
import soot.tagkit.FloatConstantValueTag;
import soot.tagkit.InnerClassAttribute;
import soot.tagkit.InnerClassTag;
import soot.tagkit.IntegerConstantValueTag;
import soot.tagkit.LongConstantValueTag;
import soot.tagkit.SignatureTag;
import soot.tagkit.SourceFileTag;
import soot.tagkit.StringConstantValueTag;
import soot.tagkit.Tag;
import soot.tagkit.VisibilityAnnotationTag;
import soot.tagkit.VisibilityParameterAnnotationTag;
import soot.toolkits.graph.Block;

public abstract class AbstractJasminClass {
  private static final Logger logger = LoggerFactory.getLogger(AbstractJasminClass.class);
private static Map<Integer, VisibilityAnnotationTag> safeVats = new HashMap<>();
protected Map<Unit, String> unitToLabel;
protected Map<Local, Integer> localToSlot;
protected Map<Unit, Integer> subroutineToReturnAddressSlot;
protected List<String> code;
protected boolean isEmittingMethodCode;
protected int labelCount;
protected boolean isNextGotoAJsr;
protected int returnAddressSlot;
protected int currentStackHeight = 0;
protected int maxStackHeight = 0;
protected Map<Local, Object> localToGroup;
protected Map<Object, Integer> groupToColorCount;
protected Map<Local, Integer> localToColor;
protected Map<Block, Integer> blockToStackHeight = new HashMap<>(); // maps a block to the stack height upon
// entering it
  protected Map<Block, Integer> blockToLogicalStackHeight = new HashMap<>(); // maps a block to the logical
public AbstractJasminClass(SootClass sootClass) {
    if (Options.v().time()) {
      Timers.v().buildJasminTimer.start();
    }

    if (Options.v().verbose()) {
      logger.debug(new StringBuilder().append("[").append(sootClass.getName()).append("] Constructing baf.JasminClass...").toString());
    }

    code = new LinkedList<>();

    // Emit the header
    {
      int modifiers = sootClass.getModifiers();

      if ((sootClass.getTag("SourceFileTag") != null) && (!Options.v().no_output_source_file_attribute())) {
        String srcName = ((SourceFileTag) sootClass.getTag("SourceFileTag")).getSourceFile();
        // Since Jasmin fails on backslashes and only Windows uses backslashes,
        // but also accepts forward slashes, we transform it.
        if (File.separatorChar == '\\') {
          srcName = srcName.replace('\\', '/');
        }
        srcName = soot.util.StringTools.getEscapedStringOf(srcName);

        // if 'srcName' starts with a digit, Jasmin throws an
        // 'Badly formatted number' error. When analyzing an Android
        // applications (.apk) their name is stored in srcName and
        // can start with a digit.
        if (!Options.v().android_jars().isEmpty() && !srcName.isEmpty() && Character.isDigit(srcName.charAt(0))) {
          srcName = "n_" + srcName;
        }

        // Jasmin does not support blanks and quotes, so get rid of them
        srcName = srcName.replace(" ", "-");
        srcName = srcName.replace("\"", "");

        if (!srcName.isEmpty()) {
          emit(".source " + srcName);
        }
      }
      if (Modifier.isInterface(modifiers)) {
        modifiers -= Modifier.INTERFACE;

        emit(new StringBuilder().append(".interface ").append(Modifier.toString(modifiers)).append(" ").append(slashify(sootClass.getName())).toString());
      } else {
        emit(new StringBuilder().append(".class ").append(Modifier.toString(modifiers)).append(" ").append(slashify(sootClass.getName())).toString());
      }

      if (sootClass.hasSuperclass()) {
        emit(".super " + slashify(sootClass.getSuperclass().getName()));
      } else {
        emit(".no_super");
      }

      emit("");
    }

    // Emit the interfaces
    {
      Iterator<SootClass> interfaceIt = sootClass.getInterfaces().iterator();

      while (interfaceIt.hasNext()) {
        SootClass inter = interfaceIt.next();

        emit(".implements " + slashify(inter.getName()));
      }

      /*
       * why do this???? if(sootClass.getInterfaceCount() != 0) emit("");
       */
    }

    // emit class attributes.
    Iterator<Tag> it = sootClass.getTags().iterator();
    while (it.hasNext()) {
      Tag tag = it.next();
      if (tag instanceof Attribute) {
        emit(new StringBuilder().append(".class_attribute ").append(tag.getName()).append(" \"").append(new String(Base64.encode(((Attribute) tag).getValue()))).append("\"").toString());
        /*
         * else { emit(""); }
         */
      }
    }

    // emit synthetic attributes
    if (sootClass.hasTag("SyntheticTag") || Modifier.isSynthetic(sootClass.getModifiers())) {
      emit(".synthetic\n");
    }
    // emit inner class attributes
    InnerClassAttribute ica = (InnerClassAttribute) sootClass.getTag("InnerClassAttribute");
    boolean condition = ica != null && ica.getSpecs().size() > 0 && !Options.v().no_output_inner_classes_attribute();
	if (condition) {
        emit(".inner_class_attr ");
        // System.out.println("inner class tag: "+ict);
		((InnerClassAttribute) sootClass.getTag("InnerClassAttribute")).getSpecs().forEach(ict -> emit(new StringBuilder().append(".inner_class_spec_attr ").append("\"").append(ict.getInnerClass()).append("\" ").append("\"").append(ict.getOuterClass()).append("\" ")
				.append("\"").append(ict.getShortName()).append("\" ").append(Modifier.toString(ict.getAccessFlags())).append(" ").append(".end .inner_class_spec_attr").toString()));
        emit(".end .inner_class_attr\n");
      }
    if (sootClass.hasTag("EnclosingMethodTag")) {
      String encMeth = ".enclosing_method_attr ";
      EnclosingMethodTag eMethTag = (EnclosingMethodTag) sootClass.getTag("EnclosingMethodTag");
      encMeth += new StringBuilder().append("\"").append(eMethTag.getEnclosingClass()).append("\" ").toString();
      encMeth += new StringBuilder().append("\"").append(eMethTag.getEnclosingMethod()).append("\" ").toString();
      encMeth += new StringBuilder().append("\"").append(eMethTag.getEnclosingMethodSig()).append("\"\n").toString();
      emit(encMeth);
    }
    // emit deprecated attributes
    if (sootClass.hasTag("DeprecatedTag")) {
      emit(".deprecated\n");
    }
    if (sootClass.hasTag("SignatureTag")) {
      String sigAttr = ".signature_attr ";
      SignatureTag sigTag = (SignatureTag) sootClass.getTag("SignatureTag");
      sigAttr += new StringBuilder().append("\"").append(sigTag.getSignature()).append("\"\n").toString();
      emit(sigAttr);
    }

    Iterator<Tag> vit = sootClass.getTags().iterator();
    while (vit.hasNext()) {
      Tag t = (Tag) vit.next();
      if ("VisibilityAnnotationTag".equals(t.getName())) {
        emit(getVisibilityAnnotationAttr((VisibilityAnnotationTag) t));
      }
    }

    // Emit the fields
    {
      Iterator<SootField> fieldIt = sootClass.getFields().iterator();

      while (fieldIt.hasNext()) {
        SootField field = (SootField) fieldIt.next();

        String fieldString = new StringBuilder().append(".field ").append(Modifier.toString(field.getModifiers())).append(" ").append("\"").append(field.getName()).append("\"").append(" ")
				.append(jasminDescriptorOf(field.getType())).toString();

        if (field.hasTag("StringConstantValueTag")) {
          fieldString += " = ";
          fieldString += soot.util.StringTools
              .getQuotedStringOf(((StringConstantValueTag) field.getTag("StringConstantValueTag")).getStringValue());
        } else if (field.hasTag("IntegerConstantValueTag")) {
          fieldString += " = ";
          fieldString += ((IntegerConstantValueTag) field.getTag("IntegerConstantValueTag")).getIntValue();
        } else if (field.hasTag("LongConstantValueTag")) {
          fieldString += " = ";
          fieldString += ((LongConstantValueTag) field.getTag("LongConstantValueTag")).getLongValue();
        } else if (field.hasTag("FloatConstantValueTag")) {
          fieldString += " = ";
          FloatConstantValueTag val = (FloatConstantValueTag) field.getTag("FloatConstantValueTag");
          fieldString += floatToString(val.getFloatValue());
        } else if (field.hasTag("DoubleConstantValueTag")) {
          fieldString += " = ";
          DoubleConstantValueTag val = (DoubleConstantValueTag) field.getTag("DoubleConstantValueTag");
          fieldString += doubleToString(val.getDoubleValue());
        }
        if (field.hasTag("SyntheticTag") || Modifier.isSynthetic(field.getModifiers())) {
          fieldString += " .synthetic";
        }

        fieldString += "\n";
        if (field.hasTag("DeprecatedTag")) {
          fieldString += ".deprecated\n";
        }
        if (field.hasTag("SignatureTag")) {
          fieldString += ".signature_attr ";
          SignatureTag sigTag = (SignatureTag) field.getTag("SignatureTag");
          fieldString += new StringBuilder().append("\"").append(sigTag.getSignature()).append("\"\n").toString();
        }
        Iterator<Tag> vfit = field.getTags().iterator();
        while (vfit.hasNext()) {
          Tag t = (Tag) vfit.next();
          if ("VisibilityAnnotationTag".equals(t.getName())) {
            fieldString += getVisibilityAnnotationAttr((VisibilityAnnotationTag) t);
          }
        }

        emit(fieldString);

        Iterator<Tag> attributeIt = field.getTags().iterator();
        while (attributeIt.hasNext()) {
          Tag tag = (Tag) attributeIt.next();
          if (tag instanceof Attribute) {
            emit(new StringBuilder().append(".field_attribute ").append(tag.getName()).append(" \"").append(new String(Base64.encode(((Attribute) tag).getValue()))).append("\"").toString());
          }
        }

      }

      if (sootClass.getFieldCount() != 0) {
        emit("");
      }
    }

    // Emit the methods
    {
      Iterator<SootMethod> methodIt = sootClass.methodIterator();

      while (methodIt.hasNext()) {
        emitMethod(methodIt.next());
        emit("");
      }
    }

    if (Options.v().time()) {
      Timers.v().buildJasminTimer.end();
    }
  }

// stack height upon entering
   // it

  public static String slashify(String s) {
   return s.replace('.', '/');
  }

public static int sizeOfType(Type t) {
    if (t instanceof DoubleWordType || t instanceof LongType || t instanceof DoubleType) {
      return 2;
    } else if (t instanceof VoidType) {
      return 0;
    } else {
      return 1;
    }
  }

public static int argCountOf(SootMethodRef m) {
    int argCount = 0;
    Iterator<Type> typeIt = m.parameterTypes().iterator();

    while (typeIt.hasNext()) {
      Type t = (Type) typeIt.next();

      argCount += sizeOfType(t);
    }

    return argCount;
  }

public static String jasminDescriptorOf(Type type) {
    TypeSwitch sw;

    type.apply(sw = new TypeSwitch() {
      @Override
	public void caseBooleanType(BooleanType t) {
        setResult("Z");
      }

      @Override
	public void caseByteType(ByteType t) {
        setResult("B");
      }

      @Override
	public void caseCharType(CharType t) {
        setResult("C");
      }

      @Override
	public void caseDoubleType(DoubleType t) {
        setResult("D");
      }

      @Override
	public void caseFloatType(FloatType t) {
        setResult("F");
      }

      @Override
	public void caseIntType(IntType t) {
        setResult("I");
      }

      @Override
	public void caseLongType(LongType t) {
        setResult("J");
      }

      @Override
	public void caseShortType(ShortType t) {
        setResult("S");
      }

      @Override
	public void defaultCase(Type t) {
        throw new RuntimeException("Invalid type: " + t);
      }

      @Override
	public void caseArrayType(ArrayType t) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < t.numDimensions; i++) {
          buffer.append("[");
        }

        setResult(buffer.toString() + jasminDescriptorOf(t.baseType));
      }

      @Override
	public void caseRefType(RefType t) {
        setResult(new StringBuilder().append("L").append(t.getClassName().replace('.', '/')).append(";").toString());
      }

      @Override
	public void caseVoidType(VoidType t) {
        setResult("V");
      }
    });

    return (String) sw.getResult();

  }

public static String jasminDescriptorOf(SootMethodRef m) {
    StringBuilder buffer = new StringBuilder();

    buffer.append("(");

    // Add methods parameters
    {
      Iterator<Type> typeIt = m.parameterTypes().iterator();

      while (typeIt.hasNext()) {
        Type t = (Type) typeIt.next();

        buffer.append(jasminDescriptorOf(t));
      }
    }

    buffer.append(")");

    buffer.append(jasminDescriptorOf(m.returnType()));

    return buffer.toString();
  }

protected void emit(String s) {
    okayEmit(s);
  }

protected void okayEmit(String s) {
    if (isEmittingMethodCode && !s.endsWith(":")) {
      code.add("    " + s);
    } else {
      code.add(s);
    }
  }

private String getVisibilityAnnotationAttr(VisibilityAnnotationTag tag) {
    StringBuilder sb = new StringBuilder();
    if (tag == null) {
      return "";
    } else if (tag.getVisibility() == AnnotationConstants.RUNTIME_VISIBLE) {
      sb.append(".runtime_visible_annotation\n");
    } else if (tag.getVisibility() == AnnotationConstants.RUNTIME_INVISIBLE) {
      sb.append(".runtime_invisible_annotation\n");
    } else {
      // source level annotation
      return "";
    }
    if (tag.hasAnnotations()) {
      Iterator<AnnotationTag> it = tag.getAnnotations().iterator();
      while (it.hasNext()) {
        AnnotationTag annot = it.next();
        sb.append(".annotation ");
        sb.append(soot.util.StringTools.getQuotedStringOf(annot.getType()) + "\n");
        annot.getElems().forEach(ae -> sb.append(getElemAttr(ae)));
        sb.append(".end .annotation\n");
      }
    }
    sb.append(".end .annotation_attr\n");
    return sb.toString();
  }

private String getVisibilityParameterAnnotationAttr(VisibilityParameterAnnotationTag tag) {
    StringBuilder sb = new StringBuilder();
    sb.append(".param ");
    if (tag.getKind() == AnnotationConstants.RUNTIME_VISIBLE) {
      sb.append(".runtime_visible_annotation\n");
    } else {
      sb.append(".runtime_invisible_annotation\n");
    }
    ArrayList<VisibilityAnnotationTag> vis_list = tag.getVisibilityAnnotations();
    if (vis_list != null) {
      vis_list.stream().map(vat -> vat == null ? getSafeVisibilityAnnotationTag(tag.getKind()) : vat).forEach(safeVat -> sb.append(getVisibilityAnnotationAttr(safeVat)));
    }
    sb.append(".end .param\n");
    return sb.toString();
  }

private VisibilityAnnotationTag getSafeVisibilityAnnotationTag(int kind) {
    VisibilityAnnotationTag safeVat = safeVats.get(kind);
    if (safeVat == null) {
      safeVats.put(kind, safeVat = new VisibilityAnnotationTag(kind));
    }
    return safeVat;
  }

private String getElemAttr(AnnotationElem elem) {
    StringBuilder result = new StringBuilder(".elem ");
    switch (elem.getKind()) {
      case 'Z': {
        result.append(".bool_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        if (elem instanceof AnnotationIntElem) {
          result.append(((AnnotationIntElem) elem).getValue());
        } else {
          if (((AnnotationBooleanElem) elem).getValue()) {
            result.append(1);
          } else {
            result.append(0);
          }
        }
        result.append("\n");
        break;
      }
      case 'S': {
        result.append(".short_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(((AnnotationIntElem) elem).getValue());
        result.append("\n");
        break;
      }
      case 'B': {
        result.append(".byte_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(((AnnotationIntElem) elem).getValue());
        result.append("\n");
        break;
      }
      case 'C': {
        result.append(".char_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(((AnnotationIntElem) elem).getValue());
        result.append("\n");
        break;
      }
      case 'I': {
        result.append(".int_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(((AnnotationIntElem) elem).getValue());
        result.append("\n");
        break;
      }
      case 'J': {
        result.append(".long_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(((AnnotationLongElem) elem).getValue());
        result.append("\n");
        break;
      }
      case 'F': {
        result.append(".float_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(((AnnotationFloatElem) elem).getValue());
        result.append("\n");
        break;
      }
      case 'D': {
        result.append(".doub_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(((AnnotationDoubleElem) elem).getValue());
        result.append("\n");
        break;
      }
      case 's': {
        result.append(".str_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(soot.util.StringTools.getQuotedStringOf(((AnnotationStringElem) elem).getValue()));
        result.append("\n");
        break;
      }
      case 'e': {
        result.append(".enum_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(soot.util.StringTools.getQuotedStringOf(((AnnotationEnumElem) elem).getTypeName()));
        result.append(" ");
        result.append(soot.util.StringTools.getQuotedStringOf(((AnnotationEnumElem) elem).getConstantName()));
        result.append("\n");
        break;
      }
      case 'c': {
        result.append(".cls_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        result.append(soot.util.StringTools.getQuotedStringOf(((AnnotationClassElem) elem).getDesc()));
        result.append("\n");
        break;
      }
      case '[': {
        result.append(".arr_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\" ").toString());
        AnnotationArrayElem arrayElem = (AnnotationArrayElem) elem;
        result.append("\n");
        for (int i = 0; i < arrayElem.getNumValues(); i++) {
          // result.append("\n");
          result.append(getElemAttr(arrayElem.getValueAt(i)));
        }
        result.append(".end .arr_elem\n");
        break;
      }
      case '@': {
        result.append(".ann_kind ");
        result.append(new StringBuilder().append("\"").append(elem.getName()).append("\"\n").toString());
        AnnotationTag annot = ((AnnotationAnnotationElem) elem).getValue();
        result.append(".annotation ");
        result.append(soot.util.StringTools.getQuotedStringOf(annot.getType()) + "\n");
        annot.getElems().forEach(ae -> result.append(getElemAttr(ae)));
        result.append(".end .annotation\n");
        result.append(".end .annot_elem\n");
        break;
      }
      default: {
        throw new RuntimeException("Unknown Elem Attr Kind: " + elem.getKind());
      }
    }
    return result.toString();
  }

protected void assignColorsToLocals(Body body) {
    if (Options.v().verbose()) {
      logger.debug(new StringBuilder().append("[").append(body.getMethod().getName()).append("] Assigning colors to locals...").toString());
    }

    if (Options.v().time()) {
      Timers.v().packTimer.start();
    }

    localToGroup = new HashMap<>(body.getLocalCount() * 2 + 1, 0.7f);
    groupToColorCount = new HashMap<>(body.getLocalCount() * 2 + 1, 0.7f);
    localToColor = new HashMap<>(body.getLocalCount() * 2 + 1, 0.7f);

    // Assign each local to a group, and set that group's color count to 0.
    {
      Iterator<Local> localIt = body.getLocals().iterator();

      while (localIt.hasNext()) {
        Local l = localIt.next();
        Object g;

        if (sizeOfType(l.getType()) == 1) {
          g = IntType.v();
        } else {
          g = LongType.v();
        }

        localToGroup.put(l, g);

        groupToColorCount.putIfAbsent(g, Integer.valueOf(0));
      }
    }

    // Assign colors to the parameter locals.
    {
      Iterator<Unit> codeIt = body.getUnits().iterator();

      while (codeIt.hasNext()) {
        Stmt s = (Stmt) codeIt.next();

        if (s instanceof IdentityStmt && ((IdentityStmt) s).getLeftOp() instanceof Local) {
          Local l = (Local) ((IdentityStmt) s).getLeftOp();

          Object group = localToGroup.get(l);
          int count = groupToColorCount.get(group).intValue();

          localToColor.put(l, Integer.valueOf(count));

          count++;

          groupToColorCount.put(group, Integer.valueOf(count));
        }
      }
    }

  }

protected void emitMethod(SootMethod method) {
    if (method.isPhantom()) {
      return;
    }

    // Emit prologue
    emit(new StringBuilder().append(".method ").append(Modifier.toString(method.getModifiers())).append(" ").append(method.getName()).append(jasminDescriptorOf(method.makeRef())).toString());

    Iterator<SootClass> throwsIt = method.getExceptions().iterator();
    while (throwsIt.hasNext()) {
      SootClass exceptClass = throwsIt.next();
      emit(".throws " + exceptClass.getName());
    }
    if (method.hasTag("SyntheticTag") || Modifier.isSynthetic(method.getModifiers())) {
      emit(".synthetic");
    }
    if (method.hasTag("DeprecatedTag")) {
      emit(".deprecated");
    }
    if (method.hasTag("SignatureTag")) {
      String sigAttr = ".signature_attr ";
      SignatureTag sigTag = (SignatureTag) method.getTag("SignatureTag");
      sigAttr += new StringBuilder().append("\"").append(sigTag.getSignature()).append("\"").toString();
      emit(sigAttr);
    }
    if (method.hasTag("AnnotationDefaultTag")) {
      String annotDefAttr = ".annotation_default ";
      AnnotationDefaultTag annotDefTag = (AnnotationDefaultTag) method.getTag("AnnotationDefaultTag");
      annotDefAttr += getElemAttr(annotDefTag.getDefaultVal());
      annotDefAttr += ".end .annotation_default";
      emit(annotDefAttr);
    }
    Iterator<Tag> vit = method.getTags().iterator();
    while (vit.hasNext()) {
      Tag t = (Tag) vit.next();
      if ("VisibilityAnnotationTag".equals(t.getName())) {
        emit(getVisibilityAnnotationAttr((VisibilityAnnotationTag) t));
      }
      if ("VisibilityParameterAnnotationTag".equals(t.getName())) {
        emit(getVisibilityParameterAnnotationAttr((VisibilityParameterAnnotationTag) t));
      }
    }

    if (method.isConcrete()) {
      if (!method.hasActiveBody()) {
        throw new RuntimeException(new StringBuilder().append("method: ").append(method.getName()).append(" has no active body!").toString());
      } else {
        emitMethodBody(method);
      }
    }

    // Emit epilogue
    emit(".end method");

    Iterator<Tag> it = method.getTags().iterator();
    while (it.hasNext()) {
      Tag tag = (Tag) it.next();
      if (tag instanceof Attribute) {
        emit(new StringBuilder().append(".method_attribute ").append(tag.getName()).append(" \"").append(new String(Base64.encode(tag.getValue()))).append("\"").toString());
      }
    }
  }

protected abstract void emitMethodBody(SootMethod method);

public void print(PrintWriter out) {
    code.forEach(out::println);
  }

protected String doubleToString(DoubleConstant v) {
    String s = v.toString();

    if ("#Infinity".equals(s)) {
      s = "+DoubleInfinity";
    } else if ("#-Infinity".equals(s)) {
      s = "-DoubleInfinity";
    } else if ("#NaN".equals(s)) {
      s = "+DoubleNaN";
    }
    return s;
  }

protected String doubleToString(double d) {
    String doubleString = Double.toString(d);

    if ("NaN".equals(doubleString)) {
      return "+DoubleNaN";
    } else if ("Infinity".equals(doubleString)) {
      return "+DoubleInfinity";
    } else if ("-Infinity".equals(doubleString)) {
      return "-DoubleInfinity";
    }

    return doubleString;
  }

protected String floatToString(FloatConstant v) {
    String s = v.toString();

    if ("#InfinityF".equals(s)) {
      s = "+FloatInfinity";
    } else if ("#-InfinityF".equals(s)) {
      s = "-FloatInfinity";
    } else if ("#NaNF".equals(s)) {
      s = "+FloatNaN";
    }
    return s;
  }

protected String floatToString(float d) {
    String floatString = Float.toString(d);

    if ("NaN".equals(floatString)) {
      return "+FloatNaN";
    } else if ("Infinity".equals(floatString)) {
      return "+FloatInfinity";
    } else if ("-Infinity".equals(floatString)) {
      return "-FloatInfinity";
    }

    return floatString;
  }

}
