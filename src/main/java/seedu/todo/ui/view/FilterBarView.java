package seedu.todo.ui.view;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.WordUtils;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.model.property.TaskViewFilter;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//@@author A0135805H
/**
 * Shows a row of filter categories via {@link TaskViewFilter}
 * to filter the tasks in {@link TodoListView}
 */
public class FilterBarView extends UiPart {
    /* Constants */
    private final Logger logger = LogsCenter.getLogger(FilterBarView.class);
    private static final String FXML = "FilterBarView.fxml";

    /* Layout Views */
    private FlowPane filterViewPane;

    /* Variables */
    private Map<TaskViewFilter, HBox> taskFilterBoxesMap = new HashMap<>();

    /* Layout Initialisation */
    /**
     * Loads and initialise the {@link #filterViewPane} to the {@link seedu.todo.ui.MainWindow}
     * @param primaryStage of the application
     * @param placeholder where the view element {@link #filterViewPane} should be placed
     * @return an instance of this class
     */
    public static FilterBarView load(Stage primaryStage, AnchorPane placeholder,
                                     ObservableValue<TaskViewFilter> filter) {

        FilterBarView filterView = UiPartLoaderUtil
                .loadUiPart(primaryStage, placeholder, new FilterBarView());
        filterView.configureLayout();
        filterView.configureProperties();
        filterView.bindListener(filter);
        return filterView;
    }

    /**
     * Configure the UI layout of {@link FilterBarView}
     */
    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(filterViewPane, 0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Initialise and configure the UI properties of {@link FilterBarView}
     */
    private void configureProperties() {
        initialiseAllViewFilters();
        selectOneViewFilter(TaskViewFilter.DEFAULT);
    }

    /**
     * Display all the {@link TaskViewFilter} on the {@link #filterViewPane}
     */
    private void initialiseAllViewFilters() {
        for (TaskViewFilter filter : TaskViewFilter.all()) {
            appendEachViewFilter(filter);
        }
    }

    /**
     * Add one {@link TaskViewFilter} on the {@link #filterViewPane}
     * and save an instance to the {@link #taskFilterBoxesMap}
     * @param filter to add onto the pane
     */
    private void appendEachViewFilter(TaskViewFilter filter) {
        HBox textContainer = constructViewFilterBox(filter);
        taskFilterBoxesMap.put(filter, textContainer);
        filterViewPane.getChildren().add(textContainer);
    }

    /**
     * Given a filter, construct a view element to be displayed on the {@link #filterViewPane}
     * @param filter to be displayed
     * @return a view element
     */
    private HBox constructViewFilterBox(TaskViewFilter filter) {
        String filterName = WordUtils.capitalize(filter.name);
        String[] partitionedText = StringUtil.partitionStringAtPosition(filterName, filter.shortcutCharPosition);

        Label leftLabel = new Label(partitionedText[0]);
        Label centreLabel = new Label(partitionedText[1]);
        Label rightLabel = new Label(partitionedText[2]);
        ViewStyleUtil.addClassStyles(centreLabel, ViewStyleUtil.STYLE_UNDERLINE);

        HBox textContainer = new HBox(leftLabel, centreLabel, rightLabel);
        textContainer.getStyleClass().add("viewFilterItem");
        return textContainer;
    }

    /**
     * Binds this component with the {@link TaskViewFilter} property it listens to
     */
    private void bindListener(ObservableValue<TaskViewFilter> filter) {
        filter.addListener((observable, oldValue, newValue) -> selectOneViewFilter(newValue));
    }
    
    /**
     * Select exactly one filter from {@link #filterViewPane}
     */
    public void selectOneViewFilter(TaskViewFilter filter) {
        clearAllViewFiltersSelection();
        selectViewFilter(filter);
    }

    /* Helper Methods */
    /**
     * Clears all selection from the {@link #filterViewPane}
     */
    private void clearAllViewFiltersSelection() {
        for (HBox filterBox : taskFilterBoxesMap.values()) {
            ViewStyleUtil.removeClassStyles(filterBox, ViewStyleUtil.STYLE_SELECTED);
        }
    }

    /**
     * Mark the filter as selected on {@link #filterViewPane}
     * However, if filter is null, nothing is done.
     */
    private void selectViewFilter(TaskViewFilter filter) {
        if (filter != null) {
            HBox filterBox = taskFilterBoxesMap.get(filter);
            ViewStyleUtil.addClassStyles(filterBox, ViewStyleUtil.STYLE_SELECTED);
        }
    }

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
