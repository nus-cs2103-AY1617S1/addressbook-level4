//@@author A0144727B
package guitests;

import org.junit.Test;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.testutil.TestTask;
import static org.junit.Assert.assertEquals;
import seedu.ggist.model.task.ReadOnlyTask;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.ggist.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;

public class DoneCommandTest extends TaskManagerGuiTest {
    
    @Test
    public void done() throws IllegalArgumentException, IllegalValueException {
        
        //marks the first task in the list as done
        int targetIndex = 1;
        assertDoneSuccess(targetIndex);

        //marks the last task in the list as done
        targetIndex = taskListPanel.getNumberOfTasks();
        assertDoneSuccess(targetIndex);

        //marks a task from the middle of the list as done
        targetIndex = taskListPanel.getNumberOfTasks()/2 == 0? 1 : taskListPanel.getNumberOfTasks()/2;
        assertDoneSuccess(targetIndex);

        //invalid index
        commandBox.runCommand("done " + taskListPanel.getNumberOfTasks() + 1);
        assertResultMessage("The task index provided is invalid");

    }
    
    /**
     * Runs the done command to mark the task at specified index as done and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark done the first task in the list, 1 should be given as the target index.
     * @throws IllegalValueException 
     * @throws IllegalArgumentException 
     */
    private void assertDoneSuccess(int targetIndexOneIndexed) throws IllegalArgumentException, IllegalValueException {
        ReadOnlyTask taskToDone = taskListPanel.getTask(targetIndexOneIndexed - 1); //-1 because array uses zero indexing
        int number = taskListPanel.getNumberOfTasks();

        commandBox.runCommand("done " + targetIndexOneIndexed);
        
        //confirm the list now contains one lesser task
        assertListSize(number - 1);
        //confirms the task mark done is no longer on the listing view
        assertEquals(taskListPanel.getTaskIndex(taskToDone), -1);
        //confirm the task is marked done
        assertTrue(taskToDone.isDone());

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
    }

}