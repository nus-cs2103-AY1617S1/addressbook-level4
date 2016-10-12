package guitests;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.task.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Homework1"); //no results
        assertFindResult("find Homework2", td.task6, td.task7); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Homework3",td.task8);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Homework"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findhomework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
