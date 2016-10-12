package guitests;

import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find dry", TypicalTestTasks.task3); //no results
        assertFindResult("find buy", TypicalTestTasks.task1, TypicalTestTasks.task9); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find buy",TypicalTestTasks.task9);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find laundry"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        if (expectedHits.length==0)
            assertResultMessage("No such task was found.");
        else assertResultMessage(expectedHits.length + " task(s) listed!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
