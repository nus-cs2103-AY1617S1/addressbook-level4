package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.MarkCommand.MESSAGE_MARK_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

/**
 * @@author A0121608N
 * Tests Mark Command for GUI Test.
 * 
 */

public class MarkCommandTest extends TaskBookGuiTest {

    @Test
    //mark the first in the list
    public void mark_firstIndex_success(){
        TestTask[] currentTaskList = td.getTypicalTasks();
        assertMarkTaskSuccess(1, currentTaskList);
    }
    
    @Test
    //mark the last in the list
    public void mark_lastIndex_success(){
        TestTask[] currentTaskList = td.getTypicalTasks();
        assertMarkTaskSuccess(currentTaskList.length, currentTaskList);
    }
    
    @Test
    //mark the middle in the list
    public void mark_middleIndex_success(){
        TestTask[] currentTaskList = td.getTypicalTasks();
        assertMarkTaskSuccess(currentTaskList.length/2, currentTaskList);
    }
    
    @Test
    //mark completed task in the list
    public void mark_completedTask_success(){
        TestTask[] currentTaskList = td.getTypicalTasks();
        assertMarkTaskSuccess(1, currentTaskList);
        commandBox.runCommand("list /t /a");
        commandBox.runCommand("mark 1");
        assertTrue(taskListPanel.isListMatching(currentTaskList));
    }
    
    @Test
    // mark an invalid index
    public void mark_invalidIndex_errorMessageShown() {
        TestTask[] currentTaskList = td.getTypicalTasks();
        commandBox.runCommand("mark " + currentTaskList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }

    /**
     * Runs the mark command to mark the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertMarkTaskSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("mark " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the marked task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_MARK_TASK_SUCCESS, targetIndexOneIndexed));
    }
}
