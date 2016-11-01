package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Text name;
    @FXML
    private Label id;
    @FXML
    private Label details;
    @FXML
    private Label priority;
    @FXML
    private Label onDate;
    @FXML
    private Label byDate;
    @FXML
    private Label tags;
    @FXML
    private Circle priorityLevel;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
//@@author A0121643R
    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        if (task.getCompletion().isCompleted()) {
        	name.setFill(Color.LIGHTGREY);
        	name.setStyle("-fx-strikethrough: true");
        	name.setOpacity(50);
        }
        
        id.setText(displayedIndex + ". ");
        details.setText(task.getDetail().value);
        onDate.setText("Start: " + task.getOnDate().toString());
        byDate.setText("End: " + task.getByDate().toString());
        priority.setText("Priority: " + task.getPriority().toString());
        if (task.getCompletion().isCompleted()) {
        	priorityLevel.setFill(Color.WHITE);
        	priorityLevel.setStroke(Color.WHITE);
        } else if (task.getPriority().toString().equals(Priority.LOW)) {
        	priorityLevel.setFill(Color.LIMEGREEN);
        } else if (task.getPriority().toString().equals(Priority.MID)) {
        	priorityLevel.setFill(Color.YELLOW);
        } else {
        	priorityLevel.setFill(Color.RED);
        }
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
