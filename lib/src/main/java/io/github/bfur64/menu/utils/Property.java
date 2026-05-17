package io.github.bfur64.menu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Property<T> {
    T get();
    void set(T value);
    T convertFromString(String value);

    default Property<T> withValidator(Predicate<T> validator) {
        return withValidator(validator, "Invalid value");
    }

    Property<T> withValidator(Predicate<T> validator, String message);

    boolean isValid(T value);

    String getLatestErrorMessage();

    static <T> Property<T> create(Supplier<T> getter, Consumer<T> setter, Function<String, T> function) {
        return new Property<>() {
            private final List<Predicate<T>> validators = new ArrayList<>();
            private final List<String> errorMessages = new ArrayList<>();
            private String latestErrorMessage;

            public T get() {
                return getter.get();
            }

            public void set(T value) {
                setter.accept(value);
            }

            public T convertFromString(String value) {
                return function.apply(value);
            }

            public Property<T> withValidator(Predicate<T> validator, String message) {
                validators.add(validator);
                errorMessages.add(message);

                return this;
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

            public String getLatestErrorMessage() {
                return latestErrorMessage;
            }
        };
    }
}
