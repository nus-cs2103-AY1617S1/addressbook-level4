package seedu.ggist.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label priority;

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
        taskName.setText(task.getTaskName().taskName);
        id.setText(displayedIndex + ". ");
        if (task.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)) {
            startDate.setText("");
        } else {
            startDate.setText(task.getStartDate().value);
        }
        if (task.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)) {
            startTime.setText("");
        } else {
            startTime.setText(task.getStartTime().value);
        }
        if (task.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) {
            endDate.setText("");
        } else {
            endDate.setText(task.getEndDate().value);
        } 
        if (task.getEndTime().value.equals(Messages.MESSAGE_NO_END_TIME_SET)) {
            endTime.setText("");
        } else {
            endTime.setText(task.getEndTime().value);
        }
        if (task.getPriority().value.equals(Messages.MESSAGE_NO_PRIORITY_VALUE)) {
            priority.setText("");
        } else {
            priority.setText(task.getPriority().value);
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
