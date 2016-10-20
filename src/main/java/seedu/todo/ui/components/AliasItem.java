package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.commons.core.AliasDefinition;
import seedu.todo.ui.UiPartLoader;

public class AliasItem extends MultiComponent {
    
    private static final String FXML_PATH = "components/AliasItem.fxml";
    
    // Props
    public AliasDefinition aliasDefinition;
    
    // FXML
    @FXML
    private Text aliasKey;
    @FXML
    private Text aliasValue;
    
    public static AliasItem load(Stage primaryStage, Pane placeholder) {
        return UiPartLoader.loadUiPart(primaryStage, placeholder, new AliasItem());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }
    
    @Override
    public void componentDidMount() {
        if (aliasDefinition != null) {
            aliasKey.setText(aliasDefinition.getAliasKey());
            aliasValue.setText(aliasDefinition.getAliasValue());
        }
    }

}
