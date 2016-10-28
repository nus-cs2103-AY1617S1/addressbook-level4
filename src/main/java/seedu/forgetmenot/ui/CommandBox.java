package seedu.forgetmenot.ui;

import java.util.logging.Logger;

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
import seedu.forgetmenot.commons.core.LogsCenter;
import seedu.forgetmenot.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.forgetmenot.commons.util.FxViewUtil;
import seedu.forgetmenot.logic.Logic;
import seedu.forgetmenot.logic.commands.CommandResult;

import java.util.Stack;
import java.util.logging.Logger;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;
    
    private Stack<String> upKeyStack;
	private Stack<String> downKeyStack;
	private String currHistLine;
	private boolean hasTempEnd;

	String[] commands = {"add", "done", "edit", "select", "delete", "show", "find", "exit"
			, "undo", "redo", "help", "clear", "setstorage"};

    private Logic logic;

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
        configureKeyEvents();
        upKeyStack = new Stack<String>();
        downKeyStack = new Stack<String>();
        registerAsAnEventHandler(this);
    }
    
    private void configureKeyEvents(){
		commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.UP) {
					getUpLine();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DOWN) {
					getDownLine();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.TAB || keyEvent.getCode() == KeyCode.SPACE) {
					autoComplete();
					keyEvent.consume();
				}
			}
		});
	}
    
    private void getUpLine(){
		if(!upKeyStack.isEmpty()){
			if(downKeyStack.isEmpty()){
				hasTempEnd = true;
			}
			downKeyStack.push(commandTextField.getText());
			currHistLine = upKeyStack.pop();
			commandTextField.setText(currHistLine);
		}
	}

	private void getDownLine(){
		if(!downKeyStack.isEmpty()){
			upKeyStack.push(commandTextField.getText());
			currHistLine = downKeyStack.pop();
			commandTextField.setText(currHistLine);
		}
	}

	private void autoComplete(){
		String currentString = commandTextField.getText();
		String completedCommand = "";
		boolean found = false;
		for (String command: commands){
			if (command.startsWith(currentString)){
				if(found){
					return;
				}
				else{
					completedCommand = command;
					found = true;
				}
			}
		}
		commandTextField.setText(completedCommand);
		commandTextField.end();
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
    	if(!downKeyStack.isEmpty()){
			upKeyStack.push(currHistLine);
			while(!downKeyStack.isEmpty()){
				upKeyStack.push(downKeyStack.pop());
			}
			if(hasTempEnd){
				upKeyStack.pop();
				hasTempEnd = false;
			}
		}

		//Take a copy of the command text
		previousCommandTest = commandTextField.getText();

		/* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
		 * in the event handling code {@link #handleIncorrectCommandAttempted}
		 */
		setStyleToIndicateCorrectCommand();
		upKeyStack.push(previousCommandTest);
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
