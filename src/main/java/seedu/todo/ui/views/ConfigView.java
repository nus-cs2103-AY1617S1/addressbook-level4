package seedu.todo.ui.views;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.todo.MainApp;
import seedu.todo.commons.core.ConfigDefinition;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.ui.UiPartLoader;
import seedu.todo.ui.components.ConfigItem;

public class ConfigView extends View {

    private static final String FXML_PATH = "views/ConfigView.fxml";

    private static final String ICON_PATH = "/images/icon-settings.png";
    private static final String TEXT_INSTRUCTIONS = "To change a setting, use the following command:\n    config SETTING VALUE";

    // FXML
    @FXML
    private Text configInstructionsText;
    @FXML
    private ImageView configImageView;
    @FXML
    private Pane configsPlaceholder;

    public static ConfigView load(Stage primaryStage, Pane placeholder) {
        return UiPartLoader.loadUiPart(primaryStage, placeholder, new ConfigView());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }

    @Override
    public void componentDidMount() {
        // Makes the Component full width wrt parent container.
        FxViewUtil.makeFullWidth(this.mainNode);
        
        // Set instructions
        configInstructionsText.setText(TEXT_INSTRUCTIONS);

        // Load image
        configImageView.setImage(new Image(ICON_PATH));
        
        // Get definitions
        List<ConfigDefinition> configDefinitions = MainApp.getConfig().getDefinitions();

        // Clear items
        ConfigItem.reset(configsPlaceholder);

        // Load items
        for (ConfigDefinition definition : configDefinitions) {
            ConfigItem item = ConfigItem.load(primaryStage, configsPlaceholder);
            item.configDefinition = definition;
            item.render();
        }
    }

}
