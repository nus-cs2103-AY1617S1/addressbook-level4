package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.todolist.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find NonExistentTask"); //no results
        assertFindResult("find Dinner", td.dinnerMum, td.dinnerFriend, td.dinnerChristmas); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Dinner",td.dinnerFriend, td.dinnerChristmas);
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
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
