package software.sigma.klym;

public class ToUppercaseConverter implements Converter {

    public ToUppercaseConverter() {
        System.out.println("    uppercase Converter created...");
    }

    @Override
    public String convert(String input) {
        return input.toUpperCase();
    }

}
