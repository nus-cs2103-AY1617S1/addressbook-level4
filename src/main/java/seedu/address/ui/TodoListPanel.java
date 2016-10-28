package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

public class TodoListPanel extends UiPart {

    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);
    private static final String FXML = "TodoListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> todoListView;

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
                                       ObservableList<ReadOnlyTask> todoList) {
        TodoListPanel todoListPanel =
                UiPartLoader.loadUiPart(primaryStage, todoListPlaceholder, new TodoListPanel());
        todoListPanel.configure(todoList);
        return todoListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> todoList) {
        setConnections(todoList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> todoList) {
        todoListView.setItems(todoList);
        todoListView.setCellFactory(listView -> new TodoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in todo list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            todoListView.getSelectionModel().clearAndSelect(index);
        });
    }
    
    public void clear() {
        Platform.runLater(() -> {
            todoListView.getSelectionModel().clearSelection();
        });
    }


    class TodoListViewCell extends ListCell<ReadOnlyTask> {

        public TodoListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask todo, boolean empty) {
            super.updateItem(todo, empty);

            if (empty || todo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TodoCard.load(todo, getIndex() + 1).getLayout());
            }
        }
    }

}
