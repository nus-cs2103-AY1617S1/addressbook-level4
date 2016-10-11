package seedu.taskman.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "TaskMan";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskManFilePath = "data/taskMan.xml";
    private String taskManName = "MyTaskMan";


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

    public String getTaskManFilePath() {
        return taskManFilePath;
    }

    public void setTaskManFilePath(String taskManFilePath) {
        this.taskManFilePath = taskManFilePath;
    }

    public String getTaskManName() {
        return taskManName;
    }

    public void setTaskManName(String taskManName) {
        this.taskManName = taskManName;
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
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(taskManFilePath, o.taskManFilePath)
                && Objects.equals(taskManName, o.taskManName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskManFilePath, taskManName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskManFilePath);
        sb.append("\nTaskMan name : " + taskManName);
        return sb.toString();
    }

}
