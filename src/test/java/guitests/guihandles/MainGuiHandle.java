package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.agendum.TestApp;

//@@author A0148031R
/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public UpcomingTasksHandle getDoItSoonPanel() {
        return new UpcomingTasksHandle(guiRobot, primaryStage);
    }

    public FloatingTasksPanelHandle getDoItAnytimePanel() {
        return new FloatingTasksPanelHandle(guiRobot, primaryStage);
    }

    public CompletedTasksPanelHandle getCompletedTasksPanel() {
        return new CompletedTasksPanelHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }
    
    public MessageDisplayHandle getMessageDisplay() {
        return new MessageDisplayHandle(guiRobot, primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }
}
