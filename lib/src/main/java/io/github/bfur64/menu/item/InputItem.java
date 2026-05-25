package io.github.bfur64.menu.item;

import io.github.bfur64.menu.MenuContext;
import io.github.bfur64.menu.utils.Property;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;
import io.github.bfur64.terminal.interfaces.TerminalBackend;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class InputItem<T> extends Item {
    private final String separator;
    protected final Property<T> property;
    private final String suffix;

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
    }

    @Override
    public String getDisplayName() {
        return name + separator + property.get() + " " + suffix;
    }

    @Override
    public void selectItem(MenuContext menuContext) {
        TerminalBackend terminal = menuContext.terminal();
        int itemX = menuContext.itemX();
        int itemY = menuContext.itemY();

        int nameOffset = itemX + (name + separator).length();

        selectItemName(terminal, itemX, itemY);
        clearItemValueSuffix(terminal, itemY, nameOffset);
        readUserInput(terminal, itemY, nameOffset);

        terminal.clearScreen();
    }

    protected void readUserInput(TerminalBackend terminal, int itemY, int nameOffset) {
        StringBuilder builderOut = new StringBuilder();
        int cursorPos = nameOffset;

        loop:
        while (true) {
            terminal.flush();

            KeyStroke keyStroke = terminal.readInput();
            KeyType keyType = keyStroke.keyType();

            switch (keyType) {
                case ESCAPE -> { break loop; }
                case CHARACTER -> {
                    char character = keyStroke.character();
                    builderOut.append(character);
                    terminal.put(cursorPos, itemY, String.valueOf(character));
                    cursorPos++;
                }
                case BACKSPACE -> {
                    if (!builderOut.isEmpty()) {
                        cursorPos--;
                        builderOut.deleteCharAt(builderOut.length() - 1);
                        terminal.put(cursorPos, itemY, " ");
                    }
                }
                case ENTER -> {
                    try {
                        String stringOut = builderOut.toString();

                        if (property.isValid(stringOut)) {
                            property.set(stringOut);
                            break loop;
                        }

                        throwUserError(terminal, itemY, nameOffset, property.getLatestError());
                    }
                    catch (IllegalArgumentException e) {
                        throwUserError(terminal, itemY, nameOffset, "Unexpected Input");
                    }

                    break loop;
                }
            }
        }
    }

    private void selectItemName(TerminalBackend terminal, int itemX, int itemY) {
        terminal.setBackgroundColor(200, 200, 200);
        terminal.setForegroundColor(0, 0, 0);
        terminal.put(itemX, itemY, name);
        terminal.resetColorAndStyle();
    }

    private void clearItemValueSuffix(TerminalBackend terminal, int itemY, int nameOffset) {
        int valueSuffixLength = (separator + property.get() + " " + suffix).length();

        for (int i = 0; i <= valueSuffixLength; i++) {
            terminal.put(nameOffset + i, itemY, " ");
        }
    }

    protected void throwUserError(TerminalBackend terminal, int itemY, int nameOffset, @Nullable String lastErrorMessage) {
        terminal.setForegroundColor(255, 70, 70);
        terminal.put(nameOffset, itemY, lastErrorMessage);
        terminal.resetColorAndStyle();

        terminal.setForegroundColor(0, 0, 0);
        terminal.setBackgroundColor(200, 200, 200);
        terminal.put(nameOffset + 2, itemY + 2, "  Press Any Key To Continue...  ");
        terminal.resetColorAndStyle();

        terminal.flush();

        terminal.readInput();
    }
}
