package guitests;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.address.model.task.Complete;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class CompleteCommandTest extends AddressBookGuiTest {

    @Test
    public void complete() {

        // mark the first in the list as complete
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertCompleteSuccess(targetIndex, currentList);
        currentList = TestUtil.markTaskInListAsComplete(currentList, targetIndex);

        // mark the last in the list as complete
        targetIndex = currentList.length;
        assertCompleteSuccess(targetIndex, currentList);
        currentList = TestUtil.markTaskInListAsComplete(currentList, targetIndex);

        // mark the task in the middle of the list as complete
        targetIndex = currentList.length/2;
        assertCompleteSuccess(targetIndex, currentList);
        currentList = TestUtil.markTaskInListAsComplete(currentList, targetIndex);

        // invalid index
        commandBox.runCommand("complete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }

    /**
     * Runs the complete command to mark the task at specified index as complete and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to complete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking as complete).
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToMarkComplete = currentList[targetIndexOneIndexed-1]; 
        //-1 because array uses zero indexing
        TestTask[] expectedNewList = TestUtil.markTaskInListAsComplete(currentList, targetIndexOneIndexed);

        commandBox.runCommand("complete " + targetIndexOneIndexed);

        //confirm the list now contains all tasks with the new task marked as completed
        assertTrue(taskListPanel.isListMatching(expectedNewList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToMarkComplete));
    }

}
