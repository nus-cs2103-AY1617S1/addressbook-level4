package guitests;

import guitests.guihandles.TaskCardViewHandle;
import org.junit.Test;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.CommandGeneratorUtil;
import seedu.todo.testutil.TaskBuilder;
import seedu.todo.testutil.TaskFactory;
import seedu.todo.testutil.TestUtil;
import seedu.todo.testutil.UiTestUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertTrue;

//@@author A0135805H
/**
 * Test the add command via GUI.
 * Note:
 *      Order-ness of the tasks is not tested.
 *      Invalid command input is not tested.
 */
public class AddCommandTest extends TodoListGuiTest {

    @Test
    public void add_initialData() {
        //Test if the data has correctly loaded the data into view.
        assertTrue(todoListView.isDisplayedCorrectly());
    }

    @Test
    public void add_addTasks() {
        //Add a task
        ImmutableTask task1 = TaskFactory.task();
        executeAddTestHelper(task1);

        //Add another task
        ImmutableTask task2 = TaskFactory.task();
        executeAddTestHelper(task2);

        //Add duplicated task
        executeAddTestHelper(task2);

        //Add an overdue task
        ImmutableTask task3 = TaskBuilder.name("Overdue Task")
                .due(LocalDateTime.now().minusHours(3))
                .build();
        executeAddTestHelper(task3);
    }

    @Test
    public void add_addEvents() {
        //Add an event
        ImmutableTask event1 = TaskFactory.event();
        executeAddTestHelper(event1);

        //Add an overdue event.
        ImmutableTask event2 = TaskBuilder.name("Overdue Event")
                .event(LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2))
                .build();
        executeAddTestHelper(event2);

        //Add an ongoing event.
        ImmutableTask event3 = TaskBuilder.name("Ongoing Event")
                .event(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusHours(2))
                .build();
        executeAddTestHelper(event3);

        //Add another event
        ImmutableTask event4 = TaskFactory.event();
        executeAddTestHelper(event4);

        //Add duplicated event
        executeAddTestHelper(event1);
    }

    @Test
    public void add_addManyRandom() {
        //Add a long list of random task, which in the end spans at least 2 pages.
        List<ImmutableTask> randomTaskList = TaskFactory.list(15, 25);
        randomTaskList.forEach(this::executeAddTestHelper);
    }

    @Test
    public void add_invalidParameters() {
        //No title
        assertErrorViewDisplayed("add /d tomorrow");

        //Wrong date
        assertErrorViewDisplayed("add some task /d hehe");

        //Invalid tag
        assertErrorViewDisplayed("add invalid tag /t oops!");
    }

    /* Helper Methods */
    /**
     * Gets the index of the newly added task.
     */
    private int getNewlyAddedTaskIndex() {
        return TestUtil.compareAndGetIndex(previousTasksFromView, todoListView.getImmutableTaskList());
    }

    /**
     * A helper method to run the entire add command process and testing.
     */
    private void executeAddTestHelper(ImmutableTask task) {
        updatePreviousTaskListFromView();
        executeAddCommand(task);
        assertCorrectnessHelper(task);
    }

    /**
     * Executes an add command given a {@code task}
     */
    private void executeAddCommand(ImmutableTask task) {
        String commandText = CommandGeneratorUtil.generateAddCommand(task);
        runCommand(commandText);
    }

    /**
     * Asserts that specific view scenarios are displayed correctly.
     */
    private void assertCorrectnessHelper(ImmutableTask newTask) {
        int addedIndex = getNewlyAddedTaskIndex();
        TaskCardViewHandle taskCardHandle = todoListView.getTaskCardViewHandle(addedIndex);

        assertAddSuccess(taskCardHandle, newTask, addedIndex);
        assertCorrectFeedbackDisplayed(newTask);
        assertCollapsed(taskCardHandle);
    }

    /**
     * Check the two following areas:
     *      1. If the {@code task} added to the view is reflected correctly in it's own task card view.
     *      2. If the remaining task cards are present and still displayed correctly in their own
     *         respective card views.
     */
    private void assertAddSuccess(TaskCardViewHandle newTaskHandle, ImmutableTask newTask, int addedListIndex) {
        int expectedDisplayedIndex = UiTestUtil.convertToUiIndex(addedListIndex);

        //Test for the newly added task.
        assertTrue(newTaskHandle.isDisplayedCorrectly(expectedDisplayedIndex, newTask));

        //Test for remaining tasks.
        assertTrue(todoListView.isDisplayedCorrectly());
    }

    /**
     * Check if the correct feedback message for adding has been displayed to the user.
     */
    private void assertCorrectFeedbackDisplayed(ImmutableTask task) {
        assertFeedbackMessage("");
    }

    /**
     * Tasks should be collapsed when newly added.
     * Checks the case where if the task is collapsible,
     * then the task is in the collapsed state when newly added.
     */
    private void assertCollapsed(TaskCardViewHandle newTask) {
        if (newTask.isTaskCollapsible()) {
            assertTrue(newTask.isTaskCardCollapsed());
        }
    }
}