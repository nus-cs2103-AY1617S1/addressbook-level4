package guitests;

/**
 * @@author A0121533W
 */

import org.junit.Test;
import tars.commons.exceptions.IllegalValueException;
import tars.logic.commands.MarkCommand;
import tars.model.task.Status;
import tars.testutil.TestTask;
import tars.testutil.TestUtil;

import static org.junit.Assert.*;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


public class MarkCommandTest extends TarsGuiTest {

    @Test
    public void mark() throws IllegalValueException {
        // Initialize Tars list
        TestTask[] currentList = td.getTypicalTasks();

        // Mark tasks as done
        commandBox.runCommand("mark /do 1 2 3 4 5 6 7");

        // Confirm the list now contains the specified tasks to be mark as done
        Status done = new Status(true);
        int[] indexesToMarkDone = {1, 2, 3, 4, 5, 6, 7};
        TestTask[] expectedDoneList = TestUtil.markTaskDone(currentList, indexesToMarkDone, done);
        assertTrue(taskListPanel.isListMatching(expectedDoneList));

        // Mark tasks as undone
        commandBox.runCommand("mark /ud 1 2 3 4 5 6 7");
        
        // Confirm the list now contains the specified tasks to be mark as undone
        Status undone = new Status(false);
        int[] indexesToMarkUndone = {1, 2, 3, 4, 5, 6, 7};
        TestTask[] expectedUndoneList = TestUtil.markTaskDone(currentList, indexesToMarkUndone, undone);
        assertTrue(taskListPanel.isListMatching(expectedUndoneList));  
        
        // invalid command
        commandBox.runCommand("mark 1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        
        // invalid index
        commandBox.runCommand("mark /do 8");
        assertResultMessage("The task index provided is invalid");
    }

}
