package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.task.model.item.ReadOnlyEvent;

public class EventCard extends UiPart {
    private static final String FXML = "EventListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label index;
    @FXML
    private Label description;
    @FXML
    private Label duration;
    

    private ReadOnlyEvent event;
    private int displayedIndex;


    public static EventCard load(ReadOnlyEvent event, int displayedIndex){
        EventCard card = new EventCard();
        card.event = event;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    //@@author-A0127570H
    @FXML
    public void initialize() {
        name.setText(event.getNameWithStatus());
        index.setText(displayedIndex + ". ");
        description.setText(event.getDescriptionValue());
        duration.setText(event.getDuration().toString());
        setCompletionBackgroundText();
    }

    //@@author-A0127570H
    //Adds the lavender colour to the background if the task status is completed
    private void setCompletionBackgroundText() {
        if (event.isEventCompleted()) {
            cardPane.getStyleClass().add("status-complete");
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
