package seedu.jimi.ui;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.events.model.TaskBookChangedEvent;
import seedu.jimi.commons.events.ui.ShowTaskPanelSectionEvent;
import seedu.jimi.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;
    
    private ObservableList<ReadOnlyTask> floatingTaskList;
    private ObservableList<ReadOnlyTask> completedTaskList;
    private ObservableList<ReadOnlyTask> incompleteTaskList;
    private ArrayList<ObservableList<ReadOnlyTask>> daysTaskList;
    
    //main accordion view
    @FXML
    Accordion tasksAccordion;
    
    //all list views
    @FXML private ListView<ReadOnlyTask> taskListView;
    @FXML private ListView<ReadOnlyTask> completedTaskListView;
    @FXML private ListView<ReadOnlyTask> incompleteTaskListView;
    @FXML private ListView<ReadOnlyTask> taskListViewDay1;
    @FXML private ListView<ReadOnlyTask> taskListViewDay2;
    @FXML private ListView<ReadOnlyTask> taskListViewDay3;
    @FXML private ListView<ReadOnlyTask> taskListViewDay4;
    @FXML private ListView<ReadOnlyTask> taskListViewDay5;
    @FXML private ListView<ReadOnlyTask> taskListViewDay6;
    @FXML private ListView<ReadOnlyTask> taskListViewDay7;
    
    //incomplete/complete title labels
    @FXML private TitledPane titleCompletedTasks;
    @FXML private TitledPane titleIncompleteTasks;
    
    //taskListPanel title labels
    @FXML private TitledPane titleFloatingTasks;
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
            ObservableList<ReadOnlyTask> floatingTaskList, ObservableList<ReadOnlyTask> incompleteTaskList,
            ObservableList<ReadOnlyTask> completedTaskList, ArrayList<ObservableList<ReadOnlyTask>> daysList) {
        TaskListPanel taskListPanel = UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(floatingTaskList, incompleteTaskList, completedTaskList, daysList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> floatingTaskList, ObservableList<ReadOnlyTask> incompleteTaskList,
            ObservableList<ReadOnlyTask> completedTaskList, ArrayList<ObservableList<ReadOnlyTask>> daysList) {
        instantiateLists(floatingTaskList, incompleteTaskList, completedTaskList, daysList);
        setConnections();
        showFloatingTasks();
        updateAllTitles();
        addToPlaceholder();
        registerAsAnEventHandler(this); // to update labels
    }

    private void instantiateLists(ObservableList<ReadOnlyTask> floatingTaskList, ObservableList<ReadOnlyTask> incompleteTaskList,
            ObservableList<ReadOnlyTask> completedTaskList, ArrayList<ObservableList<ReadOnlyTask>> daysList) {
        this.floatingTaskList = floatingTaskList;
        this.incompleteTaskList = incompleteTaskList;
        this.completedTaskList = completedTaskList;
        this.daysTaskList = daysList;
    }

    private void setConnections() {
        setupListViews();
        setEventHandlerForSelectionChangeEvent();
    }

    private void setupListViews() {
        this.taskListView.setItems(this.floatingTaskList);
        this.taskListView.setCellFactory(newListView -> new TaskListViewCell());
        this.completedTaskListView.setItems(this.completedTaskList);
        this.completedTaskListView.setCellFactory(newListView -> new TaskListViewCell());
        this.incompleteTaskListView.setItems(this.incompleteTaskList);
        this.incompleteTaskListView.setCellFactory(newListView -> new TaskListViewCell());
        
        setupDaysListViews(daysTaskList, taskListViewDay1, taskListViewDay2, taskListViewDay3, taskListViewDay4,
                taskListViewDay5, taskListViewDay6, taskListViewDay7);
    }
    
    /**
     * Checks the listview's respective DayOfWeek and then assigns the list matched to it.
     * @param daysTaskList
     * @param taskListViewDays
     */
    @SafeVarargs
    private final void setupDaysListViews(ArrayList<ObservableList<ReadOnlyTask>> daysTaskList,
            ListView<ReadOnlyTask>... taskListViewDays) {
        int i = 0;
        for (ListView<ReadOnlyTask> lv : taskListViewDays) {
            lv.setItems(daysTaskList.get(i++));
            lv.setCellFactory(newListView -> new TaskListViewCell());
        }
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
    
    private void updateFloatingTasksTitle() {
        this.titleFloatingTasks.setText("Floating Tasks (" + this.floatingTaskList.size() + ")");
    }
    
    private void updateIncompleteTasksTitle() {
        this.titleIncompleteTasks.setText("Incomplete Tasks (" + this.incompleteTaskList.size() + ")");
    }

    private void updateCompleteTasksTitle() {
        this.titleCompletedTasks.setText("Completed Tasks (" + this.completedTaskList.size() + ")");
    }
    
    private void updateDaysTitles(TitledPane... panes) {
        int i = 0;
        for(TitledPane t : panes) {
            if(i == 0) {
                t.setText("Today (" + this.daysTaskList.get(i++).size() + ")");
            } else if(i == 1) {
                t.setText("Tomorrow (" + this.daysTaskList.get(i++).size() + ")");
            } else {
                String dayOfWeek = LocalDateTime.now().getDayOfWeek().plus(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                t.setText(dayOfWeek + " (" + this.daysTaskList.get(i++).size() + ")");
            }
        }
    }
    
    private void updateAllTitles() {
        updateFloatingTasksTitle();
        updateCompleteTasksTitle();
        updateIncompleteTasksTitle();
        updateDaysTitles(titleTaskDay1, titleTaskDay2, titleTaskDay3, titleTaskDay4, titleTaskDay5, titleTaskDay6,
                titleTaskDay7);
    }
    
    //========== Event handlers ================================================================================

    
    /**
     * Updates all the titles when taskBook is changed. Updates remaining tasks for each title.
     * @param tbce
     */
    @Subscribe
    public void handleTaskBookChangedEvent(TaskBookChangedEvent tbce) {
        updateAllTitles();
        
        logger.info(LogsCenter.getEventHandlingLogMessage(tbce, "Setting floatingTaskListSize label to : " + ""+tbce.data.getTaskList().size()));
    }
    
    /**
     * Expands the relevant task panels according to user input.
     */
    @Subscribe
    public void handleShowTaskPanelSelectionEvent(ShowTaskPanelSectionEvent event) {
        switch (event.sectionToDisplay) {
        case "floating":
            showFloatingTasks();
            break;
        case "incomplete":
            showIncompleteTasks();
            break;
        case "complete":
            showCompleteTasks();
            break;
        case "today":
            showDay1();
            break;
        case "tomorrow":
            showDay2();
            break;
        case "monday":
        case "tuesday":
        case "wednesday":
        case "thursday":
        case "friday":
        case "saturday":
        case "sunday":
            showRequiredDay(event.sectionToDisplay.toLowerCase());
        default:
            break;
        }
    }
    /**
     * Finds the title to be displayed and calls its respective method to expand it.
     * @param sectionToDisplay
     */
    private void showRequiredDay(String sectionToDisplay) {
        if(titleTaskDay1.getText().toLowerCase().contains(sectionToDisplay)) {
            showDay1();
        } else if(titleTaskDay2.getText().toLowerCase().contains(sectionToDisplay)) {
            showDay2();
        } else if(titleTaskDay3.getText().toLowerCase().contains(sectionToDisplay)) {
            showDay3();
        }else if(titleTaskDay4.getText().toLowerCase().contains(sectionToDisplay)) {
            showDay4();
        }else if(titleTaskDay5.getText().toLowerCase().contains(sectionToDisplay)) {
            showDay5();
        }else if(titleTaskDay6.getText().toLowerCase().contains(sectionToDisplay)) {
            showDay6();
        }else if(titleTaskDay7.getText().toLowerCase().contains(sectionToDisplay)) {
            showDay7();
        }
    }

    //========== Method calls to expand relevant listviews in panel. ===========================================
    
    public void showFloatingTasks() {
        tasksAccordion.setExpandedPane(titleFloatingTasks); 
    }
    
    public void showIncompleteTasks() {
        tasksAccordion.setExpandedPane(titleIncompleteTasks); 
    }
    
    public void showCompleteTasks() {
        tasksAccordion.setExpandedPane(titleCompletedTasks);
    }
    
    //today
    public void showDay1() {
        tasksAccordion.setExpandedPane(titleTaskDay1);
    }
    
    //tomorrow
    public void showDay2() {
        tasksAccordion.setExpandedPane(titleTaskDay2);
    }
    
    public void showDay3() {
        tasksAccordion.setExpandedPane(titleTaskDay3);
    }
    
    public void showDay4() {
        tasksAccordion.setExpandedPane(titleTaskDay4);
    }
    
    public void showDay5() {
        tasksAccordion.setExpandedPane(titleTaskDay5);
    }
    
    public void showDay6() {
        tasksAccordion.setExpandedPane(titleTaskDay6);    
    }
    
    public void showDay7() {
        tasksAccordion.setExpandedPane(titleTaskDay7);    
    }
    
    //===========================================================================================================
    
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
