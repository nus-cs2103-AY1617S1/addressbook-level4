package seedu.taskitty.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.commons.util.FxViewUtil;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String HELPIMAGE = "/images/UIhelp.png";
    private ScrollPane mainPane;

    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (ScrollPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    //@@author A0139052L
    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);

        setIcon(dialogStage, ICON);
        
        //Get the HelpWindow image and set its boundary and size
        Image helpImage = AppUtil.getImage(HELPIMAGE);
        ImageView helpImageView = new ImageView(helpImage);        
        FxViewUtil.applyAnchorBoundaryParameters(helpImageView, 0.0, 0.0, 0.0, 0.0);
        helpImageView.autosize();
        
        //Set the HelpWindow image obtained as the content for the ScrollPane
        mainPane.setContent(helpImageView);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
