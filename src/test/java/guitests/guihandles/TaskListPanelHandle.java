package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jym.manager.model.task.ReadOnlyTask;
import jym.manager.model.task.Task;
import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the Task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String Task_LIST_VIEW_ID = "#taskListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks() {
        ListView<ReadOnlyTask> TaskList = getListView();
        return TaskList.getSelectionModel().getSelectedItems();
    }

    public ListView<ReadOnlyTask> getListView() {
        return (ListView<ReadOnlyTask>) getNode(Task_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the Task details correctly and in correct order.
     * @param Tasks A list of Task in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... Tasks) {
        return this.isListMatching(0, Tasks);
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code Tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... Tasks) {
        List<ReadOnlyTask> TasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + Tasks.length > TasksInList.size()){
            return false;
        }

        // Return false if any of the Tasks doesn't match
        for (int i = 0; i < Tasks.length; i++) {
            if (!TasksInList.get(startPosition + i).getDescription().toString().equals(Tasks[i].getDescription().toString())){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the Task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param Tasks A list of Task in the correct order.
     */
    public boolean isListMatching(int startPosition, ReadOnlyTask... Tasks) throws IllegalArgumentException {
        if (Tasks.length + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " Tasks");
        }
        assertTrue(this.containsInOrder(startPosition, Tasks));
        for (int i = 0; i < Tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(startPosition + i), Tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public TaskCardHandle navigateToTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> Task = getListView().getItems().stream().filter(p -> p.getDescription().toString().equals(name)).findAny();
        if (!Task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToTask(Task.get());
    }

    /**
     * Navigates the listview to display and select the Task.
     */
    public TaskCardHandle navigateToTask(ReadOnlyTask Task) {
        int index = getTaskIndex(Task);

        guiRobot.interact(() -> {
            getListView().scrollTo(index);
            guiRobot.sleep(150);
            getListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(Task);
    }


    /**
     * Returns the position of the Task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask) {
        List<ReadOnlyTask> TasksInList = getListView().getItems();
        for (int i = 0; i < TasksInList.size(); i++) {
            if(TasksInList.get(i).getDescription().equals(targetTask.getDescription())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a Task from the list by index
     */
    public ReadOnlyTask getTask(int index) {
        return getListView().getItems().get(index);
    }

    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(new Task(getListView().getItems().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyTask Task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> TaskCardNode = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSameTask(Task))
                .findFirst();
        if (TaskCardNode.isPresent()) {
            return new TaskCardHandle(guiRobot, primaryStage, TaskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfPeople() {
        return getListView().getItems().size();
    }
}
