package io.github.bfur64.menu;

import io.github.bfur64.menu.input.InputHandler;
import io.github.bfur64.menu.item.Item;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;
import io.github.bfur64.terminal.interfaces.TerminalBackend;

import java.util.Collections;
import java.util.List;

public class MenuManager {
    private static final KeyStroke UNKNOWN_KEY = new KeyStroke(KeyType.UNKNOWN);

    private final int itemIdent;

    private final TerminalBackend terminal;
    private boolean isRunning = true;

    private final List<Item> menuList;

    private final MenuCursor cursor;

    private final MenuRenderer renderer;
    private final InputHandler inputHandler;

    public MenuManager(TerminalBackend terminal, List<Item> menuList) {
        this.terminal = terminal;
        this.menuList = Collections.unmodifiableList(menuList);

        final Position cursorPosition = initCursorPosition();
        this.cursor = new MenuCursor(cursorPosition, ">>>");

        this.itemIdent = cursor.getCursorSymbol().length() + 2;

        this.renderer = new MenuRenderer(terminal, menuList, cursor, itemIdent);

        this.inputHandler = new InputHandler();
        initInputActions();
    }

    //Maybe change to show
    public void run() {
        update(UNKNOWN_KEY);

        while (isRunning) {
            KeyStroke keyStroke = terminal.readInput();

            update(keyStroke);
        }

        terminal.clearScreen();
        terminal.flush();
    }

    public void exit() {
        this.isRunning = false;
    }

    private void update(KeyStroke keyStroke) {
        this.inputHandler.handle(keyStroke);
        renderer.update();
    }

    private Position initCursorPosition() {
        final int cursorX = 1;

        for (int itemIndex = 0; itemIndex < menuList.size(); itemIndex++) {
            if (isItemSelectable(itemIndex)) {
                return Position.of(cursorX, itemIndex);
            }
        }

        return Position.of(cursorX, 0);
    }

    private void initInputActions() {
        this.inputHandler.setKeyAction(KeyType.ESCAPE, this::exit);
        this.inputHandler.setKeyAction(KeyType.ARROW_UP, () -> {
            moveCursor(-1);

            // :)
            cursor.setCursorSymbol("<<<");
        });
        this.inputHandler.setKeyAction(KeyType.ARROW_DOWN, () -> {
            moveCursor(1);

            // :)
            cursor.setCursorSymbol(">>>");
        });
        this.inputHandler.setKeyAction(KeyType.ENTER, () -> selectItem(cursor.getPosition()));
    }

    private void moveCursor(int cursorMovement) {
        Position newCursorPosition = Position.of(cursor.getPosition());

        do {
            newCursorPosition.add(0, cursorMovement);

            if (newCursorPosition.getY() < 0) newCursorPosition.setY(menuList.size() - 1);
            if (newCursorPosition.getY() > menuList.size() - 1) newCursorPosition.setY(0);

            if (newCursorPosition.getY() == cursor.getPosition().getY()) return;
        } while (!menuList.get(newCursorPosition.getY()).isSelectable());

        cursor.setPosition(newCursorPosition);
    }

    private void selectItem(Position cursorPosition) {
        Item menuItem = menuList.get(cursorPosition.getY());
        menuItem.selectItem(new MenuContext(terminal, itemIdent, cursorPosition.getY()));

        if (menuItem.shouldExit()) {
            isRunning = false;
        }

        update(UNKNOWN_KEY);
    }

    private boolean isItemSelectable(int itemIndex) {
        return this.menuList.get(itemIndex).isSelectable();
    }

    @SuppressWarnings("unused")
    public static String getVersion() {
        return Config.VERSION;
    }
}
