package seedu.todo.commons.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "GetShitDone";
    private Level logLevel = Level.INFO;
    private String databaseFilePath = "database.json";
    private Map<String, String> aliases = new HashMap<String, String>();

    public Config() {
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getDatabaseFilePath() {
        return databaseFilePath;
    }

    public void setDatabaseFilePath(String databaseFilePath) {
        this.databaseFilePath = databaseFilePath;
    }
    
    public Map<String, String> getAliases() {
        return aliases;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof Config)){ //this handles null as well.
            return false;
        }

        Config o = (Config)other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(databaseFilePath, o.databaseFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, databaseFilePath);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nLocal data file location : " + databaseFilePath);
        return sb.toString();
    }
    
    public List<ConfigDefinition> getDefinitions() {
        ConfigDefinition configAppTitle = new ConfigDefinition("appTitle", "App Title", appTitle);
        ConfigDefinition configDatabaseFilePath = new ConfigDefinition("databaseFilePath", "Database File Path", databaseFilePath);
                
        return Arrays.asList(configAppTitle, configDatabaseFilePath);
    }
    
    public List<String> getDefinitionsNames() {
        List<ConfigDefinition> definitions = getDefinitions();
        List<String> names = new ArrayList<>();
        
        for (ConfigDefinition definition : definitions) {
            names.add(definition.getConfigName());
        }
        
        return names;
    }

}
