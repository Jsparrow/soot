package soot.jimple.validation;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 1999 Raja Vallee-Rai
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.Body;
import soot.Trap;
import soot.Unit;
import soot.jimple.CaughtExceptionRef;
import soot.jimple.IdentityStmt;
import soot.validation.BodyValidator;
import soot.validation.ValidationException;

/**
 * This validator checks whether the jimple traps are correct. It does not perform the same checks as
 * {@link soot.validation.TrapsValidator}
 *
 * @see JimpleTrapValidator#validate(Body, List)
 * @author Marc Miltenberger
 */
public enum JimpleTrapValidator implements BodyValidator {
  INSTANCE;

  public static JimpleTrapValidator v() {
    return INSTANCE;
  }

  /**
   * Checks whether all Caught-Exception-References are associated to traps.
   */
  @Override
  public void validate(Body body, List<ValidationException> exceptions) {
    Set<Unit> caughtUnits = new HashSet<>();
    body.getTraps().forEach(trap -> {
      caughtUnits.add(trap.getHandlerUnit());

      if (!(trap.getHandlerUnit() instanceof IdentityStmt)) {
        exceptions.add(new ValidationException(trap, "Trap handler does not start with caught " + "exception reference"));
      } else {
        IdentityStmt is = (IdentityStmt) trap.getHandlerUnit();
        if (!(is.getRightOp() instanceof CaughtExceptionRef)) {
          exceptions.add(new ValidationException(trap, "Trap handler does not start with caught " + "exception reference"));
        }
      }
    });
    body.getUnits().stream().filter(u -> u instanceof IdentityStmt).map(u -> (IdentityStmt) u).forEach(id -> {
		boolean condition = id.getRightOp() instanceof CaughtExceptionRef && !caughtUnits.contains(id);
		if (condition) {
            exceptions.add(new ValidationException(id, "Could not find a corresponding trap using this statement as handler",
                new StringBuilder().append("Body of method ").append(body.getMethod().getSignature()).append(" contains a caught exception reference,").append("but not a corresponding trap using this statement as handler").toString()));
          }
	});
  }

  @Override
  public boolean isBasicValidator() {
    return true;
  }
}
