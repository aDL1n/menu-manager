package io.github.bfur64.menu;

import io.github.bfur64.menu.item.Item;
import io.github.bfur64.terminal.Terminal;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;

import java.util.List;

public class MenuManager {
    private static final int ITEM_INDENT = 3;

    private final Terminal terminal;
    private boolean isRunning = true;

    private final List<Item> menuList;

    private int cursorPos = 0;
    private int prevCursorPos = cursorPos;

    public MenuManager(Terminal terminal, List<Item> menuList) {
        this.terminal = terminal;
        this.menuList = menuList;

        initCursor();
    }

    public void run() {
        terminal.clearScreen();
        update();
        terminal.flush();

        while (isRunning) {
            KeyStroke keyStroke = terminal.readInput();

            terminal.clearScreen();

            update(keyStroke);

            terminal.flush();
        }

        terminal.clearScreen();
        terminal.flush();
    }

    private void update(KeyStroke keyStroke) {
        drawMenu();
        drawCursor(keyStroke);
    }

    private void update() {
        update(new KeyStroke(KeyType.UNKNOWN));
    }

    private void drawMenu() {
        for (int i = 0; i < menuList.size(); i++) {
            terminal.putString(ITEM_INDENT, i, menuList.get(i).getDisplayName());
        }
    }

    private void initCursor() {
        for (int i = 0; i < menuList.size(); i++) {
            if (menuList.get(i).isSelectable()) {
                prevCursorPos = cursorPos;
                cursorPos = i;
                break;
            }
        }
    }

    private void drawCursor(KeyStroke keyStroke) {
        switch (keyStroke.getKeyType()) {
            case ESCAPE -> isRunning = false;
            case ARROW_UP -> moveCursor(-1);
            case ARROW_DOWN -> moveCursor(+1);
            case ENTER -> selectItem();
        }

        int selectable = 0;
        for (Item item : menuList) {
            if (item.isSelectable()) selectable++;
        }

        if (selectable == 0) return;

        terminal.putString(0, prevCursorPos, " ");
        terminal.putString(0, cursorPos, ">");
    }

    private void moveCursor(int cursorMovement) {
        int newCursorPos = cursorPos;

        do {
            newCursorPos += cursorMovement;

            if (newCursorPos < 0) newCursorPos = menuList.size() - 1;
            if (newCursorPos > menuList.size() - 1) newCursorPos = 0;

            if (newCursorPos == cursorPos) return;
        }
        while(!menuList.get(newCursorPos).isSelectable());

        prevCursorPos = cursorPos;
        cursorPos = newCursorPos;
    }

    private void selectItem() {
        Item menuItem = menuList.get(cursorPos);
        menuItem.selectItem(new MenuContext(terminal, ITEM_INDENT, cursorPos));

        if (menuItem.exitRequested()) {
            isRunning = false;
        }

        update();
    }

    @SuppressWarnings("unused")
    public static String getVersion() {
        return Config.VERSION_NUMBER;
    }
}
