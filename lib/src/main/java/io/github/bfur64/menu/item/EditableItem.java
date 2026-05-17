package io.github.bfur64.menu.item;

import io.github.bfur64.menu.MenuContext;
import io.github.bfur64.menu.utils.Property;
import io.github.bfur64.terminal.Terminal;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;

import java.util.ArrayList;
import java.util.List;

public class EditableItem<T> extends Item {
    private static final String SEPARATOR = " = ";

    private final Property<T> property;

    private final String suffix;

    public EditableItem(String label, Property<T> property) {
        this(label, property, "");
    }

    public EditableItem(String label, Property<T> property, String suffix) {
        super(label, true);

        this.property = property;
        this.suffix = suffix;
    }

    public T getValue() {
        return property.get();
    }

    public void setValue(T value) {
        property.set(value);
    }

    @Override
    public String getDisplayName() {
        return label + SEPARATOR + getValue() + " " + suffix;
    }

    @Override
    public void selectItem(MenuContext menuContext) {
        @SuppressWarnings("resource") Terminal terminal = menuContext.terminal();

        int valueSuffixLength = (getValue() + " " + suffix).length();

        int nameOffset = menuContext.x() + label.length() + SEPARATOR.length();

        terminal.setBackgroundColor(200, 200, 200);
        terminal.setForegroundColor(0, 0, 0);

        terminal.putString(menuContext.x(), menuContext.y(), label);

        terminal.resetColorAndStyle();

        for (int i = 0; i <= valueSuffixLength; i++) {
            terminal.putString(nameOffset + i, menuContext.y(), " ");
        }

        int cursorPos = nameOffset;

        List<Character> charInp = new ArrayList<>();
        StringBuilder sBuilder = new StringBuilder();

        loop:
        while (true) {
            terminal.flush();

            KeyStroke keyStroke = terminal.readInput();
            KeyType keyType = keyStroke.getKeyType();
            Character character;

            switch (keyType) {
                case ESCAPE -> {
                    break loop;
                }
                case CHARACTER -> {
                    character = keyStroke.getCharacter();

                    terminal.putString(cursorPos, menuContext.y(), character.toString());

                    cursorPos++;

                    charInp.add(character);
                }
                case BACKSPACE -> {
                    character = ' ';
                    cursorPos--;

                    if (cursorPos < nameOffset) {
                        cursorPos = nameOffset;
                    }

                    terminal.putString(cursorPos, menuContext.y(), character.toString());

                    if (!charInp.isEmpty()) {
                        charInp.removeLast();
                    }
                }
                case ENTER -> {
                    for (Character ch : charInp) {
                        sBuilder.append(ch);
                    }

                    String userInp = sBuilder.toString();

                    try {

                        T converted = property.convertFromString(userInp);

                        if(property.isValid(converted)) {
                            setValue(converted);
                        }
                        else {
                            throwUserError(menuContext, nameOffset, "!!! " + property.getLatestErrorMessage());
                        }
                    }
                    catch (IllegalArgumentException e) {
                        throwUserError(menuContext, nameOffset, "!!! Unexpected Value");
                    }

                    break loop;
                }
            }
        }

        terminal.clearScreen();
    }

    private void throwUserError(MenuContext menuContext, int nameOffset, String lastErrorMessage) {
        @SuppressWarnings("resource") Terminal terminal = menuContext.terminal();

        terminal.setForegroundColor(255, 70, 70);
        terminal.putString(nameOffset, menuContext.y(), lastErrorMessage);
        terminal.resetColorAndStyle();

        terminal.setForegroundColor(0, 0, 0);
        terminal.setBackgroundColor(200, 200, 200);
        terminal.putString(nameOffset + 2, menuContext.y() + 2, "  Press Any Key To Continue...  ");
        terminal.resetColorAndStyle();

        terminal.flush();

        terminal.readInput();
    }
}
