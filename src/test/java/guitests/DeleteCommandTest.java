package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.task.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        int[] targetIndexes = new int[]{1};
        assertDeleteSuccess(targetIndexes, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndexes);
        targetIndexes = new int[]{currentList.getIncompleteList().length};
        assertDeleteSuccess(targetIndexes, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndexes);
        targetIndexes = new int[]{currentList.getIncompleteList().length/2};
        assertDeleteSuccess(targetIndexes, currentList);
        
        //delete multiple
        currentList = TestUtil.removeTaskFromList(currentList, targetIndexes);
        targetIndexes = new int[]{1,2};
        assertDeleteSuccess(targetIndexes, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.getIncompleteList().length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexes a list of indexes to be deleted
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int[] targetIndexes, final TestTaskList currentList) {
        TestTaskList expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexes);

        commandBox.runCommand(getCommand(targetIndexes));
        
        //confirm the incomplete list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder.getIncompleteList()));
        
        //confirm the complete list remains unchanged
        assertTrue(completeTaskListPanel.isListMatching(expectedRemainder.getCompleteList()));

        //confirm the result message is correct
        assertResultMessage(MESSAGE_DELETE_TASK_SUCCESS);
    }
    
    /**
     * Returns the command to be entered
     */
    private String getCommand(int[] targetIndexes) {
        StringBuilder builder = new StringBuilder();
        builder.append("delete ");
        for (int i = 0; i < targetIndexes.length; i++) {
            if (i == targetIndexes.length - 1) {
                builder.append(targetIndexes[i]);
            }
            else {
                builder.append(targetIndexes[i] + ", ");
            }
        }
        return builder.toString();
    }

}
