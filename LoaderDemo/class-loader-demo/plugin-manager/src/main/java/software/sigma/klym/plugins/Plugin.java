package software.sigma.klym.plugins;

public class Plugin {

    private String pluginName;

    private String jarFileName;

    private boolean loaded;

    public Plugin(String pluginName, String jarFileName) {
        this.pluginName = pluginName;
        this.jarFileName = jarFileName;
        loaded = false;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getJarFileName() {
        return jarFileName;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plugin plugin = (Plugin)o;

        if (!pluginName.equals(plugin.pluginName)) return false;
        return jarFileName.equals(plugin.jarFileName);
    }

    @Override
    public int hashCode() {
        int result = pluginName.hashCode();
        result = 31 * result + jarFileName.hashCode();
        return result;
    }
}
