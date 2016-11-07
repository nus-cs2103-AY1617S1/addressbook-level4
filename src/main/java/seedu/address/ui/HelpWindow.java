package seedu.address.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

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

    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    //@@author A0093960X
    /**
     * Configures the help window stage.
     */
    private void configure() {
        Scene scene = initializeHelpWindowProperties();
        setupKeyPressEventHandler(scene);
    }
    
    /**
     * Sets up the key press event handler for the help window scene.
     * @param scene The Help window Scene
     */
    private void setupKeyPressEventHandler(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                dialogStage.close();
            }
        });
    }

    /**
     * Initializes the help window properties.
     * @return The Scene associated with the initialized help window
     */
    private Scene initializeHelpWindowProperties() {
        Scene scene = new Scene(mainPane);
        // Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        setIcon(dialogStage, ICON);
        return scene;
    }

    /**
     * Shows the help window.
     */
    public void show() {
        dialogStage.show();
    }

}
