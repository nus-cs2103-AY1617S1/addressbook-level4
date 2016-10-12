package guitests;

import org.junit.Test;

import seedu.malitio.testutil.TestTask;
import seedu.malitio.commons.core.Messages;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends MalitioGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find jump"); //no results
        assertFindResult("find cs2103", td.lecture, td.homework); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find sleep",td.sleep);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find eat"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finddonothing");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
