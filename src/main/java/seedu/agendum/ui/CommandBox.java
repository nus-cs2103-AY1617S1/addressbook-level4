package seedu.agendum.ui;

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
import seedu.agendum.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.agendum.logic.Logic;
import seedu.agendum.logic.commands.*;
import seedu.agendum.commons.util.FxViewUtil;
import seedu.agendum.commons.core.LogsCenter;

import java.util.logging.Logger;

//@@author A0148031R
public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultPopUp resultPopUp;
    private static CommandBoxHistory commandBoxHistory;

    private Logic logic;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultPopUp resultPopUp, Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultPopUp, logic);
        commandBox.addToPlaceholder();
        commandBoxHistory = new CommandBoxHistory();
        return commandBox;
    }

    public void configure(ResultPopUp resultPopUp, Logic logic) {
        this.resultPopUp = resultPopUp;
        this.logic = logic;
        registerAsAnEventHandler(this);
        registerKeyEventFilter();
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
        //Take a copy of the command text
        commandBoxHistory.saveNewCommand(commandTextField.getText());
        String previousCommandTest = commandBoxHistory.getLastCommand();

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        System.out.println("command executed!");
        
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        if(!previousCommandTest.toLowerCase().equals("help")) {
            resultPopUp.postMessage(mostRecentResult.feedbackToUser);
        }
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    private void registerKeyEventFilter() {
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.UP)) {
                    String previousCommand = commandBoxHistory.getPreviousCommand();
                    commandTextField.setText(previousCommand);
                } else if (keyCode.equals(KeyCode.DOWN)) {
                    String nextCommand = commandBoxHistory.getNextCommand();
                    commandTextField.setText(nextCommand);
                } else {
                    return;
                }
                commandTextField.end();
                event.consume();
            }
        });  
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
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Invalid command: " + commandBoxHistory.getLastCommand()));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
    }

    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(commandBoxHistory.getLastCommand());
        commandTextField.selectEnd();
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }

}
