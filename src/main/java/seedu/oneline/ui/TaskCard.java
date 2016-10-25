package seedu.oneline.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.oneline.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox taskCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label line1;
    @FXML
    private Label recurrence;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard() {

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        TaskCardParser parser = new TaskCardParser(task);
        name.setText(parser.getName());
        id.setText(displayedIndex + ". ");
        line1.setText(parser.getTime());
        recurrence.setText(parser.getRecurrence());
    }

    public HBox getLayout() {
        return taskCardPane;
    }

    @Override
    public void setNode(Node node) {
        taskCardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
