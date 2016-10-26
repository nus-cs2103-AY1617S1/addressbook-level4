//@@author A0142102E
package guitests;

import seedu.tasklist.testutil.TypicalTestTasks;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.task8.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.task8));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Your Smart Scheduler has been cleared!");
    }
}
