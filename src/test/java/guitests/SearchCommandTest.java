package guitests;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class SearchCommandTest extends TaskManagerGuiTest {

    @Test
    public void search_nonEmptyList() {
        assertFindResult("search hello"); //no results
        assertFindResult("search go", TypicalTestTasks.floating, TypicalTestTasks.event); //multiple results

        //search after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("search jog",TypicalTestTasks.floating);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("search dance"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("search milk");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
