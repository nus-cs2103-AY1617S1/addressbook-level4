package seedu.taskitty.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskTime;

public class EventCard extends UiPart{

    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
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

    public EventCard(){

    }

    public static EventCard load(ReadOnlyTask task, int displayedIndex){
        EventCard card = new EventCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        startDate.setText("");
        startTime.setText("");
        endDate.setText("");
        endTime.setText("");
        
        TaskDate startTaskDate = task.getStartDate();
        if (startTaskDate != null) {
            startDate.setText(startTaskDate.toString());
        }
        
        TaskTime taskStartTime = task.getStartTime();
        if (taskStartTime != null) {
            startTime.setText(taskStartTime.toString());
        }
        
        TaskDate endTaskDate = task.getEndDate();
        if (endTaskDate != null) {
            endDate.setText(endTaskDate.toString());
        }
        
        TaskTime taskEndTime = task.getEndTime();
        if (taskEndTime != null) {
            endTime.setText(taskEndTime.toString());
        }
        
        boolean isDone = task.getIsDone();
        if (isDone) {
        	cardPane.setStyle("-fx-background-color: grey");
        	name.setStyle("-fx-text-fill: white");
        	id.setStyle("-fx-text-fill: white");
        	startDate.setStyle("-fx-text-fill: white");
        	endDate.setStyle("-fx-text-fill: white");
        	startTime.setStyle("-fx-text-fill: white");
        	endTime.setStyle("-fx-text-fill: white");
        	
        }
        
        id.setText(displayedIndex + ". ");
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
