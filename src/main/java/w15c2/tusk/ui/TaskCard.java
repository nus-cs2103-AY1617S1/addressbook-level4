package w15c2.tusk.ui;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.Task;

public class TaskCard extends UiPart{
    
    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label firstDate;
    @FXML
    private Label secondDate;
    @FXML
    private VBox colorTag;

    private Task task;
    private int displayedIndex;
    
    private static final String OVERDUE_CARDPANE_CSS = "-fx-background-color: rgba(214, 14, 14, 0.85);";
    private static final String OVERDUE_SECONDDATE_CSS = "-fx-text-fill: rgba(247, 246, 239, 0.7);";
    private static final String OVERDUE_FIRSTDATE_CSS = OVERDUE_SECONDDATE_CSS;
    private static final String OVERDUE_ID_CSS = "-fx-text-fill: rgba(244, 244, 244, 1.0);";
    private static final String OVERDUE_DESCRIPTION_CSS = OVERDUE_ID_CSS;
    private static final String OVERDUE_COLORTAG_CSS = "-fx-background-color: #DC143C";
    
    private static final String FAVORITE_CARDPANE_CSS = "-fx-background-color: rgba(255, 255, 9, 0.75);";
    private static final String FAVORITE_SECONDDATE_CSS = "-fx-text-fill: rgba(0, 0, 0, 0.7);";
    private static final String FAVORITE_FIRSTDATE_CSS = FAVORITE_SECONDDATE_CSS;
    private static final String FAVORITE_ID_CSS = "-fx-text-fill: rgba(0, 102, 0, 1.0);";
    private static final String FAVORITE_DESCRIPTION_CSS = FAVORITE_ID_CSS;
    private static final String FAVORITE_COLORTAG_CSS = "-fx-background-color: rgb(242, 232, 121)";
    
    private static final String COMPLETED_CARDPANE_CSS = "-fx-background-color: rgba(129, 224, 74, 1.0);";
    private static final String COMPLETED_SECONDDATE_CSS = "-fx-text-fill: white;";
    private static final String COMPLETED_FIRSTDATE_CSS = COMPLETED_SECONDDATE_CSS;
    private static final String COMPLETED_ID_CSS = "-fx-text-fill: white;";
    private static final String COMPLETED_DESCRIPTION_CSS = COMPLETED_ID_CSS;
    private static final String COMPLETED_COLORTAG_CSS = "-fx-background-color: rgb(153, 247, 98)";
    
    private static final String NORMAL_CARDPANE_CSS = "-fx-background-color: rgba(211, 174, 141, 0.5);";
    private static final String NORMAL_SECONDDATE_CSS = "-fx-text-fill: rgba(247, 246, 239, 0.7);";
    private static final String NORMAL_FIRSTDATE_CSS = NORMAL_SECONDDATE_CSS;
    private static final String NORMAL_ID_CSS = "-fx-text-fill: rgba(244, 244, 244, 1.0);";
    private static final String NORMAL_DESCRIPTION_CSS = NORMAL_ID_CSS;
    private static final String NORMAL_COLORTAG_CSS = "-fx-background-color: rgb(186, 143, 106)";
    
    private static final String FXML = "TaskListCard.fxml";
    
    public TaskCard(){
    }

    public static TaskCard load(Task task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        description.setText(task.getDescription().getContent());
        id.setText(displayedIndex + ". ");
        
        // Format to display the dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, h.mm a");
        
        // Assigning Date labels only if task is DeadlineTask or EventTask
        if (task instanceof DeadlineTask) {
        	DeadlineTask curr = (DeadlineTask) task;
        	firstDate.setText(dateFormat.format(curr.getDeadline()));
        	secondDate.setText("");
        } else if (task instanceof EventTask) {
        	EventTask curr = (EventTask) task;
        	firstDate.setText(dateFormat.format(curr.getStartDate()));
        	secondDate.setText(dateFormat.format(curr.getEndDate()));
        } else {
        	firstDate.setText("");
        	secondDate.setText("");
        }
    }
    
    // Set the colors for completed tasks
    public void setCompletedStyle() {
        cardPane.setStyle(COMPLETED_CARDPANE_CSS);
    	description.setStyle(COMPLETED_DESCRIPTION_CSS);
    	id.setStyle(COMPLETED_ID_CSS);
    	firstDate.setStyle(COMPLETED_FIRSTDATE_CSS);
    	secondDate.setStyle(COMPLETED_SECONDDATE_CSS);
    	colorTag.setStyle(COMPLETED_COLORTAG_CSS);
    }
    
    // Set the colors for overdue tasks
    public void setOverdueStyle() {
        cardPane.setStyle(OVERDUE_CARDPANE_CSS);
    	description.setStyle(OVERDUE_DESCRIPTION_CSS);
    	id.setStyle(OVERDUE_ID_CSS);
    	firstDate.setStyle(OVERDUE_FIRSTDATE_CSS);
    	secondDate.setStyle(OVERDUE_SECONDDATE_CSS);
    	colorTag.setStyle(OVERDUE_COLORTAG_CSS);
    }
    
    // Set the colors for pinned tasks
    public void setPinnedStyle() {
        cardPane.setStyle(FAVORITE_CARDPANE_CSS);
    	description.setStyle(FAVORITE_DESCRIPTION_CSS);
    	id.setStyle(FAVORITE_ID_CSS);
    	firstDate.setStyle(FAVORITE_FIRSTDATE_CSS);
    	secondDate.setStyle(FAVORITE_SECONDDATE_CSS);
    	colorTag.setStyle(FAVORITE_COLORTAG_CSS);
    }
    
    // Set the colors for normal tasks
    public void setNormalStyle() {
        cardPane.setStyle(NORMAL_CARDPANE_CSS);
    	description.setStyle(NORMAL_DESCRIPTION_CSS);
    	id.setStyle(NORMAL_ID_CSS);
    	firstDate.setStyle(NORMAL_FIRSTDATE_CSS);
    	secondDate.setStyle(NORMAL_SECONDDATE_CSS);
    	colorTag.setStyle(NORMAL_COLORTAG_CSS);
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
