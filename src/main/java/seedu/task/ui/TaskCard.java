package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart {

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label tags;
    @FXML
    private Label complete;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard() {

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getDescription().fullDescription);
        id.setText(displayedIndex + ". ");
        phone.setText("Priority: " + task.getPriority().toString());
        address.setText("Start Time: " + task.getTimeStart().toString());
        email.setText("End Time: "+ task.getTimeEnd().toString());
        tags.setText("Tags: " + task.tagsString());
        complete.setText(task.getCompleteStatus()? "  [Completed]": "  [Not Completed]");
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
