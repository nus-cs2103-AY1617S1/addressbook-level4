package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.commons.util.DateFormatter;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label tags;

    private ReadOnlyTask person;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask person, int displayedIndex){
        TaskCard card = new TaskCard();
        card.person = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(person.getName().fullName);
        hideFieldsAccordingToType(person);
        id.setText(displayedIndex + ". ");
        address.setText(person.getLocation().value);
        phone.setText("Start Date: " + person.getStartDate().getDisplayString());
        email.setText("Due Date: " + person.getEndDate().getDisplayString());
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
    
    public void hideFieldsAccordingToType(ReadOnlyTask person) {
        if (person.tagsString().contains("Event")) {
        } else if (person.tagsString().contains("Reminder")) {
            phone.setVisible(false);
            address.setVisible(false);
        } else {
            phone.setVisible(false);
            address.setVisible(false);
            email.setVisible(false);
        }
    }
}
