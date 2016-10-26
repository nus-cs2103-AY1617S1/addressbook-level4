//@@author A0153467Y
package guitests;

import static org.junit.Assert.assertFalse;
import static seedu.task.logic.commands.UnpinCommand.MESSAGE_UNPIN_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.testutil.TestTask;

public class UnpinCommandTest extends TaskManagerGuiTest {
    @Test
    public void unpin() {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // unpin the first task
        commandBox.runCommand("pin " + targetIndex);
        commandBox.runCommand("unpin " + targetIndex);
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertFalse(newTask.getImportance());
        assertResultMessage(String.format(MESSAGE_UNPIN_TASK_SUCCESS, newTask));

        // unpin one more task
        targetIndex = currentList.length;
        commandBox.runCommand("pin " + targetIndex);
        commandBox.runCommand("unpin " + targetIndex);
        newTask = taskListPanel.getTask(targetIndex - 1);
        assertFalse(newTask.getImportance());
        assertResultMessage(String.format(MESSAGE_UNPIN_TASK_SUCCESS, newTask));
    }

    @Test
    public void invalid_unpin() {
        TestTask[] currentList = td.getTypicalTasks();
     // unpin a task which is not pinned
        int targetIndex = 3;
        commandBox.runCommand("unpin " + targetIndex);
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        //check that the task is still not pinned
        assertFalse(newTask.getImportance());
        assertResultMessage(Messages.MESSAGE_INVALID_UNPIN_TASK);
        
        // unpin at an empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("unpin " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //invalid command
        commandBox.runCommand("unppinn");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        

        // invalid index
        commandBox.runCommand("unpin " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
}
