package guitests;

import seedu.stask.commons.core.Messages;
import seedu.stask.testutil.TestTask;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FindCommandTest extends TaskBookGuiTest {

    /*
    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", td.benson, td.daniel); //multiple results

        //find after deleting datedOne result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier",td.daniel);
    }
    */
    
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
        assertDatedListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(datedListPanel.isListMatching(expectedHits));
    }
}
