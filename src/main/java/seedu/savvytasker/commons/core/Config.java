package seedu.savvytasker.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Savvy Tasker";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String savvyTaskerFilePath = "data/savvytasker.xml";
    private String savvyTaskerName = "MyTaskList";


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

    public String getSavvyTaskerFilePath() {
        return savvyTaskerFilePath;
    }

    public void setSavvyTaskerFilePath(String savvyTaskerFilePath) {
        this.savvyTaskerFilePath = savvyTaskerFilePath;
    }

    public String getSavvyTaskerName() {
        return savvyTaskerName;
    }

    public void setSavvyTaskerName(String savvyTaskerName) {
        this.savvyTaskerName = savvyTaskerName;
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
                && Objects.equals(savvyTaskerFilePath, o.savvyTaskerFilePath)
                && Objects.equals(savvyTaskerName, o.savvyTaskerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, savvyTaskerFilePath, savvyTaskerName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + savvyTaskerFilePath);
        sb.append("\nSavvyTasker name : " + savvyTaskerName);
        return sb.toString();
    }

}
