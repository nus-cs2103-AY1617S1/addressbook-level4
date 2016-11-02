//@@author A0153467Y
package guitests;

import static org.junit.Assert.assertFalse;
import static seedu.task.logic.commands.UncompleteCommand.MESSAGE_UNCOMPLETE_TASK_SUCCESS;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.testutil.TestTask;

public class UncompleteCommandTest extends TaskManagerGuiTest {

    private TestTask[] currentList;

    @Before
    public void runOnceBeforeClass() {
        currentList = td.getTypicalTasks();
    }

    @Test
    public void uncomplete() {
        int targetIndex = 1;

        // mark an originally completed task as not complete
        commandBox.runCommand("complete " + targetIndex);
        commandBox.runCommand("uncomplete " + targetIndex);
        ReadOnlyTask newTask = taskListPanel.getTask(targetIndex - 1);
        assertFalse(newTask.getComplete());
        assertResultMessage(String.format(MESSAGE_UNCOMPLETE_TASK_SUCCESS, newTask));

        // unmark another task
        targetIndex = 3;
        commandBox.runCommand("complete " + targetIndex);
        commandBox.runCommand("uncomplete " + targetIndex);
        newTask = taskListPanel.getTask(targetIndex - 1);
        assertFalse(newTask.getComplete());
        assertResultMessage(String.format(MESSAGE_UNCOMPLETE_TASK_SUCCESS, newTask));

        // unmark a task which is not marked as complete before
        targetIndex = currentList.length;
        commandBox.runCommand("uncomplete " + targetIndex);
        newTask = taskListPanel.getTask(targetIndex - 1);
        // this task should still be marked as not complete
        assertFalse(newTask.getComplete());
        assertResultMessage(Messages.MESSAGE_INVALID_UNCOMPLETE_TASK);
    }

    @Test
    public void invalidUncomplete() {
        // invalid index
        commandBox.runCommand("uncomplete " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // mark at an empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("uncomplete " + (currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

}