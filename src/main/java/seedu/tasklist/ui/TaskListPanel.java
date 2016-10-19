package seedu.tasklist.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.tasklist.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> taskListView;

    public TaskListPanel() {
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
    public void setPlaceholder(Pane pane) {
        this.placeHolderPane = (AnchorPane) pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel = UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    public void selectPrevious() {
        Platform.runLater(() -> {
            taskListView.getSelectionModel().selectPrevious();
        });
    }

    public void selectNext() {
        Platform.runLater(() -> {
            taskListView.getSelectionModel().selectNext();
        });
    }
    
    public void selectLast() {
        Platform.runLater(() -> {
            taskListView.getSelectionModel().selectLast();
        });
    }
    
    public void selectFirst() {
        Platform.runLater(() -> {
            taskListView.getSelectionModel().selectFirst();;
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

    public ListView<ReadOnlyTask> getTaskListView() {
        return taskListView;
    }
}
