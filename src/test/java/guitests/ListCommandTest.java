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
		TestTask[] expectedList = td.getTypicalTasks();
		//list without parameters
		assertListResult("list   ", expectedList);
		
		//list someday
		expectedList = td.getSomedayTasks();
		assertListResult("list someday", expectedList);
		assertListResult("list sd", expectedList);
		
		//list deadline
		expectedList = new TestTask[]{td.deadlineToday, td.deadlineTomorrow, 
				td.deadlineIn7Days, td.deadlineIn30Days, td.deadline1, td.deadline2};
		assertListResult("list deadline", expectedList);
		assertListResult("list dl", expectedList);
		
		//list event
		expectedList = new TestTask[]{td.eventToday, td.eventTomorrow, td.eventIn7Days, 
				td.eventIn30Days, td.event1, td.event2};
		assertListResult("list event", expectedList);
		assertListResult("list ev", expectedList);
		
		//list done
		expectedList = new TestTask[] {td.someday1, td.deadlineToday, td.deadlineIn7Days,
				td.deadline1, td.eventToday, td.eventIn7Days, td.event1};
		assertListResult("list done", expectedList);
		
		//list not done
		expectedList = new TestTask[] {td.deadlineTomorrow, td.deadlineIn30Days, 
				td.someday2, td.eventTomorrow, td.eventIn30Days};
		assertListResult("list not-done", expectedList);
		
		//list done and deadline
		expectedList = new TestTask[] {td.deadlineToday, td.deadlineIn7Days, td.deadline1};
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