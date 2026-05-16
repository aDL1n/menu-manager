package io.github.bfur64.menu;

import io.github.bfur64.menu.item.Item;
import io.github.bfur64.terminal.Terminal;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;

import java.util.List;

public class MenuManager {
    private static final String VERSION_NUMBER = "0.2.0";
    private static final int ITEM_INDENT = 3;

    private final Terminal terminal;
    private boolean isFinished = false;

    private final List<Item> menuList;

    private int listIndex = 0;
    private int prevListIndex = listIndex;

    public MenuManager(Terminal terminal, List<Item> menuList) {
        this.terminal = terminal;
        this.menuList = menuList;

        initCursor();
    }

    public void run() {
        terminal.clearScreen();
        update();
        terminal.flush();

        while (!isFinished) {
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
                prevListIndex = listIndex;
                listIndex = i;
                break;
            }
        }
    }

    private void drawCursor(KeyStroke keyStroke) {
        switch (keyStroke.getKeyType()) {
            case ESCAPE -> isFinished = true;
            case ARROW_UP -> {
                prevListIndex = listIndex;

                do {
                    listIndex--;

                    if (listIndex < 0) {
                        listIndex = menuList.size() - 1;
                    }
                }
                while (!menuList.get(listIndex).isSelectable());
            }
            case ARROW_DOWN -> {
                prevListIndex = listIndex;

                do {
                    listIndex++;

                    if (listIndex > menuList.size() - 1) {
                        listIndex = 0;
                    }
                }
                while (!menuList.get(listIndex).isSelectable());
            }
            case ENTER -> {
                Item menuItem = menuList.get(listIndex);
                menuItem.selectItem(new MenuContext(terminal, ITEM_INDENT,listIndex));

                if (menuItem.exitRequested()) {
                    isFinished = true;
                }

                update();
            }
        }

        terminal.putString(0, prevListIndex, " ");
        terminal.putString(0, listIndex, ">");
    }

    @SuppressWarnings("unused")
    public static String getVersion() {
        return VERSION_NUMBER;
    }
}
