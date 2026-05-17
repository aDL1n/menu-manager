package io.github.bfur64.menu.item;

import io.github.bfur64.menu.MenuContext;

public class ActionItem extends Item {
    private final Runnable runnable;

    public ActionItem(String label, Runnable runnable) {
        super(label, true);
        this.runnable = runnable;
    }

    public ActionItem(String label, Runnable runnable, boolean exitRequested) {
        this(label, runnable);
        this.exitRequested = true;
    }

    @Override
    public void selectItem(MenuContext menuContext) {
        runnable.run();
    }
}
