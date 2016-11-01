//@@author A0142421X
package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import seedu.todo.model.tag.Tag;

public class TagCard extends UiPart{
	private static final String FXML = "TagCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label tags;
    @FXML
    private Text tasksCount;
    
    private Tag tag;
    private int numTasksText;
    private static int displayedIndex;
    
    public TagCard() {}
    
    public static TagCard load(Tag tag){
    	TagCard card = new TagCard();
        card.tag = tag;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
    
    public void initialize() {
        tags.setText(tag.getName());
        tasksCount.setText(tag.getCount() + "");
    }
    
    public HBox getLayout() {
        return cardPane;
    }
    
    @Override
    public void setNode(Node node) {
        cardPane = (HBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}