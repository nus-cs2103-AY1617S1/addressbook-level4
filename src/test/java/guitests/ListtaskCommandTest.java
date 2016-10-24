package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.logic.commands.ListTaskCommand.SHORT_COMMAND_WORD;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;

public class ListtaskCommandTest extends TaskManagerGuiTest {
	
	//verify ListTaskCommand works by checking size
	@Test
	public void ListTaskCommand() {
		commandBox.runCommand("listtask");
		assertListSize(3); 
		commandBox.runCommand(SHORT_COMMAND_WORD);
        assertListSize(3); 
	}
	
	@Test
    public void find_invalidCommand1_fail() {
        commandBox.runCommand("listtasks");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
	
	private void assertListtaskCommandSuccess() {
        commandBox.runCommand("listtask");
        commandBox.runCommand(SHORT_COMMAND_WORD);
        assertResultMessage("Listed all tasks");
    }

}
