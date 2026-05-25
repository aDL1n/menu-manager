package io.github.bfur64.menu;

import io.github.bfur64.terminal.interfaces.TerminalBackend;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record MenuContext (
    TerminalBackend terminal,
    int itemX,
    int itemY
) {}
