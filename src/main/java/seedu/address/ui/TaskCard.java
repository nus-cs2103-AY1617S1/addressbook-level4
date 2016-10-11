package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskType;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label tags;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard() {}

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
        tags.setText(task.tagsString());
        if (task.getType() == TaskType.NON_FLOATING) {
            initializeNonFloating();
        }
        else if (task.getType() == TaskType.FLOATING) {
            initializeFloating();
        }
    }

    private void initializeFloating() {
        startDate.setText("");
        endDate.setText("");
    }

    private void initializeNonFloating() {
        if (task.getStartDate().getDate() == TaskDate.DATE_NOT_PRESENT) {
            startDate.setText("");
        } else {
            startDate.setText(task.getStartDate().getFormattedDate());
        }
        endDate.setText(task.getEndDate().getFormattedDate());
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
