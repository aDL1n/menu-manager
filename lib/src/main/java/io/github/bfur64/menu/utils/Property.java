package io.github.bfur64.menu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Property<T> implements AbstractProperty<T> {
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    private final Function<String, T> parser;

    private final List<Predicate<T>> validators;
    private final List<String> errors;
    private String latestError;

    public static <T> Builder<T> of(T initialValue) {
        Builder<T> builder = new Builder<>();
        return builder.create(initialValue);
    }

    private Property(Supplier<T> getter, Consumer<T> setter, List<Predicate<T>> validators, List<String> errors) {
        this(getter, setter, validators, errors, s -> {
            throw new UnsupportedOperationException("String conversion not available");
        });
    }

     private Property(Supplier<T> getter, Consumer<T> setter, List<Predicate<T>> validators, List<String> errors, Function<String, T> parser) {
        this.getter = getter;
        this.setter = setter;
        this.validators = validators;
        this.errors = errors;
        this.parser = parser;
    }

    @Override
    public T get() {
        return getter.get();
    }

    @Override
    public void set(T value) {
        setter.accept(value);
    }

    @Override
    public void set(String value) {
        setter.accept(parse(value));
    }

    @Override
    public boolean isValid(T value) {
        for (int i = 0; i < validators.size(); i++) {
            if (!validators.get(i).test(value)) {
                latestError = errors.get(i);
                return false;
            }
        }

        latestError = null;
        return true;
    }

    @Override
    public boolean isValid(String value) {
        return isValid(parse(value));
    }

    @Override
    public String getLatestError() {
        return latestError;
    }

    @Override
    public T parse(String value) {
        return parser.apply(value);
    }

    public static class Builder<T> {
        private Supplier<T> getter;
        private Consumer<T> setter;
        private Function<String, T> parser;

        private final List<Predicate<T>> validators = new ArrayList<>();
        private final List<String> errors = new ArrayList<>();

        private Builder<T> create(T initialValue) {
            ValueHolder<T> holder = new ValueHolder<>(initialValue);
            getter = () -> holder.value;
            setter = newValue -> { holder.value = newValue; };
            return this;
        }

        public Builder<T> require(Predicate<T> predicate) {
            validators.add(predicate);
            errors.add("Invalid Value");
            return this;
        }

        public Builder<T> require(Predicate<T> predicate, String error) {
            validators.add(predicate);
            errors.add(error);
            return this;
        }

        public Builder<T> setter(Consumer<T> setter) {
            this.setter = setter;
            return this;
        }

        public Builder<T> getter(Supplier<T> getter) {
            this.getter = getter;
            return this;
        }

        public Builder<T> parser(Function<String, T> parser) {
            this.parser = parser;
            return this;
        }

        public Property<T> build() {
            if (parser != null) {
                return new Property<T>(getter, setter, validators, errors, parser);
            }

            return new Property<T>(getter, setter, validators, errors);
        }

        private static class ValueHolder<T> {
            T value;
            ValueHolder(T initialValue) { value = initialValue; }
        }
    }
}
