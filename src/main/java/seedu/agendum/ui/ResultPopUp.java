package seedu.agendum.ui;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import seedu.agendum.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Pop up window to show command execution result
 */
public class ResultPopUp extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(ResultPopUp.class);
    private static final String FXML = "ResultPopUp.fxml";
    
    private AnchorPane mainPane;

    private Stage dialogStage;
    private static Stage root;
    
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
    
    private void configure(){
        
        Scene scene = new Scene(mainPane);
        dialogStage = createDialogStage(null, null, scene);
        
        scene.setFill(Color.TRANSPARENT);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
    }
    
    public void postMessage(String message) {
        resultDisplay.setWrapText(true);
        resultDisplay.setText(message);
        show();
        
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> reFocusRoot());
        delay.play();
    }

    public void reFocusRoot() {
        dialogStage.setOpacity(0);
        root.requestFocus();
    }
    
    public void show() {
        dialogStage.requestFocus();
        dialogStage.setOpacity(1.0);
        dialogStage.sizeToScene();
        dialogStage.show();
        dialogStage.setX(root.getX() + root.getWidth() / 2 - dialogStage.getWidth() / 2);
        dialogStage.setY(root.getY() + root.getHeight() / 2 - dialogStage.getHeight() / 2);
    }
}
