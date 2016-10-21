package guitests.guihandles;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.testutil.TestUtil;

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

    private static final String TASK_LIST_VIEW_ID = "#taskListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks() {
        ListView<ReadOnlyTask> taskList = getListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getListView() {
        return (ListView<ReadOnlyTask>) getNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, tasks);
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()){
        	return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            System.out.println("TaskInList: " + tasksInList.get(startPosition + i).getName().fullName);
            System.out.println("tasks: " + tasks[i].getName().fullName);
        	if (!tasksInList.get(startPosition + i).getName().fullName.equals(tasks[i].getName().fullName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        
        for (int i = 0; i < tasks.length; i++) {
        	
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (tasks[i].getTaskType().value.equals(TaskType.Type.SOMEDAY) && !TestUtil.compareSomedayCardAndTask(getSomedayTaskCardHandle(startPosition + i), tasks[i])) {
                return false;
            } else if (tasks[i].getTaskType().value.equals(TaskType.Type.DEADLINE) && !TestUtil.compareDeadlineCardAndTask(getDeadlineTaskCardHandle(startPosition + i), tasks[i])) {
                return false;
            } else if (tasks[i].getTaskType().value.equals(TaskType.Type.EVENT) && !TestUtil.compareEventCardAndTask(getEventTaskCardHandle(startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public SomedayTaskCardHandle navigateToSomedayTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView().getItems().stream().filter(p -> (p.getTaskType().value.equals(TaskType.Type.SOMEDAY)) && p.getName().fullName.equals(name)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToSomedayTask(task.get());
    }

    /**
     * Navigates the listview to display and select the someday task.
     */
    public SomedayTaskCardHandle navigateToSomedayTask(ReadOnlyTask task) {
        int index = getTaskIndex(task);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getSomedayTaskCardHandle(task);
    }
    
    public DeadlineTaskCardHandle navigateToDeadlineTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView().getItems().stream().filter(p -> (p.getTaskType().value.equals(TaskType.Type.DEADLINE)) && p.getName().fullName.equals(name)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToDeadlineTask(task.get());
    }

    /**
     * Navigates the listview to display and select the deadline task.
     */
    public DeadlineTaskCardHandle navigateToDeadlineTask(ReadOnlyTask task) {
        int index = getTaskIndex(task);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getDeadlineTaskCardHandle(task);
    }
    
    public EventTaskCardHandle navigateToEventTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView().getItems().stream().filter(p -> (p.getTaskType().value.equals(TaskType.Type.DEADLINE)) && p.getName().fullName.equals(name)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToEventTask(task.get());
    }

    /**
     * Navigates the listview to display and select the deadline task.
     */
    public EventTaskCardHandle navigateToEventTask(ReadOnlyTask task) {
        int index = getTaskIndex(task);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getEventTaskCardHandle(task);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();
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
    public ReadOnlyTask getTask(int index) {
        return getListView().getItems().get(index);
    }

    public SomedayTaskCardHandle getSomedayTaskCardHandle(int index) {
        return getSomedayTaskCardHandle(new Task(getListView().getItems().get(index)));
    }

    public SomedayTaskCardHandle getSomedayTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new SomedayTaskCardHandle(guiRobot, primaryStage, n).isSameTask(task))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new SomedayTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    public DeadlineTaskCardHandle getDeadlineTaskCardHandle(int index) {
        return getDeadlineTaskCardHandle(new Task(getListView().getItems().get(index)));
    }

    public DeadlineTaskCardHandle getDeadlineTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new DeadlineTaskCardHandle(guiRobot, primaryStage, n).isSameTask(task))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new DeadlineTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }
    
    public EventTaskCardHandle getEventTaskCardHandle(int index) {
        return getEventTaskCardHandle(new Task(getListView().getItems().get(index)));
    }

    public EventTaskCardHandle getEventTaskCardHandle(ReadOnlyTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new EventTaskCardHandle(guiRobot, primaryStage, n).isSameTask(task))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new EventTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }
    
    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks() {
        return getListView().getItems().size();
    }
}
