package guitests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteCommandTest extends TaskManagerGuiTest {
	
    @Test
    public void delete() {
        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasksNotDone();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);
        
        //delete multiple tasks
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        int[] targetIndices = new int[]{1, currentList.length};
        assertDeleteSuccess(targetIndices, currentList);

        //invalid index
        commandBox.runCommand("del " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    
    //@@author A0141019U-reused
    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
    	assertDeleteSuccess(new int[]{targetIndexOneIndexed}, currentList);
    }
    
    /**
     * Runs the delete command to delete the task at specified indices and confirms the result is correct.
     * @param targetIndicesOneIndexed e.g. indices {1, 3} to delete the first and third tasks in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int[] targetIndicesOneIndexed, final TestTask[] currentList) {
        TestTask[] tasksToDelete = new TestTask[targetIndicesOneIndexed.length];
        StringBuilder sbIndices = new StringBuilder();
        StringBuilder sbTasks = new StringBuilder();
        
        for (int i=0; i<targetIndicesOneIndexed.length; i++) {
        	tasksToDelete[i] = currentList[targetIndicesOneIndexed[i] - 1];
        	
        	sbIndices.append(targetIndicesOneIndexed[i] + " ");
        	if (i == 0) {
        		sbTasks.append(tasksToDelete[i]);
        	}
        	else {
        		sbTasks.append(", " + tasksToDelete[i]);
        	}
        }
        
        TestTask[] expectedRemainder = TestUtil.removeTasksFromListByIndex(currentList, targetIndicesOneIndexed);
         System.out.println("sb indices: " + sbIndices.toString());
        
        commandBox.runCommand("del " + sbIndices.toString());

        //confirm the list now contains all previous tasks except the deleted tasks
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        // TODO figure out why square brackets are needed
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, "[" + sbTasks.toString() + "]"));
    }

}