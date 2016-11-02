package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.alias.ReadOnlyAlias;
//@@author A0142184L
public class AliasCard extends UiPart{
	
	private static final String FXML = "AliasCard.fxml";

    @FXML
    private VBox cardPane;
    @FXML
    private Label commandPhrase;
    @FXML
    private Label commandAlias;
    @FXML
    private Label id;
    

    private ReadOnlyAlias alias;
    private int displayedIndex;

    public AliasCard(){

    }

    public static AliasCard load(ReadOnlyAlias alias, int displayedIndex){
    	AliasCard card = new AliasCard();
        card.alias = alias;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        id.setText(displayedIndex + ". ");
        commandPhrase.setText(alias.getOriginalPhrase());
        commandAlias.setText(alias.getAlias());
    }
    

    public VBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (VBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}