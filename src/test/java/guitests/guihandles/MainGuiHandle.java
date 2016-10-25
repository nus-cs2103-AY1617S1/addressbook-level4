package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TaskListPanelHandle getTaskListPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }
    
    public TaskListPanelHandle getTodayTaskListTabPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }
    
    public TaskListPanelHandle getTomorrowTaskListTabPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }
    
    public TaskListPanelHandle getIn7DaysTaskListTabPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }
    
    public TaskListPanelHandle getIn30DaysTaskListTabPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }
    
    public TaskListPanelHandle getSomedayTaskListTabPanel() {
        return new TaskListPanelHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }
    
    

}
