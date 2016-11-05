package guitests;

//@@author A0142325R
import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); // no results
        assertFindResult("find friends", td.friend, td.friendEvent, td.lunch); // multiple results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find friends", td.friendEvent, td.lunch);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find project"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    // @@author A0146123R
    @Test
    public void find_advancedCommand() {
        assertFindResult("find project meeting", td.friend, td.friendEvent, td.meeting); // OR operator
                                                                                         //near match search
        assertFindResult("find meet AND friend", td.friend, td.friendEvent); // AND operator
        assertFindResult("find meet AND friend exact!"); // AND operator and exact! parameter
        assertFindResult("find exact! meet", td.friend, td.friendEvent); // exact! parameter
    }

    // @@author A0142325R
    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
