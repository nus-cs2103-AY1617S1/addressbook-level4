package seedu.task.commons.core;

import java.util.Objects;
import java.util.logging.Level;

// @@author A0147944U
/**
 * Config values used by the app
 */
public class Config {

    // Config filename to adhere to
    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Task App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskManagerFilePath = "data/taskmanager.xml";
    private String taskManagerName = "MyTaskManager";
    private String sortPreference = "Default"; // Default sorting preset is applied on first startup

    /**
     * Retrieves appTitle in String format.
     * 
     * @return The appTitle of this config file in String format.
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * Change the appTitle in this config to the input.
     * 
     * @param appTitle
     *            The appTitle to change to.
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     * Retrieves loglevel in String format.
     * 
     * @return The loglevel of this config file in String foramt.
     */
    public Level getLogLevel() {
        return logLevel;
    }

    /**
     * Change the loglevel in this config to the input.
     * 
     * @param loglevel
     *            The loglevel to change to.
     */
    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Retrieves userPrefsFilePath in String format.
     * 
     * @return The userPrefsFilePath of this config file in String format.
     */
    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    /**
     * Change the userPrefsFilePath in this config to the input.
     * 
     * @param userPrefsFilePath
     *            The userPrefsFilePath to change to.
     */
    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    /**
     * Retrieves taskManagerFilePath in String format.
     * 
     * @return The taskManagerFilePath of this config file in String format.
     */
    public String getTaskManagerFilePath() {
        return taskManagerFilePath;
    }

    /**
     * Change the taskManagerFilePath in this config to the input.
     * 
     * @param taskManagerFilePath
     *            The taskManagerFilePath to change to.
     */
    public void setTaskManagerFilePath(String taskManagerFilePath) {
        this.taskManagerFilePath = taskManagerFilePath;
    }

    /**
     * Retrieves taskManagerName in String format.
     * 
     * @return The taskManagerName of this config file in String format.
     */
    public String getTaskManagerName() {
        return taskManagerName;
    }

    /**
     * Change the taskManagerName in this config to the input.
     * 
     * @param taskManagerName
     *            The taskManagerName to change to.
     */
    public void setTaskManagerName(String taskManagerName) {
        this.taskManagerName = taskManagerName;
    }

    /**
     * Retrieves sortPreference in String format.
     * 
     * @return The sortPreference of this config file in String format.
     */
    public String getsortPreference() {
        return sortPreference;
    }

    /**
     * Change the sortPreference in this config to the input.
     * 
     * @param sortPreference
     *            The sortPreference to change to.
     */
    public void setsortPreference(String sortPreference) {
        this.sortPreference = sortPreference;
    }

    /**
     * Checks if the two are the same and returns true if the configs are equal
     * to each other and false otherwise
     * 
     * @param other
     *            The config to check against.
     * @return true if the configs are equal to each other and false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { // this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(appTitle, o.appTitle) && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(taskManagerFilePath, o.taskManagerFilePath)
                && Objects.equals(taskManagerName, o.taskManagerName)
                && Objects.equals(sortPreference, o.sortPreference);
    }

    /**
     * Generates a hash code for the config parameters.
     * 
     * @return generated hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskManagerFilePath, taskManagerName,
                sortPreference);
    }

    /**
     * Generates a string of the config parameters in a readable format
     * 
     * @return generated string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskManagerFilePath);
        sb.append("\nTaskManager name : " + taskManagerName);
        sb.append("\nCurrent Sorting Preference : " + sortPreference);
        return sb.toString();
    }

}
