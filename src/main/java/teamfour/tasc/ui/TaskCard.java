package teamfour.tasc.ui;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label deadline;
    @FXML
    private Label recurrence;
    @FXML
    private Label period;
    @FXML
    private Label tags;
    @FXML
    private Label completeStatus;

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
        name.setText(task.getName().getName());
        id.setText(displayedIndex + ". ");
        deadline.setText("Deadline: " + task.getDeadline().toString());
        recurrence.setText("Repeat: " + task.getRecurrence().toString());
        period.setText("Period : " + task.getPeriod().toString());
        tags.setText(task.tagsString().equals("") ? "" : "Tags: " + task.tagsString());
        completeStatus.setText(task.getComplete().toString());
        if (task.getComplete().isCompleted()) {
            completeStatus.setStyle("-fx-text-fill: #008600;");
        } else {
            if (task.getDeadline().hasDeadline()) {
                if (task.getDeadline().getDeadline().getTime() < Instant.now().toEpochMilli()) {
                    completeStatus.setText("Overdue");
                    completeStatus.setStyle("-fx-text-fill: #ff0000;");
                }
            }
            if (task.getPeriod().hasPeriod()) {
                if (task.getPeriod().getEndTime().getTime() < Instant.now().toEpochMilli()) {
                    completeStatus.setText("Overdue");
                    completeStatus.setStyle("-fx-text-fill: #ff0000;");
                }
            }
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
