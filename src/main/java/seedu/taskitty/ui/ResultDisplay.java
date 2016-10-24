package seedu.taskitty.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seedu.taskitty.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart {
    public static final String RESULT_DISPLAY_ID = "resultDisplay";
    //@@author A0130853L
    private static final String WELCOME_MESSAGE = "Welcome! Here is your agenda for today:";
    private static final String WELCOME_MESSAGE_WITH_OVERDUE_DEADLINES = "Welcome! You have overdue tasks.";
    private static boolean hasOverdue;
    
    //@@author
    private static final String FXML = "ResultDisplay.fxml";

    private AnchorPane placeHolder;

    private AnchorPane mainPane;
    
    @FXML
    private AnchorPane resultDisplayArea;
    
    @FXML
    private Label toolTipLabel;
    
    @FXML
    private Label descriptionLabel;

    public static ResultDisplay load(Stage primaryStage, AnchorPane placeHolder) {
        ResultDisplay statusBar = UiPartLoader.loadUiPart(primaryStage, placeHolder, new ResultDisplay());
        statusBar.configure();
        return statusBar;
    }
    //@@author A0130853L
    public void configure() {
        if (!hasOverdue) {
        	postMessage(WELCOME_MESSAGE);
        } else {
        	displayOverdueWelcomeMessage();
        }
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
        FxViewUtil.applyAnchorBoundaryParameters(resultDisplayArea, 0.0, 0.0, 0.0, 0.0);
    }
    
    //@@author
    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    //@@author A0139930B
    public void postMessage(String message) {
        toolTipLabel.setText(message);
        descriptionLabel.setText("");
    }
    
    public void postMessage(String message, String description) {
        toolTipLabel.setText(message);
        descriptionLabel.setText(description);
    }
    
    //@@author A0130853L
    public void displayOverdueWelcomeMessage() {
    	postMessage(WELCOME_MESSAGE_WITH_OVERDUE_DEADLINES);
    }
    
    public static void setOverdue() {
    	hasOverdue = true;
    }

}
