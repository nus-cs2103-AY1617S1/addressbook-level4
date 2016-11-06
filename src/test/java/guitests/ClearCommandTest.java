package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTaskList;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.ClearCommand;
import seedu.todolist.model.task.Status;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ClearCommandTest extends ToDoListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        assertAllListMatching(currentList);
        assertClearCommandSuccess(currentList);

        //verify other commands can work after a clear command
        commandBox.runCommand(td.upcomingEvent.getAddCommand());
        currentList.addTasksToList(td.upcomingEvent);
        assertAllListMatching(currentList);
        
        commandBox.runCommand("delete 1");
        currentList.removeTasksFromList(new int[]{1}, Status.Type.Incomplete);
        assertAllListMatching(currentList);

        //verify clear command works when the list is empty
        assertClearCommandSuccess(currentList);
        
        //invalid command word
        commandBox.runCommand("clear2");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid command argument
        commandBox.runCommand("clear 2");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }

    private void assertClearCommandSuccess(TestTaskList currentList) {
        commandBox.runCommand("clear");
        currentList.clear();
        assertAllListMatching(currentList);
        assertResultMessage("Incomplete task has been cleared!");
    }
}
