package co.edu.eci.ecoappetite.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ConfigLoader {
    private static Map<String, String> config;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readValue(new File("config.json"), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar config.json", e);
        }
    }

    public static String get(String key) {
        return config.get(key);
    }
}