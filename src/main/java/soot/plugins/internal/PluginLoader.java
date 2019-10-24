package soot.plugins.internal;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2013 Bernhard J. Berger
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

import java.io.File;
import java.security.InvalidParameterException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.PackManager;
import soot.Transform;
import soot.plugins.SootPhasePlugin;
import soot.plugins.model.PhasePluginDescription;
import soot.plugins.model.PluginDescription;
import soot.plugins.model.Plugins;

/**
 * Class for loading xml-based plugin configuration files.
 *
 * @author Bernhard J. Berger
 */
public class PluginLoader {
  private static final Logger logger = LoggerFactory.getLogger(PluginLoader.class);

  private static ClassLoadingStrategy loadStrategy = new ReflectionClassLoadingStrategy();

  private PluginLoader() {
  }

/**
   * Each phase has to support the enabled option. We will add it if necessary.
   *
   * @param declaredOptions
   *          Options declared by the plugin.
   * @return option list definitly containing enabled.
   */
  private static String[] appendEnabled(final String[] options) {
    for (final String option : options) {
      if ("enabled".equals(option)) {
        return options;
      }
    }

    String[] result = new String[options.length + 1];
    result[0] = "enabled";
    System.arraycopy(options, 0, result, 1, options.length);

    return result;
  }

/**
   * Creates a space separated list from {@code declaredOptions}.
   *
   * @param options
   *          the list to transform.
   * @return a string containing all options separated by a space.
   */
  private static String concat(final String[] options) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;

    for (final String option : options) {
      if (!first) {
        sb.append(" ");
      }
      first = false;
      sb.append(option);
    }

    return sb.toString();
  }

/**
   * Splits a phase name and returns the pack name.
   *
   * @param phaseName
   *          Name of the phase.
   * @return the name of the pack.
   */
  private static String getPackName(final String phaseName) {
    if (!phaseName.contains(".")) {
      throw new RuntimeException(new StringBuilder().append("Name of phase '").append(phaseName).append("'does not contain a dot.").toString());
    }

    return phaseName.substring(0, phaseName.indexOf('.'));
  }

/**
   * Loads the phase plugin and adds it to PackManager.
   *
   * @param pluginDescription
   *          the plugin description instance read from configuration file.
   */
  private static void handlePhasePlugin(final PhasePluginDescription pluginDescription) {
    try {
      final Object instance = PluginLoader.loadStrategy.create(pluginDescription.getClassName());

      if (!(instance instanceof SootPhasePlugin)) {
        throw new RuntimeException(
            new StringBuilder().append("The plugin class '").append(pluginDescription.getClassName()).append("' does not implement SootPhasePlugin.").toString());
      }

      final SootPhasePlugin phasePlugin = (SootPhasePlugin) instance;
      phasePlugin.setDescription(pluginDescription);

      final String packName = getPackName(pluginDescription.getPhaseName());

      Transform transform = new Transform(pluginDescription.getPhaseName(), phasePlugin.getTransformer());
      transform.setDeclaredOptions(concat(appendEnabled(phasePlugin.getDeclaredOptions())));
      transform.setDefaultOptions(concat(phasePlugin.getDefaultOptions()));
      PackManager.v().getPack(packName).add(transform);

    } catch (final ClassNotFoundException e) {
      throw new RuntimeException(new StringBuilder().append("Failed to load plugin class for ").append(pluginDescription).append(".").toString(), e);
    } catch (final InstantiationException e) {
      throw new RuntimeException(new StringBuilder().append("Failed to instanciate plugin class for ").append(pluginDescription).append(".").toString(), e);
    }
  }

/**
   * Loads the plugin configuration file {@code file} and registers the plugins.
   *
   * @param file
   *          the plugin configuration file.
   * @return {@code true} on success.
   */
  public static boolean load(final String file) {
    final File configFile = new File(file);

    if (!configFile.exists()) {
      logger.error(new StringBuilder().append("The configuration file '").append(configFile).append("' does not exist.").toString());
      return false;
    }

    if (!configFile.canRead()) {
      logger.error(new StringBuilder().append("Cannot read the configuration file '").append(configFile).append("'.").toString());
      return false;
    }

    try {
      final JAXBContext context
          = JAXBContext.newInstance(Plugins.class, PluginDescription.class, PhasePluginDescription.class);
      final Unmarshaller unmarshaller = context.createUnmarshaller();
      final Object root = unmarshaller.unmarshal(configFile);

      if (!(root instanceof Plugins)) {
        logger.error("Expected a root node of type Plugins got " + root.getClass());
        return false;
      }

      loadPlugins((Plugins) root);
    } catch (final RuntimeException e) {
      logger.error("Failed to load plugin correctly.");
      logger.error(e.getMessage(), e);
      return false;
    } catch (final JAXBException e) {
      logger.error(new StringBuilder().append("An error occured while loading plugin configuration '").append(file).append("'.").toString());
      logger.error(e.getMessage(), e);
      return false;
    }

    return true;
  }

/**
   * Load all plugins. Can be called by a custom main function.
   *
   * @param plugins
   *          the plugins to load.
   *
   * @throws RuntimeException
   *           if an error occurs during loading.
   */
  public static void loadPlugins(final Plugins plugins) {
    plugins.getPluginDescriptions().forEach((final PluginDescription plugin) -> {
      if (plugin instanceof PhasePluginDescription) {
        handlePhasePlugin((PhasePluginDescription) plugin);
      } else {
        logger.debug(new StringBuilder().append("[Warning] Unhandled plugin of type '").append(plugin.getClass()).append("'").toString());
      }
    });
  }

/**
   * Changes the class loading strategy used by Soot.
   *
   * @param strategy
   *          The new class loading strategy to use.
   */
  public static void setClassLoadingStrategy(final ClassLoadingStrategy strategy) {
    if (strategy == null) {
      throw new InvalidParameterException("Class loading strategy is not allowed to be null.");
    }

    PluginLoader.loadStrategy = strategy;
  }
}
