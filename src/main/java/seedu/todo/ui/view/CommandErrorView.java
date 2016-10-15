package seedu.todo.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.util.FxViewUtil;
import seedu.todo.model.ErrorBag;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A view class that displays specific command errors in greater detail.
 * @author Wang Xien Dong
 */
public class CommandErrorView extends UiPart {

    private final Logger logger = LogsCenter.getLogger(CommandFeedbackView.class);
    private static final String FXML = "CommandErrorView.fxml";

    private AnchorPane placeholder;
    private VBox errorViewBox;
    @FXML
    private VBox nonFieldErrorBox;
    @FXML
    private VBox fieldErrorBox;
    @FXML
    private GridPane nonFieldErrorGrid;
    @FXML
    private GridPane fieldErrorGrid;

    public static CommandErrorView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandErrorView errorView = UiPartLoader.loadUiPart(primaryStage, placeHolder, new CommandErrorView());
        errorView.configure();
        return errorView;
    }

    private void configure() {
        FxViewUtil.applyAnchorBoundaryParameters(errorViewBox, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(nonFieldErrorBox, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(fieldErrorBox, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(fieldErrorGrid, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(nonFieldErrorGrid, 0.0, 0.0, 0.0, 0.0);
        this.placeholder.getChildren().add(errorViewBox);
    }

    /**
     * Displays both field and non-field errors to the user
     * @param errorBag that contains both field and non-field errors
     */
    public void displayErrors(ErrorBag errorBag) {
        showCommandErrorView();
        clearOldErrorsFromViews();
        displayFieldErrors(errorBag.getFieldErrors());
        displayNonFieldErrors(errorBag.getNonFieldErrors());
    }

    private void displayNonFieldErrors(List<String> nonFieldErrors) {
        int rowCounter = 0;
        for (String error : nonFieldErrors) {
            addRowToGrid(nonFieldErrorGrid, rowCounter++, rowCounter + ".", error);
        }
    }

    private void displayFieldErrors(Map<String, String> fieldErrors) {
        int rowCounter = 0;

        for (Map.Entry<String, String> fieldError : fieldErrors.entrySet()) {
            addRowToGrid(fieldErrorGrid, rowCounter++, fieldError.getKey(), fieldError.getValue());
        }
    }


    /* Override Methods */

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public void setNode(Node node) {
        this.errorViewBox = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void clearErrorsFromViews() {
        clearGrid(nonFieldErrorGrid);
        clearGrid(fieldErrorGrid);
    }


    /*Helper Methods*/

    /**
     * Adds a row of text to the targetGrid
     * @param targetGrid to add a row of text on
     * @param rowIndex which row to add this row of text
     * @param leftText text for the first column
     * @param rightText text for the second column
     */
    private void addRowToGrid(GridPane targetGrid, int rowIndex, String leftText, String rightText) {
        Label leftLabel = generateLabel(leftText);
        Label rightLabel = generateLabel(rightText);
        targetGrid.addRow(rowIndex, leftLabel, rightLabel);
    }

    private Label generateLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("commandError");
        label.autosize();
        return label;
    }

    private void clearGrid(GridPane gridPane) {
        gridPane.getChildren().clear();
    }
}
