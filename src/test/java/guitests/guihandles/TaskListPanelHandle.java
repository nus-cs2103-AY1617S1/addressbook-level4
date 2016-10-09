package guitests.guihandles;


import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import seedu.taskman.TestApp;
import seedu.taskman.model.task.ReadOnlyTask;
import seedu.taskman.model.task.Task;
import seedu.taskman.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;

    private static final String PERSON_LIST_VIEW_ID = "#taskListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks() {
        TableView<ReadOnlyTask> taskList = getTableView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    // TODO Resolve generic type issue.
    @SuppressWarnings("unchecked")
    public TableView<ReadOnlyTask> getTableView() {
        return (TableView<ReadOnlyTask>) getNode(PERSON_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of task in the correct order.
     */
    public boolean isListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, tasks);
    }
    
    /**
     * Clicks on the TableView.
     */
    public void clickOnTableView() {
        Point2D point= TestUtil.getScreenMidPoint(getTableView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getTableView().getItems();

        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()){
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getTitle().title.equals(tasks[i].getTitle().title)){
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
        if (tasks.length + startPosition != getTableView().getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getTableView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getTableView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareRowAndTask(getTaskRowHandle(startPosition + i), tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public TaskRowHandle navigateToTask(String title) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getTableView().getItems().stream().filter(p -> p.getTitle().title.equals(title)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Title not found: " + title);
        }

        return navigateToTask(task.get());
    }

    /**
     * Navigates the TableView to display and select the task.
     */
    public TaskRowHandle navigateToTask(ReadOnlyTask task) {
        int index = getTaskIndex(task);

        guiRobot.interact(() -> {
            getTableView().scrollTo(index);
            guiRobot.sleep(150);
            getTableView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskRowHandle(task);
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask) {
        List<ReadOnlyTask> tasksInList = getTableView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if(tasksInList.get(i).getTitle().equals(targetTask.getTitle())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public ReadOnlyTask getTask(int index) {
        return getTableView().getItems().get(index);
    }

    public TaskRowHandle getTaskRowHandle(int index) {
        return new TaskRowHandle(guiRobot, primaryStage, getTableView().getItems().get(index));
    }

    public TaskRowHandle getTaskRowHandle(ReadOnlyTask task) {
        ObservableList<ReadOnlyTask> taskList = getTableView().getItems();
        Optional<ReadOnlyTask> hit = taskList.stream()
                .filter(n -> new TaskRowHandle(guiRobot, primaryStage, n).isSameTask(task))
                .findFirst();
        if (hit.isPresent()) {
            return new TaskRowHandle(guiRobot, primaryStage, hit.get());
        } else {
            return null;
        }
    }

    public int getNumberOfPeople() {
        return getTableView().getItems().size();
    }
}
