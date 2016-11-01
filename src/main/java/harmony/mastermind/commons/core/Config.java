package harmony.mastermind.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Mastermind";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskManagerFilePath = "data/mastermind.xml";
    private String taskManagerName = "MyTaskManager";


    public Config() {
    }
    
    public String getAppTitle() {
        return appTitle;
    }

    //@@author A0139194X
    public void setAppTitle(String appTitle) {
        assert appTitle != null;
        this.appTitle = appTitle;
    }

    //@@author
    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    //@@author A0139194X
    public void setUserPrefsFilePath(String userPrefsFilePath) {
        assert userPrefsFilePath != null;
        this.userPrefsFilePath = userPrefsFilePath;
    }

    //@@author
    public String getTaskManagerFilePath() {
        return taskManagerFilePath;
    }

    //@@author A0139194X
    public void setTaskManagerFilePath(String taskManagerFilePath) {
        assert taskManagerFilePath != null;
        this.taskManagerFilePath = taskManagerFilePath;
    }

    public String getTaskManagerName() {
        return taskManagerName;
    }

    //@@author A0139194X
    public void setTaskManagerName(String taskManagerName) {
        assert taskManagerName != null;
        this.taskManagerName = taskManagerName;
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
                && Objects.equals(taskManagerFilePath, o.taskManagerFilePath)
                && Objects.equals(taskManagerName, o.taskManagerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskManagerFilePath, taskManagerName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskManagerFilePath);
        sb.append("\nTaskManager name : " + taskManagerName);
        return sb.toString();
    }

}
