package harmony.mastermind.ui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.commons.events.ui.IncorrectCommandAttemptedEvent;
import harmony.mastermind.commons.events.ui.NewResultAvailableEvent;
import harmony.mastermind.commons.util.FxViewUtil;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.logic.commands.CommandResult;
import harmony.mastermind.logic.commands.ListCommand;
import harmony.mastermind.logic.commands.UpcomingCommand;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CommandBox extends UiPart {

    private static final String FXML = "CommandBox.fxml";
    
    private static final KeyCombination CTRL_ONE = new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_TWO = new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_THREE = new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_FOUR = new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.CONTROL_DOWN);
    private static final KeyCombination CTRL_FIVE = new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.CONTROL_DOWN);

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    private AnchorPane placeholder;

    private Logic logic;

    private String currCommandText;

    private CommandResult mostRecentResult;

    private Stack<String> commandHistory = new Stack<String>();
    private int commandIndex = 0;

    private AutoCompletionBinding<String> autoCompletionBinding;

    // List of words for autocomplete
    Set listOfWords = new HashSet<>();
    String[] words = { "add", "delete", "edit", "clear", "help", "undo", "mark", "find", "exit", "do", "delete" };
    
    @FXML
    private TextField commandField;
    
    @FXML
    private void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                commandField.requestFocus();
            }
        });
    }

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder, Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(logic);
        return commandBox;
    }

    @Override
    public void setNode(Node node) {
        this.commandField = (TextField) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeholder = placeholder;
    }

    private void configure(Logic logic) {
        this.logic = logic;
        this.addToPlaceholder();
        this.registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        placeholder.getChildren().add(commandField);
        FxViewUtil.applyAnchorBoundaryParameters(commandField, 0, 0, 0, 0);
    }

    @FXML
    private void handleCommandInputChanged() {
        // Take a copy of the command text
        currCommandText = commandField.getText();

        setStyleToIndicateCorrectCommand();
        /*
         * We assume the command is correct. If it is incorrect, the command box
         * will be changed accordingly in the event handling code {@link
         * #handleIncorrectCommandAttempted}
         */
        mostRecentResult = logic.execute(currCommandText);

        // adds current command into the stack
        updateCommandHistory(currCommandText);

        this.raise(new NewResultAvailableEvent(mostRecentResult.title, mostRecentResult.feedbackToUser));

        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }
    
 // @@author A0124797R
    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandField.setText("");
}

    /**
     * Handles any KeyPress in the commandField
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        KeyCode key = event.getCode();
        switch (key) {
            case UP:
                restorePrevCommandText();
                return;
            case DOWN:
                restoreNextCommandText();
                return;
            case ENTER:
                learnWord(commandField.getText());
                return;
        }

        if (CTRL_ONE.match(event)) {
            raise(new NewResultAvailableEvent(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_SUCCESS));
        } else if (CTRL_TWO.match(event)) {
            raise(new NewResultAvailableEvent(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_SUCCESS_TASKS));
        } else if (CTRL_THREE.match(event)) {
            raise(new NewResultAvailableEvent(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_SUCCESS_EVENTS));
        } else if (CTRL_FOUR.match(event)) {
            raise(new NewResultAvailableEvent(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_SUCCESS_DEADLINES));
        } else if (CTRL_FIVE.match(event)) {
            raise(new NewResultAvailableEvent(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_SUCCESS_ARCHIVES));
        }
    }

    // @@author A0143378Y
    @FXML
    private void initAutoComplete() {
        // Autocomplete function
        Collections.addAll(listOfWords, words);
        autoCompletionBinding = TextFields.bindAutoCompletion(commandField, listOfWords);
        autoCompletionBinding.setPrefWidth(500);
        autoCompletionBinding.setVisibleRowCount(5);
        autoCompletionBinding.setHideOnEscape(true);

    }

    // @@author A0143378Y
    // This function takes in whatever the user has "ENTER"-ed, and save in a
    // dictionary of words
    // These words will be in the autocomplete list of words
    private void learnWord(String text) {
        listOfWords.add(text);

        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }

        autoCompletionBinding = TextFields.bindAutoCompletion(commandField, listOfWords);
    }

 // @@author A0124797R
    private void restoreCommandText() {
        commandField.setText(currCommandText);
    }

    private void restorePrevCommandText() {
        String prevCommand = getPrevCommandHistory();
        if (prevCommand != null) {
            // need to wrap in runLater due to concurrency threading in JavaFX
            Platform.runLater(new Runnable() {
                public void run() {
                    commandField.setText(prevCommand);
                    commandField.positionCaret(prevCommand.length());
                }
            });
        } // else ignore
    }

    private void restoreNextCommandText() {
        String nextCommand = getNextCommandHistory();
        // need to wrap in runLater due to concurrency threading in JavaFX
        Platform.runLater(new Runnable() {
            public void run() {
                if (nextCommand != null) {
                    commandField.setText(nextCommand);
                    commandField.positionCaret(nextCommand.length());
                } else {
                    commandField.setText("");
                }
            }
        });
}
    
    /**
     * Adds recent input into stack
     */
    private void updateCommandHistory(String command) {
        commandHistory.push(command);
        commandIndex = commandHistory.size();
    }

    private String getPrevCommandHistory() {
        if (commandHistory.empty()) {
            return null;
        } else if (commandIndex == 0) {
            return null;
        } else {
            commandIndex--;
            return commandHistory.get(commandIndex);
        }
    }

    private String getNextCommandHistory() {
        if (commandHistory.empty()) {
            return null;
        } else if (commandIndex >= commandHistory.size() - 1) {
            commandIndex = commandHistory.size();
            return null;
        } else {
            commandIndex++;
            return commandHistory.get(commandIndex);
        }
    }
    
    @Subscribe
    private void handleIncorrectCommand(IncorrectCommandAttemptedEvent event) {
        restoreCommandText();
    }
}
