package io.github.bfur64.menu;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record MenuContext (
    MenuManager menu,
    int itemX,
    int itemY
) {
}
