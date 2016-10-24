package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends ActivityManagerGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared

        assertTrue(activityListPanel.isTaskListMatching(td.getTypicalTask()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.task.getAddCommand());
        assertTrue(activityListPanel.isTaskListMatching(td.task));

        commandBox.runCommand("delete task 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Menion has been cleared!");
    }
}
