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

//@@author A0139194X
public class HelpPopup extends UiPart {

    private static final String FXML = "HelpPopup.fxml";
    private Popup popup;
    private TextArea content;
    private boolean isFirstKey;

    //@@author A0139194X
    public HelpPopup() {
        initPopup();
        isFirstKey = true;
    }

    //@@author A0139194X
    public void show(Node node) {
        popup.show(node, 200, 100);
        popup.centerOnScreen();
    }

    //@@author A0139194X
    @Override
    public void setNode(Node node) {
    }

    //@@author A0139194X
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    //@@author A0139194X
    @FXML
    private void initPopup() {
        popup = new Popup();
        content = new TextArea();
        content.setPrefHeight(575);
        content.setPrefWidth(700);

        popup.getContent().add(content);
        popup.addEventHandler(KeyEvent.KEY_RELEASED, keyEventHandler);

        content.setEditable(false);
    }

    //@@author A0139194X
    @FXML
    EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            if (!isFirstKey && event.getCode() != null) {
                popup.hide();
            }
            isFirstKey = !isFirstKey;
        }
    };

    //@@author A0139194X
    public void setContent(String text) {
        content.setText(text);
    }
}
