package tars.ui;

import com.google.common.eventbus.Subscribe;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tars.commons.core.LogsCenter;
import tars.commons.events.ui.IncorrectCommandAttemptedEvent;
import tars.commons.util.FxViewUtil;
import tars.logic.Logic;
import tars.logic.commands.CommandResult;

import java.util.Stack;
import java.util.logging.Logger;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private final Stack<String> prevCmdTextHistStack = new Stack<String>();
    private final Stack<String> nextCmdTextHistStack = new Stack<String>();

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;

    private Logic logic;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder, ResultDisplay resultDisplay,
            Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic);
        commandBox.addToPlaceholder();
        commandBox.setTextFieldKeyPressedHandler();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        commandTextField.requestFocus();
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    private void setTextFieldKeyPressedHandler() {
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            // To allow users to cycle through the command text history
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.UP)) {
                    setTextToShowPrevCmdText(ke);
                } else if (ke.getCode().equals(KeyCode.DOWN)) {
                    setTextToShowNextCmdText(ke);
                }

            }
        });
    }

    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    @FXML
    private void handleCommandInputChanged() {
        // Take a copy of the command text
        previousCommandTest = commandTextField.getText();
        addCmdTextToPrevStack(previousCommandTest);
        /*
         * We assume the command is correct. If it is incorrect, the command box
         * will be changed accordingly in the event handling code {@link
         * #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    /**
     * Adds the user input command text into the "prev" stack
     * 
     * @@A0124333U
     */
    private void addCmdTextToPrevStack(String cmdText) {
        if (!prevCmdTextHistStack.contains(cmdText)) {
            prevCmdTextHistStack.push(cmdText);
        }
    }

    /**
     * Adds the user input command text into the "next" stack
     */
    private void addCmdTextToNextStack(String cmdText) {
        if (!nextCmdTextHistStack.contains(cmdText)) {
            nextCmdTextHistStack.push(cmdText);
        }
    }

    /**
     * Shows the prev cmdtext in the CommandBox
     * Does nothing if "prev" stack is empty
     */
    private void setTextToShowPrevCmdText(KeyEvent ke) {
        if (!prevCmdTextHistStack.isEmpty()) {
            String cmdTextToShow = prevCmdTextHistStack.pop();
            addCmdTextToNextStack(cmdTextToShow);
            ke.consume();
            commandTextField.setText(cmdTextToShow);
        }
    }
    
    /**
     * Shows the prev cmdtext in the CommandBox
     * Does nothing if "prev" stack is empty
     */
    private void setTextToShowNextCmdText(KeyEvent ke) {
        if (!nextCmdTextHistStack.isEmpty()) {
            String cmdTextToShow = nextCmdTextHistStack.pop();
            addCmdTextToPrevStack(cmdTextToShow);
            ke.consume();
            commandTextField.setText(cmdTextToShow);
        }
    }

    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Invalid command: " + previousCommandTest));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
    }

    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(previousCommandTest);
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }

}
