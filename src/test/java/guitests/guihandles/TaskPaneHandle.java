package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.oneline.TestApp;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.testutil.TestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskPaneHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TASK_LIST_VIEW_ID = "#taskListView";

    public TaskPaneHandle(GuiRobot guiRobot, Stage primaryStage) {
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
     * @param tasks A list of tasks in the correct order.
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
            if (!tasksInList.get(startPosition + i).getName().toString().equals(tasks[i].getName().toString())){
                return false;
            }
        }

        return true;
    }
    
    //@@author A0140156R
    /**
     * Returns true if the {@code tasks} appear as the sub list (in any order) at position {@code startPosition}.
     */
    public boolean contains(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView().getItems();
        if (startPosition + tasks.length > tasksInList.size()){
            assert false;
            return false;
        }
        List<ReadOnlyTask> tasksToCheck = new ArrayList<ReadOnlyTask>();
        for (int i = 0; i < tasks.length; i++) {
            tasksToCheck.add(tasks[i]);
        }
        for (int i = startPosition; i < tasksInList.size(); i++) {
            ReadOnlyTask taskToFind = tasksInList.get(i);
            boolean found = findAndRemoveTask(tasksToCheck, taskToFind);
            if (!found) {
                assert false;
                return false;
            }
        }
        if (!tasksToCheck.isEmpty()) {
            assert false;
            return false;
        }
        return true;
    }

    /**
     * @param tasksToCheck
     * @param taskToFind
     * @param found
     * @return
     */
    private boolean findAndRemoveTask(List<ReadOnlyTask> tasksToCheck, ReadOnlyTask taskToFind) {
        boolean found = false;
        for (int j = 0; j < tasksToCheck.size(); j++) {
            ReadOnlyTask taskToCheck = tasksToCheck.get(j);
            if (taskToCheck.getName().toString().equals(taskToFind.getName().toString())) {
                tasksToCheck.remove(j);
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param checkOrder True if checking tasks in specified order, else if tasks in any order
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(boolean checkOrder, ReadOnlyTask... tasks) throws IllegalArgumentException {
        return isListMatching(0, checkOrder, tasks);
    }
    
    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        return isListMatching(startPosition, true, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in specified order
     * @param startPosition The starting position of the sub list.
     * @param checkOrder True if checking tasks in specified order, else if tasks in any order
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, boolean checkOrder, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " tasks");
        }
        if (checkOrder) {
            assertTrue(this.containsInOrder(startPosition, tasks));
            for (int i = 0; i < tasks.length; i++) {
                final int scrollTo = i + startPosition;
                guiRobot.interact(() -> getListView().scrollTo(scrollTo));
                guiRobot.sleep(200);
                if (!TestUtil.compareCardAndTask(getTaskCardHandle(startPosition + i), tasks[i])) {
                    return false;
                }
            }
        } else {
            assertTrue(this.contains(startPosition, tasks));
        }
        return true;
    }
    
    //@@author A0140156R
    public int indexOf(Task t) {
        int taskCount = getListView().getItems().size();
        for (int i = 0; i < taskCount; i++) {
            final int scrollTo = i;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (TestUtil.compareCardAndTask(getTaskCardHandle(i), t)) {
                return i + 1;
            }
        }
        return -1;
    }

    public TaskCardHandle navigateToTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView().getItems().stream().filter(p -> p.getName().toString().equals(name)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToTask(task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(ReadOnlyTask task) {
        int index = getTaskIndex(task);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(task);
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

    public TaskCardHandle getTaskCardHandle(int index) {
        try {
            return getTaskCardHandle(new Task(getListView().getItems().get(index)));
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false;
        }
        return null;
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyTask task) {
        assert task != null;
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

    public int getNumberOfTasks() {
        return getListView().getItems().size();
    }
}
