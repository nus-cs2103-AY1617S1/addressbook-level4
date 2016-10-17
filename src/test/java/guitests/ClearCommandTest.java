package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskListGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalTaskComponents()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hoon.getAddFloatingCommand());
        assertTrue(floatingTaskListPanel.isListMatching(td.hoon.getTaskDateComponent().get(0)));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task list has been cleared!");
    }
}
