package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.logic.commands.ListEventCommand.SHORT_COMMAND_WORD;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;

public class ListeventCommandTest extends TaskManagerGuiTest {
	
	
	@Test
	public void ListEventCommand() {
		commandBox.runCommand("listevent");
		assertListSize(4);	
		commandBox.runCommand(SHORT_COMMAND_WORD);
        assertListSize(4);
	}
	
	@Test
    public void find_invalidCommand5_fail() {
        commandBox.runCommand("listevents");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
	
	
	
	private void assertListdeadlineCommandSuccess() {
        commandBox.runCommand("listevent");
        commandBox.runCommand("le");
        assertResultMessage("Listed all events");
    }

}
