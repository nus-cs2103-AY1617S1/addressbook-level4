package guitests;

import org.junit.Test;

import taskle.testutil.TestTask;
import taskle.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static taskle.logic.commands.RemoveCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class RemoveCommandTest extends AddressBookGuiTest {

    @Test
    public void remove() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertRemoveSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertRemoveSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertRemoveSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("remove " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertRemoveSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("remove " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
