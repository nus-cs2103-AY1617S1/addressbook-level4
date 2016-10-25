package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SetStorageCommandTest extends TaskManagerGuiTest {

    @Test
    public void setStorage() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertSetStorageCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hoon.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.hoon));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertSetStorageCommandSuccess();
    }

    private void assertSetStorageCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task manager has been cleared!");
    }
}
