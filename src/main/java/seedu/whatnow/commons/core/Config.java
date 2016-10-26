package seedu.whatnow.commons.core;
//@@author A0141021H-reused @@author A0129772U-reused
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "WhatNow App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String whatNowFilePath = "data/whatnow.xml";
    private String whatNowName = "MyWhatNow";


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

    public String getWhatNowFilePath() {
        return whatNowFilePath;
    }

    public void setWhatNowFilePath(String whatNowFilePath) {
        this.whatNowFilePath = whatNowFilePath;
    }

    public String getWhatNowName() {
        return whatNowName;
    }

    public void setWhatNowName(String whatNowName) {
        this.whatNowName = whatNowName;
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
                && Objects.equals(whatNowFilePath, o.whatNowFilePath)
                && Objects.equals(whatNowName, o.whatNowName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, whatNowFilePath, whatNowName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + whatNowFilePath);
        sb.append("\nWhatNow name : " + whatNowName);
        return sb.toString();
    }

}
