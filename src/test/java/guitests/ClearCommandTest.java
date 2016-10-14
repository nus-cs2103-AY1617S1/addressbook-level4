package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;

public class ClearCommandTest extends TaskBookGuiTest {

    @Ignore
    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.arts.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.arts));
        commandBox.runCommand("delete 1");
        assertTaskListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertTaskListSize(0);
        assertResultMessage("Task book has been cleared!");
    }
}
