package software.sigma.klym.plugins;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class PluginLoader extends ClassLoader {

    private String pathToJar;

    public PluginLoader(String pathToJar, ClassLoader parent) {
        super(parent);
        this.pathToJar = pathToJar;
    }

    @Override
    public Class<?> findClass(String className) {
        try {
            byte b[] = readClassFromJar(className);
            return defineClass(className, b, 0, b.length);
        }
        catch (IOException e) {
            throw new RuntimeException("Can't read plugin from file " + className + ".");
        }
    }

    public byte[] readClassFromJar(String className) throws IOException {
        String classPath = className.replace('.', '/') + ".class";
        String inputFile = "jar:file:/" + pathToJar + "!/" + classPath;

        URL inputURL = new URL(inputFile);
        JarURLConnection conn = (JarURLConnection) inputURL.openConnection();
        conn.setUseCaches(false);
        InputStream in = conn.getInputStream();
        byte[] bytes = IOUtils.toByteArray(in);

        in.close();

        return bytes;
    }
}
