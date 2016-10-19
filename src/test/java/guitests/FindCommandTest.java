package guitests;

import org.junit.Test;

import seedu.malitio.testutil.TestDeadline;
import seedu.malitio.testutil.TestEvent;
import seedu.malitio.testutil.TestFloatingTask;
import seedu.malitio.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends MalitioGuiTest {

    @Test
    public void find_nonEmptyList() {
       // assertFindResult("find jump"); //no results
        assertFindResult("find with", td.event1, td.event2); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find meter",td.floatingTask1);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
       // assertFindResult("find eat"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finddonothing");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    
    private void assertFindResult(String command, TestFloatingTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        
        assertTrue(floatingTaskListPanel.isListMatching(expectedHits));
    }
    private void assertFindResult(String command, TestDeadline... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        
        assertTrue(deadlineListPanel.isListMatching(expectedHits));
    }
    private void assertFindResult(String command, TestEvent... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        
        assertTrue(eventListPanel.isListMatching(expectedHits));
    }
}
