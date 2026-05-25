package io.github.bfur64.menu;

public final class MenuCursor {
    private Position position;
    private String cursorSymbol;

    public MenuCursor(Position position, String cursorSymbol) {
        this.position = position;
        this.cursorSymbol = cursorSymbol;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        position = Position.of(x, y);
    }

    public String getCursorSymbol() {
        return cursorSymbol;
    }

    public void setCursorSymbol(String cursorSymbol) {
        this.cursorSymbol = cursorSymbol;
    }
}
