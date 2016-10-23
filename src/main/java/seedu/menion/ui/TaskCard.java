package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

//@@author A0139515A
public class TaskCard extends UiPart{

    private static final String FXML = "TaskCard.fxml";

    @FXML
    private HBox taskCardPane;
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
    private ImageView completionStatus;
    
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
        if (task.getActivityStatus().toString().equals("Completed")) {
        	completionStatus.setImage(new Image("/images/complete.png"));
        }
        else {
        	completionStatus.setImage(new Image("/images/uncomplete.png"));
        }
        id.setText(displayedIndex + ". ");    
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
