package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label note;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label status;
    
    private ReadOnlyActivity task;
    
    private int displayedIndex;

    public TaskCard(){
        
    }

    public static TaskCard load(ReadOnlyActivity task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getActivityName().fullName);
        note.setText(task.getNote().toString());
        startDate.setText(task.getActivityStartDate().toString());
        startTime.setText(task.getActivityStartTime().toString());
        status.setText(task.getActivityStatus().toString());
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
