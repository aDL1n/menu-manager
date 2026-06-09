package io.github.bfur64.menu;

import org.jspecify.annotations.NullMarked;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@NullMarked
class Config {
    public static final String VERSION;

    private Config() {}

    static {
        String version;

        try (InputStream is = Config.class.getResourceAsStream("/io/github/bfur64/menu/settings.json")) {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(is);

            version = jsonNode.get("version").asString();
        }
        catch (IOException e) {
            version = "null";
            System.out.println("Failed: " + e.getMessage() + Arrays.toString(e.getStackTrace()));
        }

        VERSION = version;
    }
}
