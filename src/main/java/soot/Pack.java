package soot;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 1999 Raja Vallee-Rai and Patrick Lam
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
import java.util.Map;

import soot.util.Chain;
import soot.util.HashChain;

/**
 * A wrapper object for a pack of optimizations. Provides chain-like operations, except that the key is the phase name.
 */
public abstract class Pack implements HasPhaseOptions, Iterable<Transform> {
  private String name;
Chain<Transform> opts = new HashChain<>();

public Pack(String name) {
    this.name = name;
  }

@Override
public String getPhaseName() {
    return name;
  }

@Override
public Iterator<Transform> iterator() {
    return opts.iterator();
  }

public void add(Transform t) {
    if (!t.getPhaseName().startsWith(getPhaseName() + ".")) {
      throw new RuntimeException(new StringBuilder().append("Transforms in pack '").append(getPhaseName()).append("' must have a phase name ").append("that starts with '").append(getPhaseName()).append(".'.").toString());
    }
    PhaseOptions.v().getPM().notifyAddPack();
    if (get(t.getPhaseName()) != null) {
      throw new RuntimeException(new StringBuilder().append("Phase ").append(t.getPhaseName()).append(" already ").append("in pack").toString());
    }
    opts.add(t);
  }

public void insertAfter(Transform t, String phaseName) {
    PhaseOptions.v().getPM().notifyAddPack();
    for (Transform tr : opts) {
      if (tr.getPhaseName().equals(phaseName)) {
        opts.insertAfter(t, tr);
        return;
      }
    }
    throw new RuntimeException(new StringBuilder().append("phase ").append(phaseName).append(" not found!").toString());
  }

public void insertBefore(Transform t, String phaseName) {
    PhaseOptions.v().getPM().notifyAddPack();
    for (Transform tr : opts) {
      if (tr.getPhaseName().equals(phaseName)) {
        opts.insertBefore(t, tr);
        return;
      }
    }
    throw new RuntimeException(new StringBuilder().append("phase ").append(phaseName).append(" not found!").toString());
  }

public Transform get(String phaseName) {
    return opts.stream().filter(tr -> tr.getPhaseName().equals(phaseName)).findFirst().orElse(null);
  }

public boolean remove(String phaseName) {
    for (Transform tr : opts) {
      if (tr.getPhaseName().equals(phaseName)) {
        opts.remove(tr);
        return true;
      }
    }
    return false;
  }

protected void internalApply() {
    throw new RuntimeException("wrong type of pack");
  }

protected void internalApply(Body b) {
    throw new RuntimeException("wrong type of pack");
  }

public final void apply() {
    Map<String, String> options = PhaseOptions.v().getPhaseOptions(this);
    if (!PhaseOptions.getBoolean(options, "enabled")) {
      return;
    }
    internalApply();
  }

public final void apply(Body b) {
    Map<String, String> options = PhaseOptions.v().getPhaseOptions(this);
    if (!PhaseOptions.getBoolean(options, "enabled")) {
      return;
    }
    internalApply(b);
  }

@Override
public String getDeclaredOptions() {
    return soot.options.Options.getDeclaredOptionsForPhase(getPhaseName());
  }

@Override
public String getDefaultOptions() {
    return soot.options.Options.getDefaultOptionsForPhase(getPhaseName());
  }
}
