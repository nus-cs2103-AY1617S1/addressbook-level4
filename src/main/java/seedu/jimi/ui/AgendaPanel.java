package seedu.jimi.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.ui.TaskListPanel.TaskListViewCell;

/**
 * Agenda window of Jimi, displays most relevant tasks and events to the user when first starting up app.
 * @author zexuan
 *
 */
public class AgendaPanel extends UiPart{
    private final Logger logger = LogsCenter.getLogger(AgendaPanel.class);
    private static final String FXML = "AgendaPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    //list of tasks and events to display on agenda
    private ObservableList<ReadOnlyTask> tasksList;
    private ObservableList<ReadOnlyTask> eventsList;
    
    //main agenda views
    @FXML
    private TreeTableView<ReadOnlyTask> treeTableView;
    @FXML
    private TreeTableColumn<ReadOnlyTask, String> tasksTableColumnLeft;
    @FXML
    private TreeTableColumn<ReadOnlyTask, String> tasksTableColumnRight;
    
    
    @Override
    public void setNode(Node node) {
        this.panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    /**
     * Initializes the agenda view with relevant tasks and events.
     */
    @FXML
    private void initialize() {
        // Initialize the tasks table with the two columns.
        this.treeTableView = new TreeTableView<>();
        this.tasksTableColumnLeft = new TreeTableColumn("Name");
        this.tasksTableColumnRight = new TreeTableColumn("Details");
        
        treeTableView.getColumns().setAll(tasksTableColumnLeft, tasksTableColumnRight);
    }
    
    public static AgendaPanel load(Stage primaryStage, AnchorPane agendaPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        AgendaPanel agendaPanel = 
                UiPartLoader.loadUiPart(primaryStage, agendaPlaceholder, new AgendaPanel());
        agendaPanel.configure(taskList);
        return agendaPanel;
    }
    
    private void configure(ObservableList<ReadOnlyTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
        registerAsAnEventHandler(this); //to update labels
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        this.tasksList = FXCollections.observableArrayList();
        this.eventsList = FXCollections.observableArrayList();
        
        updateTasksList(taskList);
        
        taskListView.setItems(taskList);
        completedTaskListView.setItems(this.completedTaskList);
        incompleteTaskListView.setItems(this.incompleteTaskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        completedTaskListView.setCellFactory(listView -> new TaskListViewCell());
        incompleteTaskListView.setCellFactory(listView -> new TaskListViewCell());
        
        setEventHandlerForSelectionChangeEvent();
    }
    
    private void updateTasksList(ObservableList<ReadOnlyTask> taskList) {
        for(ReadOnlyTask t : taskList){
            if(!t.isCompleted()) {//TODO: add checks for due dates
                this.tasksList.add(t);
            }
        }
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, true);
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
}
