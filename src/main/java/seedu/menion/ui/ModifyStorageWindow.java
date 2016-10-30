package seedu.menion.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.menion.commons.core.LogsCenter;
import seedu.menion.commons.util.FxViewUtil;
import seedu.menion.logic.commands.ModifyStoragePathCommand;

import java.util.logging.Logger;

//@@author A0139515A
/**
 * Controller for modify storage pop up message
 */
public class ModifyStorageWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(ModifyStorageWindow.class);
    private static final String ICON = "/images/modify_storage_icon.png";
    private static final String FXML = "ModifyStorageWindow.fxml";
    private static final String TITLE = "Modify Storage";

    private AnchorPane mainPane;
    
    @FXML
    private Text popupText;

    private Stage dialogStage;

    public static ModifyStorageWindow load(Stage primaryStage) {
        logger.fine("Showing pop up for modifying storage path.");
        ModifyStorageWindow modifyStorageWindow = UiPartLoader.loadUiPart(primaryStage, new ModifyStorageWindow());
        modifyStorageWindow.configure();
        return modifyStorageWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @FXML
    public void initialize() {
    	popupText.setText(ModifyStoragePathCommand.MESSAGE_POPUP);
    }
    
    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setWidth(600);
        dialogStage.setHeight(100);
        setIcon(dialogStage, ICON);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}