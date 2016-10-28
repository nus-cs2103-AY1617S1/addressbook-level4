package guitests;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.testutil.TestTask;
//@@author A0139339W
public class ListCommandTest extends TaskManagerGuiTest {

	@Test
	public void list() {
		TestTask[] expectedList = td.getSortedTypicalTasks();
		//list without parameters
		assertListResult("list   ", expectedList);
		
		//list someday
		expectedList = td.getSomedayTasks();
		assertListResult("list someday", expectedList);
		assertListResult("list sd", expectedList);
		
		//list deadline
		expectedList = td.getDeadlineTasks();
		assertListResult("list deadline", expectedList);
		assertListResult("list dl", expectedList);
		
		//list event
		expectedList = td.getEventTasks();
		assertListResult("list event", expectedList);
		assertListResult("list ev", expectedList);
		
		//list done
		expectedList = td.getDoneTasks();
		assertListResult("list done", expectedList);
		
		//list not done
		expectedList = td.getNotDoneTasks();
		assertListResult("list not-done", expectedList);
		
		//list overdue
		expectedList = td.getOverdueTasks();
		assertListResult("list overdue", expectedList);
		
		//list done and deadline
		expectedList = td.getDoneAndDeadlineTasks();
		assertListResult("list done dl", expectedList);
		
		//list contains invalid input
		commandBox.runCommand("list ss done");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

	}
	
	private void assertListResult(String command, TestTask... expectedList) {
		commandBox.runCommand(command);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}

}
//@@author