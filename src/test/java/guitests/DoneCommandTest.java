package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;
import seedu.task.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.logic.commands.DoneCommand.MESSAGE_MARK_TASK_SUCCESS;

//@@author A0138601M
public class DoneCommandTest extends AddressBookGuiTest {

    @Test
    public void done() {
        
        //mark the first in the incomplete list
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        int[] targetIndexes = new int[]{1};
        assertDoneSuccess(targetIndexes, currentList);

        //mark the last in the incomplete list
        currentList = TestUtil.markTaskFromList(currentList, targetIndexes);
        targetIndexes = new int[]{currentList.getIncompleteList().length};
        assertDoneSuccess(targetIndexes, currentList);

        //mark from the middle of the incomplete list
        currentList = TestUtil.markTaskFromList(currentList, targetIndexes);
        targetIndexes = new int[]{currentList.getIncompleteList().length/2};
        assertDoneSuccess(targetIndexes, currentList);
        
        //delete multiple
        currentList = TestUtil.markTaskFromList(currentList, targetIndexes);
        targetIndexes = new int[]{1,2};
        assertDoneSuccess(targetIndexes, currentList);

        //invalid index from incomplete list
        commandBox.runCommand("done " + currentList.getIncompleteList().length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the done command to mark the task at specified index from incomplete list and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking).
     */
    private void assertDoneSuccess(int[] targetIndexes, final TestTaskList currentList) {
        TestTaskList expectedRemainder = TestUtil.markTaskFromList(currentList, targetIndexes);   
        
        commandBox.runCommand(getCommand(targetIndexes));

        //confirm the incomplete list now contains all previous tasks except the marked task
        assertTrue(taskListPanel.isListMatching(expectedRemainder.getIncompleteList()));
        
        //confirm complete list contains all marked task
        assertTrue(completeTaskListPanel.isListMatching(expectedRemainder.getCompleteList()));

        //confirm the result message is correct
        assertResultMessage(MESSAGE_MARK_TASK_SUCCESS);
    }
    
    /**
     * Returns the command to be entered
     */
    private String getCommand(int[] targetIndexes) {
        StringBuilder builder = new StringBuilder();
        builder.append("done ");
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
