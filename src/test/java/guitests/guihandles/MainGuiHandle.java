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
    
    public TodayTaskListTabPanelHandle getTodayTaskListTabPanel() {
        return new TodayTaskListTabPanelHandle(guiRobot, primaryStage);
    }
    
    public TomorrowTaskListTabPanelHandle getTomorrowTaskListTabPanel() {
        return new TomorrowTaskListTabPanelHandle(guiRobot, primaryStage);
    }
    
    public In7DaysTaskListTabPanelHandle getIn7DaysTaskListTabPanel() {
        return new In7DaysTaskListTabPanelHandle(guiRobot, primaryStage);
    }
    
    public In30DaysTaskListTabPanelHandle getIn30DaysTaskListTabPanel() {
        return new In30DaysTaskListTabPanelHandle(guiRobot, primaryStage);
    }
    
    public SomedayTaskListTabPanelHandle getSomedayTaskListTabPanel() {
        return new SomedayTaskListTabPanelHandle(guiRobot, primaryStage);
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
