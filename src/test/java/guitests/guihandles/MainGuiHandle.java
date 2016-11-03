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

    public ListPanelHandle getListPanel() {
        return new ListPanelHandle(guiRobot, primaryStage);
    }
    
    public ListPanelHandle getTodayTaskListTabPanel() {
        return new ListPanelHandle(guiRobot, primaryStage);
    }
    
    public ListPanelHandle getTomorrowTaskListTabPanel() {
        return new ListPanelHandle(guiRobot, primaryStage);
    }
    
    public ListPanelHandle getIn7DaysTaskListTabPanel() {
        return new ListPanelHandle(guiRobot, primaryStage);
    }
    
    public ListPanelHandle getIn30DaysTaskListTabPanel() {
        return new ListPanelHandle(guiRobot, primaryStage);
    }
    
    public ListPanelHandle getSomedayTaskListTabPanel() {
        return new ListPanelHandle(guiRobot, primaryStage);
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
