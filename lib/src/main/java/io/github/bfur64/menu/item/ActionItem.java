package io.github.bfur64.menu.item;

import io.github.bfur64.menu.MenuContext;
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
    public void select(MenuContext context) {
        action.accept(context);
    }
}
