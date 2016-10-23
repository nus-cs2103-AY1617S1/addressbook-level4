package guitests;

import org.junit.Ignore;
import org.junit.Test;

@Deprecated
public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    @Ignore
    public void clear() {

        //verify a non-empty list can be cleared
//        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
//        commandBox.runCommand(td.hoon.getAddCommand());
//        assertTrue(personListPanel.isListMatching(td.hoon));
//        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
//        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Address book has been cleared!");
    }
}