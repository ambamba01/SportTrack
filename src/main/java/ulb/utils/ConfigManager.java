package utils;

import exceptions.ConfigManagerException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Allow easy loading of information from the config package in resources.
 */
public class ConfigManager {

    private ConfigManager() {
        prop = new Properties();
    }

    private static final String FILE = "/config/config.properties";

    private final Properties prop;

    /**
     * Loads the properties from this url.
     *
     * @throws IOException if no file is found.
     */
    public void load() throws ConfigManagerException {
        try (InputStream input = getClass().getResourceAsStream(FILE)) {

            prop.load(input);
        } catch (IOException ex) {
            throw new ConfigManagerException(ex);
        }
    }

    /**
     * Returns the value from the key name.
     *
     * @param name key to found.
     * @return the value from the key-value pair.
     */
    public String getProperties(String name) {
        return prop.getProperty(name);
    }

    /**
     * Returns the instance of the singleton.
     *
     * @return the instance of the singleton.
     */
    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    private static class ConfigManagerHolder {

        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
