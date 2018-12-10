package software.sigma.klym;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import software.sigma.klym.plugins.Manager;

public class ClassLoaderDemo {

    public static void main(String args[]) {
        printHello();
        Manager.refreshPlugins();
        dialog();
    }

    public static void printHello() {
        System.out.println("\n**********  String converter with plugins. ***********");
        printHelp();
        System.out.println("All input strings that don't start with '-' will be converted with selected plugin");
        System.out.println("*****************************************************\n");
    }

    public static void printHelp() {
        System.out.println("\n----  Commands ---------------------------------");
        System.out.println("    -h      :show commands");
        System.out.println("    -l<N>   :load plugin by index (example: -l2)");
        System.out.println("    -r      :refresh plugin list");
        System.out.println("    -u      :unload plugin");
        System.out.println("    -q      :quit");
        System.out.println("------------------------------------------------\n");
    }

    public static void dialog() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("> ");
                String input = br.readLine();

                if(input.startsWith("-")) {
                    executeCommand(input.substring(1));
                } else {
                    Manager.convert(input);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(String command) {

        if(command.startsWith("l") && command.length() == 2) {
            loadPlugin(command);
            return;
        }

        if(command.length() != 1) {
            System.out.println("Error: invalid command!");
            return;
        }

        switch(command) {
            case "q":
                System.out.println("Exit!");
                System.exit(0);
                break;
            case "h":
                printHelp();
                break;
            case "r":
                Manager.refreshPlugins();
                break;
            case "u":
                Manager.unloadPlugin();
                break;
            default:
                System.out.println("Error: unknown command!");
                break;
        }
    }

    private static void loadPlugin(String command) {
        int index = Integer.valueOf(command.substring(1));
        Manager.loadPluginByIndex(index);
    }
}
