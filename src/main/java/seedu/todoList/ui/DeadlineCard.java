package seedu.todoList.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.todoList.model.task.*;

//@@author A0144061U-reused
public class DeadlineCard extends UiPart{

    private static final String FXML = "DeadlineCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label endTime;
    @FXML
    private Label done;

    private Deadline task;
    private int displayedIndex;

    public DeadlineCard(){

    }

    public static DeadlineCard load(ReadOnlyTask task, int displayedIndex){
        DeadlineCard card = new DeadlineCard();
        card.task = (Deadline) task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().name);
        id.setText(displayedIndex + ". ");
        date.setText("Date: " + task.getDate().date);
        endTime.setText("End Time: " + task.getEndTime().endTime);
        if(this.task.getDone().equals("true")) {
        	done.setText("Completed");
    		cardPane.setStyle("-fx-background-color: #01DF01");
    	} else {
    		done.setText("Not Completed");
    		cardPane.setStyle("-fx-background-color: #FFFFFF");
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
