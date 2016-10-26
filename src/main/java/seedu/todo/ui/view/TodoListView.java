package seedu.todo.ui.view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.ui.UiPart;
import seedu.todo.ui.util.UiPartLoaderUtil;
import seedu.todo.ui.util.FxViewUtil;
import seedu.todo.model.task.ImmutableTask;

import java.util.logging.Logger;

//@@author A0135805H
/**
 * A panel that holds all the tasks inflated from TaskCardView.
 */
public class TodoListView extends UiPart {
    /*Constants*/
    private static final String FXML = "TodoListView.fxml";
    
    /*Variables*/
    private final Logger logger = LogsCenter.getLogger(TodoListView.class);
    private VBox panel;
    
    /*Layout Declarations*/
    @FXML
    private ListView<ImmutableTask> todoListView;

    //@@author A0135805H-reused
    /**
     * Default Constructor for {@link TodoListView}
     */
    public TodoListView() {
        super();
    }

    /**
     * Loads and initialise the {@link TodoListView} to the placeHolder.
     *
     * @param primaryStage of the application
     * @param placeHolder where the view element {@link #todoListView} should be placed
     * @return an instance of this class
     */
    public static TodoListView load(Stage primaryStage, AnchorPane placeHolder,
                                    ObservableList<ImmutableTask> todoList) {
        
        TodoListView todoListView = UiPartLoaderUtil
                .loadUiPart(primaryStage, placeHolder, new TodoListView());
        todoListView.configure(todoList);
        return todoListView;
    }

    /**
     * Configures the {@link TodoListView}
     *
     * @param todoList A list of {@link ImmutableTask} to be displayed on this {@link #todoListView}.
     */
    private void configure(ObservableList<ImmutableTask> todoList) {
        setConnections(todoList);
    }

    /**
     * Links the list of {@link ImmutableTask} to the todoListView.
     *
     * @param todoList A list of {@link ImmutableTask} to be displayed on this {@link #todoListView}.
     */
    private void setConnections(ObservableList<ImmutableTask> todoList) {
        todoListView.setItems(todoList);
        todoListView.setCellFactory(param -> new TodoListViewCell());
    }

    //@@author A0135805H
    /* Ui Methods */
    /**
     * Toggles the expanded/collapsed view of a task card.
     *
     * @param task The specific to be expanded or collapsed from view.
     */
    public void toggleExpandCollapsed(ImmutableTask task) {
        TaskCardView taskCardView = TaskCardView.getTaskCard(task);
        if (taskCardView != null) {
            taskCardView.toggleCardCollapsing();
        }
    }

    /**
     * Scrolls the {@link #todoListView} to the particular task card at the listIndex.
     */
    public void scrollAndSelect(int listIndex) {
        Platform.runLater(() -> {
            todoListView.scrollTo(listIndex);
            todoListView.getSelectionModel().clearAndSelect(listIndex);
        });
    }

    /**
     * Scrolls the {@link #todoListView} to the particular task card.
     *
     * @param task for the list to scroll to.
     */
    public void scrollAndSelect(ImmutableTask task) {
        TaskCardView taskCardView = TaskCardView.getTaskCard(task);
        int listIndex = FxViewUtil.convertToListIndex(taskCardView.getDisplayedIndex());
        scrollAndSelect(listIndex);
    }

    /* Override Methods */
    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    //@@author A0135805H-reused
    /**
     * Models a Task Card as a single ListCell of the ListView
     */
    private class TodoListViewCell extends ListCell<ImmutableTask> {

        /* Override Methods */
        @Override
        protected void updateItem(ImmutableTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                TaskCardView taskCardView = TaskCardView.load(task, FxViewUtil.convertToUiIndex(getIndex()));
                setGraphic(taskCardView.getLayout());
                setTaskCardStyleProperties(taskCardView);
            }
        }

        //@@author A0135805H
        /**
         * Sets the style properties of a cell on the to-do list, that cannot be done in any other places.
         */
        private void setTaskCardStyleProperties(TaskCardView taskCardView) {
            this.setPadding(Insets.EMPTY);
            this.selectedProperty().addListener((observable, oldValue, newValue)
                    -> taskCardView.markAsSelected(newValue));
        }
    }
}
