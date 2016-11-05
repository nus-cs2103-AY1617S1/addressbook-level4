
package seedu.menion.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.menion.commons.core.LogsCenter;
import seedu.menion.commons.util.AppUtil;
import seedu.menion.commons.util.FxViewUtil;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */

public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String USERGUIDE_FILEPATH = "/images/help_sheet.png";
    
    private AnchorPane mainPane;

    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    //@@author A0139515A
    private void configure(){
        Scene scene = new Scene(mainPane);
        
        Image image = AppUtil.getImage(USERGUIDE_FILEPATH);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setHeight(image.getHeight());
        dialogStage.setWidth(image.getWidth());
        setIcon(dialogStage, ICON);
        
        ImageView imageView = new ImageView(image);
        FxViewUtil.applyAnchorBoundaryParameters(imageView, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(imageView);

    }
    //@@author

    public void show() {
        dialogStage.showAndWait();
    }
}
