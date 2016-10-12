package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void find() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find gas", td.car, td.zika); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find house",td.house);
    

        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results

        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
