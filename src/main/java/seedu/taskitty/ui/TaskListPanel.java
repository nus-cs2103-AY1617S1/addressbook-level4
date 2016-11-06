package seedu.taskitty.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskitty.commons.util.AppUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;

import java.util.logging.Logger;

//@@author A0130853L-reused
/**
 * Base class for the 3 panels containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    
    //@@author A0139930B
    private static final String ID_TODO = "todoListView";
    private static final String ID_DEADLINE = "deadlineListView";
    private static final String ID_EVENT = "eventListView";
    
    private static final String HEADER_TODO = "TODOS";
    private static final String HEADER_DEADLINE = "DEADLINES";
    private static final String HEADER_EVENT = "EVENTS";
    
    private static final String ICON_TODO = "/images/todo_icon.png";
    private static final String ICON_DEADLINE = "/images/deadline_icon.png";
    private static final String ICON_EVENT = "/images/event_icon.png";
    
    //@@author A0130853L-reused
    protected VBox panel;
    protected AnchorPane placeHolderPane;
    
    private static final String FXML = "TaskListPanel.fxml";

    @FXML
    private Label header;
    
    @FXML
    private ImageView listIcon;
    
    @FXML
    private ListView<ReadOnlyTask> taskListView;

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }
    
    public void configure(ObservableList<ReadOnlyTask> taskList, int type) {
        initializeListView(type);
        setConnections(taskListView, taskList);
        addToPlaceholder();
    }
    
    //@@author A0139930B
    /**
     * Initializes the list view header and ID depending on the type of list it is
     */
    private void initializeListView(int type) {
        
        switch (type) {
        
        case Task.TASK_COMPONENT_COUNT :
            setListUIComponents(HEADER_TODO, ICON_TODO, ID_TODO);
            break;
            
        case Task.DEADLINE_COMPONENT_COUNT :
            setListUIComponents(HEADER_DEADLINE, ICON_DEADLINE, ID_DEADLINE);
            break;
            
        case Task.EVENT_COMPONENT_COUNT :
            setListUIComponents(HEADER_EVENT, ICON_EVENT, ID_EVENT);
            break;
            
        default :
            assert false : "List must be either todo, deadline or event";
            break;
        }
    }
    
    /**
     * Sets the relevant UI components of the TaskListPanel based on input Strings
     * @param header title of the TaskListPanel
     * @param icon filepath to the image for the TaskListPanel's icon
     * @param id of the TaskListPanel. id must be set to use GuiRobot for testing
     */
    private void setListUIComponents(String header, String icon, String id) {
        this.header.setText(header);
        this.listIcon.setImage(AppUtil.getImage(icon));
        this.taskListView.setId(id);
    }
    
    //@@author A0130853L-reused
    
    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyTask> taskList, TaskListPanel listPanel, int type) {
        TaskListPanel taskListPanel =  UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, listPanel);
        taskListPanel.configure(taskList, type);
        return taskListPanel;
    }    
    
    protected void setConnections(ListView<ReadOnlyTask> taskListView, ObservableList<ReadOnlyTask> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent(taskListView);
    }

    protected void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent(ListView<ReadOnlyTask> taskListView) {
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    class TaskListViewCell extends ListCell<ReadOnlyTask> {

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
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }

}
