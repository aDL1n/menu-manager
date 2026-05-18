package io.github.bfur64.menu.item;

import io.github.bfur64.menu.utils.Property;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;

public class KeyReaderItem extends EditableItem<KeyStroke> {
    public KeyReaderItem(String name, Property<KeyStroke> property) {
        super(name, " = ", property);
    }

    public KeyReaderItem(String name, String separator, Property<KeyStroke> property) {
        super(name, separator, property, "");
    }

    public KeyReaderItem(String name, Property<KeyStroke> property, String suffix) {
        super(name, " = ", property, suffix);
    }

    public KeyReaderItem(String name, String separator, Property<KeyStroke> property, String suffix) {
        super(name, separator, property, suffix);
    }

    @Override
    protected void readUserInput(int nameOffset) {
        while (true) {
            KeyStroke keyStroke = terminal.readInput();

            if (keyStroke.getKeyType() == KeyType.UNKNOWN) {
                throwUserError(nameOffset, "Unknown Character!");
                continue;
            }

            property.setValue(keyStroke);

            break;
        }

    }
}
