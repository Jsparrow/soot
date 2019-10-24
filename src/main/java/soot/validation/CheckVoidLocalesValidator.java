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

import java.util.List;

import soot.Body;
import soot.Local;
import soot.VoidType;

public enum CheckVoidLocalesValidator implements BodyValidator {
  INSTANCE;

  public static CheckVoidLocalesValidator v() {
    return INSTANCE;
  }

  @Override
  public void validate(Body body, List<ValidationException> exception) {
    body.getLocals().stream().filter(l -> l.getType() instanceof VoidType).forEach(l -> exception.add(new ValidationException(l, new StringBuilder().append("Local ").append(l).append(" in ").append(body.getMethod()).append(" defined with void type").toString())));
  }

  @Override
  public boolean isBasicValidator() {
    return false;
  }
}
