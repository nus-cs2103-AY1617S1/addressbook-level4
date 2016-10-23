package guitests;

import org.junit.Test;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find project", td.benson, td.carl, td.george); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Groupwork", td.daniel);
    }
    
    @Test
    public void find_Date() {
        assertFindResult("find 14-Oct-2016", td.benson, td.carl);
    }
    
    @Test
    public void find_address() {
        assertFindResult("find hall", td.elle, td.george);
    }
    
    @Test
    public void find_tag() {
        commandBox.runCommand("mark 1");
        commandBox.runCommand("mark 4");
        assertFindResult("find Completed", td.alice, td.daniel);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findsomething");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
