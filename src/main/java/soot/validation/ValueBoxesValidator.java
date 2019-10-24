package soot.validation;

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

import static java.util.Collections.newSetFromMap;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import soot.Body;
import soot.Unit;
import soot.ValueBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ValueBoxesValidator implements BodyValidator {
  INSTANCE;

  private static final Logger logger = LoggerFactory.getLogger(ValueBoxesValidator.class);

public static ValueBoxesValidator v() {
    return INSTANCE;
  }

  @Override
  /** Verifies that a ValueBox is not used in more than one place. */
  public void validate(Body body, List<ValidationException> exception) {
    Set<ValueBox> set = newSetFromMap(new IdentityHashMap<>());

    for (ValueBox vb : body.getUseAndDefBoxes()) {
      if (set.add(vb)) {
        continue;
      }

      exception.add(new ValidationException(vb, new StringBuilder().append("Aliased value box : ").append(vb).append(" in ").append(body.getMethod()).toString()));

      body.getUnits().forEach(u -> logger.error(String.valueOf(u)));
    }
  }

  @Override
  public boolean isBasicValidator() {
    return false;
  }
}
