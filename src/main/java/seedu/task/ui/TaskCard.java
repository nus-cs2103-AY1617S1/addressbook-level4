package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label venue;
    @FXML
    private Label id;
    @FXML
    private Label dateTime;
    @FXML
    private Label priority;
    @FXML
    private Label status;
    @FXML
    private Label tags;

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

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        setTextForVenue();
        setTextForDate();
        id.setText(displayedIndex + ". ");
        priority.setText(task.getPriority().toString());
        status.setText(task.getStatus().toString());
        tags.setText(task.tagsString());
    }

    private void setTextForDate() {
        if(!task.getStartDate().value.isEmpty()){
            dateTime.setText(task.getStartDate().value + " till " + task.getEndDate().value);        
        } else {
            if(task.getEndDate().value.isEmpty()){
                dateTime.setText("");
            } else {
                dateTime.setText("Due by: " + task.getEndDate().value); 
            }
        }
    }

    private void setTextForVenue() {
        if(task.getVenue().value.isEmpty()){
            venue.setText("");
        } else {
            venue.setText("Venue: " + task.getVenue().value);
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
