/* This file was generated by SableCC (http://www.sablecc.org/). */

package soot.jimple.parser.analysis;

import java.util.*;
import soot.jimple.parser.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    @Override
    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    @Override
    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    @Override
    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    @Override
    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    @Override
    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFile(AFile node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAbstractModifier(AAbstractModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFinalModifier(AFinalModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANativeModifier(ANativeModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPublicModifier(APublicModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAProtectedModifier(AProtectedModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPrivateModifier(APrivateModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStaticModifier(AStaticModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASynchronizedModifier(ASynchronizedModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATransientModifier(ATransientModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVolatileModifier(AVolatileModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStrictfpModifier(AStrictfpModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEnumModifier(AEnumModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAnnotationModifier(AAnnotationModifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClassFileType(AClassFileType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAInterfaceFileType(AInterfaceFileType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAExtendsClause(AExtendsClause node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAImplementsClause(AImplementsClause node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFileBody(AFileBody node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASingleNameList(ASingleNameList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultiNameList(AMultiNameList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClassNameSingleClassNameList(AClassNameSingleClassNameList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClassNameMultiClassNameList(AClassNameMultiClassNameList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFieldMember(AFieldMember node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMethodMember(AMethodMember node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVoidType(AVoidType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANovoidType(ANovoidType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASingleParameterList(ASingleParameterList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultiParameterList(AMultiParameterList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAParameter(AParameter node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAThrowsClause(AThrowsClause node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABooleanBaseTypeNoName(ABooleanBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAByteBaseTypeNoName(AByteBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACharBaseTypeNoName(ACharBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAShortBaseTypeNoName(AShortBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntBaseTypeNoName(AIntBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALongBaseTypeNoName(ALongBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFloatBaseTypeNoName(AFloatBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADoubleBaseTypeNoName(ADoubleBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANullBaseTypeNoName(ANullBaseTypeNoName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABooleanBaseType(ABooleanBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAByteBaseType(AByteBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACharBaseType(ACharBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAShortBaseType(AShortBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntBaseType(AIntBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALongBaseType(ALongBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFloatBaseType(AFloatBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADoubleBaseType(ADoubleBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANullBaseType(ANullBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClassNameBaseType(AClassNameBaseType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABaseNonvoidType(ABaseNonvoidType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAQuotedNonvoidType(AQuotedNonvoidType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentNonvoidType(AIdentNonvoidType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFullIdentNonvoidType(AFullIdentNonvoidType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArrayBrackets(AArrayBrackets node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEmptyMethodBody(AEmptyMethodBody node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFullMethodBody(AFullMethodBody node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADeclaration(ADeclaration node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUnknownJimpleType(AUnknownJimpleType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANonvoidJimpleType(ANonvoidJimpleType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALocalName(ALocalName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASingleLocalNameList(ASingleLocalNameList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultiLocalNameList(AMultiLocalNameList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALabelStatement(ALabelStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABreakpointStatement(ABreakpointStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEntermonitorStatement(AEntermonitorStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAExitmonitorStatement(AExitmonitorStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATableswitchStatement(ATableswitchStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALookupswitchStatement(ALookupswitchStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentityStatement(AIdentityStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentityNoTypeStatement(AIdentityNoTypeStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAssignStatement(AAssignStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIfStatement(AIfStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGotoStatement(AGotoStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANopStatement(ANopStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseARetStatement(ARetStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAReturnStatement(AReturnStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAThrowStatement(AThrowStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAInvokeStatement(AInvokeStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALabelName(ALabelName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACaseStmt(ACaseStmt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAConstantCaseLabel(AConstantCaseLabel node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADefaultCaseLabel(ADefaultCaseLabel node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGotoStmt(AGotoStmt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACatchClause(ACatchClause node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANewExpression(ANewExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACastExpression(ACastExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAInstanceofExpression(AInstanceofExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAInvokeExpression(AInvokeExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAReferenceExpression(AReferenceExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABinopExpression(ABinopExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUnopExpression(AUnopExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAImmediateExpression(AImmediateExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASimpleNewExpr(ASimpleNewExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArrayNewExpr(AArrayNewExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultiNewExpr(AMultiNewExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArrayDescriptor(AArrayDescriptor node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAReferenceVariable(AReferenceVariable node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALocalVariable(ALocalVariable node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABinopBoolExpr(ABinopBoolExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUnopBoolExpr(AUnopBoolExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANonstaticInvokeExpr(ANonstaticInvokeExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStaticInvokeExpr(AStaticInvokeExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADynamicInvokeExpr(ADynamicInvokeExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABinopExpr(ABinopExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUnopExpr(AUnopExpr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASpecialNonstaticInvoke(ASpecialNonstaticInvoke node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVirtualNonstaticInvoke(AVirtualNonstaticInvoke node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAInterfaceNonstaticInvoke(AInterfaceNonstaticInvoke node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUnnamedMethodSignature(AUnnamedMethodSignature node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMethodSignature(AMethodSignature node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArrayReference(AArrayReference node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFieldReference(AFieldReference node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentArrayRef(AIdentArrayRef node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAQuotedArrayRef(AQuotedArrayRef node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALocalFieldRef(ALocalFieldRef node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASigFieldRef(ASigFieldRef node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFieldSignature(AFieldSignature node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFixedArrayDescriptor(AFixedArrayDescriptor node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASingleArgList(ASingleArgList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultiArgList(AMultiArgList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALocalImmediate(ALocalImmediate node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAConstantImmediate(AConstantImmediate node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntegerConstant(AIntegerConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFloatConstant(AFloatConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringConstant(AStringConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClzzConstant(AClzzConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANullConstant(ANullConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAndBinop(AAndBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOrBinop(AOrBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAXorBinop(AXorBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAModBinop(AModBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmpBinop(ACmpBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmpgBinop(ACmpgBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmplBinop(ACmplBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmpeqBinop(ACmpeqBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmpneBinop(ACmpneBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmpgtBinop(ACmpgtBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmpgeBinop(ACmpgeBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmpltBinop(ACmpltBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACmpleBinop(ACmpleBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAShlBinop(AShlBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAShrBinop(AShrBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAUshrBinop(AUshrBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPlusBinop(APlusBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMinusBinop(AMinusBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultBinop(AMultBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADivBinop(ADivBinop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALengthofUnop(ALengthofUnop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANegUnop(ANegUnop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAQuotedClassName(AQuotedClassName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentClassName(AIdentClassName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFullIdentClassName(AFullIdentClassName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAQuotedName(AQuotedName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentName(AIdentName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIgnored(TIgnored node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAbstract(TAbstract node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFinal(TFinal node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNative(TNative node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPublic(TPublic node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTProtected(TProtected node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPrivate(TPrivate node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStatic(TStatic node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSynchronized(TSynchronized node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTransient(TTransient node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTVolatile(TVolatile node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStrictfp(TStrictfp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEnum(TEnum node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAnnotation(TAnnotation node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTClass(TClass node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInterface(TInterface node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTVoid(TVoid node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBoolean(TBoolean node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTByte(TByte node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTShort(TShort node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTChar(TChar node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInt(TInt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLong(TLong node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFloat(TFloat node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDouble(TDouble node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNullType(TNullType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTUnknown(TUnknown node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTExtends(TExtends node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTImplements(TImplements node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBreakpoint(TBreakpoint node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCase(TCase node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCatch(TCatch node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmp(TCmp node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmpg(TCmpg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmpl(TCmpl node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDefault(TDefault node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEntermonitor(TEntermonitor node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTExitmonitor(TExitmonitor node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGoto(TGoto node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIf(TIf node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInstanceof(TInstanceof node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInterfaceinvoke(TInterfaceinvoke node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLengthof(TLengthof node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLookupswitch(TLookupswitch node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNeg(TNeg node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNew(TNew node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNewarray(TNewarray node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNewmultiarray(TNewmultiarray node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNop(TNop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRet(TRet node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTReturn(TReturn node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSpecialinvoke(TSpecialinvoke node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStaticinvoke(TStaticinvoke node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDynamicinvoke(TDynamicinvoke node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTableswitch(TTableswitch node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTThrow(TThrow node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTThrows(TThrows node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTVirtualinvoke(TVirtualinvoke node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNull(TNull node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFrom(TFrom node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTo(TTo node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTWith(TWith node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCls(TCls node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLBrace(TLBrace node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRBrace(TRBrace node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSemicolon(TSemicolon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLBracket(TLBracket node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRBracket(TRBracket node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLParen(TLParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRParen(TRParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTColon(TColon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDot(TDot node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTQuote(TQuote node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTColonEquals(TColonEquals node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEquals(TEquals node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAnd(TAnd node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTOr(TOr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTXor(TXor node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMod(TMod node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmpeq(TCmpeq node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmpne(TCmpne node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmpgt(TCmpgt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmpge(TCmpge node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmplt(TCmplt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTCmple(TCmple node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTShl(TShl node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTShr(TShr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTUshr(TUshr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPlus(TPlus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMinus(TMinus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMult(TMult node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDiv(TDiv node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTQuotedName(TQuotedName node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFullIdentifier(TFullIdentifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIdentifier(TIdentifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAtIdentifier(TAtIdentifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBoolConstant(TBoolConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIntegerConstant(TIntegerConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFloatConstant(TFloatConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStringConstant(TStringConstant node)
    {
        defaultCase(node);
    }

    @Override
    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    public void defaultCase(@SuppressWarnings("unused") Node node)
    {
        // do nothing
    }
}
