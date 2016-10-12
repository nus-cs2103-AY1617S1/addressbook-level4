package seedu.malitio.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.malitio.model.task.ReadOnlyTask;

public class TaskCard2 extends UiPart{

    private static final String FXML = "TaskListCard2.fxml";

    @FXML
    private HBox cardPane2;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard2(){

    }

    public static TaskCard2 load(ReadOnlyTask task, int displayedIndex){
        TaskCard2 card = new TaskCard2();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        tags.setText(task.tagsString());
    }

    public HBox getLayout() {
        return cardPane2;
    }

    @Override
    public void setNode(Node node) {
        cardPane2 = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
