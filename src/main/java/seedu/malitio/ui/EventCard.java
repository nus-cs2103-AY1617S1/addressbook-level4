package seedu.malitio.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.malitio.model.task.ReadOnlyEvent;


public class EventCard extends UiPart{

    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane3;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label tags;

    private ReadOnlyEvent event;
    private int displayedIndex;

    public EventCard(){

    }

    public static EventCard load(ReadOnlyEvent event, int displayedIndex){
        EventCard card = new EventCard();
        card.event = event;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(event.getName().fullName);
        id.setText("E" + displayedIndex + ". ");
        start.setText("Start: " + event.getStart().toString());
        end.setText("End: " + event.getEnd().toString());
        tags.setText(event.tagsString());
        
        if (event.isMarked()) {
            cardPane3.setStyle("-fx-background-color: yellow;");
        } else {
            cardPane3.setStyle("-fx-background-color: white;");
        }
    }

    public HBox getLayout() {
        return cardPane3;
    }

    @Override
    public void setNode(Node node) {
        cardPane3 = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
