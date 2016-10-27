package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTaskList;

import static org.junit.Assert.assertTrue;

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
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertIncompleteListSize(0);
        assertResultMessage("Incomplete task has been cleared!");
    }
}
