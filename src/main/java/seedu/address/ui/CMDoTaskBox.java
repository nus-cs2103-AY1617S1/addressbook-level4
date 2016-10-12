package seedu.address.ui;

import seedu.address.MainApp;
import seedu.address.model.task.Detail;
import javafx.scene.layout.*;
import javafx.fxml.*;
import javafx.scene.control.Label;

/*
 * CMDo Task Box Controls
 * New Tasks added are passed into CMDoTaskBox() and reflected on the UI
 * 
 * @author A0141006B
 */

public class CMDoTaskBox extends HBox {
	//private static final double ZERO_OPACITY = 0.0;
	private static final String FXML = "CMDoTaskBox.fxml";
	
	//FXML
	@FXML	
	private Label index;
	
	@FXML
	private Label task;
	
	@FXML
	private Label dueDate;
	
	@FXML
	private Label dueTime;
	
	@FXML
	private Label priority;
	
	public CMDoTaskBox(int taskIndex, String taskTitle, String taskDueDate, String taskDueTime,
			String taskPriority) {
		
		loadFXML();
		
		if(taskTitle != null) {
			this.task.setText(taskTitle);
		} else{
			setBlankField(task);
		}
		
		if(taskDueDate != null) {
			this.dueDate.setText(taskDueDate);
		} else{
			setBlankField(dueDate);
		}
		
		if(taskDueTime != null) {
			this.dueTime.setText(taskDueTime);
		} else{
			setBlankField(dueTime);
		}
		
		if(taskPriority != null) {
			this.priority.setText(taskPriority);
		} else{
			setBlankField(priority);
		}
	}
	
	private void loadFXML() {
		try {
			FXMLLoader cmdoLoader = new FXMLLoader(getClass().getResource(FXML));
			cmdoLoader.setRoot(this);
			cmdoLoader.setController(this);
			cmdoLoader.load();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	private void setBlankField(Label label) {
		label.setText("");		
	}
}
