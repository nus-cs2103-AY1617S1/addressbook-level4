package guitests;

import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find today"); //no results
        assertFindResult("find Failure", td.daniel, td.fiona); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Alice",td.alice);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find CS2103"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findCS2103");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
