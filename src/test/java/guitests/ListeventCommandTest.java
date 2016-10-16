package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;

public class ListeventCommandTest extends TaskManagerGuiTest {
	
	
	@Test
	public void ListEventCommand() {
		commandBox.runCommand("listevent");
		assertListSize(4);	
	}
	
	@Test
    public void find_invalidCommand5_fail() {
        commandBox.runCommand("listevents");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
	
	
	
	private void assertListdeadlineCommandSuccess() {
        commandBox.runCommand("listevent");
        assertResultMessage("Listed all events");
    }

}
