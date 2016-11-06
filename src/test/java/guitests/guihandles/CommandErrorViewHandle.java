package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.testutil.UiTestUtil;

//@@author A0135805H
/**
 * A handler for retrieving error messages to user via {@link seedu.todo.ui.view.CommandErrorView}
 */
public class CommandErrorViewHandle extends GuiHandle {
    /* Constants */
    private static final String NON_FIELD_ERROR_GRID_ID = "#nonFieldErrorGrid";
    private static final String FIELD_ERROR_GRID_ID = "#fieldErrorGrid";
    private static final String ERROR_VIEW_PLACEHOLDER_ID = "#commandErrorViewPlaceholder";

    /**
     * Constructs a handle to the {@link CommandFeedbackViewHandle}
     *
     * @param guiRobot {@link GuiRobot} for the current GUI test.
     * @param primaryStage The stage where the views for this handle is located.
     */
    public CommandErrorViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Returns true if the placeholder is visible.
     */
    public boolean isVisible() {
        Node placeholder = getNode(ERROR_VIEW_PLACEHOLDER_ID);
        return UiTestUtil.isDisplayed(placeholder);
    }

    /**
     * Returns true if the placeholder has messages displayed.
     */
    public boolean hasErrorMessages() {
        int numOfErrorMessages = 0;

        GridPane errorField = (GridPane) getNode(FIELD_ERROR_GRID_ID);
        GridPane nonErrorField = (GridPane) getNode(NON_FIELD_ERROR_GRID_ID);

        numOfErrorMessages += errorField.getChildren().size() / 2;
        numOfErrorMessages += nonErrorField.getChildren().size() / 2;

        return numOfErrorMessages > 0;
    }

    /**
     * Returns true if there are error messages displayed on the user's screen.
     */
    public boolean isErrorMessagesDisplayed() {
        return isVisible() && hasErrorMessages();
    }
}
