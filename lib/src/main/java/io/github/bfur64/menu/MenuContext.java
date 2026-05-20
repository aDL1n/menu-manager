package io.github.bfur64.menu;

import io.github.bfur64.terminal.TerminalBackend;

public record MenuContext (
    TerminalBackend terminal,
    int itemX,
    int itemY
) {}
