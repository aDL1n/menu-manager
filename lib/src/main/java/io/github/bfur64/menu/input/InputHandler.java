package io.github.bfur64.menu.input;

import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;

import java.util.EnumMap;
import java.util.Map;

public class InputHandler {

    private final Map<KeyType, Action> actions = new EnumMap<>(KeyType.class);

    public void setKeyAction(KeyType keyType, Action action) {
        this.actions.put(keyType, action);
    }

    public void handle(KeyStroke keyStroke) {
        actions.entrySet().stream()
                .filter(entry -> entry.getKey() == keyStroke.keyType())
                .forEach(entry -> entry.getValue().execute());
    }
}
