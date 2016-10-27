package tars.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tars.commons.events.model.TarsChangedEvent;
import tars.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final String PRIORITY_HIGH = "high";
    private static final String PRIORITY_MEDIUM = "medium";
    private static final String PRIORITY_LOW = "low";
    private static final String STATUS_UNDONE = "Undone";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label statusTick;
    @FXML
    private Label tags;
    @FXML
    private Circle priorityCircle;
    @FXML
    private Label status;
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
        card.registerAsAnEventHandler(card);
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        setName();
        setIndex();
        setDate();
        setPriority();
        setStatus();
        setTags();
        setTextFill();
    }

    private void setName() {
        name.setText(task.getName().taskName);
    }   

    private void setIndex() {
        id.setText(displayedIndex + ". ");
    }

    private void setDate() {
        String startDateString = task.getDateTime().startDateString;
        String endDateString = task.getDateTime().endDateString;
        if (startDateString == null) {
            startDate.setVisible(false);
            startDate.setManaged(false);
        } else if (startDateString != null) {
            startDate.setText(startDateString);
        }
        if (endDateString == null) {
            endDate.setVisible(false);
            endDate.setManaged(false);
        } else if (endDateString != null){
            endDate.setText(endDateString);
        }      
    }

    /**
     * Sets tick color based on task's status
     */
    private void setStatus() {
        if (task.getStatus().toString().equals(STATUS_UNDONE)) {
            String tickColor = "";
            switch (task.priorityString()) {
            case PRIORITY_HIGH:
                tickColor = "red";
                break;
            case PRIORITY_MEDIUM:
                tickColor = "orange";
                break;
            case PRIORITY_LOW:
                tickColor = "green";
                break;  
            default:
                tickColor = "darkgrey";
            }
            statusTick.setStyle("-fx-text-fill: " + tickColor);
        } else {
            statusTick.setStyle("-fx-text-fill: #F5F5F5");
        }
        status.setText(task.getStatus().toString());
        status.setVisible(false);
        status.setManaged(false);
    }

    /**
     * Set text to different color based on status of task
     */
    private void setTextFill() {
        if (task.getStatus().toString().equals(STATUS_UNDONE)) {
            id.setStyle("-fx-text-fill: #212121");
            name.setStyle("-fx-text-fill: #212121");
            start.setStyle("-fx-text-fill: #212121");
            startDate.setStyle("-fx-text-fill: #212121");
            end.setStyle("-fx-text-fill: #212121");
            endDate.setStyle("-fx-text-fill: #212121");
        } else {
            id.setStyle("-fx-text-fill: #BDBDBD");
            name.setStyle("-fx-text-fill: #BDBDBD");
            start.setStyle("-fx-text-fill: #BDBDBD");
            startDate.setStyle("-fx-text-fill: #BDBDBD");
            end.setStyle("-fx-text-fill: #BDBDBD");
            endDate.setStyle("-fx-text-fill: #BDBDBD");
        }
    }

    @Subscribe
    private void handleTarsChangeEvent(TarsChangedEvent event) {
        setTextFill();
        setStatus();
    }

    /**
     * Sets colors to priority label based on task's priority
     * 
     * @@author A0121533W
     */
    private void setPriority() {
        switch (task.priorityString()) {
        case PRIORITY_HIGH:
            priorityCircle.setFill(Color.RED);
            break;
        case PRIORITY_MEDIUM:
            priorityCircle.setFill(Color.ORANGE);
            break;
        case PRIORITY_LOW:
            priorityCircle.setFill(Color.GREEN);
            break;  
        default:
            priorityCircle.setFill(Color.DARKGREY);
        }
        priority.setText(task.priorityString());
        priority.setVisible(false);
        priority.setManaged(false);
    }

    private void setTags() {
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
