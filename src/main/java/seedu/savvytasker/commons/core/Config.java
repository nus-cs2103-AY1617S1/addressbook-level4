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
<<<<<<< HEAD
    private String savvyTaskerListName = "MyTaskList";
=======
    private String savvyTaskerName = "MyTaskList";
>>>>>>> parent of ff51186... Revert "Save Command and Keyboard Shortcuts and Task Card Colour Code according to Priority Level"


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

<<<<<<< HEAD
    public String getSavvyTaskerListName() {
        return savvyTaskerListName;
    }

    public void setSavvyTaskerName(String savvyTaskerName) {
        this.savvyTaskerListName = savvyTaskerName;
=======
    public String getSavvyTaskerName() {
        return savvyTaskerName;
    }

    public void setSavvyTaskerName(String savvyTaskerName) {
        this.savvyTaskerName = savvyTaskerName;
>>>>>>> parent of ff51186... Revert "Save Command and Keyboard Shortcuts and Task Card Colour Code according to Priority Level"
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
<<<<<<< HEAD
                && Objects.equals(savvyTaskerListName, o.savvyTaskerListName);
=======
                && Objects.equals(savvyTaskerName, o.savvyTaskerName);
>>>>>>> parent of ff51186... Revert "Save Command and Keyboard Shortcuts and Task Card Colour Code according to Priority Level"
    }

    @Override
    public int hashCode() {
<<<<<<< HEAD
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, savvyTaskerFilePath, savvyTaskerListName);
=======
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, savvyTaskerFilePath, savvyTaskerName);
>>>>>>> parent of ff51186... Revert "Save Command and Keyboard Shortcuts and Task Card Colour Code according to Priority Level"
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + savvyTaskerFilePath);
<<<<<<< HEAD
        sb.append("\nSavvy Tasker List name : " + savvyTaskerListName);
=======
        sb.append("\nSavvyTasker name : " + savvyTaskerName);
>>>>>>> parent of ff51186... Revert "Save Command and Keyboard Shortcuts and Task Card Colour Code according to Priority Level"
        return sb.toString();
    }

}
