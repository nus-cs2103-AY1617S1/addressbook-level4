package guitests;

import org.junit.Test;

import seedu.simply.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends SimplyGuiTest {

   @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.hoon.getAddCommand());
        assertTrue(personListPanel.isListMatching(TypicalTestTasks.hoon));
        commandBox.runCommand("delete E1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Simply has been cleared!");
    }
}
