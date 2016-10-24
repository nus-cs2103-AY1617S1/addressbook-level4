package harmony.mastermind.ui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Popup;

//@@author A0139149X
public class HelpPopup extends UiPart {

    private static final String FXML = "HelpPopup.fxml";
    private Popup popup;
    private TextArea content;
    private boolean isFirstKey;

    //@@author A0139149X
    public HelpPopup() {
        initPopup();
        isFirstKey = true;
    }

    //@@author A0139149X
    public void show(Node node) {
        popup.show(node, 200, 100);
        popup.centerOnScreen();
    }

    //@@author A0139149X
    @Override
    public void setNode(Node node) {
    }

    //@@author A0139149X
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    //@@author A0139149X
    @FXML
    private void initPopup() {
        popup = new Popup();
        content = new TextArea();
        properties();

        popup.getContent().add(content);
        popup.addEventHandler(KeyEvent.KEY_RELEASED, keyEventHandler);

        content.setEditable(false);
    }

    //@@author A0139149X
    @FXML
    EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            if (!isFirstKey && event.getCode() != null) {
                popup.hide();
            }
            isFirstKey = !isFirstKey;
        }
    };

    //@@author A0139149X
    public void setContent(String text) {
        content.setText(text);
    }
    
    //@@author A0143378Y
    public void properties() { 
        content.setPrefHeight(700);
        content.setPrefWidth(700);
        content.setWrapText(true);
        content.setStyle("-fx-background-color: #00BFFF;-fx-padding:10px;"
                + "-fx-text-fill: #000080;"+ "-fx-font-family: Consolas;"
                + "-fx-alignment: center"
                );

//        content.setStyle("-fx-font-family: sample; -fx-font-size: 20;");
    }
}
