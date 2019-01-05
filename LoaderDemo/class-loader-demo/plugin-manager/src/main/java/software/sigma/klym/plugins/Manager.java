package software.sigma.klym.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import software.sigma.klym.Converter;
import software.sigma.klym.util.ManifestReader;

public class Manager {

    private static final String CURRENT_PATH = System.getProperty("user.dir").replace('\\', '/') + "/";
    private static final String LIB_PATH = CURRENT_PATH + "/plugin-manager/lib/plugin/";

    private static List<Plugin> availablePlugins = new ArrayList<>();
    private static Plugin loadedPlugin;
    private static Converter loadedConverter;

    public static void refreshPlugins() {
        System.out.println("-- Refresh plugin list.");
        File folder = new File(LIB_PATH);
        File[] files = folder.listFiles();
        List<String> fileNames = new ArrayList<>();

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                fileNames.add(fileName);
                String pluginName = ManifestReader.getPluginName(LIB_PATH + fileName);
                String pluginClass = ManifestReader.getPluginClassName(LIB_PATH + fileName);
                Plugin plugin = new Plugin(pluginName, pluginClass, fileName);
                addPlugin(plugin);
            }
        }

        for (final Iterator<Plugin> iterator = availablePlugins.iterator(); iterator.hasNext(); ) {
            Plugin plugin = iterator.next();
            if (!fileNames.contains(plugin.getJarFileName())) {
                unloadPlugin();
                iterator.remove();
            }
        }

        printPluginList();
    }

    private static void addPlugin(Plugin plugin) {
        for(Plugin existingPlugin : availablePlugins) {
            if(existingPlugin.equals(plugin)) {
                return;
            }
        }
        availablePlugins.add(plugin);
    }

    public static void loadPluginByIndex(int i) {
        if(availablePlugins.size() < i) {
            System.out.println(String.format("Error: there is no plugin N %d.", i));
            return;
        }

        unloadPlugin();

        loadedPlugin = availablePlugins.get(i-1);
        System.out.println(String.format("-- Load plugin '%s'.", loadedPlugin.getPluginName()));
        loadPluginFromJar(loadedPlugin.getJarFileName(), loadedPlugin.getPluginClass());
        loadedPlugin.setLoaded(true);
    }

    public static void unloadPlugin() {
        if(loadedPlugin != null) {
            System.out.println(String.format("\n-- Unload plugin '%s'.", loadedPlugin.getPluginName()));
            loadedConverter = null;
            loadedPlugin.setLoaded(false);
            loadedPlugin = null;
        } else {
            System.out.println("\nThere are no loaded plugins");
        }
    }

    public static void convert(String input) {
        if(loadedConverter == null) {
            System.out.println("There is no loaded plugin.");
        } else {
            System.out.println(String.format(" '%s' -> convert with %s -> '%s'", input,
                    loadedPlugin.getPluginName(), loadedConverter.convert(input)));
        }
    }

    public static void loadPluginFromJar(String jarFileName, String className) {
        String pathToJar = LIB_PATH + jarFileName;

        PluginLoader loader = new PluginLoader(pathToJar, ClassLoader.getSystemClassLoader());
        try {
            Class clazz = loader.loadClass(className);
            loadedConverter = (Converter) clazz.newInstance();
        }
        catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            System.out.println("Error: plugin hasn't been loaded.");
        }
    }

    private static void printPluginList() {
        if(availablePlugins.size() == 0) {
            System.out.println("There are no available plugins.");
        }

        System.out.println("-------- available plugins -------------");

        int i = 1;
        for (Plugin plugin : availablePlugins) {
            String loaded = plugin.isLoaded() ? " loaded  " : "available";
            System.out.println(String.format("| %d | %s | %s", i++, loaded, plugin.getPluginName()));
        }
        System.out.println("----------------------------------------");
    }

}
