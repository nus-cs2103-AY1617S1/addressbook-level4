package seedu.address.ui;

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
import seedu.address.commons.events.ui.TomorrowTaskTabPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskFilter;
import seedu.address.model.task.TaskType;
import seedu.address.commons.core.LogsCenter;

import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Panel containing the list of tomorrow's tasks.
 */
public class TomorrowTaskListTabPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TomorrowTaskListTabPanel.class);
    private static final String FXML = "TomorrowTaskListTabPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyTask> taskListView;

    public TomorrowTaskListTabPanel() {
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

    public static TomorrowTaskListTabPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        TomorrowTaskListTabPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TomorrowTaskListTabPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList.filtered(getTomorrowTaskFilter()));
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private static Predicate<ReadOnlyTask> getTomorrowTaskFilter() {
		return TaskFilter.isTomorrowTask();
	}
    
    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TomorrowTaskTabPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
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
            	if (task.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
                    setGraphic(SomedayTaskCard.load(task, getIndex() + 1).getLayout());
            	} else if (task.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
            		setGraphic(DeadlineTaskCard.load(task, getIndex() + 1).getLayout());
            	} else {
            		setGraphic(EventTaskCard.load(task, getIndex() + 1).getLayout());
            	}
            }
        }
    }

}
