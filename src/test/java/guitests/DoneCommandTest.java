package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_DONE_ITEM_SUCCESS;

public class DoneCommandTest extends AddressBookGuiTest {

    @Test
    public void done() {

        //archive the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList);

        //archive the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDoneSuccess(targetIndex, currentList);

        //archive from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDoneSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The item index provided is invalid");
        
    }

    /**
     * Runs the done command to archive the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to archive the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the archived task
        assertTrue(personListPanel.isListMatching(expectedRemainder));

        //confirm the done list now contains all previous done tasks plus the new done task
        
        
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_ITEM_SUCCESS, taskToDone));
    }

}