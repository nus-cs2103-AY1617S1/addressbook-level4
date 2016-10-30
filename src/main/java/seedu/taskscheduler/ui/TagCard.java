package seedu.taskscheduler.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.taskscheduler.model.tag.Tag;

//@@author A0148145E
/**
 * Represents task card in Ui
 */
public class TagCard extends UiPart{

    private static final String FXML = "TagListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    
    private Tag tag;

    public static TagCard load(Tag tag, int displayedIndex){
        
        TagCard card = new TagCard();
        card.tag = tag;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(tag.tagName);
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
