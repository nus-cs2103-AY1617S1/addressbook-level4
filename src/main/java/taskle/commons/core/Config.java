package taskle.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Taskle";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskManagerFileDirectory = "data";
    private String taskManagerFileName = "taskmanager.xml";
    private static final String DIRECTORY_DELIMITER = "\\";
    private String taskManagerName = "MyTaskManager";


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

    public String getTaskManagerFileName() {
        return taskManagerFileName;
    }
    
    public void setTaskManagerFileName(String taskManagerFileName) {
        this.taskManagerFileName = taskManagerFileName;
    }
    
    public String getTaskManagerFilePath() {
        return taskManagerFileDirectory + DIRECTORY_DELIMITER + taskManagerFileName;
    }
    
    public void setTaskManagerFileDirectory(String taskManagerFileDirectory) {
        this.taskManagerFileDirectory = taskManagerFileDirectory;
    }

    public String getTaskManagerFileDirectory() {
        return taskManagerFileDirectory;
    }
    
    public String getTaskManagerName() {
        return taskManagerName;
    }

    public void setTaskManagerName(String taskManagerName) {
        this.taskManagerName = taskManagerName;
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
                && Objects.equals(taskManagerFileDirectory, o.taskManagerFileDirectory)
                && Objects.equals(taskManagerFileName, o.taskManagerFileName)
                && Objects.equals(taskManagerName, o.taskManagerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskManagerFileDirectory, taskManagerName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskManagerFileDirectory + DIRECTORY_DELIMITER +
                taskManagerFileName);
        sb.append("\nTaskManager name : " + taskManagerName);
        return sb.toString();
    }

}
