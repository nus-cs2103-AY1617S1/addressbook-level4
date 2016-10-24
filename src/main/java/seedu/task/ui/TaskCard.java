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
    private Label id;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label deadlineLabel;
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
        id.setText(displayedIndex + ". ");
        if(task.getStartTime().value != "now" && task.getStartTime().value != " from now"){
        	startTimeLabel.setText(" from " + task.getStartTime().value);
        }else{
        	startTimeLabel.setText("");
        }
        if(task.getEndTime().value != "no endtime" && task.getEndTime().value != " to no endtime"){
        	endTimeLabel.setText(" to " + task.getEndTime().value);
        }else{
        	endTimeLabel.setText("");
        }
        if(task.getDeadline().value != "no deadline" && task.getDeadline().value != " to no deadline"){
        	deadlineLabel.setText(" ends " + task.getDeadline().value);
        }else{
        	deadlineLabel.setText("");
        }
        tags.setText(task.tagsString());
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
