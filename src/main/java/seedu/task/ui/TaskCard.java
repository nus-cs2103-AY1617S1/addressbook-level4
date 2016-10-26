package seedu.task.ui;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private Label tags;

    private static ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        TaskCard.task = task;
        card.displayedIndex = displayedIndex;
        
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        if(!task.getDeadline().value.equals("now") && !task.getDeadline().value.equals(" from now")){
        	startTimeLabel.setText(" from " + task.getStartTime().value);
        }else{
        	startTimeLabel.setText("");
        }
        if(!task.getEndTime().value.equals("no endtime") && !task.getEndTime().value.equals(" to no endtime")){
        	endTimeLabel.setText(" to " + task.getEndTime().value);
        }else{
        	endTimeLabel.setText("");
        }
        if(!task.getDeadline().value.equals("no deadline") && !task.getDeadline().value.equals(" to no deadline")){
        	deadlineLabel.setText(" ends " + task.getDeadline().value);
        }else{
        	deadlineLabel.setText("");
        }
        tags.setText(task.tagsString());
    }

    public HBox getLayout() {
        if(task.getStatus().getNewlyAddedStatus() == true) {
            
            cardPane.setStyle("-fx-background-color: #FFFE00");
            //cardPane.setStyle("-fx-background-color: #FFFE00");
            
                    
                   
           
            
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    cardPane.setStyle("-fx-background-color: #FFFFFF");
                    task.getStatus().setNewlyAdded(false);
                }
            });
            delay.play();
            
            //
        }
        if(task.getStatus().getDoneStatus() == true) {
            cardPane.setStyle("-fx-background-color: #ADDBAC");
        }
        return cardPane;
    }

    public static boolean isAdded(){
        return task.getStatus().getNewlyAddedStatus();
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
