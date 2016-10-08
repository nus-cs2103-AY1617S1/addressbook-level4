package seedu.address.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Remindaroo";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String appDataFilePath = "data/remindaroo.xml";
    private String appName = "Remindaroo";


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

    public String getAddressBookFilePath() {
        return appDataFilePath;
    }

    public void setAddressBookFilePath(String addressBookFilePath) {
        this.appDataFilePath = addressBookFilePath;
    }

    public String getAddressBookName() {
        return appName;
    }

    public void setAddressBookName(String addressBookName) {
        this.appName = addressBookName;
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
                && Objects.equals(appDataFilePath, o.appDataFilePath)
                && Objects.equals(appName, o.appName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, appDataFilePath, appName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + appDataFilePath);
        sb.append("\nApplication name : " + appName);
        return sb.toString();
    }

}
