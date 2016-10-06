package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.taskcommons.core.Messages;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;

public class FindCommandTest extends TaskBookGuiTest {

    @Ignore
    @Test
    public void find_nonEmptyList() {
        assertFindResult("find cs2010"); //no results
        assertFindResult("find cs10", td.cs1010, td.cs1020); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find cs10",td.cs1020);
    }

    @Ignore
    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find cs1010"); //no results
    }

    @Ignore
    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findcs1010");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
