package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

public class PersonCard extends UiPart{

    private static final String FXML = "PersonListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startline;
    @FXML
    private Label deadlines;
    @FXML
    private Label priority;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public PersonCard(){

    }

    public static PersonCard load(ReadOnlyTask person, int displayedIndex){
        PersonCard card = new PersonCard();
        card.task = person;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        startline.setText("Start: " + task.getStartline().value);
        deadlines.setText("End: " + task.getDeadline().value);
        priority.setText("Priority: " + task.getPriority().value);
        tags.setText(task.tagsString());
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
