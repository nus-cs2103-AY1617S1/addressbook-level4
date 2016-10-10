package seedu.todo.ui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Display rich textual feedback to command input via this view with {@link #displayMessage(String)}.
 */
public class CommandFeedbackView extends UiPart {

    private static final String FXML = "CommandFeedbackView.fxml";
    
    private TextFlow commandFeedbackTextFlow;
    
    public void displayMessage(String message) {
        Text text = new Text(message);
        commandFeedbackTextFlow.getChildren().add(text);
    }

    public static CommandFeedbackView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandFeedbackView feedbackView = UiPartLoader.loadUiPart(primaryStage, placeHolder, new CommandFeedbackView());
        return feedbackView;
    }
    
    @Override
    public void setNode(Node node) {
        this.commandFeedbackTextFlow = (TextFlow) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}