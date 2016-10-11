package guitests;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class SearchCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find movie"); //no results
        assertFindResult("find movie", td.jog, td.dinner); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find holiday",td.dance);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find dance"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("searchmilk");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
