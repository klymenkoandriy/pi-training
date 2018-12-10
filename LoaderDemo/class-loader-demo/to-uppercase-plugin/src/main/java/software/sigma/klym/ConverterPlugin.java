package software.sigma.klym;

public class ConverterPlugin implements Converter {

    public ConverterPlugin() {
        System.out.println("    uppercase Converter created...");
    }

    @Override
    public String convert(String input) {
        return input.toUpperCase();
    }

}
