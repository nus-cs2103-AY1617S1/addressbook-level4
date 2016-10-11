package seedu.manager.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.manager.model.activity.Activity;

public class ActivityCard extends UiPart{

    private static final String FXML = "ActivityListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
// TODO: re-instate tags or equivalent when implementation is complete    
//    @FXML
//    private Label tags;

    private Activity activity;
    private int displayedIndex;

    public ActivityCard(){

    }

    public static ActivityCard load(Activity activity, int displayedIndex){
        ActivityCard card = new ActivityCard();
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
