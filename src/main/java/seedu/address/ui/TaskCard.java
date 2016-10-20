package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskType;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label tags;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label recurringType;

    private ReadOnlyTask task;
    private int displayedIndex;
    private TaskComponent dateComponent;
    
    public TaskCard() {}

    public static TaskCard load(TaskComponent taskComponent, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = taskComponent.getTaskReference();
        card.displayedIndex = displayedIndex;
        card.dateComponent = taskComponent;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        tags.setText(task.tagsString());
        initializeDate();
        initializeRecurringType();
        setCellColor();
    }

    private void initializeRecurringType() {
        String recurringTypeToShow = "";
        if (!task.getRecurringType().equals(RecurringType.NONE)) {
            recurringTypeToShow = task.getRecurringType().name();
        }
        recurringType.setText(recurringTypeToShow);
    }

    
    private void initializeDate() {
    	if (dateComponent.getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT) {
            startDate.setText("");
        } else {
            startDate.setText(dateComponent.getStartDate().getFormattedDate());
        }
    	
    	if (dateComponent.getEndDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT) {
            endDate.setText("");
        } else {
        	endDate.setText(dateComponent.getEndDate().getFormattedDate());
        }
    }
    
    private void setCellColor(){
    	
    	//normal non-floating task
    	cardPane.setStyle("-fx-background-color : rgba(152, 208, 255, 0.3);");
    	//Deadline
    	if(dateComponent.getStartDate().getDateInLong() == TaskDate.DATE_NOT_PRESENT
    			&& dateComponent.getEndDate().getDateInLong() != TaskDate.DATE_NOT_PRESENT)
    		cardPane.setStyle("-fx-background-color : rgba(255, 0, 0, 0.3);");
    	//Floating task
    	if(task.getTaskType() == TaskType.FLOATING)
    		cardPane.setStyle("-fx-background-color : rgba(255, 249, 152, 0.3);");
    	//Blocked Slot
    	if(task.getName().fullName.equals("BLOCKED SLOT"))
    		cardPane.setStyle("-fx-background-color : rgba(255, 0, 221, 0.3);");
    	//Completed
    	if(task.getTaskType() == TaskType.COMPLETED){
    		cardPane.setStyle("-fx-background-color : rgba(34,51,34,0.3);");
    		name.setStyle("-fx-text-fill : derive(#F0F0F0, 20%);");
    		id.setStyle("-fx-text-fill : derive(#F0F0F0, 20%);");
    		startDate.setStyle("-fx-text-fill : derive(#F0F0F0, 20%);");
    		endDate.setStyle("-fx-text-fill : derive(#F0F0F0, 20%);");
    	}
    	
    	
    }



    public HBox getLayout() {
    	
        return cardPane;
    }
    
    public HBox getNewLayout() {
    	cardPane.setStyle("-fx-background-color : derive(#00ffff, 20%);");
		name.setStyle("-fx-text-fill : derive(#F0F0F0, 20%);");
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
