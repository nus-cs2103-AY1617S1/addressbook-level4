package tars.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "T.A.R.S. - Task and Remember Stuff";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String tarsFilePath = "data/tars.xml";
    private String tarsName = "MyTars";


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

    public String getTarsFilePath() {
        return tarsFilePath;
    }

    public void setTarsFilePath(String tarsFilePath) {
        this.tarsFilePath = tarsFilePath;
    }

    public String getTarsName() {
        return tarsName;
    }

    public void setTarsName(String tarsName) {
        this.tarsName = tarsName;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { // this handles null as well.
            return false;
        }

        Config o = (Config)other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(tarsFilePath, o.tarsFilePath)
                && Objects.equals(tarsName, o.tarsName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, tarsFilePath, tarsName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + tarsFilePath);
        sb.append("\nTars name : " + tarsName);
        return sb.toString();
    }

}
