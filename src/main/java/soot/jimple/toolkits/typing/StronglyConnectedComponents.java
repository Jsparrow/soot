package soot.jimple.toolkits.typing;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2000 Etienne Gagnon.  All rights reserved.
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
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class StronglyConnectedComponents {
  private static final Logger logger = LoggerFactory.getLogger(StronglyConnectedComponents.class);
private static final boolean DEBUG = false;
List<TypeVariable> variables;
Set<TypeVariable> black;
List<TypeVariable> finished;
List<List<TypeVariable>> forest = new LinkedList<>();
List<TypeVariable> current_tree;

private StronglyConnectedComponents(List<TypeVariable> typeVariableList) throws TypeException {
    variables = typeVariableList;

    black = new TreeSet<>();
    finished = new LinkedList<>();

    variables.stream().filter(var -> !black.add(var)).forEach(this::dfsg_visit);

    black = new TreeSet<>();

    finished.stream().filter(var -> !black.add(var)).forEach(var -> {
        current_tree = new LinkedList<>();
        forest.add(current_tree);
        dfsgt_visit(var);
      });

    for (Iterator<List<TypeVariable>> i = forest.iterator(); i.hasNext();) {
      List<TypeVariable> list = i.next();
      TypeVariable previous = null;
      StringBuilder s = null;
      if (DEBUG) {
        s = new StringBuilder("scc:\n");
      }

      for (TypeVariable current : list) {
        if (DEBUG) {
          s.append(new StringBuilder().append(" ").append(current).append("\n").toString());
        }

        if (previous == null) {
          previous = current;
        } else {
          try {
            previous = previous.union(current);
          } catch (TypeException e) {
            if (DEBUG) {
              logger.debug("" + s);
            }
            throw e;
          }
        }
      }
    }
  }

public static void merge(List<TypeVariable> typeVariableList) throws TypeException {
    new StronglyConnectedComponents(typeVariableList);
  }

private void dfsg_visit(TypeVariable var) {
    List<TypeVariable> parents = var.parents();

    parents.stream().filter(parent -> !black.add(parent)).forEach(this::dfsg_visit);

    finished.add(0, var);
  }

private void dfsgt_visit(TypeVariable var) {
    current_tree.add(var);

    List<TypeVariable> children = var.children();

    children.stream().filter(child -> !black.add(child)).forEach(this::dfsgt_visit);
  }
}
