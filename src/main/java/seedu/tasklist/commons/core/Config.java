package seedu.tasklist.commons.core;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Lazyman's Friend";
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

    public void setTaskListFilePath(String taskListFilePath) throws JSONException, IOException, ParseException {
    	    	//JSONObject obj = (JSONObject) new JSONParser().parse(read);
    	    	//String result = (String) obj.get("taskListFilePath");
    	    	//JSONObject tasklist = (JSONObject) obj.get("taskListFilePath");
    	    	//System.out.println("Result: "+ result);
    	    	JSONObject obj = new JSONObject();
    			obj.put("taskListFilePath", taskListFilePath);
    			obj.put("userPrefsFilePath", "preferences.json");
    			obj.put("appTitle", "Lazyman's Friend");
    			obj.put("logLevel", "INFO");
    			obj.put("taskListName", "MyTaskList");
    			try (FileWriter file = new FileWriter("config.json")) {
    				file.write(obj.toJSONString());
    				System.out.println("Successfully Copied JSON Object to File...");
    				System.out.println("\nJSON Object: " + obj);
    			}
    			
    	this.taskListFilePath = taskListFilePath;
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
