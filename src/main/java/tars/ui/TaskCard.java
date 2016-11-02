package tars.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.util.StringUtil;
import tars.model.task.ReadOnlyTask;

/**
 * UI Controller for Task Card
 * 
 * @@author A0121533W
 *
 */
public class TaskCard extends UiPart {

    private static final String FXML = "TaskListCard.fxml";
    private static final String PRIORITY_HIGH = "high";
    private static final String PRIORITY_MEDIUM = "medium";
    private static final String PRIORITY_LOW = "low";
    private static final String STATUS_UNDONE = "Undone";
    private static final Object STATUS_DONE = "Done";

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

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard() {

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex) {
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
        setTickColorByStatus();
        setTags();
        setTextFillByStatus();
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
        } else if (endDateString != null) {
            endDate.setText(endDateString);
        }
    }

    /**
     * Sets tick priority color based on the status of a task
     */
    private void setTickColorByStatus() {
        if (task.getStatus().toString().equals(STATUS_UNDONE)) {
            statusTick.setStyle("-fx-text-fill: transparent");
        } else {
            statusTick.setStyle(UiColor.STATUS_DONE_TICK_COLOR);
        }
    }

    /**
     * Sets text to different color based on the status of a task
     */
    private void setTextFillByStatus() {
        String taskStatus = task.getStatus().toString();
        String color = StringUtil.EMPTY_STRING;
        if (taskStatus.equals(STATUS_UNDONE)) {
            color = UiColor.STATUS_UNDONE_TEXT_FILL;
        } else if (taskStatus.equals(STATUS_DONE)) {
            color = UiColor.STATUS_DONE_TEXT_FILL;
        } else {
            // default case
            color = UiColor.STATUS_UNDONE_TEXT_FILL;
        }
        id.setStyle(color);
        name.setStyle(color);
        start.setStyle(color);
        end.setStyle(color);
        startDate.setStyle(color);
        endDate.setStyle(color);
        tags.setStyle(color);
    }

    @Subscribe
    private void handleTarsChangeEvent(TarsChangedEvent event) {
        setTextFillByStatus();
        setTickColorByStatus();
    }

    /**
     * Sets colors to priority label based on task's priority
     * 
     * @@author A0121533W
     */
    private void setPriority() {
        switch (task.priorityString()) {
            case PRIORITY_HIGH:
                priorityCircle.setFill(UiColor.Priority.HIGH.getCircleColor());
                break;
            case PRIORITY_MEDIUM:
                priorityCircle
                        .setFill(UiColor.Priority.MEDIUM.getCircleColor());
                break;
            case PRIORITY_LOW:
                priorityCircle.setFill(UiColor.Priority.LOW.getCircleColor());
                break;
            default:
                priorityCircle
                        .setFill(UiColor.Priority.DEFAULT.getCircleColor());
        }
    }

    private void setTags() {
        tags.setText(task.tagsString());
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
