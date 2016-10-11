package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.util.FxViewUtil;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<AnchorPane> {

    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "/view/HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String USERGUIDE_URL =
            "https://github.com/se-edu/addressbook-level4/blob/master/docs/UserGuide.md";

    @FXML
    private AnchorPane helpWindowRoot;

    private Stage dialogStage;

    private final Stage primaryStage;

    public HelpWindow(Stage primaryStage) {
        super(FXML);
        this.primaryStage = primaryStage;
        Scene scene = new Scene(helpWindowRoot);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        WebView browser = new WebView();
        browser.getEngine().load(USERGUIDE_URL);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        helpWindowRoot.getChildren().add(browser);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
