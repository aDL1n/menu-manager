package io.github.bfur64.menu.utils;

public class Converters {
    public static final Converter<String> STRING;
    public static final Converter<Integer> INTEGER;
    public static final Converter<Double> DOUBLE;
    public static final Converter<Boolean> BOOLEAN;

    static {
        STRING = new Converter<String>() {
            @Override
            public String fromString(String string) throws IllegalArgumentException {
                return string;
            }
        };

        INTEGER = new Converter<Integer>() {
            @Override
            public Integer fromString(String string) throws IllegalArgumentException {
                return Integer.parseInt(string);
            }
        };

        DOUBLE = new Converter<Double>() {
            @Override
            public Double fromString(String string) throws IllegalArgumentException {
                return Double.parseDouble(string);
            }
        };

        BOOLEAN = new Converter<Boolean>() {
            @Override
            public Boolean fromString(String string) throws IllegalArgumentException {
                return Boolean.parseBoolean(string);
            }
        };
    }
}
