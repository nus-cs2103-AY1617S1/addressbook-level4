package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.ReadOnlyPerson;

public class PersonCard extends UiPart{

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

    private Activity activity;
    private int displayedIndex;

    public PersonCard(){

    }

    public static PersonCard load(Activity activity, int displayedIndex){
        PersonCard card = new PersonCard();
        card.activity = activity;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(activity.name);
        id.setText(displayedIndex + ". ");
//        phone.setText(person.getPhone().value);
//        address.setText(person.getAddress().value);
//        email.setText(person.getEmail().value);
//        tags.setText(person.tagsString());
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
