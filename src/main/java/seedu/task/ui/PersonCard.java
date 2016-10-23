package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.task.model.task.ReadOnlyTask;

public class PersonCard extends UiPart {

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

    private ReadOnlyTask person;
    private int displayedIndex;

    public PersonCard() {

    }

    public static PersonCard load(ReadOnlyTask person, int displayedIndex){
        PersonCard card = new PersonCard();
        card.person = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(person.getDescription().fullDescription);
        id.setText(displayedIndex + ". ");
        phone.setText("Priority: " + person.getPriority().toString());
        address.setText("Start Time: " + person.getTimeStart().toString());
        email.setText("End Time: "+ person.getTimeEnd().toString());
        tags.setText("Tags: " + person.tagsString());
        complete.setText(person.getCompleteStatus()? "  [Completed]": "  [Not Completed]");
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
