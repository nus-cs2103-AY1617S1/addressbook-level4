package guitests;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(datedListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.one.getAddCommand());
        assertTrue(datedListPanel.isListMatching(TypicalTestTasks.one));
        commandBox.runCommand("delete B1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
