package seedu.menion.ui;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.menion.commons.core.LogsCenter;
import seedu.menion.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.menion.commons.util.FxViewUtil;
import seedu.menion.logic.Logic;
import seedu.menion.logic.commands.*;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

import java.util.logging.Logger;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;
    
    private ActivityListPanel activityListPanel;
    private Logic logic;

    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultDisplay resultDisplay, Logic logic, ActivityListPanel activityListPanel) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic, activityListPanel);
        commandBox.addToPlaceholder();

        return commandBox;
    }

    public void configure(ResultDisplay resultDisplay, Logic logic, ActivityListPanel activityListPanel) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        this.activityListPanel = activityListPanel;
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
        //Take a copy of the command text
        previousCommandTest = commandTextField.getText();

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
        highlightMostRecentlyChangedActivity();
    }
    
    //@@author A139515A
    /**
     * Highlight the most recently changed activity to indicate changes are made to it
     */
    public void highlightMostRecentlyChangedActivity() {
    	ReadOnlyActivity activityToHighlight = logic.getMostRecentUpdatedActivity();
    	if (activityToHighlight != null) {
	    	if (activityToHighlight.getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
	    		activityListPanel.scrollToFloating(activityToHighlight);
	    	}
	    	else if (activityToHighlight.getActivityType().equals(Activity.TASK_TYPE)) {
	    		activityListPanel.scrollToTask(activityToHighlight);
	    	}
	    	else if (activityToHighlight.getActivityType().equals(Activity.EVENT_TYPE)) {
	    		activityListPanel.scrollToEvent(activityToHighlight);
	    	}
    	}
    	else {
    		activityListPanel.clearSelection();
    	}
    }
    //@@author

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
