package seedu.todo.ui.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

import java.util.logging.Logger;

//@@author A0135805H
/**
 * Displays the message when {@link TodoListView} is empty.
 */
public class EmptyListView extends UiPart {

    /* Constants */
    private static final String FXML = "EmptyListView.fxml";

    private static final String EMPTY_MESSAGE_DEFAULT
            = "No tasks in view. Type 'add' to add a new tasks.";
    private static final String EMPTY_MESSAGE_COMPLETED
            = "You have no completed tasks.";
    private static final String EMPTY_MESSAGE_INCOMPLETE
            = "You have no incomplete tasks or upcoming events.";
    private static final String EMPTY_MESSAGE_DUE_SOON
            = "You have completed all your tasks.";
    private static final String EMPTY_MESSAGE_EVENTS
            = "You have no upcoming events. Type 'add' to add new events.";
    private static final String EMPTY_MESSAGE_TODAY
            = "You have no tasks/events for today. Type 'add' to add a new task.";

    private static final String EMOJI_HAPPY_URL = "/images/emoji-happy.png";
    private static final String EMOJI_SAD_URL = "/images/emoji-sad.png";

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

    /**
     * Sets the content of empty list based on {@link TaskViewFilter}
     */
    private void setEmptyListContent(TaskViewFilter filter) {
        if (filter == TaskViewFilter.DEFAULT) {
            setEmptyListContent(EMPTY_MESSAGE_DEFAULT, EMOJI_HAPPY_URL);

        } else if (filter == TaskViewFilter.COMPLETED) {
            setEmptyListContent(EMPTY_MESSAGE_COMPLETED, EMOJI_SAD_URL);

        } else if (filter == TaskViewFilter.INCOMPLETE) {
            setEmptyListContent(EMPTY_MESSAGE_INCOMPLETE, EMOJI_HAPPY_URL);

        } else if (filter == TaskViewFilter.DUE_SOON) {
            setEmptyListContent(EMPTY_MESSAGE_DUE_SOON, EMOJI_HAPPY_URL);

        } else if (filter == TaskViewFilter.EVENTS) {
            setEmptyListContent(EMPTY_MESSAGE_EVENTS, EMOJI_HAPPY_URL);

        }
    }

    /**
     * Sets the content of empty list by {@code message} and {@code imageUrl}
     */
    private void setEmptyListContent(String message, String imageUrl) {
        emptyListLabel.setText(message);
        emptyListImage.setImage(new Image(imageUrl));
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
