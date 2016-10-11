package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.emeraldo.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    //When changing the FXML labels, PersonCardHandle.java has to be updated also
    
    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label dateTime;
    @FXML
    private Label tags;

    private ReadOnlyTask person;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.person = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        description.setText(person.getDescription().fullName);
        id.setText(displayedIndex + ". ");
        phone.setText(person.getPhone().value);
        dateTime.setText(person.getDateTime().value);
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
}
