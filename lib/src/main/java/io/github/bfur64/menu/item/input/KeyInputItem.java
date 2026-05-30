package io.github.bfur64.menu.item.input;

import io.github.bfur64.menu.utils.Property;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class KeyInputItem extends InputItem<KeyStroke> {
    public KeyInputItem(String name, Property<KeyStroke> property) {
        super(name, " = ", property);
    }

    public KeyInputItem(String name, String separator, Property<KeyStroke> property) {
        super(name, separator, property, "");
    }

    public KeyInputItem(String name, Property<KeyStroke> property, String suffix) {
        super(name, " = ", property, suffix);
    }

    public KeyInputItem(String name, String separator, Property<KeyStroke> property, String suffix) {
        super(name, separator, property, suffix);
    }

    @Override
    public void handle(KeyStroke keyStroke) {
        if (keyStroke.keyType() == KeyType.UNKNOWN) {
            // TODO Throw User Error
            value = property.get().toString();
            isFinished = true;
            return;
        }

        property.set(keyStroke);
        value = keyStroke.toString();
        isFinished = true;
    }
}
