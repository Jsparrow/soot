package soot.plugins.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2018 Bernhard J. Berger
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

/**
 * Class loading strategy that uses traditional reflection for instantiation.
 *
 * @author Bernhard J. Berger
 */
public class ReflectionClassLoadingStrategy implements ClassLoadingStrategy {

  private static final Logger logger = LoggerFactory.getLogger(ReflectionClassLoadingStrategy.class);

@Override
  public Object create(final String className) throws ClassNotFoundException, InstantiationException {
    final Class<?> clazz = Class.forName(className);

    try {
      return clazz.newInstance();
    } catch (final IllegalAccessException e) {
      logger.error(e.getMessage(), e);
	throw new InstantiationException(new StringBuilder().append("Failed to create instance of ").append(className).append(" due to access restrictions.").toString());
    }
  }
}
