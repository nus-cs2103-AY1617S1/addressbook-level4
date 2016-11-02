package seedu.menion.ui;

import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.menion.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class DatetimeDisplay extends UiPart {
    public static final String RESULT_DISPLAY_ID = "datetimeDisplay";
    private static final String DATE_STYLE_SHEET = "cell_big_label";
    private TextArea resultDisplayArea;
    private final StringProperty displayed = new SimpleStringProperty("");

    private static final String FXML = "DatetimeDisplay.fxml";

    private AnchorPane placeHolder;

    private AnchorPane mainPane;

    public static DatetimeDisplay load(Stage primaryStage, AnchorPane placeHolder, String currentDate) {
    	DatetimeDisplay datetimeDisplay = UiPartLoader.loadUiPart(primaryStage, placeHolder, new DatetimeDisplay());
    	datetimeDisplay.configure(currentDate);
        return datetimeDisplay;
    }

    public void configure(String currentDate) {
        resultDisplayArea = new TextArea();
        resultDisplayArea.setEditable(false);
        resultDisplayArea.setId(RESULT_DISPLAY_ID);
        resultDisplayArea.getStyleClass().removeAll();
        resultDisplayArea.getStyleClass().add(DATE_STYLE_SHEET);
        resultDisplayArea.setText("Today's date: " + currentDate);
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

}