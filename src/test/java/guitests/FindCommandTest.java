package guitests;

import org.junit.Test;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TypicalTestFloatingTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find to", 
                TypicalTestFloatingTasks.airport, 
                TypicalTestFloatingTasks.flight,
                TypicalTestFloatingTasks.beach); //multiple results
        
        //find after deleting one result
        commandBox.runCommand("delete t1");
        assertFindResult("find to", 
                TypicalTestFloatingTasks.flight, 
                TypicalTestFloatingTasks.beach);
    }
    
    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }
    
    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestFloatingTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " task(s) listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
