package soot.jimple.toolkits.ide.exampleproblems;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2013 Eric Bodden and others
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

import heros.DefaultSeeds;
import heros.FlowFunction;
import heros.FlowFunctions;
import heros.InterproceduralCFG;
import heros.flowfunc.Identity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import soot.Local;
import soot.NullType;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.jimple.InvokeExpr;
import soot.jimple.ReturnStmt;
import soot.jimple.Stmt;
import soot.jimple.internal.JimpleLocal;
import soot.jimple.toolkits.ide.DefaultJimpleIFDSTabulationProblem;

public class IFDSLiveVariables extends DefaultJimpleIFDSTabulationProblem<Value, InterproceduralCFG<Unit, SootMethod>> {
  public IFDSLiveVariables(InterproceduralCFG<Unit, SootMethod> icfg) {
    super(icfg);
  }

  @Override
  public FlowFunctions<Unit, Value, SootMethod> createFlowFunctionsFactory() {
    return new FlowFunctions<Unit, Value, SootMethod>() {

      @Override
      public FlowFunction<Value> getNormalFlowFunction(Unit curr, Unit succ) {
        if (curr.getUseAndDefBoxes().isEmpty()) {
          return Identity.v();
        }

        final Stmt s = (Stmt) curr;

        return new FlowFunction<Value>() {
          @Override
		public Set<Value> computeTargets(Value source) {
            // kill defs
            List<ValueBox> defs = s.getDefBoxes();
            boolean condition = !defs.isEmpty() && defs.get(0).getValue().equivTo(source);
			if (condition) {
                return Collections.emptySet();
              }

            // gen uses out of zero value
			if (!source.equals(zeroValue())) {
				// else just propagate
				return Collections.singleton(source);
			}
			Set<Value> liveVars = new HashSet<>();
			s.getUseBoxes().stream().map(ValueBox::getValue).forEach(liveVars::add);
			return liveVars;
          }
        };
      }

      @Override
      public FlowFunction<Value> getCallFlowFunction(Unit callStmt, final SootMethod destinationMethod) {
        final Stmt s = (Stmt) callStmt;
        return new FlowFunction<Value>() {
          @Override
		public Set<Value> computeTargets(Value source) {
            if (!s.getDefBoxes().isEmpty()) {
              Value callerSideReturnValue = s.getDefBoxes().get(0).getValue();
              if (callerSideReturnValue.equivTo(source)) {
                Set<Value> calleeSideReturnValues = new HashSet<>();
                interproceduralCFG().getStartPointsOf(destinationMethod).stream().filter(calleeUnit -> calleeUnit instanceof ReturnStmt).map(calleeUnit -> (ReturnStmt) calleeUnit).forEach(returnStmt -> calleeSideReturnValues.add(returnStmt.getOp()));
                return calleeSideReturnValues;
              }
            }

            // no return value, nothing to propagate
            return Collections.emptySet();
          }
        };
      }

      @Override
      public FlowFunction<Value> getReturnFlowFunction(final Unit callSite, SootMethod calleeMethod, final Unit exitStmt,
          Unit returnSite) {
        Stmt s = (Stmt) callSite;
        InvokeExpr ie = s.getInvokeExpr();
        final List<Value> callArgs = ie.getArgs();
        final List<Local> paramLocals = new ArrayList<>();
        for (int i = 0; i < calleeMethod.getParameterCount(); i++) {
          paramLocals.add(calleeMethod.getActiveBody().getParameterLocal(i));
        }
        return new FlowFunction<Value>() {
          @Override
		public Set<Value> computeTargets(Value source) {
            Set<Value> liveParamsAtCallee = new HashSet<>();
            for (int i = 0; i < paramLocals.size(); i++) {
              if (paramLocals.get(i).equivTo(source)) {
                liveParamsAtCallee.add(callArgs.get(i));
              }
            }
            return liveParamsAtCallee;
          }
        };
      }

      @Override
      public FlowFunction<Value> getCallToReturnFlowFunction(Unit callSite, Unit returnSite) {
        if (callSite.getUseAndDefBoxes().isEmpty()) {
          return Identity.v();
        }

        final Stmt s = (Stmt) callSite;

        return new FlowFunction<Value>() {
          @Override
		public Set<Value> computeTargets(Value source) {
            // kill defs
            List<ValueBox> defs = s.getDefBoxes();
            boolean condition = !defs.isEmpty() && defs.get(0).getValue().equivTo(source);
			if (condition) {
                return Collections.emptySet();
              }

            // gen uses out of zero value
			if (!source.equals(zeroValue())) {
				// else just propagate
				return Collections.singleton(source);
			}
			Set<Value> liveVars = new HashSet<>();
			// only "gen" those values that are not parameter values;
              // the latter are taken care of by the return-flow function
              List<Value> args = s.getInvokeExpr().getArgs();
			s.getUseBoxes().stream().map(ValueBox::getValue).forEach(value -> {
				if (!args.contains(value)) {
                  liveVars.add(value);
                }
			});
			return liveVars;
          }
        };
      }
    };
  }

  @Override
public Map<Unit, Set<Value>> initialSeeds() {
    return DefaultSeeds.make(interproceduralCFG().getStartPointsOf(Scene.v().getMainMethod()), zeroValue());
  }

  @Override
public Value createZeroValue() {
    return new JimpleLocal("<<zero>>", NullType.v());
  }

}
