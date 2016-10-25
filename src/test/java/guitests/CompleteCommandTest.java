package guitests;

import static org.junit.Assert.*;
import static seedu.jimi.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TestUtil;

public class CompleteCommandTest extends AddressBookGuiTest {

    @Test
    public void complete() {

        //complete the first task in the list
        TestFloatingTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertCompleteSuccess(targetIndex, currentList);

        //complete the last in the list
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertCompleteSuccess(targetIndex, currentList);

        //complete from the middle of the list
        currentList = TestUtil.completeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertCompleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("complete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the complete command to complete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before completion).
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, final TestFloatingTask[] currentList) {
        TestFloatingTask taskToComplete = currentList[targetIndexOneIndexed - 1]; //-1 because array uses zero indexing

        commandBox.runCommand("complete t" + targetIndexOneIndexed);

        //confirm the list now contains the task being set to complete
        assertTrue(taskListPanel.getTask(targetIndexOneIndexed).isCompleted());

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToComplete));
    }

}
