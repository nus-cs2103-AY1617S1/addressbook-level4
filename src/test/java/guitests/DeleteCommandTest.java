package guitests;

import org.junit.Test;

import seedu.address.testutil.TestActivity;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.menion.logic.commands.DeleteCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS;

public class DeleteCommandTest extends ActivityManagerGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestActivity[] currentList = td.getTypicalFloatingTask();

        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);
        

        /*delete the last in the list
        currentList = td.getTypicalActivity();
        currentList = TestUtil.removeActivityFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        System.out.println("targetIndex: "+  targetIndex);
        assertDeleteSuccess(targetIndex, currentList);

        
        //delete from the middle of the list
        currentList = td.getTypicalActivity();
        currentList = TestUtil.removeActivityFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);
    */
        //invalid index
        commandBox.runCommand("delete task " + currentList.length + 1);
        assertResultMessage("The activity index provided is invalid");

    }

    /**
     * Runs the delete command to delete the activity at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first activity in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestActivity[] currentList) {
        TestActivity activityToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestActivity[] expectedRemainder = TestUtil.removeActivityFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete floating " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted activity
        assertTrue(activityListPanel.isFloatingTaskListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete));
    }

}
