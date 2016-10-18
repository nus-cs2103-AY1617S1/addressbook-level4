package seedu.address.ui;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.Task;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label first_date;
    @FXML
    private Label second_date;

    private Task task;
    private int displayedIndex;

    public TaskCard(){
    }

    public static TaskCard load(Task task, int displayedIndex){
    	//if(task.isComplete()){
    		TaskCard card = new TaskCard();
    		card.task = task;
    		card.displayedIndex = displayedIndex;
    		return UiPartLoader.loadUiPart(card);
    	/*}
    	else{
    		return null;
    	}
    	*/
    }

    @FXML
    public void initialize() {
        description.setText(task.getDescription().getContent());
        if(task.isComplete()){
        	id.setText(displayedIndex + ". [Completed] ");
        }
        else{
        	id.setText(displayedIndex + ". ");
        }
        
        // Format to display the dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        
        // Assigning Date labels only if task is DeadlineTask or EventTask
        if (task instanceof DeadlineTask) {
        	DeadlineTask curr = (DeadlineTask) task;
        	first_date.setText("Deadline: " + dateFormat.format(curr.getDeadline()));
        	second_date.setText("");
        } else if (task instanceof EventTask) {
        	EventTask curr = (EventTask) task;
        	first_date.setText("Start date: " + dateFormat.format(curr.getStartDate()));
        	second_date.setText("End date: " + dateFormat.format(curr.getEndDate()));
        } else {
        	first_date.setText("");
        	second_date.setText("");
        }
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
