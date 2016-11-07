package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.core.Status;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.parser.ListParser.ListTarget;
import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;

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
 * 	3. /t or /e with /a
 * 	4. /t or /e without /a
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
	public void list_upcomingItems_showOnlyIncompleted() {
		
		// list events
		commandBox.runCommand("list /e");
		assertListEventSuccess(OPTION_NOT_SHOW_ALL, incompletedEventList);

		// list tasks
		commandBox.runCommand("list /t");
		assertListTaskSuccess(OPTION_NOT_SHOW_ALL, incompletedTaskList);

		
		// list both upcoming tasks and events
		commandBox.runCommand("list /e /t");
		assertBothListSuccess(OPTION_NOT_SHOW_ALL, incompletedTaskList, incompletedEventList);
	}
	
	@Test 
	public void list_allItems_showAll() {
		// list all events
		commandBox.runCommand("list /e /a");
		assertListEventSuccess(OPTION_SHOW_ALL, allEventList);

		// list all tasks
		commandBox.runCommand("list /t /a");
		assertListTaskSuccess(OPTION_SHOW_ALL, allTaskList);
		
		// both lists
		commandBox.runCommand("list /t /e /a");
		assertBothListSuccess(OPTION_SHOW_ALL, allTaskList, allEventList);

	}
	
	@Test 
	public void list_flexibleFlags_valid() {
		//flexible sequence of flags
		commandBox.runCommand("list /e /t /a");
		assertBothListSuccess(OPTION_SHOW_ALL, allTaskList, allEventList);
		commandBox.runCommand("list /t /e /a");
		assertBothListSuccess(OPTION_SHOW_ALL, allTaskList, allEventList);
		
	}
	
	/********************* Helper Methods **********************/

	private void assertListEventSuccess(boolean showAll, final TestEvent[] currentList) {
		if (!showAll) {
			// confirm result message is correct.
			assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_FORMAT, Status.INCOMPLETED, ListTarget.EVENT.toString()));
		} else {
			// confirm result message is correct.
			assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_FORMAT, Status.BOTH, ListTarget.EVENT.toString()));
		}
		// confirm the list shows all events not completed.
		assertTrue(eventListPanel.isListMatching(currentList));
	}

	private void assertListTaskSuccess(boolean showAll, final TestTask[] currentList) {
		if (!showAll) {
			// confirm result message is correct.
			assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_FORMAT, Status.INCOMPLETED, ListTarget.TASK.toString()));
		} else {
			// confirm result message is correct.
			assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_FORMAT, Status.BOTH, ListTarget.TASK.toString()));
		}

		// confirm the list shows all tasks not completed.
		assertTrue(taskListPanel.isListMatching(currentList));
	}
	
	private void assertBothListSuccess(boolean showAll, final TestTask[] currentTasks, final TestEvent[] currentEvents) {
		if (!showAll) {
			assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_FORMAT, Status.INCOMPLETED, ListTarget.BOTH.toString()));
			assertTrue(eventListPanel.isListMatching(currentEvents));
			assertTrue(taskListPanel.isListMatching(currentTasks));
			
		} else {
			assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS_FORMAT, Status.BOTH, ListTarget.BOTH.toString()));
			assertTrue(eventListPanel.isListMatching(currentEvents));
			assertTrue(taskListPanel.isListMatching(currentTasks));
		}
	}
	
}
