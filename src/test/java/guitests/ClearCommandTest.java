package guitests;

import org.junit.Test;

import seedu.jimi.testutil.TypicalTestFloatingTasks;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestFloatingTasks.night.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TypicalTestFloatingTasks.night));
        commandBox.runCommand("delete t1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Jimi has been cleared!");
    }
}
