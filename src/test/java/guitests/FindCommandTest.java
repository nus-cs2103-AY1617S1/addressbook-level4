package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.todolist.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find NonExistentTask"); //no results
        assertFindResult("find Event", td.eventWithoutParameters, td.eventWithLocation, td.eventWithRemarks, td.eventWithLocationAndRemarks); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Event",td.eventWithLocation, td.eventWithRemarks, td.eventWithLocationAndRemarks);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertIncompleteListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
