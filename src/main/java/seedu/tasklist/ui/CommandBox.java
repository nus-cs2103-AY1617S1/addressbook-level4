package seedu.tasklist.ui;

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
import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.tasklist.commons.events.ui.TaskListScrollEvent;
import seedu.tasklist.commons.util.FxViewUtil;
import seedu.tasklist.logic.Logic;
import seedu.tasklist.logic.commands.*;

import java.io.IOException;
import java.util.Stack;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class CommandBox extends UiPart {
	private final Logger logger = LogsCenter.getLogger(CommandBox.class);
	private static final String FXML = "CommandBox.fxml";

	private AnchorPane placeHolderPane;
	private AnchorPane commandPane;
	private ResultDisplay resultDisplay;
	String previousCommandTest;
	//@@author A0146107M
	private Stack<String> upStack;
	private Stack<String> downStack;
	private String currHistLine;
	private boolean hasTempEnd;

	String[] commands = {"add", "done", "update", "delete", "show", "find", "exit"
			, "undo", "redo", "help", "clear", "setstorage"};
	//@@author

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
	
	//@@author A0146107M
	public void configure(ResultDisplay resultDisplay, Logic logic) {
		this.resultDisplay = resultDisplay;
		this.logic = logic;
		configureKeyEvents();
		upStack = new Stack<String>();
		downStack = new Stack<String>();
		commandTextField.requestFocus();
		registerAsAnEventHandler(this);
	}

    /**
     * Configures events for different keypresses
     *
     */
	private void configureKeyEvents(){
		commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				switch(keyEvent.getCode()){
				case UP:
					handleButtonUp(keyEvent.isControlDown());
					keyEvent.consume();
					break;
				case DOWN:
					handleButtonDown(keyEvent.isControlDown());
					keyEvent.consume();
					break;
				case SPACE: case TAB:
					autoComplete();
					keyEvent.consume();
					break;
				default:
					break;
				}
			}
		});
	}
	
    /**
     * Handler for button up keypress
     * 
     * @param isControlDown Boolean describing state of Control key
     */
	private void handleButtonUp(boolean isControlDown){
		if(isControlDown){
			EventsCenter.getInstance().post(new TaskListScrollEvent(TaskListPanel.Direction.UP));
		}
		else{
			getUpLine();
		}
	}

    /**
     * Handler for button down keypress
     * 
     * @param isControlDown Boolean describing state of Control key
     */
	private void handleButtonDown(boolean isControlDown){
		if(isControlDown){
			EventsCenter.getInstance().post(new TaskListScrollEvent(TaskListPanel.Direction.DOWN));
		}
		else{
			getDownLine();
		}
	}
	
    /**
     * Display the previously entered command
     * 
     */
	private void getUpLine(){
		//Check if older commands exist
		if(!upStack.isEmpty()){
			//check if temporarily entered command has to be stored
			if(downStack.isEmpty()){
				hasTempEnd = true;
			}
			//temporarily store entered command
			downStack.push(commandTextField.getText());
			//display previous command
			currHistLine = upStack.pop();
			commandTextField.setText(currHistLine);
			commandTextField.end();
		}
	}

    /**
     * Display the next entered command
     * 
     */
	private void getDownLine(){
		//check if newer commands exist
		if(!downStack.isEmpty()){
			//store currently displayed command
			upStack.push(commandTextField.getText());
			//display next command
			currHistLine = downStack.pop();
			commandTextField.setText(currHistLine);
			commandTextField.end();
		}
	}

    /**
     * Autocomplete the current commandBox input
     * 
     */
	private void autoComplete(){
		String currentString = commandTextField.getText();
		String completedCommand = commandTextField.getText();
		boolean found = false;
		for (String command: commands){
			if (command.startsWith(currentString)){
				if(found){ //more than 1 match
					return;
				}
				else{ //no matches yet
					completedCommand = command;
					found = true;
				}
			}
		}
		if(!found){ //no matches found
			return;
		}
		else{ //replace text with command
			commandTextField.setText(completedCommand);
			commandTextField.end();
		}
	}
	//@@author
	
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

	//@@author A0146107M
	/**
	 * Prepare the upStack and downStack for addition of a new command
	 * 
	 */
	private void prepareStacksForAddition(){
		if(!downStack.isEmpty()){
			//store current line
			upStack.push(currHistLine);
			//push downStack back into upStack
			while(!downStack.isEmpty()){
				upStack.push(downStack.pop());
			}
			//if half-entered command is temporarily stored
			if(hasTempEnd){
				//clear half-entered command
				upStack.pop();
				hasTempEnd = false;
			}
		}
	}
	//@@author

	@FXML
	private void handleCommandInputChanged() throws IOException, JSONException, ParseException {
		prepareStacksForAddition();
		
		//Take a copy of the command text
		previousCommandTest = commandTextField.getText();

		/* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
		 * in the event handling code {@link #handleIncorrectCommandAttempted}
		 */
		setStyleToIndicateCorrectCommand();
		upStack.push(previousCommandTest);

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
