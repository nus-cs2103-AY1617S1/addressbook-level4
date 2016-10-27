package guitests;

import org.junit.Test;

import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.MarkCommand.MESSAGE_MARK_TASK_SUCCESS;

/**
 * Tests Mark Command for GUI Test.
 * @@author A0121608N
 */

public class MarkCommandTest extends TaskBookGuiTest {

    @Test
    public void mark() {

        //mark the first in the list
        TestTask[] currentTaskList = td.getTypicalTasks();
        int targetIndex = 1;
        assertMarkTaskSuccess(targetIndex, currentTaskList);

        //mark the last in the list
        currentTaskList = TestUtil.removeTaskFromList(currentTaskList, targetIndex);
        targetIndex = currentTaskList.length;
        assertMarkTaskSuccess(targetIndex, currentTaskList);

        //mark from the middle of the list
        currentTaskList = TestUtil.removeTaskFromList(currentTaskList, targetIndex);
        targetIndex = currentTaskList.length/2;
        assertMarkTaskSuccess(targetIndex, currentTaskList);

        //invalid index
        commandBox.runCommand("mark " + currentTaskList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the mark command to mark the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertMarkTaskSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToMark = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("mark " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the marked task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_MARK_TASK_SUCCESS, targetIndexOneIndexed));
    }
}
