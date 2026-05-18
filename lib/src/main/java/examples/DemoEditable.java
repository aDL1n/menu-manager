package examples;

import io.github.bfur64.menu.MenuManager;
import io.github.bfur64.menu.item.*;
import io.github.bfur64.menu.utils.Property;
import io.github.bfur64.terminal.Terminal;
import io.github.bfur64.terminal.input.KeyStroke;
import io.github.bfur64.terminal.input.KeyType;

import java.io.IOException;
import java.util.List;

public class DemoEditable {
    public static void main(String[] args) throws IOException {
        try (Terminal terminal = Terminal.auto()) {
            Config config = new Config();

            List<String> terminalInfo = terminal.getTerminalInfo();

            MenuManager menu = new MenuManager(terminal, List.of(
                new StaticText("Editing Test"),
                new StaticText("Menu Manager: " + MenuManager.getVersion()),
                new StaticText("Renderer: " + terminal.getCurrentTerminal()),
                new StaticText(terminalInfo.getFirst()),
                new StaticText(terminalInfo.get(1)),
                new StaticText(terminalInfo.getLast()),
                new LineBreak(),
                new ActionItem("[ Check Credentials ]", () -> startGame(config, terminal)),
                new LineBreak(),
                new EditableItem<>("Name", ": ", config.getNameProperty()),
                new EditableItem<>("Age", ": ", config.getAgeProperty()),
                new EditableItem<>("Allowance", " = ", config.getAllowanceProperty(), "Pesos"),
                new ToggleItem("Admin", config.getAdminProperty()),
                new KeyReaderItem("Read Key", config.getDropBlockProperty()),
                new LineBreak(),
                new StaticText("  | Dynamic Text |"),
                new LineBreak(),
                new DynamicText<>("Dynamic Name : ", config::getName),
                new DynamicText<>("Dynamic Age: ", config::getAge),
                new DynamicText<>("Dynamic Allowance = ", "PHP", config::getAllowance),
                new DynamicText<>("Drop Block Key: ", config::getDropBlock),
                new LineBreak(),
                new ActionItem("[ Exit ]", true)
            ));
            
            menu.run();
        }
    }

    private static void startGame(Config config, Terminal terminal) {
        MenuManager menu = new MenuManager(terminal, List.of(
                new StaticText("Credentials"),
                new LineBreak(),
                new StaticText("Name: " + config.getName()),
                new StaticText("Age: " + config.getAge()),
                new StaticText("Allowance: " + config.getAllowance()),
                new StaticText("Admin: " + config.isAdmin()),
                new LineBreak(),
                new ActionItem("[ Return ]", true)
        ));

        menu.run();
    }
}

class Config {
    private String name;
    private int age;
    private double allowance;
    private boolean isAdmin;

    private KeyStroke dropBlock = new KeyStroke(KeyType.ARROW_DOWN);

    public Property<String> getNameProperty() {
        return Property.create(this::getName, this::setName, String::toString)
            .withValidator(name -> !name.isEmpty(), "Name cannot be blank")
            .withValidator(name -> !name.equalsIgnoreCase("terrance"), "Terrance is not valid!")
            .withValidator(name -> !name.equalsIgnoreCase("Drew"), "How dare you Drew?!?!?1");
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public Property<Integer> getAgeProperty() {
        return Property.create(this::getAge, this::setAge, Integer::parseInt)
            .withValidator(age -> age >= 18)
            .withValidator(age -> age < 50, "You are too old!");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int value) {
        age = value;
    }

    public Property<Double> getAllowanceProperty() {
        return Property.create(this::getAllowance, this::setAllowance);
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public Property<Boolean> getAdminProperty() {
        return Property.create(this::isAdmin, this::setAdmin);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Property<KeyStroke> getDropBlockProperty() {
        return Property.create(this::getDropBlock, this::setDropBlock);
    }

    public KeyStroke getDropBlock() {
        return dropBlock;
    }

    public void setDropBlock(KeyStroke dropBlock) {
        this.dropBlock = dropBlock;
    }
}
