//@@author A0153467Y
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.PinCommand.MESSAGE_PIN_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.testutil.TestTask;

public class PinCommandTest extends TaskManagerGuiTest {
    @Test
    public void pin() {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // pin the first task
        commandBox.runCommand("pin " + targetIndex);
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(newTask.getImportance());
        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_PIN_TASK_SUCCESS, newTask));

        // pin another task
        targetIndex = 3;
        commandBox.runCommand("pin " + targetIndex);
        ReadOnlyTask otherTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(otherTask.getImportance());
        assertResultMessage(String.format(MESSAGE_PIN_TASK_SUCCESS, otherTask));

        // pin the last task
        targetIndex = currentList.length;
        commandBox.runCommand("pin " + targetIndex);
        newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(newTask.getImportance());
        assertResultMessage(String.format(MESSAGE_PIN_TASK_SUCCESS, newTask));

        

    }
    @Test
    public void invalid_pin() {
        TestTask[] currentList = td.getTypicalTasks();

        // invalid index
        commandBox.runCommand("pin " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        // pin at an empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("pin " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

   
}
