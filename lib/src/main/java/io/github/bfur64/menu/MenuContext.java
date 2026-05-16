package io.github.bfur64.menu;

import io.github.bfur64.terminal.Terminal;

public record MenuContext (
    Terminal terminal,
    int x,
    int y
) {}
