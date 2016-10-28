package guitests;

import org.junit.Test;

import seedu.todoList.testutil.TestTask;
import seedu.todoList.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends ListGuiTest {

    @Test
  //@@author A0132157M reused
    public void find_nonEmptyList() {
        assertFindResult("find priority 999"); //no results
        assertFindResult("find project", td.a2, td.a5); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find project 1",td.a2);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find assignment 99"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findassignment");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
