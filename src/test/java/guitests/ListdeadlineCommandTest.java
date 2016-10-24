package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.logic.commands.ListDeadlineCommand.SHORT_COMMAND_WORD;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;

public class ListdeadlineCommandTest extends TaskManagerGuiTest {
	
	
	@Test
	public void ListDeadlineCommand() {
		commandBox.runCommand("listdeadline");
		assertListSize(3);
		commandBox.runCommand(SHORT_COMMAND_WORD);
        assertListSize(3);
	}
	
	@Test
    public void find_invalidCommand3_fail() {
        commandBox.runCommand("listdeadlines");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
	
	
	
	private void assertListdeadlineCommandSuccess() {
        commandBox.runCommand("listdeadline");
        commandBox.runCommand("ld");
        assertResultMessage("Listed all deadline");
    }

}


