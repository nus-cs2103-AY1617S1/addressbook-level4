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
import tars.commons.core.KeyCombinations;
import tars.commons.core.LogsCenter;
import tars.commons.events.ui.CommandBoxTextFieldValueChangedEvent;
import tars.commons.events.ui.IncorrectCommandAttemptedEvent;
import tars.commons.events.ui.KeyCombinationPressedEvent;
import tars.commons.util.FxViewUtil;
import tars.logic.Logic;
import tars.logic.commands.CommandResult;
import tars.logic.commands.ConfirmCommand;
import tars.logic.commands.RedoCommand;
import tars.logic.commands.RsvCommand;
import tars.logic.commands.UndoCommand;

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
        commandBox.setTextFieldValueHandler();
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
    
    // @@author A0124333U
    private void setTextFieldKeyPressedHandler() {
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.UP)) {
                    setTextToShowPrevCmdText(ke);
                } else if (ke.getCode().equals(KeyCode.DOWN)) {
                    setTextToShowNextCmdText(ke);
                } else if (KeyCombinations.KEY_COMB_CTRL_RIGHT_ARROW.match(ke)) {
                    raise(new KeyCombinationPressedEvent(
                            KeyCombinations.KEY_COMB_CTRL_RIGHT_ARROW));
                    ke.consume();
                } else if (KeyCombinations.KEY_COMB_CTRL_LEFT_ARROW.match(ke)) {
                    raise(new KeyCombinationPressedEvent(KeyCombinations.KEY_COMB_CTRL_LEFT_ARROW));
                    ke.consume();
                } else if (KeyCombinations.KEY_COMB_CTRL_Z.match(ke)) {
                    handleUndoAndRedoKeyRequest(UndoCommand.COMMAND_WORD);
                } else if (KeyCombinations.KEY_COMB_CTRL_Y.match(ke)) {
                    handleUndoAndRedoKeyRequest(RedoCommand.COMMAND_WORD);
                }
            }
        });
    }
    
    private void setTextFieldValueHandler() {
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(RsvCommand.COMMAND_WORD) || newValue.equals(ConfirmCommand.COMMAND_WORD)) {
                raise(new CommandBoxTextFieldValueChangedEvent(newValue));
            }
        });
        
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
     * Handle any undo and redo request
     * 
     * @@author A0139924W
     */
    private void handleUndoAndRedoKeyRequest(String commandWord) {
        if (UndoCommand.COMMAND_WORD.equals(commandWord)) {
            mostRecentResult = logic.execute(UndoCommand.COMMAND_WORD);
        } else if (RedoCommand.COMMAND_WORD.equals(commandWord)) {
            mostRecentResult = logic.execute(RedoCommand.COMMAND_WORD);
        }
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
     * Shows the prev cmdtext in the CommandBox. Does nothing if "prev" stack is
     * empty
     */
    private void setTextToShowPrevCmdText(KeyEvent ke) {
        if (!prevCmdTextHistStack.isEmpty()) {
            if (nextCmdTextHistStack.isEmpty()) {
                nextCmdTextHistStack.push(commandTextField.getText());
            }
            String cmdTextToShow = prevCmdTextHistStack.pop();
            addCmdTextToNextStack(cmdTextToShow);
            if (commandTextField.getText().equals(cmdTextToShow) && !prevCmdTextHistStack.isEmpty()) {
                cmdTextToShow = prevCmdTextHistStack.pop();
                addCmdTextToNextStack(cmdTextToShow);
            }
            ke.consume();
            commandTextField.setText(cmdTextToShow);
        }
    }

    /**
     * Shows the next cmdtext in the CommandBox. Does nothing if "next" stack is
     * empty
     */
    private void setTextToShowNextCmdText(KeyEvent ke) {
        if (!nextCmdTextHistStack.isEmpty()) {
            String cmdTextToShow = nextCmdTextHistStack.pop();
            addCmdTextToPrevStack(cmdTextToShow);
            if (commandTextField.getText().equals(cmdTextToShow) && !nextCmdTextHistStack.isEmpty()) {
                cmdTextToShow = nextCmdTextHistStack.pop();
                if (!nextCmdTextHistStack.isEmpty()) {
                    addCmdTextToNextStack(cmdTextToShow);
                }
            }
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
