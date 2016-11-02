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
import seedu.address.model.alias.ReadOnlyAlias;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskType;
import seedu.address.commons.core.LogsCenter;

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
    @FXML
    private ListView<ReadOnlyAlias> aliasListView;

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
    
    public AnchorPane getPlaceHolderPane() {
    	return placeHolderPane;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel loadTaskList(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configureTask(taskList);
        taskListPanel.hideAliasListViewSize();
        return taskListPanel;
    }
    
    private void configureTask(ObservableList<ReadOnlyTask> taskList) {
        setTaskConnections(taskList);
        addToPlaceholder();
    }
    
    private void setTaskConnections(ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }
    
    //@@author A0142184L
    private void hideAliasListViewSize() {
    	aliasListView.setMaxHeight(0.0);
	}

	public static TaskListPanel loadAliasList(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyAlias> aliasList) {
    	TaskListPanel taskListPanel = UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configureAlias(aliasList);
        taskListPanel.hideTaskListViewSize();
        return taskListPanel;
    }
	
    private void hideTaskListViewSize() {
    	taskListView.setMaxHeight(0.0);
	}
    
    private void configureAlias(ObservableList<ReadOnlyAlias> aliasList) {
        setAliasConnections(aliasList);
        addToPlaceholder();
    }
    
    private void setAliasConnections(ObservableList<ReadOnlyAlias> aliasList) {
        aliasListView.setItems(aliasList);
        aliasListView.setCellFactory(listView -> new AliasListViewCell());
    }
    
    //@@author
    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
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
    
    //@@author A0142184L
    class AliasListViewCell extends ListCell<ReadOnlyAlias> {

        public AliasListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyAlias alias, boolean empty) {
            super.updateItem(alias, empty);

            if (empty || alias == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(AliasCard.load(alias, getIndex() + 1).getLayout());
            }
        }
     }
}
