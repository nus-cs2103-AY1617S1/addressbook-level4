package seedu.tasklist.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.tasklist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label dueDate;
    @FXML
    private Label description;
    @FXML
    private Label tags;

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
        name.setText(task.getTitle().fullTitle);
        id.setText(displayedIndex + ". ");
        if(task.getStartDate().startDate != null){
        	startDate.setText("Start:  " +task.getStartDate().toString().replaceAll(" ", "    Time:  "));
        } else{
        	startDate.setText(" ");
        }
        if(task.getDueDate().dueDate != null){
        	dueDate.setText("End:    " + task.getDueDate().toString().replaceAll(" ", "    Time:  "));
        } else{
        	dueDate.setText(" ");
        }
        description.setText(task.getDescription().description);
        tags.setText(task.tagsString());
        setBackgroundColor();
    }

    public void setBackgroundColor() {
        if (task.isCompleted()) {
            cardPane.setStyle("-fx-background-color: yellow;");
        } else {
            cardPane.setStyle("-fx-background-color: white;");
        }
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
