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
        int targetIndex = 1;
        assertDoneSuccess(targetIndex, currentList);

        //mark the last in the incomplete list
        currentList = TestUtil.markTaskFromList(currentList, targetIndex);
        targetIndex = currentList.getIncompleteList().length;
        assertDoneSuccess(targetIndex, currentList);

        //mark from the middle of the incomplete list
        currentList = TestUtil.markTaskFromList(currentList, targetIndex);
        targetIndex = currentList.getIncompleteList().length/2;
        assertDoneSuccess(targetIndex, currentList);

        //invalid index from incomplete list
        commandBox.runCommand("done " + currentList.getIncompleteList().length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the done command to mark the task at specified index from incomplete list and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTaskList currentList) {
        TestTask taskToMark = currentList.getIncompleteList()[targetIndexOneIndexed - 1]; //-1 because array uses zero indexing
        TestTaskList expectedRemainder = TestUtil.markTaskFromList(currentList, targetIndexOneIndexed);   
        
        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm the incomplete list now contains all previous tasks except the marked task
        //and complete list contains all marked task
        assertTrue(taskListPanel.isListMatching(expectedRemainder.getIncompleteList()));
        assertTrue(completeTaskListPanel.isListMatching(expectedRemainder.getCompleteList()));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

}
