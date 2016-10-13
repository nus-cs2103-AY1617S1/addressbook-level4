package guitests;

import org.junit.Test;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.testutil.TestPerson;
import seedu.savvytasker.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Zoo"); //no results
        assertFindResult("find Hello", td.hello, td.hello2); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Hello", td.hello2);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Shopping"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findmyring");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
