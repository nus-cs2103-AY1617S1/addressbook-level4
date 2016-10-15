package seedu.jimi.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.ReadOnlyTask;

import java.util.List;
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
    
    private Integer floatingTaskListSize; //size of current floatingTaskList
    private ObservableList<ReadOnlyTask> floatingTaskList;
    private ObservableList<ReadOnlyTask> completedTaskList;
    private ObservableList<ReadOnlyTask> incompleteTaskList;
    
    @FXML
    private ListView<ReadOnlyTask> taskListView;
    @FXML
    private ListView<ReadOnlyTask> completedTaskListView;
    @FXML
    private ListView<ReadOnlyTask> incompleteTaskListView;
    
    //incomplete/complete title labels
    @FXML private TitledPane titleCompletedTasks;
    @FXML private TitledPane titleIncompleteTasks;
    
    //taskListPanel title labels
    @FXML private TitledPane titleFloatingTaskListSize;
    @FXML private TitledPane titleTaskDay1;
    @FXML private TitledPane titleTaskDay2;
    @FXML private TitledPane titleTaskDay3;
    @FXML private TitledPane titleTaskDay4;
    @FXML private TitledPane titleTaskDay5;
    @FXML private TitledPane titleTaskDay6;
    @FXML private TitledPane titleTaskDay7;

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
                                       ObservableList<ReadOnlyTask> taskList) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
        registerAsAnEventHandler(this); //to update labels
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        this.completedTaskList = FXCollections.observableArrayList();
        this.incompleteTaskList = FXCollections.observableArrayList();
        
        updateFloatingTaskList(taskList);
        updateCompletedAndIncompleteTaskList(taskList);
        
        setupListView(taskListView, taskList);
        setupListView(completedTaskListView, taskList);
        setupListView(incompleteTaskListView, taskList);
        
        setEventHandlerForSelectionChangeEvent();
    }

    private void setupListView(ListView<ReadOnlyTask> listView, ObservableList<ReadOnlyTask> taskList) {
        listView.setItems(taskList);
        listView.setCellFactory(newListView -> new TaskListViewCell());
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, true);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        setEventHandlerForListView(taskListView);
        setEventHandlerForListView(completedTaskListView);
        setEventHandlerForListView(incompleteTaskListView);
    }

    private void setEventHandlerForListView(ListView<ReadOnlyTask> listView) {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
    
    /**
     * Updates all the titles when taskBook is changed. Updates remaining tasks for each title.
     * @param abce
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        updateFloatingTaskList(abce.data.getTaskList());
        updateCompletedAndIncompleteTaskList(abce.data.getTaskList());
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting floatingTaskListSize label to : " + ""+abce.data.getTaskList().size()));
    }
    
    private void updateFloatingTaskList(List<ReadOnlyTask> taskList) {
        floatingTaskListSize = taskList.size();
        ObservableList<ReadOnlyTask> floatingTaskList = FXCollections.observableArrayList();
        
        for(ReadOnlyTask t : taskList) {
            if(!(t instanceof DeadlineTask) && 
                    !(t instanceof Event) && 
                    !(t.isCompleted())){
                floatingTaskList.add(t);
            }
        }
        
        updateFloatingTasksTitle();
    }

    private void updateFloatingTasksTitle() {
        titleFloatingTaskListSize.setText("Floating Tasks (" + floatingTaskListSize.toString() + ")");
    }
    
    private void updateCompletedAndIncompleteTaskList(List<ReadOnlyTask> taskList) {
        ObservableList<ReadOnlyTask> newCompletedTaskList = FXCollections.observableArrayList();
        ObservableList<ReadOnlyTask> newIncompleteTaskList = FXCollections.observableArrayList();
        
        //populate complete and incomplete task lists
        for(ReadOnlyTask t : taskList){
            if(t.isCompleted()){
                newCompletedTaskList.add(t);
            }
            else
                newIncompleteTaskList.add(t);
        }
        
        this.completedTaskList.setAll(newCompletedTaskList);
        this.incompleteTaskList.setAll(newIncompleteTaskList);
       
        updateCompleteTasksTitle();
        updateIncompleteTasksTitle();
    }

    private void updateIncompleteTasksTitle() {
        this.titleIncompleteTasks.setText("Incomplete Tasks (" + incompleteTaskList.size() + ")");
    }

    private void updateCompleteTasksTitle() {
        this.titleCompletedTasks.setText("Completed Tasks (" + completedTaskList.size() + ")");
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

}
