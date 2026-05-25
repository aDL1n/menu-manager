package io.github.bfur64.menu;

public class Position {
    private int x;
    private int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int i) {
        return new Position(i, i);
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    public static Position of(Position position) {
        return new Position(position.getX(), position.getY());
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
