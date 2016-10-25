package seedu.todo.ui.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.model.ErrorBag;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.ViewGeneratorUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

//@@author A0135805H
/**
 * A view class that displays specific command errors in greater detail.
 */
public class CommandErrorView extends UiPart {

    private final Logger logger = LogsCenter.getLogger(CommandFeedbackView.class);
    private static final String FXML = "CommandErrorView.fxml";

    private AnchorPane placeholder;
    private VBox errorViewBox;
    @FXML private VBox nonFieldErrorBox;
    @FXML private VBox fieldErrorBox;
    @FXML private GridPane nonFieldErrorGrid;
    @FXML private GridPane fieldErrorGrid;

    /**
     * Loads and initialise the feedback view element to the placeHolder
     * @param primaryStage of the application
     * @param placeHolder where the view element {@link #errorViewBox} should be placed
     * @return an instance of this class
     */
    public static CommandErrorView load(Stage primaryStage, AnchorPane placeHolder) {
        CommandErrorView errorView = UiPartLoaderUtil.loadUiPart(primaryStage, placeHolder, new CommandErrorView());
        errorView.configureLayout();
        errorView.hideCommandErrorView();
        return errorView;
    }

    /**
     * Configure the UI layout of {@link CommandErrorView}
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(errorViewBox, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(nonFieldErrorBox, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(fieldErrorBox, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(fieldErrorGrid, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(nonFieldErrorGrid, 0.0, 0.0, 0.0, 0.0);
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

    /**
     * Feeds non field errors to the {@link #nonFieldErrorGrid}.
     * If there are no non-field errors, then {@link #nonFieldErrorBox} will be hidden.
     * @param nonFieldErrors that stores a list of non-field errors
     */
    private void displayNonFieldErrors(List<String> nonFieldErrors) {
        if (nonFieldErrors.isEmpty()) {
            hideErrorBox(nonFieldErrorBox);
        } else {
            int rowCounter = 0;
            for (String error : nonFieldErrors) {
                addRowToGrid(nonFieldErrorGrid, rowCounter++, rowCounter + ".", error);
            }
        }
    }

    /**
     * Feeds field errors to the {@link #fieldErrorGrid}.
     * If there are no field errors, then {@link #fieldErrorBox} will be hidden.
     * @param fieldErrors that stores the field errors
     */
    private void displayFieldErrors(Map<String, String> fieldErrors) {
        if (fieldErrors.isEmpty()) {
            hideErrorBox(fieldErrorBox);
        } else {
            int rowCounter = 0;
            for (Map.Entry<String, String> fieldError : fieldErrors.entrySet()) {
                addRowToGrid(fieldErrorGrid, rowCounter++, fieldError.getKey(), fieldError.getValue());
            }
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

    /*Helper Methods*/
    /**
     * Adds a row of text to the targetGrid
     * @param targetGrid to add a row of text on
     * @param rowIndex which row to add this row of text
     * @param leftText text for the first column
     * @param rightText text for the second column
     */
    private void addRowToGrid(GridPane targetGrid, int rowIndex, String leftText, String rightText) {
        Label leftLabel = ViewGeneratorUtil.constructLabel(leftText, ViewStyleUtil.STYLE_TEXT_4);
        Label rightLabel = ViewGeneratorUtil.constructLabel(rightText, ViewStyleUtil.STYLE_TEXT_4);
        targetGrid.addRow(rowIndex, leftLabel, rightLabel);
    }

    /**
     * Clears all elements in the given grid.
     */
    private void clearGrid(GridPane gridPane) {
        gridPane.getChildren().clear();
    }

    /**
     * Hides a field or non-field error box.
     * @param vBox can be either {@link #fieldErrorBox} or {@link #nonFieldErrorBox}
     */
    private void hideErrorBox(VBox vBox) {
        FxViewUtil.setCollapsed(vBox, true);
    }

    /**
     * Shows a field or non-field error box.
     * @param vBox can be either {@link #fieldErrorBox} or {@link #nonFieldErrorBox}
     */
    private void showErrorBox(VBox vBox) {
        FxViewUtil.setCollapsed(vBox, false);
    }

    /**
     * Hides the entire {@link CommandErrorView}
     */
    public void hideCommandErrorView() {
        FxViewUtil.setCollapsed(placeholder, true);
    }

    /**
     * Displays the entire {@link CommandErrorView}
     */
    private void showCommandErrorView() {
        FxViewUtil.setCollapsed(placeholder, false);
    }

    /**
     * Clears previous errors from the grid, and then unhide all the error boxes.
     */
    private void clearOldErrorsFromViews() {
        clearGrid(nonFieldErrorGrid);
        clearGrid(fieldErrorGrid);
        showErrorBox(nonFieldErrorBox);
        showErrorBox(fieldErrorBox);
    }
}
