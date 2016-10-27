package seedu.forgetmenot.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import seedu.forgetmenot.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    ImageView tickmark = new ImageView("/images/tick.png");
    ImageView recurringIcon = new ImageView("/images/recurring.png");
    ImageView floatingIcon = new ImageView("/images/floatingicon.png");
    ImageView crossicon = new ImageView("/images/cross.png");
    ImageView overdueicon = new ImageView("/images/overdue.png");
    @FXML
    private ImageView floating;
    @FXML
    private ImageView recurring;
    @FXML
    private ImageView stateicon;
    @FXML
    private ImageView overdue;
    @FXML
    private GridPane gridpane;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label address;
    @FXML
    private Label start;

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
    
    /**
     * @@author A0139211R
     */
    
    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        address.setText("End: " + task.getEndTime().easyReadDateFormatForUI());
        start.setText("Start: " + task.getStartTime().easyReadDateFormatForUI());
        if (task.getDone().getDoneValue() == true) {
        	stateicon.setImage(tickmark.getImage());
            cardPane.setStyle("-fx-background-color : #c1f0c1;");   
        }
        if (task.getRecurrence().getValue() == true) {
        	recurring.setImage(recurringIcon.getImage());
        }
        if (task.getStartTime().isMissing() 
				 && task.getEndTime().isMissing()) {
        	floating.setImage(floatingIcon.getImage());
        }
        if (task.checkOverdue() == true && task.getDone().getDoneValue() == false) {
        	overdue.setImage(overdueicon.getImage());
        	cardPane.setStyle("-fx-background-color : #ff7f7f");
        }
       

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
