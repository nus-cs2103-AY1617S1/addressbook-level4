package seedu.todo.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.util.logging.Logger;

//@@author A0135805H
/**
 * Display textual feedback to command input via this view with {@link #displayMessage(String)}.
 */
public class CommandFeedbackView extends UiPart {
    /* Constants */
    private static final String FXML = "CommandFeedbackView.fxml";

    /* Variables */
    private final Logger logger = LogsCenter.getLogger(CommandFeedbackView.class);

    /* Layout Elements */
    @FXML private Label commandFeedbackLabel;
    private AnchorPane textContainer;

    /**
     * Loads and initialise the feedback view element to the placeholder.
     *
     * @param primaryStage The main stage of the application.
     * @param placeholder The place where the view element {@link #textContainer} should be placed.
     * @return An instance of this class.
     */
    public static CommandFeedbackView load(Stage primaryStage, AnchorPane placeholder) {
        CommandFeedbackView feedbackView = UiPartLoaderUtil.loadUiPart(primaryStage, placeholder, new CommandFeedbackView());
        feedbackView.configureLayout();
        return feedbackView;
    }

    /**
     * Configure the UI layout of {@link CommandFeedbackView}.
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(textContainer, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandFeedbackLabel, 0.0, 0.0, 0.0, 0.0);
    }

    /* Interfacing Methods */
    /**
     * Displays a message onto the {@link #commandFeedbackLabel}.
     * @param message The feedback message to be shown to the user.
     */
    public void displayMessage(String message) {
        commandFeedbackLabel.setText(message);
    }

    /**
     * Clears any message in {@link #commandFeedbackLabel}.
     */
    public void clearMessage() {
        commandFeedbackLabel.setText("");
    }

    /**
     * Indicate an error visually on the {@link #commandFeedbackLabel}.
     */
    public void flagError() {
        ViewStyleUtil.addClassStyles(commandFeedbackLabel, ViewStyleUtil.STYLE_ERROR);
    }

    /**
     * Remove the error flag visually on the {@link #commandFeedbackLabel}.
     */
    public void unFlagError() {
        ViewStyleUtil.removeClassStyles(commandFeedbackLabel, ViewStyleUtil.STYLE_ERROR);
    }

    /* Override Methods */
    @Override
    public void setNode(Node node) {
        this.textContainer = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}