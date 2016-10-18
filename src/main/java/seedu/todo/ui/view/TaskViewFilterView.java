package seedu.todo.ui.view;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.enumerations.TaskViewFilters;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.UiPartLoader;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.ViewGeneratorUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.util.logging.Logger;

/**
 * Shows a row of filter categories via {@link seedu.todo.commons.enumerations.TaskViewFilters}
 * to filter the tasks in {@link seedu.todo.ui.TodoListPanel}
 */
public class TaskViewFilterView extends UiPart {
    /* Constants */
    private final Logger logger = LogsCenter.getLogger(TaskViewFilterView.class);
    private static final String FXML = "TaskViewFilterView.fxml";

    /* Layout Views */
    private AnchorPane placeholder;
    private FlowPane filterViewPane;

    /* Layout Initialisation */
    /**
     * Loads and initialise the {@link #filterViewPane} to the {@link seedu.todo.ui.MainWindow}
     * @param primaryStage of the application
     * @param placeholder where the view element {@link #filterViewPane} should be placed
     * @return an instance of this class
     */
    public static TaskViewFilterView load(Stage primaryStage, AnchorPane placeholder) {
        TaskViewFilterView filterView = UiPartLoader.loadUiPart(primaryStage, placeholder, new TaskViewFilterView());
        filterView.addToPlaceholder();
        filterView.configureLayout();
        filterView.configureProperties();
        return filterView;
    }

    /**
     * Adds this view element to external placeholder
     */
    private void addToPlaceholder() {
        placeholder.getChildren().add(filterViewPane);
    }

    /**
     * Configure the UI layout of {@link TaskViewFilterView}
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(filterViewPane, 0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Configure the UI properties of {@link TaskViewFilterView}
     */
    private void configureProperties() {
        displayAllViewFilters();
    }

    /**
     * Display all the {@link TaskViewFilters} on the {@link #filterViewPane}
     */
    private void displayAllViewFilters() {
        for (TaskViewFilters filter : TaskViewFilters.values()) {
            appendEachViewFilter(filter);
        }
    }

    private void appendEachViewFilter(TaskViewFilters filter) {
        String[] partitionedText = StringUtil.partitionStringAtPosition(filter.toString(), filter.getUnderlineChar());
        Text leftText = ViewGeneratorUtil.constructText(partitionedText[0], ViewStyleUtil.STYLE_TEXT_4);
        Text centreText = ViewGeneratorUtil.constructText(partitionedText[1], ViewStyleUtil.STYLE_TEXT_4);
        Text rightText = ViewGeneratorUtil.constructText(partitionedText[2], ViewStyleUtil.STYLE_TEXT_4);
        ViewStyleUtil.addClassStyles(centreText, ViewStyleUtil.STYLE_UNDERLINE);

        TextFlow textContainer = ViewGeneratorUtil.placeIntoTextFlow(leftText, centreText, rightText);
        ViewStyleUtil.addClassStyles(textContainer, ViewStyleUtil.STYLE_ROUND_LABEL);
        filterViewPane.getChildren().add(textContainer);
    }

    /* Methods interfacing with UiManager */

    /* Helper Methods */


    /* Override Methods */
    @Override
    public void setNode(Node node) {
        this.filterViewPane = (FlowPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
