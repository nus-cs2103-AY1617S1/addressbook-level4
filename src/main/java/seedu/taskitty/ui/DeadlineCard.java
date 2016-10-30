package seedu.taskitty.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskTime;

public class DeadlineCard extends UiPart {
    
    private static final String FXML = "DeadlineListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    @FXML
    private Label id;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public static DeadlineCard load(ReadOnlyTask task, int displayedIndex){
       	DeadlineCard card = new DeadlineCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    //@@author A0139930B
    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        endDate.setText("");
        endTime.setText("");
        
        TaskDate endTaskDate = task.getPeriod().getEndDate();
        if (endTaskDate != null) {
            endDate.setText(endTaskDate.toString());
        }
        
        TaskTime taskEndTime = task.getPeriod().getEndTime();
        if (taskEndTime != null) {
            endTime.setText(taskEndTime.toString());
        }
        
        //@@author A0130853L
        boolean isDone = task.getIsDone();
        if (isDone) {
            cardPane.setStyle("-fx-background-color: grey");
            name.setStyle("-fx-text-fill: white");
            id.setStyle("-fx-text-fill: white");
            endDate.setStyle("-fx-text-fill: white");
            endTime.setStyle("-fx-text-fill: white");
            
        } else {
            
        	// only deadline tasks have isOverdue attribute
            boolean isOverdue = task.isOverdue();
            if (isOverdue) {
                cardPane.setStyle("-fx-background-color: red");
            }
        }
        
        //@@author A0139930B
        id.setText("d" + displayedIndex + ". ");
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
