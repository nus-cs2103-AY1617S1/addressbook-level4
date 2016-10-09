package seedu.todo.ui.io;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;

/**
 * Display textual feedback to command input via this view with {@link #displayMessage(String)}.
 */
public class CommandFeedbackView extends UiPart {

    private static final String FXML = "CommandFeedbackView.fxml";
    
    private Label commandFeedbackLabel;
    
    public void displayMessage(String message) {
        commandFeedbackLabel.setText(message);
    }

    public static CommandFeedbackView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandFeedbackView feedbackView = UiPartLoader.loadUiPart(primaryStage, placeHolder, new CommandFeedbackView());
        return feedbackView;
    }
    
    @Override
    public void setNode(Node node) {
        this.commandFeedbackLabel = (Label) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}