package soot;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1997 - 2012 Raja Vallee-Rai and others
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

import static java.net.URLEncoder.encode;

import com.google.common.base.Joiner;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import soot.options.CGOptions;
import soot.options.Options;
import soot.toolkits.astmetrics.ClassData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Main class for Soot; provides Soot's command-line user interface. */
public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

// TODO: the following string should be updated by the source control
  // No it shouldn't. Prcs is horribly broken in this respect, and causes
  // the code to not compile all the time.
  public static final String versionString = Main.class.getPackage().getImplementationVersion() == null ? "trunk"
      : Main.class.getPackage().getImplementationVersion();

private Date start;

private long startNano;

private long finishNano;

public String[] cmdLineArgs = new String[0];

public Main(Singletons.Global g) {
  }

public static Main v() {
    return G.v().soot_Main();
  }

private void printVersion() {
    logger.info("Soot version " + versionString);
    logger.info("Copyright (C) 1997-2010 Raja Vallee-Rai and others.");
    logger.info("All rights reserved.");
    System.out.println();
    logger.info("Contributions are copyright (C) 1997-2010 by their respective contributors.");
    logger.info("See the file 'credits' for a list of contributors.");
    logger.info("See individual source files for details.");
    System.out.println();
    logger.info("Soot comes with ABSOLUTELY NO WARRANTY.  Soot is free software,");
    logger.info("and you are welcome to redistribute it under certain conditions.");
    logger.info("See the accompanying file 'COPYING-LESSER.txt' for details.");

    logger.info("Visit the Soot website:");
    logger.info("  http://www.sable.mcgill.ca/soot/");

    logger.info("For a list of command line options, enter:");
    logger.info("  java soot.Main --help");
  }

private void processCmdLine(String[] args) {

    if (!Options.v().parse(args)) {
      throw new OptionsParseException("Option parse error");
    }

    if (PackManager.v().onlyStandardPacks()) {
      PackManager.v().allPacks().forEach(pack -> {
        Options.v().warnForeignPhase(pack.getPhaseName());
        for (Transform tr : pack) {
          Options.v().warnForeignPhase(tr.getPhaseName());
        }
      });
    }
    Options.v().warnNonexistentPhase();

    if (Options.v().help()) {
      logger.info(Options.v().getUsage());
      throw new CompilationDeathException(CompilationDeathException.COMPILATION_SUCCEEDED);
    }

    if (Options.v().phase_list()) {
      logger.info(Options.v().getPhaseList());
      throw new CompilationDeathException(CompilationDeathException.COMPILATION_SUCCEEDED);
    }

    if (!Options.v().phase_help().isEmpty()) {
      Options.v().phase_help().forEach(phase -> logger.info(Options.v().getPhaseHelp(phase)));
      throw new CompilationDeathException(CompilationDeathException.COMPILATION_SUCCEEDED);
    }

    if ((!Options.v().unfriendly_mode() && (args.length == 0)) || Options.v().version()) {
      printVersion();
      throw new CompilationDeathException(CompilationDeathException.COMPILATION_SUCCEEDED);
    }

    if (Options.v().on_the_fly()) {
      Options.v().set_whole_program(true);
      PhaseOptions.v().setPhaseOption("cg", "off");
    }

    postCmdLineCheck();
  }

private void postCmdLineCheck() {
    if (Options.v().classes().isEmpty() && Options.v().process_dir().isEmpty()) {
      throw new CompilationDeathException(CompilationDeathException.COMPILATION_ABORTED, "No input classes specified!");
    }
  }

/**
   * Entry point for cmd line invocation of soot.
   */
  public static void main(String[] args) {
    try {
      Main.v().run(args);
    } catch (OptionsParseException e) {
		logger.error(e.getMessage(), e);
      // error message has already been printed
    } catch (StackOverflowError e) {
      logger.error("" + "Soot has run out of stack memory.");
      logger.error("" + "To allocate more stack memory to Soot, use the -Xss switch to Java.");
      logger.error("" + "For example (for 2MB): java -Xss2m soot.Main ...");
      throw e;
    } catch (OutOfMemoryError e) {
      logger.error("" + "Soot has run out of the memory allocated to it by the Java VM.");
      logger.error("" + "To allocate more memory to Soot, use the -Xmx switch to Java.");
      logger.error("" + "For example (for 2GB): java -Xmx2g soot.Main ...");
      throw e;
    } catch (RuntimeException e) {
      logger.error(e.getMessage(), e);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      logger.error(e.getMessage(), e);
      String stackStraceString = bos.toString();
      try {
        final String TRACKER_URL = "https://github.com/Sable/soot/issues/new?";
        String commandLineArgs = Joiner.on(" ").join(args);
        String body = new StringBuilder().append("Steps to reproduce:\n1.) ...\n\n").append("Files used to reproduce: \n...\n\n").append("Soot version: ").append("<pre>").append(escape(versionString)).append("</pre>").append("\n\n")
				.append("Command line:\n").append("<pre>").append(escape(commandLineArgs)).append("</pre>\n\n").append("Max Memory:\n").append("<pre>").append(escape((Runtime.getRuntime().maxMemory() / (1024 * 1024)) + "MB")).append("</pre>")
				.append("\n\n").append("Stack trace:\n").append("<pre>").append(escape(stackStraceString)).append("</pre>").toString();

        String title = e.getClass().getName() + " when ...";

        StringBuilder sb = new StringBuilder();
        sb.append("\n\nOuuups... something went wrong! Sorry about that.\n");
        sb.append("Follow these steps to fix the problem:\n");
        sb.append("1.) Are you sure you used the right command line?\n");
        sb.append("    Click here to double-check:\n");
        sb.append("    https://github.com/Sable/soot/wiki/Options-and-JavaDoc\n");
        sb.append("\n");
        sb.append("2.) Not sure whether it's a bug? Feel free to discuss\n");
        sb.append("    the issue on the Soot mailing list:\n");
        sb.append("    https://github.com/Sable/soot/wiki/Getting-help\n");
        sb.append("\n");
        sb.append("3.) Sure it's a bug? Click this link to report it.\n");
        sb.append(new StringBuilder().append("    ").append(TRACKER_URL).append("title=").append(encode(title, "UTF-8")).append("&body=").append(encode(body, "UTF-8"))
				.append("\n").toString());
        sb.append("    Please be as precise as possible when giving us\n");
        sb.append("    information on how to reproduce the problem. Thanks!");

        logger.error(String.valueOf(sb));

        // Exit with an exit code 1
        System.exit(1);

      } catch (UnsupportedEncodingException e1) {

        logger.error(e1.getMessage(), e1);
		// Exit with an exit code 1
        System.exit(1);
      }
    }
  }

private static CharSequence escape(CharSequence s) {
    int start = 0;
    int end = s.length();

    StringBuilder sb = new StringBuilder(32 + (end - start));
    for (int i = start; i < end; i++) {
      int c = s.charAt(i);
      switch (c) {
        case '<':
        case '>':
        case '"':
        case '\'':
        case '&':
          sb.append(s, start, i);
          sb.append("&#");
          sb.append(c);
          sb.append(';');
          start = i + 1;
          break;
      }
    }
    return sb.append(s, start, end);
  }

/**
   * Entry point to the soot's compilation process.
   */
  public void run(String[] args) {
    cmdLineArgs = args;

    start = new Date();
    startNano = System.nanoTime();

    try {
      Timers.v().totalTimer.start();

      processCmdLine(cmdLineArgs);

      autoSetOptions();

      logger.info("Soot started on " + start);

      Scene.v().loadNecessaryClasses();

      /*
       * By this all the java to jimple has occured so we just check ast-metrics flag
       *
       * If it is set......print the astMetrics.xml file and stop executing soot
       */
      if (Options.v().ast_metrics()) {
        try (OutputStream streamOut = new FileOutputStream("../astMetrics.xml")) {
          PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
          writerOut.println("<?xml version='1.0'?>");
          writerOut.println("<ASTMetrics>");

          // each is a classData object
		G.v().ASTMetricsData.forEach(writerOut::println);

          writerOut.println("</ASTMetrics>");
          writerOut.flush();
        } catch (IOException e) {
          throw new CompilationDeathException("Cannot output file astMetrics", e);
        }
        return;
      }

      PackManager.v().runPacks();
      if (!Options.v().oaat()) {
        PackManager.v().writeOutput();
      }

      Timers.v().totalTimer.end();

      // Print out time stats.
      if (Options.v().time()) {
        Timers.v().printProfilingInformation();
      }

    } catch (CompilationDeathException e) {
      Timers.v().totalTimer.end();
      if (e.getStatus() != CompilationDeathException.COMPILATION_SUCCEEDED) {
        throw e;
      } else {
        return;
      }
    }

    finishNano = System.nanoTime();

    logger.info("Soot finished on " + new Date());
    long runtime = (finishNano - startNano) / 1000000l;
    logger.info(new StringBuilder().append("").append("Soot has run for ").append(runtime / 60000).append(" min. ").append((runtime % 60000) / 1000).append(" sec.")
			.toString());

  }

public void autoSetOptions() {
    // when no-bodies-for-excluded is enabled, also enable phantom refs
    if (Options.v().no_bodies_for_excluded()) {
      Options.v().set_allow_phantom_refs(true);
    }

    // when reflection log is enabled, also enable phantom refs
    CGOptions cgOptions = new CGOptions(PhaseOptions.v().getPhaseOptions("cg"));
    String log = cgOptions.reflection_log();
    if ((log != null) && (log.length() > 0)) {
      Options.v().set_allow_phantom_refs(true);
    }

    // if phantom refs enabled, ignore wrong staticness in type assigner
    if (Options.v().allow_phantom_refs()) {
      Options.v().set_wrong_staticness(Options.wrong_staticness_fix);
    }
  }
}
