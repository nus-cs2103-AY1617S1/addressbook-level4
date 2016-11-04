package seedu.simply.ui;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.simply.commons.events.ui.OverdueChangedEvent;
import seedu.simply.model.task.ReadOnlyTask;

//@@author A0147890U
public class EventCard extends UiPart {

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

    public EventCard() {

    }

    public static EventCard load(ReadOnlyTask event, int displayedIndex) {
        EventCard card = new EventCard();
        card.event = event;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        String startTime = twelveHourConvertor(event.getStart().value);
        String endTime = twelveHourConvertor(event.getEnd().value);

        name.setText(event.getName().taskDetails);
        id.setText("E" + displayedIndex + ". ");
        date.setText("Date:" + "            " + event.getDate().value);
        start.setText("Start time:" + "    " + startTime);
        end.setText("End time:" + "     " + endTime);
        tags.setText(event.tagsString());

        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleTaskOverdueChanged(OverdueChangedEvent change) {
        changeColor();
    }

    private void changeColor() {
        int overdueState = overdueChangeBorderColor(event, this.cardPane);
        setTextColor(overdueState);
    }

    private void setTextColor(int overdueState) {
        if (overdueState == 1) {
            setFxStyle("red");
        }

        if (overdueState == 2) {
            setFxStyle("#004402");
        }

        if (overdueState == 0) {
            name.setStyle(null);
            id.setStyle(null);
            date.setStyle(null);
            start.setStyle(null);
            end.setStyle(null);
            tags.setStyle(null);
        }
    }

    private void setFxStyle(String color) {
        name.setStyle("-fx-text-fill: " + color);
        id.setStyle("-fx-text-fill: " + color);
        date.setStyle("-fx-text-fill: " + color);
        start.setStyle("-fx-text-fill: " + color);
        end.setStyle("-fx-text-fill: " + color);
        tags.setStyle("-fx-text-fill: " + color);
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
