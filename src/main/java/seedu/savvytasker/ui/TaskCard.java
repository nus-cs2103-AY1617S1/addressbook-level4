package seedu.savvytasker.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.savvytasker.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final String EMPTY_FIELD = " - ";
    
    public static final String LOW_PRIORITY_BACKGROUND = "-fx-background-color:#CEFFDC";
    public static final String MEDIUM_PRIORITY_BACKGROUND = "-fx-background-color:#FFFED8";
    public static final String HIGH_PRIORITY_BACKGROUND = "-fx-background-color:#FF8180";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label details;

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
        taskName.setText(task.getTaskName());
        id.setText(displayedIndex + ". ");
        details.setText(task.getTextForUi());
        setCardBackground();
    }

    public HBox getLayout() {
        return cardPane;
    }
    
    private void setCardBackground() {
    	
        if (task.getPriority().toString().equals("High")) {
        	
            cardPane.setStyle(HIGH_PRIORITY_BACKGROUND);
        
        } else if (task.getPriority().toString().equals("Medium"))  {
         
        	cardPane.setStyle(MEDIUM_PRIORITY_BACKGROUND);
       
        } else if (task.getPriority().toString().equals("Low"))  {
        
        	cardPane.setStyle(LOW_PRIORITY_BACKGROUND);
      
        }
        
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
