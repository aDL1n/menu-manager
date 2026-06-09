package io.github.bfur64.menu.item;

import org.jspecify.annotations.NullMarked;

import java.util.function.Consumer;

@NullMarked
public class ActionItem extends SelectableItem {
    private final Consumer<MenuContext> action;

    public ActionItem(String name, Consumer<MenuContext> action) {
        super(name);
        this.action = action;
    }

    @Override
    public void selectItem() {
        action.run();
    }
}
