package seedu.taskman.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.taskman.model.task.EventInterface;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label deadline;
    @FXML
    private Label status;
    @FXML
    private Label recurrence;
    @FXML
    private Label schedule;
    @FXML
    private Label tags;

    private EventInterface task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(EventInterface task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        title.setText(task.getTitle().title);
        id.setText(displayedIndex + ". ");
        // deadline.setText(task.getDeadline().toString()); // need fix
        // status.setText(task.getStatus().toString());
        recurrence.setText(task.getRecurrence().toString());
        schedule.setText(task.getSchedule().toString());
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
