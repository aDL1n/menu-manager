package io.github.bfur64.menu.utils;

public interface Converter<T> {
    T fromString(String string) throws IllegalArgumentException;
}
