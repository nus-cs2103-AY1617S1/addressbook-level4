package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NavbarCard extends UiPart{

    private static final String FXML = "NavbarCard.fxml";

    @FXML
    private HBox cardPaneNav;
    @FXML
    private Label navname;

    private String label;

    public NavbarCard() {}

    public static NavbarCard load(String label){
        NavbarCard card = new NavbarCard();
        card.label = label;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        navname.setText(label);
        
    }

    public HBox getLayout() {
        return cardPaneNav;
    }

    @Override
    public void setNode(Node node) {
        cardPaneNav = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}