package seedu.cmdo.ui;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import seedu.cmdo.model.task.ReadOnlyTask;

/**
 * Task Card displays the individual tasks in the list.
 * 
 * @author A0141006B
 */

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final String DEADLINE_SOON = "/images/deadlinesoon.png";
    private static final String CHILL = "/images/chillzone.png";
    private static final String OVERDUE = "/images/overduestatus.png";
    
    @FXML
    private HBox cardPane;
    @FXML
    private Label detail;
    @FXML
    private Rectangle status;
    @FXML
    private Label id;
    @FXML
    private Label st;
    @FXML
    private Label sd;
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
    	//Images for status
    	Image deadlineSoon = new Image(DEADLINE_SOON);
    	Image chill = new Image(CHILL);
    	Image overdue = new Image (OVERDUE);
    	
    	status.setStrokeWidth(0.0);
    	
    	status.setFill(Color.TRANSPARENT);
    	if(task.getDueByDate().start.isBefore(LocalDate.now().plusDays(3)) 
    			&& task.getDueByDate().start.isAfter(LocalDate.now())) {
    		status.setFill(new ImagePattern(deadlineSoon));
    	} else if(task.getDueByDate().start.isBefore(LocalDate.now().plusDays(9999)) 
    			&& task.getDueByDate().start.isAfter(LocalDate.now())) {
    		status.setFill(new ImagePattern(chill));
    	}else if(task.getDueByDate().start.isBefore(LocalDate.now())
    			&& task.getDueByDate().start.isAfter(LocalDate.MIN.plusDays(1))) {
    		status.setFill(new ImagePattern(overdue));
    		id.setTextFill(Color.RED);
    		detail.setTextFill(Color.RED);
    		sd.setTextFill(Color.RED);
    		st.setTextFill(Color.RED);
    		dbd.setTextFill(Color.RED);
    		dbt.setTextFill(Color.RED);
    	}
    	
    	// Done all green
    	if(task.checkDone().value) {
    		status.setFill(Color.GREEN);
    		id.setTextFill(Color.BLACK);
    		detail.setTextFill(Color.BLACK);
    		sd.setTextFill(Color.BLACK);
    		st.setTextFill(Color.BLACK);
    		dbd.setTextFill(Color.BLACK);
    		dbt.setTextFill(Color.BLACK);
    	} 
    	// Not done, check for
    	// Floating tasks
    	else if (task.getDueByDate().start == LocalDate.MIN) {
	    	status.setFill(Color.TRANSPARENT);
    	}

        id.setText(displayedIndex + ". ");
    	detail.setText(task.getDetail().details);    		
        sd.setText(task.getDueByDate().getFriendlyStartString());                    
        st.setText(task.getDueByTime().getFriendlyStartString());
        dbd.setText(task.getDueByDate().getFriendlyEndString());        	
        dbt.setText(task.getDueByTime().getFriendlyEndString());
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
