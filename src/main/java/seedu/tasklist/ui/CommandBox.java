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
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.events.ui.IncorrectCommandAttemptedEvent;
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

	String[] commands = {"add ", "done ", "update ", "delete ", "show ", "find ", "exit "
			, "undo ", "redo ", "help ", "clear ", "setstorage "};
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
				if (keyEvent.getCode() == KeyCode.TAB) {
					autoComplete();
					keyEvent.consume();
				}
			}
		});
	}

	private void getUpLine(){
		if(!upStack.isEmpty()){
			if(downStack.isEmpty()){
				hasTempEnd = true;
			}
			downStack.push(commandTextField.getText());
			currHistLine = upStack.pop();
			commandTextField.setText(currHistLine);
		}
	}

	private void getDownLine(){
		if(!downStack.isEmpty()){
			upStack.push(commandTextField.getText());
			currHistLine = downStack.pop();
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
	private void handleCommandInputChanged() throws IOException, JSONException, ParseException {
		//@@author A0146107M
		if(!downStack.isEmpty()){
			upStack.push(currHistLine);
			while(!downStack.isEmpty()){
				upStack.push(downStack.pop());
			}
			if(hasTempEnd){
				upStack.pop();
				hasTempEnd = false;
			}
		}

		//Take a copy of the command text
		previousCommandTest = commandTextField.getText();

		/* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
		 * in the event handling code {@link #handleIncorrectCommandAttempted}
		 */
		setStyleToIndicateCorrectCommand();
		upStack.push(previousCommandTest);
		//@@author
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
