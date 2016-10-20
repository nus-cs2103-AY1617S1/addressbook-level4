package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.FindCommand;
import seedu.taskmanager.testutil.TestItem;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult(FindCommand.COMMAND_WORD + " Mark"); //no results
        assertFindResult(FindCommand.COMMAND_WORD + " this is a", td.deadline1, td.event2, td.task2); //multiple results
        assertFindResult(FindCommand.SHORT_COMMAND_WORD + " this is a", td.deadline1, td.event2, td.task2); //test SHORT_COMMAND_WORD
        
        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult(FindCommand.COMMAND_WORD + " this is a", td.event2, td.task2);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult(FindCommand.COMMAND_WORD + " Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand(FindCommand.COMMAND_WORD + "george");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestItem... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " items listed!");
        assertTrue(shortItemListPanel.isListMatching(expectedHits));
    }
}
