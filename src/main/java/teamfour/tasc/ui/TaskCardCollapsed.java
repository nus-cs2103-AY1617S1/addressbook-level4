package teamfour.tasc.ui;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.ReadOnlyTask;

public class TaskCardCollapsed extends UiPart{

    private static final String FXML = "TaskListCardCollapsed.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCardCollapsed(){

    }

    public static TaskCardCollapsed load(ReadOnlyTask task, int displayedIndex){
        TaskCardCollapsed card = new TaskCardCollapsed();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().getName());
        id.setText(displayedIndex + ". ");
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

