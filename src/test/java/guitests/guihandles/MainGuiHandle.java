package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.agendum.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";
    
    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public DoItSoonPanelHandle getDoItSoonPanel() {
        return new DoItSoonPanelHandle(guiRobot, primaryStage);
    }

    public DoItAnytimePanelHandle getDoItAnytimePanel() {
        return new DoItAnytimePanelHandle(guiRobot, primaryStage);
    }

    public CompletedTasksPanelHandle getCompletedTasksPanel() {
        return new CompletedTasksPanelHandle(guiRobot, primaryStage);
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
    
    public boolean isWindowClose() {
        return getNode(HELP_WINDOW_ROOT_FIELD_ID) != null;
    }

}
