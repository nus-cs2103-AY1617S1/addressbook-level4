package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.testutil.TestTask;
import harmony.mastermind.testutil.TypicalTestTasks;

public class FindTagCommandTest extends TaskManagerGuiTest {
    //@@author A0124797R
    @Test
    public void find_nonEmptyList() {
        assertFindTagResult("findtag test"); //no results
        assertFindTagResult("findtag homework", TypicalTestTasks.task1, TypicalTestTasks.task2); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindTagResult("findtag homework", TypicalTestTasks.task2);
        
        assertListSize(1); // should only contain task1
        assertResultMessage("1 tasks listed!");
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.task2));
    }

    @Test
    public void findTag_emptyList(){
        commandBox.runCommand("clear");
        assertFindTagResult("findtag Marking"); //no results
    }

    @Test
    public void findTag_invalidCommand_fail() {
        commandBox.runCommand("findtagdinner");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND+": findtagdinner");
    }

    /**
     * Checks if findtag command list the correct number of tasks as the expectedHits
     * @param command
     * @param expectedHits
     */
    private void assertFindTagResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}