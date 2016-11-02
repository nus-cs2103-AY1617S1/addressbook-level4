package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.ClearCommand;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ClearCommandTest extends ToDoListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        assertTrue(taskListPanel.isListMatching(currentList.getIncompleteList()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.event.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.event));
        commandBox.runCommand("delete 1");
        assertIncompleteListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
        
        //invalid command word
        commandBox.runCommand("clear2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid command argument
        commandBox.runCommand("clear 2");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertIncompleteListSize(0);
        assertResultMessage("Incomplete task has been cleared!");
    }
}
