package seedu.task.ui;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label tags;
    @FXML
    private Label completeStatus;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard() {

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getDescription().fullDescription);
        id.setText(displayedIndex + ". ");
        priority.setText("Priority: " + task.getPriority().toString());
	    priority.setTextFill(Color.RED);

	    if(task.getTimeStart().toString().equals("")){
	    	startTime.setText("");
	    }
	    else startTime.setText("Start Time: " + task.getTimeStart().toString());

	    if(task.getTimeEnd().toString().equals(""))
	    	endTime.setText("");
	    else {
	    	if(task.getTimeStart().toString().equals(""))
	    		endTime.setText("Deadline: " + task.getTimeEnd().toString());
	    	else endTime.setText("End Time: " + task.getTimeEnd().toString());
	    }

	    if(task.tagsString().equals(""))
	    	tags.setText("");
	    else tags.setText("Tags: " + task.tagsString());
        completeStatus.setText(task.getCompleteStatus()? "  [Completed]":
        			(task.getTimeEnd().isBefore(LocalDateTime.now())? "  [OverDue]" : "  [Not Completed]"));
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
