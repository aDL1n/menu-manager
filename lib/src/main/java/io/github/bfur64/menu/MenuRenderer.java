package io.github.bfur64.menu;

import io.github.bfur64.menu.item.Item;
import io.github.bfur64.terminal.interfaces.TerminalBackend;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
public class MenuRenderer {
    private final TerminalBackend terminal;
    private final List<Item> menuItems;
    private final MenuCursor cursor;
    private final int itemIndent;

    private final int selectableItemCount;

    private @Nullable Popup popup;

    public MenuRenderer(TerminalBackend terminal, List<Item> menuItems, MenuCursor cursor, int itemIndent) {
        this.terminal = terminal;
        this.menuItems = menuItems;
        this.cursor = cursor;
        this.itemIndent = itemIndent;

        this.selectableItemCount = countSelectableItems(menuItems);
    }

    public void update() {
        terminal.clearScreen();

        drawMenu();
        drawCursor();
        drawPopup();

        terminal.flush();
    }

    private void drawMenu() {
        for (int i = 0; i < menuItems.size(); i++) {
            terminal.put(itemIndent, i, menuItems.get(i).getDisplayName());
        }
    }

    private void drawCursor() {
        if (selectableItemCount == 0) return;

        terminal.put(cursor.getPosition().x(), cursor.getPosition().y(), cursor.getCursorSymbol());
    }

    private void drawPopup() {
        if (popup == null) return;

        popup.draw();
    }

    private int countSelectableItems(List<Item> items) {
        return (int) items.stream()
            .filter(Item::isSelectable)
            .count();
    }

    public void setPopup(@Nullable Popup popup) {
        this.popup = popup;
    }
}
