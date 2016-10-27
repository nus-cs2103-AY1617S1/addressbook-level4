package guitests;

import org.junit.Test;

import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import static org.junit.Assert.assertTrue;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Before;
import org.junit.Ignore;

import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.ListEventCommand;
import seedu.task.logic.commands.ListTaskCommand;

//@@author A0144702N
/**
 * GuiTest class for ListCommand
 * @author xuchen
 *
 */

/*
 * EQ of List Command
 * 	1. /t or /e without /a
 * 	2. /t or /e with /a
 * 
 * Tested Valid Use cases:
 * 	1. /t or /e with /a
 * 	2. /t or /e without /a
 * 
 * Tested Invalid Use Cases:
 * 	1. Both /t /e
 * 	2. No /t /e 
 * 
 */
public class ListCommandTest extends TaskBookGuiTest {

	private static final boolean OPTION_SHOW_ALL = true;
	private static final boolean OPTION_NOT_SHOW_ALL = false;
	private TestEvent[] allEventList;
	private TestEvent[] incompletedEventList;
	private TestTask[] allTaskList;
	private TestTask[] incompletedTaskList;
	
	@Before
	public void setupLists() {
		allEventList = te.getTypicalAllEvents();
		incompletedEventList = te.getTypicalNotCompletedEvents();
		allTaskList = td.getTypicalAllTasks();
		incompletedTaskList = td.getTypicalTasks();
	}
	
	
	@Test
	public void listTest_invalid() {
		//empty args
		commandBox.runCommand("list ");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
		
		commandBox.runCommand("list /e /t");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
		
		commandBox.runCommand("list /e /t /a");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
		
		commandBox.runCommand("list /a");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
	}
	
	@Test
	public void listTest_valid() {
		
		// list events
		assertListEventSuccess(OPTION_NOT_SHOW_ALL, incompletedEventList);

		// list all events
		assertListEventSuccess(OPTION_SHOW_ALL, allEventList);

		// list tasks
		assertListTaskSuccess(OPTION_NOT_SHOW_ALL, incompletedTaskList);

		// list all tasks
		assertListTaskSuccess(OPTION_SHOW_ALL, allTaskList);
	}

	private void assertListEventSuccess(boolean showAll, final TestEvent[] currentList) {
		if (!showAll) {
			commandBox.runCommand("list /e");

			// confirm result message is correct.
			assertResultMessage(ListEventCommand.MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			commandBox.runCommand("list /e /a");

			// confirm result message is correct.
			assertResultMessage(ListEventCommand.MESSAGE_ALL_SUCCESS);
		}

		// confirm the list shows all events not completed.
		assertTrue(eventListPanel.isListMatching(currentList));
	}

	private void assertListTaskSuccess(boolean showAll, final TestTask[] currentList) {
		if (!showAll) {
			commandBox.runCommand("list /t");

			// confirm result message is correct.
			assertResultMessage(ListTaskCommand.MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			commandBox.runCommand("list /t /a");

			// confirm result message is correct.
			assertResultMessage(ListTaskCommand.MESSAGE_ALL_SUCCESS);
		}

		// confirm the list shows all tasks not completed.
		assertTrue(taskListPanel.isListMatching(currentList));
	}
	
}
