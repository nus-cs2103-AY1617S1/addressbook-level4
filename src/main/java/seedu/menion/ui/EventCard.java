package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

public class EventCard extends UiPart{

    private static final String FXML = "EventCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label activityType;
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
    @FXML
    private Label status;
    
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
        activityType.setText(event.getActivityType().toString());
        name.setText(event.getActivityName().fullName);
        note.setText(event.getNote().toString());
        startDate.setText(event.getActivityStartDate().toString());
        startTime.setText(event.getActivityStartTime().toString()); 
        endDate.setText(event.getActivityEndDate().toString());
        endTime.setText(event.getActivityEndTime().toString());
        status.setText(event.getActivityStatus().toString());
        id.setText(displayedIndex + ". ");    
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

