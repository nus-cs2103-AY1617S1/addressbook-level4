package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;

public class ListtaskCommandTest extends TaskManagerGuiTest {
	
	//verify ListTaskCommand works by checking size
	@Test
	public void ListTaskCommand() {
		commandBox.runCommand("listtask");
		assertListSize(2); 
	}
	
	@Test
    public void find_invalidCommand1_fail() {
        commandBox.runCommand("listtasks");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
	
	private void assertListtaskCommandSuccess() {
        commandBox.runCommand("listtask");
        assertResultMessage("Listed all tasks");
    }

}
