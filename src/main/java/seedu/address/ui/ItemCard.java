package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.item.ReadOnlyItem;
import seedu.address.model.person.ReadOnlyPerson;

public class ItemCard extends UiPart{

    private static final String FXML = "ItemListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;

    private ReadOnlyItem item;
    private int displayedIndex;

    public ItemCard(){

    }

    public static ItemCard load(ReadOnlyItem item, int displayedIndex){
        ItemCard card = new ItemCard();
        card.item = item;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
    	description.setText(item.getDescription().getFullDescription());
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
