package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;

public class CommandBox extends UiPart<Pane> {

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    private static final String FXML = "/view/CommandBox.fxml";

    private final ResultDisplay resultDisplay;
    String previousCommandTest;

    private final Logic logic;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public CommandBox(ResultDisplay resultDisplay, Logic logic) {
        super(FXML);
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged() {
        //Take a copy of the command text
        previousCommandTest = commandTextField.getText();

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
    private void handleIncorrectCommandAttempted(IncorrectCommandAttemptedEvent event) {
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
