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
public class ActivityListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);
    private static final String FXML = "ActivityListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyActivity> taskListView;
    
    @FXML
    private ListView<ReadOnlyActivity> eventListView;
    
    @FXML
    private ListView<ReadOnlyActivity> floatingTaskListView;

    public ActivityListPanel() {
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

    public static ActivityListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyActivity> floatingTaskList,
                                       ObservableList<ReadOnlyActivity> taskList,
                                       ObservableList<ReadOnlyActivity> eventList) {
        ActivityListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new ActivityListPanel());
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
    	floatingTaskListView.setCellFactory(listView -> new FloatingTaskListViewCell());
    	
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        
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
        protected void updateItem(ReadOnlyActivity task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }
   
    class FloatingTaskListViewCell extends ListCell<ReadOnlyActivity> {

        public FloatingTaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyActivity floatingTask, boolean empty) {
            super.updateItem(floatingTask, empty);

            if (empty || floatingTask == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(FloatingTaskCard.load(floatingTask, getIndex() + 1).getLayout());
            }
        }
    }
    
    class EventListViewCell extends ListCell<ReadOnlyActivity> {

        public EventListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyActivity event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(EventCard.load(event, getIndex() + 1).getLayout());
            }
        }
    }
}
