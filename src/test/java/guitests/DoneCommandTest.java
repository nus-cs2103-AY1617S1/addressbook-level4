package guitests;

import org.junit.Test;

import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;

/*
 * @@author A0141128R
 */

public class DoneCommandTest extends ToDoListGuiTest {

    @Test
    public void done() {

        //done the first task in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertdoneSuccess(targetIndex, currentList);

        //done a task that is the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertdoneSuccess(targetIndex, currentList);

        //done task from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertdoneSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the done command to change the task done status at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to done the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before done).
     */
    private void assertdoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the done task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
        
        //need to write test for listing after done to show it wont be shown

//        //confirm the result message is correct
//        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
    }

}
