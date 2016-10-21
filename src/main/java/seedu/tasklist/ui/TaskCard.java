package seedu.tasklist.ui;

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
    private Label startDateTime;
    @FXML
    private Label endDateTime;
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
        initializeId();
        initializeTitle();
        initializeDateTime();
        initializeDescription();
        initializeTags();
        setBackgroundColor();
    }
    
    private void initializeId() {
        id.setText(displayedIndex + ". ");
    }
    
    private void initializeTitle() {
        name.setText(task.getTitle().fullTitle);
    }
    
    private void initializeDescription() {
        if (task.getDescription().description.equals("")) {
            description.setManaged(false);
        } else {
            description.setManaged(true);
            description.setText(task.getDescription().description);
        }
    }

    private void initializeDateTime() {
        if(!task.getStartDateTime().toString().isEmpty()) {
            startDateTime.setManaged(true);
            startDateTime.setText("Start:  " + task.getStartDateTime().toString().replaceAll(" ", "    Time:  "));
        } else {
            startDateTime.setManaged(false);
        }
        
        if(!task.getEndDateTime().toString().isEmpty()){
            endDateTime.setManaged(true);
            endDateTime.setText("End:    " + task.getEndDateTime().toString().replaceAll(" ", "    Time:  "));
        } else {
            endDateTime.setManaged(false);
        }
    }
    
    private void initializeTags() {
        tags.setText(task.tagsString());
    }
    
    public void setBackgroundColor() {
        if (task.isFloating()) {
            cardPane.setStyle("-fx-background-color: #FFFFCC;");    //pale yellow
        } else if (task.isCompleted()) {
            cardPane.setStyle("-fx-background-color: #98FB98;");    //pale green
        } else if (task.isOverdue()){
            cardPane.setStyle("-fx-background-color: #ff9999;");    //pale red
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
