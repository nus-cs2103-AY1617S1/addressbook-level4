package guitests.guihandles;


import guitests.GuiRobot;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.testfx.service.finder.NodeFinderException;
import seedu.todo.TestApp;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.TestUtil;
import seedu.todo.testutil.UiTestUtil;
import seedu.todo.ui.view.TodoListView;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Provides a handle for the {@link TodoListView}
 * containing a list of tasks.
 */
public class TodoListViewHandle extends GuiHandle {

    /* Constants */
    public static final int NOT_FOUND = -1;
    private static final String TASK_CARD_ID = "#taskCard";
    private static final String TODO_LIST_VIEW_ID = "#todoListView";

    /**
     * Constructs a handle for {@link TodoListView}.
     *
     * @param guiRobot The GUI test robot.
     * @param primaryStage The main stage that is executed from the application's UI.
     */
    public TodoListViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /* View Element Helper Methods */
    /**
     * Gets an instance of {@link ListView} of {@link TodoListView}
     */
    public ListView<ImmutableTask> getTodoListView() {
        return (ListView<ImmutableTask>) getNode(TODO_LIST_VIEW_ID);
    }

    /**
     * Gets a list of {@link ImmutableTask}
     */
    public List<ImmutableTask> getImmutableTaskList() {
        return getTodoListView().getItems();
    }

    /**
     * Gets a set of task card nodes in this to-do list.
     */
    public Set<Node> getAllTaskCardNodes() {
        return guiRobot.lookup(TASK_CARD_ID).queryAll();
    }

    /**
     * Gets a specific task from the list of tasks stored in the view by the list index.
     */
    public ImmutableTask getTask(int listIndex) {
        return getImmutableTaskList().get(listIndex);
    }

    /**
     * Gets the first occurring element from {@link #getImmutableTaskList()}
     * where the detail matches the {@code task} param.
     *
     * @return Returns a value bounded from 0 to length - 1.
     *         Also returns {@code NOT_FOUND} (value of -1) if not found in the list.
     */
    public int getFirstTaskIndex(ImmutableTask task) {
        List<ImmutableTask> tasks = getImmutableTaskList();
        for (int listIndex = 0; listIndex < tasks.size(); listIndex++) {
            ImmutableTask taskInList = tasks.get(listIndex);
            if (TestUtil.isShallowEqual(task, taskInList)) {
                return listIndex;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a {@link TaskCardViewHandle} object with the position {@code listIndex} located in the to-do list view.
     * Guaranteed unique result for identical tasks.
     * Inherits the behaviour from {@link #getTaskCardViewNode(int)}.
     *
     * @param listIndex Index of the {@link #getImmutableTaskList()}.
     * @return An instance of the handle.
     */
    public TaskCardViewHandle getTaskCardViewHandle(int listIndex) {
        Node taskCardNode = getTaskCardViewNode(listIndex);
        if (taskCardNode != null) {
            return new TaskCardViewHandle(guiRobot, primaryStage, taskCardNode);
        } else {
            return null;
        }
    }

    /**
     * Gets a {@link Node} object with the position {@code listIndex} located in the to-do list view.
     * Guarantees:
     *      -  Unique result for identical tasks, because we are referencing from the index displayed
     *         in the UI.
     *      -  The object returned will never be null.
     *         This means if I can't find the object, an exception will be thrown.
     * Behaviour:
     *      Because a task card node is only drawn when the task card is shown on the screen,
     *      this method will automatically scroll the to-do list view to the position where the
     *      node is drawn.
     *
     * @param listIndex Index of the {@link #getImmutableTaskList()}
     *                  (must be a valid value from 0 to length of task list - 1,
     *                  else {@link RuntimeException} is thrown.
     * @return An instance of the node. Guarantees non-null.
     * @throws NodeFinderException if we can't find the node after finite attempts.
     */
    private Node getTaskCardViewNode(int listIndex) throws NodeFinderException {
        int displayedIndex = UiTestUtil.convertToUiIndex(listIndex);
        Optional<Node> possibleNode;
        int attemptCounter = 0;

        do {
            Platform.runLater(() -> getTodoListView().scrollTo(listIndex));
            guiRobot.sleep(50 * attemptCounter++); //Allow the new nodes to be loaded from scrolling.

            Set<Node> taskCardNodes = getAllTaskCardNodes();
            possibleNode = taskCardNodes.stream().filter(node -> {
                TaskCardViewHandle taskCardView = new TaskCardViewHandle(guiRobot, primaryStage, node);
                return taskCardView.matchesTask(displayedIndex);
            }).findFirst();
        } while (!possibleNode.isPresent() && attemptCounter < 50);

        if (possibleNode.isPresent()) {
            return possibleNode.get();
        } else {
            String errorMessage = "Either the node fails to draw on the screen, or you provided an invalid index "
                                  + listIndex + " where the number of nodes is " + getAllTaskCardNodes().size();
            throw new NodeFinderException(errorMessage, NodeFinderException.ErrorType.NO_NODES_FOUND);
        }
    }

    /**
     * Checks if all the tasks stored inside the to-do list are displayed correctly.
     * Note: This does not check the sorted-ness of the list.
     * @return True if all the items are correctly displayed.
     */
    public boolean isDisplayedCorrectly() {
        return doesTodoListMatch();
    }

    /**
     * Given a list of tasks, check if all the tasks in the list are displayed correctly in the
     * {@link TodoListView}.
     * Note: this does not check the sorted-ness of the list.
     *
     * @return True if all the tasks in the {@code tasks} are displayed correctly.
     */
    public boolean doesTodoListMatch() {
        boolean outcome = true;
        List<ImmutableTask> tasks = getImmutableTaskList();
        for (int listIndex = 0; listIndex < tasks.size(); listIndex++) {
            ImmutableTask task = tasks.get(listIndex);
            TaskCardViewHandle handle = getTaskCardViewHandle(listIndex);
            int displayedIndex = UiTestUtil.convertToUiIndex(listIndex);
            outcome &= handle.isDisplayedCorrectly(displayedIndex, task);
        }
        return outcome;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point= TestUtil.getScreenMidPoint(getTodoListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ImmutableTask... tasks) {
        List<ImmutableTask> taskList = getImmutableTaskList();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > taskList.size()){
            return false;
        }

        // Return false if any of the task doesn't match
        for (int i = 0; i < tasks.length; i++) {
            ImmutableTask taskInView = taskList.get(i);
            ImmutableTask taskInList = tasks[i];
            if (!taskInView.equals(taskInList)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Navigates the {@link TodoListView} to display and select the task.
     * @param listIndex Index of the list that the list view should navigate to.
     * @return Handle of the selected task.
     */
    public TaskCardViewHandle navigateToTask(int listIndex) {
        guiRobot.interact(() -> {
            getTodoListView().scrollTo(listIndex);
            guiRobot.sleep(150);
            getTodoListView().getSelectionModel().select(listIndex);
        });
        guiRobot.sleep(100);
        return getTaskCardViewHandle(listIndex);
    }
}
