package guitests;

import org.junit.Test;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0139915W
public class FindCommandTest extends SavvyTaskerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Zoo"); //no results
        assertFindResult("find Priority", td.highPriority, td.medPriority, td.lowPriority); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Priority", td.medPriority, td.lowPriority);
    }
    
    @Test
    public void find_nonEmptyList_byPartialMatch() {
        // covered by find_nonEmptyList()
    }
    
    @Test
    public void find_nonEmptyList_byFullMatch() {
        assertFindResult("find t/full Due", td.furthestDue, td.nearerDue, 
                td.notSoNearerDue, td.earliestDue, td.longDue); //multiple results
    }
    
    @Test
    public void find_nonEmptyList_byExactMatch() {
        assertFindResult("find t/exact Nearer Due Task", td.nearerDue); // one matching result only
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Shopping"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findmyring");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
//@@author A0139915W
