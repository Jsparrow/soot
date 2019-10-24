package soot.jimple.paddle;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2004 - 2005 Ondrej Lhotak
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

import java.util.Map;

import soot.G;
import soot.SceneTransformer;
import soot.Singletons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads the Paddle plugin.
 *
 * @author Ondrej Lhotak
 */
public class PaddleHook extends SceneTransformer {
  private static final Logger logger = LoggerFactory.getLogger(PaddleHook.class);
private IPaddleTransformer paddleTransformer;
private Object paddleG;

public PaddleHook(Singletons.Global g) {
  }

public static PaddleHook v() {
    return G.v().soot_jimple_paddle_PaddleHook();
  }

public IPaddleTransformer paddleTransformer() {
    if (paddleTransformer == null) {
      paddleTransformer = (IPaddleTransformer) instantiate("soot.jimple.paddle.PaddleTransformer");
    }
    return paddleTransformer;
  }

@Override
protected void internalTransform(String phaseName, Map<String, String> options) {
    paddleTransformer().transform(phaseName, options);
  }

public Object instantiate(String className) {
    Object ret;
    try {
      ret = Class.forName(className).newInstance();
    } catch (ClassNotFoundException e) {
      logger.error(e.getMessage(), e);
	throw new RuntimeException(new StringBuilder().append("Could not find ").append(className).append(". Did you include Paddle on your Java classpath?").toString());
    } catch (IllegalAccessException | InstantiationException e) {
      throw new RuntimeException(new StringBuilder().append("Could not instantiate ").append(className).append(": ").append(e).toString());
    }
    return ret;
  }

public Object paddleG() {
    if (paddleG == null) {
      paddleG = instantiate("soot.PaddleG");
    }
    return paddleG;
  }

/**
   * This is called when Soot finishes executing all interprocedural phases. Paddle uses it to stop profiling if profiling is
   * enabled.
   */
  public void finishPhases() {
    if (paddleTransformer != null) {
      paddleTransformer().finishPhases();
    }
  }
}
