package io.github.bfur64.menu;

public final class MenuCursor {
    private Position position;
    private String cursorSymbol;

    public MenuCursor(Position position, String cursorSymbol) {
        this.position = position;
        this.cursorSymbol = cursorSymbol;
    }

    public void setPosition(int x, int y) {
        this.position = Position.of(x, y);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public String getCursorSymbol() {
        return cursorSymbol;
    }

    public void setCursorSymbol(String cursorSymbol) {
        this.cursorSymbol = cursorSymbol;
    }
}
