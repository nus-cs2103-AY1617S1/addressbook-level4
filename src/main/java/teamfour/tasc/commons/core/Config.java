package teamfour.tasc.commons.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

import javax.xml.bind.JAXBException;

import teamfour.tasc.commons.util.JsonUtil;
import teamfour.tasc.commons.util.XmlUtil;
import teamfour.tasc.storage.XmlSerializableTaskList;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "TaSc";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String taskListFilePath = "data/tasklist.xml";
    private String taskListName = "MyTaskList";

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

    public String getTaskListFilePath() {
        return taskListFilePath;
    }

    public void setTaskListFilePath(String newTaskListFilePath) {
        this.taskListFilePath = newTaskListFilePath;
    }
    
    public void changeTaskListFilePath(String newTaskListFilePath) throws IOException, JAXBException {
        File oldFile = new File(taskListFilePath);
        XmlSerializableTaskList data = XmlUtil.getDataFromFile(oldFile, XmlSerializableTaskList.class);
        oldFile.delete();
        File newFilePath = new File(newTaskListFilePath);
        newFilePath.mkdirs();
        File newFile = new File(newTaskListFilePath + "/tasklist.xml");
        newFile.createNewFile();
        XmlUtil.saveDataToFile(newFile, data);
        this.taskListFilePath = newTaskListFilePath + "/tasklist.xml";
        String newConfig = JsonUtil.toJsonString(this);
        PrintWriter newConfigFileWriter = new PrintWriter(DEFAULT_CONFIG_FILE);
        newConfigFileWriter.write(newConfig);
        newConfigFileWriter.close();
    }

    public String getTaskListName() {
        return taskListName;
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;
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
                && Objects.equals(taskListFilePath, o.taskListFilePath)
                && Objects.equals(taskListName, o.taskListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, taskListFilePath, taskListName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + taskListFilePath);
        sb.append("\nTaskList name : " + taskListName);
        return sb.toString();
    }

}
