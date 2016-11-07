package seedu.agendum.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import seedu.agendum.commons.core.LogsCenter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Logger;

//@@author A0148031R
/**
 * Controller for a pop up window that shows command execution result
 */
public class ResultPopUp extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(ResultPopUp.class);
    private static final String FXML = "ResultPopUp.fxml";
    private static Stage root;
    private AnchorPane mainPane;
    private Stage dialogStage;

    private final PauseTransition delay = new PauseTransition(Duration.seconds(5));

    @FXML
    private Label resultDisplay;

    public static ResultPopUp load(Stage primaryStage) {
        logger.fine("Showing command execution result.");
        root = primaryStage;
        ResultPopUp resultPopUp = UiPartLoader.loadUiPart(primaryStage, new ResultPopUp());
        resultPopUp.configure();
        return resultPopUp;
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

        dialogStage = createDialogStage(null, null, scene);
        dialogStage.initModality(Modality.NONE);
        dialogStage.setAlwaysOnTop(true);
        dialogStage.setOnShown((e1) -> primaryStage.requestFocus());

        scene.setFill(Color.TRANSPARENT);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialogStage.setMaxHeight(screenSize.getWidth());
        dialogStage.setMaxWidth(screenSize.getHeight());
    }
    
    private boolean isShowingMessage() {
        return dialogStage.isShowing() && dialogStage.getOpacity() != 0;
    }

    /**
     * Shows message in a pop up window for several seconds
     * @param message The command execution result to be shown
     */
    public void postMessage(String message) {
        
        if(this.isShowingMessage()) {
            delay.playFromStart();
        } else {
            delay.setOnFinished(event -> dialogStage.setOpacity(0));
            delay.play();
        }
        
        resultDisplay.setWrapText(true);
        resultDisplay.setText(message);
        show();
    }
    
    private void show() {
        dialogStage.setOpacity(1.0);
        dialogStage.sizeToScene();
        dialogStage.show();
        dialogStage.setX(root.getX() + root.getWidth() / 2 - dialogStage.getWidth() / 2);
        dialogStage.setY(root.getY() + root.getHeight() / 2 - dialogStage.getHeight() / 2);
    }
}
