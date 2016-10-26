//@@author A0153467Y
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.testutil.TestTask;

public class CompleteCommandTest extends TaskManagerGuiTest {
    @Test
    public void complete() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        // invalid index
        commandBox.runCommand("complete " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // mark the first task as complete
        commandBox.runCommand("complete " + targetIndex);
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(newTask.getComplete());

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, newTask));

        // mark another task as complete
        targetIndex = 3;
        commandBox.runCommand("complete " + targetIndex);
        ReadOnlyTask otherTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(otherTask.getComplete());
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, otherTask));

        // mark the last task as complete
        targetIndex = currentList.length;
        commandBox.runCommand("complete " + targetIndex);
        newTask = taskListPanel.getTask(targetIndex - 1);
        assertTrue(newTask.getComplete());
        assertResultMessage(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, newTask));

        // mark at an empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("complete " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

}
