package io.github.bfur64.menu.item;

import io.github.bfur64.menu.MenuContext;
import io.github.bfur64.menu.utils.Property;

public class ToggleItem extends Item {
    private static final String TOGGLE_ON = "■";
    private static final String TOGGLE_OFF = "□";

    private final Property<Boolean> property;

    public ToggleItem(String label, Property<Boolean> property) {
        super(label, true);

        this.property = property;
    }

    @Override
    public String getDisplayName() {
        String toggle = property.get() ? TOGGLE_ON : TOGGLE_OFF;
        return toggle + " " + label;
    }

    @Override
    public void selectItem(MenuContext menuContext) {
        if (property.get()) {
            property.set(false);
        }
        else {
            property.set(true);
        }
    }
}
