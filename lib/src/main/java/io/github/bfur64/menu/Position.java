package io.github.bfur64.menu;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record Position(int x, int y) {
    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    public static Position of(Position position) {
        return new Position(position.x, position.y);
    }

    public Position add(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }
}
