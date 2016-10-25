package seedu.agendum.ui;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.agendum.model.task.ReadOnlyTask;

//@@author A0148031R
public class CompletedTaskCard extends UiPart {
    
    private static final String FXML = "CompletedTaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    private ReadOnlyTask task;
    private int displayedIndex;

    public CompletedTaskCard(){

    }

    public static CompletedTaskCard load(ReadOnlyTask task, int displayedIndex){
        CompletedTaskCard card = new CompletedTaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
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
