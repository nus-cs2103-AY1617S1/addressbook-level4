package guitests;

import org.junit.Test;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.testutil.TestDeadline;
import seedu.malitio.testutil.TestEvent;
import seedu.malitio.testutil.TestFloatingTask;
import seedu.malitio.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.malitio.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

//@@author a0126633j
public class DeleteCommandTest extends MalitioGuiTest {
  
    private static final String FLOATING_TASK_KEYWORD = "f";
    private static final String DEADLINE_KEYWORD = "d";
    private static final String EVENT_KEYWORD = "e";
    
    @Test
    public void delete() {

        //Populate test tasks
        TestFloatingTask[] floatingTaskList = td.getTypicalFloatingTasks(); //5 floating tasks
        TestDeadline[] deadlineList = td.getTypicalDeadlines(); // 2 deadlines
        TestEvent[] eventList = td.getTypicalEvents(); // 4 events
        
        //invalid index argument
        commandBox.runCommand("delete " + FLOATING_TASK_KEYWORD + floatingTaskList.length + 1); // boundary
        assertResultMessage("The task index provided is invalid");
        commandBox.runCommand("delete " + EVENT_KEYWORD + 0); // boundary
        assertResultMessage("The task index provided is invalid");
        commandBox.runCommand("delete " + "g1"); // invalid task type
        assertResultMessage("The task index provided is invalid");
        
        // to save time, work on each list because each list is identical
        //delete first in floating task
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, floatingTaskList);

        //delete the last in deadline
        targetIndex = deadlineList.length;   
        assertDeleteSuccess(targetIndex, deadlineList);

        //delete from the middle of event list
        targetIndex = eventList.length/2;
        assertDeleteSuccess(targetIndex, eventList);


    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param floatingTaskList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestFloatingTask[] floatingTaskList) {
        TestFloatingTask taskToDelete = floatingTaskList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestFloatingTask[] expectedRemainder = TestUtil.removeTasksFromList(floatingTaskList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + FLOATING_TASK_KEYWORD + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(floatingTaskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
    
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestDeadline[] deadlineList) {
        TestDeadline taskToDelete = deadlineList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestDeadline[] expectedRemainder = TestUtil.removeTasksFromList(deadlineList, taskToDelete);

        commandBox.runCommand("delete " + DEADLINE_KEYWORD + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(deadlineListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
    
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestEvent[] eventList) {
        TestEvent taskToDelete = eventList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestEvent[] expectedRemainder = TestUtil.removeTasksFromList(eventList, taskToDelete);

        commandBox.runCommand("delete " + EVENT_KEYWORD + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        try {
            assertTrue(eventListPanel.isListMatching(expectedRemainder));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
