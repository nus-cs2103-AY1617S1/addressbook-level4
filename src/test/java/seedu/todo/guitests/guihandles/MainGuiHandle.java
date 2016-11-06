package seedu.todo.guitests.guihandles;

import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.guitests.GuiRobot;

// @@author A0139812A
public class MainGuiHandle extends GuiHandle {
    
    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public ConsoleHandle getConsole() {
        return new ConsoleHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public AliasViewHandle getAliasView() {
        return new AliasViewHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public ConfigViewHandle getConfigView() {
        return new ConfigViewHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public HelpViewHandle getHelpView() {
        return new HelpViewHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public TaskListHandle getTaskList() {
        return new TaskListHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
    public TagListHandle getTagList() {
        return new TagListHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }
    
}
