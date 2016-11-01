package guitests;

import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess("B"+targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess("B"+targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete B" + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first person in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(String targetIndexOneIndexed, final TestTask[] currentList) {
        int targetIndex = Integer.parseInt(targetIndexOneIndexed.trim().substring(1));
        TestTask personToDelete = currentList[targetIndex-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removePersonFromList(currentList, targetIndex);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person
        assertTrue(datedListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete));
    }

}
