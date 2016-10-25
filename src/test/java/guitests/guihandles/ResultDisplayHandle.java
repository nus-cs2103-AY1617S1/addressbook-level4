package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.agendum.TestApp;

//@@author A0148031R
/**
 * A handler for the ResultDisplay of the UI
 */
public class ResultDisplayHandle extends GuiHandle {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getText() {
        return getResultDisplay().getText();
    }

    private Label getResultDisplay() {
        return (Label) getNode(RESULT_DISPLAY_ID);
    }
}
