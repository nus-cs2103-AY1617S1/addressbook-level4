package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS;
import static seedu.task.logic.commands.DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

/**
 * @@author A0121608N
 * Tests Delete Command for GUI Test.
 * 
 */

public class DeleteCommandTest extends TaskBookGuiTest {

    @Test
    //delete the first in the tasklist
    public void deleteTask_firstIndex_success(){
        TestTask[] currentTaskList = td.getTypicalTasks();
        assertDeleteTaskSuccess(1, currentTaskList);
    }
    
    @Test
    //delete the last in the tasklist
    public void deleteTask_lastIndex_success(){
        TestTask[] currentTaskList = td.getTypicalTasks();
        assertDeleteTaskSuccess(currentTaskList.length, currentTaskList);
    }
    
    @Test
    //delete the middle in the tasklist
    public void deleteTask_middleIndex_success(){
        TestTask[] currentTaskList = td.getTypicalTasks();
        assertDeleteTaskSuccess(currentTaskList.length/2, currentTaskList);
    }
    
    @Test
    // delete an invalid index in tasklist
    public void deleteTask_invalidIndex_errorMessageShown() {
        TestTask[] currentTaskList = td.getTypicalTasks();
        commandBox.runCommand("delete /t " + currentTaskList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }
    
    @Test
    //delete the first in the eventlist
    public void deleteEvent_firstIndex_success(){
        TestEvent[] currentEventList = te.getTypicalNotCompletedEvents();
        assertDeleteEventSuccess(1, currentEventList);
    }
    
    @Test
    //delete the last in the eventlist
    public void deleteEvent_lastIndex_success(){
        TestEvent[] currentEventList = te.getTypicalNotCompletedEvents();
        assertDeleteEventSuccess(currentEventList.length, currentEventList);
    }
    
    @Test
    //delete the middle in the eventlist
    public void deleteEvent_middleIndex_success(){
        TestEvent[] currentEventList = te.getTypicalNotCompletedEvents();
        assertDeleteEventSuccess(currentEventList.length/2, currentEventList);
    }
    
    @Test
    // delete an invalid index in eventlist
    public void deleteEvent_invalidIndex_errorMessageShown() {
        TestEvent[] currentEventList = te.getTypicalNotCompletedEvents();
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
