package io.github.bfur64.menu.item;

import io.github.bfur64.menu.MenuContext;

public abstract class Item {
    protected final String displayName;
    protected final boolean isSelectable;
    protected boolean shouldExit;

    protected Item(String displayName, boolean isSelectable) {
        this(displayName, isSelectable, false);
    }

    protected Item(String displayName, boolean isSelectable, boolean shouldExit) {
        this.displayName = displayName;
        this.isSelectable = isSelectable;
        this.shouldExit = shouldExit;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    public void selectItem(MenuContext menuContext) {

    }
}
