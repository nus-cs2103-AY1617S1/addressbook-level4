package seedu.whatnow.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.whatnow.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final String INCOMPLETE = "";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label taskDate;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label taskTime;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label id;
    @FXML
    private Label tags;
    @FXML
    private Label status;

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
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");

        if(task.getTaskDate() != null) {
        	taskDate.setText(task.getTaskDate().getDate());
        } else if(task.getTaskDate() == null) {
        	taskDate.setText("");
        }
        
        if(task.getStartDate() != null) {
            startDate.setText(task.getStartDate().getDate());
        } else if(task.getStartDate() == null) {
            startDate.setText("");
        }
        
        if(task.getEndDate() != null) {
            endDate.setText("to " + task.getEndDate().getDate());
        } else if(task.getEndDate() == null) {
            endDate.setText("");
        }
        
        if(task.getTaskTime() != null) {
            taskTime.setText(task.getTaskTime());
        } else if(task.getTaskTime() == null) {
            taskTime.setText("");
        }
        
        if(task.getStartTime() != null) {
            startTime.setText(task.getStartTime());
        } else if(task.getStartTime() == null) {
            startTime.setText("");
        }
        
        if(task.getEndTime() != null) {
            if (task.getEndDate() == null)
                endTime.setText("to " + task.getEndTime());
            else if (task.getEndDate() != null)
                endTime.setText(task.getEndTime());
        } else if(task.getEndTime() == null) {
            endTime.setText("");
        }
        
        tags.setText(task.tagsString());
        if (task.getStatus().equals("incomplete")) {
            status.setText(INCOMPLETE);
        } else {
            status.setText(task.getStatus());
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
