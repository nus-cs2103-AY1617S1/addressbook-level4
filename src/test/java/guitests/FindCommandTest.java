package guitests;

import org.junit.Test;

import seedu.dailyplanner.commons.core.Messages;
import seedu.testplanner.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find bellydancing"); //no results
        assertFindResult("find CS2103", td.CS2103_Project, td.CS2103_Lecture); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find soccer", td.SoccerWithFriends);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find homework"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("find123");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
