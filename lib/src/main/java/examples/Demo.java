package examples;

import io.github.bfur64.menu.MenuManager;
import io.github.bfur64.menu.item.ActionItem;
import io.github.bfur64.menu.item.BreakItem;
import io.github.bfur64.menu.item.TextItem;
import io.github.bfur64.menu.item.Item;
import io.github.bfur64.terminal.Terminal;
import io.github.bfur64.terminal.input.KeyStroke;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        try (
            Terminal terminal = Terminal.auto();
        ) {

            List<Item> items = List.of(
                new TextItem("<< Some Kinda Title >>"),
                new BreakItem(),
                new ActionItem("Start", () -> {}),
                new ActionItem("About", () -> {}),
                new ActionItem("Exit", () -> {}, true),
                new BreakItem(),
                new TextItem("    This is so FUCKED!!!")
            );

            MenuManager menu = new MenuManager(terminal, items);

            menu.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
