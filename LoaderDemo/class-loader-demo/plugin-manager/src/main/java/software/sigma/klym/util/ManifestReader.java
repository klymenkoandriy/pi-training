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
        try (JarInputStream jis = new JarInputStream(new FileInputStream(pathToPlugin)) ){
            String name = jis.getManifest().getMainAttributes().getValue("PluginName");
            return name;
        }
        catch (IOException e) {
            throw new RuntimeException("Cand read plugin name.");
        }
    }

}
