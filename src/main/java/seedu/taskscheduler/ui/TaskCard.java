package seedu.taskscheduler.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.ReadOnlyTask.TaskType;


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
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label tags;
    @FXML
    private Polygon completeStatus;

    private ReadOnlyTask person;
    private int displayedIndex;

    public static final Paint COMPLETED_INDICATION = Paint.valueOf("#47fc00");
    public static final Paint OVERDUE_INDICATION = Paint.valueOf("#fc0000");

    public static TaskCard load(ReadOnlyTask person, int displayedIndex){
        TaskCard card = new TaskCard();
        card.person = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(person.getName().fullName);
        hideFieldsAccordingToType(person);
        indicatingColourByCondition(person);
        id.setText(displayedIndex + ". ");
        address.setText(person.getLocation().value);
        phone.setText("Start Date: " + person.getStartDate().getDisplayString());
        email.setText("Due Date: " + person.getEndDate().getDisplayString());
        tags.setText(person.tagsString());
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
            phone.setVisible(false);
            address.setVisible(false);
        } else if (task.getType() == TaskType.FLOATING) {
            phone.setVisible(false);
            address.setVisible(false);
            email.setVisible(false);
        }
    }

    //@@author A0148145E
    public void indicatingColourByCondition(ReadOnlyTask task) {
        
        if (task.getCompleteStatus()) {
            completeStatus.setFill(COMPLETED_INDICATION);
        } else if (task.getEndDate().getDate() != null && task.getEndDate().getDate().before(new Date())) {
            completeStatus.setFill(OVERDUE_INDICATION);
        }
        
    }
}
