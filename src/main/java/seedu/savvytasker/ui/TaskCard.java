//@@author A0138431L

package seedu.savvytasker.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import seedu.savvytasker.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    
    private static final String ICON = "/images/overdue.png";
	private static final Image OVERDUE_IMAGE = new Image(MainWindow.class.getResourceAsStream(ICON));
	
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
    @FXML
	private ImageView overdueIcon;

    private boolean isShowingIndex;
    private ReadOnlyTask task;
    private int displayedIndex;
    
    public TaskCard(boolean isShowingIndex){
        this.isShowingIndex = isShowingIndex;
    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex, boolean isShowingIndex){
        TaskCard card = new TaskCard(isShowingIndex);
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        
    	taskName.setText(task.getTaskName());
    	if (isShowingIndex) {
            id.setText(displayedIndex + ". ");
    	}
        details.setText(task.getTextForUi());
        setCardBackground();
		setOverdue();
		
    }

    public HBox getLayout() {
        return cardPane;
    }
    
    private void setOverdue() {
    	
    	Date today = new Date();
    	
		if (task.getEndDateTime() != null) {

			Date endDateTime = task.getEndDateTime();

			if (endDateTime.compareTo(today)<0 && task.isArchived() == false) {

				overdueIcon.setImage(OVERDUE_IMAGE);
			}
		}
    	
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
