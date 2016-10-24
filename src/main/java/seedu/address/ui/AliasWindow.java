package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.*;

import java.util.logging.Logger;

/**
 * Controller for the alias windows
 */
public class AliasWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(AliasWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "AliasWindow.fxml";
    private static final String TITLE = "Alias List";
    
    private VBox rootLayout;
    private Scene scene;
    private AliasListPanel aliasPanel;
    private Stage dialogStage;
    private Stage primaryStage;
    
    @FXML
    private AnchorPane aliasListPanelPlaceholder;
    


    public static AliasWindow load(Stage primaryStage, Logic logic) {
        logger.fine("Showing list of aliases.");
        AliasWindow aliasWindow = UiPartLoader.loadUiPart(primaryStage, new AliasWindow());
        aliasWindow.configure(logic);
        return aliasWindow;
    }

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(Logic logic){
        scene = new Scene(rootLayout);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        setIcon(dialogStage, ICON);
        aliasPanel = AliasListPanel.load(dialogStage, aliasListPanelPlaceholder,logic.getAlias() );        
        
    }

    public void show() {
        dialogStage.show();
    }
}
