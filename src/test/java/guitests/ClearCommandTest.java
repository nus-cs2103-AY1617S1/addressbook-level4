package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.learnPython.getAddCommand());
        assertTrue(personListPanel.isListMatching(td.learnPython));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Daily Planner has been cleared!");
    }
}
