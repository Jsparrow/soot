package soot.options;

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

/* THIS FILE IS AUTO-GENERATED FROM soot_options.xml. DO NOT MODIFY. */

import soot.*;
import java.util.*;

/**
 * Soot command-line options parser.
 *
 * @author Ondrej Lhotak
 */
@javax.annotation.Generated(value = "Saxonica v3.0", comments = "from soot_options.xml")
public class Options extends OptionsBase {

    public static final int src_prec_c = 1;
	public static final int src_prec_class = 1;
	public static final int src_prec_only_class = 2;
	public static final int src_prec_J = 3;
	public static final int src_prec_jimple = 3;
	public static final int src_prec_java = 4;
	public static final int src_prec_apk = 5;
	public static final int src_prec_apk_class_jimple = 6;
	public static final int src_prec_apk_c_j = 6;
	public static final int output_format_J = 1;
	public static final int output_format_jimple = 1;
	public static final int output_format_j = 2;
	public static final int output_format_jimp = 2;
	public static final int output_format_S = 3;
	public static final int output_format_shimple = 3;
	public static final int output_format_s = 4;
	public static final int output_format_shimp = 4;
	public static final int output_format_B = 5;
	public static final int output_format_baf = 5;
	public static final int output_format_b = 6;
	public static final int output_format_G = 7;
	public static final int output_format_grimple = 7;
	public static final int output_format_g = 8;
	public static final int output_format_grimp = 8;
	public static final int output_format_X = 9;
	public static final int output_format_xml = 9;
	public static final int output_format_dex = 10;
	public static final int output_format_force_dex = 11;
	public static final int output_format_n = 12;
	public static final int output_format_none = 12;
	public static final int output_format_jasmin = 13;
	public static final int output_format_c = 14;
	public static final int output_format_class = 14;
	public static final int output_format_d = 15;
	public static final int output_format_dava = 15;
	public static final int output_format_t = 16;
	public static final int output_format_template = 16;
	public static final int output_format_a = 17;
	public static final int output_format_asm = 17;
	public static final int java_version_default = 1;
	public static final int java_version_1_1 = 2;
	public static final int java_version_1 = 2;
	public static final int java_version_1_2 = 3;
	public static final int java_version_2 = 3;
	public static final int java_version_1_3 = 4;
	public static final int java_version_3 = 4;
	public static final int java_version_1_4 = 5;
	public static final int java_version_4 = 5;
	public static final int java_version_1_5 = 6;
	public static final int java_version_5 = 6;
	public static final int java_version_1_6 = 7;
	public static final int java_version_6 = 7;
	public static final int java_version_1_7 = 8;
	public static final int java_version_7 = 8;
	public static final int java_version_1_8 = 9;
	public static final int java_version_8 = 9;
	public static final int java_version_1_9 = 10;
	public static final int java_version_9 = 10;
	public static final int wrong_staticness_fail = 1;
	public static final int wrong_staticness_ignore = 2;
	public static final int wrong_staticness_fix = 3;
	public static final int wrong_staticness_fixstrict = 4;
	public static final int field_type_mismatches_fail = 1;
	public static final int field_type_mismatches_ignore = 2;
	public static final int field_type_mismatches_null = 3;
	public static final int throw_analysis_pedantic = 1;
	public static final int throw_analysis_unit = 2;
	public static final int throw_analysis_dalvik = 3;
	public static final int check_init_throw_analysis_auto = 1;
	public static final int check_init_throw_analysis_pedantic = 2;
	public static final int check_init_throw_analysis_unit = 3;
	public static final int check_init_throw_analysis_dalvik = 4;
	private boolean coffi = false;
	private boolean jasminBackend = false;
	private boolean help = false;
	private boolean phaseList = false;
	private List<String> phaseHelp = null;
	private boolean version = false;
	private boolean verbose = false;
	private boolean interactiveMode = false;
	private boolean unfriendlyMode = false;
	private boolean app = false;
	private boolean wholeProgram = false;
	private boolean wholeShimple = false;
	private boolean onTheFly = false;
	private boolean validate = false;
	private boolean debug = false;
	private boolean debugResolver = false;
	private boolean ignoreResolvingLevels = false;
	private String sootClasspath = "";
	private boolean prependClasspath = false;
	private boolean ignoreClasspathErrors = false;
	private boolean processMultipleDex = false;
	private boolean searchDexInArchives = false;
	private List<String> processDir = null;
	private boolean oaat = false;
	private String androidJars = "";
	private String forceAndroidJar = "";
	private int androidApiVersion = -1;
	private boolean astMetrics = false;
	private int srcPrec = 0;
	private boolean fullResolver = false;
	private boolean allowPhantomRefs = false;
	private boolean allowPhantomElms = false;
	private boolean noBodiesForExcluded = false;
	private boolean j2me = false;
	private String mainClass = "";
	private boolean polyglot = false;
	private boolean permissiveResolving = false;
	private boolean dropBodiesAfterLoad = true;
	private String outputDir = "";
	private int outputFormat = 0;
	private int javaVersion = 0;
	private boolean outputJar = false;
	private boolean hierarchyDirs = false;
	private boolean xmlAttributes = false;
	private boolean printTagsInOutput = false;
	private boolean noOutputSourceFileAttribute = false;
	private boolean noOutputInnerClassesAttribute = false;
	private List<String> dumpBody = null;
	private List<String> dumpCfg = null;
	private boolean showExceptionDests = true;
	private boolean gzip = false;
	private boolean forceOverwrite = false;
	private List<String> plugin = null;
	private int wrongStaticness = 0;
	private int fieldTypeMismatches = 0;
	private boolean viaGrimp = false;
	private boolean viaShimple = false;
	private int throwAnalysis = 0;
	private int checkInitThrowAnalysis = 0;
	private boolean omitExceptingUnitEdges = false;
	private boolean ignoreResolutionErrors = false;
	private List<String> include = null;
	private List<String> exclude = null;
	private boolean includeAll = false;
	private List<String> dynamicClass = null;
	private List<String> dynamicDir = null;
	private List<String> dynamicPackage = null;
	private boolean keepLineNumber = false;
	private boolean keepOffset = false;
	private boolean writeLocalAnnotations = false;
	private boolean time = false;
	private boolean subtractGc = false;
	private boolean noWriteoutBodyReleasing = false;

	public Options(Singletons.Global g) {
    }

	public static Options v() {
        return G.v().soot_options_Options();
    }

	@SuppressWarnings("unused")
    public boolean parse(String[] argv) {
        List<String> phaseOptions = new LinkedList<>();

        for(int i = argv.length; i > 0; i--) {
            pushOption(argv[i-1]);
        }

        while(hasMoreOptions()) {
            String option = nextOption();

            if(option.charAt(0) != '-') {
                classes.add(option);
                continue;
            }

            while(option.charAt(0) == '-') {
                option = option.substring(1);
            }

            if (false) {
			} else if (false
                    || "coffi".equals(option)
            ) {
				coffi = true;
			} else if (false
                    || "jasmin-backend".equals(option)
            ) {
				jasminBackend = true;
			} else if (false
                    || "h".equals(option)
                    || "help".equals(option)
            ) {
				help = true;
			} else if (false
                    || "pl".equals(option)
                    || "phase-list".equals(option)
            ) {
				phaseList = true;
			} else if (false
                    || "ph".equals(option)
                    || "phase-help".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (phaseHelp == null) {
					phaseHelp = new LinkedList<>();
				}
                phaseHelp.add(value);
            }
            else if (false
                    || "version".equals(option)
            ) {
				version = true;
			} else if (false
                    || "v".equals(option)
                    || "verbose".equals(option)
            ) {
				verbose = true;
			} else if (false
                    || "interactive-mode".equals(option)
            ) {
				interactiveMode = true;
			} else if (false
                    || "unfriendly-mode".equals(option)
            ) {
				unfriendlyMode = true;
			} else if (false
                    || "app".equals(option)
            ) {
				app = true;
			} else if (false
                    || "w".equals(option)
                    || "whole-program".equals(option)
            ) {
				wholeProgram = true;
			} else if (false
                    || "ws".equals(option)
                    || "whole-shimple".equals(option)
            ) {
				wholeShimple = true;
			} else if (false
                    || "fly".equals(option)
                    || "on-the-fly".equals(option)
            ) {
				onTheFly = true;
			} else if (false
                    || "validate".equals(option)
            ) {
				validate = true;
			} else if (false
                    || "debug".equals(option)
            ) {
				debug = true;
			} else if (false
                    || "debug-resolver".equals(option)
            ) {
				debugResolver = true;
			} else if (false
                    || "ignore-resolving-levels".equals(option)
            ) {
				ignoreResolvingLevels = true;
			} else if (false
                    || "cp".equals(option)
                    || "soot-class-path".equals(option)
                    || "soot-classpath".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (sootClasspath.isEmpty()) {
					sootClasspath = value;
				} else {
                    G.v().out.println(new StringBuilder().append("Duplicate values ").append(sootClasspath).append(" and ").append(value).append(" for option -").append(option)
							.toString());
                    return false;
                }
            }
            else if (false
                    || "pp".equals(option)
                    || "prepend-classpath".equals(option)
            ) {
				prependClasspath = true;
			} else if (false
                    || "ice".equals(option)
                    || "ignore-classpath-errors".equals(option)
            ) {
				ignoreClasspathErrors = true;
			} else if (false
                    || "process-multiple-dex".equals(option)
            ) {
				processMultipleDex = true;
			} else if (false
                    || "search-dex-in-archives".equals(option)
            ) {
				searchDexInArchives = true;
			} else if (false
                    || "process-path".equals(option)
                    || "process-dir".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (processDir == null) {
					processDir = new LinkedList<>();
				}
                processDir.add(value);
            }
            else if (false
                    || "oaat".equals(option)
            ) {
				oaat = true;
			} else if (false
                    || "android-jars".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (androidJars.isEmpty()) {
					androidJars = value;
				} else {
                    G.v().out.println(new StringBuilder().append("Duplicate values ").append(androidJars).append(" and ").append(value).append(" for option -").append(option)
							.toString());
                    return false;
                }
            }
            else if (false
                    || "force-android-jar".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (forceAndroidJar.isEmpty()) {
					forceAndroidJar = value;
				} else {
                    G.v().out.println(new StringBuilder().append("Duplicate values ").append(forceAndroidJar).append(" and ").append(value).append(" for option -").append(option)
							.toString());
                    return false;
                }
            }
            else if (false
                || "android-api-version".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if(androidApiVersion == -1) {
					androidApiVersion = Integer.valueOf(value);
				} else {
                    G.v().out.println(new StringBuilder().append("Duplicate values ").append(androidApiVersion).append(" and ").append(value).append(" for option -").append(option)
							.toString());
                    return false;
                }
            }
            else if (false
                    || "ast-metrics".equals(option)
            ) {
				astMetrics = true;
			} else if (false
                    || "src-prec".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
        
                if (false) {
				} else if (false
                        || "c".equals(value)
                        || "class".equals(value)
                ) {
                    if (srcPrec != 0 && srcPrec != src_prec_class) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    srcPrec = src_prec_class;
                }
                else if (false
                        || "only-class".equals(value)
                ) {
                    if (srcPrec != 0 && srcPrec != src_prec_only_class) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    srcPrec = src_prec_only_class;
                }
                else if (false
                        || "J".equals(value)
                        || "jimple".equals(value)
                ) {
                    if (srcPrec != 0 && srcPrec != src_prec_jimple) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    srcPrec = src_prec_jimple;
                }
                else if (false
                        || "java".equals(value)
                ) {
                    if (srcPrec != 0 && srcPrec != src_prec_java) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    srcPrec = src_prec_java;
                }
                else if (false
                        || "apk".equals(value)
                ) {
                    if (srcPrec != 0 && srcPrec != src_prec_apk) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    srcPrec = src_prec_apk;
                }
                else if (false
                        || "apk-class-jimple".equals(value)
                        || "apk-c-j".equals(value)
                ) {
                    if (srcPrec != 0 && srcPrec != src_prec_apk_c_j) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    srcPrec = src_prec_apk_c_j;
                }
                else {
                    G.v().out.println(String.format("Invalid value %s given for option -%s", option, value));
                    return false;
                }
            }
            else if (false
                    || "full-resolver".equals(option)
            ) {
				fullResolver = true;
			} else if (false
                    || "allow-phantom-refs".equals(option)
            ) {
				allowPhantomRefs = true;
			} else if (false
                    || "allow-phantom-elms".equals(option)
            ) {
				allowPhantomElms = true;
			} else if (false
                    || "no-bodies-for-excluded".equals(option)
            ) {
				noBodiesForExcluded = true;
			} else if (false
                    || "j2me".equals(option)
            ) {
				j2me = true;
			} else if (false
                    || "main-class".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (mainClass.isEmpty()) {
					mainClass = value;
				} else {
                    G.v().out.println(new StringBuilder().append("Duplicate values ").append(mainClass).append(" and ").append(value).append(" for option -").append(option)
							.toString());
                    return false;
                }
            }
            else if (false
                    || "polyglot".equals(option)
            ) {
				polyglot = true;
			} else if (false
                    || "permissive-resolving".equals(option)
            ) {
				permissiveResolving = true;
			} else if (false
                    || "no-drop-bodies-after-load".equals(option)
            ) {
				dropBodiesAfterLoad = false;
			} else if (false
                    || "d".equals(option)
                    || "output-dir".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (outputDir.isEmpty()) {
					outputDir = value;
				} else {
                    G.v().out.println(new StringBuilder().append("Duplicate values ").append(outputDir).append(" and ").append(value).append(" for option -").append(option)
							.toString());
                    return false;
                }
            }
            else if (false
                    || "f".equals(option)
                    || "output-format".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
        
                if (false) {
				} else if (false
                        || "J".equals(value)
                        || "jimple".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_jimple) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_jimple;
                }
                else if (false
                        || "j".equals(value)
                        || "jimp".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_jimp) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_jimp;
                }
                else if (false
                        || "S".equals(value)
                        || "shimple".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_shimple) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_shimple;
                }
                else if (false
                        || "s".equals(value)
                        || "shimp".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_shimp) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_shimp;
                }
                else if (false
                        || "B".equals(value)
                        || "baf".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_baf) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_baf;
                }
                else if (false
                        || "b".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_b) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_b;
                }
                else if (false
                        || "G".equals(value)
                        || "grimple".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_grimple) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_grimple;
                }
                else if (false
                        || "g".equals(value)
                        || "grimp".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_grimp) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_grimp;
                }
                else if (false
                        || "X".equals(value)
                        || "xml".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_xml) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_xml;
                }
                else if (false
                        || "dex".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_dex) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_dex;
                }
                else if (false
                        || "force-dex".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_force_dex) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_force_dex;
                }
                else if (false
                        || "n".equals(value)
                        || "none".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_none) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_none;
                }
                else if (false
                        || "jasmin".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_jasmin) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_jasmin;
                }
                else if (false
                        || "c".equals(value)
                        || "class".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_class) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_class;
                }
                else if (false
                        || "d".equals(value)
                        || "dava".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_dava) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_dava;
                }
                else if (false
                        || "t".equals(value)
                        || "template".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_template) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_template;
                }
                else if (false
                        || "a".equals(value)
                        || "asm".equals(value)
                ) {
                    if (outputFormat != 0 && outputFormat != output_format_asm) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    outputFormat = output_format_asm;
                }
                else {
                    G.v().out.println(String.format("Invalid value %s given for option -%s", option, value));
                    return false;
                }
            }
            else if (false
                    || "java-version".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
        
                if (false) {
				} else if (false
                        || "default".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_default) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_default;
                }
                else if (false
                        || "1.1".equals(value)
                        || "1".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_1) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_1;
                }
                else if (false
                        || "1.2".equals(value)
                        || "2".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_2) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_2;
                }
                else if (false
                        || "1.3".equals(value)
                        || "3".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_3) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_3;
                }
                else if (false
                        || "1.4".equals(value)
                        || "4".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_4) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_4;
                }
                else if (false
                        || "1.5".equals(value)
                        || "5".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_5) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_5;
                }
                else if (false
                        || "1.6".equals(value)
                        || "6".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_6) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_6;
                }
                else if (false
                        || "1.7".equals(value)
                        || "7".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_7) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_7;
                }
                else if (false
                        || "1.8".equals(value)
                        || "8".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_8) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_8;
                }
                else if (false
                        || "1.9".equals(value)
                        || "9".equals(value)
                ) {
                    if (javaVersion != 0 && javaVersion != java_version_9) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    javaVersion = java_version_9;
                }
                else {
                    G.v().out.println(String.format("Invalid value %s given for option -%s", option, value));
                    return false;
                }
            }
            else if (false
                    || "outjar".equals(option)
                    || "output-jar".equals(option)
            ) {
				outputJar = true;
			} else if (false
                    || "hierarchy-dirs".equals(option)
            ) {
				hierarchyDirs = true;
			} else if (false
                    || "xml-attributes".equals(option)
            ) {
				xmlAttributes = true;
			} else if (false
                    || "print-tags".equals(option)
                    || "print-tags-in-output".equals(option)
            ) {
				printTagsInOutput = true;
			} else if (false
                    || "no-output-source-file-attribute".equals(option)
            ) {
				noOutputSourceFileAttribute = true;
			} else if (false
                    || "no-output-inner-classes-attribute".equals(option)
            ) {
				noOutputInnerClassesAttribute = true;
			} else if (false
                    || "dump-body".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (dumpBody == null) {
					dumpBody = new LinkedList<>();
				}
                dumpBody.add(value);
            }
            else if (false
                    || "dump-cfg".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (dumpCfg == null) {
					dumpCfg = new LinkedList<>();
				}
                dumpCfg.add(value);
            }
            else if (false
                    || "no-show-exception-dests".equals(option)
            ) {
				showExceptionDests = false;
			} else if (false
                    || "gzip".equals(option)
            ) {
				gzip = true;
			} else if (false
                    || "force-overwrite".equals(option)
            ) {
				forceOverwrite = true;
			} else if (false
                    || "plugin".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (plugin == null) {
					plugin = new LinkedList<>();
				}
                plugin.add(value);
                if (!loadPluginConfiguration(value)) {
                    G.v().out.println("Failed to load plugin " + value);
                    return false;
                }
        
            }
            else if (false
                    || "wrong-staticness".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
        
                if (false) {
				} else if (false
                        || "fail".equals(value)
                ) {
                    if (wrongStaticness != 0 && wrongStaticness != wrong_staticness_fail) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    wrongStaticness = wrong_staticness_fail;
                }
                else if (false
                        || "ignore".equals(value)
                ) {
                    if (wrongStaticness != 0 && wrongStaticness != wrong_staticness_ignore) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    wrongStaticness = wrong_staticness_ignore;
                }
                else if (false
                        || "fix".equals(value)
                ) {
                    if (wrongStaticness != 0 && wrongStaticness != wrong_staticness_fix) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    wrongStaticness = wrong_staticness_fix;
                }
                else if (false
                        || "fixstrict".equals(value)
                ) {
                    if (wrongStaticness != 0 && wrongStaticness != wrong_staticness_fixstrict) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    wrongStaticness = wrong_staticness_fixstrict;
                }
                else {
                    G.v().out.println(String.format("Invalid value %s given for option -%s", option, value));
                    return false;
                }
            }
            else if (false
                    || "field-type-mismatches".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
        
                if (false) {
				} else if (false
                        || "fail".equals(value)
                ) {
                    if (fieldTypeMismatches != 0 && fieldTypeMismatches != field_type_mismatches_fail) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    fieldTypeMismatches = field_type_mismatches_fail;
                }
                else if (false
                        || "ignore".equals(value)
                ) {
                    if (fieldTypeMismatches != 0 && fieldTypeMismatches != field_type_mismatches_ignore) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    fieldTypeMismatches = field_type_mismatches_ignore;
                }
                else if (false
                        || "null".equals(value)
                ) {
                    if (fieldTypeMismatches != 0 && fieldTypeMismatches != field_type_mismatches_null) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    fieldTypeMismatches = field_type_mismatches_null;
                }
                else {
                    G.v().out.println(String.format("Invalid value %s given for option -%s", option, value));
                    return false;
                }
            }
            else if (false
                || "p".equals(option)
                || "phase-option".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No phase name given for option -" + option);
                    return false;
                }
                String phaseName = nextOption();
                if (!hasMoreOptions()) {
                    G.v().out.println(new StringBuilder().append("No phase option given for option -").append(option).append(" ").append(phaseName).toString());
                    return false;
                }
                String phaseOption = nextOption();
        
                phaseOptions.add(phaseName);
                phaseOptions.add(phaseOption);
            }
            else if (false
                || "O".equals(option)
                || "optimize".equals(option)
            ) {
                pushOption("enabled:true");
                pushOption("sop");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("jop");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("gop");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("bop");
                pushOption("-p");
                pushOption("only-stack-locals:false");
                pushOption("gb.a2");
                pushOption("-p");
                pushOption("only-stack-locals:false");
                pushOption("gb.a1");
                pushOption("-p");
            }
            else if (false
                || "W".equals(option)
                || "whole-optimize".equals(option)
            ) {
                pushOption("-O");
                pushOption("-w");
                pushOption("enabled:true");
                pushOption("wsop");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("wjop");
                pushOption("-p");
            }
            else if (false
                    || "via-grimp".equals(option)
            ) {
				viaGrimp = true;
			} else if (false
                    || "via-shimple".equals(option)
            ) {
				viaShimple = true;
			} else if (false
                    || "throw-analysis".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
        
                if (false) {
				} else if (false
                        || "pedantic".equals(value)
                ) {
                    if (throwAnalysis != 0 && throwAnalysis != throw_analysis_pedantic) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    throwAnalysis = throw_analysis_pedantic;
                }
                else if (false
                        || "unit".equals(value)
                ) {
                    if (throwAnalysis != 0 && throwAnalysis != throw_analysis_unit) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    throwAnalysis = throw_analysis_unit;
                }
                else if (false
                        || "dalvik".equals(value)
                ) {
                    if (throwAnalysis != 0 && throwAnalysis != throw_analysis_dalvik) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    throwAnalysis = throw_analysis_dalvik;
                }
                else {
                    G.v().out.println(String.format("Invalid value %s given for option -%s", option, value));
                    return false;
                }
            }
            else if (false
                    || "check-init-ta".equals(option)
                    || "check-init-throw-analysis".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
        
                if (false) {
				} else if (false
                        || "auto".equals(value)
                ) {
                    if (checkInitThrowAnalysis != 0 && checkInitThrowAnalysis != check_init_throw_analysis_auto) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    checkInitThrowAnalysis = check_init_throw_analysis_auto;
                }
                else if (false
                        || "pedantic".equals(value)
                ) {
                    if (checkInitThrowAnalysis != 0 && checkInitThrowAnalysis != check_init_throw_analysis_pedantic) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    checkInitThrowAnalysis = check_init_throw_analysis_pedantic;
                }
                else if (false
                        || "unit".equals(value)
                ) {
                    if (checkInitThrowAnalysis != 0 && checkInitThrowAnalysis != check_init_throw_analysis_unit) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    checkInitThrowAnalysis = check_init_throw_analysis_unit;
                }
                else if (false
                        || "dalvik".equals(value)
                ) {
                    if (checkInitThrowAnalysis != 0 && checkInitThrowAnalysis != check_init_throw_analysis_dalvik) {
                        G.v().out.println("Multiple values given for option " + option);
                        return false;
                    }
                    checkInitThrowAnalysis = check_init_throw_analysis_dalvik;
                }
                else {
                    G.v().out.println(String.format("Invalid value %s given for option -%s", option, value));
                    return false;
                }
            }
            else if (false
                    || "omit-excepting-unit-edges".equals(option)
            ) {
				omitExceptingUnitEdges = true;
			} else if (false
                || "trim-cfgs".equals(option)
            ) {
                pushOption("enabled:true");
                pushOption("jb.tt");
                pushOption("-p");
                pushOption("-omit-excepting-unit-edges");
                pushOption("unit");
                pushOption("-throw-analysis");
            }
            else if (false
                    || "ire".equals(option)
                    || "ignore-resolution-errors".equals(option)
            ) {
				ignoreResolutionErrors = true;
			} else if (false
                    || "i".equals(option)
                    || "include".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (include == null) {
					include = new LinkedList<>();
				}
                include.add(value);
            }
            else if (false
                    || "x".equals(option)
                    || "exclude".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (exclude == null) {
					exclude = new LinkedList<>();
				}
                exclude.add(value);
            }
            else if (false
                    || "include-all".equals(option)
            ) {
				includeAll = true;
			} else if (false
                    || "dynamic-class".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (dynamicClass == null) {
					dynamicClass = new LinkedList<>();
				}
                dynamicClass.add(value);
            }
            else if (false
                    || "dynamic-dir".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (dynamicDir == null) {
					dynamicDir = new LinkedList<>();
				}
                dynamicDir.add(value);
            }
            else if (false
                    || "dynamic-package".equals(option)
            ) {
                if (!hasMoreOptions()) {
                    G.v().out.println("No value given for option -" + option);
                    return false;
                }

                String value = nextOption();
                if (dynamicPackage == null) {
					dynamicPackage = new LinkedList<>();
				}
                dynamicPackage.add(value);
            }
            else if (false
                    || "keep-line-number".equals(option)
            ) {
				keepLineNumber = true;
			} else if (false
                    || "keep-bytecode-offset".equals(option)
                    || "keep-offset".equals(option)
            ) {
				keepOffset = true;
			} else if (false
                    || "write-local-annotations".equals(option)
            ) {
				writeLocalAnnotations = true;
			} else if (false
                || "annot-purity".equals(option)
            ) {
                pushOption("enabled:true");
                pushOption("wjap.purity");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("cg.spark");
                pushOption("-p");
                pushOption("-w");
            }
            else if (false
                || "annot-nullpointer".equals(option)
            ) {
                pushOption("enabled:true");
                pushOption("tag.an");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("jap.npc");
                pushOption("-p");
            }
            else if (false
                || "annot-arraybounds".equals(option)
            ) {
                pushOption("enabled:true");
                pushOption("tag.an");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("jap.abc");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("wjap.ra");
                pushOption("-p");
            }
            else if (false
                || "annot-side-effect".equals(option)
            ) {
                pushOption("enabled:true");
                pushOption("tag.dep");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("jap.sea");
                pushOption("-p");
                pushOption("-w");
            }
            else if (false
                || "annot-fieldrw".equals(option)
            ) {
                pushOption("enabled:true");
                pushOption("tag.fieldrw");
                pushOption("-p");
                pushOption("enabled:true");
                pushOption("jap.fieldrw");
                pushOption("-p");
                pushOption("-w");
            }
            else if (false
                    || "time".equals(option)
            ) {
				time = true;
			} else if (false
                    || "subtract-gc".equals(option)
            ) {
				subtractGc = true;
			} else if (false
                    || "no-writeout-body-releasing".equals(option)
            ) {
				noWriteoutBodyReleasing = true;
			} else {
                G.v().out.println("Invalid option -" + option);
                return false;
            }
        }

        Iterator<String> it = phaseOptions.iterator();
        while(it.hasNext()) {
            String phaseName = it.next();
            String phaseOption = it.next();
            if(!setPhaseOption(phaseName, "enabled:true")) {
				return false;
			}
        }

        it = phaseOptions.iterator();
        while(it.hasNext()) {
            String phaseName = it.next();
            String phaseOption = it.next();
            if(!setPhaseOption(phaseName, phaseOption)) {
				return false;
			}
        }

        return true;
    }

	public boolean coffi() { return coffi; }

	public void set_coffi(boolean setting) { coffi = setting; }

	public boolean jasmin_backend() { return jasminBackend; }

	public void set_jasmin_backend(boolean setting) { jasminBackend = setting; }

	public boolean help() { return help; }

	public void set_help(boolean setting) { help = setting; }

	public boolean phase_list() { return phaseList; }

	public void set_phase_list(boolean setting) { phaseList = setting; }

	public List<String> phase_help() {
        return phaseHelp == null ? Collections.emptyList() : phaseHelp;
    }

	public void set_phase_help(List<String> setting) { phaseHelp = setting; }

	public boolean version() { return version; }

	public void set_version(boolean setting) { version = setting; }

	public boolean verbose() { return verbose; }

	public void set_verbose(boolean setting) { verbose = setting; }

	public boolean interactive_mode() { return interactiveMode; }

	public void set_interactive_mode(boolean setting) { interactiveMode = setting; }

	public boolean unfriendly_mode() { return unfriendlyMode; }

	public void set_unfriendly_mode(boolean setting) { unfriendlyMode = setting; }

	public boolean app() { return app; }

	public void set_app(boolean setting) { app = setting; }

	public boolean whole_program() { return wholeProgram; }

	public void set_whole_program(boolean setting) { wholeProgram = setting; }

	public boolean whole_shimple() { return wholeShimple; }

	public void set_whole_shimple(boolean setting) { wholeShimple = setting; }

	public boolean on_the_fly() { return onTheFly; }

	public void set_on_the_fly(boolean setting) { onTheFly = setting; }

	public boolean validate() { return validate; }

	public void set_validate(boolean setting) { validate = setting; }

	public boolean debug() { return debug; }

	public void set_debug(boolean setting) { debug = setting; }

	public boolean debug_resolver() { return debugResolver; }

	public void set_debug_resolver(boolean setting) { debugResolver = setting; }

	public boolean ignore_resolving_levels() { return ignoreResolvingLevels; }

	public void set_ignore_resolving_levels(boolean setting) { ignoreResolvingLevels = setting; }

	public String soot_classpath() { return sootClasspath; }

	public void set_soot_classpath(String setting) { sootClasspath = setting; }

	public boolean prepend_classpath() { return prependClasspath; }

	public void set_prepend_classpath(boolean setting) { prependClasspath = setting; }

	public boolean ignore_classpath_errors() { return ignoreClasspathErrors; }

	public void set_ignore_classpath_errors(boolean setting) { ignoreClasspathErrors = setting; }

	public boolean process_multiple_dex() { return processMultipleDex; }

	public void set_process_multiple_dex(boolean setting) { processMultipleDex = setting; }

	public boolean search_dex_in_archives() { return searchDexInArchives; }

	public void set_search_dex_in_archives(boolean setting) { searchDexInArchives = setting; }

	public List<String> process_dir() {
        return processDir == null ? Collections.emptyList() : processDir;
    }

	public void set_process_dir(List<String> setting) { processDir = setting; }

	public boolean oaat() { return oaat; }

	public void set_oaat(boolean setting) { oaat = setting; }

	public String android_jars() { return androidJars; }

	public void set_android_jars(String setting) { androidJars = setting; }

	public String force_android_jar() { return forceAndroidJar; }

	public void set_force_android_jar(String setting) { forceAndroidJar = setting; }

	public int android_api_version() { return androidApiVersion; }

	public void set_android_api_version(int setting) { androidApiVersion = setting; }

	public boolean ast_metrics() { return astMetrics; }

	public void set_ast_metrics(boolean setting) { astMetrics = setting; }

	public int src_prec() {
        if (srcPrec == 0) {
			return src_prec_class;
		}
        return srcPrec; 
    }

	public void set_src_prec(int setting) { srcPrec = setting; }

	public boolean full_resolver() { return fullResolver; }

	public void set_full_resolver(boolean setting) { fullResolver = setting; }

	public boolean allow_phantom_refs() { return allowPhantomRefs; }

	public void set_allow_phantom_refs(boolean setting) { allowPhantomRefs = setting; }

	public boolean allow_phantom_elms() { return allowPhantomElms; }

	public void set_allow_phantom_elms(boolean setting) { allowPhantomElms = setting; }

	public boolean no_bodies_for_excluded() { return noBodiesForExcluded; }

	public void set_no_bodies_for_excluded(boolean setting) { noBodiesForExcluded = setting; }

	public boolean j2me() { return j2me; }

	public void set_j2me(boolean setting) { j2me = setting; }

	public String main_class() { return mainClass; }

	public void set_main_class(String setting) { mainClass = setting; }

	public boolean polyglot() { return polyglot; }

	public void set_polyglot(boolean setting) { polyglot = setting; }

	public boolean permissive_resolving() { return permissiveResolving; }

	public void set_permissive_resolving(boolean setting) { permissiveResolving = setting; }

	public boolean drop_bodies_after_load() { return dropBodiesAfterLoad; }

	public void set_drop_bodies_after_load(boolean setting) { dropBodiesAfterLoad = setting; }

	public String output_dir() { return outputDir; }

	public void set_output_dir(String setting) { outputDir = setting; }

	public int output_format() {
        if (outputFormat == 0) {
			return output_format_class;
		}
        return outputFormat; 
    }

	public void set_output_format(int setting) { outputFormat = setting; }

	public int java_version() {
        return javaVersion; 
    }

	public void set_java_version(int setting) { javaVersion = setting; }

	public boolean output_jar() { return outputJar; }

	public void set_output_jar(boolean setting) { outputJar = setting; }

	public boolean hierarchy_dirs() { return hierarchyDirs; }

	public void set_hierarchy_dirs(boolean setting) { hierarchyDirs = setting; }

	public boolean xml_attributes() { return xmlAttributes; }

	public void set_xml_attributes(boolean setting) { xmlAttributes = setting; }

	public boolean print_tags_in_output() { return printTagsInOutput; }

	public void set_print_tags_in_output(boolean setting) { printTagsInOutput = setting; }

	public boolean no_output_source_file_attribute() { return noOutputSourceFileAttribute; }

	public void set_no_output_source_file_attribute(boolean setting) { noOutputSourceFileAttribute = setting; }

	public boolean no_output_inner_classes_attribute() { return noOutputInnerClassesAttribute; }

	public void set_no_output_inner_classes_attribute(boolean setting) { noOutputInnerClassesAttribute = setting; }

	public List<String> dump_body() {
        return dumpBody == null ? Collections.emptyList() : dumpBody;
    }

	public void set_dump_body(List<String> setting) { dumpBody = setting; }

	public List<String> dump_cfg() {
        return dumpCfg == null ? Collections.emptyList() : dumpCfg;
    }

	public void set_dump_cfg(List<String> setting) { dumpCfg = setting; }

	public boolean show_exception_dests() { return showExceptionDests; }

	public void set_show_exception_dests(boolean setting) { showExceptionDests = setting; }

	public boolean gzip() { return gzip; }

	public void set_gzip(boolean setting) { gzip = setting; }

	public boolean force_overwrite() { return forceOverwrite; }

	public void set_force_overwrite(boolean setting) { forceOverwrite = setting; }

	public List<String> plugin() {
        return plugin == null ? Collections.emptyList() : plugin;
    }

	public void set_plugin(List<String> setting) { plugin = setting; }

	public int wrong_staticness() {
        if (wrongStaticness == 0) {
			return wrong_staticness_fixstrict;
		}
        return wrongStaticness; 
    }

	public void set_wrong_staticness(int setting) { wrongStaticness = setting; }

	public int field_type_mismatches() {
        if (fieldTypeMismatches == 0) {
			return field_type_mismatches_null;
		}
        return fieldTypeMismatches; 
    }

	public void set_field_type_mismatches(int setting) { fieldTypeMismatches = setting; }

	public boolean via_grimp() { return viaGrimp; }

	public void set_via_grimp(boolean setting) { viaGrimp = setting; }

	public boolean via_shimple() { return viaShimple; }

	public void set_via_shimple(boolean setting) { viaShimple = setting; }

	public int throw_analysis() {
        if (throwAnalysis == 0) {
			return throw_analysis_unit;
		}
        return throwAnalysis; 
    }

	public void set_throw_analysis(int setting) { throwAnalysis = setting; }

	public int check_init_throw_analysis() {
        if (checkInitThrowAnalysis == 0) {
			return check_init_throw_analysis_auto;
		}
        return checkInitThrowAnalysis; 
    }

	public void set_check_init_throw_analysis(int setting) { checkInitThrowAnalysis = setting; }

	public boolean omit_excepting_unit_edges() { return omitExceptingUnitEdges; }

	public void set_omit_excepting_unit_edges(boolean setting) { omitExceptingUnitEdges = setting; }

	public boolean ignore_resolution_errors() { return ignoreResolutionErrors; }

	public void set_ignore_resolution_errors(boolean setting) { ignoreResolutionErrors = setting; }

	public List<String> include() {
        return include == null ? Collections.emptyList() : include;
    }

	public void set_include(List<String> setting) { include = setting; }

	public List<String> exclude() {
        return exclude == null ? Collections.emptyList() : exclude;
    }

	public void set_exclude(List<String> setting) { exclude = setting; }

	public boolean include_all() { return includeAll; }

	public void set_include_all(boolean setting) { includeAll = setting; }

	public List<String> dynamic_class() {
        return dynamicClass == null ? Collections.emptyList() : dynamicClass;
    }

	public void set_dynamic_class(List<String> setting) { dynamicClass = setting; }

	public List<String> dynamic_dir() {
        return dynamicDir == null ? Collections.emptyList() : dynamicDir;
    }

	public void set_dynamic_dir(List<String> setting) { dynamicDir = setting; }

	public List<String> dynamic_package() {
        return dynamicPackage == null ? Collections.emptyList() : dynamicPackage;
    }

	public void set_dynamic_package(List<String> setting) { dynamicPackage = setting; }

	public boolean keep_line_number() { return keepLineNumber; }

	public void set_keep_line_number(boolean setting) { keepLineNumber = setting; }

	public boolean keep_offset() { return keepOffset; }

	public void set_keep_offset(boolean setting) { keepOffset = setting; }

	public boolean write_local_annotations() { return writeLocalAnnotations; }

	public void set_write_local_annotations(boolean setting) { writeLocalAnnotations = setting; }

	public boolean time() { return time; }

	public void set_time(boolean setting) { time = setting; }

	public boolean subtract_gc() { return subtractGc; }

	public void set_subtract_gc(boolean setting) { subtractGc = setting; }

	public boolean no_writeout_body_releasing() { return noWriteoutBodyReleasing; }

	public void set_no_writeout_body_releasing(boolean setting) { noWriteoutBodyReleasing = setting; }

	public String getUsage() {
        return new StringBuilder().append("").append("\nGeneral Options:\n").append(padOpt("-coffi", "Use the good old Coffi front end for parsing Java bytecode (instead of using ASM).")).append(padOpt("-jasmin-backend", "Use the Jasmin back end for generating Java bytecode (instead of using ASM).")).append(padOpt("-h, -help", "Display help and exit"))
				.append(padOpt("-pl, -phase-list", "Print list of available phases")).append(padOpt("-ph ARG -phase-help ARG", "Print help for specified ARG")).append(padOpt("-version", "Display version information and exit")).append(padOpt("-v, -verbose", "Verbose mode")).append(padOpt("-interactive-mode", "Run in interactive mode"))
				.append(padOpt("-unfriendly-mode", "Allow Soot to run with no command-line options")).append(padOpt("-app", "Run in application mode")).append(padOpt("-w, -whole-program", "Run in whole-program mode")).append(padOpt("-ws, -whole-shimple", "Run in whole-shimple mode")).append(padOpt("-fly, -on-the-fly", "Run in on-the-fly mode"))
				.append(padOpt("-validate", "Run internal validation on bodies")).append(padOpt("-debug", "Print various Soot debugging info")).append(padOpt("-debug-resolver", "Print debugging info from SootResolver")).append(padOpt("-ignore-resolving-levels", "Ignore mismatching resolving levels")).append("\nInput Options:\n").append(padOpt("-cp ARG -soot-class-path ARG -soot-classpath ARG", "Use ARG as the classpath for finding classes."))
				.append(padOpt("-pp, -prepend-classpath", "Prepend the given soot classpath to the default classpath.")).append(padOpt("-ice, -ignore-classpath-errors", "Ignores invalid entries on the Soot classpath.")).append(padOpt("-process-multiple-dex", "Process all DEX files found in APK.")).append(padOpt("-search-dex-in-archives", "Also includes Jar and Zip files when searching for DEX files under the provided classpath.")).append(padOpt("-process-path ARG -process-dir ARG", "Process all classes found in ARG"))
				.append(padOpt("-oaat", "From the process-dir, processes one class at a time.")).append(padOpt("-android-jars ARG", "Use ARG as the path for finding the android.jar file")).append(padOpt("-force-android-jar ARG", "Force Soot to use ARG as the path for the android.jar file.")).append(padOpt("-ast-metrics", "Compute AST Metrics if performing java to jimple")).append(padOpt("-src-prec ARG", "Sets source precedence to ARG files"))
				.append(padVal("c class (default)", "Favour class files as Soot source")).append(padVal("only-class", "Use only class files as Soot source")).append(padVal("J jimple", "Favour Jimple files as Soot source")).append(padVal("java", "Favour Java files as Soot source")).append(padVal("apk", "Favour APK files as Soot source"))
				.append(padVal("apk-class-jimple apk-c-j", "Favour APK files as Soot source, disregard Java files")).append(padOpt("-full-resolver", "Force transitive resolving of referenced classes")).append(padOpt("-allow-phantom-refs", "Allow unresolved classes; may cause errors")).append(padOpt("-allow-phantom-elms", "Allow phantom methods and fields in non-phantom classes")).append(padOpt("-no-bodies-for-excluded", "Do not load bodies for excluded classes"))
				.append(padOpt("-j2me", "Use J2ME mode; changes assignment of types")).append(padOpt("-main-class ARG", "Sets the main class for whole-program analysis.")).append(padOpt("-polyglot", "Use Java 1.4 Polyglot frontend instead of JastAdd")).append(padOpt("-permissive-resolving", "Use alternative sources when classes cannot be found using the normal resolving strategy")).append(padOpt("-drop-bodies-after-load", "Drop the method source after it has served its purpose of loading the method body")).append("\nOutput Options:\n")
				.append(padOpt("-d ARG -output-dir ARG", "Store output files in ARG")).append(padOpt("-f ARG -output-format ARG", "Set output format for Soot")).append(padVal("J jimple", "Produce .jimple Files")).append(padVal("j jimp", "Produce .jimp (abbreviated Jimple) files")).append(padVal("S shimple", "Produce .shimple files"))
				.append(padVal("s shimp", "Produce .shimp (abbreviated Shimple) files")).append(padVal("B baf", "Produce .baf files")).append(padVal("b", "Produce .b (abbreviated Baf) files")).append(padVal("G grimple", "Produce .grimple files")).append(padVal("g grimp", "Produce .grimp (abbreviated Grimp) files"))
				.append(padVal("X xml", "Produce .xml Files")).append(padVal("dex", "Produce Dalvik Virtual Machine files")).append(padVal("force-dex", "Produce Dalvik DEX files")).append(padVal("n none", "Produce no output")).append(padVal("jasmin", "Produce .jasmin files"))
				.append(padVal("c class (default)", "Produce .class Files")).append(padVal("d dava", "Produce dava-decompiled .java files")).append(padVal("t template", "Produce .java files with Jimple templates.")).append(padVal("a asm", "Produce .asm files as textual bytecode representation generated with the ASM back end.")).append(padOpt("-java-version ARG", "Force Java version of bytecode generated by Soot."))
				.append(padVal("default", "Let Soot determine Java version of generated bytecode.")).append(padVal("1.1 1", "Force Java 1.1 as output version.")).append(padVal("1.2 2", "Force Java 1.2 as output version.")).append(padVal("1.3 3", "Force Java 1.3 as output version.")).append(padVal("1.4 4", "Force Java 1.4 as output version."))
				.append(padVal("1.5 5", "Force Java 1.5 as output version.")).append(padVal("1.6 6", "Force Java 1.6 as output version.")).append(padVal("1.7 7", "Force Java 1.7 as output version.")).append(padVal("1.8 8", "Force Java 1.8 as output version.")).append(padVal("1.9 9", "Force Java 1.9 as output version (Experimental)."))
				.append(padOpt("-outjar, -output-jar", "Make output dir a Jar file instead of dir")).append(padOpt("-hierarchy-dirs", "Generate class hierarchy directories for Jimple/Shimple")).append(padOpt("-xml-attributes", "Save tags to XML attributes for Eclipse")).append(padOpt("-print-tags, -print-tags-in-output", "Print tags in output files after stmt")).append(padOpt("-no-output-source-file-attribute", "Don't output Source File Attribute when producing class files"))
				.append(padOpt("-no-output-inner-classes-attribute", "Don't output inner classes attribute in class files")).append(padOpt("-dump-body ARG", "Dump the internal representation of each method before and after phase ARG")).append(padOpt("-dump-cfg ARG", "Dump the internal representation of each CFG constructed during phase ARG")).append(padOpt("-show-exception-dests", "Include exception destination edges as well as CFG edges in dumped CFGs")).append(padOpt("-gzip", "GZip IR output files"))
				.append(padOpt("-force-overwrite", "Force Overwrite Output Files")).append("\nProcessing Options:\n").append(padOpt("-plugin ARG", "Load all plugins found in ARG")).append(padOpt("-wrong-staticness ARG", "Ignores or fixes errors due to wrong staticness")).append(padVal("fail", "Raise an error when wrong staticness is detected")).append(padVal("ignore", "Ignore errors caused by wrong staticness"))
				.append(padVal("fix", "Transparently fix staticness errors")).append(padVal("fixstrict (default)", "Transparently fix staticness errors, but do not ignore remaining errors")).append(padOpt("-field-type-mismatches ARG", "Specifies how errors shall be handled when resolving field references with mismatching types")).append(padVal("fail", "Raise an error when a field type mismatch is detected")).append(padVal("ignore", "Ignore field type mismatches"))
				.append(padVal("null (default)", "Return null in case of type mismatch")).append(padOpt("-p ARG -phase-option ARG", "Set PHASE 's OPT option to VALUE")).append(padOpt("-O, -optimize", "Perform intraprocedural optimizations")).append(padOpt("-W, -whole-optimize", "Perform whole program optimizations")).append(padOpt("-via-grimp", "Convert to bytecode via Grimp instead of via Baf"))
				.append(padOpt("-via-shimple", "Enable Shimple SSA representation")).append(padOpt("-throw-analysis ARG", "")).append(padVal("pedantic", "Pedantically conservative throw analysis")).append(padVal("unit (default)", "Unit Throw Analysis")).append(padVal("dalvik", "Dalvik Throw Analysis"))
				.append(padOpt("-check-init-ta ARG -check-init-throw-analysis ARG", "")).append(padVal("auto (default)", "Automatically select a throw analysis")).append(padVal("pedantic", "Pedantically conservative throw analysis")).append(padVal("unit", "Unit Throw Analysis")).append(padVal("dalvik", "Dalvik Throw Analysis"))
				.append(padOpt("-omit-excepting-unit-edges", "Omit CFG edges to handlers from excepting units which lack side effects")).append(padOpt("-trim-cfgs", "Trim unrealizable exceptional edges from CFGs")).append(padOpt("-ire, -ignore-resolution-errors", "Does not throw an exception when a program references an undeclared field or method.")).append("\nApplication Mode Options:\n").append(padOpt("-i ARG -include ARG", "Include classes in ARG as application classes")).append(padOpt("-x ARG -exclude ARG", "Exclude classes in ARG from application classes"))
				.append(padOpt("-include-all", "Set default excluded packages to empty list")).append(padOpt("-dynamic-class ARG", "Note that ARG may be loaded dynamically")).append(padOpt("-dynamic-dir ARG", "Mark all classes in ARG as potentially dynamic")).append(padOpt("-dynamic-package ARG", "Marks classes in ARG as potentially dynamic")).append("\nInput Attribute Options:\n").append(padOpt("-keep-line-number", "Keep line number tables"))
				.append(padOpt("-keep-bytecode-offset, -keep-offset", "Attach bytecode offset to IR")).append("\nOutput Attribute Options:\n").append(padOpt("-write-local-annotations", "Write out debug annotations on local names")).append("\nAnnotation Options:\n").append(padOpt("-annot-purity", "Emit purity attributes")).append(padOpt("-annot-nullpointer", "Emit null pointer attributes"))
				.append(padOpt("-annot-arraybounds", "Emit array bounds check attributes")).append(padOpt("-annot-side-effect", "Emit side-effect attributes")).append(padOpt("-annot-fieldrw", "Emit field read/write attributes")).append("\nMiscellaneous Options:\n").append(padOpt("-time", "Report time required for transformations")).append(padOpt("-subtract-gc", "Subtract gc from time"))
				.append(padOpt("-no-writeout-body-releasing", "Disables the release of method bodies after writeout. This flag is used internally.")).toString();
    }

	public String getPhaseList() {
        return new StringBuilder().append("").append(padOpt("jb", "Creates a JimpleBody for each method")).append(padVal("jb.dtr", "Reduces chains of catch-all traps")).append(padVal("jb.ese", "Removes empty switch statements")).append(padVal("jb.ls", "Local splitter: one local per DU-UD web"))
				.append(padVal("jb.a", "Aggregator: removes some unnecessary copies")).append(padVal("jb.ule", "Unused local eliminator")).append(padVal("jb.tr", "Assigns types to locals")).append(padVal("jb.ulp", "Local packer: minimizes number of locals")).append(padVal("jb.lns", "Local name standardizer"))
				.append(padVal("jb.cp", "Copy propagator")).append(padVal("jb.dae", "Dead assignment eliminator")).append(padVal("jb.cp-ule", "Post-copy propagation unused local eliminator")).append(padVal("jb.lp", "Local packer: minimizes number of locals")).append(padVal("jb.ne", "Nop eliminator"))
				.append(padVal("jb.uce", "Unreachable code eliminator")).append(padVal("jb.tt", "Trap Tightener")).append(padOpt("jj", "Creates a JimpleBody for each method directly from source")).append(padVal("jj.ls", "Local splitter: one local per DU-UD web")).append(padVal("jj.a", "Aggregator: removes some unnecessary copies"))
				.append(padVal("jj.ule", "Unused local eliminator")).append(padVal("jj.tr", "Assigns types to locals")).append(padVal("jj.ulp", "Local packer: minimizes number of locals")).append(padVal("jj.lns", "Local name standardizer")).append(padVal("jj.cp", "Copy propagator"))
				.append(padVal("jj.dae", "Dead assignment eliminator")).append(padVal("jj.cp-ule", "Post-copy propagation unused local eliminator")).append(padVal("jj.lp", "Local packer: minimizes number of locals")).append(padVal("jj.ne", "Nop eliminator")).append(padVal("jj.uce", "Unreachable code eliminator"))
				.append(padOpt("wjpp", "Whole Jimple Pre-processing Pack")).append(padVal("wjpp.cimbt", "Replaces base objects of calls to Method.invoke() that are string constants by locals")).append(padOpt("wspp", "Whole Shimple Pre-processing Pack")).append(padOpt("cg", "Call graph constructor")).append(padVal("cg.cha", "Builds call graph using Class Hierarchy Analysis"))
				.append(padVal("cg.spark", "Spark points-to analysis framework")).append(padVal("cg.paddle", "Paddle points-to analysis framework")).append(padOpt("wstp", "Whole-shimple transformation pack")).append(padOpt("wsop", "Whole-shimple optimization pack")).append(padOpt("wjtp", "Whole-jimple transformation pack"))
				.append(padVal("wjtp.mhp", "Determines what statements may be run concurrently")).append(padVal("wjtp.tn", "Finds critical sections, allocates locks")).append(padVal("wjtp.rdc", "Rename duplicated classes when the file system is not case sensitive")).append(padOpt("wjop", "Whole-jimple optimization pack")).append(padVal("wjop.smb", "Static method binder: Devirtualizes monomorphic calls"))
				.append(padVal("wjop.si", "Static inliner: inlines monomorphic calls")).append(padOpt("wjap", "Whole-jimple annotation pack: adds interprocedural tags")).append(padVal("wjap.ra", "Rectangular array finder")).append(padVal("wjap.umt", "Tags all unreachable methods")).append(padVal("wjap.uft", "Tags all unreachable fields"))
				.append(padVal("wjap.tqt", "Tags all qualifiers that could be tighter")).append(padVal("wjap.cgg", "Creates graphical call graph.")).append(padVal("wjap.purity", "Emit purity attributes")).append(padOpt("shimple", "Sets parameters for Shimple SSA form")).append(padOpt("stp", "Shimple transformation pack"))
				.append(padOpt("sop", "Shimple optimization pack")).append(padVal("sop.cpf", "Shimple constant propagator and folder")).append(padOpt("jtp", "Jimple transformation pack: intraprocedural analyses added to Soot")).append(padOpt("jop", "Jimple optimization pack (intraprocedural)")).append(padVal("jop.cse", "Common subexpression eliminator"))
				.append(padVal("jop.bcm", "Busy code motion: unaggressive partial redundancy elimination")).append(padVal("jop.lcm", "Lazy code motion: aggressive partial redundancy elimination")).append(padVal("jop.cp", "Copy propagator")).append(padVal("jop.cpf", "Constant propagator and folder")).append(padVal("jop.cbf", "Conditional branch folder"))
				.append(padVal("jop.dae", "Dead assignment eliminator")).append(padVal("jop.nce", "Null Check Eliminator")).append(padVal("jop.uce1", "Unreachable code eliminator, pass 1")).append(padVal("jop.ubf1", "Unconditional branch folder, pass 1")).append(padVal("jop.uce2", "Unreachable code eliminator, pass 2"))
				.append(padVal("jop.ubf2", "Unconditional branch folder, pass 2")).append(padVal("jop.ule", "Unused local eliminator")).append(padOpt("jap", "Jimple annotation pack: adds intraprocedural tags")).append(padVal("jap.npc", "Null pointer checker")).append(padVal("jap.npcolorer", "Null pointer colourer: tags references for eclipse"))
				.append(padVal("jap.abc", "Array bound checker")).append(padVal("jap.profiling", "Instruments null pointer and array checks")).append(padVal("jap.sea", "Side effect tagger")).append(padVal("jap.fieldrw", "Field read/write tagger")).append(padVal("jap.cgtagger", "Call graph tagger"))
				.append(padVal("jap.parity", "Parity tagger")).append(padVal("jap.pat", "Colour-codes method parameters that may be aliased")).append(padVal("jap.lvtagger", "Creates color tags for live variables")).append(padVal("jap.rdtagger", "Creates link tags for reaching defs")).append(padVal("jap.che", "Indicates whether cast checks can be eliminated"))
				.append(padVal("jap.umt", "Inserts assertions into unreachable methods")).append(padVal("jap.lit", "Tags loop invariants")).append(padVal("jap.aet", "Tags statements with sets of available expressions")).append(padVal("jap.dmt", "Tags dominators of statement")).append(padOpt("gb", "Creates a GrimpBody for each method"))
				.append(padVal("gb.a1", "Aggregator: removes some copies, pre-folding")).append(padVal("gb.cf", "Constructor folder")).append(padVal("gb.a2", "Aggregator: removes some copies, post-folding")).append(padVal("gb.ule", "Unused local eliminator")).append(padOpt("gop", "Grimp optimization pack"))
				.append(padOpt("bb", "Creates Baf bodies")).append(padVal("bb.lso", "Load store optimizer")).append(padVal("bb.sco", "Store chain optimizer")).append(padVal("bb.pho", "Peephole optimizer")).append(padVal("bb.ule", "Unused local eliminator"))
				.append(padVal("bb.lp", "Local packer: minimizes number of locals")).append(padOpt("bop", "Baf optimization pack")).append(padOpt("tag", "Tag aggregator: turns tags into attributes")).append(padVal("tag.ln", "Line number aggregator")).append(padVal("tag.an", "Array bounds and null pointer check aggregator"))
				.append(padVal("tag.dep", "Dependence aggregator")).append(padVal("tag.fieldrw", "Field read/write aggregator")).append(padOpt("db", "Dummy phase to store options for Dava")).append(padVal("db.transformations", "The Dava back-end with all its transformations")).append(padVal("db.renamer", "Apply heuristics based naming of local variables"))
				.append(padVal("db.deobfuscate", "Apply de-obfuscation analyses")).append(padVal("db.force-recompile", "Try to get recompilable code.")).toString();
    }

	public String getPhaseHelp(String phaseName) {
        if ("jb".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nJimple Body Creation creates a JimpleBody for each input method, \nusing either asm, to read .class files, or the jimple parser, to \nread .jimple files.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("use-original-names (false)", "")).append(padOpt("preserve-source-annotations (false)", "")).append(padOpt("stabilize-local-names (false)", "")).append(padOpt("model-lambdametafactory (true)", "Replace dynamic invoke instructions to the LambdaMetafactory by static invokes to a synthetic LambdaMetafactory implementation.")).toString();
		}

        if ("jb.dtr".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis transformer detects cases in which the same code block is \ncovered by two different catch all traps with different \nexception handlers (A and B), and if there is at the same time a \nthird catch all trap that covers the second handler B and jumps \nto A, then the second trap is unnecessary, because it is already \ncovered by a combination of the other two traps. This \ntransformer removes the unnecessary trap.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jb.ese".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Empty Switch Eliminator detects and removes switch \nstatements that have no data labels. Instead, the code is \ntransformed to contain a single jump statement to the default \nlabel.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jb.ls".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Local Splitter identifies DU-UD webs for local variables and \nintroduces new variables so that each disjoint web is associated \nwith a single local.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jb.a".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Jimple Local Aggregator removes some unnecessary copies by \ncombining local variables. Essentially, it finds definitions \nwhich have only a single use and, if it is safe to do so, \nremoves the original definition after replacing the use with the \ndefinition's right-hand side. At this stage in JimpleBody \nconstruction, local aggregation serves largely to remove the \ncopies to and from stack variables which simulate load and store \ninstructions in the original bytecode.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-stack-locals (true)", "")).toString();
		}

        if ("jb.ule".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unused Local Eliminator removes any unused locals from the \nmethod.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jb.tr".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Type Assigner gives local variables types which will \naccommodate the values stored in them over the course of the \nmethod.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("use-older-type-assigner (false)", "Enables the older type assigner")).append(padOpt("compare-type-assigners (false)", "Compares Ben Bellamy's and the older type assigner")).append(padOpt("ignore-nullpointer-dereferences (false)", "Ignores virtual method calls on base objects that may only be null")).toString();
		}

        if ("jb.ulp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unsplit-originals Local Packer executes only when the \n`use-original-names' option is chosen for the `jb' phase. The \nLocal Packer attempts to minimize the number of local variables \nrequired in a method by reusing the same variable for disjoint \nDU-UD webs. Conceptually, it is the inverse of the Local \nSplitter.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("unsplit-original-locals (true)", "")).toString();
		}

        if ("jb.lns".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Local Name Standardizer assigns generic names to local \nvariables.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-stack-locals (false)", "")).append(padOpt("sort-locals (false)", "Specifies whether the locals shall be ordered.")).toString();
		}

        if ("jb.cp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis phase performs cascaded copy propagation. If the propagator \nencounters situations of the form: A: a = ...; ... B: x = a; ... \nC: ... = ... x; where a and x are each defined only once (at A \nand B, respectively), then it can propagate immediately without \nchecking between B and C for redefinitions of a. In this case \nthe propagator is global. Otherwise, if a has multiple \ndefinitions then the propagator checks for redefinitions and \npropagates copies only within extended basic blocks.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-regular-locals (false)", "")).append(padOpt("only-stack-locals (true)", "")).toString();
		}

        if ("jb.dae".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Dead Assignment Eliminator eliminates assignment statements \nto locals whose values are not subsequently used, unless \nevaluating the right-hand side of the assignment may cause \nside-effects.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-stack-locals (true)", "")).toString();
		}

        if ("jb.cp-ule".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis phase removes any locals that are unused after copy \npropagation.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jb.lp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Local Packer attempts to minimize the number of local \nvariables required in a method by reusing the same variable for \ndisjoint DU-UD webs. Conceptually, it is the inverse of the \nLocal Splitter.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("unsplit-original-locals (false)", "")).toString();
		}

        if ("jb.ne".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Nop Eliminator removes nop statements from the method.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jb.uce".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unreachable Code Eliminator removes unreachable code and \ntraps whose catch blocks are empty.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("remove-unreachable-traps (true)", "")).toString();
		}

        if ("jb.tt".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Trap Tightener changes the area protected by each exception \nhandler, so that it begins with the first instruction in the old \nprotected area which is actually capable of throwing an \nexception caught by the handler, and ends just after the last \ninstruction in the old protected area which can throw an \nexception caught by the handler. This reduces the chance of \nproducing unverifiable code as a byproduct of pruning \nexceptional control flow within CFGs.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jj".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nJimple Body Creation creates a JimpleBody for each input method, \nusing polyglot, to read .java files.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("use-original-names (true)", "")).toString();
		}

        if ("jj.ls".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Local Splitter identifies DU-UD webs for local variables and \nintroduces new variables so that each disjoint web is associated \nwith a single local.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jj.a".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Jimple Local Aggregator removes some unnecessary copies by \ncombining local variables. Essentially, it finds definitions \nwhich have only a single use and, if it is safe to do so, \nremoves the original definition after replacing the use with the \ndefinition's right-hand side. At this stage in JimpleBody \nconstruction, local aggregation serves largely to remove the \ncopies to and from stack variables which simulate load and store \ninstructions in the original bytecode.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-stack-locals (true)", "")).toString();
		}

        if ("jj.ule".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unused Local Eliminator removes any unused locals from the \nmethod.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jj.tr".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Type Assigner gives local variables types which will \naccommodate the values stored in them over the course of the \nmethod.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jj.ulp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unsplit-originals Local Packer executes only when the \n`use-original-names' option is chosen for the `jb' phase. The \nLocal Packer attempts to minimize the number of local variables \nrequired in a method by reusing the same variable for disjoint \nDU-UD webs. Conceptually, it is the inverse of the Local \nSplitter.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("unsplit-original-locals (false)", "")).toString();
		}

        if ("jj.lns".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Local Name Standardizer assigns generic names to local \nvariables.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-stack-locals (false)", "")).toString();
		}

        if ("jj.cp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis phase performs cascaded copy propagation. If the propagator \nencounters situations of the form: A: a = ...; ... B: x = a; ... \nC: ... = ... x; where a and x are each defined only once (at A \nand B, respectively), then it can propagate immediately without \nchecking between B and C for redefinitions of a. In this case \nthe propagator is global. Otherwise, if a has multiple \ndefinitions then the propagator checks for redefinitions and \npropagates copies only within extended basic blocks.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-regular-locals (false)", "")).append(padOpt("only-stack-locals (true)", "")).toString();
		}

        if ("jj.dae".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Dead Assignment Eliminator eliminates assignment statements \nto locals whose values are not subsequently used, unless \nevaluating the right-hand side of the assignment may cause \nside-effects.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-stack-locals (true)", "")).toString();
		}

        if ("jj.cp-ule".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis phase removes any locals that are unused after copy \npropagation.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jj.lp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Local Packer attempts to minimize the number of local \nvariables required in a method by reusing the same variable for \ndisjoint DU-UD webs. Conceptually, it is the inverse of the \nLocal Splitter.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("unsplit-original-locals (false)", "")).toString();
		}

        if ("jj.ne".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Nop Eliminator removes nop statements from the method.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jj.uce".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unreachable Code Eliminator removes unreachable code and \ntraps whose catch blocks are empty.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("wjpp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis pack allows you to insert pre-processors that are run \nbefore call-graph construction. Only enabled in whole-program \nmode.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("wjpp.cimbt".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nWhen using the types-for-invoke option of the cg phase, problems \nmight occur if the base object of a call to Method.invoke() (the \nfirst argument) is a string constant. This option replaces all \nstring constants of such calls by locals and, therefore, allows \nthe static resolution of reflective call targets on constant \nstring objects.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("verbose (false)", "")).toString();
		}

        if ("wspp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis pack allows you to insert pre-processors that are run \nbefore call-graph construction. Only enabled in whole-program \nShimple mode. In an unmodified copy of Soot, this pack is empty.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("cg".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Call Graph Constructor computes a call graph for whole \nprogram analysis. When this pack finishes, a call graph is \navailable in the Scene. The different phases in this pack are \ndifferent ways to construct the call graph. Exactly one phase in \nthis pack must be enabled; Soot will raise an error otherwise.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("safe-forname (false)", "Handle Class.forName() calls conservatively")).append(padOpt("safe-newinstance (false)", "Handle Class.newInstance() calls conservatively")).append(padOpt("library", "Specifies whether the target classes should be treated as an application or a library.")).append(padVal("disabled (default)", "Call(and pointer assignment) graph construction treat the target classes as application starting from the entry points.")).append(padVal("any-subtype", "In this mode types of any accessible field, method parameter, this local, or caugth exception is set to any possible sub type according to the class hierarchy of the target library."))
					.append(padVal("signature-resolution", "In this mode types of any accessible field, method parameter, this local, or caugth exception is set to any possible sub type according to a possible extended class hierarchy of the target library.")).append(padOpt("verbose (false)", "Print warnings about where the call graph may be incomplete")).append(padOpt("jdkver (3)", "JDK version for native methods")).append(padOpt("all-reachable (false)", "Assume all methods of application classes are reachable.")).append(padOpt("implicit-entry (true)", "Include methods called implicitly by the VM as entry points"))
					.append(padOpt("trim-clinit (true)", "Removes redundant static initializer calls")).append(padOpt("reflection-log", "Uses a reflection log to resolve reflective calls.")).append(padOpt("guards (ignore)", "Describes how to guard the program from unsound assumptions.")).append(padOpt("types-for-invoke (false)", "Uses reaching types inferred by the pointer analysis to resolve reflective calls.")).append(padOpt("resolve-all-abstract-invokes (false)", "Causes methods invoked on abstract classes to be resolved even if there are no non-abstract children of the classes in the Scene."))
					.toString();
		}

        if ("cg.cha".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis phase uses Class Hierarchy Analysis to generate a call \ngraph.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("verbose (false)", "Print statistics about the resulting call graph")).append(padOpt("apponly (false)", "Consider only application classes")).toString();
		}

        if ("cg.spark".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nSpark is a flexible points-to analysis framework. Aside from \nbuilding a call graph, it also generates information about the \ntargets of pointers. For details about Spark, please see Ondrej \nLhotak's M.Sc. thesis.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("verbose (false)", "Print detailed information about the execution of Spark")).append(padOpt("ignore-types (false)", "Make Spark completely ignore declared types of variables")).append(padOpt("force-gc (false)", "Force garbage collection for measuring memory usage")).append(padOpt("pre-jimplify (false)", "Jimplify all methods before starting Spark")).append(padOpt("apponly (false)", "Consider only application classes"))
					.append(padOpt("vta (false)", "Emulate Variable Type Analysis")).append(padOpt("rta (false)", "Emulate Rapid Type Analysis")).append(padOpt("field-based (false)", "Use a field-based rather than field-sensitive representation")).append(padOpt("types-for-sites (false)", "Represent objects by their actual type rather than allocation site")).append(padOpt("merge-stringbuffer (true)", "Represent all StringBuffers as one object"))
					.append(padOpt("string-constants (false)", "Propagate all string constants, not just class names")).append(padOpt("simulate-natives (true)", "Simulate effects of native methods in standard class library")).append(padOpt("empties-as-allocs (false)", "Treat singletons for empty sets etc. as allocation sites")).append(padOpt("simple-edges-bidirectional (false)", "Equality-based analysis between variable nodes")).append(padOpt("on-fly-cg (true)", "Build call graph as receiver types become known"))
					.append(padOpt("simplify-offline (false)", "Collapse single-entry subgraphs of the PAG")).append(padOpt("simplify-sccs (false)", "Collapse strongly-connected components of the PAG")).append(padOpt("ignore-types-for-sccs (false)", "Ignore declared types when determining node equivalence for SCCs")).append(padOpt("propagator", "Select propagation algorithm")).append(padVal("iter", "Simple iterative algorithm"))
					.append(padVal("worklist (default)", "Fast, worklist-based algorithm")).append(padVal("cycle", "Unfinished on-the-fly cycle detection algorithm")).append(padVal("merge", "Unfinished field reference merging algorithms")).append(padVal("alias", "Alias-edge based algorithm")).append(padVal("none", "Disable propagation"))
					.append(padOpt("set-impl", "Select points-to set implementation")).append(padVal("hash", "Use Java HashSet")).append(padVal("bit", "Bit vector")).append(padVal("hybrid", "Hybrid representation using bit vector for large sets")).append(padVal("array", "Sorted array representation"))
					.append(padVal("heintze", "Heintze's shared bit-vector and overflow list representation")).append(padVal("sharedlist", "Shared list representation")).append(padVal("double (default)", "Double set representation for incremental propagation")).append(padOpt("double-set-old", "Select implementation of points-to set for old part of double set")).append(padVal("hash", "Use Java HashSet"))
					.append(padVal("bit", "Bit vector")).append(padVal("hybrid (default)", "Hybrid representation using bit vector for large sets")).append(padVal("array", "Sorted array representation")).append(padVal("heintze", "Heintze's shared bit-vector and overflow list representation")).append(padVal("sharedlist", "Shared list representation"))
					.append(padOpt("double-set-new", "Select implementation of points-to set for new part of double set")).append(padVal("hash", "Use Java HashSet")).append(padVal("bit", "Bit vector")).append(padVal("hybrid (default)", "Hybrid representation using bit vector for large sets")).append(padVal("array", "Sorted array representation"))
					.append(padVal("heintze", "Heintze's shared bit-vector and overflow list representation")).append(padVal("sharedlist", "Shared list representation")).append(padOpt("dump-html (false)", "Dump pointer assignment graph to HTML for debugging")).append(padOpt("dump-pag (false)", "Dump pointer assignment graph for other solvers")).append(padOpt("dump-solution (false)", "Dump final solution for comparison with other solvers"))
					.append(padOpt("topo-sort (false)", "Sort variable nodes in dump")).append(padOpt("dump-types (true)", "Include declared types in dump")).append(padOpt("class-method-var (true)", "In dump, label variables by class and method")).append(padOpt("dump-answer (false)", "Dump computed reaching types for comparison with other solvers")).append(padOpt("add-tags (false)", "Output points-to results in tags for viewing with the Jimple"))
					.append(padOpt("set-mass (false)", "Calculate statistics about points-to set sizes")).append(padOpt("cs-demand (false)", "After running Spark, refine points-to sets on demand with context information")).append(padOpt("lazy-pts (true)", "Create lazy points-to sets that create context information only when needed.")).append(padOpt("traversal (75000)", "Make the analysis traverse at most this number of nodes per query.")).append(padOpt("passes (10)", "Perform at most this number of refinement iterations."))
					.append(padOpt("geom-pta (false)", "This switch enables/disables the geometric analysis.")).append(padOpt("geom-encoding (Geom)", "Encoding methodology")).append(padVal("Geom (default)", "Geometric Encoding")).append(padVal("HeapIns", "Heap Insensitive Encoding")).append(padVal("PtIns", "PtIns"))
					.append(padOpt("geom-worklist (PQ)", "Worklist type")).append(padVal("PQ (default)", "Priority Queue")).append(padVal("FIFO", "FIFO Queue")).append(padOpt("geom-dump-verbose ()", "Filename for detailed execution log")).append(padOpt("geom-verify-name ()", "Filename for verification file"))
					.append(padOpt("geom-eval (0)", "Precision evaluation methodologies")).append(padOpt("geom-trans (false)", "Transform to context-insensitive result")).append(padOpt("geom-frac-base (40)", "Fractional parameter for precision/performance trade-off")).append(padOpt("geom-blocking (true)", "Enable blocking strategy for recursive calls")).append(padOpt("geom-runs (1)", "Iterations of analysis"))
					.append(padOpt("geom-app-only (true)", "Processing pointers that impact pointers in application code only")).toString();
		}

        if ("cg.paddle".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nPaddle is a BDD-based interprocedural analysis framework. It \nincludes points-to analysis, call graph construction, and \nvarious client analyses.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("verbose (false)", "Print detailed information about the execution of Paddle")).append(padOpt("conf", "Select Paddle configuration")).append(padVal("ofcg (default)", "On-the fly call graph")).append(padVal("cha", "CHA only")).append(padVal("cha-aot", "CHA ahead-of-time callgraph"))
					.append(padVal("ofcg-aot", "OFCG-AOT callgraph")).append(padVal("cha-context-aot", "CHA-Context-AOT callgraph")).append(padVal("ofcg-context-aot", "OFCG-Context-AOT callgraph")).append(padVal("cha-context", "CHA-Context callgraph")).append(padVal("ofcg-context", "OFCG-Context callgraph"))
					.append(padOpt("bdd (false)", "Use BDD version of Paddle")).append(padOpt("order (32)", "")).append(padOpt("dynamic-order", "")).append(padOpt("profile (false)", "Profile BDDs using JeddProfiler")).append(padOpt("verbosegc (false)", "Print memory usage at each BDD garbage collection."))
					.append(padOpt("q", "Select queue implementation")).append(padVal("auto (default)", "Select queue implementation based on bdd option")).append(padVal("trad", "Normal worklist queue implementation")).append(padVal("bdd", "BDD-based queue implementation")).append(padVal("debug", "Debugging queue implementation"))
					.append(padVal("trace", "Tracing queue implementation")).append(padVal("numtrace", "Number-tracing queue implementation")).append(padOpt("backend", "Select BDD backend")).append(padVal("auto (default)", "Select backend based on bdd option")).append(padVal("buddy", "BuDDy backend"))
					.append(padVal("cudd", "CUDD backend")).append(padVal("sable", "SableJBDD backend")).append(padVal("javabdd", "JavaBDD backend")).append(padVal("none", "No BDDs")).append(padOpt("bdd-nodes (0)", "Number of BDD nodes to allocate (0=unlimited)"))
					.append(padOpt("ignore-types (false)", "Make Paddle completely ignore declared types of variables")).append(padOpt("pre-jimplify (false)", "Jimplify all methods before starting Paddle")).append(padOpt("context", "Select context-sensitivity level")).append(padVal("insens (default)", "Builds a context-insensitive call graph")).append(padVal("1cfa", "Builds a 1-CFA call graph"))
					.append(padVal("kcfa", "Builds a k-CFA call graph")).append(padVal("objsens", "Builds an object-sensitive call graph")).append(padVal("kobjsens", "Builds a k-object-sensitive call graph")).append(padVal("uniqkobjsens", "Builds a unique-k-object-sensitive call graph")).append(padVal("threadkobjsens", "Experimental option for thread-entry-point sensitivity"))
					.append(padOpt("k (2)", "")).append(padOpt("context-heap (false)", "Treat allocation sites context-sensitively")).append(padOpt("rta (false)", "Emulate Rapid Type Analysis")).append(padOpt("field-based (false)", "Use a field-based rather than field-sensitive representation")).append(padOpt("types-for-sites (false)", "Represent objects by their actual type rather than allocation site"))
					.append(padOpt("merge-stringbuffer (true)", "Represent all StringBuffers as one object")).append(padOpt("string-constants (false)", "Propagate all string constants, not just class names")).append(padOpt("simulate-natives (true)", "Simulate effects of native methods in standard class library")).append(padOpt("global-nodes-in-natives (false)", "Use global node to model variables in simulations of native methods")).append(padOpt("simple-edges-bidirectional (false)", "Equality-based analysis between variable nodes"))
					.append(padOpt("this-edges (false)", "Use pointer assignment edges to model this parameters")).append(padOpt("precise-newinstance (true)", "Make newInstance only allocate objects of dynamic classes")).append(padOpt("propagator", "Select propagation algorithm")).append(padVal("auto (default)", "Select propagation algorithm based on bdd option")).append(padVal("iter", "Simple iterative algorithm"))
					.append(padVal("worklist", "Fast, worklist-based algorithm")).append(padVal("alias", "Alias-edge based algorithm")).append(padVal("bdd", "BDD-based propagator")).append(padVal("incbdd", "Incrementalized BDD-based propagator")).append(padOpt("set-impl", "Select points-to set implementation"))
					.append(padVal("hash", "Use Java HashSet")).append(padVal("bit", "Bit vector")).append(padVal("hybrid", "Hybrid representation using bit vector for large sets")).append(padVal("array", "Sorted array representation")).append(padVal("heintze", "Heintze's shared bit-vector and overflow list representation"))
					.append(padVal("double (default)", "Double set representation for incremental propagation")).append(padOpt("double-set-old", "Select implementation of points-to set for old part of double set")).append(padVal("hash", "Use Java HashSet")).append(padVal("bit", "Bit vector")).append(padVal("hybrid (default)", "Hybrid representation using bit vector for large sets"))
					.append(padVal("array", "Sorted array representation")).append(padVal("heintze", "Heintze's shared bit-vector and overflow list representation")).append(padOpt("double-set-new", "Select implementation of points-to set for new part of double set")).append(padVal("hash", "Use Java HashSet")).append(padVal("bit", "Bit vector"))
					.append(padVal("hybrid (default)", "Hybrid representation using bit vector for large sets")).append(padVal("array", "Sorted array representation")).append(padVal("heintze", "Heintze's shared bit-vector and overflow list representation")).append(padOpt("context-counts (false)", "Print number of contexts for each method")).append(padOpt("total-context-counts (false)", "Print total number of contexts"))
					.append(padOpt("method-context-counts (false)", "Print number of contexts for each method")).append(padOpt("set-mass (false)", "Calculate statistics about points-to set sizes")).append(padOpt("number-nodes (true)", "Print node numbers in dumps")).toString();
		}

        if ("wstp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nSoot can perform whole-program analyses. In whole-shimple mode, \nSoot applies the contents of the Whole-Shimple Transformation \nPack to the scene as a whole after constructing a call graph for \nthe program. In an unmodified copy of Soot the Whole-Shimple \nTransformation Pack is empty.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("wsop".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nIf Soot is running in whole shimple mode and the Whole-Shimple \nOptimization Pack is enabled, the pack's transformations are \napplied to the scene as a whole after construction of the call \ngraph and application of any enabled Whole-Shimple \nTransformations. In an unmodified copy of Soot the Whole-Shimple \nOptimization Pack is empty.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("wjtp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nSoot can perform whole-program analyses. In whole-program mode, \nSoot applies the contents of the Whole-Jimple Transformation \nPack to the scene as a whole after constructing a call graph for \nthe program.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("wjtp.mhp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nMay Happen in Parallel (MHP) Analyses determine what program \nstatements may be run by different threads concurrently. This \nphase does not perform any transformation.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("wjtp.tn".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Lock Allocator finds critical sections (synchronized \nregions) in Java programs and assigns locks for execution on \nboth optimistic and pessimistic JVMs. It can also be used to \nanalyze the existing locks.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("locking-scheme", "Selects the granularity of the generated lock allocation")).append(padVal("medium-grained (default)", "Use a runtime object for synchronization where possible")).append(padVal("coarse-grained", "Use static objects for synchronization")).append(padVal("single-static", "Use just one static synchronization object for all transactional regions")).append(padVal("leave-original", "Analyse the existing lock structure without making changes"))
					.append(padOpt("avoid-deadlock (true)", "Perform Deadlock Avoidance")).append(padOpt("open-nesting (true)", "Use an open nesting model")).append(padOpt("do-mhp (true)", "Perform a May-Happen-in-Parallel analysis")).append(padOpt("do-tlo (true)", "Perform a Local-Objects analysis")).append(padOpt("print-graph (false)", "Print topological graph of transactions"))
					.append(padOpt("print-table (false)", "Print table of transactions")).append(padOpt("print-debug (false)", "Print debugging info")).toString();
		}

        if ("wjtp.rdc".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nRename duplicated classes when the file system is not case \nsensitive. If the file system is case sensitive, this phase does \nnothing.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("fcn fixed-class-names", "Set ARG for the fixed class names.")).toString();
		}

        if ("wjop".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nIf Soot is running in whole program mode and the Whole-Jimple \nOptimization Pack is enabled, the pack's transformations are \napplied to the scene as a whole after construction of the call \ngraph and application of any enabled Whole-Jimple \nTransformations.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("wjop.smb".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Static Method Binder statically binds monomorphic call \nsites. That is, it searches the call graph for virtual method \ninvocations that can be determined statically to call only a \nsingle implementation of the called method. Then it replaces \nsuch virtual invocations with invocations of a static copy of \nthe single called implementation.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("insert-null-checks (true)", "")).append(padOpt("insert-redundant-casts (true)", "")).append(padOpt("allowed-modifier-changes", "")).append(padVal("unsafe (default)", "")).append(padVal("safe", ""))
					.append(padVal("none", "")).toString();
		}

        if ("wjop.si".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Static Inliner visits all call sites in the call graph in a \nbottom-up fashion, replacing monomorphic calls with inlined \ncopies of the invoked methods.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("rerun-jb (true)", "")).append(padOpt("insert-null-checks (true)", "")).append(padOpt("insert-redundant-casts (true)", "")).append(padOpt("allowed-modifier-changes", "")).append(padVal("unsafe (default)", ""))
					.append(padVal("safe", "")).append(padVal("none", "")).append(padOpt("expansion-factor (3)", "")).append(padOpt("max-container-size (5000)", "")).append(padOpt("max-inlinee-size (20)", ""))
					.toString();
		}

        if ("wjap".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nSome analyses do not transform Jimple body directly, but \nannotate statements or values with tags. Whole-Jimple annotation \npack provides a place for annotation-oriented analyses in whole \nprogram mode.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("wjap.ra".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Rectangular Array Finder traverses Jimple statements based \non the static call graph, and finds array variables which always \nhold rectangular two-dimensional array objects. In Java, a \nmulti-dimensional array is an array of arrays, which means the \nshape of the array can be ragged. Nevertheless, many \napplications use rectangular arrays. Knowing that an array is \nrectangular can be very helpful in proving safe array bounds \nchecks. The Rectangular Array Finder does not change the program \nbeing analyzed. Its results are used by the Array Bound Checker.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("wjap.umt".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nUses the call graph to determine which methods are unreachable \nand adds color tags so they can be highlighted in a source \nbrowser.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("wjap.uft".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nUses the call graph to determine which fields are unreachable \nand adds color tags so they can be highlighted in a source \nbrowser.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("wjap.tqt".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nDetermines which methods and fields have qualifiers that could \nbe tightened. For example: if a field or method has the \nqualifier of public but is only used within the declaring class \nit could be private. This, this field or method is tagged with \ncolor tags so that the results can be highlighted in a source \nbrowser.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("wjap.cgg".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nCreates graphical call graph.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("show-lib-meths (false)", "")).toString();
		}

        if ("wjap.purity".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nPurity anaysis implemented by Antoine Mine and based on the \npaper A Combined Pointer and Purity Analysis for Java Programs \nby Alexandru Salcianu and Martin Rinard.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("dump-summaries (true)", "")).append(padOpt("dump-cg (false)", "")).append(padOpt("dump-intra (false)", "")).append(padOpt("print (true)", "")).append(padOpt("annotate (true)", "Marks pure methods with a purity bytecode attribute"))
					.append(padOpt("verbose (false)", "")).toString();
		}

        if ("shimple".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nShimple Control sets parameters which apply throughout the \ncreation and manipulation of Shimple bodies. Shimple is Soot's \nSSA representation.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("node-elim-opt (true)", "Node elimination optimizations")).append(padOpt("standard-local-names (false)", "Uses naming scheme of the Local Name Standardizer.")).append(padOpt("extended (false)", "Compute extended SSA (SSI) form.")).append(padOpt("debug (false)", "Enables debugging output, if any.")).toString();
		}

        if ("stp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nWhen the Shimple representation is produced, Soot applies the \ncontents of the Shimple Transformation Pack to each method under \nanalysis. This pack contains no transformations in an unmodified \nversion of Soot.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("sop".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Shimple Optimization Pack contains transformations that \nperform optimizations on Shimple, Soot's SSA representation.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("sop.cpf".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nA powerful constant propagator and folder based on an algorithm \nsketched by Cytron et al that takes conditional control flow \ninto account. This optimization demonstrates some of the \nbenefits of SSA -- particularly the fact that Phi nodes \nrepresent natural merge points in the control flow.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("prune-cfg (true)", "Take advantage of CFG optimization opportunities.")).toString();
		}

        if ("jtp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nSoot applies the contents of the Jimple Transformation Pack to \neach method under analysis. This pack contains no \ntransformations in an unmodified version of Soot.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jop".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nWhen Soot's Optimize option is on, Soot applies the Jimple \nOptimization Pack to every JimpleBody in application classes. \nThis section lists the default transformations in the Jimple \nOptimization Pack.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "Eliminates common subexpressions")).toString();
		}

        if ("jop.cse".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Common Subexpression Eliminator runs an available \nexpressions analysis on the method body, then eliminates common \nsubexpressions. This implementation is especially slow, as it \nruns on individual statements rather than on basic blocks. A \nbetter implementation (which would find most common \nsubexpressions, but not all) would use basic blocks instead. \nThis implementation is also slow because the flow universe is \nexplicitly created; it need not be. A better implementation \nwould implicitly compute the kill sets at every node. Because of \nits current slowness, this transformation is not enabled by \ndefault.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("naive-side-effect (false)", "Use naive side effect analysis even if interprocedural information is available")).toString();
		}

        if ("jop.bcm".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nBusy Code Motion is a straightforward implementation of Partial \nRedundancy Elimination. This implementation is not very \naggressive. Lazy Code Motion is an improved version which should \nbe used instead of Busy Code Motion.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("naive-side-effect (false)", "Use a naive side effect analysis even if interprocedural information is available")).toString();
		}

        if ("jop.lcm".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nLazy Code Motion is an enhanced version of Busy Code Motion, a \nPartial Redundancy Eliminator. Before doing Partial Redundancy \nElimination, this optimization performs loop inversion (turning \nwhile loops into do while loops inside an if statement). This \nallows the Partial Redundancy Eliminator to optimize loop \ninvariants of while loops.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("safety", "")).append(padVal("safe (default)", "")).append(padVal("medium", "")).append(padVal("unsafe", "")).append(padOpt("unroll (true)", ""))
					.append(padOpt("naive-side-effect (false)", "Use a naive side effect analysis even if interprocedural information is available")).toString();
		}

        if ("jop.cp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis phase performs cascaded copy propagation.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-regular-locals (false)", "")).append(padOpt("only-stack-locals (false)", "")).toString();
		}

        if ("jop.cpf".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Jimple Constant Propagator and Folder evaluates any \nexpressions consisting entirely of compile-time constants, for \nexample 2 * 3, and replaces the expression with the constant \nresult, in this case 6.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jop.cbf".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Conditional Branch Folder statically evaluates the \nconditional expression of Jimple if statements. If the condition \nis identically true or false, the Folder replaces the \nconditional branch statement with an unconditional goto \nstatement.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jop.dae".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Dead Assignment Eliminator eliminates assignment statements \nto locals whose values are not subsequently used, unless \nevaluating the right-hand side of the assignment may cause \nside-effects.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-tag (false)", "")).append(padOpt("only-stack-locals (false)", "")).toString();
		}

        if ("jop.nce".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nReplaces statements 'if(x!=null) goto y' with 'goto y' if x is \nknown to be non-null or with 'nop' if it is known to be null, \netc. Generates dead code and is hence followed by unreachable \ncode elimination. Disabled by default because it can be \nexpensive on methods with many locals.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jop.uce1".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unreachable Code Eliminator removes unreachable code and \ntraps whose catch blocks are empty.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("remove-unreachable-traps (false)", "")).toString();
		}

        if ("jop.ubf1".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unconditional Branch Folder removes unnecessary `goto' \nstatements from a JimpleBody. If a goto statement's target is \nthe next instruction, then the statement is removed. If a goto's \ntarget is another goto, with target y, then the first \nstatement's target is changed to y. If some if statement's \ntarget is a goto statement, then the if's target can be replaced \nwith the goto's target. (These situations can result from other \noptimizations, and branch folding may itself generate more \nunreachable code.)").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jop.uce2".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nAnother iteration of the Unreachable Code Eliminator.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("remove-unreachable-traps (false)", "")).toString();
		}

        if ("jop.ubf2".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nAnother iteration of the Unconditional Branch Folder.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jop.ule".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Unused Local Eliminator phase removes any unused locals from \nthe method.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jap".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Jimple Annotation Pack contains phases which add annotations \nto Jimple bodies individually (as opposed to the Whole-Jimple \nAnnotation Pack, which adds annotations based on the analysis of \nthe whole program).").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("jap.npc".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Null Pointer Checker finds instruction which have the \npotential to throw NullPointerExceptions and adds annotations \nindicating whether or not the pointer being dereferenced can be \ndetermined statically not to be null.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("only-array-ref (false)", "Annotate only array references")).append(padOpt("profiling (false)", "Insert instructions to count safe pointer accesses")).toString();
		}

        if ("jap.npcolorer".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nProduce colour tags that the Soot plug-in for Eclipse can use to \nhighlight null and non-null references.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.abc".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Array Bound Checker performs a static analysis to determine \nwhich array bounds checks may safely be eliminated and then \nannotates statements with the results of the analysis. If Soot \nis in whole-program mode, the Array Bound Checker can use the \nresults provided by the Rectangular Array Finder.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("with-all (false)", "")).append(padOpt("with-cse (false)", "")).append(padOpt("with-arrayref (false)", "")).append(padOpt("with-fieldref (false)", "")).append(padOpt("with-classfield (false)", ""))
					.append(padOpt("with-rectarray (false)", "")).append(padOpt("profiling (false)", "Profile the results of array bounds check analysis.")).append(padOpt("add-color-tags (false)", "Add color tags to results of array bound check analysis.")).toString();
		}

        if ("jap.profiling".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Profiling Generator inserts the method invocations required \nto initialize and to report the results of any profiling \nperformed by the Null Pointer Checker and Array Bound Checker. \nUsers of the Profiling Generator must provide a MultiCounter \nclass implementing the methods invoked. For details, see the \nProfilingGenerator source code.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("notmainentry (false)", "Instrument runBenchmark() instead of main()")).toString();
		}

        if ("jap.sea".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Side Effect Tagger uses the active invoke graph to produce \nside-effect attributes, as described in the Spark thesis, \nchapter 6.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("naive (false)", "")).toString();
		}

        if ("jap.fieldrw".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Field Read/Write Tagger uses the active invoke graph to \nproduce tags indicating which fields may be read or written by \neach statement, including invoke statements.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("threshold (100)", "")).toString();
		}

        if ("jap.cgtagger".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Call Graph Tagger produces LinkTags based on the call graph. \nThe Eclipse plugin uses these tags to produce linked popup lists \nwhich indicate the source and target methods of the statement. \nSelecting a link from the list moves the cursor to the indicated \nmethod.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.parity".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Parity Tagger produces StringTags and ColorTags indicating \nthe parity of a variable (even, odd, top, or bottom). The \neclipse plugin can use tooltips and variable colouring to \ndisplay the information in these tags. For example, even \nvariables (such as x in x = 2) are coloured yellow.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.pat".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nFor each method with parameters of reference type, this tagger \nindicates the aliasing relationships between the parameters \nusing colour tags. Parameters that may be aliased are the same \ncolour. Parameters that may not be aliased are in different \ncolours.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.lvtagger".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nColors live variables.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.rdtagger".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nFor each use of a local in a stmt creates a link to the reaching \ndef.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.che".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nIndicates whether cast checks can be eliminated.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.umt".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nWhen the whole-program analysis determines a method to be \nunreachable, this transformer inserts an assertion into the \nmethod to check that it is indeed unreachable.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.lit".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nAn expression whose operands are constant or have reaching \ndefinitions from outside the loop body are tagged as loop \ninvariant.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("jap.aet".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nA each statement a set of available expressions is after the \nstatement is added as a tag.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", ""))
					.append(padOpt("kind", "")).append(padVal("optimistic (default)", "")).append(padVal("pessimistic", "")).toString();
		}

        if ("jap.dmt".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nProvides link tags at a statement to all of the satements \ndominators.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("gb".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Grimp Body Creation phase creates a GrimpBody for each \nsource method. It is run only if the output format is grimp or \ngrimple, or if class files are being output and the Via Grimp \noption has been specified.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("gb.a1".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Grimp Pre-folding Aggregator combines some local variables, \nfinding definitions with only a single use and removing the \ndefinition after replacing the use with the definition's \nright-hand side, if it is safe to do so. While the mechanism is \nthe same as that employed by the Jimple Local Aggregator, there \nis more scope for aggregation because of Grimp's more \ncomplicated expressions.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-stack-locals (true)", "")).toString();
		}

        if ("gb.cf".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Grimp Constructor Folder combines new statements with the \nspecialinvoke statement that calls the new object's constructor. \nFor example, it turns r2 = new java.util.ArrayList; r2.init(); \ninto r2 = new java.util.ArrayList();").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("gb.a2".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Grimp Post-folding Aggregator combines local variables after \nconstructors have been folded. Constructor folding typically \nintroduces new opportunities for aggregation, since when a \nsequence of instructions like r2 = new java.util.ArrayList; \nr2.init(); r3 = r2 is replaced by r2 = new \njava.util.ArrayList(); r3 = r2 the invocation of init no longer \nrepresents a potential side-effect separating the two \ndefinitions, so they can be combined into r3 = new \njava.util.ArrayList(); (assuming there are no subsequent uses of \nr2).").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("only-stack-locals (true)", "")).toString();
		}

        if ("gb.ule".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis phase removes any locals that are unused after constructor \nfolding and aggregation.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("gop".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Grimp Optimization pack performs optimizations on GrimpBodys \n(currently there are no optimizations performed specifically on \nGrimpBodys, and the pack is empty). It is run only if the output \nformat is grimp or grimple, or if class files are being output \nand the Via Grimp option has been specified.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("bb".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Baf Body Creation phase creates a BafBody from each source \nmethod. It is run if the output format is baf or b or asm or a, \nor if class files are being output and the Via Grimp option has \nnot been specified.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("bb.lso".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Load Store Optimizer replaces some combinations of loads to \nand stores from local variables with stack instructions. A \nsimple example would be the replacement of store.r $r2; load.r \n$r2; with dup1.r in cases where the value of r2 is not used \nsubsequently.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("debug (false)", "")).append(padOpt("inter (false)", "")).append(padOpt("sl (true)", "")).append(padOpt("sl2 (false)", "")).append(padOpt("sll (true)", ""))
					.append(padOpt("sll2 (false)", "")).toString();
		}

        if ("bb.sco".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe store chain optimizer detects chains of push/store pairs \nthat write to the same variable and only retains the last store. \nIt removes the unnecessary previous push/stores that are \nsubsequently overwritten.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("bb.pho".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nApplies peephole optimizations to the Baf intermediate \nrepresentation. Individual optimizations must be implemented by \nclasses implementing the Peephole interface. The Peephole \nOptimizer reads the names of the Peephole classes at runtime \nfrom the file peephole.dat and loads them dynamically. Then it \ncontinues to apply the Peepholes repeatedly until none of them \nare able to perform any further optimizations. Soot provides \nonly one Peephole, named ExamplePeephole, which is not enabled \nby the delivered peephole.dat file. ExamplePeephole removes all \ncheckcast instructions.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("bb.ule".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThis phase removes any locals that are unused after load store \noptimization and peephole optimization.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("bb.lp".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Local Packer attempts to minimize the number of local \nvariables required in a method by reusing the same variable for \ndisjoint DU-UD webs. Conceptually, it is the inverse of the \nLocal Splitter.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("unsplit-original-locals (false)", "")).toString();
		}

        if ("bop".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Baf Optimization pack performs optimizations on BafBodys \n(currently there are no optimizations performed specifically on \nBafBodys, and the pack is empty). It is run only if the output \nformat is baf or b or asm or a, or if class files are being \noutput and the Via Grimp option has not been specified.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("tag".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Tag Aggregator pack aggregates tags attached to individual \nunits into a code attribute for each method, so that these \nattributes can be encoded in Java class files.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("tag.ln".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Line Number Tag Aggregator aggregates line number tags.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("tag.an".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Array Bounds and Null Pointer Tag Aggregator aggregates tags \nproduced by the Array Bound Checker and Null Pointer Checker.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("tag.dep".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Dependence Tag Aggregator aggregates tags produced by the \nSide Effect Tagger.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("tag.fieldrw".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe Field Read/Write Tag Aggregator aggregates field read/write \ntags produced by the Field Read/Write Tagger, phase jap.fieldrw.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("db".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe decompile (Dava) option is set using the -f dava options in \nSoot. Options provided by Dava are added to this dummy phase so \nas not to clutter the soot general arguments. -p db (option \nname):(value) will be used to set all required values for Dava.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", ""))
					.append(padOpt("source-is-javac (true)", "")).toString();
		}

        if ("db.transformations".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nThe transformations implemented using AST Traversal and \nstructural flow analses on Dava's AST").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("db.renamer".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nIf set, the renaming analyses implemented in Dava are applied to \neach method body being decompiled. The analyses use heuristics \nto choose potentially better names for local variables. (As of \nFebruary 14th 2006, work is still under progress on these \nanalyses (dava.toolkits.base.renamer).").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (false)", "")).toString();
		}

        if ("db.deobfuscate".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nCertain analyses make sense only when the bytecode is obfuscated \ncode. There are plans to implement such analyses and apply them \non methods only if this flag is set. Dead Code elimination which \nincludes removing code guarded by some condition which is always \nfalse or always true is one such analysis. Another suggested \nanalysis is giving default names to classes and fields. \nOnfuscators love to use weird names for fields and classes and \neven a simple re-naming of these could be a good help to the \nuser. Another more advanced analysis would be to check for \nredundant constant fields added by obfuscators and then remove \nuses of these constant fields from the code.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        if ("db.force-recompile".equals(phaseName)) {
			return new StringBuilder().append("Phase ").append(phaseName).append(":\n").append("\nWhile decompiling we have to be clear what our aim is: do we \nwant to convert bytecode to Java syntax and stay as close to the \nactual execution of bytecode or do we want recompilably Java \nsource representing the bytecode. This distinction is important \nbecause some restrictions present in Java source are absent from \nthe bytecode. Examples of this include that fact that in Java a \ncall to a constructor or super needs to be the first statement \nin a constructors body. This restriction is absent from the \nbytecode. Similarly final fields HAVE to be initialized once and \nonly once in either the static initializer (static fields) or \nall the constructors (non-static fields). Additionally the \nfields should be initialized on all possible execution paths. \nThese restrictions are again absent from the bytecode. In doing \na one-one conversion of bytecode to Java source then no attempt \nshould be made to fix any of these and similar problems in the \nJava source. However, if the aim is to get recompilable code \nthen these and similar issues need to be fixed. Setting the \nforce-recompilability flag will ensure that the decompiler tries \nits best to produce recompilable Java source.").append("\n\nRecognized options (with default values):\n").append(padOpt("enabled (true)", "")).toString();
		}

        return "Unrecognized phase: " + phaseName;
    }

	public static String getDeclaredOptionsForPhase(String phaseName) {
        if ("jb".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "use-original-names",
                    "preserve-source-annotations",
                    "stabilize-local-names",
                    "model-lambdametafactory"
            );
		}

        if ("jb.dtr".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jb.ese".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jb.ls".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jb.a".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-stack-locals"
            );
		}

        if ("jb.ule".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jb.tr".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "use-older-type-assigner",
                    "compare-type-assigners",
                    "ignore-nullpointer-dereferences"
            );
		}

        if ("jb.ulp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "unsplit-original-locals"
            );
		}

        if ("jb.lns".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-stack-locals",
                    "sort-locals"
            );
		}

        if ("jb.cp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-regular-locals",
                    "only-stack-locals"
            );
		}

        if ("jb.dae".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-stack-locals"
            );
		}

        if ("jb.cp-ule".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jb.lp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "unsplit-original-locals"
            );
		}

        if ("jb.ne".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jb.uce".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "remove-unreachable-traps"
            );
		}

        if ("jb.tt".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jj".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "use-original-names"
            );
		}

        if ("jj.ls".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jj.a".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-stack-locals"
            );
		}

        if ("jj.ule".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jj.tr".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jj.ulp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "unsplit-original-locals"
            );
		}

        if ("jj.lns".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-stack-locals"
            );
		}

        if ("jj.cp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-regular-locals",
                    "only-stack-locals"
            );
		}

        if ("jj.dae".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-stack-locals"
            );
		}

        if ("jj.cp-ule".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jj.lp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "unsplit-original-locals"
            );
		}

        if ("jj.ne".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jj.uce".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjpp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjpp.cimbt".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "verbose"
            );
		}

        if ("wspp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("cg".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "safe-forname",
                    "safe-newinstance",
                    "library",
                    "verbose",
                    "jdkver",
                    "all-reachable",
                    "implicit-entry",
                    "trim-clinit",
                    "reflection-log",
                    "guards",
                    "types-for-invoke",
                    "resolve-all-abstract-invokes"
            );
		}

        if ("cg.cha".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "verbose",
                    "apponly"
            );
		}

        if ("cg.spark".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "verbose",
                    "ignore-types",
                    "force-gc",
                    "pre-jimplify",
                    "apponly",
                    "vta",
                    "rta",
                    "field-based",
                    "types-for-sites",
                    "merge-stringbuffer",
                    "string-constants",
                    "simulate-natives",
                    "empties-as-allocs",
                    "simple-edges-bidirectional",
                    "on-fly-cg",
                    "simplify-offline",
                    "simplify-sccs",
                    "ignore-types-for-sccs",
                    "propagator",
                    "set-impl",
                    "double-set-old",
                    "double-set-new",
                    "dump-html",
                    "dump-pag",
                    "dump-solution",
                    "topo-sort",
                    "dump-types",
                    "class-method-var",
                    "dump-answer",
                    "add-tags",
                    "set-mass",
                    "cs-demand",
                    "lazy-pts",
                    "traversal",
                    "passes",
                    "geom-pta",
                    "geom-encoding",
                    "geom-worklist",
                    "geom-dump-verbose",
                    "geom-verify-name",
                    "geom-eval",
                    "geom-trans",
                    "geom-frac-base",
                    "geom-blocking",
                    "geom-runs",
                    "geom-app-only"
            );
		}

        if ("cg.paddle".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "verbose",
                    "conf",
                    "bdd",
                    "order",
                    "dynamic-order",
                    "profile",
                    "verbosegc",
                    "q",
                    "backend",
                    "bdd-nodes",
                    "ignore-types",
                    "pre-jimplify",
                    "context",
                    "k",
                    "context-heap",
                    "rta",
                    "field-based",
                    "types-for-sites",
                    "merge-stringbuffer",
                    "string-constants",
                    "simulate-natives",
                    "global-nodes-in-natives",
                    "simple-edges-bidirectional",
                    "this-edges",
                    "precise-newinstance",
                    "propagator",
                    "set-impl",
                    "double-set-old",
                    "double-set-new",
                    "context-counts",
                    "total-context-counts",
                    "method-context-counts",
                    "set-mass",
                    "number-nodes"
            );
		}

        if ("wstp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wsop".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjtp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjtp.mhp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjtp.tn".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "locking-scheme",
                    "avoid-deadlock",
                    "open-nesting",
                    "do-mhp",
                    "do-tlo",
                    "print-graph",
                    "print-table",
                    "print-debug"
            );
		}

        if ("wjtp.rdc".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "fcn fixed-class-names"
            );
		}

        if ("wjop".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjop.smb".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "insert-null-checks",
                    "insert-redundant-casts",
                    "allowed-modifier-changes"
            );
		}

        if ("wjop.si".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "rerun-jb",
                    "insert-null-checks",
                    "insert-redundant-casts",
                    "allowed-modifier-changes",
                    "expansion-factor",
                    "max-container-size",
                    "max-inlinee-size"
            );
		}

        if ("wjap".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjap.ra".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjap.umt".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjap.uft".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjap.tqt".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("wjap.cgg".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "show-lib-meths"
            );
		}

        if ("wjap.purity".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "dump-summaries",
                    "dump-cg",
                    "dump-intra",
                    "print",
                    "annotate",
                    "verbose"
            );
		}

        if ("shimple".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "node-elim-opt",
                    "standard-local-names",
                    "extended",
                    "debug"
            );
		}

        if ("stp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("sop".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("sop.cpf".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "prune-cfg"
            );
		}

        if ("jtp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jop".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jop.cse".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "naive-side-effect"
            );
		}

        if ("jop.bcm".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "naive-side-effect"
            );
		}

        if ("jop.lcm".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "safety",
                    "unroll",
                    "naive-side-effect"
            );
		}

        if ("jop.cp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-regular-locals",
                    "only-stack-locals"
            );
		}

        if ("jop.cpf".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jop.cbf".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jop.dae".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-tag",
                    "only-stack-locals"
            );
		}

        if ("jop.nce".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jop.uce1".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "remove-unreachable-traps"
            );
		}

        if ("jop.ubf1".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jop.uce2".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "remove-unreachable-traps"
            );
		}

        if ("jop.ubf2".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jop.ule".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.npc".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-array-ref",
                    "profiling"
            );
		}

        if ("jap.npcolorer".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.abc".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "with-all",
                    "with-cse",
                    "with-arrayref",
                    "with-fieldref",
                    "with-classfield",
                    "with-rectarray",
                    "profiling",
                    "add-color-tags"
            );
		}

        if ("jap.profiling".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "notmainentry"
            );
		}

        if ("jap.sea".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "naive"
            );
		}

        if ("jap.fieldrw".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "threshold"
            );
		}

        if ("jap.cgtagger".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.parity".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.pat".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.lvtagger".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.rdtagger".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.che".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.umt".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.lit".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("jap.aet".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "kind"
            );
		}

        if ("jap.dmt".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("gb".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("gb.a1".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-stack-locals"
            );
		}

        if ("gb.cf".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("gb.a2".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "only-stack-locals"
            );
		}

        if ("gb.ule".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("gop".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("bb".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("bb.lso".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "debug",
                    "inter",
                    "sl",
                    "sl2",
                    "sll",
                    "sll2"
            );
		}

        if ("bb.sco".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("bb.pho".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("bb.ule".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("bb.lp".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "unsplit-original-locals"
            );
		}

        if ("bop".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("tag".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("tag.ln".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("tag.an".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("tag.dep".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("tag.fieldrw".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("db".equals(phaseName)) {
			return String.join(" ", 
                    "enabled",
                    "source-is-javac"
            );
		}

        if ("db.transformations".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("db.renamer".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("db.deobfuscate".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        if ("db.force-recompile".equals(phaseName)) {
			return String.join(" ", 
                    "enabled"
            );
		}

        // The default set of options is just enabled.
        return "enabled";
    }

	public static String getDefaultOptionsForPhase(String phaseName) {
        if ("jb".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("use-original-names:false ").append("preserve-source-annotations:false ").append("stabilize-local-names:false ").append("model-lambdametafactory:true ").toString();
		}

        if ("jb.dtr".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jb.ese".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jb.ls".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jb.a".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-stack-locals:true ").toString();
		}

        if ("jb.ule".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jb.tr".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("use-older-type-assigner:false ").append("compare-type-assigners:false ").append("ignore-nullpointer-dereferences:false ").toString();
		}

        if ("jb.ulp".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("unsplit-original-locals:true ").toString();
		}

        if ("jb.lns".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-stack-locals:false ").append("sort-locals:false ").toString();
		}

        if ("jb.cp".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-regular-locals:false ").append("only-stack-locals:true ").toString();
		}

        if ("jb.dae".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-stack-locals:true ").toString();
		}

        if ("jb.cp-ule".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jb.lp".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("unsplit-original-locals:false ").toString();
		}

        if ("jb.ne".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jb.uce".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("remove-unreachable-traps:true ").toString();
		}

        if ("jb.tt".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jj".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("use-original-names:true ").toString();
		}

        if ("jj.ls".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jj.a".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-stack-locals:true ").toString();
		}

        if ("jj.ule".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jj.tr".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jj.ulp".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("unsplit-original-locals:false ").toString();
		}

        if ("jj.lns".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-stack-locals:false ").toString();
		}

        if ("jj.cp".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-regular-locals:false ").append("only-stack-locals:true ").toString();
		}

        if ("jj.dae".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-stack-locals:true ").toString();
		}

        if ("jj.cp-ule".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jj.lp".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("unsplit-original-locals:false ").toString();
		}

        if ("jj.ne".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jj.uce".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("wjpp".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("wjpp.cimbt".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("verbose:false ").toString();
		}

        if ("wspp".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("cg".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("safe-forname:false ").append("safe-newinstance:false ").append("library:disabled ").append("verbose:false ").append("jdkver:3 ").append("all-reachable:false ")
					.append("implicit-entry:true ").append("trim-clinit:true ").append("guards:ignore ").append("types-for-invoke:false ").append("resolve-all-abstract-invokes:false ").toString();
		}

        if ("cg.cha".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("verbose:false ").append("apponly:false ").toString();
		}

        if ("cg.spark".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("verbose:false ").append("ignore-types:false ").append("force-gc:false ").append("pre-jimplify:false ").append("apponly:false ").append("vta:false ")
					.append("rta:false ").append("field-based:false ").append("types-for-sites:false ").append("merge-stringbuffer:true ").append("string-constants:false ").append("simulate-natives:true ").append("empties-as-allocs:false ").append("simple-edges-bidirectional:false ").append("on-fly-cg:true ")
					.append("simplify-offline:false ").append("simplify-sccs:false ").append("ignore-types-for-sccs:false ").append("propagator:worklist ").append("set-impl:double ").append("double-set-old:hybrid ").append("double-set-new:hybrid ").append("dump-html:false ").append("dump-pag:false ")
					.append("dump-solution:false ").append("topo-sort:false ").append("dump-types:true ").append("class-method-var:true ").append("dump-answer:false ").append("add-tags:false ").append("set-mass:false ").append("cs-demand:false ").append("lazy-pts:true ")
					.append("traversal:75000 ").append("passes:10 ").append("geom-pta:false ").append("geom-encoding:Geom ").append("geom-encoding:Geom ").append("geom-worklist:PQ ").append("geom-worklist:PQ ").append("geom-dump-verbose: ").append("geom-verify-name: ")
					.append("geom-eval:0 ").append("geom-trans:false ").append("geom-frac-base:40 ").append("geom-blocking:true ").append("geom-runs:1 ").append("geom-app-only:true ").toString();
		}

        if ("cg.paddle".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("verbose:false ").append("conf:ofcg ").append("bdd:false ").append("order:32 ").append("profile:false ").append("verbosegc:false ")
					.append("q:auto ").append("backend:auto ").append("bdd-nodes:0 ").append("ignore-types:false ").append("pre-jimplify:false ").append("context:insens ").append("k:2 ").append("context-heap:false ").append("rta:false ")
					.append("field-based:false ").append("types-for-sites:false ").append("merge-stringbuffer:true ").append("string-constants:false ").append("simulate-natives:true ").append("global-nodes-in-natives:false ").append("simple-edges-bidirectional:false ").append("this-edges:false ").append("precise-newinstance:true ")
					.append("propagator:auto ").append("set-impl:double ").append("double-set-old:hybrid ").append("double-set-new:hybrid ").append("context-counts:false ").append("total-context-counts:false ").append("method-context-counts:false ").append("set-mass:false ").append("number-nodes:true ")
					.toString();
		}

        if ("wstp".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("wsop".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("wjtp".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("wjtp.mhp".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("wjtp.tn".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("locking-scheme:medium-grained ").append("avoid-deadlock:true ").append("open-nesting:true ").append("do-mhp:true ").append("do-tlo:true ").append("print-graph:false ")
					.append("print-table:false ").append("print-debug:false ").toString();
		}

        if ("wjtp.rdc".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("wjop".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("wjop.smb".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("insert-null-checks:true ").append("insert-redundant-casts:true ").append("allowed-modifier-changes:unsafe ").toString();
		}

        if ("wjop.si".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("rerun-jb:true ").append("insert-null-checks:true ").append("insert-redundant-casts:true ").append("allowed-modifier-changes:unsafe ").append("expansion-factor:3 ").append("max-container-size:5000 ")
					.append("max-inlinee-size:20 ").toString();
		}

        if ("wjap".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("wjap.ra".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("wjap.umt".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("wjap.uft".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("wjap.tqt".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("wjap.cgg".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("show-lib-meths:false ").toString();
		}

        if ("wjap.purity".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("dump-summaries:true ").append("dump-cg:false ").append("dump-intra:false ").append("print:true ").append("annotate:true ").append("verbose:false ")
					.toString();
		}

        if ("shimple".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("node-elim-opt:true ").append("standard-local-names:false ").append("extended:false ").append("debug:false ").toString();
		}

        if ("stp".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("sop".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("sop.cpf".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("prune-cfg:true ").toString();
		}

        if ("jtp".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jop".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jop.cse".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("naive-side-effect:false ").toString();
		}

        if ("jop.bcm".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("naive-side-effect:false ").toString();
		}

        if ("jop.lcm".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("safety:safe ").append("unroll:true ").append("naive-side-effect:false ").toString();
		}

        if ("jop.cp".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-regular-locals:false ").append("only-stack-locals:false ").toString();
		}

        if ("jop.cpf".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jop.cbf".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jop.dae".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-tag:false ").append("only-stack-locals:false ").toString();
		}

        if ("jop.nce".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jop.uce1".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("remove-unreachable-traps:false ").toString();
		}

        if ("jop.ubf1".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jop.uce2".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("remove-unreachable-traps:false ").toString();
		}

        if ("jop.ubf2".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jop.ule".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jap".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("jap.npc".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("only-array-ref:false ").append("profiling:false ").toString();
		}

        if ("jap.npcolorer".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.abc".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("with-all:false ").append("with-cse:false ").append("with-arrayref:false ").append("with-fieldref:false ").append("with-classfield:false ").append("with-rectarray:false ")
					.append("profiling:false ").append("add-color-tags:false ").toString();
		}

        if ("jap.profiling".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("notmainentry:false ").toString();
		}

        if ("jap.sea".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("naive:false ").toString();
		}

        if ("jap.fieldrw".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("threshold:100 ").toString();
		}

        if ("jap.cgtagger".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.parity".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.pat".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.lvtagger".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.rdtagger".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.che".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.umt".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.lit".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("jap.aet".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:false ").append("kind:optimistic ").toString();
		}

        if ("jap.dmt".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("gb".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("gb.a1".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-stack-locals:true ").toString();
		}

        if ("gb.cf".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("gb.a2".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("only-stack-locals:true ").toString();
		}

        if ("gb.ule".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("gop".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("bb".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("bb.lso".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("debug:false ").append("inter:false ").append("sl:true ").append("sl2:false ").append("sll:true ").append("sll2:false ")
					.toString();
		}

        if ("bb.sco".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("bb.pho".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("bb.ule".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("bb.lp".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("unsplit-original-locals:false ").toString();
		}

        if ("bop".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("tag".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("tag.ln".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("tag.an".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("tag.dep".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("tag.fieldrw".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("db".equals(phaseName)) {
			return new StringBuilder().append("").append("enabled:true ").append("source-is-javac:true ").toString();
		}

        if ("db.transformations".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("db.renamer".equals(phaseName)) {
			return ""
                    + "enabled:false ";
		}

        if ("db.deobfuscate".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        if ("db.force-recompile".equals(phaseName)) {
			return ""
                    + "enabled:true ";
		}

        // The default default value is enabled.
        return "enabled";
    }

	public void warnForeignPhase(String phaseName) {
        if (false
                || "jb".equals(phaseName)
                || "jb.dtr".equals(phaseName)
                || "jb.ese".equals(phaseName)
                || "jb.ls".equals(phaseName)
                || "jb.a".equals(phaseName)
                || "jb.ule".equals(phaseName)
                || "jb.tr".equals(phaseName)
                || "jb.ulp".equals(phaseName)
                || "jb.lns".equals(phaseName)
                || "jb.cp".equals(phaseName)
                || "jb.dae".equals(phaseName)
                || "jb.cp-ule".equals(phaseName)
                || "jb.lp".equals(phaseName)
                || "jb.ne".equals(phaseName)
                || "jb.uce".equals(phaseName)
                || "jb.tt".equals(phaseName)
                || "jj".equals(phaseName)
                || "jj.ls".equals(phaseName)
                || "jj.a".equals(phaseName)
                || "jj.ule".equals(phaseName)
                || "jj.tr".equals(phaseName)
                || "jj.ulp".equals(phaseName)
                || "jj.lns".equals(phaseName)
                || "jj.cp".equals(phaseName)
                || "jj.dae".equals(phaseName)
                || "jj.cp-ule".equals(phaseName)
                || "jj.lp".equals(phaseName)
                || "jj.ne".equals(phaseName)
                || "jj.uce".equals(phaseName)
                || "wjpp".equals(phaseName)
                || "wjpp.cimbt".equals(phaseName)
                || "wspp".equals(phaseName)
                || "cg".equals(phaseName)
                || "cg.cha".equals(phaseName)
                || "cg.spark".equals(phaseName)
                || "cg.paddle".equals(phaseName)
                || "wstp".equals(phaseName)
                || "wsop".equals(phaseName)
                || "wjtp".equals(phaseName)
                || "wjtp.mhp".equals(phaseName)
                || "wjtp.tn".equals(phaseName)
                || "wjtp.rdc".equals(phaseName)
                || "wjop".equals(phaseName)
                || "wjop.smb".equals(phaseName)
                || "wjop.si".equals(phaseName)
                || "wjap".equals(phaseName)
                || "wjap.ra".equals(phaseName)
                || "wjap.umt".equals(phaseName)
                || "wjap.uft".equals(phaseName)
                || "wjap.tqt".equals(phaseName)
                || "wjap.cgg".equals(phaseName)
                || "wjap.purity".equals(phaseName)
                || "shimple".equals(phaseName)
                || "stp".equals(phaseName)
                || "sop".equals(phaseName)
                || "sop.cpf".equals(phaseName)
                || "jtp".equals(phaseName)
                || "jop".equals(phaseName)
                || "jop.cse".equals(phaseName)
                || "jop.bcm".equals(phaseName)
                || "jop.lcm".equals(phaseName)
                || "jop.cp".equals(phaseName)
                || "jop.cpf".equals(phaseName)
                || "jop.cbf".equals(phaseName)
                || "jop.dae".equals(phaseName)
                || "jop.nce".equals(phaseName)
                || "jop.uce1".equals(phaseName)
                || "jop.ubf1".equals(phaseName)
                || "jop.uce2".equals(phaseName)
                || "jop.ubf2".equals(phaseName)
                || "jop.ule".equals(phaseName)
                || "jap".equals(phaseName)
                || "jap.npc".equals(phaseName)
                || "jap.npcolorer".equals(phaseName)
                || "jap.abc".equals(phaseName)
                || "jap.profiling".equals(phaseName)
                || "jap.sea".equals(phaseName)
                || "jap.fieldrw".equals(phaseName)
                || "jap.cgtagger".equals(phaseName)
                || "jap.parity".equals(phaseName)
                || "jap.pat".equals(phaseName)
                || "jap.lvtagger".equals(phaseName)
                || "jap.rdtagger".equals(phaseName)
                || "jap.che".equals(phaseName)
                || "jap.umt".equals(phaseName)
                || "jap.lit".equals(phaseName)
                || "jap.aet".equals(phaseName)
                || "jap.dmt".equals(phaseName)
                || "gb".equals(phaseName)
                || "gb.a1".equals(phaseName)
                || "gb.cf".equals(phaseName)
                || "gb.a2".equals(phaseName)
                || "gb.ule".equals(phaseName)
                || "gop".equals(phaseName)
                || "bb".equals(phaseName)
                || "bb.lso".equals(phaseName)
                || "bb.sco".equals(phaseName)
                || "bb.pho".equals(phaseName)
                || "bb.ule".equals(phaseName)
                || "bb.lp".equals(phaseName)
                || "bop".equals(phaseName)
                || "tag".equals(phaseName)
                || "tag.ln".equals(phaseName)
                || "tag.an".equals(phaseName)
                || "tag.dep".equals(phaseName)
                || "tag.fieldrw".equals(phaseName)
                || "db".equals(phaseName)
                || "db.transformations".equals(phaseName)
                || "db.renamer".equals(phaseName)
                || "db.deobfuscate".equals(phaseName)
                || "db.force-recompile".equals(phaseName)
        ) {
			return;
		}

        G.v().out.println(new StringBuilder().append("Warning: Phase ").append(phaseName).append(" is not a standard Soot phase listed in XML files.").toString());
    }

	public void warnNonexistentPhase() {
        if (!PackManager.v().hasPhase("jb")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb");
		}
        if (!PackManager.v().hasPhase("jb.dtr")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.dtr");
		}
        if (!PackManager.v().hasPhase("jb.ese")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.ese");
		}
        if (!PackManager.v().hasPhase("jb.ls")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.ls");
		}
        if (!PackManager.v().hasPhase("jb.a")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.a");
		}
        if (!PackManager.v().hasPhase("jb.ule")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.ule");
		}
        if (!PackManager.v().hasPhase("jb.tr")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.tr");
		}
        if (!PackManager.v().hasPhase("jb.ulp")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.ulp");
		}
        if (!PackManager.v().hasPhase("jb.lns")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.lns");
		}
        if (!PackManager.v().hasPhase("jb.cp")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.cp");
		}
        if (!PackManager.v().hasPhase("jb.dae")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.dae");
		}
        if (!PackManager.v().hasPhase("jb.cp-ule")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.cp-ule");
		}
        if (!PackManager.v().hasPhase("jb.lp")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.lp");
		}
        if (!PackManager.v().hasPhase("jb.ne")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.ne");
		}
        if (!PackManager.v().hasPhase("jb.uce")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.uce");
		}
        if (!PackManager.v().hasPhase("jb.tt")) {
			G.v().out.println("Warning: Options exist for non-existent phase jb.tt");
		}
        if (!PackManager.v().hasPhase("jj")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj");
		}
        if (!PackManager.v().hasPhase("jj.ls")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.ls");
		}
        if (!PackManager.v().hasPhase("jj.a")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.a");
		}
        if (!PackManager.v().hasPhase("jj.ule")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.ule");
		}
        if (!PackManager.v().hasPhase("jj.tr")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.tr");
		}
        if (!PackManager.v().hasPhase("jj.ulp")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.ulp");
		}
        if (!PackManager.v().hasPhase("jj.lns")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.lns");
		}
        if (!PackManager.v().hasPhase("jj.cp")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.cp");
		}
        if (!PackManager.v().hasPhase("jj.dae")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.dae");
		}
        if (!PackManager.v().hasPhase("jj.cp-ule")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.cp-ule");
		}
        if (!PackManager.v().hasPhase("jj.lp")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.lp");
		}
        if (!PackManager.v().hasPhase("jj.ne")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.ne");
		}
        if (!PackManager.v().hasPhase("jj.uce")) {
			G.v().out.println("Warning: Options exist for non-existent phase jj.uce");
		}
        if (!PackManager.v().hasPhase("wjpp")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjpp");
		}
        if (!PackManager.v().hasPhase("wjpp.cimbt")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjpp.cimbt");
		}
        if (!PackManager.v().hasPhase("wspp")) {
			G.v().out.println("Warning: Options exist for non-existent phase wspp");
		}
        if (!PackManager.v().hasPhase("cg")) {
			G.v().out.println("Warning: Options exist for non-existent phase cg");
		}
        if (!PackManager.v().hasPhase("cg.cha")) {
			G.v().out.println("Warning: Options exist for non-existent phase cg.cha");
		}
        if (!PackManager.v().hasPhase("cg.spark")) {
			G.v().out.println("Warning: Options exist for non-existent phase cg.spark");
		}
        if (!PackManager.v().hasPhase("cg.paddle")) {
			G.v().out.println("Warning: Options exist for non-existent phase cg.paddle");
		}
        if (!PackManager.v().hasPhase("wstp")) {
			G.v().out.println("Warning: Options exist for non-existent phase wstp");
		}
        if (!PackManager.v().hasPhase("wsop")) {
			G.v().out.println("Warning: Options exist for non-existent phase wsop");
		}
        if (!PackManager.v().hasPhase("wjtp")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjtp");
		}
        if (!PackManager.v().hasPhase("wjtp.mhp")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjtp.mhp");
		}
        if (!PackManager.v().hasPhase("wjtp.tn")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjtp.tn");
		}
        if (!PackManager.v().hasPhase("wjtp.rdc")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjtp.rdc");
		}
        if (!PackManager.v().hasPhase("wjop")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjop");
		}
        if (!PackManager.v().hasPhase("wjop.smb")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjop.smb");
		}
        if (!PackManager.v().hasPhase("wjop.si")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjop.si");
		}
        if (!PackManager.v().hasPhase("wjap")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjap");
		}
        if (!PackManager.v().hasPhase("wjap.ra")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjap.ra");
		}
        if (!PackManager.v().hasPhase("wjap.umt")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjap.umt");
		}
        if (!PackManager.v().hasPhase("wjap.uft")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjap.uft");
		}
        if (!PackManager.v().hasPhase("wjap.tqt")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjap.tqt");
		}
        if (!PackManager.v().hasPhase("wjap.cgg")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjap.cgg");
		}
        if (!PackManager.v().hasPhase("wjap.purity")) {
			G.v().out.println("Warning: Options exist for non-existent phase wjap.purity");
		}
        if (!PackManager.v().hasPhase("shimple")) {
			G.v().out.println("Warning: Options exist for non-existent phase shimple");
		}
        if (!PackManager.v().hasPhase("stp")) {
			G.v().out.println("Warning: Options exist for non-existent phase stp");
		}
        if (!PackManager.v().hasPhase("sop")) {
			G.v().out.println("Warning: Options exist for non-existent phase sop");
		}
        if (!PackManager.v().hasPhase("sop.cpf")) {
			G.v().out.println("Warning: Options exist for non-existent phase sop.cpf");
		}
        if (!PackManager.v().hasPhase("jtp")) {
			G.v().out.println("Warning: Options exist for non-existent phase jtp");
		}
        if (!PackManager.v().hasPhase("jop")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop");
		}
        if (!PackManager.v().hasPhase("jop.cse")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.cse");
		}
        if (!PackManager.v().hasPhase("jop.bcm")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.bcm");
		}
        if (!PackManager.v().hasPhase("jop.lcm")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.lcm");
		}
        if (!PackManager.v().hasPhase("jop.cp")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.cp");
		}
        if (!PackManager.v().hasPhase("jop.cpf")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.cpf");
		}
        if (!PackManager.v().hasPhase("jop.cbf")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.cbf");
		}
        if (!PackManager.v().hasPhase("jop.dae")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.dae");
		}
        if (!PackManager.v().hasPhase("jop.nce")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.nce");
		}
        if (!PackManager.v().hasPhase("jop.uce1")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.uce1");
		}
        if (!PackManager.v().hasPhase("jop.ubf1")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.ubf1");
		}
        if (!PackManager.v().hasPhase("jop.uce2")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.uce2");
		}
        if (!PackManager.v().hasPhase("jop.ubf2")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.ubf2");
		}
        if (!PackManager.v().hasPhase("jop.ule")) {
			G.v().out.println("Warning: Options exist for non-existent phase jop.ule");
		}
        if (!PackManager.v().hasPhase("jap")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap");
		}
        if (!PackManager.v().hasPhase("jap.npc")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.npc");
		}
        if (!PackManager.v().hasPhase("jap.npcolorer")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.npcolorer");
		}
        if (!PackManager.v().hasPhase("jap.abc")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.abc");
		}
        if (!PackManager.v().hasPhase("jap.profiling")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.profiling");
		}
        if (!PackManager.v().hasPhase("jap.sea")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.sea");
		}
        if (!PackManager.v().hasPhase("jap.fieldrw")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.fieldrw");
		}
        if (!PackManager.v().hasPhase("jap.cgtagger")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.cgtagger");
		}
        if (!PackManager.v().hasPhase("jap.parity")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.parity");
		}
        if (!PackManager.v().hasPhase("jap.pat")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.pat");
		}
        if (!PackManager.v().hasPhase("jap.lvtagger")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.lvtagger");
		}
        if (!PackManager.v().hasPhase("jap.rdtagger")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.rdtagger");
		}
        if (!PackManager.v().hasPhase("jap.che")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.che");
		}
        if (!PackManager.v().hasPhase("jap.umt")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.umt");
		}
        if (!PackManager.v().hasPhase("jap.lit")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.lit");
		}
        if (!PackManager.v().hasPhase("jap.aet")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.aet");
		}
        if (!PackManager.v().hasPhase("jap.dmt")) {
			G.v().out.println("Warning: Options exist for non-existent phase jap.dmt");
		}
        if (!PackManager.v().hasPhase("gb")) {
			G.v().out.println("Warning: Options exist for non-existent phase gb");
		}
        if (!PackManager.v().hasPhase("gb.a1")) {
			G.v().out.println("Warning: Options exist for non-existent phase gb.a1");
		}
        if (!PackManager.v().hasPhase("gb.cf")) {
			G.v().out.println("Warning: Options exist for non-existent phase gb.cf");
		}
        if (!PackManager.v().hasPhase("gb.a2")) {
			G.v().out.println("Warning: Options exist for non-existent phase gb.a2");
		}
        if (!PackManager.v().hasPhase("gb.ule")) {
			G.v().out.println("Warning: Options exist for non-existent phase gb.ule");
		}
        if (!PackManager.v().hasPhase("gop")) {
			G.v().out.println("Warning: Options exist for non-existent phase gop");
		}
        if (!PackManager.v().hasPhase("bb")) {
			G.v().out.println("Warning: Options exist for non-existent phase bb");
		}
        if (!PackManager.v().hasPhase("bb.lso")) {
			G.v().out.println("Warning: Options exist for non-existent phase bb.lso");
		}
        if (!PackManager.v().hasPhase("bb.sco")) {
			G.v().out.println("Warning: Options exist for non-existent phase bb.sco");
		}
        if (!PackManager.v().hasPhase("bb.pho")) {
			G.v().out.println("Warning: Options exist for non-existent phase bb.pho");
		}
        if (!PackManager.v().hasPhase("bb.ule")) {
			G.v().out.println("Warning: Options exist for non-existent phase bb.ule");
		}
        if (!PackManager.v().hasPhase("bb.lp")) {
			G.v().out.println("Warning: Options exist for non-existent phase bb.lp");
		}
        if (!PackManager.v().hasPhase("bop")) {
			G.v().out.println("Warning: Options exist for non-existent phase bop");
		}
        if (!PackManager.v().hasPhase("tag")) {
			G.v().out.println("Warning: Options exist for non-existent phase tag");
		}
        if (!PackManager.v().hasPhase("tag.ln")) {
			G.v().out.println("Warning: Options exist for non-existent phase tag.ln");
		}
        if (!PackManager.v().hasPhase("tag.an")) {
			G.v().out.println("Warning: Options exist for non-existent phase tag.an");
		}
        if (!PackManager.v().hasPhase("tag.dep")) {
			G.v().out.println("Warning: Options exist for non-existent phase tag.dep");
		}
        if (!PackManager.v().hasPhase("tag.fieldrw")) {
			G.v().out.println("Warning: Options exist for non-existent phase tag.fieldrw");
		}
        if (!PackManager.v().hasPhase("db")) {
			G.v().out.println("Warning: Options exist for non-existent phase db");
		}
        if (!PackManager.v().hasPhase("db.transformations")) {
			G.v().out.println("Warning: Options exist for non-existent phase db.transformations");
		}
        if (!PackManager.v().hasPhase("db.renamer")) {
			G.v().out.println("Warning: Options exist for non-existent phase db.renamer");
		}
        if (!PackManager.v().hasPhase("db.deobfuscate")) {
			G.v().out.println("Warning: Options exist for non-existent phase db.deobfuscate");
		}
        if (!PackManager.v().hasPhase("db.force-recompile")) {
			G.v().out.println("Warning: Options exist for non-existent phase db.force-recompile");
		}
        }

}
