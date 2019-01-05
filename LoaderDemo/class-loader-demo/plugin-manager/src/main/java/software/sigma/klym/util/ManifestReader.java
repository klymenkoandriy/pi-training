package software.sigma.klym.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarInputStream;

public class ManifestReader extends JarInputStream {

    public ManifestReader(InputStream in) throws IOException {
        super(in);
    }

    public static String getPluginName(String pathToPlugin) {
        return readAttribute(pathToPlugin, "PluginName");
    }

    public static String getPluginClassName(String pathToPlugin) {
        return readAttribute(pathToPlugin, "PluginClass");
    }

    private static String readAttribute(String pathToPlugin, String attributeName) {
        try (JarInputStream jis = new JarInputStream(new FileInputStream(pathToPlugin)) ){
            String name = jis.getManifest().getMainAttributes().getValue(attributeName);
            return name;
        }
        catch (IOException e) {
            throw new RuntimeException("Can't read attribute '" + attributeName + "' from Manifest.");
        }
    }
}
