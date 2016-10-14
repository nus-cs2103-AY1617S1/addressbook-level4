package tars.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tars.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final String PRIORITY_HIGH = "high";
    private static final String PRIORITY_MEDIUM = "medium";
    private static final String PRIORITY_LOW = "low";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label priority;
    @FXML
    private Label status;
    @FXML
    private Label tags;

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
        name.setText(task.getName().taskName);
        setPriority(task);
        id.setText(displayedIndex + ". ");
        if (task.getDateTime().startDateString != null) {
            startDate.setText(task.getDateTime().startDateString);
        } else {
            startDate.setVisible(false);
            startDate.setManaged(false);
        }
        endDate.setText(task.getDateTime().endDateString);
        status.setText(task.getStatus().toString());
        tags.setText(task.tagsString());
    }

    /**
     * Sets colors to priority label based on task's priority
     * 
     * @@author A0121533W
     */
    private void setPriority(ReadOnlyTask task) {
        switch (task.priorityString()) {
        case PRIORITY_HIGH:
            priority.setText(task.priorityString());
            priority.setStyle("-fx-text-fill: red");
            break;
        case PRIORITY_MEDIUM:
            priority.setText(task.priorityString());
            priority.setStyle("-fx-text-fill: orange");
            break;
        case PRIORITY_LOW:
            priority.setText(task.priorityString());
            priority.setStyle("-fx-text-fill: green");
            break;  
        default:
            priority.setText(task.priorityString());
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
