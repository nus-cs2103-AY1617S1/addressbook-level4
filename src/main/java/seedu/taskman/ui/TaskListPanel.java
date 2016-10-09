package seedu.taskman.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.taskman.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskman.model.task.ReadOnlyTask;
import seedu.taskman.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private AnchorPane panel;
    private AnchorPane placeHolderPane;
    
    @FXML
    private TableView<ReadOnlyTask> taskListView;
    @FXML
    private TableColumn<ReadOnlyTask, String> titleColumn; 
    @FXML
    private TableColumn<ReadOnlyTask, String> deadlineColumn;
    
    public TaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }
    
    // TODO Resolve generic type issue.
    @SuppressWarnings("unchecked")
    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);       
        titleColumn = new TableColumn<ReadOnlyTask,String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<ReadOnlyTask, String>("title"));
        deadlineColumn = new TableColumn<ReadOnlyTask,String>("Deadline");
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<ReadOnlyTask, String>("deadline"));
        taskListView.getColumns().setAll(titleColumn, deadlineColumn);
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
    }
    // TODO Edit
    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }
    // TODO Edit
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

}
