package examples;

import io.github.bfur64.menu.MenuManager;
import io.github.bfur64.menu.item.ActionItem;
import io.github.bfur64.menu.item.LineBreak;
import io.github.bfur64.menu.item.StaticText;
import io.github.bfur64.menu.item.Item;
import io.github.bfur64.terminal.Terminal;
import io.github.bfur64.terminal.interfaces.TerminalBackend;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        try (TerminalBackend terminal = Terminal.auto()) {
            terminal.start();

            List<Item> items = List.of(
                new StaticText("<< Some Kinda Title >>"),
                new LineBreak(),
                new ActionItem("Start", () -> {}),
                new ActionItem("About", () -> {}),
                new ActionItem("Exit", () -> {}, true),
                new LineBreak(),
                new StaticText("    This is so FUCKED!!!")
            );

            MenuManager menu = new MenuManager(terminal, items);

            menu.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
