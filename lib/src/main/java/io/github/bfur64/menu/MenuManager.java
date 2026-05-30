package io.github.bfur64.menu;

import io.github.bfur64.menu.input.InputHandler;
import io.github.bfur64.menu.input.KeyAction;
import io.github.bfur64.menu.item.Item;
import io.github.bfur64.menu.item.SelectableItem;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;
import io.github.bfur64.terminal.interfaces.TerminalBackend;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class MenuManager {
    private static final KeyStroke UNKNOWN_KEY = new KeyStroke(KeyType.UNKNOWN);

    private final TerminalBackend terminal;
    private final MenuCursor cursor;
    private final MenuRenderer renderer;
    private final InputHandler inputHandler;

    private final int itemIndent;
    private final List<Item> menuList;

    private boolean isRunning = true;

    public MenuManager(TerminalBackend terminal, List<Item> menuList) {
        this.terminal = terminal;
        this.menuList = menuList;

        cursor = new MenuCursor(initCursorPosition(), ">");

        itemIndent = cursor.getCursorSymbol().length() + 2;

        renderer = new MenuRenderer(terminal, menuList, cursor, itemIndent);

        inputHandler = new InputHandler();
        initInputActions();
    }

    public void start() {
        update(UNKNOWN_KEY);

        while (isRunning) {
            update(terminal.readInput());
        }

        terminal.clearScreen();
        terminal.flush();
    }

    private void update(KeyStroke keyStroke) {
        inputHandler.handle(keyStroke);
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

    private boolean isItemSelectable(int itemIndex) {
        return menuList.get(itemIndex) instanceof SelectableItem;
    }

    private void initInputActions() {
        inputHandler.addKeyActions(
            new KeyAction(KeyType.ENTER, () -> selectItem(cursor.getPosition())),

            new KeyAction(KeyType.ESCAPE, this::exit),

            new KeyAction(KeyType.ARROW_UP, () -> {
                moveCursor(-1);

                cursor.setCursorSymbol("<");
            }),

            new KeyAction(KeyType.ARROW_DOWN, () -> {
                moveCursor(1);

                cursor.setCursorSymbol(">");
            })
        );
    }

    private void moveCursor(int cursorMovement) {
        int x = cursor.getPosition().x();
        int y = cursor.getPosition().y();

        do {
            y += cursorMovement;

            if (y < 0) y = menuList.size() - 1;

            if (y > menuList.size() - 1) y = 0;

            if (y == cursor.getPosition().y()) return;
        }
        while (!(menuList.get(y) instanceof SelectableItem));

        cursor.setPosition(Position.of(x, y));
    }

    private void selectItem(Position cursorPosition) {
        if (!(menuList.get(cursorPosition.y()) instanceof SelectableItem selectableItem))
            return;

        selectableItem.select(new MenuContext(this, itemIndent, cursorPosition.y()));

        update(UNKNOWN_KEY);
    }

    public void exit() {
        isRunning = false;
    }

    public static String getVersion() {
        return Config.VERSION;
    }

    public TerminalBackend getTerminal() {
        return terminal;
    }
}
