package io.github.bfur64.menu.item;

import io.github.bfur64.menu.utils.ErrorListener;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public abstract class Item {
    protected final String name;
    protected final boolean isSelectable;
    protected boolean shouldExit;
    protected @Nullable ErrorListener errorListener;

    protected Item(String name, boolean isSelectable) {
        this(name, isSelectable, false);
    }

    protected Item(String name, boolean isSelectable, boolean shouldExit) {
        this.name = name;
        this.isSelectable = isSelectable;
        this.shouldExit = shouldExit;
    }

    public String getDisplayName() {
        return name;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    public void selectItem() {}

    public void setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }
}
