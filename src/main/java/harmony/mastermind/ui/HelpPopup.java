package harmony.mastermind.ui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Popup;


public class HelpPopup extends UiPart {

    private static final String FXML = "HelpPopup.fxml";
    private Popup popup;
    private TextArea content;
    
    public HelpPopup() {
        initPopup();
    }
    
    public void show(Node node) {
        popup.show(node, 250, 250);
        popup.centerOnScreen();
    }
    
    @Override
    public void setNode(Node node) {
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @FXML
    void initPopup() {
        popup = new Popup();
        
        content = new TextArea();
        
        content.setEditable(false);
        content.setText("HELLO");

        
        popup.getContent().add(content);
    }
}
