package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.UnpinCommand.MESSAGE_UNPIN_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.testutil.TestTask;

public class UnpinCommandTest extends TaskManagerGuiTest {
    @Test
    public void unpin() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // invalid index
        commandBox.runCommand("unpin " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        // unpin the first task
        commandBox.runCommand("pin " + targetIndex);
        commandBox.runCommand("unpin " + targetIndex);
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(!newTask.getImportance());
        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UNPIN_TASK_SUCCESS, newTask));

       // unpin a task which is not pinned
        targetIndex = 3;
        commandBox.runCommand("unpin " + targetIndex);
        ReadOnlyTask otherTask = taskListPanel.getTask(targetIndex - 1);
        assertResultMessage(Messages.MESSAGE_INVALID_UNPIN_TASK);

        // unpin one more task
        targetIndex = currentList.length;
        commandBox.runCommand("pin " + targetIndex);
        commandBox.runCommand("unpin " + targetIndex);
        newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(!newTask.getImportance());
        assertResultMessage(String.format(MESSAGE_UNPIN_TASK_SUCCESS, newTask));

        // unpin at an empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("unpin " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //invalid command
        commandBox.runCommand("unppinn");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

    }
}
