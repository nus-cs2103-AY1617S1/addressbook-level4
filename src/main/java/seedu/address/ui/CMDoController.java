package seedu.address.ui;

import seedu.address.logic.parser.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent;

/*
 * UI display controller for CMDo. 
 * Reads in keyboard inputs and updates other fields in the display accordingly.
 * 
 * @author A0141006B
 * 
 * Status: Work in progress
 */

public class CMDoController {

	//Constants
	
	
	//FXML
	@FXML
	private Label overdue;
	
	@FXML
	private Label today;

	@FXML
	private Label thisWeek;
	
	@FXML
	private Label thisMonth;
	
	@FXML
	private Label someday;
	
	@FXML
	private Label totalTasks;
	
	//Command box to input command
	@FXML
	private TextField commandBox;
	
	//Shows the task list in the main panel
	@FXML
	private VBox taskPanel;
	
	//shows to user the current status of their commands e.g. All tasks listed!
	@FXML
	private Label infoToUser;
	
	@FXML
	public void readKeyboardInput(KeyEvent event) throws Exception {
		String keyboardInput = commandBox.getText();
		runCommand(keyboardInput);
	}

	private void runCommand(String keyboardInput) {
		MainParser.parseCommand(keyboardInput);
	}
	
}
