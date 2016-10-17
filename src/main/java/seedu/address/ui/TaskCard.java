package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label detail;
    @FXML
    private Label id;
    @FXML
    private Label dbd;
    @FXML
    private Label dbt;
    @FXML
    private Label priority;
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
        detail.setText(task.getDetail().details);
        id.setText(displayedIndex + ". ");
        dbd.setText(task.getDueByDate().getFriendlyString());
        dbt.setText(task.getDueByTime().getFriendlyString());
        priority.setText(task.getPriority().value);        
        switch(task.getPriority().value) {
        	case "low": 
        		priority.setTextFill(Color.LAWNGREEN);
        		break;
        	case "medium":
        		priority.setTextFill(Color.GOLD);
        		break;
        	case "high":
        		priority.setTextFill(Color.RED);
        		break;
        }
        tags.setText(task.tagsString());
        tags.setTextFill(Color.MAROON);        
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
