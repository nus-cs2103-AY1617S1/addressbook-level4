package guitests;

import org.junit.Test;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.CommandGeneratorUtil;
import seedu.todo.testutil.UiTestUtil;

import java.util.List;
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

    @Test
    public void delete() {
        List<ImmutableTask> initialData = initialTaskData;

        //delete the last item in the list
        executeDeleteHelper(initialData.size());

        //delete the first in the list
        if (initialTaskData.size() > 1) {
            executeDeleteHelper(1);
        }

        //delete a random item in the list (if we have enough size.
        if (initialTaskData.size() > 2) {
            Random random = new Random();
            executeDeleteHelper(1 + random.nextInt(initialTaskData.size() - 3));
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
        String commandText = CommandGeneratorUtil.generateDeleteCommand(displayedIndex);
        ImmutableTask deletedTask = todoListView.getTask(UiTestUtil.convertToListIndex(displayedIndex));
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
