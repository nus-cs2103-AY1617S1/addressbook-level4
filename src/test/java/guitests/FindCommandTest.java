package guitests;

import org.junit.Test;

import seedu.address.testutil.TestPerson;
import seedu.dailyplanner.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", td.CS2103_Lecture, td.soccerWithFriends); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier",td.soccerWithFriends);
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

    private void assertFindResult(String command, TestPerson... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
