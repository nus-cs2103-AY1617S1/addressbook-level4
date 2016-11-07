package guitests.guihandles;

import java.util.List;
import static org.junit.Assert.*;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import w15c2.tusk.TestApp;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the person list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TASK_LIST_VIEW_ID = "#taskListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Task> getSelectedPersons() {
        ListView<Task> personList = getListView();
        return personList.getSelectionModel().getSelectedItems();
    }

    public ListView<Task> getListView() {
        return (ListView<Task>) getNode(TASK_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param persons A list of person in the correct order.
     */
    public boolean isListMatching(List<Task> tasks) {
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
    public boolean containsInOrder(int startPosition, List<Task> tasks) {
        List<Task> tasksInList = getListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.size() > tasksInList.size()){
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasksInList.get(startPosition + i).equals(tasks.get(i))){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the person details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of person in the correct order.
     */
    public boolean isListMatching(int startPosition, List<Task> tasks) throws IllegalArgumentException {
        if (tasks.size() + startPosition != getListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView().getItems().size() - 1) + " persons");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.size(); i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getTaskCardHandle(startPosition + i), tasks.get(i))) {
                return false;
            }
        }
        return true;
    }



    /**
     * Navigates the listview to display and select the task.
     */
    public TaskCardHandle navigateToTask(Task task) {
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
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(Task targetTask) {
        List<Task> tasksInList = getListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if(tasksInList.get(i).equals(targetTask)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(getListView().getItems().get(index));
    }

    public TaskCardHandle getTaskCardHandle(Task task) {
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
