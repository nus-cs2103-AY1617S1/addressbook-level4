package guitests;

import org.junit.Test;

import tars.commons.core.Messages;
import tars.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TarsGuiTest {

    //@@author A0124333U
    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Meeting"); //no results
        assertFindResult("find Task B",
                td.taskB); //single result
        assertFindResult("find Task", 
                td.taskA, td.taskB,
                td.taskC, td.taskD,
                td.taskE, td.taskF,
                td.taskG); //multiple results

        //find after deleting one result
        commandBox.runCommand("del 1");
        assertFindResult("find A");
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find No Such Task"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findmeeting");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
