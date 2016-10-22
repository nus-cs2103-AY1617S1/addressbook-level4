package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.menion.model.activity.ReadOnlyActivity;

//@@author A0139515A
public class FloatingTaskCard extends UiPart{

    private static final String FXML = "FloatingTaskCard.fxml";

    @FXML
    private HBox floatingTaskCardPane;
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
    //@@author

    public HBox getLayout() {
        return floatingTaskCardPane;
    }

    @Override
    public void setNode(Node node) {
        floatingTaskCardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}

