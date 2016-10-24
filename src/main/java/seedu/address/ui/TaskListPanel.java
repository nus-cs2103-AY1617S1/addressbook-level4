package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.model.task.Task;
import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.NewTaskListEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<Task> taskListView;

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
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<Task> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<Task> taskList) {
        setConnections(taskList);
        addToPlaceholder();
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Task> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

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
    
    @Subscribe
    public void handleNewTaskListEvent(NewTaskListEvent abce) {
    	FilteredList<Task> newTasks = abce.filteredTasks;
		setConnections(newTasks);
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Refreshed task list"));
    }

    class TaskListViewCell extends ListCell<Task> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } 
            else {
            	TaskCard currentCard = TaskCard.load(task, getIndex() + 1);
            	HBox cardPane = currentCard.getLayout();
               
            	setGraphic(cardPane);
            	
            	// Set the color of the card based on whether it's favorited
                if (task.isFavorite()) {
                	cardPane.setStyle("-fx-background-color: yellow;");
                } else {
                	cardPane.setStyle(null);
                }
            }
        }
    }

}
