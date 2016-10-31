package seedu.jimi.ui;

import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.jimi.commons.util.CommandUtil;
import seedu.jimi.commons.util.FxViewUtil;
import seedu.jimi.commons.util.StringUtil;
import seedu.jimi.logic.Logic;
import seedu.jimi.logic.commands.CommandResult;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;

    private Logic logic;

    private Stack<String> previousInputs = new Stack<String>();
    private Stack<String> aheadInputs = new Stack<String>();
    
    public static final String MESSAGE_COMMAND_SUGGESTIONS = 
            "Are you looking for these commands? \n" 
            + "> %1$s";
    
    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultDisplay resultDisplay, Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic);
        commandBox.addToPlaceholder();
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
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
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
        // Do nothing for empty input.
        if (commandTextField.getText().trim().isEmpty()) {
            return; 
        }
        
        //Take a copy of the command text
        previousCommandTest = commandTextField.getText().trim();
        resetTextInputStacks();
        previousInputs.push(previousCommandTest);

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        setTextFieldAndCommandBoxToDefault();
        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    // @@author A0140133B
    @FXML
    private void handleTextFieldKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
        case UP :
            cyclePreviousInput();
            return;
        case DOWN :
            cycleAheadInput();
            return;
        case ENTER :
            return; // Do nothing since {@link #handleCommandInputChanged} already handles this. 
        default :
            handleTextFieldKeyTyped();
        }
    }
    
    /** Handles the event when a key is typed in {@code commandTextField}. */
    private void handleTextFieldKeyTyped() {
        String currentText = commandTextField.getText().trim();
        
        logger.info("Text in text field: " + currentText);
        
        if (currentText.isEmpty()) {
            setTextFieldAndCommandBoxToDefault();
        } else {
            postRealTimeSuggestions(currentText);
        }
    }
    
    /** Posts suggestions for commands in real time according to the first word of {@code text} */
    private void postRealTimeSuggestions(String currentText) {
        String firstWordOfInput = StringUtil.getFirstWord(currentText);
        List<String> commandWordMatches = CommandUtil.getInstance().getCommandWordMatches(firstWordOfInput);
        
        logger.info("Suggestions: " + commandWordMatches);
        
        /* 
         * Only providing suggestions for first word, so once the full input text length 
         * exceeds the length of the first word, stop providing suggestions. 
         */
        if (currentText.length() > firstWordOfInput.length() || commandWordMatches.isEmpty()) {
            setResultDisplayToDefault();
        } else {
            resultDisplay.postMessage(String.format(MESSAGE_COMMAND_SUGGESTIONS, String.join(", ", commandWordMatches)));
        }
    }
    
    /** Sets {@code resultDisplay} to its default posting */
    private void setResultDisplayToDefault() {
        // If most recent result does not exist, post welcome message instead.
        resultDisplay.postMessage(
                mostRecentResult == null ? Messages.MESSAGE_WELCOME_JIMI : mostRecentResult.feedbackToUser);
    }
    
    /** Sets {@code commandTextField} and {@code resultDisplay} to their default styles/postings */
    private void setTextFieldAndCommandBoxToDefault() {
        setStyleToDefaultCommandBox();
        setResultDisplayToDefault();
    }
    
    /** Sets {@code commandTextField} with input texts following the most recent input texts. */
    private void cycleAheadInput() {
        if (aheadInputs.isEmpty()) {
            return;
        }
        
        commandTextField.setText(aheadInputs.peek());
        setCaretToRightEnd();
        
        // Last input text does not need to be popped so as to avoid double counting.
        if (aheadInputs.size() > 1) { 
            previousInputs.push(aheadInputs.pop());
        }
    }

    /** Shifts caret to the right end of text field */
    private void setCaretToRightEnd() {
        commandTextField.positionCaret(commandTextField.getText().length());
    }
    
    /** Sets texts field with input texts following the latest input text. */
    private void cyclePreviousInput() {
        if (previousInputs.isEmpty()) {
            return;
        }
        
        commandTextField.setText(previousInputs.peek());
        setCaretToRightEnd();
        
        // Last input text does not need to be popped so as to avoid double counting.
        if (previousInputs.size() > 1) { 
            aheadInputs.push(previousInputs.pop());
        }
    }
    
    /** Pushes all input text in {@code aheadInputs} into {@code previousInputs}. */
    private void resetTextInputStacks() {
        while (!aheadInputs.isEmpty()) {
            previousInputs.push(aheadInputs.pop());
        }
    }
    // @@author

    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToDefaultCommandBox() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Invalid command: " + previousCommandTest));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
        setCaretToRightEnd();
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
