package seedu.savvytasker.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.savvytasker.model.alias.AliasSymbol;

public class AliasSymbolCard extends UiPart{

    private static final String FXML = "AliasSymbolListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label aliasName;
    @FXML
    private Label id;
    @FXML
    private Label details;

    private AliasSymbol symbol;
    private int displayedIndex;

    public AliasSymbolCard(){

    }

    public static AliasSymbolCard load(AliasSymbol symbol, int displayedIndex){
        AliasSymbolCard card = new AliasSymbolCard();
        card.symbol = symbol;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        aliasName.setText(symbol.getKeyword());
        id.setText(displayedIndex + ". ");
        details.setText(symbol.getRepresentation());
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
