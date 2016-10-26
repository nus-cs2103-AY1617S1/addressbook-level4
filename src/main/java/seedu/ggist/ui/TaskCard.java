package seedu.ggist.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import seedu.ggist.commons.core.Messages;
import seedu.ggist.model.task.ReadOnlyTask;

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
    
    private ColorPicker low = new ColorPicker(Color.web("#ffa900"));
    private ColorPicker med = new ColorPicker(Color.web("#ff5c00"));
    private ColorPicker high = new ColorPicker(Color.web("#e80549"));
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
            if (level.equals("low")) {
                id.textFillProperty().bind(low.valueProperty());
                taskName.textFillProperty().bind(low.valueProperty());
            } else if (level.equals("med")) {
                id.textFillProperty().bind(med.valueProperty());
                taskName.textFillProperty().bind(med.valueProperty());
            } else if (level.equals("high")) {
                id.textFillProperty().bind(high.valueProperty());
                taskName.textFillProperty().bind(high.valueProperty());
            }
        }
        if (task.isDone()) {
            id.textFillProperty().bind(done.valueProperty());
            taskName.textFillProperty().bind(done.valueProperty());
        }
        if (task.isOverdue()) {
            startTime.textFillProperty().bind(overdue.valueProperty());
            endTime.textFillProperty().bind(overdue.valueProperty());
            startDate.textFillProperty().bind(overdue.valueProperty());
            endDate.textFillProperty().bind(overdue.valueProperty());
        } else {
            startTime.textFillProperty().bind(none.valueProperty());
            endTime.textFillProperty().bind(none.valueProperty());
            startDate.textFillProperty().bind(none.valueProperty());
            endDate.textFillProperty().bind(none.valueProperty());
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
