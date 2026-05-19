package io.github.bfur64.menu.item;

import io.github.bfur64.menu.MenuContext;
import io.github.bfur64.menu.utils.Property;

public class ToggleItem extends Item {
    private static final String TOGGLE_ON = "■";
    private static final String TOGGLE_OFF = "□";

    private final Property<Boolean> property;

    public ToggleItem(String name, Property<Boolean> property) {
        super(name, true);
        this.property = property;
    }

    @Override
    public String getDisplayName() {
        return (property.get() ? TOGGLE_ON : TOGGLE_OFF) + " " + name;
    }

    @Override
    public void selectItem(MenuContext menuContext) {
        property.set(!property.get());
    }
}
