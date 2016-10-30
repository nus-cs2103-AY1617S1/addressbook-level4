package guitests;

import org.junit.Test;
import seedu.agendum.commons.core.Messages;
import seedu.agendum.testutil.TestTask;
import seedu.agendum.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", TypicalTestTasks.benson, TypicalTestTasks.daniel); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", TypicalTestTasks.daniel);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("delete 1-7");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, expectedHits.length));
        assertAllPanelsMatch(expectedHits);
    }
}
