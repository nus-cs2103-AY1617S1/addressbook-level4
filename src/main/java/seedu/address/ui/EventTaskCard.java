//@@author A0142184L
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.TaskDateTimeFormatter;

public class EventTaskCard extends UiPart{

    private static final String FXML = "EventTaskCard.fxml";

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
    private Label startDateAndTime;
    @FXML
    private Label endDateAndTime;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public EventTaskCard(){

    }

    public static EventTaskCard load(ReadOnlyTask task, int displayedIndex){
    	EventTaskCard card = new EventTaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        taskName.setText(task.getName().value);
        id.setText(displayedIndex + ". ");
        taskType.setText(task.getTaskType().toString());
        setTaskDateTime();
        setTaskStatus();
        tags.setText(task.tagsString());
    }

	private void setTaskDateTime() {
		if (task.getStartDate().get().toLocalDate().equals(task.getEndDate().get().toLocalDate())) {
            startDateAndTime.setText(TaskDateTimeFormatter.formatToShowDateAndTime(task.getStartDate().get()));
            endDateAndTime.setText(TaskDateTimeFormatter.formatToShowTimeOnly(task.getEndDate().get().toLocalTime()));
        } else {
          startDateAndTime.setText(TaskDateTimeFormatter.formatToShowDateAndTime(task.getStartDate().get()));
          endDateAndTime.setText(TaskDateTimeFormatter.formatToShowDateAndTime(task.getEndDate().get()));
        }
	}

    private void setTaskStatus() {
		if (task.getStatus().value.equals(Status.StatusType.DONE)) {
			taskStatus.setText(task.getStatus().value.toString().toUpperCase());
			taskStatus.setStyle("-fx-text-fill: green");
		} else if (task.getStatus().value.equals(Status.StatusType.OVERDUE)) {
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