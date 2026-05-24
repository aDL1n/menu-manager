package io.github.bfur64.menu;

import io.github.bfur64.menu.item.Item;
import io.github.bfur64.terminal.interfaces.TerminalBackend;

import java.util.List;

public class MenuRenderer {

    private final TerminalBackend terminal;
    private final List<Item> menuItems;
    private final MenuCursor cursor;
    private final int itemIdent;

    private final int selectableItemCount;

    public MenuRenderer(
            TerminalBackend terminal,
            List<Item> menuItems,
            MenuCursor cursor,
            int itemIdent) {
        this.terminal = terminal;
        this.menuItems = menuItems;
        this.cursor = cursor;
        this.itemIdent = itemIdent;

        this.selectableItemCount = countSelectableItems(menuItems);
    }

    public void update() {
        terminal.clearScreen();

        drawMenu();
        drawCursor();

        terminal.flush();
    }

    private void drawMenu() {
        for (int i = 0; i < menuItems.size(); i++) {
            terminal.put(itemIdent, i, menuItems.get(i).getDisplayName());
        }
    }

    private void drawCursor() {
        if (selectableItemCount == 0) return;

        terminal.put(cursor.getPosition().getX(), cursor.getPosition().getY(), cursor.getCursorSymbol());
    }

    private int countSelectableItems(List<Item> items) {
        return (int) items.stream()
                .filter(Item::isSelectable)
                .count();
    }
}
