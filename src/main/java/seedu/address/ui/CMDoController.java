package seedu.address.ui;

import seedu.address.logic.parser.*;
import seedu.address.model.task.Task;
import seedu.address.model.task.Detail;
import seedu.address.model.task.Done;
import seedu.address.model.task.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private ListView<HBox> taskPanel;
	
	//shows to user the current status and information of their commands e.g. All tasks listed!
	@FXML
	private Label infoToUser;
	
	@FXML
	public void readKeyboardInput(KeyEvent event) throws Exception {
		String keyboardInput = commandBox.getText();
		runCommand(keyboardInput);
	}

	private void runCommand(String keyboardInput) {
		MainParser parser = MainParser.getInstance();
		parser.parseCommand(keyboardInput);		
	}
	
    public void updateDisplayToUser(String display) {
        infoToUser.setText(display);
    }
    
    public Label getInfoToUser() {
        return infoToUser;
    }
    
    public void updateTasksOverviewPanel(ObservableList<Task> taskObservableList) {
        //Integer tasksInInbox = 0;
    	Integer overdueNumber = 0;
        Integer todayNumber = 0;
        Integer thisWeekNumber = 0;
        Integer thisMonthNumber = 0;
        Integer somedayNumber = 0;
        Integer totalTasksNumber = taskObservableList.size();
        
        //To figure out logic to increment task numbers       	
        
        //Setting 
        today.setText(todayNumber.toString());
        thisWeek.setText(thisWeekNumber.toString());
        thisMonth.setText(thisMonthNumber.toString());
        someday.setText(somedayNumber.toString());
        totalTasks.setText(totalTasksNumber.toString());

    } 
    
    /*
     * Setters
     */
    private void setItemsToTaskPanel(ObservableList<HBox> taskbox ) {
    	taskPanel.setItems(taskbox);
    }
    
    /*
     * Getters for the different task fields
     * Calls the task details from seedu.address.model.task.
     * Task Index needs to be incremented. DueDate and DueTime set to null if null
     */
    private int getTaskIndex(ObservableList<Task> taskObservableList, Task task) {
        return taskObservableList.indexOf(task) + 1;
    }
    
    private String getTaskDueDate(Task task) {
        return task.getFriendlyDate() == null ? null : task.getFriendlyDate();
    }
    
    private String getTaskDueTime(Task task) {
    	return task.getFriendlyTime() == null ? null : task.getFriendlyTime();
    }
    
    
      
    //To update the main panel
    public void updateMainDisplay(ObservableList<Task> taskObservableList) {
        ObservableList<HBox> taskBox = FXCollections.observableArrayList();

        for (Task task : taskObservableList) {
            int taskIndex = getTaskIndex(taskObservableList, task);
            String taskTitle = task.getDetail().details;
            String taskDueDate = getTaskDueDate(task);
            String taskDueTime = getTaskDueTime(task);
            String taskPriority = task.getPriority().value;
            
           // Done isDone = task.checkDone(); 
            
            CMDoTaskBox newTaskBox = new CMDoTaskBox(taskIndex,  
                    taskTitle, 
                    taskDueDate,
                    taskDueTime, 
                    taskPriority); 
        }               
        
        setItemsToTaskPanel(taskBox);
    }
    
    
    public HBox addHBox(int taskIndex, String taskTitle, String taskDueDate,
    		String taskDueTime, String taskPriority) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        Label id = new Label(Integer.toString(taskIndex));
        Label title = new Label(taskTitle);
        Label startTime = new Label(taskDueDate);
        Label endTime = new Label(taskDueTime);
        Label priority = new Label(taskPriority);
       
        if (taskPriority != null) {
            hbox.getChildren().addAll(priority);
        }
        return hbox;
    }
}
