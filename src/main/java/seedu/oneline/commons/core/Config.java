package seedu.oneline.commons.core;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "OneLine";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskBookFileName = "taskbook.xml";
    private String taskBookFilePath = "data/" + taskBookFileName;
    private String taskBookName = "MyTaskBook";


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

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }
    
    public String getTaskBookFileName() {
        return taskBookFileName;
    }
    
    public void setTaskBookFileName(String taskBookFileName) {
        this.taskBookFileName = taskBookFileName;
    }

    public String getTaskBookFilePath() {
        return taskBookFilePath;
    }

    public void setTaskBookFilePath(String taskBookFilePath) {
        this.taskBookFilePath = taskBookFilePath;
    }
    
    //@@author A0121657H
    public String getTaskBookName() {
        return taskBookName;
    }

    public void setTaskBookName(String taskBookName) {
        this.taskBookName = taskBookName;
    }

    public void setStorageLocation(String storagePath) {
        this.setTaskBookFilePath(Paths.get(storagePath, taskBookFileName).toString());
    }
    //@@author 

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
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(taskBookFilePath, o.taskBookFilePath)
                && Objects.equals(taskBookName, o.taskBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskBookFilePath, taskBookName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskBookFilePath);
        sb.append("\nTaskBook name : " + taskBookName);
        return sb.toString();
    }

}
