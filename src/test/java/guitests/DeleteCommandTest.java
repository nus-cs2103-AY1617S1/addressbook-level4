package guitests;

import org.junit.Test;

import seedu.lifekeeper.testutil.TestActivity;
import seedu.lifekeeper.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.lifekeeper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lifekeeper.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.lifekeeper.logic.commands.DeleteCommand.MESSAGE_USAGE;
import seedu.lifekeeper.commons.core.Messages;

//@@author A0125097A
public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete_validIndex() {
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
        targetIndex = currentList.length / 2;        
        assertDeleteSuccess(targetIndex, currentList);
    }
    
    @Test
    public void delete_invalidIndex() {
        TestActivity[] currentList = td.getTypicalActivities();
        int targetIndex = currentList.length + 1;
        commandBox.runCommand("delete " + targetIndex);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        commandBox.runCommand("delete " + 0);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    /**
     * Runs the delete command to delete the activity at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first activity in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of activities (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestActivity[] currentList) {
        TestActivity activityToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestActivity[] expectedRemainder = TestUtil.removeActivityFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous activities except the deleted activity
        assertTrue(activityListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, activityToDelete.getAsText()));
    }

}
