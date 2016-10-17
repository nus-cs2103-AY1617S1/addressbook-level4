package seedu.todo.ui;

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
import seedu.todo.model.task.ImmutableTask;

import java.util.logging.Logger;

/**
 * A panel that holds all the tasks inflated from TaskCard.
 */
public class TodoListPanel extends UiPart {
    /*Constants*/
    private static final String FXML = "TodoListPanel.fxml";
    
    /*Variables*/
    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    /*Layout Declarations*/
    @FXML
    private ListView<ImmutableTask> todoListView;

    public TodoListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    public static TodoListPanel load(Stage primaryStage, AnchorPane todoListPlaceholder, 
            ObservableList<ImmutableTask> todoList) {
        
        TodoListPanel todoListPanel = 
                UiPartLoader.loadUiPart(primaryStage, todoListPlaceholder, new TodoListPanel());
        todoListPanel.configure(todoList);
        return todoListPanel;
    }

    private void configure(ObservableList<ImmutableTask> todoList) {
        setConnections(todoList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ImmutableTask> todoList) {
        todoListView.setItems(todoList);
        todoListView.setCellFactory(listView -> new TodoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in todo list panel changed to : '" + newValue + "'");
                //TODO: raise(new TodoListPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            todoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    private class TodoListViewCell extends ListCell<ImmutableTask> {

        public TodoListViewCell() {
        }

        @Override
        protected void updateItem(ImmutableTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                TaskCard taskCard = TaskCard.load(task, getIndex() + 1);
                setGraphic(taskCard.getLayout());
                setTaskCardStyleProperties(taskCard);
            }
        }

        /**
         * Sets the style properties of a cell on the to-do list, that cannot be done in any other places.
         */
        private void setTaskCardStyleProperties(TaskCard taskCard) {
            this.setPadding(Insets.EMPTY);
            this.selectedProperty().addListener((observable, oldValue, newValue) -> taskCard.markAsSelected(newValue));
        }
    }

}
