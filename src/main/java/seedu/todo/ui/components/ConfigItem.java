package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.commons.core.ConfigDefinition;
import seedu.todo.ui.UiPartLoader;

public class ConfigItem extends MultiComponent {

    private static final String FXML_PATH = "components/ConfigItem.fxml";
    
    // Props
    public ConfigDefinition configDefinition;
    
    // FXML
    @FXML
    private Text configDescription;
    @FXML
    private Text configName;
    @FXML
    private Text configValue;
    
    public static ConfigItem load(Stage primaryStage, Pane placeholder) {
        return UiPartLoader.loadUiPart(primaryStage, placeholder, new ConfigItem());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }
    
    @Override
    public void componentDidMount() {
        if (configDefinition != null) {
            configDescription.setText(configDefinition.getConfigDescription());
            configName.setText(configDefinition.getConfigName());
            configValue.setText(configDefinition.getConfigValue());
        }
    }

}
