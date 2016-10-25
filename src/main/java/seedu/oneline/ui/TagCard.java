package seedu.oneline.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.oneline.model.tag.Tag;

public class TagCard extends UiPart{

    private static final String FXML = "TagListCard.fxml";

    @FXML
    private HBox tagCardPane;
    @FXML
    private Label name;    
    @FXML 
    private Label duetoday; 
    
    private Tag tag;
    private String colour; 
    
    public TagCard() {

    }

    public static TagCard load(Tag tag){
        TagCard card = new TagCard();
        card.tag = tag;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText("#" + tag.tagName);
        tagCardPane.setStyle("-fx-background-color: " + colour);
    }

    public HBox getLayout() {
        return tagCardPane;
    }

    @Override
    public void setNode(Node node) {
        tagCardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
