package examples;

import io.github.bfur64.menu.MenuManager;
import io.github.bfur64.menu.item.ActionItem;
import io.github.bfur64.menu.item.StaticText;
import io.github.bfur64.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class StressTest {
    private final Terminal terminal;

    public static void main(String[] args) throws IOException {
        try (Terminal terminal = Terminal.auto()) {
            terminal.start();

            StressTest test = new StressTest(terminal);

            MenuManager menu = new MenuManager(terminal, List.of(
                    new ActionItem("[ Test 1 ]", test::test1),
                    new ActionItem("[ Test 2 ]", test::test2),
                    new ActionItem("[ Test 3 ]", test::test3),
                    new ActionItem("[ Test 4 ]", test::test4)
            ));
            menu.run();
        }
    }

    public StressTest(Terminal terminal) {
        this.terminal = terminal;
    }

    private void test1() {
        MenuManager menu = new MenuManager(terminal, List.of(
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only")
        ));
        menu.run();
    }

    private void test2() {
        MenuManager menu = new MenuManager(terminal, List.of(
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new ActionItem("Action!", () -> {}),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only")
        ));
        menu.run();
    }

    private void test3() {
        MenuManager menu = new MenuManager(terminal, List.of(
                new ActionItem("Action!", () -> {}),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new ActionItem("Action!", () -> {})
        ));
        menu.run();
    }

    private void test4() {
        MenuManager menu = new MenuManager(terminal, List.of(
                new ActionItem("Action!", () -> {}),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new ActionItem("Action!", () -> {}),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new StaticText("Text Only"),
                new ActionItem("Action!", () -> {})
        ));
        menu.run();
    }
}
