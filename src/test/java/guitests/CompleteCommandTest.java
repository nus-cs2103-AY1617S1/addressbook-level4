package guitests;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS;;
//@@author A0147967J
/**
 * Tests if the correct task can be marked as done. 
 */
public class CompleteCommandTest extends TaskMasterGuiTest {

    @Test
    public void complete() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] completed = new TestTask[3];
        int targetIndex = 1;
        completed[0] = currentList[targetIndex-1];
        assertCompleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        completed[2] = currentList[targetIndex-1];
        assertCompleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = 3;
        completed[1] = currentList[targetIndex-1];
        assertCompleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
        //Check changes are reflected in Completed panel
        //Noted that completed tasks are not listed in the deleting order but adding order.
        commandBox.runCommand("find -C");
        assertTrue(taskListPanel.isListMatching(TestUtil.convertTasksToDateComponents(completed)));

    }

    /**
     * Runs the delete command to delete the floatingTask at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first floatingTask in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of floatingTasks (before deletion).
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToComplete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the list now contains all previous floatingTasks except the deleted floatingTask
        assertTrue(taskListPanel.isListMatching(TestUtil.convertTasksToDateComponents(expectedRemainder)));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }

}
