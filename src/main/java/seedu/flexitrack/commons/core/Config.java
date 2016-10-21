package seedu.flexitrack.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "FlexiTrack";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String flexiTrackFilePath = "tasktracker.xml";
    private String flexiTrackName = "MyTaskTracker";

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

    public String getFlexiTrackFilePath() {
        return flexiTrackFilePath;
    }

    public void setFlexiTrackFilePath(String flexiTrackFilePath) {
        this.flexiTrackFilePath = flexiTrackFilePath;
    }

    public String getFlexiTrackName() {
        return flexiTrackName;
    }

    public void setFlexiTrackName(String flexiTrackName) {
        this.flexiTrackName = flexiTrackName;
    }

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
                && Objects.equals(flexiTrackFilePath, o.flexiTrackFilePath)
                && Objects.equals(flexiTrackName, o.flexiTrackName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, flexiTrackFilePath, flexiTrackName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + flexiTrackFilePath);
        sb.append("\nFlexiTrack name : " + flexiTrackName);
        return sb.toString();
    }

}
