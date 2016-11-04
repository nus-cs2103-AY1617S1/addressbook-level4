package guitests;

import org.junit.Test;

import seedu.simply.commons.core.Messages;
import seedu.simply.testutil.TestTask;
import seedu.simply.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends SimplyGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", TypicalTestTasks.benson, TypicalTestTasks.daniel); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete E1, D1, T1");
        assertFindResult("find Meier",TypicalTestTasks.daniel);
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

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage("Found " + expectedHits.length + " Events! \n"
        		+ "Found " + expectedHits.length + " Deadlines!\n"
        		+ "Found " + expectedHits.length + " Todo!");
        assertTrue(personListPanel.isListMatching(expectedHits));
    }
}
