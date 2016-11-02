package seedu.emeraldo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.emeraldo.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    //When changing the FXML labels, TaskCardHandle.java has to be updated also
    
    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label dateTime;
    @FXML
    private Label completedDateTime;
    @FXML 
    private Label overdueContext;
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
        description.setText(task.getDescription().fullDescription);
        id.setText(displayedIndex + ". ");
        
        if (task.getDateTime().completedValueDate == null)
        	dateTime.setText(task.getDateTime().toString());
        else 
        	completedDateTime.setText(task.getDateTime().toString());
        
        overdueContext.setText(task.getDateTime().getOverdueContext());
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
