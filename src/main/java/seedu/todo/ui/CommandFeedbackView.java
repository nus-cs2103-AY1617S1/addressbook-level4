package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.util.FxViewUtil;

import java.util.logging.Logger;

/**
 * Display rich textual feedback to command input via this view with {@link #displayMessage(String)}.
 */
public class CommandFeedbackView extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandFeedbackView.class);
    private static final String FXML = "CommandFeedbackView.fxml";
    private static final String ERROR_STYLE = "error";

    @FXML
    private Label commandFeedbackLabel;
    private AnchorPane placeHolder, textFlowContainer;

    /**
     * Loads the feedback view element to the placeHolder
     * @param primaryStage of the application
     * @param placeHolder where the view element {@link #textFlowContainer} should be placed
     * @return an instance of this class
     */
    public static CommandFeedbackView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandFeedbackView feedbackView = UiPartLoader.loadUiPart(primaryStage, placeHolder, new CommandFeedbackView());
        feedbackView.configure();
        return feedbackView;
    }

    /**
     * Configure the UI properties of CommandFeedbackView
     */
    private void configure() {
        FxViewUtil.applyAnchorBoundaryParameters(textFlowContainer, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandFeedbackLabel, 0.0, 0.0, 0.0, 0.0);
        this.placeHolder.getChildren().add(textFlowContainer);
    }

    /**
     * Displays a message onto the {@link #commandFeedbackLabel}
     * @param message to be shown
     */
    public void displayMessage(String message) {
        commandFeedbackLabel.setText("Message " + message);
    }

    /**
     * Indicate an error visually on the {@link #commandFeedbackLabel}
     */
    public void flagError() {
        commandFeedbackLabel.getStyleClass().add(ERROR_STYLE);
    }

    /**
     * Remove the error flag visually on the {@link #commandFeedbackLabel}
     */
    public void unFlagError() {
        commandFeedbackLabel.getStyleClass().remove(ERROR_STYLE);
    }

    /* Override Methods */
    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }
    
    @Override
    public void setNode(Node node) {
        this.textFlowContainer = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}