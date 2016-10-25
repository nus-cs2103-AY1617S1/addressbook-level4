package guitests;

import org.junit.Test;
import seedu.todo.model.TodoList;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.CommandGeneratorUtil;
import seedu.todo.testutil.UiTestUtil;

import java.util.Random;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

//@@author A0135805H
/**
 * Test the delete command via GUI.
 * Note:
 *      Invalid indices are not tested.
 */
public class DeleteCommandTest extends TodoListGuiTest {

    @Override
    protected TodoList getInitialData() {
        return getInitialDataHelper(10, 20);
    }

    @Test
    public void delete_correctBoundary() {
        //delete the last item in the list
        executeDeleteHelper(initialTaskData.size());

        //delete the first in the list (note that we initialised the size to be > 2.
        executeDeleteHelper(1);

    }

    @Test
    public void delete_allTasks() {
        //delete all the elements, until you have none left.
        Random random = new Random();
        int remainingTasks = initialTaskData.size();
        while (remainingTasks > 0) {
            int randomChoice = random.nextInt(remainingTasks--) + 1;
            executeDeleteHelper(randomChoice);
        }
    }

    /**
     * A helper method to run the entire delete command process and testing.
     */
    private void executeDeleteHelper(int displayedIndex) {
        ImmutableTask deletedTask = executeDeleteCommand(displayedIndex);
        assertDeleteSuccess(deletedTask);
        assertCorrectFeedbackDisplayed(deletedTask);
    }

    /**
     * Deletes a task from the to-do list view, and returns the deleted task for verification.
     */
    private ImmutableTask executeDeleteCommand(int displayedIndex) {
        int listIndex = UiTestUtil.convertToListIndex(displayedIndex);
        String commandText = CommandGeneratorUtil.generateDeleteCommand(displayedIndex);
        ImmutableTask deletedTask = todoListView.getTask(listIndex);

        runCommand(commandText);
        return deletedTask;
    }

    /**
     * Check if the {@code task} deleted from the view is reflected correctly (i.e. it's no longer there)
     * and check if the remaining tasks are displayed correctly.
     */
    private void assertDeleteSuccess(ImmutableTask deletedTask) {
        //Check if the task is really deleted.
        assertFalse(todoListView.getImmutableTaskList().contains(deletedTask));

        //Test if the remaining list is correct.
        assertTrue(todoListView.isDisplayedCorrectly());
    }

    /**
     * Check if the correct feedback message for deleting has been displayed to the user.
     */
    private void assertCorrectFeedbackDisplayed(ImmutableTask task) {
        assertFeedbackMessage("\'" + task.getTitle() + "\' successfully deleted!");
    }

}
