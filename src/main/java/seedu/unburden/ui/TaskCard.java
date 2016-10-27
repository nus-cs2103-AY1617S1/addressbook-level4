package seedu.unburden.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.unburden.model.task.ReadOnlyTask;

//@@author A0143095H 
public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label taskD;
    @FXML
    private Label date;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label id;
    @FXML
    private Label tags;
    @FXML
    private Label done;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        if(task.getDone()){
        	cardPane.setStyle("-fx-background-color : #f97f9c");
        }
        if(task.getTaskDescription().fullTaskDescriptions != "NIL"){
        	taskD.setText("Task Description : " + task.getTaskDescription().fullTaskDescriptions);
        }
        if(task.getDate().fullDate != "NIL"){
            date.setText("Date: " + task.getDate().fullDate);
        }
        if(task.getStartTime().fullTime != "NIL"){
        	startTime.setText("Start Time: " + task.getStartTime().fullTime);
        }
        if(task.getStartTime().fullTime != "NIL"){
            endTime.setText("End Time: " + task.getEndTime().fullTime);
        } 
        
        done.setText(" [ " + task.getDoneString() + " ] ");
        tags.setText("      " + task.tagsString());
        
        /*
    	if(person.getDate().fullDate != "NIL" && person.getStartTime().fullTime != "NIL"){
        name.setText(
        person.getName().fullName + "\n" 
        + "Deadline : " + person.getDate().fullDate + "\n"
        + "Start Time : " + person.getStartTime().fullTime + "\n"
        + "End Time : " + person.getEndTime().fullTime + "\n"
        );
        id.setText(displayedIndex + ". ");
        //date.setText(person.getDate().fullDate);
        //startTime.setText(person.getStartTime().fullTime);
        //endTime.setText(person.getEndTime().fullTime);
        tags.setText("      " + person.tagsString());
    	}
    	
    	if(person.getStartTime().fullTime == "NIL" && person.getEndTime().fullTime == "NIL" && person.getDate().fullDate != "NIL"){
    		name.setText(person.getName().fullName + "\n"
    		+ "Deadline : " + person.getDate().fullDate + "\n");
    		id.setText(displayedIndex + ". ");
            tags.setText("      " + person.tagsString());
    	}
    	
    	if(person.getStartTime().fullTime == "NIL" && person.getEndTime().fullTime == "NIL" && person.getDate().fullDate == "NIL"){
    		name.setText(person.getName().fullName + "\n");
    		id.setText(displayedIndex + ". ");
            tags.setText("      " + person.tagsString());
    	}
    	*/
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
