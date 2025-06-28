package org.dexflex.basicallystopwatch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class StopwatchConfig {
    public float x = 0.5f;
    public float y = 0.5f;
    public String textColor = "#FFFFFF";

    private static final File CONFIG_FILE = new File("config/basicallystopwatch.json");

    public static StopwatchConfig load() {
        try {
            if (CONFIG_FILE.exists()) {
                return new Gson().fromJson(new FileReader(CONFIG_FILE), StopwatchConfig.class);
            }
        } catch (Exception ignored) {}
        StopwatchConfig config = new StopwatchConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(CONFIG_FILE);
            new GsonBuilder().setPrettyPrinting().create().toJson(this, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
