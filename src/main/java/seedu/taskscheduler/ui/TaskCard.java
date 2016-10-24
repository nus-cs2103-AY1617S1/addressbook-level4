package seedu.taskscheduler.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.taskscheduler.model.task.ReadOnlyTask;


/**
 * Represents task card in Ui
 */
public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label tags;

    private ReadOnlyTask person;
    private int displayedIndex;

    public static final String COMPLETED_INDICATION = "-fx-background-color: #ccffcc;";
    public static final String OVERDUE_INDICATION = "-fx-background-color:  #ffcce6;";
    
    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask person, int displayedIndex){
        TaskCard card = new TaskCard();
        card.person = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(person.getName().fullName);
        hideFieldsAccordingToType(person);
        indicatingColourByCondition(person);
        id.setText(displayedIndex + ". ");
        address.setText(person.getLocation().value);
        phone.setText("Start Date: " + person.getStartDate().getDisplayString());
        email.setText("Due Date: " + person.getEndDate().getDisplayString());
        tags.setText(person.tagsString());
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

    //@@author A0148145E
    public void hideFieldsAccordingToType(ReadOnlyTask task) {
        
        if (task.tagsString().contains("Event")) {
        } else if (task.tagsString().contains("Deadline")) {
            phone.setVisible(false);
            address.setVisible(false);
        } else {
            phone.setVisible(false);
            address.setVisible(false);
            email.setVisible(false);
        }
    }

    //@@author A0148145E
    public void indicatingColourByCondition(ReadOnlyTask task) {
        
        if (task.tagsString().contains("Completed")) {
            // if task completed
            cardPane.setStyle(COMPLETED_INDICATION);
        } else if (task.getEndDate().getDate() != null && task.getEndDate().getDate().before(new Date())) {
            // if task overdue
            cardPane.setStyle(OVERDUE_INDICATION);
        }
        
    }
}
