package guitests;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.logic.commands.Command;
import seedu.oneline.testutil.TestTask;
import seedu.oneline.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskBookGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find NotPartOfName"); //no results
        assertFindResult("find Consolidate", TypicalTestTasks.todo2, TypicalTestTasks.float1); //multiple results

        //find after deleting one result
        commandBox.runCommand("del 1");
        assertFindResult("find Consolidate", TypicalTestTasks.float1);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find NotATask"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findtask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(Command.getMessageForTaskListShownSummary(expectedHits.length));
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
