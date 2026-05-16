package io.github.bfur64.menu.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Property<T> {
    T get();
    void set(T value);
    T convertFromString(String value);

    static <T> Property<T> create(Supplier<T> getter, Consumer<T> setter, Converter<T> converter) {
        return new Property<>() {
            public T get() { return getter.get(); }
            public void set(T value) { setter.accept(value); }
            public T convertFromString(String value) { return converter.fromString(value); }
        };
    }
}
