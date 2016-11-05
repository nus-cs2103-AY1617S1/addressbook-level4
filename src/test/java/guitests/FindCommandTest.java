package guitests;

import org.junit.Test;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.testutil.TestFloatingTask;
import seedu.jimi.testutil.TypicalTestFloatingTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find \"zeroresults\""); //no results
        assertFindResult("find \"to\"", 
                TypicalTestFloatingTasks.airport, 
                TypicalTestFloatingTasks.flight,
                TypicalTestFloatingTasks.beach,
                TypicalTestFloatingTasks.car); //multiple results
        
        //find after deleting one result
        commandBox.runCommand("delete t1");
        assertFindResult("find \"to\"", 
                TypicalTestFloatingTasks.airport,
                TypicalTestFloatingTasks.flight, 
                TypicalTestFloatingTasks.beach);
    }
    
    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find \"Jean\""); //no results
    }
    
    @Test
    public void find_invalidCommand_fail() {
        String invalidFindCommand = "findgeorge";
        commandBox.runCommand(invalidFindCommand);
        assertResultMessage(String.format(Messages.MESSAGE_UNKNOWN_COMMAND, invalidFindCommand));
    }

    private void assertFindResult(String command, TestFloatingTask... expectedHits) {
        commandBox.runCommand(command);
//        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " task(s) found!");
//        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
