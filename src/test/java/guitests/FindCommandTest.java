package guitests;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.menion.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", td.assignment2, td.assignment4); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier",td.assignment4);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " persons listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
