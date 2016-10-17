package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;

public class DeadlineCard extends UiPart {
    
    private static final String FXML = "DeadlineListCard.fxml";
    
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
    
    private ReadOnlyTask deadline;
    private int displayedIndex;
    
    public DeadlineCard() {
        
    }
    
    public static DeadlineCard load(ReadOnlyTask deadline, int displayedIndex) {
        DeadlineCard card = new DeadlineCard();
        card.deadline = deadline;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
    
    @FXML
    public void initialize() {
        name.setText(deadline.getName().taskDetails);
        id.setText("D" + displayedIndex + ". ");
        phone.setText(deadline.getDate().value);
        address.setText(deadline.getStart().value);
        email.setText(deadline.getEnd().value);
        tags.setText(deadline.tagsString());
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
