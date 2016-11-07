package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.task.TestApp;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Status;
import seedu.todolist.model.task.Task;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String LIST_VIEW_ID_INCOMPLETE = "#taskListView";
    private static final String LIST_VIEW_ID_COMPLETE = "#completeTaskListView";
    private static final String LIST_VIEW_ID_OVERDUE = "#overdueTaskListView";
    
    private static final String TAB_ID_INCOMPLETE = "#incompleteTab";
    private static final String TAB_ID_COMPLETE = "#completedTab";
    private static final String TAB_ID_OVERDUE = "#overdueTab";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks(Status.Type type) {
        
        ListView<ReadOnlyTask> taskList = getListView(type);  
        return taskList.getSelectionModel().getSelectedItems();
    }
    
    //@@author A0138601M
    /**
     * Get the list view based on the type of task it is showing
     */
    public ListView<ReadOnlyTask> getListView(Status.Type type) {
        switch (type) {
        
        case Complete :
            return getCompleteListView();
            
        case Incomplete :
            return getIncompleteListView();
            
        case Overdue :
            return getOverdueListView();
            
        default :
            assert false : "Type must be either Complete, Incomplete or Overdue";
            return null;
            
        }
    }
    
    private ListView<ReadOnlyTask> getIncompleteListView() {
        return (ListView<ReadOnlyTask>) getNode(LIST_VIEW_ID_INCOMPLETE);
    }
    
    private ListView<ReadOnlyTask> getCompleteListView() {
        return (ListView<ReadOnlyTask>) getNode(LIST_VIEW_ID_COMPLETE);
    }
    
    private ListView<ReadOnlyTask> getOverdueListView() {
        return (ListView<ReadOnlyTask>) getNode(LIST_VIEW_ID_OVERDUE);
    }
    
    /**
     * Get the list tab based on the type of task it is showing
     */
    public Node getListTab(Status.Type type) {
        switch (type) {
        
        case Complete :
            return getCompleteListTab();
            
        case Incomplete :
            return getIncompleteListTab();
            
        case Overdue :
            return getOverdueListTab();
            
        default :
            assert false : "Type must be either Complete, Incomplete or Overdue";
            return null;           
        }
    }
    
    private Node getIncompleteListTab() {
        return getNode(TAB_ID_INCOMPLETE);
    }
    
    private Node getCompleteListTab() {
        return getNode(TAB_ID_COMPLETE);
    }
    
    private Node getOverdueListTab() {
        return getNode(TAB_ID_OVERDUE);
    }
    
    //@@author
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView(Status.Type type) {
        Point2D point = TestUtil.getScreenMidPoint(getListView(type));
        guiRobot.clickOn(point.getX(), point.getY());
    }
    
    /**
     * Clicks on the list tab.
     */
    public void clickOnListTab(Status.Type type) {
        Point2D point = TestUtil.getScreenMidPoint(getListTab(type));
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order).
     */
    public boolean containsInOrder(Status.Type type, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView(type).getItems();

        // Return false if the list in panel is too short to contain the given list
        if (tasks.length > tasksInList.size()){
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(i).equals(tasks[i])){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(Status.Type type, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length != getListView(type).getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView(type).getItems().size() - 1) + " tasks" + tasks.length);
        }
        assertTrue(this.containsInOrder(type, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i;
            guiRobot.interact(() -> getListView(type).scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(i, type), tasks[i])) {
                return false;
            }
        }
        return true;
    }

    public TaskCardHandle navigateToTask(TestTask taskToFind) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        Status.Type type = taskToFind.getStatus().getType();
        clickOnListTab(type);
        final Optional<ReadOnlyTask> task = getListView(type).getItems().stream().filter(p -> p.equals(taskToFind)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task not found: " + taskToFind.getName().fullName);
        }

        return navigateToTask(task.get(), type);
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(ReadOnlyTask task, Status.Type type) {
        int index = getTaskIndex(task, type);

        guiRobot.interact(() -> {
            getListView(type).scrollTo(index);
            guiRobot.sleep(150);
            getListView(type).getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(task);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask, Status.Type type) {
        List<ReadOnlyTask> tasksInList = getListView(type).getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if(tasksInList.get(i).getName().equals(targetTask.getName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public ReadOnlyTask getTask(int index, Status.Type type) {
        return getListView(type).getItems().get(index);
    }

    public TaskCardHandle getTaskCardHandle(int index, Status.Type type) {
        return getTaskCardHandle(new Task(getListView(type).getItems().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSameTask(task))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTask(Status.Type type) {
        return getListView(type).getItems().size();
    }
}
