package guitests;

import org.junit.Test;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    //@@author A0124797R
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find assignment", td.task2, td.task5); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find assignment",td.task2);
    }

    @Test
    //@@author A0124797R
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Mark"); //no results
    }

    @Test
    //@@author A0124797R
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findassignment");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
