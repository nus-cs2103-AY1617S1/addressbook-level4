package guitests;

import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends AddressBookGuiTest {

    //@Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.one.getAddCommand());
        assertTrue(personListPanel.isListMatching(TypicalTestTasks.one));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Address book has been cleared!");
    }
}
