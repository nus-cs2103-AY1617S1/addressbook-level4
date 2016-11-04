package seedu.agendum.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_DATA_DIR = "data/";
    public static final String DEFAULT_JSON_DIR = "json/";
    public static final String DEFAULT_CONFIG_FILE = DEFAULT_DATA_DIR + DEFAULT_JSON_DIR + "config.json";
    public static final String DEFAULT_ALIAS_TABLE_FILE =  DEFAULT_DATA_DIR + DEFAULT_JSON_DIR + "commands.json";
    public static final String DEFAULT_USER_PREFS_FILE = DEFAULT_DATA_DIR + DEFAULT_JSON_DIR + "preferences.json";
    public static final String DEFAULT_SAVE_LOCATION = DEFAULT_DATA_DIR + "todolist.xml";

    // Config values customizable through config file
    private String appTitle = "Agendum";
    private Level logLevel = Level.INFO;
    private String aliasTableFilePath = DEFAULT_ALIAS_TABLE_FILE;
    private String userPrefsFilePath = DEFAULT_USER_PREFS_FILE;
    private String toDoListFilePath = DEFAULT_SAVE_LOCATION;
    private String configFilePath = DEFAULT_CONFIG_FILE;
    private String toDoListName = "MyToDoList";

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

    public String getAliasTableFilePath() {
        return aliasTableFilePath;
    }

    public void setAliasTableFilePath(String aliasTableFilePath) {
        this.aliasTableFilePath = aliasTableFilePath;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getToDoListFilePath() {
        return toDoListFilePath;
    }

    public void setToDoListFilePath(String toDoListFilePath) {
        this.toDoListFilePath = toDoListFilePath;
    }

    public String getToDoListName() {
        return toDoListName;
    }

    public void setToDoListName(String toDoListName) {
        this.toDoListName = toDoListName;
    }
    
    public String getConfigFilePath() {
        return configFilePath;
    }
    
    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
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
                && Objects.equals(aliasTableFilePath, o.aliasTableFilePath)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(toDoListFilePath, o.toDoListFilePath)
                && Objects.equals(toDoListName, o.toDoListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, aliasTableFilePath,
                userPrefsFilePath, toDoListFilePath, toDoListName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nAlias Table file location: " + aliasTableFilePath);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + toDoListFilePath);
        sb.append("\nToDoList name : " + toDoListName);
        return sb.toString();
    }

}
