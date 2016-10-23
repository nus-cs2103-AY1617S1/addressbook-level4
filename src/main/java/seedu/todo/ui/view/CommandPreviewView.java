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

//@@author A0139021U

/**
 * Display textual feedback to command input via this view with {@link #displayMessage(String)}.
 */
public class CommandPreviewView extends UiPart {
    /* Constants */
    private static final String FXML = "CommandPreviewView.fxml";

    /* Variables */
    private final Logger logger = LogsCenter.getLogger(CommandPreviewView.class);

    /* Layout Elements */
    @FXML private Label commandLivePreviewLabel;
    private AnchorPane textContainer;

    /**
     * Loads and initialise the feedback view element to the placeholder.
     *
     * @param primaryStage The main stage of the application.
     * @param placeholder The place where the view element {@link #textContainer} should be placed.
     * @return An instance of this class.
     */
    public static CommandPreviewView load(Stage primaryStage, AnchorPane placeholder) {
        CommandPreviewView feedbackView = UiPartLoaderUtil.loadUiPart(primaryStage, placeholder, new CommandPreviewView());
        feedbackView.configureLayout();
        return feedbackView;
    }

    /**
     * Configure the UI layout of {@link CommandPreviewView}.
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(textContainer, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandLivePreviewLabel, 0.0, 0.0, 0.0, 0.0);
    }

    /* Interfacing Methods */
    /**
     * Displays a message onto the {@link #commandLivePreviewLabel}.
     * @param message The feedback message to be shown to the user.
     */
    public void displayMessage(String message) {
        commandLivePreviewLabel.setText(message);
    }

    /**
     * Clears any message in {@link #commandLivePreviewLabel}.
     */
    public void clearMessage() {
        commandLivePreviewLabel.setText("");
    }

    /**
     * Indicate an error visually on the {@link #commandLivePreviewLabel}.
     */
    public void flagError() {
        ViewStyleUtil.addClassStyles(commandLivePreviewLabel, ViewStyleUtil.STYLE_ERROR);
    }

    /**
     * Remove the error flag visually on the {@link #commandLivePreviewLabel}.
     */
    public void unFlagError() {
        ViewStyleUtil.removeClassStyles(commandLivePreviewLabel, ViewStyleUtil.STYLE_ERROR);
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