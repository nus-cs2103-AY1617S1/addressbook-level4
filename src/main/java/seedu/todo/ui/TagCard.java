package seedu.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.todo.model.tag.Tag;

public class TagCard extends UiPart{
	private static final String FXML = "TagCard.fxml";

    @FXML
    private HBox cardPane;
    
    @FXML
    private Label tags;
    private Tag tag;
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