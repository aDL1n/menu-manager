package examples;

import io.github.bfur64.menu.MenuManager;
import io.github.bfur64.menu.item.*;
import io.github.bfur64.menu.utils.Property;
import io.github.bfur64.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class DemoEditable {
    public static void main(String[] args) throws IOException {
        try (
            Terminal terminal = Terminal.auto()
        ) {
            Config config = new Config();

            List<Item> items = List.of(
                new TextItem("Editing Test"),
                new BreakItem(),
                new ActionItem("[ Check Credentials ]", () -> startGame(config, terminal)),
                new BreakItem(),
                new EditableItem<>("Name", config.getNameProperty()),
                new EditableItem<>("Age", config.getAgeProperty()),
                new EditableItem<>("Allowance", config.getAllowanceProperty()),
                new ToggleItem("Admin", config.getAdminProperty())
            );

            MenuManager menu = new MenuManager(terminal, items);
            menu.run();
        }
    }

    private static void startGame(Config config, Terminal terminal) {
        MenuManager menu = new MenuManager(terminal, List.of(
                new TextItem("Credentials"),
                new BreakItem(),
                new TextItem("Name: " + config.getName()),
                new TextItem("Age: " + config.getAge()),
                new TextItem("Allowance: " + config.getAllowance()),
                new TextItem("Admin: " + config.isAdmin()),
                new BreakItem(),
                new ActionItem("[ Return ]", () -> {}, true)
        ));

        menu.run();
    }
}

class Config {
    private String name;
    private int age;
    private double allowance;
    private boolean isAdmin;

    public Property<String> getNameProperty() {
        return Property.create(this::getName, this::setName, String::toString);
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public Property<Integer> getAgeProperty() {
        return Property.create(this::getAge, this::setAge, Integer::parseInt);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int value) {
        age = value;
    }

    public Property<Double> getAllowanceProperty() {
        return Property.create(this::getAllowance, this::setAllowance, Double::parseDouble);
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public Property<Boolean> getAdminProperty() {
        return Property.create(this::isAdmin, this::setAdmin, Boolean::parseBoolean);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
