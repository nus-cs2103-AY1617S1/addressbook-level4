package seedu.ggist.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.events.ui.JumpToListRequestEvent;
import seedu.ggist.model.task.Priority;
import seedu.ggist.model.task.ReadOnlyTask;
//@@author A0144727B
public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;

    private ReadOnlyTask task;
    private int displayedIndex;
    
    private ColorPicker low = new ColorPicker(Color.web("#DEDE26"));
    private ColorPicker med = new ColorPicker(Color.web("#E69D0B"));
    private ColorPicker high = new ColorPicker(Color.web("D92121"));
    private ColorPicker none = new ColorPicker(Color.web("#663220"));
    private ColorPicker done = new ColorPicker(Color.web("#2eb211"));
    private ColorPicker overdue = new ColorPicker(Color.web("#ff0000"));

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
  //@@author A0138411N
    @FXML
    public void initialize() {
        taskName.setText(task.getTaskName().taskName);
        id.setText(displayedIndex + ". ");
        if (task.getStartDate().value.equals(Messages.MESSAGE_NO_START_DATE_SPECIFIED)) {
            startDate.setText("");
        } else {
            startDate.setText(task.getStartDate().value);
        }
        if (task.getStartTime().value.equals(Messages.MESSAGE_NO_START_TIME_SET)) {
            startTime.setText("");
        } else {
            startTime.setText(task.getStartTime().value);
        }
        if (task.getEndDate().value.equals(Messages.MESSAGE_NO_END_DATE_SPECIFIED)) {
            endDate.setText("");
        } else {
            endDate.setText(task.getEndDate().value);
        } 
        if (task.getEndTime().value.equals(Messages.MESSAGE_NO_END_TIME_SET)) {
            endTime.setText("");
        } else {
            endTime.setText(task.getEndTime().value);
        }
        if (task.getPriority().value.equals(Messages.MESSAGE_NO_PRIORITY_VALUE)) {
            id.textFillProperty().bind(none.valueProperty());
            taskName.textFillProperty().bind(none.valueProperty());
        } else {
            String level = task.getPriority().value;
            if (level.equals(Priority.PriorityType.LOW.toString())) {
                setLowPriorityTextColor();
            } else if (level.equals(Priority.PriorityType.MEDIUM.toString())) {
                setMedPriorityTextColor();
            } else if (level.equals(Priority.PriorityType.HIGH.toString())) {
                setHighPriorityTextColor();
            }
        }
        if (task.isDone()) {
            setDoneTextColor();
        }
        if (task.isOverdue()) {
            setOverdueTextColor();
        } else {
            setDefaultTextColor();
        }
    }
    
    /*changes task name to yellow*/
    private void setLowPriorityTextColor() {
        id.textFillProperty().bind(low.valueProperty());
        taskName.textFillProperty().bind(low.valueProperty());
    }
    
    /*changes task name to orange*/
    private void setMedPriorityTextColor() {
        id.textFillProperty().bind(med.valueProperty());
        taskName.textFillProperty().bind(med.valueProperty());
    }
    
    /*changes task name to red*/
    private void setHighPriorityTextColor() {
        id.textFillProperty().bind(high.valueProperty());
        taskName.textFillProperty().bind(high.valueProperty());
    }
    
    /*changes task name to green*/
    private void setDoneTextColor() {
        id.textFillProperty().bind(done.valueProperty());
        taskName.textFillProperty().bind(done.valueProperty());
    }
    
    /*Changes start and end date time to default color*/
    private void setDefaultTextColor() {
        startTime.textFillProperty().bind(none.valueProperty());
        endTime.textFillProperty().bind(none.valueProperty());
        startDate.textFillProperty().bind(none.valueProperty());
        endDate.textFillProperty().bind(none.valueProperty());
    }
    
    /*Changes start and end date time to red color */
    private void setOverdueTextColor() {
        startTime.textFillProperty().bind(overdue.valueProperty());
        endTime.textFillProperty().bind(overdue.valueProperty());
        startDate.textFillProperty().bind(overdue.valueProperty());
        endDate.textFillProperty().bind(overdue.valueProperty());
    }
    
//@@author A0138411N
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
    
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        
    }
}
