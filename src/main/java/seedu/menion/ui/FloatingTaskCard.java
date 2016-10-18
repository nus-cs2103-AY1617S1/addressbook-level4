package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

public class FloatingTaskCard extends UiPart{

    private static final String FXML = "FloatingTaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label note;
    @FXML
    private Label status;
    
    private ReadOnlyActivity floatingTask;
    
    private int displayedIndex;

    public FloatingTaskCard(){
        
    }

    public static FloatingTaskCard load(ReadOnlyActivity task, int displayedIndex){
        FloatingTaskCard card = new FloatingTaskCard();
        card.floatingTask = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(floatingTask.getActivityName().fullName);
        note.setText(floatingTask.getNote().toString());
        status.setText(floatingTask.getActivityStatus().toString());
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

