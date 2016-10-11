package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.*;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class AliasWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(AliasWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "AliasWindow.fxml";
    private static final String TITLE = "Alias List";
    private static final String USERGUIDE_URL =
            "https://github.com/CS2103AUG2016-W15-C2/main/blob/master/docs/UserGuide.md";

    private VBox mainPane;
    private AliasListPanel aliasListPanel;
    private Stage dialogStage;
    
    @FXML
    private AnchorPane aliasListPanelPlaceholder;

    public static AliasWindow load(Stage primaryStage, Logic logic) {
        logger.fine("Showing help page about the application.");
        AliasWindow aliasWindow = UiPartLoader.loadUiPart(primaryStage, new AliasWindow());
        aliasWindow.configure(logic);
        return aliasWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(Logic logic){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(dialogStage, ICON);
        aliasListPanel = AliasListPanel.load(dialogStage, aliasListPanelPlaceholder, logic.getAlias());
        
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
