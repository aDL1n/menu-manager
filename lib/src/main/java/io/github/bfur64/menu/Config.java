package io.github.bfur64.menu;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;

class Config {
    public static final String VERSION;

    static {
        InputStream is = Config.class.getResourceAsStream("/io/github/bfur64/menu/settings.json");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(is);

        VERSION = jsonNode.get("version").asString();
    }
}
