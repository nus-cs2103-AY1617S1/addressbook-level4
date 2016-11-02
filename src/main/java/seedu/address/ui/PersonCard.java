package seedu.address.ui;

import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.deadline.DateManager;
import seedu.address.model.task.ReadOnlyTask;

public class PersonCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startline;
    @FXML
    private Label deadlines;
    @FXML
    private Label priority;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public PersonCard(){

    }

    public static PersonCard load(ReadOnlyTask person, int displayedIndex){
        PersonCard card = new PersonCard();
        card.task = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {    	
        name.setText(task.getName().fullName);
        Calendar cal = task.getDeadline().calendar;
        if(cal != null) {
	        DateManager datemanager = new DateManager(task.getDeadline().calendar);
	        switch(datemanager.calculateDaysRemaining()){
	        	case 4: name.setStyle("-fx-text-fill: blue;"); break;
	        	case 3: name.setStyle("-fx-text-fill: green;"); break; 
	        	case 2: name.setStyle("-fx-text-fill: purple;"); break;
	        	case 1: case 0: name.setStyle("-fx-text-fill: orange;"); break;
	        }
	        if(datemanager.checkOverdue()) {
	        	name.setStyle("-fx-text-fill: red;");
	        }
        }
        if(task.getDeadline().isOverdue) {
        	name.setStyle("-fx-text-fill: red;");
        }
        id.setText(displayedIndex + ". ");
        if(task.getStartline().calendar != null){
        	startline.setText("Start: " + task.getStartline());
        } else {
        	startline.setText("");
        }
        if(task.getDeadline().calendar != null) {
        	deadlines.setText("End: " + task.getDeadline().value);
        } else {
        	deadlines.setText("");
        }
        if(Integer.parseInt(task.getPriority().value) != 0) {
        	priority.setText("Priority: " + task.getPriority().value);
        } else {
        	priority.setText("");
        }
        tags.setText(task.tagsString());
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
