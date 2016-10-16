package guitests;

import org.junit.Test;

import seedu.address.testutil.TestTask;
import seedu.emeraldo.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends EmeraldoGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find party", td.party, td.food); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find party",td.food);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jeans"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findmoney");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
