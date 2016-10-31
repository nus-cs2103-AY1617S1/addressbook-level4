package seedu.tasklist.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.util.FxViewUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {
    // @@author A0146107M
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String USERGUIDE_URL = HelpWindow.class.getResource("/ug_html/CommandSummary.html")
            .toExternalForm();
    // @@author
    private AnchorPane mainPane;

    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    public Stage getStage() {
        return dialogStage;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;

    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure() {
        Scene scene = new Scene(mainPane);

        // Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);

        // @@author A0146107M
        WebView browser = new WebView();
        browser.getEngine().load(USERGUIDE_URL);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);

        // dialogStage.setMaximized(false); //TODO: set a more appropriate
        // initial size
        setIcon(dialogStage, ICON);
        mainPane.getChildren().add(browser);

        // @@author A0135769N
        dialogStage.setMaxWidth(575.0);
        dialogStage.setMinWidth(0.0);
        dialogStage.setMaxHeight(580.0);
        dialogStage.setMinHeight(0.0);
        dialogStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                dialogStage.close();
                keyEvent.consume();
            }
        });

        // @@author
    }

    public void show() {
        dialogStage.showAndWait();
    }

}
