package seedu.cmdo.ui;

import java.time.LocalDate;
import java.time.LocalTime;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import seedu.cmdo.commons.events.ui.JumpToListRequestEvent;
import seedu.cmdo.model.task.ReadOnlyTask;

//@@author A0141006B
/**
 * Task Card displays the individual tasks in the list.
 * 
 * @@author A0141006B
 */
public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final String DEADLINE_SOON = "/images/deadlinesoon.png";
    private static final String CHILL = "/images/chillzone.png";
    private static final String OVERDUE = "/images/overduestatus.png";
    private static final String FLOATING = "/images/floating.png";
    
    //@FXML
    //private VBox cardPane;
    @FXML
    private HBox cardPane;
    @FXML
    private HBox firstLine;
    @FXML
    private HBox secondLine;
    @FXML
    private HBox thirdLine;
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
    	setStatus();    
    	setId();
    	setStartDateAndTime();
    	setDueDateAndTime();
    	setDetails();    	
    	setTags();
    	setPriority();                   
    }

    private void setPriority() {
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
	}

	private void setTags() {
    	 tags.setText(task.tagsString());
         tags.setTextFill(Color.MAROON);
	}

	private void setDetails() {
		detail.setText(task.getDetail().details);
    	detail.wrapTextProperty();
    	detail.setWrapText(true);
    }

	private void setId() {
        id.setText(displayedIndex + "");
	}

	private void setDueDateAndTime() {
    	  dbd.setText(task.getDueByDate().getFriendlyEndString());      
          //format due time
          if(task.getDueByTime().end.equals(LocalTime.MAX)) {
          	dbt.setText(task.getDueByTime().getFriendlyEndString());
          }
          else {
          	dbt.setText(", "+ task.getDueByTime().getFriendlyEndString());
          }
	}

	private void setStartDateAndTime() {
    	 sd.setText(task.getDueByDate().getFriendlyStartString());  		
    	  //format start time
         if(task.getDueByTime().start.equals(LocalTime.MAX)) {
         	st.setText(task.getDueByTime().getFriendlyStartString());
         }else {
         	st.setText(", " + task.getDueByTime().getFriendlyStartString());
         } 
	}

	private void setStatus() {
    	//Images for status
    	Image deadlineSoon = new Image(DEADLINE_SOON);
    	Image chill = new Image(CHILL);
    	Image overdue = new Image (OVERDUE);
    	Image floating = new Image (FLOATING);
    	
    	//status.setStrokeWidth(0.0);    	
    	status.setFill(Color.TRANSPARENT);
    	
    	if(task.getDueByDate().start.isBefore(LocalDate.now().plusDays(3)) 
    			&& task.getDueByDate().start.isAfter(LocalDate.now().plusDays(-1))) {
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
    	} else {
    		status.setFill(new ImagePattern(floating));
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
	}

	private void setFont(Label element, String font, int fontSize) {
    	element.setFont(new Font(font, fontSize));
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
    
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
    	System.out.println(displayedIndex + " / " + event.targetIndex);
    	if (displayedIndex == event.targetIndex) {
    		cardPane.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));;
    	}
    }
}
