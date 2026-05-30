package io.github.bfur64.menu.utils;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface AbstractProperty<T> {

    T get();
    void set(T value);
    void set(String value);
    boolean isValid(T value);
    boolean isValid(String value);
    @Nullable String getLatestError();
    @Nullable T parse(String value);
}
