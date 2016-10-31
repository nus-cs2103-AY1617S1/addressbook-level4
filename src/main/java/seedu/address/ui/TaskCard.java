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
    
    private static final String OVERDUE_CARDPANE_CSS = "-fx-background-color: rgba(214, 14, 14, 0.85);";//"-fx-background-color: #d60e0e; -fx-background-opacity: 0.85;";
    private static final String OVERDUE_SECONDDATE_CSS = "-fx-text-fill: rgba(225, 242, 225, 1.0);";//"-fx-text-fill: #e1f2e1;";
    private static final String OVERDUE_FIRSTDATE_CSS = OVERDUE_SECONDDATE_CSS;
    private static final String OVERDUE_ID_CSS = "-fx-text-fill: white;";
    private static final String OVERDUE_DESCRIPTION_CSS = OVERDUE_ID_CSS;
    
    private static final String FAVORITE_CARDPANE_CSS = "-fx-background-color: rgba(9, 198, 9, 0.85);";//"-fx-background-color: #09c609; -fx-background-opacity: 0.85;";
    private static final String FAVORITE_SECONDDATE_CSS = OVERDUE_SECONDDATE_CSS;
    private static final String FAVORITE_FIRSTDATE_CSS = OVERDUE_FIRSTDATE_CSS;
    private static final String FAVORITE_ID_CSS = "-fx-text-fill: rgba(0, 102, 0, 1.0);";//"-fx-text-fill: #006600;";
    private static final String FAVORITE_DESCRIPTION_CSS = FAVORITE_ID_CSS;
    
    private static final String FXML = "TaskListCard.fxml";
    
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, h.mm a");
        first_date.setStyle("-fx-text-fill: gray;");
        second_date.setStyle("-fx-text-fill: gray;");
        
        // Assigning Date labels only if task is DeadlineTask or EventTask
        if (task instanceof DeadlineTask) {
        	DeadlineTask curr = (DeadlineTask) task;
        	first_date.setText("by " + dateFormat.format(curr.getDeadline()));
        	second_date.setText("");
        } else if (task instanceof EventTask) {
        	EventTask curr = (EventTask) task;
        	first_date.setText("from " + dateFormat.format(curr.getStartDate()));
        	second_date.setText(" to  " + dateFormat.format(curr.getEndDate()));
        } else {
        	first_date.setText("");
        	second_date.setText("");
        }
    }
    
    // Set the colors for overdue tasks
    public void setOverdueStyle() {
        cardPane.setStyle(OVERDUE_CARDPANE_CSS);
    	description.setStyle(OVERDUE_DESCRIPTION_CSS);
    	id.setStyle(OVERDUE_ID_CSS);
    	first_date.setStyle(OVERDUE_FIRSTDATE_CSS);
    	second_date.setStyle(OVERDUE_SECONDDATE_CSS);
    }
    
    // Set the colors for pinned tasks
    public void setPinnedStyle() {
        cardPane.setStyle(FAVORITE_CARDPANE_CSS);
    	description.setStyle(FAVORITE_DESCRIPTION_CSS);
    	id.setStyle(FAVORITE_ID_CSS);
    	first_date.setStyle(FAVORITE_FIRSTDATE_CSS);
    	second_date.setStyle(FAVORITE_SECONDDATE_CSS);
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
