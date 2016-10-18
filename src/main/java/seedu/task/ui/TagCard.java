package seedu.task.ui;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.task.model.tag.Tag;

public class TagCard extends UiPart{

    private static final String FXML = "TagListCard.fxml";

    @FXML
    private VBox tagCardPane;
    @FXML
    private Label name;

    private Tag tag;

    public TagCard(){

    }

    public static TagCard load(Tag tag){
        TagCard card = new TagCard();
        card.tag = tag;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(tag.tagName);
    }

    public VBox getLayout() {
        return tagCardPane;
    }

    @Override
    public void setNode(Node node) {
        tagCardPane = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
