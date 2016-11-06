package guitests;

import org.junit.Test;

import seedu.lifekeeper.testutil.TestActivity;
import seedu.lifekeeper.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.lifekeeper.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
//@@author A0125097A
public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestActivity[] currentList = td.getTypicalActivities();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeActivityFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeActivityFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the activity at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first activity in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestActivity[] currentList) {
        TestActivity personToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestActivity[] expectedRemainder = TestUtil.removeActivityFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted activity
        assertTrue(activityListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete.getAsText()));
    }

}
