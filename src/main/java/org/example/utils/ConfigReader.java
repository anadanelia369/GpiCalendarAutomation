package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {

    private static String read(String key) {

        Properties properties = new Properties();
        try {
            InputStream inputStream = Files.newInputStream(Paths.get("config.properties"));
            properties.load(inputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key);
    }

    public static String get(String key) {
        return read(key);

    }

    public static Long getLong(String key) {
        return Long.parseLong(read(key));
    }
}

