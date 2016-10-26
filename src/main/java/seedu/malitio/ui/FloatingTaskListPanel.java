package seedu.malitio.ui;

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
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.events.ui.DeadlinePanelSelectionChangedEvent;
import seedu.malitio.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */

public class FloatingTaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(FloatingTaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyFloatingTask> taskListView;

    public FloatingTaskListPanel() {
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

    public static FloatingTaskListPanel load(Stage primaryStage, AnchorPane taskListPanelPlaceholder,
                                       ObservableList<ReadOnlyFloatingTask> taskList) {
        FloatingTaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPanelPlaceholder, new FloatingTaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyFloatingTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyFloatingTask> taskList) {
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
    
    public ListView<ReadOnlyFloatingTask> getTaskListView() {
        return taskListView;
    }

    class TaskListViewCell extends ListCell<ReadOnlyFloatingTask> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyFloatingTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(FloatingTaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }
}
