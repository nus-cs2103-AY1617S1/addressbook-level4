package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView completionStatus;
    
    private ReadOnlyActivity floatingTask;
    
    private int displayedIndex;

    public FloatingTaskCard(){
        
    }

    public static FloatingTaskCard load(ReadOnlyActivity floatingTask, int displayedIndex){
        FloatingTaskCard card = new FloatingTaskCard();
        card.floatingTask = floatingTask;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(floatingTask.getActivityName().fullName);
        note.setText(floatingTask.getNote().toString());
        if (floatingTask.getActivityStatus().toString().equals("Completed")) {
        	completionStatus.setImage(new Image("/images/complete.png"));
        }
        else {
        	completionStatus.setImage(new Image("/images/uncomplete.png"));
        }
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

