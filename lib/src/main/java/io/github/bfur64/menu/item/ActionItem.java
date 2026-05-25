package io.github.bfur64.menu.item;

import io.github.bfur64.menu.MenuContext;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ActionItem extends Item {
    private final Runnable action;

    public ActionItem(String name, Runnable action) {
        this(name, action, false);
    }

    public ActionItem(String name, boolean shouldExit) {
        this(name, () -> {}, shouldExit);
    }

    public ActionItem(String name, Runnable action, boolean shouldExit) {
        super(name, true, shouldExit);
        this.action = action;
    }

    @Override
    public void selectItem(MenuContext menuContext) {
        action.run();
    }
}
