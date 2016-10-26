package seedu.malitio.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

public class FloatingTaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label tags;

    private ReadOnlyFloatingTask task;
    private int displayedIndex;

    public FloatingTaskCard(){

    }

    public static FloatingTaskCard load(ReadOnlyFloatingTask task, int displayedIndex){
        FloatingTaskCard card = new FloatingTaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
    	if (task.getCompleted()){
    		name.setText(task.getName().fullName);
    		name.getStylesheets().addAll(getClass().getResource("/view/strikethrough.css").toExternalForm());
    	} else {
    		name.setText(task.getName().fullName);
    	}
    	
    	if (task.isMarked()) {
    	    cardPane.setStyle("-fx-background-color: yellow;");
    	}
        id.setText("F" + displayedIndex + ". ");
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
