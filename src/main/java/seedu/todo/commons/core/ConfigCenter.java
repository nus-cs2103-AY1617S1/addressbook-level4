package seedu.todo.commons.core;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.todo.MainApp;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.ConfigUtil;

// @@author A0139812A
/**
 * Singleton to store the current Config used by the app.
 * This is especially necessary in a testing environment, 
 * and needs to be decoupled with the MainApp instance,
 * but rather instantiated independently.
 */
public class ConfigCenter {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static ConfigCenter instance;
    
    private Config config;
    private String configFilePath;

    public static ConfigCenter getInstance() {
        if (instance == null) {
            instance = new ConfigCenter();
        }
        
        return instance;
    }
    
    public void setConfigFilePath(String path) {
        configFilePath = path;
    }
    
    public Config getConfig() {
        if (config == null) {
            Optional<Config> configOptional;
            
            try {
                configOptional = ConfigUtil.readConfig(configFilePath);
                config = configOptional.orElse(new Config());
            } catch (DataConversionException e) {
                logger.warning("Config file at " + configFilePath + " is not in the correct format. " +
                        "Using default config properties");
            }
        }
        
        return config;
    }
    
    public void saveConfig(Config config) throws IOException {
        ConfigUtil.saveConfig(config, configFilePath);
        this.config = config;
    }
}
