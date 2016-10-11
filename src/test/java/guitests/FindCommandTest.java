package guitests;

import org.junit.Test;

import seedu.manager.commons.core.Messages;
import seedu.manager.testutil.TestActivity;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends ActivityManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find buy", ta.groceries); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find buy");
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

    private void assertFindResult(String command, TestActivity... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " activities listed!");
        assertTrue(activityListPanel.isListMatching(expectedHits));
    }
}
