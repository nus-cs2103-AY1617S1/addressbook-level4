package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.TaskType;

public class DeadlineTaskCard extends UiPart{

    private static final String FXML = "DeadlineTaskCard.fxml";

    @FXML
    private VBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label taskType;
    @FXML
    private Label taskStatus;
    @FXML
    private Label dueDateAndTime;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public DeadlineTaskCard(){

    }

    public static DeadlineTaskCard load(ReadOnlyTask task, int displayedIndex){
    	DeadlineTaskCard card = new DeadlineTaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        taskName.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        taskType.setText(task.getTaskType().toString());
        dueDateAndTime.setText(task.getEndDate().get().toString());
        setTaskStatus();
        tags.setText(task.tagsString());
    }

    private void setTaskStatus() {
		if (task.getStatus().value.equals(Status.DoneStatus.DONE)) {
			taskStatus.setText(task.getStatus().value.toString().toUpperCase());
			taskStatus.setStyle("-fx-text-fill: green");
		} else if (task.getStatus().value.equals(Status.DoneStatus.OVERDUE)) {
			taskStatus.setText(task.getStatus().value.toString().toUpperCase());
			taskStatus.setStyle("-fx-text-fill: red");
		}
	}
    
    public VBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (VBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}