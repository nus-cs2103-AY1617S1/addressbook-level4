package seedu.todo.ui.components;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seedu.todo.commons.core.ConfigDefinition;

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
