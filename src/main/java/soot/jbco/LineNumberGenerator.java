package soot.jbco;

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

import java.util.Iterator;
import java.util.Map;

import soot.Body;
import soot.BodyTransformer;
import soot.PackManager;
import soot.PatchingChain;
import soot.Transform;
import soot.Unit;
import soot.tagkit.LineNumberTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineNumberGenerator {

  BafLineNumberer bln = new BafLineNumberer();

  public static void main(String[] argv) {
    // if you do not want soot to output new class files, run with comman line option "-f n"

    // if you want the source code line numbers for jimple statements, use this:
    PackManager.v().getPack("jtp").add(new Transform("jtp.lnprinter", new LineNumberGenerator().bln));

    // if you want the source code line numbers for baf instructions, use this:
    PackManager.v().getPack("bb").add(new Transform("bb.lnprinter", new LineNumberGenerator().bln));

    soot.Main.main(argv);
  }

  class BafLineNumberer extends BodyTransformer {
    private final Logger logger = LoggerFactory.getLogger(BafLineNumberer.class);

	@Override
	protected void internalTransform(Body b, String phaseName, Map<String, String> options) {

      logger.info("Printing Line Numbers for: " + b.getMethod().getSignature());

      PatchingChain<Unit> units = b.getUnits(); // get the method code
      units.stream().map(unit -> (Unit) unit).forEach(u -> {
		if (u.hasTag("LineNumberTag")) { // see if a LineNumberTag exists (it will if you use -keep-line-number)
          LineNumberTag tag = (LineNumberTag) u.getTag(("LineNumberTag"));
          logger.info(new StringBuilder().append(u).append(" has Line Number: ").append(tag.getLineNumber()).toString()); // print out the unit and line number
        } else {
          logger.info(u + " has no Line Number");
        }
	});

      logger.info("\n");
    }
  }
}