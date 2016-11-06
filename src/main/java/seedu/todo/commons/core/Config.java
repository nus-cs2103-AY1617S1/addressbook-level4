package seedu.todo.commons.core;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

import seedu.todo.commons.util.ConfigUtil;

/**
 * Config values used by the app
 */
public class Config {
    
    //@@author A0093896H
    public static final String DEFAULT_CONFIG_FILE = "defaultConfig.json";
    //@@author
    public static final String USER_CONFIG_FILE = "config.json";
    
    // Config values customizable through config file
    private String appTitle = "Do-DoBird";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String toDoListFilePath = "data/dodobird.xml";
    private String toDoListName = "dodobird";
    
    //@@author A0093896H
    public void resetConfig(Config initializedConfig) throws IOException {
        ConfigUtil.saveConfig(initializedConfig, USER_CONFIG_FILE);
        
        this.setAppTitle(initializedConfig.getAppTitle());
        this.setLogLevel(initializedConfig.getLogLevel());
        this.setToDoListFilePath(initializedConfig.getToDoListFilePath());
        this.setToDoListName(initializedConfig.getToDoListName());
        this.setUserPrefsFilePath(initializedConfig.getUserPrefsFilePath());
    }
    //@@author
    
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

    public void updateToDoListFilePath(String toDoListFilePath) throws IOException {
        this.setToDoListFilePath(toDoListFilePath);
        ConfigUtil.saveConfig(this, Config.USER_CONFIG_FILE);
    }
    

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof Config)){ //this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(toDoListFilePath, o.toDoListFilePath)
                && Objects.equals(toDoListName, o.toDoListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, toDoListFilePath, toDoListName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + toDoListFilePath);
        sb.append("\nToDoList name : " + toDoListName);
        return sb.toString();
    }

}
