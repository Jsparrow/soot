package soot.jbco.util;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import soot.Body;
import soot.PatchingChain;
import soot.Trap;
import soot.Unit;
import soot.baf.JSRInst;
import soot.baf.TableSwitchInst;
import soot.baf.TargetArgInst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Debugger {

  private static final Logger logger = LoggerFactory.getLogger(Debugger.class);

public static void printBaf(Body b) {

    logger.info(b.getMethod().getName() + "\n");
    int i = 0;
    Map<Unit, Integer> index = new HashMap<>();
    Iterator<Unit> it = b.getUnits().iterator();
    while (it.hasNext()) {
      index.put(it.next(), Integer.valueOf(i++));
    }
    it = b.getUnits().iterator();
    while (it.hasNext()) {
      Object o = it.next();
      logger.info(new StringBuilder().append(index.get(o).toString()).append(" ").append(o).append(" ").append(o instanceof TargetArgInst ? index.get(((TargetArgInst) o).getTarget()).toString() : "")
			.toString());
    }
    logger.info("\n");
  }

  public static void printUnits(Body b, String msg) {
    int i = 0;
    Map<Unit, Integer> numbers = new HashMap<>();
    PatchingChain<Unit> u = b.getUnits();
    Iterator<Unit> it = u.snapshotIterator();
    while (it.hasNext()) {
      numbers.put(it.next(), Integer.valueOf(i++));
    }

    int jsr = 0;
    logger.info(new StringBuilder().append("\r\r").append(b.getMethod().getName()).append("  ").append(msg).toString());
    Iterator<Unit> udit = u.snapshotIterator();
    while (udit.hasNext()) {
      Unit unit = (Unit) udit.next();
      Integer numb = numbers.get(unit);

      if (numb.intValue() == 149) {
        logger.info("hi");
      }

      if (unit instanceof TargetArgInst) {
        if (unit instanceof JSRInst) {
          jsr++;
        }
        TargetArgInst ti = (TargetArgInst) unit;
        if (ti.getTarget() == null) {
          logger.info(unit + " null null null null null null null null null");
          continue;
        }
        logger.info(new StringBuilder().append(numbers.get(unit).toString()).append(" ").append(unit).append("   #").append(numbers.get(ti.getTarget()).toString()).toString());
        continue;
      } else if (unit instanceof TableSwitchInst) {
        TableSwitchInst tswi = (TableSwitchInst) unit;
        logger.info(numbers.get(unit).toString() + " SWITCH:");
        logger.info(new StringBuilder().append("\tdefault: ").append(tswi.getDefaultTarget()).append("  ").append(numbers.get(tswi.getDefaultTarget()).toString()).toString());
        int index = 0;
        for (int x = tswi.getLowIndex(); x <= tswi.getHighIndex(); x++) {
          logger
              .info(new StringBuilder().append("\t ").append(x).append(": ").append(tswi.getTarget(index)).append("  ").append(numbers.get(tswi.getTarget(index++)).toString())
					.toString());
        }
        continue;
      }
      logger.info(new StringBuilder().append(numb.toString()).append(" ").append(unit).toString());
    }

    Iterator<Trap> tit = b.getTraps().iterator();
    while (tit.hasNext()) {
      Trap t = tit.next();
      logger.info(new StringBuilder().append(numbers.get(t.getBeginUnit())).append(" ").append(t.getBeginUnit()).append(" to ").append(numbers.get(t.getEndUnit())).append(" ")
			.append(t.getEndUnit()).append("  at ").append(numbers.get(t.getHandlerUnit())).append(" ").append(t.getHandlerUnit()).toString());
    }
    if (jsr > 0) {
      logger.info("\r\tJSR Instructions: " + jsr);
    }
  }

  public static void printUnits(PatchingChain<Unit> u, String msg) {
    int i = 0;
    HashMap<Unit, Integer> numbers = new HashMap<>();
    Iterator<Unit> it = u.snapshotIterator();
    while (it.hasNext()) {
      numbers.put(it.next(), Integer.valueOf(i++));
    }

    logger.info("\r\r***********  " + msg);
    Iterator<Unit> udit = u.snapshotIterator();
    while (udit.hasNext()) {
      Unit unit = (Unit) udit.next();
      Integer numb = numbers.get(unit);

      if (numb.intValue() == 149) {
        logger.info("hi");
      }

      if (unit instanceof TargetArgInst) {
        TargetArgInst ti = (TargetArgInst) unit;
        if (ti.getTarget() == null) {
          logger.info(unit + " null null null null null null null null null");
          continue;
        }
        logger.info(new StringBuilder().append(numbers.get(unit).toString()).append(" ").append(unit).append("   #").append(numbers.get(ti.getTarget()).toString()).toString());
        continue;
      } else if (unit instanceof TableSwitchInst) {
        TableSwitchInst tswi = (TableSwitchInst) unit;
        logger.info(numbers.get(unit).toString() + " SWITCH:");
        logger.info(new StringBuilder().append("\tdefault: ").append(tswi.getDefaultTarget()).append("  ").append(numbers.get(tswi.getDefaultTarget()).toString()).toString());
        int index = 0;
        for (int x = tswi.getLowIndex(); x <= tswi.getHighIndex(); x++) {
          logger
              .info(new StringBuilder().append("\t ").append(x).append(": ").append(tswi.getTarget(index)).append("  ").append(numbers.get(tswi.getTarget(index++)).toString())
					.toString());
        }
        continue;
      }
      logger.info(new StringBuilder().append(numb.toString()).append(" ").append(unit).toString());
    }
  }
}
