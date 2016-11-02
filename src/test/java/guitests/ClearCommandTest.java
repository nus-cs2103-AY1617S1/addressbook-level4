package guitests;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends TaskBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(datedListPanel.isListMatching(td.getTypicalDatedTasks()));
        assertTrue(undatedListPanel.isListMatching(td.getTypicalUndatedTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.datedOne.getAddCommand());
        assertTrue(datedListPanel.isListMatching(TypicalTestTasks.datedOne));
        commandBox.runCommand(TypicalTestTasks.undatedOne.getAddCommand());
        assertTrue(undatedListPanel.isListMatching(TypicalTestTasks.undatedOne));
        commandBox.runCommand("delete B1");
        assertDatedListSize(0);
        commandBox.runCommand("delete A1");
        assertUndatedListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertDatedListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
