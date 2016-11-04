package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.forgetmenot.commons.core.Messages;
import seedu.forgetmenot.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find invalid"); //no results
        assertFindResult("find bananas", td.bananas, td.deed); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find bananas",td.deed);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findhomework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
