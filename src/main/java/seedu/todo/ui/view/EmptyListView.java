package seedu.todo.ui.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.model.property.TaskViewFilter;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.ViewStyleUtil;

import java.util.logging.Logger;

//@@author A0135805H
/**
 * Displays the message when {@link TodoListView} is empty.
 */
public class EmptyListView extends UiPart {

    /* Constants */
    private static final String FXML = "EmptyListView.fxml";

    /* Variables */
    private final Logger logger = LogsCenter.getLogger(EmptyListView.class);

    /*Layouts*/
    private VBox emptyListView;
    @FXML private ImageView emptyListImage;
    @FXML private Label emptyListLabel;

    /**
     * Loads and initialise the {@link #emptyListView} to the {@code placeholder}.
     * @param primaryStage of the application
     * @param placeholder where the view element {@link #emptyListView} should be placed
     * @param todoList for checking whether the list is empty.
     * @param viewProperty the view that the user is at.
     * @return an instance of this class
     */
    public static EmptyListView load(Stage primaryStage, AnchorPane placeholder,
            ObservableList<ImmutableTask> todoList, ObjectProperty<TaskViewFilter> viewProperty) {

        EmptyListView emptyListView =  UiPartLoaderUtil
                .loadUiPart(primaryStage, placeholder, new EmptyListView());

        emptyListView.configureLayout();
        emptyListView.setEmptyListConnections(todoList, viewProperty);
        return emptyListView;
    }

    private void configureLayout() {
        FxViewUtil.applyAnchorBoundaryParameters(emptyListView, 0.0, 0.0, 0.0, 0.0);
    }


    /* Empty List View Methods */
    /**
     * Configures the {@link EmptyListView} with the {@code todoList} property and {@code viewProperty}
     * property.
     */
    private void setEmptyListConnections(ObservableList<ImmutableTask> todoList,
                                         ObjectProperty<TaskViewFilter> viewProperty) {

        ListChangeListener<ImmutableTask> visibilityUpdate
                = c -> setEmptyListViewVisibility(todoList.isEmpty());

        ChangeListener<TaskViewFilter> viewUpdate
                = (observable, oldValue, newValue) -> setEmptyListContent(newValue);

        todoList.addListener(visibilityUpdate);
        visibilityUpdate.onChanged(null);

        viewProperty.addListener(viewUpdate);
        viewUpdate.changed(null, null, viewProperty.get());
    }

    /**
     * Displays the {@link #emptyListView} if {@code isVisible} is true, hides otherwise.
     */
    private void setEmptyListViewVisibility(boolean isVisible) {
        FxViewUtil.setCollapsed(emptyListView, !isVisible);
    }

    private void setEmptyListContent(TaskViewFilter viewFilter) {
        emptyListLabel.setText(viewFilter.emptyListMessage);
    }

    /* Override Methods */
    @Override
    public void setNode(Node node) {
        this.emptyListView = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
