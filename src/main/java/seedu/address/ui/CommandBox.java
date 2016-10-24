package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
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
    private InputHistory history;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultDisplay resultDisplay, Logic logic, InputHistory history) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic, history);
        commandBox.addToPlaceholder();
        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic, InputHistory history) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        this.history = history;
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
    
    /**
     * Handles the event where user input in the command box changes.
     */
    @FXML
    private void handleCommandInputChanged(KeyEvent event){
        KeyCode key = getKeyCodeFromEvent(event);
        
        boolean isKeyToHandle = checkIfKeyToHandle(key);
        boolean isNotKeyToHandle = !isKeyToHandle;
        
        boolean isNavigatingInputHistory = checkIfNavigatingInputHistory(key);
                
        if (isNotKeyToHandle) {
            return;
        }
               
        if (isNavigatingInputHistory) {
            handleArrowKeyEvent(key);
        }
                       
        updateTooltipForUser();
    }

    private KeyCode getKeyCodeFromEvent(KeyEvent event) {
        return event.getCode();
    }

    /**
     * Returns if the key press corresponds to an up or down arrow key used to navigate input history.
     * @param keyCode the key to check
     * @return boolean representing if the key is an up or down arrow key
     */
    private boolean checkIfNavigatingInputHistory(KeyCode key) {
        return checkIfWantPrevInput(key) || key == KeyCode.DOWN;
    }

    /**
     * Returns if the key press corresponds to a key to be handled by the commandbox controller.
     * @param key the key to check
     * @return boolean representing if the key needs to be handled
     */
    private boolean checkIfKeyToHandle(KeyCode key) {
        return key == KeyCode.BACK_SPACE || key == KeyCode.SPACE || key.isDigitKey() || 
                key.isLetterKey() || checkIfWantPrevInput(key) || key == KeyCode.DOWN;
    }

    /**
     * Updates the tooltip on the GUI for the user to see.
     */
    private void updateTooltipForUser() {
        String toDisplay = logic.generateToolTip(commandTextField.getText());
        resultDisplay.postMessage(toDisplay);
    }

    
    private void handleArrowKeyEvent(KeyCode keyCode) {
        boolean wantPrev = checkIfWantPrevInput(keyCode);
        boolean wantNext = !wantPrev;
        
        // if attempt to get next command while at latest command input or prev while at earliest, return
        if ((history.isLatestInput() && wantNext) || (history.isEarliestInput() && wantPrev)) {
            return;
        }
                
        // handle differently depending on up arrow
        if (wantPrev){
            // store the current input into the next first
            if (history.isLatestInput()) {
                history.pushNextInput(commandTextField.getText());
            }
                
            else {
                history.pushNextInput(history.getStoredCurrentShownInput());
            }
                
            // get a previous command input and replace current input
            commandTextField.setText(history.popPrevInput());
        }
        
        // or down arrow
        else {
            // store the current input into the prev first
            history.pushPrevInput(history.getStoredCurrentShownInput());
            
            // get a next command input and replace current input
            commandTextField.setText(history.popNextInput());
        }
        
        String currentInputShown = commandTextField.getText();
        // positions the caret at the end of the string for easy edit
        commandTextField.positionCaret(currentInputShown.length());
    }

    private boolean checkIfWantPrevInput(KeyCode keyCode) {
        return keyCode == KeyCode.UP;
    }

    @FXML
    private void handleCommandInputEntered() {
        //Take a copy of the command text
        previousCommandTest = commandTextField.getText();
        
        // first push back all 'next' commands into 'prev' command       
        // immediately add it to the history of command inputs
        history.updateInputHistory(previousCommandTest); 

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }


    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event,"Invalid command: " + previousCommandTest));
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
