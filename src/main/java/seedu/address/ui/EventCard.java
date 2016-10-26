package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0147890U
public class EventCard extends UiPart{

    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label tags;

    private ReadOnlyTask event;
    private int displayedIndex;

    public EventCard(){

    }

    public static EventCard load(ReadOnlyTask event, int displayedIndex){
        EventCard card = new EventCard();
        card.event = event;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(event.getName().taskDetails);
        id.setText("E" + displayedIndex + ". ");
        date.setText("Date:" + "    " + event.getDate().value);
        start.setText("Start time:" + "    " + event.getStart().value);
        end.setText("End time:" + "    " + event.getEnd().value);
        tags.setText(event.tagsString());
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
