/*package seedu.address.ui;

import java.awt.Label;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.ReadOnlyTask;

public class TaskCountPanel extends UiPart{
    
    private final Logger logger = LogsCenter.getLogger(TaskCountPanel.class);
    private static final String FXML = "TaskCountPanel.fxml";
    private AnchorPane placeHolderPane;
    
    private AnchorPane mainPane;
    
    @FXML
    private Label totalTasks;
    
    @FXML
    private Label todayTasks;
    
    @FXML
    private Label events;
    
    @FXML
    private Label deadlines;
    
    @FXML
    private Label todos;
    
    
    
    public TaskCountPanel() {
        super();
    }
    
    public static TaskCountPanel load(Stage primaryStage, AnchorPane taskCountPanelPlaceholder,
            ObservableList<ReadOnlyTask> deadlineList, ObservableList<ReadOnlyTask> eventList, ObservableList<ReadOnlyTask> todoList) {
        TaskCountPanel taskCountPanel = UiPartLoader.loadUiPart(primaryStage, taskCountPanelPlaceholder, new TaskCountPanel());
        taskCountPanel.configure(eventList, deadlineList, todoList);
        return taskCountPanel;
    }
    
    
    
    private void configure(ObservableList<ReadOnlyTask> deadlineList, ObservableList<ReadOnlyTask> eventList, ObservableList<ReadOnlyTask> todoList) {
        int eventSize = eventList.size();
        int deadlineSize = deadlineList.size();
        int todoSize = todoList.size();
        int total = eventSize + deadlineSize + todoSize;
        
        totalTasks.setText(Integer.toString(total));
        //todayTasks.setText(text);
        events.setText(Integer.toString(eventSize));
        deadlines.setText(Integer.toString(deadlineSize));
        todos.setText(Integer.toString(todoSize));
        
    }
    
    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }
    
    @Override
    public String getFxmlPath() {
        return FXML;
    }
    
}*/
