package seedu.todolist.ui;

import java.io.File;

import com.google.common.base.Strings;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import seedu.todolist.commons.util.AppUtil;
import seedu.todolist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private ImageView intervalIcon;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private ImageView locationIcon;
    @FXML
    private Label locationParam; //location is a keyword
    @FXML
    private ImageView remarksIcon;
    @FXML
    private Label remarks;
    
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
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        formatIntervalField();
        formatLocationField();
        formatRemarksField();
    }
    
    private void formatIntervalField() {
        if (task.getInterval().isFloat()){
            intervalIcon.setVisible(false);
            startDate.setText(""); 
            endDate.setText("");  
        }
        else if (task.getInterval().isDeadlineWithTime()) {
            Image img = AppUtil.getImage("/images/alarm_clock.png");
            intervalIcon.setImage(img);
            startDate.setManaged(false);
            endDate.setText(task.getInterval().formatEndDateTime());
        }
        else if (task.getInterval().isDeadlineWithoutTime()) {
            Image img = AppUtil.getImage("/images/alarm_clock.png");
            intervalIcon.setImage(img);
            startDate.setManaged(false);
            endDate.setText(task.getInterval().formatEndDate());    
        }
        else {
            Image img = AppUtil.getImage("/images/hourglass.png");
            intervalIcon.setImage(img);
            startDate.setText(task.getInterval().formatStartDateTime()); 
            endDate.setText(task.getInterval().formatEndDateTime());   
        }
        
    }
    
    private void formatLocationField() {
        if (!Strings.isNullOrEmpty(task.getLocation().location)) {
            locationIcon.setVisible(true);
        }
        locationParam.setText(task.getLocation().toString());
    }
    
    private void formatRemarksField() {
        if (!Strings.isNullOrEmpty(task.getRemarks().remarks)) {
            remarksIcon.setVisible(true);
        }
        remarks.setText(task.getRemarks().toString());
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
