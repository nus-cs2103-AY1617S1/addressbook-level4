package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import tars.TestApp;
import tars.model.task.rsv.RsvTask;
import tars.testutil.TestUtil;
import tars.ui.MainWindow;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the task list.
 */
public class RsvTaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String RSV_TASK_LIST_VIEW_ID = "#rsvTaskListView";
    private static final String TAB_PANEL_ROOT_FIELD_ID = "#tabPane";

    public RsvTaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<RsvTask> getSelectedTasks() {
        ListView<RsvTask> rsvtaskList = getRsvListView();
        return rsvtaskList.getSelectionModel().getSelectedItems();
    }

    public ListView<RsvTask> getRsvListView() {
        return (ListView<RsvTask>) getNode(RSV_TASK_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(RsvTask... tasks) {
        return this.isListMatching(0, tasks);
    }
    
    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getRsvListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, RsvTask... tasks) {
        List<RsvTask> tasksInList = getRsvListView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()){
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().taskName.equals(tasks[i].getName().taskName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the RsvTask details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of RsvTask in the correct order.
     */
    public boolean isListMatching(int startPosition, RsvTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getRsvListView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getRsvListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getRsvListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndRsvTask(getRsvTaskCardHandle(startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public RsvTaskCardHandle navigateToRsvTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<RsvTask> task = getRsvListView().getItems().stream().filter(p -> p.getName().taskName.equals(name)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Name not found: " + name);
        }

        return navigateToRsvTask(task.get());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public RsvTaskCardHandle navigateToRsvTask(RsvTask task) {
        int index = getRsvTaskIndex(task);

        guiRobot.interact(() -> {
            getRsvListView().scrollTo(index);
            guiRobot.sleep(150);
            getRsvListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getRsvTaskCardHandle(task);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getRsvTaskIndex(RsvTask targetTask) {
        List<RsvTask> tasksInList = getRsvListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if(tasksInList.get(i).getName().equals(targetTask.getName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a rsv task from the list by index
     */
    public RsvTask getRsvTask(int index) {
        return getRsvListView().getItems().get(index);
    }

    public RsvTaskCardHandle getRsvTaskCardHandle(int index) {
        return getRsvTaskCardHandle(new RsvTask(getRsvListView().getItems().get(index)));
    }

    public RsvTaskCardHandle getRsvTaskCardHandle(RsvTask task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new RsvTaskCardHandle(guiRobot, primaryStage, n).isSameRsvTask(task))
                .findFirst();
        if (taskCardNode.isPresent()) {
            return new RsvTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTasks() {
        return getRsvListView().getItems().size();
    }
    
    public boolean isTabSelected() {
        return ((TabPane) getNode(TAB_PANEL_ROOT_FIELD_ID)).getSelectionModel().isSelected(MainWindow.RSV_TASK_LIST_PANEL_TAB_PANE_INDEX);
    }
}
