package seedu.stask.ui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.stask.commons.core.LogsCenter;
import seedu.stask.commons.util.FxViewUtil;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";

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

    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        //dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(dialogStage, ICON);

        ImageView browser = new ImageView( new Image ("/images/HelpCommand.png"));
        browser.setFitHeight(800);
        browser.setFitWidth(1000);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(browser);
        
        dialogStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                dialogStage.close();
                keyEvent.consume();
            }
        });
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
