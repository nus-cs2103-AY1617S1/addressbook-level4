package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.todo.TestApp;
import seedu.todo.testutil.UiTestUtil;
import seedu.todo.ui.view.CommandFeedbackView;

//@@author A0135805H
/**
 * A handler for retrieving feedback to user via {@link CommandFeedbackView}
 */
public class CommandFeedbackViewHandle extends GuiHandle {
    /* Constants */
    public static final String FEEDBACK_VIEW_LABEL_ID = "#commandFeedbackLabel";

    /**
     * Constructs a handle to the {@link CommandFeedbackViewHandle}
     *
     * @param guiRobot {@link GuiRobot} for the current GUI test.
     * @param primaryStage The stage where the views for this handle is located.
     */
    public CommandFeedbackViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Get the feedback {@link Label} object.
     */
    private Label getFeedbackLabel() {
        return (Label) getNode(FEEDBACK_VIEW_LABEL_ID);
    }

    /**
     * Get the text that is displayed on this {@link #getFeedbackLabel()} object.
     */
    public String getText() {
        return getFeedbackLabel().getText();
    }

    /**
     * Returns true if the {@code feedbackMessage} matches the displayed message in the view.
     */
    public boolean doesFeedbackMessageMatch(String feedbackMessage) {
        return this.getText().equals(feedbackMessage);
    }

    /**
     * Returns true if this feedback view has error style applied.
     */
    public boolean isErrorStyleApplied() {
        return UiTestUtil.containsStyleClass(getFeedbackLabel(), "error");
    }
}
