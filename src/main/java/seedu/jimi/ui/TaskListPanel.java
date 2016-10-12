package seedu.jimi.ui;

import javafx.application.Platform;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
import seedu.jimi.model.task.ReadOnlyTask;

import java.util.ArrayList;
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
    private List<ReadOnlyTask> completedTaskList;
    private List<ReadOnlyTask> incompleteTaskList;
    
    @FXML
    private ListView<ReadOnlyTask> taskListView;
    
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
        updateFloatingTaskSize(taskList);
        
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
    
    /**
     * Updates all the titles when taskBook is changed. Updates remaining tasks for each title.
     * @param abce
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        updateFloatingTaskSize(abce.data.getTaskList());
        updateCompletedAndIncompleteTaskList(abce.data.getTaskList());
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting floatingTaskListSize label to : " + ""+abce.data.getTaskList().size()));
    }
    
    private void updateFloatingTaskSize(List<ReadOnlyTask> taskList) {
        floatingTaskListSize = taskList.size();
        titleFloatingTaskListSize.setText("Floating Tasks (" + floatingTaskListSize.toString() + ")");
    }
    
    private void updateCompletedAndIncompleteTaskList(List<ReadOnlyTask> taskList) {
        this.completedTaskList = new ArrayList<ReadOnlyTask>(taskList.size());
        this.incompleteTaskList = new ArrayList<ReadOnlyTask>(taskList.size());
        
        for(ReadOnlyTask t : taskList){
            if(t.isCompleted()){
                this.completedTaskList.add(t);
            }
            else
                this.incompleteTaskList.add(t);
        }
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
