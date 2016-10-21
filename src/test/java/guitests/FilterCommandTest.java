package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FilterCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFilterResult("filter d/12.10.2016"); //no results
        commandBox.runCommand("list");
        assertFilterResult("filter d/11.10.2016", td.friendEvent, td.work); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFilterResult("filter d/11.10.2016",td.work);
        
        // Filter for event start date
        commandBox.runCommand("list");
        assertFilterResult("filter s/11.10.2016",td.travel);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFilterResult("filter d/11.10.2016"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("filterd/11.10.2016");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFilterResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
