package seedu.menion.ui;

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
import seedu.menion.commons.core.LogsCenter;
import seedu.menion.commons.events.ui.ActivityPanelSelectionChangedEvent;
import seedu.menion.model.activity.ReadOnlyActivity;

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
    private ListView<ReadOnlyActivity> taskListView;
    
    @FXML
    private ListView<ReadOnlyActivity> eventListView;
    
    @FXML
    private ListView<ReadOnlyActivity> floatingTaskListView;

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
                                       ObservableList<ReadOnlyActivity> floatingTaskList,
                                       ObservableList<ReadOnlyActivity> taskList,
                                       ObservableList<ReadOnlyActivity> eventList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(floatingTaskList, taskList, eventList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyActivity> floatingTaskList, 
    						ObservableList<ReadOnlyActivity> taskList,
    						ObservableList<ReadOnlyActivity> eventList) {
        setConnections(floatingTaskList, taskList, eventList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyActivity> floatingTaskList, 
								ObservableList<ReadOnlyActivity> taskList,
								ObservableList<ReadOnlyActivity> eventList) {
    	floatingTaskListView.setItems(floatingTaskList);
    	floatingTaskListView.setCellFactory(listView -> new TaskListViewCell());
    	
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new TaskListViewCell());
        
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
                raise(new ActivityPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyActivity> {

        public TaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyActivity activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(ActivityCard.load(activity, getIndex() + 1).getLayout());
            }
        }
    }

}
