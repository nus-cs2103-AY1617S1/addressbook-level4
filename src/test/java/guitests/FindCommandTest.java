package guitests;

import org.junit.Test;

import seedu.address.testutil.TestActivity;

import seedu.menion.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        commandBox.runCommand("clear");
        commandBox.runCommand(td.task.getAddCommand());
        commandBox.runCommand(td.task.getAddCommand());
        assertFindResult("find Mark"); //no results
        assertFindResult("find " + td.task.getActivityName().fullName, td.task, td.task); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find " + td.task.getActivityName().fullName, td.task);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find CS2103"); //no results
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
