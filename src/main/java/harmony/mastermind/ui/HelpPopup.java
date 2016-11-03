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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//@@author A0139194X
public class HelpPopup extends UiPart {

    private static final String FXML = "HelpPopup.fxml";
    private final int DEFAULT_X_POS = 200;
    private final int DEFAULT_Y_POS = 100;
    private final int DEFAULT_HEIGHT = 800;
    private final int DEFAULT_WIDTH = 1000;
    private Popup popup;
    private TextArea content;
    private boolean isFirstKey;
    private TableView<String> table = new TableView<String>();

    //@@author A0139194X
    public HelpPopup() {
        initPopup();
        initTable();
        isFirstKey = true;
    }

    //@@author A0139194X
    public void show(Node node) {
        popup.show(node, DEFAULT_X_POS, DEFAULT_Y_POS);
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
        properties();

        popup.getContent().add(content);
        popup.addEventHandler(KeyEvent.KEY_RELEASED, keyEventHandler);

        content.setEditable(false);
    }
    
    @FXML
    private void initTable() {
        TableColumn<String, String> commandCol = new TableColumn<String, String>("Command");
        TableColumn<String, String> formatCol = new TableColumn<String, String>("Format");
        table.getColumns().setAll(commandCol, formatCol);
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
    
    //@@author A0143378Y
    public void properties() { 
        //Setting up the width and height
        content.setPrefHeight(DEFAULT_HEIGHT);
        content.setPrefWidth(DEFAULT_WIDTH);
        
        //Setting up wrapping of text in the content box 
        content.setWrapText(true);
        
        //Setting up the background, font and borders
        content.setStyle("-fx-background-color: #00BFFF;-fx-padding:10px;"
                + "-fx-text-fill: #000080;"+ "-fx-font-family: Consolas;"
                + "-fx-alignment: center"
                );

//        content.setStyle("-fx-font-family: sample; -fx-font-size: 20;");
    }
}
