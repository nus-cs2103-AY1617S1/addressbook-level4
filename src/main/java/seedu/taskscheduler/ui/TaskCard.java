package seedu.taskscheduler.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.ReadOnlyTask.TaskType;

//@@author A0148145E
/**
 * Represents task card in Ui
 */
public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label address;
    @FXML
    private Label tags;
    @FXML
    private Shape completeStatus;

    private ReadOnlyTask task;
    private int displayedIndex;

    public static final Paint COMPLETED_INDICATION = Paint.valueOf("#47fc00");
    public static final Paint OVERDUE_INDICATION = Paint.valueOf("#fc0000");

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        hideFieldsAccordingToType(task);
        indicatingColourByCondition(task);
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        address.setText(task.getLocation().value);
        startDateTime.setText("Start Date: " + task.getStartDate().getDisplayString());
        endDateTime.setText("Due Date: " + task.getEndDate().getDisplayString());
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

    //@@author A0148145E
    public void hideFieldsAccordingToType(ReadOnlyTask task) {
        if (task.getType() == TaskType.DEADLINE) {
            startDateTime.setManaged(false);
            address.setManaged(false);
        } else if (task.getType() == TaskType.FLOATING) {
            startDateTime.setManaged(false);
            address.setManaged(false);
            endDateTime.setManaged(false);
        }
    }

    //@@author A0148145E
    public void indicatingColourByCondition(ReadOnlyTask task) {
        
        if (task.isCompleted()) {
            completeStatus.setFill(COMPLETED_INDICATION);
        } else if (task.getEndDate().getDate() != null && task.getEndDate().getDate().before(new Date())) {
            completeStatus.setFill(OVERDUE_INDICATION);
        }
        
    }
}
