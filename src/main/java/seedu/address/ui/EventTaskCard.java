package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

public class EventTaskCard extends UiPart{

    private static final String FXML = "EventTaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label id;
    @FXML
    private Label taskType;
    @FXML
    private Label startDateAndTime;
    @FXML
    private Label endDateAndTime;

    private ReadOnlyTask task;
    private int displayedIndex;

    public EventTaskCard(){

    }

    public static EventTaskCard load(ReadOnlyTask task, int displayedIndex){
    	EventTaskCard card = new EventTaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        taskName.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        taskType.setText(task.getTaskType().toString());
        startDateAndTime.setText(task.getEndDate().toString());
        endDateAndTime.setText(task.getEndDate().toString());
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
// Note: translate V-box Y: -18
