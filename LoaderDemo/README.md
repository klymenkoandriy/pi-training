### **Classloader demo**

- **1.  Build the project 'class-loader-demo' :**

        cd class-loader-demo
        mvn clean install

- **2.  Start application:**

        java -jar plugin-manager/target/plugin-manager-0.0.2-SNAPSHOT.jar

- **3. Add and remove plugins.**

    You can add your own plugin in the 'class-loader-demo\plugin-manager\lib\plugin' folder.
    
    If you add or remove plugins (jar files) in runtime, you should perform the 'refresh' command.

- **4. Plugin requirements.**

        1. Jar file must contain class which implements plugin-api:software.sigma.klym.Converter interface.
        
        2. Manifest must contain a 'PluginClass' entry with a class name of a converter.
            (Example: software.sigma.klym.ConverterPlugin)
        
        3. Manifest must contain a 'PluginName' entry with a plugin name for UI.

- **5. Commands.**

        -h      :show commands
        -l<N>   :load plugin by index (example: -l2)
        -r      :refresh plugin list
        -u      :unload plugin
        -q      :quit

- **6. Previous versions.**

    *0.0.1-SNAPSHOT*

    Plugin requirements: 

        1. Jar file must contain software.sigma.klym.ConverterPlugin class
                which implements plugin-api:software.sigma.klym.Converter interface.
        
        2. Manifest must contain a 'PluginName' entry.
