package seedu.task.ui;

import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.task.MainApp;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.FxViewUtil;

/**
 * Controller for a help page
 * TODO: add auto cache of new local html if internet access available
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    
    private static final String USERGUIDE_URL =
            "/Userguide.html";
    
    private AnchorPane mainPane;

    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.info("Showing help page about the application.");
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

    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); 
        setIcon(dialogStage, ICON);

        WebView browser = new WebView(); 
        String path  = MainApp.class.getProtectionDomain().getCodeSource().getLocation().getFile()+USERGUIDE_URL;
        logger.info("Opening help popup from: " + path);
        browser.getEngine().load(MainApp.class.getResource(USERGUIDE_URL).toExternalForm());
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(browser);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
