package io.github.bfur64.menu.input;

import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;

public record KeyAction(KeyStroke keyStroke, Action action) {

    public KeyAction(KeyType keyType, Action action) {
        this(new KeyStroke(keyType), action);
    }
}
