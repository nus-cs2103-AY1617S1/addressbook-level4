package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.task.model.task.ReadOnlyTask;
import java.util.Date;
import java.text.*;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label startDate;
    @FXML
    private Label dueDate;
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
    	title.setText(task.getTitle().fullTitle);
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().fullDescription);
        startDate.setText("Start Date: " + isDateNull(task.getStartDate().startDate));
        dueDate.setText("Due Date: " + isDateNull(task.getDueDate().dueDate));
        status.setText(task.getStatus().status.toString());
        tags.setText(task.tagsString());
    }
    
    public String isDateNull(Date inputDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd-MM-yyyy HH:mm");
        return inputDate == null ? "Not Set" : dateFormat.format(inputDate);
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
