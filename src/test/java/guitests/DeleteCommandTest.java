package guitests;

import org.junit.Test;

import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.task.logic.commands.DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS;

/**
 * Tests Delete Command for GUI Test.
 * @@author A0121608N
 */

public class DeleteCommandTest extends TaskBookGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTask[] currentTaskList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteTaskSuccess(targetIndex, currentTaskList);

        //delete the last in the list
        currentTaskList = TestUtil.removeTaskFromList(currentTaskList, targetIndex);
        targetIndex = currentTaskList.length;
        assertDeleteTaskSuccess(targetIndex, currentTaskList);

        //delete from the middle of the list
        currentTaskList = TestUtil.removeTaskFromList(currentTaskList, targetIndex);
        targetIndex = currentTaskList.length/2;
        assertDeleteTaskSuccess(targetIndex, currentTaskList);

        //invalid index
        commandBox.runCommand("delete /t " + currentTaskList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
        commandBox.runCommand("clear /t /a");
        
        
        
        //delete the first in the list
        TestEvent[] currentEventList = te.getTypicalNotCompletedEvents();
        targetIndex = 1;
        assertDeleteEventSuccess(targetIndex, currentEventList);

        //delete the last in the list
        currentEventList = TestUtil.removeEventFromList(currentEventList, targetIndex);
        targetIndex = currentEventList.length;
        assertDeleteEventSuccess(targetIndex, currentEventList);

        //invalid index
        commandBox.runCommand("delete /e " + currentEventList.length + 1);
        assertResultMessage("The event index provided is invalid");

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteTaskSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete /t " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    
    /**
     * Runs the delete command to delete the event at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first event in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of events (before deletion).
     */
    private void assertDeleteEventSuccess(int targetIndexOneIndexed, final TestEvent[] currentList) {
        TestEvent eventToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestEvent[] expectedRemainder = TestUtil.removeEventFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete /e " + targetIndexOneIndexed);

        //confirm the list now contains all previous events except the deleted event
        assertTrue(eventListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }
}
