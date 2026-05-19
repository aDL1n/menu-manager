package io.github.bfur64.menu.utils;

public interface AbstractProperty<T> {
    T get();
    void set(T value);
    void set(String value);
    boolean isValid(T value);
    boolean isValid(String value);
    String getLatestError();
    T parse(String value);
}
