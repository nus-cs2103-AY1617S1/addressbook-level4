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
 * Controller for a help page
 */
public class AliasWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "AliasWindow.fxml";
    private static final String TITLE = "Alias List";
    private static final String USERGUIDE_URL =
            "https://github.com/CS2103AUG2016-W15-C2/main/blob/master/docs/UserGuide.md";

    private AnchorPane mainPane;

    private Stage dialogStage;

    public static AliasWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        AliasWindow aliasWindow = UiPartLoader.loadUiPart(primaryStage, new AliasWindow());
        aliasWindow.configure();
        return aliasWindow;
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
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(dialogStage, ICON);
        
        Text t = new Text("Hello world");
        FxViewUtil.applyAnchorBoundaryParameters(t, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(t);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
