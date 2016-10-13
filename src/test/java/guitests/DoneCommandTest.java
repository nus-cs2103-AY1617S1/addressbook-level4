package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tasklist.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;

import org.junit.Test;

import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;

public class DoneCommandTest extends TaskListGuiTest {
	
    @Test
    public void done() {

        TestTask[] currentList = td.getTypicalTasks();

        //mark the first in the list as complete
        int targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList);

        //mark the last in the list as complete
        currentList = TestUtil.markTaskAsComplete(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDoneSuccess(targetIndex, currentList);

        //mark the middle of the list as complete
        currentList = TestUtil.markTaskAsComplete(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDoneSuccess(targetIndex, currentList);
        
        //mark any random task from the list as complete
        currentList = TestUtil.markTaskAsComplete(currentList, targetIndex);
        targetIndex = (int)(Math.random()*currentList.length + 1);
        assertDoneSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToMark = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedList = TestUtil.markTaskAsComplete(currentList, targetIndexOneIndexed);

        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToMark));
    }
    
}
