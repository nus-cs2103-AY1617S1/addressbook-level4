package guitests;

import guitests.guihandles.TaskCardViewHandle;
import org.junit.Test;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.CommandGeneratorUtil;
import seedu.todo.testutil.TaskFactory;
import seedu.todo.testutil.TestUtil;
import seedu.todo.testutil.UiTestUtil;

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
    }

    @Test
    public void add_addEvents() {
        //Add an event
        ImmutableTask event1 = TaskFactory.event();
        executeAddTestHelper(event1);

        //Add another event
        ImmutableTask event2 = TaskFactory.event();
        executeAddTestHelper(event2);

        //Add duplicated task
        executeAddTestHelper(event1);
    }

    @Test
    public void add_addRandom() {
        //Add a random task
        ImmutableTask random1 = TaskFactory.random();
        executeAddTestHelper(random1);

        //Add another random task
        ImmutableTask random2 = TaskFactory.random();
        executeAddTestHelper(random2);
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
        assertAddSuccess(task);
        assertCorrectFeedbackDisplayed(task);
    }

    /**
     * Executes an add command given a {@code task}
     */
    private void executeAddCommand(ImmutableTask task) {
        String commandText = CommandGeneratorUtil.generateAddCommand(task);
        runCommand(commandText);
    }

    /**
     * Check if the {@code task} added to the view is reflected correctly,
     * and the remaining tasks remains in the list, and is displayed correctly.
     */
    private void assertAddSuccess(ImmutableTask task) {
        //Test if this single add is correct.
        int addedIndex = getNewlyAddedTaskIndex();
        int expectedDisplayedIndex = UiTestUtil.convertToUiIndex(addedIndex);
        TaskCardViewHandle taskCard = todoListView.getTaskCardViewHandle(addedIndex);
        assertTrue(taskCard.isDisplayedCorrectly(expectedDisplayedIndex, task));

        //Test if the remaining list is correct.
        assertTrue(todoListView.isDisplayedCorrectly());
    }

    /**
     * Check if the correct feedback message for adding has been displayed to the user.
     */
    private void assertCorrectFeedbackDisplayed(ImmutableTask task) {
        assertFeedbackMessage("\'" + task.getTitle() + "\' successfully added!");
    }
}