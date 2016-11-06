package w15c2.tusk.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import w15c2.tusk.model.Alias;

//@@author A0139708W
/*
 * Card for alias list panel
*/
public class AliasCard extends UiPart{
        private static final String FXML = "AliasListCard.fxml";

        @FXML
        private HBox cardPane;
        @FXML
        private Label cardAlias;
        @FXML
        private Label command;
        @FXML
        private Label id;

        private Alias alias;
        private int displayedIndex;

        public AliasCard(){
        }
        /**
         * Loads alias information on to an alias card.
         *  
         * @param alias             Alias object to fill up card
         * @param displayedIndex    Index of card
         * @return                  Card with relevant alias info
         */
        public static AliasCard load(Alias alias, int displayedIndex){
            assert alias != null;
            assert displayedIndex >= 0;
            AliasCard card = new AliasCard();
            card.alias = alias;
            card.displayedIndex = displayedIndex;
            return UiPartLoader.loadUiPart(card);
        }
        
        @FXML
        public void initialize() {
            cardAlias.setText(alias.getShortcut());
            command.setText(alias.getSentence());
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
