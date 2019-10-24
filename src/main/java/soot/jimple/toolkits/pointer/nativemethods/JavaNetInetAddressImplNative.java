package soot.jimple.toolkits.pointer.nativemethods;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2003 Feng Qian
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

import soot.SootMethod;
import soot.jimple.toolkits.pointer.representations.Environment;
import soot.jimple.toolkits.pointer.representations.ReferenceVariable;
import soot.jimple.toolkits.pointer.util.NativeHelper;

public class JavaNetInetAddressImplNative extends NativeMethodClass {
  public JavaNetInetAddressImplNative(NativeHelper helper) {
    super(helper);
  }

  /**
   * Implements the abstract method simulateMethod. It distributes the request to the corresponding methods by signatures.
   */
  @Override
public void simulateMethod(SootMethod method, ReferenceVariable thisVar, ReferenceVariable returnVar,
      ReferenceVariable params[]) {

    String subSignature = method.getSubSignature();

    if ("java.lang.String getLocalHostName()".equals(subSignature)) {
      java_net_InetAddressImpl_getLocalHostName(method, thisVar, returnVar, params);
      return;

    } else if ("java.lang.String getHostByAddress(int)".equals(subSignature)) {
      java_net_InetAddressImpl_getHostByAddr(method, thisVar, returnVar, params);
      return;

    } else {
      defaultMethod(method, thisVar, returnVar, params);
      return;

    }
  }

  /************************ java.net.InetAddressImpl *****************/
  /**
   * Returns a variable pointing to a string constant
   *
   * I am not sure if repeated calls of methods in this class will return the same object or not. A conservative approach
   * would say YES, for definitely points-to, but NO for may points-to.
   *
   * We should avoid analyzing these unsafe native methods.
   *
   * native java.lang.String getLocalHostName() throws java.net.UnknownHostException;
   */
  public void java_net_InetAddressImpl_getLocalHostName(SootMethod method, ReferenceVariable thisVar,
      ReferenceVariable returnVar, ReferenceVariable params[]) {
    helper.assignObjectTo(returnVar, Environment.v().getStringObject());
  }

  /**
   * Create a string object
   *
   * native java.lang.String getHostByAddr(int) throws java.net.UnknownHostException;
   */
  public void java_net_InetAddressImpl_getHostByAddr(SootMethod method, ReferenceVariable thisVar,
      ReferenceVariable returnVar, ReferenceVariable params[]) {
    helper.assignObjectTo(returnVar, Environment.v().getStringObject());
  }

  /**
   * NO side effects. native void makeAnyLocalAddress(java.net.InetAddress); native byte
   * lookupAllHostAddr(java.lang.String)[][] throws java.net.UnknownHostException; native int getInetFamily();
   *
   * @see default(...)
   */

}
