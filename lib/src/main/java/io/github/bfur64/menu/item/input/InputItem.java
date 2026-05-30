package io.github.bfur64.menu.item.input;

import io.github.bfur64.menu.input.InputHandler;
import io.github.bfur64.menu.item.Item;
import io.github.bfur64.menu.utils.Property;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class InputItem<T> extends Item implements InputHandler {
    private final String separator;
    protected final Property<T> property;
    private final String suffix;

    protected String value;
    protected boolean isFinished;

    public InputItem(String name, Property<T> property) {
        this(name, " = ", property);
    }

    public InputItem(String name, String separator, Property<T> property) {
        this(name, separator, property, "");
    }

    public InputItem(String name, Property<T> property, String suffix) {
        this(name, " = ", property, suffix);
    }

    public InputItem(String name, String separator, Property<T> property, String suffix) {
        super(name, true);
        this.separator = separator;
        this.property = property;
        this.suffix = suffix;
        value = property.get().toString();
    }

    @Override
    public String getDisplayName() {
        return name + separator + value + " " + suffix;
    }

    @Override
    public void selectItem() {
        isFinished = false;
        value = "";
    }

    @Override
    public void handle(KeyStroke keyStroke) {
        KeyType keyType = keyStroke.keyType();

        switch (keyType) {
            case ESCAPE -> {
                value = property.get().toString();
                isFinished = true;
            }
            case CHARACTER -> value += keyStroke.character();
            case BACKSPACE -> {
                if (!value.isEmpty()) {
                    value = value.substring(0, value.length() - 1);
                }
            }
            case ENTER -> {
                try {
                    if (property.isValid(value)) {
                        property.set(value);
                        isFinished = true;
                        break;
                    }

                    // TODO Throw User Error
                    value = property.get().toString();
                }
                catch (IllegalArgumentException e) {
                    // TODO Throw User Error
                    value = property.get().toString();
                }

                isFinished = true;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
