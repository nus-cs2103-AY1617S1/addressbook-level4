package seedu.todo.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.util.logging.Logger;

//@@author A0315805H
/**
 * Display textual feedback to command input via this view with {@link #displayMessage(String)}.
 */
public class CommandFeedbackView extends UiPart {

    private final Logger logger = LogsCenter.getLogger(CommandFeedbackView.class);
    private static final String FXML = "CommandFeedbackView.fxml";

    @FXML
    private Label commandFeedbackLabel;
    private AnchorPane placeHolder;
    private AnchorPane textContainer;

    /**
     * Loads and initialise the feedback view element to the placeHolder
     * @param primaryStage of the application
     * @param placeHolder where the view element {@link #textContainer} should be placed
     * @return an instance of this class
     */
    public static CommandFeedbackView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandFeedbackView feedbackView = UiPartLoaderUtil.loadUiPart(primaryStage, placeHolder, new CommandFeedbackView());
        feedbackView.addToPlaceholder();
        feedbackView.configureLayout();
        return feedbackView;
    }

    /**
     * Adds this view element to external placeholder
     */
    private void addToPlaceholder() {
        this.placeHolder.getChildren().add(textContainer);
    }

    /**
     * Configure the UI layout of {@link CommandFeedbackView}
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(textContainer, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandFeedbackLabel, 0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Displays a message onto the {@link #commandFeedbackLabel}
     * @param message to be shown
     */
    public void displayMessage(String message) {
        commandFeedbackLabel.setText(message);
    }

    /**
     * Clears any message in {@link #commandFeedbackLabel}
     */
    public void clearMessage() {
        commandFeedbackLabel.setText("");
    }

    /**
     * Indicate an error visually on the {@link #commandFeedbackLabel}
     */
    public void flagError() {
        ViewStyleUtil.addClassStyles(commandFeedbackLabel, ViewStyleUtil.STYLE_ERROR);
    }

    /**
     * Remove the error flag visually on the {@link #commandFeedbackLabel}
     */
    public void unFlagError() {
        ViewStyleUtil.removeClassStyles(commandFeedbackLabel, ViewStyleUtil.STYLE_ERROR);
    }

    /* Override Methods */
    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }
    
    @Override
    public void setNode(Node node) {
        this.textContainer = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}