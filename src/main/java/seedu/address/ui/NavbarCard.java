package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NavbarCard extends UiPart{

    private static final String FXML = "NavbarCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;

    private String label;

    public NavbarCard() {}

    public static NavbarCard load(String label){
        NavbarCard card = new NavbarCard();
        card.label = label;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(label);
        
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