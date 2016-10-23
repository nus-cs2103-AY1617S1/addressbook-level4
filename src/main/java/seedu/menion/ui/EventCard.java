package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.menion.model.activity.ReadOnlyActivity;

//@@author A0139515A
public class EventCard extends UiPart{

    private static final String FXML = "EventCard.fxml";

    @FXML
    private HBox eventCardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label note;
    @FXML
    private Label startDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endDate;
    @FXML
    private Label endTime;
    
    private ReadOnlyActivity event;
    
    private int displayedIndex;

    public EventCard(){
        
    }

    public static EventCard load(ReadOnlyActivity event, int displayedIndex){
        EventCard card = new EventCard();
        card.event = event;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(event.getActivityName().fullName);
        note.setText(event.getNote().toString());
        startDate.setText(event.getActivityStartDate().toString());
        startTime.setText(event.getActivityStartTime().toString()); 
        endDate.setText(event.getActivityEndDate().toString());
        endTime.setText(event.getActivityEndTime().toString());
        id.setText(displayedIndex + ". ");    
    }
    //@@author
    
    public HBox getLayout() {
        return eventCardPane;
    }

    @Override
    public void setNode(Node node) {
        eventCardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}

