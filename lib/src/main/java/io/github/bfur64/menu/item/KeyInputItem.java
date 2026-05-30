package io.github.bfur64.menu.item;

import io.github.bfur64.menu.utils.Property;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;
import io.github.bfur64.terminal.interfaces.TerminalBackend;
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
    protected void readUserInput(TerminalBackend terminal, int itemY, int nameOffset) {
        while (true) {
            KeyStroke keyStroke = terminal.readInput();

            if (keyStroke.keyType() == KeyType.UNKNOWN) {
                throwUserError(terminal, itemY, nameOffset, "Unknown Character!");
                continue;
            }

            property.set(keyStroke);

            break;
        }
    }
}
