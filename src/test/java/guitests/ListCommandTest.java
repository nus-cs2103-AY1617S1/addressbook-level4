package guitests;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.testutil.TestTask;
//@@author A0139339W
public class ListCommandTest extends TaskManagerGuiTest {

	@Test
	public void list() {
		TestTask[] expectedList = td.getDefaultTasks();
//		//list without parameters
//		assertListResult("list   ", expectedList);
//		
//		//list all
//		expectedList = td.getTypicalTasks();
//		assertListResult("list all", expectedList);
//		
//		//list someday
//		expectedList = td.getSomedayTasks();
//		assertListResult("list someday", expectedList);
//		assertListResult("list sd", expectedList);
//		
//		//list deadline
//		expectedList = td.getDeadlineTasks();
//		assertListResult("list deadline", expectedList);
//		assertListResult("list dl", expectedList);
//		
//		//list event
//		expectedList = td.getEventTasks();
//		assertListResult("list event", expectedList);
//		assertListResult("list ev", expectedList);
//		
//		//list done
//		expectedList = td.getDoneTasks();
//		assertListResult("list done", expectedList);
//		
//		//list not done
//		expectedList = td.getNotDoneTasks();
//		assertListResult("list pending", expectedList);
		
//		//list overdue
//		expectedList = td.getOverdueTasks();
//		assertListResult("list overdue", expectedList);
//		
//		//list natural language day
//		expectedList = td.getTodayTasks();
//		assertListResult("list today", expectedList);
		
		//list exact date
		expectedList = td.getTomorrowTasks();
		DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;
		assertListResult("list " + LocalDate.now().plusDays(1).format(format), expectedList);
		
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