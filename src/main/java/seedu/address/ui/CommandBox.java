package seedu.address.ui;

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
import seedu.address.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.*;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.history.InputHistory;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;

    private Logic logic;
    private InputHistory inputHistory;

    private static final String BACKSPACE_UNICODE = "\u0008";
    private static final String SPACE_UNICODE = "\u0020";
    private static final String CARRIAGE_RETURN = "\r";
    private static final String NEW_LINE = "\n";
    private static final String STRING_EMPTY = "";
    private static final String STRING_ONE_SPACE = " ";


    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder, ResultDisplay resultDisplay,
            Logic logic, InputHistory history) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic, history);
        commandBox.addToPlaceholder();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic, InputHistory history) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        this.inputHistory = history;
        registerAsAnEventHandler(this);
    }

    //@@author A0093960X
    private void addToPlaceholder() {
        setupCommandBoxPosition();
        setupKeyPressHandler();
    }

    /**
     * Sets up the key press event handler for the command box.
     */
    private void setupKeyPressHandler() {
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                case UP :
                    // Fallthrough
                case DOWN :
                    keyEvent.consume();
                    handleUpDownArrow(keyEvent);
                    break;
                default:
                    break;
                }
            }
        });
    }

    /**
     * Sets up the command box position at the placeholder position.
     */
    private void setupCommandBoxPosition() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    //@@author
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

    //@@author A0093960X
    /**
     * Handles the event where the user enters an input in the command box.
     */
    @FXML
    private void handleKeyInput(KeyEvent event) {
        String keyInputAsString = event.getCharacter();

        if (isKeyPressedEnter(keyInputAsString)) {
            return;
        }

        setStyleToIndicateCorrectCommand();
        String userInput = getUserInputAfterKeyPressed(keyInputAsString);
        updateTooltipForUser(userInput);

    }

    /**
     * Returns whether the given keyInputAsString is an 'enter' key on Windows
     * and Unix machines. <br>
     * This is represented by CRLF on windows and LF on unix systems.
     * 
     * @param keyInputAsString The String to check
     * @return A boolean representing if the String is a newline character
     */
    private boolean isKeyPressedEnter(String keyInputAsString) {
        return keyInputAsString.equals(CARRIAGE_RETURN) || keyInputAsString.equals(NEW_LINE);
    }

    /**
     * Handles the up or down arrow key event.
     * @param event
     */
    private void handleUpDownArrow(KeyEvent event) {
        KeyCode key = event.getCode();

        setStyleToIndicateCorrectCommand();
        handleInputHistoryNavigation(key);
        String userInput = commandTextField.getText();
        updateTooltipForUser(userInput);
    }

    /**
     * Get the complete user input taking into account the current key pressed
     * as key pressed event is triggered before the command box text is updated.
     * key is either a backspace, space, letter or digit key.
     * 
     * @param keyAsString the key that was pressed as string
     * @return the full user input taking into account the key pressed
     */
    private String getUserInputAfterKeyPressed(String keyAsString) {
        String userSelectedText = commandTextField.getSelectedText();

        if (!userSelectedText.isEmpty()) {
            commandTextField.replaceSelection(STRING_EMPTY);
        }

        String userInput = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();

        switch (keyAsString) {
        case BACKSPACE_UNICODE :
            // backspace action occurs before event triggers, just return the
            // user input
            return userInput;
        case SPACE_UNICODE :
            return StringUtil.applyStringAtPosition(userInput, STRING_ONE_SPACE, caretPosition);
        default :
            // is a normal letter/digit
            return StringUtil.applyStringAtPosition(userInput, keyAsString, caretPosition);
        }

    }

    /**
     * Updates the tooltip on the GUI for the user to see.
     */
    private void updateTooltipForUser(String userInput) {
        String toDisplay = logic.generateToolTip(userInput);
        resultDisplay.postMessage(toDisplay);
    }

    /**
     * Handles the event where the user is trying to navigate the input history.<br>
     * Asserts that the KeyCode specified is either the UP or DOWN KeyCode.
     * 
     * @param keyCode The KeyCode associated with this event
     */
    private void handleInputHistoryNavigation(KeyCode keyCode) {
        assert (keyCode == KeyCode.UP) || (keyCode == KeyCode.DOWN);

        // if attempt to get next command while at latest command input or prev
        // while at earliest, return
        if (desiredInputHistoryUnavailable(keyCode)) {
            return;
        }

        if (isAttemptingToGetPrevInput(keyCode)) {
            handleGetPreviousInput();
        } else {
            handleGetNextInput();
        }

        updateCaretPosition();
    }

    /**
     * Returns whether the user is trying to access a previous or next input in
     * the input history but is already at the limit (either earliest history or
     * latest history respectively). <br>
     * Asserts that the KeyCode specified is either an UP or DOWN KeyCode.
     * 
     * @param keyCode The KeyCode pressed
     * @return The boolean representing the above
     */
    private boolean desiredInputHistoryUnavailable(KeyCode keyCode) {
        assert keyCode == KeyCode.UP || keyCode == KeyCode.DOWN;

        return isAtEarliestHistoryButWantPrevInput(keyCode) || isAtLatestHistoryButWantNextInput(keyCode);
    }

    /**
     * Returns whether the user is already at the latest input history state but
     * wants to access a next input.
     * 
     * @param keyCode The KeyCode pressed
     * @return The boolean representing the above
     */
    private boolean isAtLatestHistoryButWantNextInput(KeyCode keyCode) {
        return inputHistory.isLatestInput() && isAttemptingToGetNextInput(keyCode);
    }

    /**
     * Returns whether the user is already at the earliest input history state
     * but wants to access a previous input.
     * 
     * @param keyCode the KeyCode pressed
     * @return boolean representing the above
     */
    private boolean isAtEarliestHistoryButWantPrevInput(KeyCode keyCode) {
        return inputHistory.isEarliestInput() && isAttemptingToGetPrevInput(keyCode);
    }

    /**
     * Updates the caret position to the end of the current text input in the
     * command box.
     */
    private void updateCaretPosition() {
        String currentInputShown = commandTextField.getText();
        int positionAtEndOfString = currentInputShown.length();

        commandTextField.positionCaret(positionAtEndOfString);
    }

    /**
     * Handles the event where the user wants to get the next input from input
     * history.
     */
    private void handleGetNextInput() {
        // store the current input into the prev first
        String nextInput = inputHistory.nextStep();

        // get a next command input and replace current input
        commandTextField.setText(nextInput);
    }

    /**
     * Handles the event where the user wants to get the previous input from
     * input history.
     */
    private void handleGetPreviousInput() {
        // store the current input and get prev input
        String currentInput = commandTextField.getText();
        String prevInput = inputHistory.prevStep(currentInput);

        // show user the prev input
        commandTextField.setText(prevInput);
    }

    /**
     * Returns whether the user wants to get the previous input from input
     * history.
     * 
     * @param keyCode the key the user pressed to trigger the event
     * @return boolean representing the above
     */
    private boolean isAttemptingToGetPrevInput(KeyCode keyCode) {
        return keyCode == KeyCode.UP;
    }

    /**
     * Returns whether the user wants to get the next input from input history.
     * 
     * @param keyCode the key the user pressed to trigger the event
     * @return boolean representing the above
     */
    private boolean isAttemptingToGetNextInput(KeyCode keyCode) {
        return keyCode == KeyCode.DOWN;
    }

    @FXML
    private void handleCommandInputEntered() {
        // Take a copy of the command text
        previousCommandTest = commandTextField.getText();

        // first push back all 'next' commands into 'prev' command
        // immediately add it to the history of command inputs
        inputHistory.updateInputHistory(previousCommandTest);

        /*
         * We assume the command is correct. If it is incorrect, the command box
         * will be changed accordingly in the event handling code {@link
         * #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        commandTextField.setText(STRING_EMPTY);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    //@@author
    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Invalid command: " + previousCommandTest));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
    }

    //@@author
    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(previousCommandTest);
    }

    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }

}
