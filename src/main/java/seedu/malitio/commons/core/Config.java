package seedu.malitio.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";
    public static final String DEFAULT_FILE_NAME = "malitio.xml";
    public static final String DEFAULT_FILE_PATH = "data/";

    // Config values customizable through config file
    private String appTitle = "Malitio";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String malitioFilePath = DEFAULT_FILE_PATH + DEFAULT_FILE_NAME;
    private String malitioName = "MyMalitio";


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

    public String getMalitioFilePath() {
        return malitioFilePath;
    }

    public void setMalitioFilePath(String malitioFilePath) {
        this.malitioFilePath = malitioFilePath;
    }

    public String getMalitioName() {
        return malitioName;
    }

    public void setMalitioName(String malitioName) {
        this.malitioName = malitioName;
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
                && Objects.equals(malitioFilePath, o.malitioFilePath)
                && Objects.equals(malitioName, o.malitioName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, malitioFilePath, malitioName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + malitioFilePath);
        sb.append("\nMalitio name : " + malitioName);
        return sb.toString();
    }

}
