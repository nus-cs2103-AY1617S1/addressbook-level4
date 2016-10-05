package guitests;

import org.junit.Test;
import seedu.taskman.commons.core.Messages;
import seedu.taskman.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends TaskManGuiTest {

    @Test
    public void list_nonEmptyList() {
        assertListResult("list Mark"); //no results
        assertListResult("list Meier", td.benson, td.daniel); //multiple results

        //list after deleting one result
        commandBox.runCommand("delete 1");
        assertListResult("list Meier",td.daniel);
    }

    @Test
    public void list_emptyList(){
        commandBox.runCommand("clear");
        assertListResult("list Jean"); //no results
    }

    @Test
    public void list_invalidCommand_fail() {
        commandBox.runCommand("listgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
