package software.sigma.klym;

public class ToLowercaseConverter implements Converter {

    public ToLowercaseConverter() {
        System.out.println("    lowercase Converter created...");
    }

    @Override
    public String convert(String input) {
        return input.toLowerCase();
    }

}
