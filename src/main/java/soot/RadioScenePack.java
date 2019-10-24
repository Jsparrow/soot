package soot;

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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper object for a pack of optimizations. Provides chain-like operations, except that the key is the phase name.
 */
public class RadioScenePack extends ScenePack {
  private static final Logger logger = LoggerFactory.getLogger(RadioScenePack.class);

  public RadioScenePack(String name) {
    super(name);
  }

  @Override
protected void internalApply() {
    LinkedList<Transform> enableds = new LinkedList<>();

    for (Iterator<Transform> tIt = this.iterator(); tIt.hasNext();) {

      final Transform t = tIt.next();
      Map<String, String> opts = PhaseOptions.v().getPhaseOptions(t);
      if (!PhaseOptions.getBoolean(opts, "enabled")) {
        continue;
      }
      enableds.add(t);
    }
    if (enableds.size() == 0) {
      logger
          .debug(new StringBuilder().append("").append("Exactly one phase in the pack ").append(getPhaseName()).append(" must be enabled. Currently, none of them are.").toString());
      throw new CompilationDeathException(CompilationDeathException.COMPILATION_ABORTED);
    }
    if (enableds.size() > 1) {
      logger.debug(
          new StringBuilder().append("").append("Only one phase in the pack ").append(getPhaseName()).append(" may be enabled. The following are enabled currently: ").toString());
      enableds.forEach(t -> logger.debug(new StringBuilder().append("").append("  ").append(t.getPhaseName()).toString()));
      throw new CompilationDeathException(CompilationDeathException.COMPILATION_ABORTED);
    }
    enableds.forEach(Transform::apply);
  }

  @Override
public void add(Transform t) {
    super.add(t);
    checkEnabled(t);
  }

  @Override
public void insertAfter(Transform t, String phaseName) {
    super.insertAfter(t, phaseName);
    checkEnabled(t);
  }

  @Override
public void insertBefore(Transform t, String phaseName) {
    super.insertBefore(t, phaseName);
    checkEnabled(t);
  }

  private void checkEnabled(Transform t) {
    Map<String, String> options = PhaseOptions.v().getPhaseOptions(t);
    if (PhaseOptions.getBoolean(options, "enabled")) {
      // Enabling this one will disable all the others
      PhaseOptions.v().setPhaseOption(t, "enabled:true");
    }
  }
}
