package tars.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.util.StringUtil;
import tars.model.task.ReadOnlyTask;
import tars.ui.formatter.DateFormatter;

// @@author A0121533W
/**
 * UI Controller for Task Card
 */
public class TaskCard extends UiPart {

    private static final String FXML = "TaskListCard.fxml";
    private static final String STATUS_UNDONE = "Undone";
    private static final String STATUS_DONE = "Done";
    private static final String PRIORITY_HIGH = "h";
    private static final String PRIORITY_MEDIUM = "m";
    private static final String PRIORITY_LOW = "l";
    private static final String LABEL_HIGH = "H";
    private static final String LABEL_MEDIUM = "M";
    private static final String LABEL_LOW = "L";
    private static final String LABEL_DONE = "✔";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label circleLabel;
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
        setTags();
        setCircle();
        setCardTextColorByStatus();
    }

    private void setName() {
        name.setText(task.getName().taskName);
    }

    private void setIndex() {
        id.setText(displayedIndex + StringUtil.STRING_FULLSTOP);
    }

    private void setDate() {
        date.setText(DateFormatter.formatDate(task.getDateTime()));
    }

    /**
     * Sets UI for Task Card Circle
     */
    private void setCircle() {
        String priority = task.getPriority().priorityLevel;
        String status = task.getStatus().toString();

        Color circleColor = getColorBasedOnPriorityAndStatus(priority, status);
        String label = getLabelBasedOnPriorityAndStatus(priority, status);

        priorityCircle.setFill(circleColor);

        circleLabel.setText(label);
        circleLabel.setStyle(UiColor.CIRCLE_LABEL_COLOR);
    }

    // @@author A0121533W
    /**
     * Gets color for circle based on task's priority and status
     */
    private Color getColorBasedOnPriorityAndStatus(String priority,
            String status) {
        if (status.equals(STATUS_DONE)) {
            return UiColor.CircleColor.DONE.getCircleColor();
        } else {
            switch (priority) {
                case PRIORITY_HIGH:
                    return UiColor.CircleColor.HIGH.getCircleColor();
                case PRIORITY_MEDIUM:
                    return UiColor.CircleColor.MEDIUM.getCircleColor();
                case PRIORITY_LOW:
                    return UiColor.CircleColor.LOW.getCircleColor();
                default:
                    return UiColor.CircleColor.NONE.getCircleColor();
            }
        }
    }

    // @@author A0121533W
    /**
     * Gets label for circle based on task's priority and status
     */
    private String getLabelBasedOnPriorityAndStatus(String priority,
            String status) {
        if (status.equals(STATUS_DONE)) {
            return LABEL_DONE;
        } else {
            switch (priority) {
                case PRIORITY_HIGH:
                    return LABEL_HIGH;
                case PRIORITY_MEDIUM:
                    return LABEL_MEDIUM;
                case PRIORITY_LOW:
                    return LABEL_LOW;
                default:
                    return StringUtil.EMPTY_STRING;
            }
        }
    }

    /**
     * Sets text to different color based on the status of a task
     */
    private void setCardTextColorByStatus() {
        String taskStatus = task.getStatus().toString();
        String color = StringUtil.EMPTY_STRING;
        switch (taskStatus) {
            case STATUS_UNDONE:
                color = UiColor.STATUS_UNDONE_TEXT_FILL_DARK;
                break;
            case STATUS_DONE:
                color = UiColor.STATUS_DONE_TEXT_FILL;
                break;
        }
        id.setStyle(color);
        name.setStyle(color);
        date.setStyle(color);
        tags.setStyle(color);

        if (taskStatus.equals(STATUS_UNDONE)) {
            date.setStyle(UiColor.STATUS_UNDONE_TEXT_FILL_LIGHT);
            tags.setStyle(UiColor.STATUS_UNDONE_TEXT_FILL_LIGHT);
        }
    }

    @Subscribe
    private void handleTarsChangeEvent(TarsChangedEvent event) {
        setCircle();
        setCardTextColorByStatus();
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
