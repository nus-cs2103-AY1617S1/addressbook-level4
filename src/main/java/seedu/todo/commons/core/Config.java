package seedu.todo.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Uncle Jim's Discount To-do List";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String todoListFilePath = "data/todolist.xml";
    private String todoListName = "My Todo List";


    public Config() {}

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

    public String getTodoListFilePath() {
        return todoListFilePath;
    }

    public void setTodoListFilePath(String todoListFIlePath) {
        this.todoListFilePath = todoListFIlePath;
    }

    public String getTodoListName() {
        return todoListName;
    }

    public void setTodoListName(String todoListName) {
        this.todoListName = todoListName;
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
                && Objects.equals(todoListFilePath, o.todoListFilePath)
                && Objects.equals(todoListName, o.todoListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, todoListFilePath, todoListName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder()
            .append("App title : ").append(appTitle)
            .append("\nCurrent log level : ").append(logLevel)
            .append("\nPreference file Location : ").append(userPrefsFilePath)
            .append("\nLocal data file location : ").append(todoListFilePath)
            .append("\nTodo List name : ").append(todoListName);
        return sb.toString();
    }

}
