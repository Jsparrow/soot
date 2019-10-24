package soot.jimple.spark.pag;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2003 Ondrej Lhotak
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import soot.ArrayType;
import soot.Body;
import soot.Context;
import soot.EntryPoints;
import soot.G;
import soot.RefLikeType;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.VoidType;
import soot.jimple.Stmt;
import soot.jimple.spark.builder.MethodNodeFactory;
import soot.jimple.spark.internal.SparkLibraryHelper;
import soot.options.CGOptions;
import soot.util.NumberedString;
import soot.util.queue.ChunkedQueue;
import soot.util.queue.QueueReader;

/**
 * Part of a pointer assignment graph for a single method.
 *
 * @author Ondrej Lhotak
 */
public final class MethodPAG {
  private static final String mainSubSignature = SootMethod.getSubSignature("main",
	      Collections.<Type>singletonList(ArrayType.v(RefType.v("java.lang.String"), 1)), VoidType.v());
	private PAG pag;
	private Set<Context> addedContexts;
	private final ChunkedQueue<Node> internalEdges = new ChunkedQueue<>();
	private final ChunkedQueue<Node> inEdges = new ChunkedQueue<>();
	private final ChunkedQueue<Node> outEdges = new ChunkedQueue<>();
	private final QueueReader<Node> internalReader = internalEdges.reader();
	private final QueueReader<Node> inReader = inEdges.reader();
	private final QueueReader<Node> outReader = outEdges.reader();
	SootMethod method;
	protected MethodNodeFactory nodeFactory;
	protected boolean hasBeenAdded = false;
	protected boolean hasBeenBuilt = false;
	protected final NumberedString sigCanonicalize
	      = Scene.v().getSubSigNumberer().findOrAdd("java.lang.String canonicalize(java.lang.String)");

	protected MethodPAG(PAG pag, SootMethod m) {
	    this.pag = pag;
	    this.method = m;
	    this.nodeFactory = new MethodNodeFactory(pag, this);
	  }

	public PAG pag() {
	    return pag;
	  }

	/**
	   * Adds this method to the main PAG, with all VarNodes parameterized by varNodeParameter.
	   */
	  public void addToPAG(Context varNodeParameter) {
	    if (!hasBeenBuilt) {
	      throw new RuntimeException(String.format("No PAG built for context %s and method %s", varNodeParameter, method));
	    }
	    if (varNodeParameter == null) {
	      if (hasBeenAdded) {
	        return;
	      }
	      hasBeenAdded = true;
	    } else {
	      if (addedContexts == null) {
	        addedContexts = new HashSet<>();
	      }
	      if (!addedContexts.add(varNodeParameter)) {
	        return;
	      }
	    }
	    QueueReader<Node> reader = internalReader.clone();
	    while (reader.hasNext()) {
	      Node src = reader.next();
	      src = parameterize(src, varNodeParameter);
	      Node dst = reader.next();
	      dst = parameterize(dst, varNodeParameter);
	      pag.addEdge(src, dst);
	    }
	    reader = inReader.clone();
	    while (reader.hasNext()) {
	      Node src = reader.next();
	      Node dst = reader.next();
	      dst = parameterize(dst, varNodeParameter);
	      pag.addEdge(src, dst);
	    }
	    reader = outReader.clone();
	    while (reader.hasNext()) {
	      Node src = reader.next();
	      src = parameterize(src, varNodeParameter);
	      Node dst = reader.next();
	      pag.addEdge(src, dst);
	    }
	  }

	public void addInternalEdge(Node src, Node dst) {
	    if (src == null) {
	      return;
	    }
	    internalEdges.add(src);
	    internalEdges.add(dst);
	    if (hasBeenAdded) {
	      pag.addEdge(src, dst);
	    }
	  }

	public void addInEdge(Node src, Node dst) {
	    if (src == null) {
	      return;
	    }
	    inEdges.add(src);
	    inEdges.add(dst);
	    if (hasBeenAdded) {
	      pag.addEdge(src, dst);
	    }
	  }

	public void addOutEdge(Node src, Node dst) {
	    if (src == null) {
	      return;
	    }
	    outEdges.add(src);
	    outEdges.add(dst);
	    if (hasBeenAdded) {
	      pag.addEdge(src, dst);
	    }
	  }

	public SootMethod getMethod() {
	    return method;
	  }

	public MethodNodeFactory nodeFactory() {
	    return nodeFactory;
	  }

	public static MethodPAG v(PAG pag, SootMethod m) {
	    MethodPAG ret = G.v().MethodPAG_methodToPag.get(m);
	    if (ret == null) {
	      ret = new MethodPAG(pag, m);
	      G.v().MethodPAG_methodToPag.put(m, ret);
	    }
	    return ret;
	  }

	public void build() {
	    if (hasBeenBuilt) {
	      return;
	    }
	    hasBeenBuilt = true;
	    if (method.isNative()) {
	      if (pag().getOpts().simulate_natives()) {
	        buildNative();
	      }
	    } else {
	      if (method.isConcrete() && !method.isPhantom()) {
	        buildNormal();
	      }
	    }
	    addMiscEdges();
	  }

	protected VarNode parameterize(LocalVarNode vn, Context varNodeParameter) {
	    SootMethod m = vn.getMethod();
	    if (m != method && m != null) {
	      throw new RuntimeException(new StringBuilder().append("VarNode ").append(vn).append(" with method ").append(m).append(" parameterized in method ").append(method).toString());
	    }
	    // System.out.println( "parameterizing "+vn+" with "+varNodeParameter );
	    return pag().makeContextVarNode(vn, varNodeParameter);
	  }

	protected FieldRefNode parameterize(FieldRefNode frn, Context varNodeParameter) {
	    return pag().makeFieldRefNode((VarNode) parameterize(frn.getBase(), varNodeParameter), frn.getField());
	  }

	public Node parameterize(Node n, Context varNodeParameter) {
	    if (varNodeParameter == null) {
	      return n;
	    }
	    if (n instanceof LocalVarNode) {
	      return parameterize((LocalVarNode) n, varNodeParameter);
	    }
	    if (n instanceof FieldRefNode) {
	      return parameterize((FieldRefNode) n, varNodeParameter);
	    }
	    return n;
	  }

	protected void buildNormal() {
	    Body b = method.retrieveActiveBody();
	    b.getUnits().forEach(u -> nodeFactory.handleStmt((Stmt) u));
	  }

	protected void buildNative() {
	    ValNode thisNode = null;
	    ValNode retNode = null;
	    if (!method.isStatic()) {
	      thisNode = (ValNode) nodeFactory.caseThis();
	    }
	    if (method.getReturnType() instanceof RefLikeType) {
	      retNode = (ValNode) nodeFactory.caseRet();
	
	      // on library analysis we assume that the return type of an native method can
	      // be anything matching to the declared type.
	      if (pag.getCGOpts().library() != CGOptions.library_disabled) {
	        Type retType = method.getReturnType();
	
	        retType.apply(new SparkLibraryHelper(pag, retNode, method));
	      }
	    }
	    ValNode[] args = new ValNode[method.getParameterCount()];
	    for (int i = 0; i < method.getParameterCount(); i++) {
	      if (!(method.getParameterType(i) instanceof RefLikeType)) {
	        continue;
	      }
	      args[i] = (ValNode) nodeFactory.caseParm(i);
	    }
	    pag.nativeMethodDriver.process(method, thisNode, retNode, args);
	  }

	protected void addMiscEdges() {
	    // Add node for parameter (String[]) in main method
	    final String signature = method.getSignature();
	    if (method.getSubSignature().equals(mainSubSignature)) {
	      addInEdge(pag().nodeFactory().caseArgv(), nodeFactory.caseParm(0));
	    } else if ("<java.lang.Thread: void <init>(java.lang.ThreadGroup,java.lang.String)>".equals(signature)) {
	      addInEdge(pag().nodeFactory().caseMainThread(), nodeFactory.caseThis());
	      addInEdge(pag().nodeFactory().caseMainThreadGroup(), nodeFactory.caseParm(0));
	    } else if ("<java.lang.ref.Finalizer: void <init>(java.lang.Object)>".equals(signature)) {
	      addInEdge(nodeFactory.caseThis(), pag().nodeFactory().caseFinalizeQueue());
	    } else if ("<java.lang.ref.Finalizer: void runFinalizer()>".equals(signature)) {
	      addInEdge(pag.nodeFactory().caseFinalizeQueue(), nodeFactory.caseThis());
	    } else if ("<java.lang.ref.Finalizer: void access$100(java.lang.Object)>".equals(signature)) {
	      addInEdge(pag.nodeFactory().caseFinalizeQueue(), nodeFactory.caseParm(0));
	    } else if ("<java.lang.ClassLoader: void <init>()>".equals(signature)) {
	      addInEdge(pag.nodeFactory().caseDefaultClassLoader(), nodeFactory.caseThis());
	    } else if ("<java.lang.Thread: void exit()>".equals(signature)) {
	      addInEdge(pag.nodeFactory().caseMainThread(), nodeFactory.caseThis());
	    } else if ("<java.security.PrivilegedActionException: void <init>(java.lang.Exception)>".equals(signature)) {
	      addInEdge(pag.nodeFactory().caseThrow(), nodeFactory.caseParm(0));
	      addInEdge(pag.nodeFactory().casePrivilegedActionException(), nodeFactory.caseThis());
	    }
	
	    if (method.getNumberedSubSignature().equals(sigCanonicalize)) {
	      SootClass cl = method.getDeclaringClass();
	      while (cl != null) {
	        if (cl.equals(Scene.v().getSootClass("java.io.FileSystem"))) {
	          addInEdge(pag.nodeFactory().caseCanonicalPath(), nodeFactory.caseRet());
	        }
	        cl = cl.getSuperclassUnsafe();
	      }
	    }
	
	    boolean isImplicit = false;
	    for (SootMethod implicitMethod : EntryPoints.v().implicit()) {
	      if (implicitMethod.getNumberedSubSignature().equals(method.getNumberedSubSignature())) {
	        isImplicit = true;
	        break;
	      }
	    }
	    if (!isImplicit) {
			return;
		}
		SootClass c = method.getDeclaringClass();
		outer: do {
	        while (!"java.lang.ClassLoader".equals(c.getName())) {
	          if (!c.hasSuperclass()) {
	            break outer;
	          }
	          c = c.getSuperclass();
	        }
	        if ("<init>".equals(method.getName())) {
	          continue;
	        }
	        addInEdge(pag().nodeFactory().caseDefaultClassLoader(), nodeFactory.caseThis());
	        addInEdge(pag().nodeFactory().caseMainClassNameString(), nodeFactory.caseParm(0));
	      } while (false);
	  }
}
