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
    private Text details;
    @FXML
    private Text recurrence;
    @FXML
    private Text onDate;
    @FXML
    private Text byDate;
    @FXML
    private Text tags;
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
        
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        details.setText(task.getDetail().value);
        
        initOnDate();
        initByDate();
        initRecurrence();
        initPriority();
        tags.setText(task.tagsString());
        
        if(task.getCompletion().isCompleted()) {
            styleForCompletion();
        }
        
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
    
    private void initOnDate() {
        if (task.getOnDate().getDate() != null) {
            onDate.setText("Start: " + PrettifyDate.prettifyDate(task.getOnDate().getDate()) 
                            + " @ " + task.getOnDate().getTime());
        } else {
            onDate.setText("");
        }
    }
    
    private void initByDate() {
        if (task.getByDate().getDate() != null) {
            byDate.setText("End: " + PrettifyDate.prettifyDate(task.getByDate().getDate()) 
                            + " @ " + task.getByDate().getTime());
        } else {
            byDate.setText("");
        }
    }
    
    
    private void initPriority() {
        if (task.getPriority().toString().equals(Priority.LOW)) {
            priorityLevel.setFill(Color.LIMEGREEN);
        } else if (task.getPriority().toString().equals(Priority.MID)) {
            priorityLevel.setFill(Color.YELLOW);
        } else {
            priorityLevel.setFill(Color.RED);
        }
    }
    
    private void initRecurrence() {
        if (task.isRecurring()) {
            recurrence.setText("Every: " + task.getRecurrence().toString());
        } else {
            recurrence.setText("");
        }
    }
    
    
    private void styleForCompletion() {
        name.setFill(Color.LIGHTGREY);
        name.setStyle("-fx-strikethrough: true");
        name.setOpacity(50);
        
        details.setFill(Color.LIGHTGREY);
        onDate.setFill(Color.LIGHTGREY);
        byDate.setFill(Color.LIGHTGREY);
        recurrence.setFill(Color.LIGHTGREY);
        tags.setFill(Color.LIGHTGREY);

        priorityLevel.setFill(Color.WHITE);
        priorityLevel.setStroke(Color.WHITE);

    }
}
