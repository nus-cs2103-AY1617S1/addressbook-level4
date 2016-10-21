package seedu.taskscheduler.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Must Do List";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskSchedulerFilePath = "data/taskscheduler.xml";
    private String taskSchedulerName = "MustDoList";


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

    public String getTaskSchedulerFilePath() {
        return taskSchedulerFilePath;
    }

    public void setTaskSchedulerFilePath(String taskSchedulerFilePath) {
        this.taskSchedulerFilePath = taskSchedulerFilePath;
    }

    public String getTaskSchedulerName() {
        return taskSchedulerName;
    }

    public void setTaskSchedulerName(String taskSchedulerName) {
        this.taskSchedulerName = taskSchedulerName;
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
                && Objects.equals(taskSchedulerFilePath, o.taskSchedulerFilePath)
                && Objects.equals(taskSchedulerName, o.taskSchedulerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskSchedulerFilePath, taskSchedulerName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskSchedulerFilePath);
        sb.append("\nTaskScheduler name : " + taskSchedulerName);
        return sb.toString();
    }

}
