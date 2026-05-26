package io.github.bfur64.menu.input;

import io.github.bfur64.terminal.input.KeyStroke;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NullMarked
public class InputHandler {
    private final List<KeyAction> actions = new ArrayList<>();

    public void addKeyActions(List<KeyAction> keyActions) {
        this.actions.addAll(keyActions);
    }

    public void addKeyActions(KeyAction... keyActions) {
        addKeyActions(Arrays.stream(keyActions).toList());
    }

    public void addKeyAction(KeyAction keyAction) {
        this.actions.add(keyAction);
    }

    public void handle(KeyStroke keyStroke) {
        actions.stream()
            .filter(keyAction -> keyAction.keyStroke().equals(keyStroke))
            .forEach(keyAction -> keyAction.action().run());
    }
}
