//@@author A0146107M
package guitests;

import org.junit.Before;
import org.junit.Test;

import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.tasklist.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteCommandTest extends TaskListGuiTest {
	TestTask[] currentList;
	int targetIndex;
	
	@Before
	public void initialize(){
		currentList = td.getTypicalTasks();
	}

    @Test
    public void deleteTestByIndex_deleteFirst_success() {
        //delete the first in the list
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);
    }
    
    @Test
    public void deleteTestByIndex_deleteLast_success() {
        //delete the last in the list
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);
    }
    
    @Test
    public void deleteTestByIndex_deleteFromMiddle_success() {
        //delete from the middle of the list
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);
    }
    
    @Test
    public void deleteTestByIndex_deleteRandom() {
        //delete any random task from the list
        targetIndex = (int)(Math.random()*currentList.length + 1);
        assertDeleteSuccess(targetIndex, currentList);
    }
    
    @Test
    public void deleteTestByIndex_indexOutOfBounds_deleteFail() {
        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }
    
    @Test
    public void deleteTestByString_oneMatch_success() {
    	//only 1 match
    	commandBox.runCommand("delete buy eggs");
    	assertResultMessage("Deleted Task: Buy Eggs");
    }
    
    @Test
    public void deleteTestByString_multipleMatches_selectionQuery() {
    	//2 matches
        commandBox.runCommand("delete complete");
        assertResultMessage("Multiple tasks were found containing the entered keywords. Please check below and delete by index.");
    }
    
    @Test
    public void deleteTestByString_noMatches_selectionQuery() {
    	//no matches
        commandBox.runCommand("delete asfsvsytrshr");
        assertResultMessage("No such task was found.");
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete.getTaskDetails()));
    }


}