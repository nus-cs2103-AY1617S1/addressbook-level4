package seedu.jimi.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import seedu.jimi.commons.events.model.AddressBookChangedEvent;
import seedu.jimi.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.task.ReadOnlyTask;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
            ObservableList<ReadOnlyTask> taskList, ObservableList<ReadOnlyTask> deadlineTaskList,
            ObservableList<ReadOnlyTask> eventList) {
        TaskListPanel taskListPanel = UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(taskList, deadlineTaskList, eventList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> taskList, ObservableList<ReadOnlyTask> deadlineTaskList,
            ObservableList<ReadOnlyTask> eventList) {
        instantiateLists();
        
        updateFloatingTaskList(taskList);
        updateCompletedAndIncompleteTaskList(taskList, deadlineTaskList);
        updateTasksAndEventsForDays(deadlineTaskList, eventList);
        
        setConnections(taskList, deadlineTaskList, eventList);
        tasksAccordion.setExpandedPane(titleFloatingTasks); //expands floating task list when Jimi starts
        addToPlaceholder();
        registerAsAnEventHandler(this); //to update labels
    }

    private void instantiateLists() {
        this.floatingTaskList = FXCollections.observableArrayList();
        this.completedTaskList = FXCollections.observableArrayList();
        this.incompleteTaskList = FXCollections.observableArrayList();
        this.daysTaskList = new ArrayList<>(7); //7 days in a week
        
        for(int i = 0; i < 7; i++) {
            this.daysTaskList.add(FXCollections.observableArrayList());
        }
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList, ObservableList<ReadOnlyTask> deadlineTaskList,
            ObservableList<ReadOnlyTask> eventList) {
        setupListViews(taskList, taskListView, completedTaskListView, incompleteTaskListView);

        setupDaysListViews(daysTaskList, taskListViewDay1, taskListViewDay2, taskListViewDay3, taskListViewDay4,
                taskListViewDay5, taskListViewDay6, taskListViewDay7);

        setEventHandlerForSelectionChangeEvent();
    }

    private void setupDaysListViews(ArrayList<ObservableList<ReadOnlyTask>> daysTaskList, 
                        ListView<ReadOnlyTask>... taskListViewDays) {
        int i = 0;
        for(ListView<ReadOnlyTask> lv : taskListViewDays){
            lv.setItems(daysTaskList.get(i++));
            lv.setCellFactory(newListView -> new TaskListViewCell());
        }
    }

    private void setupListViews(ObservableList<ReadOnlyTask> taskList, ListView<ReadOnlyTask>... listViews) {
        for(ListView<ReadOnlyTask> t : listViews) {
            t.setItems(taskList);
            t.setCellFactory(newListView -> new TaskListViewCell());
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
    
    /**
     * Updates all the titles when taskBook is changed. Updates remaining tasks for each title.
     * @param abce
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        updateFloatingTaskList(abce.data.getTaskList());
        updateCompletedAndIncompleteTaskList(abce.data.getTaskList(), abce.data.getDeadlineTaskList());
        updateTasksAndEventsForDays(abce.data.getDeadlineTaskList(), abce.data.getEventList());
        
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting floatingTaskListSize label to : " + ""+abce.data.getTaskList().size()));
    }
    
    private void updateFloatingTaskList(List<ReadOnlyTask> floatingTaskList) {
        ObservableList<ReadOnlyTask> newFloatingTaskList = FXCollections.observableArrayList();
        
        for(ReadOnlyTask t : floatingTaskList) {
            if(!(t instanceof DeadlineTask) && 
                    !(t instanceof Event) && 
                    !(t.isCompleted())){
                newFloatingTaskList.add(t);
            }
        }
        
        this.floatingTaskList.setAll(newFloatingTaskList);
        
        updateFloatingTasksTitle();
    }
    
    private void updateCompletedAndIncompleteTaskList(List<ReadOnlyTask> floatingTaskList, List<ReadOnlyTask> deadlineTaskList) {
        ObservableList<ReadOnlyTask> newCompletedTaskList = FXCollections.observableArrayList();
        ObservableList<ReadOnlyTask> newIncompleteTaskList = FXCollections.observableArrayList();
        
        //populate complete and incomplete task lists
        for(ReadOnlyTask t : floatingTaskList){
            if(t.isCompleted()){
                newCompletedTaskList.add(t);
            }
            else
                newIncompleteTaskList.add(t);
        }
        
        for(ReadOnlyTask t : deadlineTaskList){
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

    /**
     * Populates all the individual listViews for days with respective tasks due on the specific day.
     * @param taskList
     */
    private void updateTasksAndEventsForDays(List<ReadOnlyTask> deadlineTaskList, List<ReadOnlyTask> eventList) {
        ArrayList<ObservableList<ReadOnlyTask>> newDaysList = new ArrayList<>(7); //7 days in a week
        
        //initialise newDaysList
        for(int i = 0; i < 7; i++) {
            newDaysList.add(FXCollections.observableArrayList());
        }
        
        //filter the tasks and events by days and populate each day with corresponding tasks and events
        for(ReadOnlyTask t : deadlineTaskList) {
            DateTime timeOfTask = ((DeadlineTask) t).getDeadline();
            DateTime timeNow = new DateTime();

            long dayToAdd = timeNow.getDifferenceInDays(timeOfTask); //checks for how many days ahead the task is
            
            logger.info("dayToAdd is :" + dayToAdd);
            
            if(dayToAdd >= 0) {
                newDaysList.get((int) dayToAdd).add(t); //add the task to the respective day
            }
        }
        
        for(ReadOnlyTask t : eventList) {
            DateTime timeOfEvent = ((Event) t).getStart();
            DateTime timeNow = new DateTime();
            
            long dayToAdd = timeNow.getDifferenceInDays(timeOfEvent); //checks for how many days ahead the event is
            
            if(dayToAdd >= 0) {
                newDaysList.get((int) dayToAdd).add(t); //add the event to the respective day
            }
        }
        
        //manually set all list to the new updated ones
        int i = 0;
        for(ObservableList<ReadOnlyTask> t : this.daysTaskList) {
            t.setAll(newDaysList.get(i++));
        }
        
        updateDaysTitles(titleTaskDay1, titleTaskDay2, titleTaskDay3, titleTaskDay4, titleTaskDay5, titleTaskDay6,
                titleTaskDay7);
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
