package seedu.tasklist.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import seedu.tasklist.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final String COMPLETED_ICON_URL = 
    		TaskCard.class.getResource("/images/icon_checkmark.png").toExternalForm();
    private static final String OVERDUE_ICON_URL = 
    		TaskCard.class.getResource("/images/icon_exclamation.png").toExternalForm();
    private static final String INCOMPLETE_ICON_URL = 
    		TaskCard.class.getResource("/images/icon_incomplete.png").toExternalForm();

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label priority;
    @FXML
    private Label recurring;
    @FXML
    private ImageView statusButton;

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
        name.setText(task.getTaskDetails().taskDetails);
        id.setText(displayedIndex + ". ");
        startTime.setText(task.getStartTime().toCardString());
        //priority.setText(task.getPriority().toString());
        if (task.getPriority().toString().equals("high"))
            priority.setGraphic(new ImageView(new Image("/images/three_stars.png")));
        else if (task.getPriority().toString().equals("med"))
            priority.setGraphic(new ImageView(new Image("/images/two_stars.png")));
        else priority.setGraphic(new ImageView(new Image("/images/one_star.png")));
        priority.setText("");
        recurring.setText("");
        if (task.getRecurringFrequency().equals("daily"))
            recurring.setGraphic(new ImageView(new Image("/images/daily_rec.png")));
        else if (task.getRecurringFrequency().equals("weekly"))
            recurring.setGraphic(new ImageView(new Image("/images/weekly_rec.png")));
        else if (task.getRecurringFrequency().equals("monthly"))
            recurring.setGraphic(new ImageView(new Image("/images/monthly_rec.png")));
        else if (task.getRecurringFrequency().equals("yearly"))
            recurring.setGraphic(new ImageView(new Image("/images/yearly_rec.png")));
        endTime.setText(task.getEndTime().toCardString());
        setColour();
        statusButton.setVisible(true);
        setStatusButtonColour();
        //tags.setText(task.tagsString());
    }

    public HBox getLayout() {
        return cardPane;
    }
    
    private void setColour(){
        if(task.isComplete()){
        	cardPane.setStyle("-fx-background-color: #C0FFC0;");
        }
        else if(task.isOverDue()){
            cardPane.setStyle("-fx-background-color: #FFC0C0;");
        }
        else {
        	cardPane.setStyle("-fx-background-color: #FFFFFF;");
        }
    }

    public void setStatusButtonColour() {
    	if(task.isComplete()){
    		statusButton.setImage(new Image(COMPLETED_ICON_URL));
    	}
    	else if(task.isOverDue()){
    		statusButton.setImage(new Image(OVERDUE_ICON_URL));
    	}
    	else{
    		statusButton.setImage(new Image(INCOMPLETE_ICON_URL));
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
