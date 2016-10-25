package seedu.malitio.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.malitio.model.task.ReadOnlyDeadline;


public class DeadlineCard extends UiPart{
    

    private static final String FXML = "DeadlineListCard.fxml";

    @FXML
    private HBox cardPane2;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label due;

    @FXML
    private Label tags;

    private ReadOnlyDeadline deadline;
    private int displayedIndex;

    //@@author A0129595N
    public DeadlineCard(){

    }

    public static DeadlineCard load(ReadOnlyDeadline deadline, int displayedIndex){
        DeadlineCard card = new DeadlineCard();
        card.deadline = deadline;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
    	if (deadline.getCompleted()){
    		name.setText(deadline.getName().fullName);
    		name.getStylesheets().addAll(getClass().getResource("/view/strikethrough.css").toExternalForm());
    	} else {
    		name.setText(deadline.getName().fullName);
    	}
        id.setText("D"+displayedIndex + ". ");
        due.setText("Due: "+ deadline.getDue().toString());
        tags.setText(deadline.tagsString());
    }

    public HBox getLayout() {
        return cardPane2;
    }

    @Override
    public void setNode(Node node) {
        cardPane2 = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
