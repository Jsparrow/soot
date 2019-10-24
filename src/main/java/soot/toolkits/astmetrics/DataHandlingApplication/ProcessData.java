package soot.toolkits.astmetrics.DataHandlingApplication;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2006 Nomair A. Naeem
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import soot.CompilationDeathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessData {

  private static final Logger logger = LoggerFactory.getLogger(ProcessData.class);

// when printing class names what is the max size of each class name
  private static final int CLASSNAMESIZE = 15;

  private static final int CLASS = 0;
  private static final int BENCHMARK = 1;

  private static String metricListFileName = null;

  private static final ArrayList<String> xmlFileList = new ArrayList<>();

  private static int aggregationMechanism = -1;

  private static OutputStream streamOut;
  private static PrintWriter bench;

  // set to true to getcsv instead of tex
  private static final boolean CSV = true;
  private static final boolean decompiler = false; // else it is obfuscated

  /**
   * @param args
   */
  public static void main(String[] args) {
    int argLength = args.length;
    if (argLength == 0) {
      printIntro();
      useHelp();
      System.exit(1);
    }

    if ("--help".equals(args[0])) {
      printHelp();
      System.exit(1);
    } else if ("-metricList".equals(args[0])) {
      metricListFileName(args);
      logger.info("A list of metrics will be stored in: " + metricListFileName);

      readXMLFileNames(2, args);

      try (OutputStream streamOut = new FileOutputStream(metricListFileName)) {
        PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
        writeMetricLists(writerOut);

        writerOut.flush();
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
		throw new CompilationDeathException("Cannot output file " + metricListFileName);
      }

    } else if ("-tables".equals(args[0])) {
      metricListFileName(args);
      logger.info("Will read column table headings from: " + metricListFileName);

      // read aggregation option
      aggregationOption(args);
      if (aggregationMechanism == ProcessData.BENCHMARK) {
        logger.info("Aggregating over benchmarks...each row is one of the xml files");
        logger.info(new StringBuilder().append("Only one tex file with the name").append(metricListFileName).append(".tex will be created").toString());
      } else if (aggregationMechanism == ProcessData.CLASS) {
        logger.info("Aggregating over class...each row is one class...");
        logger.info("Each benchmark (xml file) will have its own tex file");
      }

      readXMLFileNames(3, args);

      // TODO: hello
      generateMetricsTables();
    } else {
      logger.info("Incorrect argument number 1: expecting -metricList or -tables");
      System.exit(1);
    }

  }

  /*
   * check if there is a 3rd argument and it should be either -class or -benchmark
   */
  private static void aggregationOption(String[] args) {
    if (args.length < 3) {
      logger.info("Expecting -class or -benchmark at argument number 3");
      System.exit(1);
    }
    if ("-class".equals(args[2])) {
      aggregationMechanism = ProcessData.CLASS;
    } else if ("-benchmark".equals(args[2])) {
      aggregationMechanism = ProcessData.BENCHMARK;
    } else {
      logger.info("Expecting -class or -benchmark at argument number 3");
      System.exit(1);
    }
  }

  /*
   * Check if there is a 3rd argument if not complain
   *
   * @param startIndex is the first args element where we expect to have an xml file or a *
   */
  private static void readXMLFileNames(int startIndex, String args[]) {
    if (args.length < startIndex + 1) {
      logger.info("Expecting an xml file OR * symbol as argument number" + (startIndex + 1));
      System.exit(1);
    }

    // check if its a *
    if ("*".equals(args[startIndex])) {
      logger.info("Will read all xml files from directory");

      // READ DIRECTORY STRUCTURE
      readStar();
    } else {
      for (int i = startIndex; i < args.length; i++) {
        String temp = args[i];
        if (!temp.endsWith(".xml")) {
          // System.out.println("Argument number "+(startIndex+1) + ": '" + temp+"' is not a valid xml file");
          // System.exit(1);
        } else {
          xmlFileList.add(temp);
        }
      }
    }

    xmlFileList.forEach(aXmlFileList -> logger.info("Will be reading: " + aXmlFileList));
  }

  /*
   * Check if args1 exists and if so store the metric List FileName
   */
  private static void metricListFileName(String[] args) {
    if (args.length < 2) {
      logger.info("Expecting name of metricList as argumnet number 2");
      System.exit(1);
    }
    metricListFileName = args[1];

  }

  public static void printHelp() {
    printIntro();
    logger.info("There are two main modes of execution");
    logger.info("To execute the program the first argument should be one of these modes");
    logger.info("-metricList and -tables");
    logger.info("\n\n The -metricList mode");
    logger.info("The argument at location 1 should be name of a file where the list of metrics will be stored");
    logger.info("All arguments following argument 1 have to be xml files to be processed");
    logger.info(
        "If argument at location 2 is * then the current directory is searched and all xml files will be processed");

    logger.info("\n\n The -tables mode");
    logger.info("The argument at location 1 should be name of a file where the list of metrics are stored");
    logger.info("These metrics will become the COLUMNS in the tables created");
    logger.info("Argument at location 2 is the choice of aggregation");
    logger.info("\t -class for class level metrics");
    logger.info("\t -benchmark for benchmark level metrics");
    logger.info("Each xml file is considered to be a benchmark with a bunch of classes in it");

    logger.info("All arguments following argument 2 have to be xml files to be processed");
    logger.info(
        "If argument at location 3 is * then the current directory is searched and all xml files will be processed");
  }

  public static void printIntro() {
    logger.info("Welcome to the processData application");
    logger.info("The application is an xml document parser.");
    logger.info("Its primary aim is to create pretty tex tables");
  }

  public static void useHelp() {
    logger.info("Use the --help flag for more details");
  }

  /*
   * Read all the xml files from current directory into the xmlFileList arraylist
   */
  private static void readStar() {
    String curDir = System.getProperty("user.dir");
    logger.info("Current system directory is" + curDir);
    File dir = new File(curDir);

    String[] children = dir.list();
    if (children == null) {
      // Either dir does not exist or is not a directory
    } else {

      FilenameFilter filter = (File dir1, String name) -> name.endsWith(".xml");
      children = dir.list(filter);

      if (children != null) {
        for (String element : children) {
          xmlFileList.add(element);
        }
      }
    }
  }

  private static void writeMetricLists(PrintWriter out) {
    ArrayList<String> metricList = new ArrayList<>();

    Iterator<String> it = xmlFileList.iterator();
    while (it.hasNext()) {
      String fileName = it.next();
      try {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new File(fileName));
        logger.info("Retrieving Metric List from xml file: " + fileName);
        // normalize text representation
        doc.getDocumentElement().normalize();

        NodeList metrics = doc.getElementsByTagName("Metric");

        for (int s = 0; s < metrics.getLength(); s++) {
          Node metricNode = metrics.item(s);
          if (metricNode.getNodeType() == Node.ELEMENT_NODE) {

            Element metricElement = (Element) metricNode;
            NodeList metricName = metricElement.getElementsByTagName("MetricName");
            Element name = (Element) metricName.item(0);

            NodeList textFNList = name.getChildNodes();
            // System.out.println("MetricName: " + ((Node)textFNList.item(0)).getNodeValue().trim());
            if (!metricList.contains(((Node) textFNList.item(0)).getNodeValue().trim())) {
              metricList.add(((Node) textFNList.item(0)).getNodeValue().trim());
            }

          } // end of if clause
        } // end of for loop with s var
      } catch (SAXParseException err) {
        logger.info(new StringBuilder().append("** Parsing error").append(", line ").append(err.getLineNumber()).append(", uri ").append(err.getSystemId()).toString(), err);
        logger.info(" " + err.getMessage(), err);
      } catch (SAXException e) {
        Exception x = e.getException();
        ((x == null) ? e : x).printStackTrace();
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }

    it = metricList.iterator();
    while (it.hasNext()) {
      out.println(it.next());
    }
    logger.info(metricListFileName + " created.");
  }

  @SuppressWarnings("fallthrough")
  private static void generateMetricsTables() {

    Vector<String> columns = new Vector<>();

    /*
     * create the columns which are the metriclist
     */
    try {
      FileReader file = new FileReader(metricListFileName);
      BufferedReader fileInput = new BufferedReader(file);
      // System.out.print(text+"\t");
	// System.out.println("Columns");
	fileInput.lines().forEach(columns::add);
      fileInput.close();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
	logger.info("Exception while reading from metricList" + metricListFileName);
      System.exit(1);
    }

    Vector<String> allMetrics = new Vector<>();

    try {
      FileReader file = new FileReader("myList");
      BufferedReader fileInput = new BufferedReader(file);
      // System.out.print(text+"\t");
	// System.out.println("Columns");
	fileInput.lines().forEach(allMetrics::add);
      fileInput.close();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
	logger.info("Exception while reading from metricList" + metricListFileName);
      System.exit(1);
    }

    String newClassName = "";
    if (aggregationMechanism == ProcessData.BENCHMARK) {
      // TODO: create a metricListfileName.xml with metric info

      newClassName = metricListFileName;
      if (CSV) {
        newClassName += ".csv";
        logger.info(new StringBuilder().append("Creating csv file").append(newClassName).append(" from metrics info").toString());
      } else {
        newClassName += ".tex";
        logger.info(new StringBuilder().append("Creating tex file").append(newClassName).append(" from metrics info").toString());
      }

      bench = openWriteFile(newClassName);

      /*
       * For benchmarks we want to print xml files dealing with same benchmark in one table hence fft-enabled.xml
       * fft-disabled.xml should be in one table where as matrix-enabled.xml matrix-disabled.xml should be in another table
       */
      Map<String, List<String>> benchMarkToFiles = new HashMap<>();
      for (String fileName : xmlFileList) {
        if (fileName.indexOf('-') < 0) {
          logger.info("XML files should have following syntax:\n "
              + "<BENCHMARKNAME>-<PROPERTY>.xml\n PROPERTY should be enabled disabled etc");
          return;
        }
        String benchmark = fileName.substring(0, fileName.indexOf('-'));
        List<String> temp = benchMarkToFiles.get(benchmark);
        List<String> tempList = null;
        if (temp == null) {
          tempList = new ArrayList<>();
        } else {
          tempList = temp;
        }
        tempList.add(fileName);
        benchMarkToFiles.put(benchmark, tempList);

        /*
         * if csv check that xml files have proper "property names"
         */
        if (CSV) {
          if (fileName.indexOf('-') < 0 || fileName.lastIndexOf(".xml") < 0) {
            logger.info("XML files should have following syntax:\n <BENCHMARKNAME>-<PROPERTY>.xml\n "
                + "PROPERTY should be enabled disabled etc");
            return;
          }
          String xmlfileColumnType = fileName.substring(fileName.lastIndexOf('-') + 1, fileName.lastIndexOf(".xml"));
          logger.info("XML FILE COLUMN TYPE" + xmlfileColumnType);
          if ("Jad".equals(xmlfileColumnType) || "original".equals(xmlfileColumnType)
              || "SourceAgain".equals(xmlfileColumnType) || "disabled".equals(xmlfileColumnType)
              || "enabled".equals(xmlfileColumnType)) {

            // TODO will have to change this when dealin with obfuscator xml file names
          } else {
            throw new RuntimeException("XML FILE <property> not recognized");
          }
        }
      }

      if (CSV) {
        printCSVHeader(bench);
      }

      Iterator<String> keys = benchMarkToFiles.keySet().iterator();
      while (keys.hasNext()) {
        // each key gets its own table
        String key = keys.next();

        if (!CSV) {
          printTexTableHeader(bench, key, columns);
        }

        // go through each value which is an xml file
        List<String> tempValue = benchMarkToFiles.get(key);
        if (tempValue == null) {
          continue;
        }
        List<String> files = tempValue;

        /*
         * We want the files to be read in a specific order depending on decompiler or obfuscator
         */
        if (decompiler) {
          // coming from decompiler ordering is: original, Jad, SourceAgain, Dava(enabled), Dava(disabled)

          if (files.size() != 5) {
            throw new RuntimeException("not all xml files available for this benchmark!!");
          }
          logger.info("old order" + files.toString());
          String[] newFileOrder = new String[files.size()];
          for (String fileSort : files) {
            if (fileSort.contains("Jad")) {
              newFileOrder[1] = fileSort;
            } else if (fileSort.contains("original")) {
              newFileOrder[0] = fileSort;
            } else if (fileSort.contains("SourceAgain")) {
              newFileOrder[2] = fileSort;
            } else if (fileSort.contains("disabled")) {
              newFileOrder[4] = fileSort;
            } else if (fileSort.contains("enabled")) {
              newFileOrder[3] = fileSort;
            } else {
              throw new RuntimeException("property xml not correct");
            }

          }

          files = new ArrayList<>();
          files.add(newFileOrder[0]);
          files.add(newFileOrder[1]);
          files.add(newFileOrder[2]);
          files.add(newFileOrder[3]);
          files.add(newFileOrder[4]);
          logger.info("new order" + files.toString());
        } else {
          // coming from obfuscator ordering is: original, jbco enabled, jbco disabled, klassmaster enabled,klassmaster
          // disabled,

          if (files.size() != 5) {
            throw new RuntimeException("not all xml files available for this benchmark!!");
          }
          logger.info("old order" + files.toString());
          String[] newFileOrder = new String[files.size()];
          for (String fileSort : files) {
            if (fileSort.contains("original")) {
              newFileOrder[0] = fileSort;
            } else if (fileSort.contains("jbco-enabled")) {
              newFileOrder[1] = fileSort;
            } else if (fileSort.contains("jbco-disabled")) {
              newFileOrder[2] = fileSort;
            } else if (fileSort.contains("klassmaster-enabled")) {
              newFileOrder[3] = fileSort;
            } else if (fileSort.contains("klassmaster-disabled")) {
              newFileOrder[4] = fileSort;
            } else {
              throw new RuntimeException("property xml not correct");
            }
          }

          files = new ArrayList<>();
          files.add(newFileOrder[0]);
          files.add(newFileOrder[1]);
          files.add(newFileOrder[2]);
          files.add(newFileOrder[3]);
          files.add(newFileOrder[4]);
          logger.info("new order" + files.toString());

        }

        int count = -1;
        for (String fileName : files) {
          count++;
          try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(fileName));
            logger.info("Gethering metric info from from xml file: " + fileName);
            // normalize text representation

            if (!CSV) {
              // print the name of the xml file as the name of the benchmark
              if (fileName.endsWith(".xml")) {
                bench.print(fileName.substring(0, fileName.length() - 4));
              } else {
                bench.print(fileName);
              }
            }

            HashMap<String, Number> aggregatedValues = new HashMap<>();

            // TODO Should compute all metrics always
            // only print out the one we want
            Iterator<String> tempIt = allMetrics.iterator();
            while (tempIt.hasNext()) {
              aggregatedValues.put(tempIt.next(), Integer.valueOf(0));
            }

            int numClasses = aggregateXMLFileMetrics(doc, aggregatedValues);

            // at this point the hashmap contains aggregatedValue of all metrics

            // get the metrics we might need to divide
            Object myTemp = aggregatedValues.get("Total-Conditionals");
            if (myTemp == null) {
              logger.info("Total-Conditionals not found in aggregatedValues");
              System.exit(1);
            }
            double total_if_ifelse = ((Integer) myTemp).doubleValue();

            myTemp = aggregatedValues.get("Total Loops");
            if (myTemp == null) {
              logger.info("Total Loops not found in aggregatedValues");
              System.exit(1);
            }
            double totalLoops = ((Integer) myTemp).doubleValue();

            double totalConditional = total_if_ifelse + totalLoops;

            myTemp = aggregatedValues.get("AST-Node-Count");
            if (myTemp == null) {
              logger.info("AST-Node-Count not found in aggregatedValues");
              System.exit(1);
            }
            double astCount = ((Integer) myTemp).doubleValue();

            myTemp = aggregatedValues.get("NameCount");
            if (myTemp == null) {
              logger.info("NameCount not found in aggregatedValues");
              System.exit(1);
            }
            double nameCount = ((Double) myTemp).doubleValue();

            myTemp = aggregatedValues.get("Expr-Count");
            if (myTemp == null) {
              logger.info("ExprCount not found in aggregatedValues");
              System.exit(1);
            }
            double exprCount = ((Double) myTemp).doubleValue();

            tempIt = columns.iterator();
            while (tempIt.hasNext()) {
              String nexttempit = tempIt.next();
              Object temp = aggregatedValues.get(nexttempit);
              // System.out.println("NEXT TEMP IT ISSSSSSSSSSSSSSSSSSSSSS"+nexttempit);
              if (temp instanceof Integer) {
                int val = ((Integer) temp).intValue();
                if (CSV) {
                  switch (count) {
                    case 0:// original
                      bench.print(fileName.substring(0, fileName.indexOf('-')));
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                      if ("Total-Abrupt".equals(nexttempit)) {
                        // no averaging
                        bench.print("," + val);
                      } else if ("Total-Cond-Complexity".equals(nexttempit)) {
                        if (totalConditional != 0) {
                          // average by dividing total-cond-complexity for sum of if+ifelse+loops
                          logger.info("conditional complexit is" + val);
                          logger.info("totalConditionals are" + totalConditional);

                          bench.print("," + val / totalConditional);
                        } else if (val == 0) {
                          bench.print("," + val);
                        } else {
                          // val not 0 but toalconds are 0...not good
                          logger.info("Val not 0 but totalConditionals are zero!!!");
                          System.exit(1);
                        }

                      } else if ("D-W-Complexity".equals(nexttempit)) {
                        if (astCount != 0) {
                          // average by dividing D-W-Complexity by node count
                          bench.print("," + val / astCount);
                        } else if (val == 0) {
                          bench.print("," + val);
                        } else {
                          // val not 0 but astcount is 0...not good
                          logger.info("Val not 0 but astcount is zero!!!");
                          System.exit(1);
                        }

                      } else if ("Expr-Complexity".equals(nexttempit)) {

                        if (exprCount != 0) {
                          // average by dividing expr-complexity for exprCount
                          bench.print("," + val / exprCount);
                        } else if (val == 0) {
                          bench.print("," + val);
                        } else {
                          // val not 0 but expr-count are 0...not good
                          logger.info("Val not 0 but exprcount is zero!!!");
                          System.exit(1);
                        }

                      } else if ("Name-Complexity".equals(nexttempit)) {

                        if (nameCount != 0) {
                          // average by dividing name-complexity for nameCount
                          bench.print("," + val / nameCount);
                        } else if (val == 0) {
                          bench.print("," + val);
                        } else {
                          // val not 0 but name-count are 0...not good
                          logger.info("Val not 0 but name-count is zero!!!");
                          System.exit(1);
                        }
                      } else {
                        // labeled blocks, locals, if-ifelse, ASTNodeCount
                        bench.print("," + val);
                      }
                      break;
                    default:
                      logger.info("unhandled count value");
                      System.exit(1);
                  }

                } else {
                  // not CSV
                  bench.print("&" + val);
                }
              } else if (temp instanceof Double) {
                double val = ((Double) temp).doubleValue();
                if (CSV) {
                  switch (count) {
                    case 0:// original
                      bench.print(fileName.substring(0, fileName.indexOf('-')));
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                      if ("Total-Abrupt".equals(nexttempit)) {
                        // no averaging
                        bench.print("," + val);
                      } else if ("Total-Cond-Complexity".equals(nexttempit)) {
                        if (totalConditional != 0) {
                          // average by dividing total-cond-complexity for sum of if+ifelse+loops
                          logger.info("conditional complexit is" + val);
                          logger.info("totalConditionals are" + totalConditional);
                          bench.print("," + val / totalConditional);
                        } else if (val == 0) {
                          bench.print("," + val);
                        } else {
                          // val not 0 but toalconds are 0...not good
                          logger.info("Val not 0 but totalConditionals are zero!!!");
                          System.exit(1);
                        }

                      } else if ("D-W-Complexity".equals(nexttempit)) {
                        if (astCount != 0) {
                          // average by dividing D-W-Complexity by node count
                          bench.print("," + val / astCount);
                        } else if (val == 0) {
                          bench.print("," + val);
                        } else {
                          // val not 0 but astcount is 0...not good
                          logger.info("Val not 0 but astcount is zero!!!");
                          System.exit(1);
                        }

                      } else if ("Expr-Complexity".equals(nexttempit)) {

                        if (exprCount != 0) {
                          // average by dividing expr-complexity for exprCount
                          bench.print("," + val / exprCount);
                        } else if (val == 0) {
                          bench.print("," + val);
                        } else {
                          // val not 0 but expr-count are 0...not good
                          logger.info("Val not 0 but exprcount is zero!!!");
                          System.exit(1);
                        }

                      } else if ("Name-Complexity".equals(nexttempit)) {

                        if (nameCount != 0) {
                          // average by dividing name-complexity for nameCount
                          bench.print("," + val / nameCount);
                        } else if (val == 0) {
                          bench.print("," + val);
                        } else {
                          // val not 0 but name-count are 0...not good
                          logger.info("Val not 0 but name-count is zero!!!");
                          System.exit(1);
                        }
                      } else {
                        // labeled blocks, locals, if-ifelse, ASTNodeCount
                        bench.print("," + val);
                      }
                      break;
                    default:
                      logger.info("unhandled count value");
                      System.exit(1);
                  }

                } else {
                  bench.print("&" + val);
                }
              } else {
                throw new RuntimeException("Unknown type of object stored!!!");
              }
              if (CSV) {
                if (tempIt.hasNext()) {
                  logger.info("Only allowed one metric for CSV");
                  System.exit(1);
                }
              } else {
                if (tempIt.hasNext()) {
                  bench.print("   ");
                } else {
                  bench.println("\\\\");
                }
              }
            }

          } catch (SAXParseException err) {
            logger.info(new StringBuilder().append("** Parsing error").append(", line ").append(err.getLineNumber()).append(", uri ").append(err.getSystemId()).toString(), err);
            logger.info(" " + err.getMessage(), err);
          } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();
          } catch (Throwable t) {
            t.printStackTrace();
          }
        } // done with all files for this benchmark

        // print closing for the table for this benchmark
        if (CSV) {
          bench.println("");
        } else {
          printTexTableFooter(bench, "");
        }
      } // done with all benchmarks

      closeWriteFile(bench, newClassName);

    } else {

      for (String fileName : xmlFileList) {
        try {
          DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
          Document doc = docBuilder.parse(new File(fileName));
          logger.info("Gethering metric info from from xml file: " + fileName);
          // normalize text representation
          doc.getDocumentElement().normalize();

          if (aggregationMechanism == ProcessData.CLASS) {
            getClassMetrics(fileName, doc, columns);
          } else {
            logger.info("Unknown aggregation Mechanism");
            System.exit(1);
          }
        } catch (SAXParseException err) {
          logger.info(new StringBuilder().append("** Parsing error").append(", line ").append(err.getLineNumber()).append(", uri ").append(err.getSystemId()).toString(), err);
          logger.info(" " + err.getMessage(), err);
        } catch (SAXException e) {
          Exception x = e.getException();
          ((x == null) ? e : x).printStackTrace();
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }
    }
  }

  private static PrintWriter openWriteFile(String fileName) {
    PrintWriter writerOut;
    try {
      streamOut = new FileOutputStream(fileName);
      writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
	throw new CompilationDeathException("Cannot output file " + fileName);
    }
    return writerOut;
  }

  private static void closeWriteFile(PrintWriter writerOut, String fileName) {
    try {
      writerOut.flush();
      streamOut.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
	throw new CompilationDeathException("Cannot output file " + fileName);
    }
  }

  /*
   * This method is called for each xml class sent as input.
   *
   * Should read all of its metrics and add them to the aggregated values
   */
  private static int aggregateXMLFileMetrics(Document doc, HashMap<String, Number> aggregated) {

    NodeList classes = doc.getElementsByTagName("Class");
    int numClasses = classes.getLength();

    logger.info("NumClasses for this document are" + numClasses);

    NodeList metrics = doc.getElementsByTagName("Metric");

    for (int s = 0; s < metrics.getLength(); s++) {
      Node metricNode = metrics.item(s);
      if (metricNode.getNodeType() == Node.ELEMENT_NODE) {

        Element metricElement = (Element) metricNode;
        NodeList metricName = metricElement.getElementsByTagName("MetricName");
        Element name = (Element) metricName.item(0);

        NodeList textFNList = name.getChildNodes();
        String tempName = ((Node) textFNList.item(0)).getNodeValue().trim();

        Object tempObj = aggregated.get(tempName);
        if (tempObj == null) {
          // not found in hashmap so we dont care about this metric
          continue;
        }

        // We get to this point only if the metric is important

        NodeList value = metricElement.getElementsByTagName("Value");
        Element name1 = (Element) value.item(0);

        NodeList textFNList1 = name1.getChildNodes();
        String valToPrint = ((Node) textFNList1.item(0)).getNodeValue().trim();

        boolean notInt = false;
        try {
          int temp = Integer.parseInt(valToPrint);
          if (tempObj instanceof Integer) {
            Integer valSoFar = (Integer) tempObj;
            aggregated.put(tempName, Integer.valueOf(valSoFar.intValue() + temp));
          } else if (tempObj instanceof Double) {
            Double valSoFar = (Double) tempObj;
            aggregated.put(tempName, Double.valueOf(valSoFar.doubleValue() + temp));
          } else {
            throw new RuntimeException("\n\nobject type not found");
          }
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
		// temp was not an int
          notInt = true;
        }

        if (notInt) {
          // probably a double
          try {
            double temp = Double.parseDouble(valToPrint);
            if (tempObj instanceof Integer) {
              Integer valSoFar = (Integer) tempObj;
              aggregated.put(tempName, Double.valueOf(valSoFar.intValue() + temp));
            } else if (tempObj instanceof Double) {
              Double valSoFar = (Double) tempObj;
              aggregated.put(tempName, Double.valueOf(valSoFar.doubleValue() + temp));
            } else {
              throw new RuntimeException("\n\nobject type not found");
            }
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
			throw new RuntimeException("\n\n not an integer not a double unhandled!!!!");
          }
        }
      } // end of if metricNode is an element_Node
    } // end of for loop with s var
    return numClasses;
  }

  private static void getClassMetrics(String fileName, Document doc, Vector<String> columns) {
    // create a tex file with name (fileName - xml + tex)
    String newClassName = fileName;
    if (newClassName.endsWith(".xml")) {
      newClassName = newClassName.substring(0, newClassName.length() - 4);
    }

    newClassName += ".tex";

    logger.info(new StringBuilder().append("Creating tex file").append(newClassName).append(" from metrics info in file").append(fileName).toString());

    PrintWriter writerOut = openWriteFile(newClassName);
    printTexTableHeader(writerOut, "Classes", columns);

    /*
     * In order to print all class info alphabetically we will create a map of className to data to be displayed
     */
    ArrayList<String> classNames = new ArrayList<>();
    HashMap<String, String> classData = new HashMap<>();

    // each row is a class the name is obtained from the tag Class
    NodeList classes = doc.getElementsByTagName("Class");

    for (int cl = 0; cl < classes.getLength(); cl++) {
      // going through each class node
      Node classNode = classes.item(cl);
      if (classNode.getNodeType() == Node.ELEMENT_NODE) {

        Element classElement = (Element) classNode;

        /*
         * Class Name
         */
        NodeList classNameNodeList = classElement.getElementsByTagName("ClassName");
        Element classNameElement = (Element) classNameNodeList.item(0);

        NodeList classNameTextFNList = classNameElement.getChildNodes();
        String className = ((Node) classNameTextFNList.item(0)).getNodeValue().trim();

        // writerOut.println("");

        className = className.replace('_', '-');
        if (className.length() > CLASSNAMESIZE) {
          // writerOut.print(className.substring(0,CLASSNAMESIZE)+" ");
          className = className.substring(0, CLASSNAMESIZE);
          classNames.add(className);
        } else {
          // writerOut.print(className+" ");
          classNames.add(className);
        }

        logger.info("\nclassName " + className);

        String data = "   ";

        /*
         * Metrics
         */
        NodeList metrics = classElement.getElementsByTagName("Metric");

        int columnIndex = 0; // which one we are printing right now

        for (int s = 0; s < metrics.getLength() && columnIndex < columns.size(); s++) {
          Node metricNode = metrics.item(s);
          if (metricNode.getNodeType() == Node.ELEMENT_NODE) {

            Element metricElement = (Element) metricNode;
            NodeList metricName = metricElement.getElementsByTagName("MetricName");
            Element name = (Element) metricName.item(0);

            NodeList textFNList = name.getChildNodes();
            String tempName = ((Node) textFNList.item(0)).getNodeValue().trim();

            /*
             * If the name of this metric is not the next column name in the columns simply skip over it and continue
             */
            if (!tempName.equals(columns.elementAt(columnIndex))) {
              // System.out.println("here");
              continue;
            }

            // We get to this point only if the metric name is the same as the column name

            NodeList value = metricElement.getElementsByTagName("Value");
            Element name1 = (Element) value.item(0);

            NodeList textFNList1 = name1.getChildNodes();
            String valToPrint = ((Node) textFNList1.item(0)).getNodeValue().trim();
            logger.info(" " + valToPrint);
            // writerOut.print("&"+valToPrint);
            data += "&" + valToPrint;

            columnIndex++;
            if (columns.size() > columnIndex) {
              // writerOut.print(" ");
              data += "   ";
            } else {
              // writerOut.println("\\\\");
              data += "\\\\";
            }

          } // end of if metricNode is an element_Node
        } // end of for loop with s var

        classData.put(className, data);

      } // end of if classNode is an element_Node
    } // end of for loop with cl

    // writerOut.println();
    // writerOut.println();
    // writerOut.println();

    Collections.sort(classNames);

    classNames.forEach(className -> {
      String data = classData.get(className);
      writerOut.print(className);
      writerOut.println(data);
    });
    printTexTableFooter(writerOut, fileName);

    closeWriteFile(writerOut, metricListFileName);
  }

  private static void printTexTableFooter(PrintWriter out, String tableCaption) {
    out.println("");
    out.println("\\hline");
    out.println("\\end{tabular}");
    out.println(new StringBuilder().append("\\caption{ ...").append(tableCaption).append("..... }").toString());
    out.println("\\end{table}");
    // out.println("\\end{figure}");
  }

  /*
   * Prints the name of??
   */
  private static void printCSVHeader(PrintWriter out) {
    if (decompiler) {
      out.println(",Original,Jad,SourceAgain,Dava(enabled),Dava(disabled)");
    } else {
      out.println(",Original,JBCO-enabled,JBCO-disabled,klassmaster-enabled,klassmaster-disabled");
    }
  }

  private static void printTexTableHeader(PrintWriter out, String rowHeading, Vector<String> columns) {
    // out.println("\\begin{figure}[hbtp]");
    out.println("\\begin{table}[hbtp]");
    out.print("\\begin{tabular}{");

    for (int i = 0; i <= columns.size(); i++) {
      out.print("|l");
    }

    out.println("|}");
    out.println("\\hline");

    out.print(rowHeading + "   ");

    Iterator<String> it = columns.iterator();
    while (it.hasNext()) {
      out.print("&" + it.next());
      if (it.hasNext()) {
        out.print("   ");
      }
    }
    out.println("\\\\");

    out.println("\\hline");
  }
}
