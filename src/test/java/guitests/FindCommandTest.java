package guitests;

import org.junit.Test;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.testutil.TypicalTestEvents;
import seedu.jimi.testutil.TypicalTestFloatingTasks;

import static org.junit.Assert.assertTrue;

// @@author A0143471L
public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find \"zeroresults\""); //no results
        assertFindResult("find \"to\"", 
                TypicalTestFloatingTasks.airport, 
                TypicalTestFloatingTasks.flight,
                TypicalTestFloatingTasks.beach,
                TypicalTestFloatingTasks.car,
                TypicalTestEvents.tuition); //multiple results
        
        //find after deleting one result
        commandBox.runCommand("delete t1");
        assertFindResult("find \"to\"", 
                TypicalTestFloatingTasks.airport,
                TypicalTestFloatingTasks.flight, 
                TypicalTestFloatingTasks.beach,
                TypicalTestEvents.tuition);
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

    private void assertFindResult(String command, ReadOnlyTask... expectedHits) {
        commandBox.runCommand(command);
//        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " task(s) found!");
//        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
