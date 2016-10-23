package seedu.taskitty.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.taskitty.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart {
    public static final String RESULT_DISPLAY_ID = "resultDisplay";
    private static final String STATUS_BAR_STYLE_SHEET = "result-display";
    private static final String WELCOME_MESSAGE = "Welcome! Here is your agenda for today:";
    private static final String WELCOME_MESSAGE_WITH_OVERDUE_DEADLINES = "Welcome! You have overdue tasks.";
    private TextArea resultDisplayArea;
    private final StringProperty displayed = new SimpleStringProperty("");
    private static boolean hasOverdue;

    private static final String FXML = "ResultDisplay.fxml";

    private AnchorPane placeHolder;

    private AnchorPane mainPane;

    public static ResultDisplay load(Stage primaryStage, AnchorPane placeHolder) {
        ResultDisplay statusBar = UiPartLoader.loadUiPart(primaryStage, placeHolder, new ResultDisplay());
        statusBar.configure();
        return statusBar;
    }

    public void configure() {
        resultDisplayArea = new TextArea();
        resultDisplayArea.setEditable(false);
        resultDisplayArea.setId(RESULT_DISPLAY_ID);
        resultDisplayArea.getStyleClass().removeAll();
        resultDisplayArea.getStyleClass().add(STATUS_BAR_STYLE_SHEET);
        resultDisplayArea.setText("");
        resultDisplayArea.textProperty().bind(displayed);
        if (!hasOverdue) {
        	postMessage(WELCOME_MESSAGE);
        } else {
        	displayOverdueWelcomeMessage();
        }
        FxViewUtil.applyAnchorBoundaryParameters(resultDisplayArea, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(resultDisplayArea);
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

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

    public void postMessage(String message) {
        displayed.setValue(message);
    }
    
    public void displayOverdueWelcomeMessage() {
    	postMessage(WELCOME_MESSAGE_WITH_OVERDUE_DEADLINES);
    }
    
    public static void setOverdue() {
    	hasOverdue = true;
    }

}
