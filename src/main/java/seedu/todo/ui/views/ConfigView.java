package seedu.todo.ui.views;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.ui.UiPartLoader;

public class ConfigView extends View {
    
    private static final String FXML_PATH = "views/ConfigView.fxml";
    
    private static final String ICON_PATH = "/images/icon-settings.png";
    
    // FXML
    @FXML
    private ImageView configImageView;
    
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
        
        // Load image
        configImageView.setImage(new Image(ICON_PATH));
    }

}
