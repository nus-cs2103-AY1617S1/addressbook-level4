package w15c2.tusk.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import w15c2.tusk.model.Alias;

//@@author A0139708W
/**
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
        @FXML
        private VBox colorTag;

        private Alias alias;
        private int displayedIndex;
        
        private static final String CARDPANE_CSS = "-fx-background-color: rgba(211, 174, 141, 0.5);";
        private static final String CARD_ALIAS_CSS = "-fx-text-fill: rgba(247, 246, 239, 0.7);";
        private static final String ID_CSS = "-fx-text-fill: rgba(244, 244, 244, 1.0);";
        private static final String COMMAND_CSS = ID_CSS;
        private static final String  COLORTAG_CSS = "-fx-background-color: rgb(186, 143, 106)";
        
        /**
         * Empty constructor for AliasCard 
         * to use during load method.
         */
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
        
        /**
         * Assigning various Alias parameters
         * to AliasCard.
         */
        @FXML
        public void initialize() {
            cardAlias.setText(alias.getShortcut());
            command.setText(alias.getSentence());
            id.setText(displayedIndex + ". ");
            
            cardPane.setStyle(CARDPANE_CSS);
        	cardAlias.setStyle(CARD_ALIAS_CSS);
        	command.setStyle(COMMAND_CSS);
        	id.setStyle(ID_CSS);
        	colorTag.setStyle(COLORTAG_CSS);
        }
        
        /**
         * Returns pane containing AliasCard
         * for AliasViewCell.
         * 
         * @return  Pane containing AliasCard.
         */
        public HBox getLayout() {
            return cardPane;
        }
        
        /**
         * Sets pane containing AliasCard
         * to appropriate node, used in 
         * UiPart.
         */
        @Override
        public void setNode(Node node) {
            cardPane = (HBox)node;
        }
        
        /**
         * Return FXML file name for UiPart loading.
         * 
         * @return  String representation of FXML file name.
         */
        @Override
        public String getFxmlPath() {
            return FXML;
        }
}
