package w15c2.tusk.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.logic.*;

import java.util.logging.Logger;

//@@author A0139708W
/**
 * Controller for the alias windows
 */
public class AliasWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(AliasWindow.class);
    private static final String FXML = "AliasWindow.fxml";
    private static final String TITLE = "Alias List";
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 300;
    
    private VBox rootLayout;
    private Scene scene;
    private AliasListPanel aliasPanel;
    private Stage dialogStage;
    private Stage primaryStage;
    
    @FXML
    private AnchorPane aliasListPanelPlaceholder;
    
    /**
     * Loads alias window.
     * 
     * @param primaryStage  Primary stage of application.
     * @param logic         Instance of Logic class with aliases.
     * @return              Window of aliases.
     */
    public static AliasWindow load(Stage primaryStage, Logic logic) {
        logger.fine("Showing list of aliases.");
        AliasWindow aliasWindow = UiPartLoader.loadUiPart(primaryStage, new AliasWindow());
        aliasWindow.configure(logic);
        return aliasWindow;
    }

    /**
     * Sets pane containing AliasWindow
     * to appropriate node, used in 
     * UiPart.
     */
    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }
    
    /**
     * Return FXML file name for UiPart loading.
     * 
     * @return  String representation of FXML file name.
     */
    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(Logic logic){
        scene = new Scene(rootLayout);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        setDimensions();
        aliasPanel = AliasListPanel.load(dialogStage, aliasListPanelPlaceholder,logic.getAlias());        
        
    }
    
    /** 
     * Set appropriate dimensions
     */
    private void setDimensions() {
        dialogStage.setMinHeight(WINDOW_HEIGHT);
        dialogStage.setMaxHeight(WINDOW_HEIGHT);
        dialogStage.setMinWidth(WINDOW_WIDTH);
    }
    
    /**
     * Shows AliasWindow.
     */
    public void show() {
        dialogStage.show();
    }
}
