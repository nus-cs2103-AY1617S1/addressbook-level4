package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.event.ReadOnlyEvent;

public class PersonCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label line1;
    @FXML
    private Label line2;
    @FXML
    private Label reminder;
    @FXML
    private Label tags;
    @FXML
    private Label completion;
    
    private ReadOnlyActivity person;
    private int displayedIndex;

    public PersonCard(){

    }

    public static PersonCard load(ReadOnlyActivity person2, int displayedIndex){
        PersonCard card = new PersonCard();
        card.person = person2;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        
        
        name.setText(person.getName().fullName);
        id.setText(displayedIndex + ". ");
        
        if(person.getClass().getSimpleName().equalsIgnoreCase("task")) {
            line1.setText(((ReadOnlyTask) person).getDueDate().forDisplay());
            line2.setText(((ReadOnlyTask) person).getPriority().forDisplay());  
        
        } else if(person.getClass().getSimpleName().equalsIgnoreCase("event")) {
            line1.setText(((ReadOnlyEvent) person).getStartTime().forDisplay());
            line2.setText(((ReadOnlyEvent) person).getEndTime().forDisplay());   
        } else {
            line1.setText("");
            line2.setText("");
        }

        reminder.setText(person.getReminder().forDisplay());
        
        tags.setText(person.tagsString());
        completion.setText(person.toStringCompletionStatus());
        if(person.getCompletionStatus() == true) {
        	cardPane.setStyle("-fx-background-color: springgreen;");
        } else if(person.passedDueDate()){
            cardPane.setStyle("-fx-background-color: red;");
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
