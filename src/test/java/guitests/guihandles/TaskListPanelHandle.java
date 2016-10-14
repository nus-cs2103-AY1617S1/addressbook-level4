package guitests.guihandles;


import guitests.GuiRobot;
import harmony.mastermind.TestApp;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.testutil.TestUtil;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the task list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;

    private static final String TASK_LIST_VIEW_ID = "#taskTable";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<ReadOnlyTask> getSelectedTasks() {
        TableView<ReadOnlyTask> taskList = getTableView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public TableView<ReadOnlyTask> getTableView() {
        return (TableView<ReadOnlyTask>) getNode(TASK_LIST_VIEW_ID);

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
            if (!tasksInList.get(startPosition + i).getName().equals(tasks[i].getName())){
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
    //@@author A0124797R
    public boolean isListMatching(int startPosition, ReadOnlyTask... tasks) throws IllegalArgumentException {
        List<ReadOnlyTask> table = getTableView().getItems();
        if (tasks.length + startPosition != table.size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (table.size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, tasks));
        
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getTableView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            
            if (!TestUtil.compareTasks(table.get(i), tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public void navigateToTask(String name) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getTableView().getItems().stream().filter(p -> p.getName().equals(name)).findAny();


        int index = getTaskIndex(task.get());
        
        System.out.println("getting task: " + index);

        guiRobot.interact(() -> {
            getTableView().scrollTo(index);
            guiRobot.sleep(150);
            getTableView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        
    }


    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask) {
        System.out.println("inside");
        List<ReadOnlyTask> tasksInList = getTableView().getItems();

        System.out.println("targetTask: " + targetTask.getName());
        for (int i = 0; i < tasksInList.size(); i++) {
            String name = tasksInList.get(i).getName();
            System.out.println("row " + i + ": " + name);
            if(name.equals(targetTask.getName())){
                return i;
            }
        }
        System.out.println("failed");
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public ReadOnlyTask getTask(int index) {
        return getTableView().getItems().get(index);
    }

    public int getNumberOfTask() {
        return getTableView().getItems().size();
    }
}
