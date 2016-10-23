package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.todo.TestApp;

/**
 * Provides a handle for the main GUI.
 */
public class MainGuiHandle extends GuiHandle {

    //TODO: Where should the TestApp.APP_TITLE be stored?
    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public TodoListViewHandle getTodoListView() {
        return new TodoListViewHandle(guiRobot, primaryStage);
    }

    public CommandInputViewHandle getCommandInputView() {
        return new CommandInputViewHandle(guiRobot, primaryStage);
    }

    @Deprecated
    public PersonListPanelHandle getPersonListPanel() {
        return new PersonListPanelHandle(guiRobot, primaryStage);
    }

    @Deprecated
    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    @Deprecated
    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }

}
