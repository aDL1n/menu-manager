package examples;

import io.github.bfur64.menu.MenuManager;
import io.github.bfur64.menu.item.ActionItem;
import io.github.bfur64.menu.item.display.DynamicText;
import io.github.bfur64.menu.item.display.LineBreak;
import io.github.bfur64.menu.item.display.StaticText;
import io.github.bfur64.menu.item.input.InputItem;
import io.github.bfur64.menu.item.input.KeyInputItem;
import io.github.bfur64.menu.item.input.ToggleItem;
import io.github.bfur64.menu.Property;
import io.github.bfur64.terminal.BufferedTerminal;
import io.github.bfur64.terminal.Terminal;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;
import io.github.bfur64.terminal.interfaces.TerminalBackend;

import java.io.IOException;
import java.util.List;

public class DemoEditableOld {
    public static void main(String[] args) throws IOException {
        try (TerminalBackend terminal = BufferedTerminal.auto()) {
            terminal.start();
            Config config = new Config();

            MenuManager menu = new MenuManager(terminal, List.of(
                new StaticText("Editing Test"),
                new StaticText("Menu Manager: " + MenuManager.getVersion()),
                new StaticText("Renderer: " + terminal.getTerminalInfo()),
                new StaticText(terminal.getTerminalInfo()),
                new StaticText(Terminal.getLibraryInfo()),
                new LineBreak(),
                new ActionItem("[ Check Credentials ]", () -> startGame(config, terminal)),
                new LineBreak(),
                new InputItem<>("Name", ": ", config.username),
                new InputItem<>("Age", ": ", config.age),
                new InputItem<>("Allowance", " = ", config.allowance, "Pesos"),
                new ToggleItem("Admin", config.admin),
                new KeyInputItem("Read Key", config.dropBlock),
                new LineBreak(),
                new StaticText("  | Dynamic Text |"),
                new LineBreak(),
                new DynamicText<>("Dynamic Name : ", config.username::get),
                new DynamicText<>("Dynamic Age: ", config.age::get),
                new DynamicText<>("Dynamic Allowance = ", "PHP", config.allowance::get),
                new DynamicText<>("Drop Block Key: ", config.allowance::get),
                new LineBreak(),
                new ActionItem("[ Exit ]", true)
            ));
            
            menu.start();
        }
    }

    private static void startGame(Config config, TerminalBackend terminal) {
        MenuManager menu = new MenuManager(terminal, List.of(
                new StaticText("Credentials"),
                new LineBreak(),
                new StaticText("Name: " + config.username.get()),
                new StaticText("Age: " + config.age.get()),
                new StaticText("Allowance: " + config.allowance.get()),
                new StaticText("Admin: " + config.admin.get()),
                new LineBreak(),
                new ActionItem("[ Return ]", true)
        ));

        int age = config.age.get();
        int newAge = 34;

        if (config.age.isValid(newAge)) {
            config.age.set(newAge);
        }

        menu.start();
    }
}

class Config {
    Property<String> username = Property.of("")
            .require(name -> !name.isEmpty(), "Name cannot be blank")
            .require(name -> !name.equalsIgnoreCase("terrance"), "Terrance is not valid?!?!")
            .require(name -> !name.equalsIgnoreCase("drew"))
            .parser(String::toString).build();

    private Integer ageInt = 0;
    Property<Integer> age = Property.of(0)
            .require(age -> age >= 18)
            .require(age -> age < 50, "You are too old!")
            .setter(value -> ageInt = value )
            .getter( () -> ageInt )
            .parser(Integer::parseInt).build();

    Property<Double> allowance = Property.of(0D)
            .parser(Double::parseDouble).build();

    Property<Boolean> admin = Property.of(false).build();

    Property<KeyStroke> dropBlock = Property.of(new KeyStroke(KeyType.ARROW_DOWN)).build();
}
