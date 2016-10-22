package seedu.unburden.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.unburden.model.task.ReadOnlyTask;

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

    private ReadOnlyTask person;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask person, int displayedIndex){
        TaskCard card = new TaskCard();
        card.person = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        taskD.setText("Task Description : " + person.getTaskDescription().fullTaskDescriptions);
        date.setText("Date: " + person.getDate().fullDate);
        startTime.setText("Start Time: " + person.getStartTime().fullTime);
        endTime.setText("End Time: " + person.getEndTime().fullTime);
        done.setText(" [ " + person.getDoneString() + " ] ");
        tags.setText("      " + person.tagsString());
        
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
