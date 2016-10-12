package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;

public class ListtaskCommandTest extends TaskManagerGuiTest {
	
	
	@Test
	public void ListTaskCommand() {
		
		//verify ListTaskCommand works by checking size
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
