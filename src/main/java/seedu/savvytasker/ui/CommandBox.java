//@@author A0138431L
package seedu.savvytasker.ui;

import java.io.File;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.events.ui.IncorrectCommandAttemptedEvent;
import seedu.savvytasker.commons.util.FxViewUtil;
import seedu.savvytasker.logic.Logic;
import seedu.savvytasker.logic.commands.CommandResult;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;
    
    // stack to store commands history
 	private static Stack<String> COMMAND_HISTORY_STACK = new Stack<String>();
 	private static Stack<String> COMMAND_FUTURE_STACK = new Stack<String>();

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
        
        COMMAND_HISTORY_STACK.push(previousCommandTest);
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
        commandTextField.setText("");
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }

	public Node getCommandTextField() {
		return commandTextField;
	}
	
	//==================== Keyboard Shortcuts Code =================================================================
    /**
	 * Key pressed handler for text box.
	 * 
	 * @param keyEvent key event for the button that is being pressed.
	 */
	public void commandTextFieldOnKeyPressedHandler(KeyEvent keyEvent) {
		
		String userInput = commandTextField.getText().trim();
		
		KeyCombination saveKey = new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN);
		KeyCombination undoKey = new KeyCodeCombination(KeyCode.Z, KeyCombination.META_DOWN);
		KeyCombination redoKey = new KeyCodeCombination(KeyCode.Y, KeyCombination.META_DOWN);
		KeyCombination helpKey = new KeyCodeCombination(KeyCode.H, KeyCombination.META_DOWN);
		KeyCombination exitKey = new KeyCodeCombination(KeyCode.Q, KeyCombination.META_DOWN);
		KeyCombination listKey = new KeyCodeCombination(KeyCode.L, KeyCombination.META_DOWN);
		KeyCombination listArchivedKey = new KeyCodeCombination(KeyCode.A, KeyCombination.META_DOWN);
		KeyCombination clearKey = new KeyCodeCombination(KeyCode.D, KeyCombination.META_DOWN);
		
		try {

			KeyCode keyCode = keyEvent.getCode();

			if(saveKey.match(keyEvent)) {
		        
				processSave();
		        
			}else if (keyCode == KeyCode.ESCAPE) {
				
				//close help dialog 
				//processEsc();
				
			}else if (keyCode == KeyCode.UP) {

				processUp(userInput);
				
			} else if (keyCode == KeyCode.DOWN) {
				
				processDown(userInput);
				
			} else if (undoKey.match(keyEvent)) {
					
				executeCommand("undo");
					
			} else if (redoKey.match(keyEvent)) {
				
				executeCommand("redo");
				
			} else if (helpKey.match(keyEvent)) {
				
				executeCommand("help");
				
			} else if (exitKey.match(keyEvent)) {
				
				executeCommand("exit");
				
			} else if (listKey.match(keyEvent)) {
				
				executeCommand("list");
				
			} else if (listArchivedKey.match(keyEvent)) {
				
				executeCommand("list t/Archived");
				
			} else if (clearKey.match(keyEvent)) {
				
				executeCommand("clear");
				
			}
			
		} catch (IllegalArgumentException e) {

			commandTextField.setText("");

	        COMMAND_HISTORY_STACK.add(userInput);
			
			this.logger.info("Illegal Argument has been entered.");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			commandTextField.setText("");
			
			this.logger.info("Illegal Argument has been entered.");
			
		}
		
	}
	
	/**
	 * Process the event that occurs after the user presses the ctrl-S button.
	 * 
	 * @param userInput the command keyed in by the user.
	 */
	public void processSave() {
		
		//Execute the save command
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedFile = directoryChooser.showDialog(primaryStage);
		String filepath = selectedFile.getAbsolutePath();
		executeCommand("save " + filepath);
	    
	}
	
	/**
	 * Process the event that occurs after the user presses the [ESC] button.
	 * 
	 * @param userInput the command keyed in by the user.
	 
	public void processEsc() {
			
		if (userInput.equals("") && isHelpViewVisible()) {
			
			hideHelpView();
		
	}
	
	/**
	 * Process the event that occurs after the user presses the [UP] button.
	 * 
	 * @param userInput the command keyed in by the user.
	 */
	public void processUp(String userInput) {

		if (!COMMAND_HISTORY_STACK.isEmpty()) {
			
			String previousCommand = COMMAND_HISTORY_STACK.pop();
			
			if (!userInput.equals("")) {
				
				COMMAND_FUTURE_STACK.push(userInput);
				
			}
			
			commandTextField.setText(previousCommand);
			
		}
		
		commandTextField.positionCaret(commandTextField.getText().length());
		
	}
	
	/**
	 * Process the event that occurs after the user presses the down button.
	 * 
	 * @param userInput the command keyed in by the user.
	 */
	public void processDown(String userInput) {
		
		if (!COMMAND_FUTURE_STACK.isEmpty()) {
			
			String nextCommand = COMMAND_FUTURE_STACK.pop();
			
			COMMAND_HISTORY_STACK.push(userInput);
			commandTextField.setText(nextCommand);
			
		} else if (!userInput.equals("")) {
			
			COMMAND_HISTORY_STACK.push(userInput);
			commandTextField.setText("");
			
		}
		
		commandTextField.positionCaret(commandTextField.getText().length());
		
	}
	
	/**
	 * Execute commands
	 * 
	 * @param command to be executed
	 */
	public void executeCommand(String commandInput) {
		CommandResult commandResult = logic.execute(commandInput);	
		resultDisplay.postMessage(commandResult.feedbackToUser);
		logger.info("Result: " + commandResult.feedbackToUser);
	}
}

