package io.github.bfur64.menu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Property<T> {
    T getValue();

    default void setValue(String value) {
        setValue(convertString(value));
    }

    void setValue(T value);

    default boolean isValid(String value) {
        return isValid(convertString(value));
    }

    boolean isValid(T value);

    default Property<T> withValidator(Predicate<T> validator) {
        return withValidator(validator, "Invalid value");
    }
    Property<T> withValidator(Predicate<T> validator, String message);
    String getLatestErrorMessage();

    T convertString(String value);

    static <T> Property<T> create(Supplier<T> getter, Consumer<T> setter) {
        return create(getter, setter, s -> {
            throw new UnsupportedOperationException("String conversion not available");
        } );
    }

    static <T> Property<T> create(Supplier<T> getter, Consumer<T> setter, Function<String, T> function) {
        return new Property<>() {
            private final List<Predicate<T>> validators = new ArrayList<>();
            private final List<String> errorMessages = new ArrayList<>();
            private String latestErrorMessage;

            public T getValue() {
                return getter.get();
            }

            public void setValue(T value) {
                setter.accept(value);
            }

            public boolean isValid(T value) {
                for (int i = 0; i < validators.size(); i++) {
                    if (!validators.get(i).test(value)) {
                        latestErrorMessage = errorMessages.get(i);
                        return false;
                    }
                }

                latestErrorMessage = null;
                return true;
            }

            public Property<T> withValidator(Predicate<T> validator, String message) {
                validators.add(validator);
                errorMessages.add(message);

                return this;
            }

            public String getLatestErrorMessage() {
                return latestErrorMessage;
            }

            public T convertString(String value) {
                return function.apply(String.valueOf(value));
            }
        };
    }
}
