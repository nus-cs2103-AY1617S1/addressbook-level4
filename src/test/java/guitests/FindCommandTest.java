package guitests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult(FindCommand.COMMAND_WORD + " Mark"); // no results
        assertFindResult(FindCommand.COMMAND_WORD + " 4", TypicalTestTasks.deadlineIn7Days, TypicalTestTasks.event1); //multiple results

        // find after deleting one result
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFindResult(FindCommand.COMMAND_WORD + " 4", TypicalTestTasks.event1);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertFindResult(FindCommand.COMMAND_WORD + " hw"); //no results
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed.");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
