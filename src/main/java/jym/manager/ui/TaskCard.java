package jym.manager.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import jym.manager.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label deadline;
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
    	try{
	//    	System.out.println(task);
	        desc.setText(task.getDescription().toString());
	        id.setText(displayedIndex + ". ");
	        if(task.getLocation() != null) {
	        	address.setText(task.getLocation().toString());
	        }
	        if(task.getDate() != null) {
	        	deadline.setText("" + task.getDateString());
	        } else {
	        	deadline.setText("No deadline");
	        }
    	}catch(NullPointerException npe){
    		npe.printStackTrace();
    	}
 //       tags.setText(task.tagsString());
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
