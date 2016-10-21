package guitests.guihandles;


import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.taskitty.TestApp;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.testutil.TestUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Provides a handle for the panel containing the person list.
 */
public class TaskListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String TODO_LIST_VIEW_ID = "#taskListView";
    private static final String DEADLINE_LIST_VIEW_ID = "#deadlineListView";
    private static final String EVENT_LIST_VIEW_ID = "#eventListView";

    public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Returns a list depending on the type of task
     * 
     * @param type according to Task. Either TASK_COMPONENT_COUNT, DEADLINE_COMPONENT_COUNT or EVENT_COMPONENT_COUNT
     */
    public List<ReadOnlyTask> getSelectedTasks(int type) {
        ListView<ReadOnlyTask> personList = getListView(type);
        return personList.getSelectionModel().getSelectedItems();
    }

    /**
     * Returns a list view depending on the type of task
     * 
     * @param type according to Task class. Either TASK_COMPONENT_COUNT, DEADLINE_COMPONENT_COUNT or EVENT_COMPONENT_COUNT
     */
    public ListView<ReadOnlyTask> getListView(int type) {
        assert type == Task.TASK_COMPONENT_COUNT
                || type == Task.DEADLINE_COMPONENT_COUNT
                || type == Task.EVENT_COMPONENT_COUNT;
        
        ListView<ReadOnlyTask> listView;
        
        if (type == Task.TASK_COMPONENT_COUNT) {
            listView = (ListView<ReadOnlyTask>) getNode(TODO_LIST_VIEW_ID);
        } else if (type == Task.DEADLINE_COMPONENT_COUNT) {
            listView = (ListView<ReadOnlyTask>) getNode(DEADLINE_LIST_VIEW_ID);
        } else { //it must be event
            listView = (ListView<ReadOnlyTask>) getNode(EVENT_LIST_VIEW_ID);
        }
    
        return listView;
    }
    
    public ListView<ReadOnlyTask> getDeadlineListView() {
        return (ListView<ReadOnlyTask>) getNode(DEADLINE_LIST_VIEW_ID);
    }
    
    public ListView<ReadOnlyTask> getEventListView() {
        return (ListView<ReadOnlyTask>) getNode(EVENT_LIST_VIEW_ID);
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param tasks A list of person in the correct order.
     */
    public boolean isTodoListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, Task.TASK_COMPONENT_COUNT, tasks);
    }
    
    public boolean isDeadlineListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, Task.DEADLINE_COMPONENT_COUNT, tasks);
    }
    
    public boolean isEventListMatching(ReadOnlyTask... tasks) {
        return this.isListMatching(0, Task.EVENT_COMPONENT_COUNT, tasks);
    }
    
    /**
     * Clicks on the ListView depending on type
     * Either TASK_COMPONENT_COUNT, DEADLINE_COMPONENT_COUNT or EVENT_COMPONENT_COUNT
     */
    public void clickOnListView(int type) {
        Point2D point= TestUtil.getScreenMidPoint(getListView(type));
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order) at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, int type, ReadOnlyTask... tasks) {
        List<ReadOnlyTask> tasksInList = getListView(type).getItems();
        // Return false if the list in panel is too short to contain the given list
        if (startPosition + tasks.length > tasksInList.size()){
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getName().fullName.equals(tasks[i].getName().fullName)){
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the list is showing the task details correctly and in correct order.
     * @param startPosition The starting position of the sub list.
     * @param tasks A list of tasks in the correct order.
     */
    public boolean isListMatching(int startPosition, int type, ReadOnlyTask... tasks) throws IllegalArgumentException {
        if (tasks.length + startPosition != getListView(type).getItems().size()) {
            throw new IllegalArgumentException("List size mismatched\n" +
                    "Expected " + (getListView(type).getItems().size()) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, type, tasks));
        for (int i = 0; i < tasks.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getListView(type).scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndPerson(getTaskCardHandle(startPosition + i, type), tasks[i])) {
                return false;
            }
        }
        return true;
    }


    public TaskCardHandle navigateToTask(String name, int type) {
        guiRobot.sleep(500); //Allow a bit of time for the list to be updated
        final Optional<ReadOnlyTask> task = getListView(type).getItems().stream().filter(p -> p.getName().fullName.equals(name)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task not found: " + name);
        }

        return navigateToTask(task.get(), type);
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public TaskCardHandle navigateToTask(ReadOnlyTask person, int type) {
        int index = getTaskIndex(person, type);

        guiRobot.interact(() -> {
            getListView(type).scrollTo(index);
            guiRobot.sleep(150);
            getListView(type).getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getTaskCardHandle(person);
    }


    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found in the list.
     */
    public int getTaskIndex(ReadOnlyTask targetTask, int type) {
        List<ReadOnlyTask> tasksInList = getListView(type).getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if(tasksInList.get(i).getName().equals(targetTask.getName())){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     */
    public ReadOnlyTask getTask(int index, int type) {
        return getListView(type).getItems().get(index);
    }

    public TaskCardHandle getTaskCardHandle(int index, int type) {
        return getTaskCardHandle(new Task(getListView(type).getItems().get(index)));
    }

    public TaskCardHandle getTaskCardHandle(ReadOnlyTask person) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new TaskCardHandle(guiRobot, primaryStage, n).isSamePerson(person))
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

    public int getNumberOfTasks(int type) {
        return getListView(type).getItems().size();
    }
}
