package soot.JastAddJ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.io.File;
import java.util.*;
import beaver.*;
import java.util.ArrayList;
import java.util.zip.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Collection;
import soot.*;
import soot.util.*;
import soot.jimple.*;
import soot.coffi.ClassFile;
import soot.coffi.method_info;
import soot.coffi.CONSTANT_Utf8_info;
import soot.tagkit.SourceFileTag;
import soot.coffi.CoffiMethodSource;
/**
  * @ast class
 * 
 */
public class Frontend extends java.lang.Object {
    private static final Logger logger = LoggerFactory.getLogger(Frontend.class);

    protected Program program;



    protected Frontend() {
      program = new Program();
      program.state().reset();
    }



    public boolean process(String[] args, BytecodeReader reader, JavaParser parser) {
      program.initBytecodeReader(reader);
      program.initJavaParser(parser);

      initOptions();
      processArgs(args);

      Collection files = program.options().files();

      if(program.options().hasOption("-version")) {
        printVersion();
        return true;
      }
      if(program.options().hasOption("-help") || files.isEmpty()) {
        printUsage();
        return true;
      }

      try {
        for(Iterator iter = files.iterator(); iter.hasNext(); ) {
          String name = (String)iter.next();
          if(!new File(name).exists()) {
			logger.error(new StringBuilder().append("WARNING: file \"").append(name).append("\" does not exist").toString());
		}
          program.addSourceFile(name);
        }

        for(Iterator iter = program.compilationUnitIterator(); iter.hasNext(); ) {
          CompilationUnit unit = (CompilationUnit)iter.next();
          if(unit.fromSource()) {
            try {
              Collection errors = unit.parseErrors();
              Collection warnings = new LinkedList();
              // compute static semantic errors when there are no parse errors
              // or the recover from parse errors option is specified
              if(errors.isEmpty() || program.options().hasOption("-recover")) {
				unit.errorCheck(errors, warnings);
			}
              if(!errors.isEmpty()) {
                processErrors(errors, unit);
                return false;
              }
              else {
               if(!warnings.isEmpty()) {
				processWarnings(warnings, unit);
			}
                processNoErrors(unit);
              }
            } catch (Throwable t) {
              logger.error("Errors:");
              logger.error(new StringBuilder().append("Fatal exception while processing ").append(unit.pathName()).append(":").toString());
              logger.error(t.getMessage(), t);
              return false;
            }
          }
        }
      } catch (Throwable t) {
        logger.error("Errors:");
        logger.error("Fatal exception:");
        logger.error(t.getMessage(), t);
        return false;
      }
      return true;
    }



    protected void initOptions() {
      Options options = program.options();
      options.initOptions();
      options.addKeyOption("-version");
      options.addKeyOption("-print");
      options.addKeyOption("-g");
      options.addKeyOption("-g:none");
      options.addKeyOption("-g:lines,vars,source");
      options.addKeyOption("-nowarn");
      options.addKeyOption("-verbose");
      options.addKeyOption("-deprecation");
      options.addKeyValueOption("-classpath");
      options.addKeyValueOption("-cp");
      options.addKeyValueOption("-sourcepath");
      options.addKeyValueOption("-bootclasspath");
      options.addKeyValueOption("-extdirs");
      options.addKeyValueOption("-d");
      options.addKeyValueOption("-encoding");
      options.addKeyValueOption("-source");
      options.addKeyValueOption("-target");
      options.addKeyOption("-help");
      options.addKeyOption("-O");
      options.addKeyOption("-J-Xmx128M");
      options.addKeyOption("-recover");
    }


    protected void processArgs(String[] args) {
      program.options().addOptions(args);
    }



    protected void processErrors(Collection errors, CompilationUnit unit) {
      logger.error("Errors:");
      for(Iterator iter2 = errors.iterator(); iter2.hasNext(); ) {
        logger.error(String.valueOf(iter2.next()));
      }
    }


    protected void processWarnings(Collection warnings, CompilationUnit unit) {
      logger.error("Warnings:");
      for(Iterator iter2 = warnings.iterator(); iter2.hasNext(); ) {
        logger.error(String.valueOf(iter2.next()));
      }
    }


    protected void processNoErrors(CompilationUnit unit) {
    }



    protected void printUsage() {
      printLongVersion();
      logger.info(
          new StringBuilder().append("\n").append(name()).append("\n\n").append("Usage: java ").append(name()).append(" <options> <source files>\n").append("  -verbose                  Output messages about what the compiler is doing\n")
				.append("  -classpath <path>         Specify where to find user class files\n").append("  -sourcepath <path>        Specify where to find input source files\n").append("  -bootclasspath <path>     Override location of bootstrap class files\n").append("  -extdirs <dirs>           Override location of installed extensions\n").append("  -d <directory>            Specify where to place generated class files\n").append("  -help                     Print a synopsis of standard options\n").append("  -version                  Print version information\n").toString()
          );
    }



    protected void printLongVersion() {
      logger.info(new StringBuilder().append(name()).append(" ").append(url()).append(" Version ").append(version()).toString());
    }



    protected void printVersion() {
      logger.info(new StringBuilder().append(name()).append(" ").append(version()).toString());
    }



    protected String name() {
      return "Java1.4Frontend";
    }


    protected String url() {
      return "(http://jastadd.cs.lth.se)";
    }



    protected String version() {
      return "R20070504";
    }


}
