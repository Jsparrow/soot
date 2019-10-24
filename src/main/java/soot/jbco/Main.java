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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.Local;
import soot.Pack;
import soot.PackManager;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Transformer;
import soot.jbco.bafTransformations.AddJSRs;
import soot.jbco.bafTransformations.BAFCounter;
import soot.jbco.bafTransformations.BAFPrintout;
import soot.jbco.bafTransformations.BafLineNumberer;
import soot.jbco.bafTransformations.ConstructorConfuser;
import soot.jbco.bafTransformations.FindDuplicateSequences;
import soot.jbco.bafTransformations.FixUndefinedLocals;
import soot.jbco.bafTransformations.IfNullToTryCatch;
import soot.jbco.bafTransformations.IndirectIfJumpsToCaughtGotos;
import soot.jbco.bafTransformations.Jimple2BafLocalBuilder;
import soot.jbco.bafTransformations.LocalsToBitField;
import soot.jbco.bafTransformations.MoveLoadsAboveIfs;
import soot.jbco.bafTransformations.RemoveRedundantPushStores;
import soot.jbco.bafTransformations.TryCatchCombiner;
import soot.jbco.bafTransformations.UpdateConstantsToFields;
import soot.jbco.bafTransformations.WrapSwitchesInTrys;
import soot.jbco.jimpleTransformations.AddSwitches;
import soot.jbco.jimpleTransformations.ArithmeticTransformer;
import soot.jbco.jimpleTransformations.BuildIntermediateAppClasses;
import soot.jbco.jimpleTransformations.ClassRenamer;
import soot.jbco.jimpleTransformations.CollectConstants;
import soot.jbco.jimpleTransformations.CollectJimpleLocals;
import soot.jbco.jimpleTransformations.FieldRenamer;
import soot.jbco.jimpleTransformations.GotoInstrumenter;
import soot.jbco.jimpleTransformations.LibraryMethodWrappersBuilder;
import soot.jbco.jimpleTransformations.MethodRenamer;

/**
 * @author Michael Batchelder
 *
 *         Created on 24-Jan-2006
 */
public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static boolean jbcoDebug = false;
  public static boolean jbcoSummary = true;
  public static boolean jbcoVerbose = false;
  public static boolean metrics = false;

  public static Map<String, Integer> transformsToWeights = new ConcurrentHashMap<>();
  public static Map<String, Map<Object, Integer>> transformsToMethodsToWeights = new ConcurrentHashMap<>();
  public static Map method2Locals2REALTypes = new ConcurrentHashMap();
  public static Map<SootMethod, Map<Local, Local>> methods2Baf2JLocals = new ConcurrentHashMap<>();
  public static Map<SootMethod, List<Local>> methods2JLocals = new ConcurrentHashMap<>();
  public static List<SootClass> IntermediateAppClasses = new CopyOnWriteArrayList<>();

  static List<Transformer> jbcotransforms = new CopyOnWriteArrayList<>();

  static String[][] optionStrings = new String[][] {
      { "Rename Classes", "Rename Methods", "Rename Fields", "Build API Buffer Methods", "Build Library Buffer Classes",
          "Goto Instruction Augmentation", "Add Dead Switche Statements", "Convert Arith. Expr. To Bitshifting Ops",
          "Convert Branches to JSR Instructions", "Disobey Constructor Conventions", "Reuse Duplicate Sequences",
          "Replace If(Non)Nulls with Try-Catch", "Indirect If Instructions", "Pack Locals into Bitfields",
          "Reorder Loads Above Ifs", "Combine Try and Catch Blocks", "Embed Constants in Fields",
          "Partially Trap Switches" },
      { "wjtp.jbco_cr", "wjtp.jbco_mr", "wjtp.jbco_fr", "wjtp.jbco_blbc", "wjtp.jbco_bapibm", "jtp.jbco_gia",
          "jtp.jbco_adss", "jtp.jbco_cae2bo", "bb.jbco_cb2ji", "bb.jbco_dcc", "bb.jbco_rds", "bb.jbco_riitcb", "bb.jbco_iii",
          "bb.jbco_plvb", "bb.jbco_rlaii", "bb.jbco_ctbcb", "bb.jbco_ecvf", "bb.jbco_ptss" } };

  public static void main(String[] argv) {
    int rcount = 0;
    Vector<String> transformsToAdd = new Vector<>();
    boolean remove[] = new boolean[argv.length];
    for (int i = 0; i < argv.length; i++) {
      String arg = argv[i];
      if ("-jbco:help".equals(arg)) {
        logger.info("The Java Bytecode Obfuscator (JBCO)\n\nGeneral Options:");
        logger.info("\t-jbco:help     -  print this help message.");
        logger.info("\t-jbco:verbose  -  print extra information during obfuscation.");
        logger.info("\t-jbco:silent   -  turn off all output, including summary information.");
        logger.info("\t-jbco:metrics  -  calculate total vertices and edges;\n"
            + "\t                  calculate avg. and highest graph degrees.");
        logger.info(new StringBuilder().append("\t-jbco:debug    -  turn on extra debugging like\n").append("\t                  stack height and type verifier.\n\nTransformations ( -t:[W:]<name>[:pattern] )\n").append("\tW              -  specify obfuscation weight (0-9)\n").append("\t<name>         -  name of obfuscation (from list below)\n").append("\tpattern        -  limit to method names matched by pattern\n").append("\t                  prepend * to pattern if a regex\n").toString());

        for (int j = 0; j < optionStrings[0].length; j++) {
          String line = "\t" + optionStrings[1][j];
          int size = 20 - line.length();
          while (size-- > 0) {
            line += " ";
          }
          line += "-  " + optionStrings[0][j];
          logger.info(line);
        }
        System.exit(0);
      } else if ("-jbco:metrics".equals(arg)) {
        metrics = true;
        remove[i] = true;
        rcount++;
      } // temp dumby arg to printout name of benchmark for output files
      else if (arg.startsWith("-jbco:name:")) {
        remove[i] = true;
        rcount++;
      } // dumby dumby dumby
      else if (arg.startsWith("-t:")) {
        arg = arg.substring(3);

        int tweight = 9;
        char cweight = arg.charAt(0);
        if (cweight >= '0' && cweight <= '9') {
          try {
            tweight = Integer.parseInt(arg.substring(0, 1));
          } catch (NumberFormatException nfe) {
            logger.error(nfe.getMessage(), nfe);
			logger.debug("Improperly formated transformation weight: " + argv[i]);
            System.exit(1);
          }
          arg = arg.substring(arg.indexOf(':') + 1);
        }

        transformsToAdd.add(arg);
        transformsToWeights.put(arg, Integer.valueOf(tweight));
        if ("wjtp.jbco_fr".equals(arg)) {
          FieldRenamer.v().setRenameFields(true);
        }
        remove[i] = true;
        rcount++;
      } else if ("-jbco:verbose".equals(arg)) {
        jbcoVerbose = true;
        remove[i] = true;
        rcount++;
      } else if ("-jbco:silent".equals(arg)) {
        jbcoSummary = false;
        jbcoVerbose = false;
        remove[i] = true;
        rcount++;
      } else if ("-jbco:debug".equals(arg)) {
        jbcoDebug = true;
        remove[i] = true;
        rcount++;
      } else if (arg.startsWith("-i") && arg.length() > 4 && arg.charAt(3) == ':' && arg.charAt(2) == 't') {
        Object o = null;
        arg = arg.substring(4);

        int tweight = 9;
        char cweight = arg.charAt(0);
        if (cweight >= '0' && cweight <= '9') {
          try {
            tweight = Integer.parseInt(arg.substring(0, 1));
          } catch (NumberFormatException nfe) {
            logger.error(nfe.getMessage(), nfe);
			logger.debug("Improperly formatted transformation weight: " + argv[i]);
            System.exit(1);
          }
          if (arg.indexOf(':') < 0) {
            logger.debug("Illegally Formatted Option: " + argv[i]);
            System.exit(1);
          }
          arg = arg.substring(arg.indexOf(':') + 1);
        }

        int index = arg.indexOf(':');
        if (index < 0) {
          logger.debug("Illegally Formatted Option: " + argv[i]);
          System.exit(1);
        }

        String trans = arg.substring(0, index);
        arg = arg.substring(index + 1, arg.length());

        if (arg.startsWith("*")) {
          arg = arg.substring(1);
          try {
            o = java.util.regex.Pattern.compile(arg);
          } catch (java.util.regex.PatternSyntaxException pse) {
            logger.error(pse.getMessage(), pse);
			logger.debug("Illegal Regular Expression Pattern: " + arg);
            System.exit(1);
          }
        } else {
          o = arg;
        }

        transformsToAdd.add(trans);
        Map<Object, Integer> htmp = transformsToMethodsToWeights.get(trans);
        if (htmp == null) {
          htmp = new HashMap<>();
          transformsToMethodsToWeights.put(trans, htmp);
        }
        htmp.put(o, Integer.valueOf(tweight));
        remove[i] = true;
        rcount++;
      } else {
        remove[i] = false;
      }
    }

    if (rcount > 0) {
      int index = 0;
      String tmp[] = (String[]) argv.clone();
      argv = new String[argv.length - rcount];
      for (int i = 0; i < tmp.length; i++) {
        if (!remove[i]) {
          argv[index++] = tmp[i];
        }
      }
    }

    transformsToAdd.remove("wjtp.jbco_cc");
    transformsToAdd.remove("jtp.jbco_jl");
    transformsToAdd.remove("bb.jbco_j2bl");
    transformsToAdd.remove("bb.jbco_ful");

    if (!metrics) {
      if (transformsToAdd.size() == 0) {
        logger.debug("No Jbco tasks to complete.  Shutting Down...");
        System.exit(0);
      }

      Pack wjtp = PackManager.v().getPack("wjtp");
      Pack jtp = PackManager.v().getPack("jtp");
      Pack bb = PackManager.v().getPack("bb");

      if (transformsToAdd.contains("jtp.jbco_adss")) {
        wjtp.add(new Transform("wjtp.jbco_fr", newTransform((Transformer) getTransform("wjtp.jbco_fr"))));
        if (transformsToAdd.remove("wjtp.jbco_fr")) {
          FieldRenamer.v().setRenameFields(true);
        }
      }

      if (transformsToAdd.contains("bb.jbco_ecvf")) {
        wjtp.add(new Transform("wjtp.jbco_cc", newTransform((Transformer) getTransform("wjtp.jbco_cc"))));
      }

      String jl = null;
      for (String aTransformsToAdd : transformsToAdd) {
        if (aTransformsToAdd.startsWith("bb")) {
          jl = "jtp.jbco_jl";
          jtp.add(new Transform(jl, newTransform((Transformer) getTransform(jl))));
          bb.insertBefore(new Transform("bb.jbco_j2bl", newTransform((Transformer) getTransform("bb.jbco_j2bl"))), "bb.lso");
          bb.insertBefore(new Transform("bb.jbco_ful", newTransform((Transformer) getTransform("bb.jbco_ful"))), "bb.lso");
          bb.add(new Transform("bb.jbco_rrps", newTransform((Transformer) getTransform("bb.jbco_rrps"))));

          break;
        }
      }

      for (String tname : transformsToAdd) {
        IJbcoTransform t = getTransform(tname);

        Pack p = tname.startsWith("wjtp") ? wjtp : tname.startsWith("jtp") ? jtp : bb;
        String insertBefore = p == jtp ? jl : p == bb ? "bb.jbco_ful" : null;
        if (insertBefore != null) {
          p.insertBefore(new Transform(tname, newTransform((Transformer) t)), insertBefore);
        } else {
          p.add(new Transform(tname, newTransform((Transformer) t)));
        }
      }

      for (Iterator<Transform> phases = wjtp.iterator(); phases.hasNext();) {
        if (((Transform) phases.next()).getPhaseName().indexOf("jbco") > 0) {
          argv = checkWhole(argv, true);
          break;
        }
      }

      if (jbcoSummary) {
        for (int i = 0; i < 3; i++) {
          Iterator<Transform> phases = i == 0 ? wjtp.iterator() : i == 1 ? jtp.iterator() : bb.iterator();
          logger.debug(i == 0 ? "Whole Program Jimple Transformations:"
              : i == 1 ? "Jimple Method Body Transformations:" : "Baf Method Body Transformations:");
          while (phases.hasNext()) {
            Transform o = (Transform) phases.next();
            Transformer t = o.getTransformer();
            if (t instanceof IJbcoTransform) {
              logger.debug(new StringBuilder().append("\t").append(((IJbcoTransform) t).getName()).append("  JBCO").toString());
            } else {
              logger.debug(new StringBuilder().append("\t").append(o.getPhaseName()).append("  default").toString());
            }
          }
        }

      }

      bb.add(new Transform("bb.jbco_bln", new BafLineNumberer()));
      bb.add(new Transform("bb.jbco_lta", soot.tagkit.LineNumberTagAggregator.v()));
    } else {
      argv = checkWhole(argv, false);
    }

    soot.Main.main(argv);

    if (!jbcoSummary) {
		return;
	}
	logger.debug("\n***** JBCO SUMMARY *****\n");
	jbcotransforms.stream().filter(o -> o instanceof IJbcoTransform).forEach(o -> ((IJbcoTransform) o).outputSummary());
	logger.debug("\n***** END SUMMARY *****\n");
  }

  private static String[] checkWhole(String argv[], boolean add) {
    for (int i = 0; i < argv.length; i++) {
      if ("-w".equals(argv[i])) {
        if (add) {
          return argv;
        } else {
          String tmp[] = new String[argv.length - 1];
          if (i == 0) {
            System.arraycopy(argv, 1, tmp, 0, tmp.length);
          } else {
            System.arraycopy(argv, 0, tmp, 0, i);
            if (i < (argv.length - 1)) {
              System.arraycopy(argv, i + 1, tmp, i, tmp.length - i);
            }
          }
          return tmp;
        }
      }
    }

    if (!add) {
		return argv;
	}
	String tmp[] = new String[argv.length + 1];
	tmp[0] = "-w";
	System.arraycopy(argv, 0, tmp, 1, argv.length);
	return tmp;
  }

  private static Transformer newTransform(Transformer t) {
    jbcotransforms.add(t);
    return t;
  }

  private static IJbcoTransform getTransform(String name) {
    if (name.startsWith("bb.jbco_rrps")) {
      return new RemoveRedundantPushStores();
    }
    if (name.startsWith("bb.printout")) {
      return new BAFPrintout(name, true);
    }
    if ("bb.jbco_j2bl".equals(name)) {
      return new Jimple2BafLocalBuilder();
    }
    if ("jtp.jbco_gia".equals(name)) {
      return new GotoInstrumenter();
    }
    if ("jtp.jbco_cae2bo".equals(name)) {
      return new ArithmeticTransformer();
    }
    if ("wjtp.jbco_cc".equals(name)) {
      return new CollectConstants();
    }
    if ("bb.jbco_ecvf".equals(name)) {
      return new UpdateConstantsToFields();
    }
    if ("bb.jbco_rds".equals(name)) {
      return new FindDuplicateSequences();
    }
    if ("bb.jbco_plvb".equals(name)) {
      return new LocalsToBitField();
    }
    if ("bb.jbco_rlaii".equals(name)) {
      return new MoveLoadsAboveIfs();
    }
    if ("bb.jbco_ptss".equals(name)) {
      return new WrapSwitchesInTrys();
    }
    if ("bb.jbco_iii".equals(name)) {
      return new IndirectIfJumpsToCaughtGotos();
    }
    if ("bb.jbco_ctbcb".equals(name)) {
      return new TryCatchCombiner();
    }
    if ("bb.jbco_cb2ji".equals(name)) {
      return new AddJSRs();
    }
    if ("bb.jbco_riitcb".equals(name)) {
      return new IfNullToTryCatch();
    }
    if ("wjtp.jbco_blbc".equals(name)) {
      return new LibraryMethodWrappersBuilder();
    }
    if ("wjtp.jbco_bapibm".equals(name)) {
      return new BuildIntermediateAppClasses();
    }
    if ("wjtp.jbco_cr".equals(name)) {
      return ClassRenamer.v();
    }
    if ("bb.jbco_ful".equals(name)) {
      return new FixUndefinedLocals();
    }
    if ("wjtp.jbco_fr".equals(name)) {
      return FieldRenamer.v();
    }
    if ("wjtp.jbco_mr".equals(name)) {
      return MethodRenamer.v();
    }
    if ("jtp.jbco_adss".equals(name)) {
      return new AddSwitches();
    }
    if ("jtp.jbco_jl".equals(name)) {
      return new CollectJimpleLocals();
    }
    if ("bb.jbco_dcc".equals(name)) {
      return new ConstructorConfuser();
    }
    if ("bb.jbco_counter".equals(name)) {
      return new BAFCounter();
    }

    return null;
  }

  private static int getWeight(String phasename) {
    int weight = 9;
    Integer intg = transformsToWeights.get(phasename);
    if (intg != null) {
      weight = intg.intValue();
    }
    return weight;
  }

  public static int getWeight(String phasename, String method) {
    Map<Object, Integer> htmp = transformsToMethodsToWeights.get(phasename);

    int result = 10;

    if (htmp != null) {
      Iterator<Object> keys = htmp.keySet().iterator();

      while (keys.hasNext()) {
        Integer intg = null;
        Object o = keys.next();
        if (o instanceof java.util.regex.Pattern) {
          if (((java.util.regex.Pattern) o).matcher(method).matches()) {
            intg = (Integer) htmp.get(o);
          } else {
            intg = 0;
          }
        } else if (o instanceof String && method.equals(o)) {
          intg = (Integer) htmp.get(o);
        }

        if (intg != null && intg.intValue() < result) {
          result = intg.intValue();
        }
      }
    }
    if (result > 9 || result < 0) {
      result = getWeight(phasename);
    }

    if (jbcoVerbose) {
      logger.debug(new StringBuilder().append("[").append(phasename).append("] Processing ").append(method).append(" with weight: ").append(result).toString());
    }
    return result;
  }
}

/*
 * //bb.insertBefore(new Transform("bb.printout2", newTransform(new BAFPrintout(true))),"bb.lp");
 *
 * for (int i = 0; i < transformsToAdd.size(); i++) { String t = (String)transformsToAdd.get(i);
 *
 * if (t.equals("bb.jbco_j2bl")) bb.insertBefore(new Transform("bb.jbco_j2bl", newTransform(new
 * Jimple2BafLocalBuilder())),"bb.lp"); else {
 *
 * } } //bb.insertBefore(new Transform("bb.jbco_j2bl", newTransform(new Jimple2BafLocalBuilder())),"bb.lp"); // requires
 * CollecJimpleLocals in jtp pack to run
 *
 * //jtp.add(new Transform("jtp.jbco_gia", newTransform(new GotoInstrumenter()))); // seems like this might cause issues -
 * too obvious? not worth it?
 *
 * { // works fine - no problems whatsoever //jtp.add(new Transform("jtp.jbco_cae2bo", newTransform(new
 * ArithmeticTransformer()))); }
 *
 * // these two must come together - THESE ARE _not_ CAUSING PROBLEMS IN TRIPHASE?!?!?! { //wjtp.add(new
 * Transform("wjtp.jbco_cc", newTransform(new CollectConstants()))); //bb.add(new Transform("bb.jbco_ecvf", newTransform(new
 * UpdateConstantsToFields()))); }
 *
 * // these two must come together { // works fine //bb.insertBefore(new Transform("bb.jbco_rds", newTransform(new
 * FindDuplicateSequences())),"bb.lp");
 *
 * // THIS HAS BEEN TESTED AND RESULTS REPORTED // this SLOWS performance - not worth it //bb.insertBefore(new
 * Transform("bb.jbco_plvb", newTransform(new LocalsToBitField())),"bb.lp"); }
 *
 * // works fine - having problems with this in TRIPHASE?? bb.insertBefore(new Transform("bb.jbco_rlaii", newTransform(new
 * MoveLoadsAboveIfs())),"bb.lp");
 *
 * // works fine //bb.insertBefore(new Transform("bb.jbco_ptss", newTransform(new WrapSwitchesInTrys())),"bb.lp");
 *
 * // works fine //bb.insertBefore(new Transform("bb.jbco_iii", newTransform(new IndirectIfJumpsToCaughtGotos())),"bb.lp");
 *
 * // should be LAST CALLED in bb // works fine //bb.insertBefore(new Transform("bb.jbco_ctbcb", newTransform(new
 * TryCatchCombiner())),"bb.lp");
 *
 * // works fine //bb.insertBefore(new Transform("bb.jbco_cb2ji", newTransform(new AddJSRs())),"bb.lp");
 *
 * //bb.insertBefore(new Transform("bb.jbco_riitcb", newTransform(new IfNullToTryCatch())),"bb.lp"); /////bb.add(new
 * Transform("bb.jbco_v2s", newTransform(new Virtual2Special()))); // working on it //wjtp.add(new
 * Transform("wjtp.jbco_c2lco", newTransform(new _ConvertToLeastCommonObject())));
 *
 * // works fine //wjtp.add(new Transform("wjtp.jbco_blbc", newTransform(new LibraryMethodWrappersBuilder())));
 * //wjtp.add(new Transform("wjtp.jbco_TEST", new jimpleTester()));
 *
 * // works fine //wjtp.add(new Transform("wjtp.jbco_bapibm", newTransform(new BuildIntermediateAppClasses())));
 *
 * // works fine //wjtp.add(new Transform("wjtp.jbco_cr", newTransform(new ClassRenamer())));
 *
 *
 * //bb.insertBefore(new Transform("bb.jbco_ful", newTransform(new FixUndefinedLocals())),"bb.lp");
 *
 *
 * // works fine wjtp.add(new Transform("wjtp.jbco_fr", newTransform(new FieldRenamer())));
 *
 * FieldRenamer.rename_fields = true;
 *
 * // works fine wjtp.add(new Transform("wjtp.jbco_mr", newTransform(new MethodRenamer())));
 *
 * // jtp.add(new Transform("jtp.jbco_v2s", newTransform(new Virtual2SpecialConverter())));
 *
 *
 *
 * bb.insertBefore(new Transform("bb.printout1", newTransform(new BAFPrintout(true))),"bb.lp"); // always call BEFORE
 * localpacker, else you're in trouble
 *
 * jtp.add(new Transform("jtp.jbco_adss", newTransform(new AddSwitches())));
 *
 * //bb.insertBefore(new Transform("bb.printout1", new BAFPrintout(true)),"bb.lp");
 *
 * //bb.add(new Transform("bb.printout", new BAFPrintout(true)));
 *
 * //jtp.add(new Transform("jtp.jbco_jl", newTransform(new CollectJimpleLocals()))); // helper transform for B2J Local
 */
