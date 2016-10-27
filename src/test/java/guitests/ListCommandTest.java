package guitests;

import org.junit.Test;

import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import static org.junit.Assert.assertTrue;
import seedu.task.logic.commands.ListEventCommand;
import seedu.task.logic.commands.ListTaskCommand;

/**
 * GuiTest class for ListCommand
 * @author xuchen
 *
 */
public class ListCommandTest extends TaskBookGuiTest {

	private static final boolean OPTION_SHOW_ALL = true;
	private static final boolean OPTION_NOT_SHOW_ALL = false;

	@Test
	public void listTest() {
		TestEvent[] allEventList = te.getTypicalAllEvents();
		TestEvent[] incompletedEventList = te.getTypicalNotCompletedEvents();
		TestTask[] allTaskList = td.getTypicalAllTasks();
		TestTask[] incompletedTaskList = td.getTypicalTasks();

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
			commandBox.runCommand("list -e");
			// confirm result message is correct.
			assertResultMessage(ListEventCommand.MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			commandBox.runCommand("list -e -a");

			// confirm result message is correct.
			assertResultMessage(ListEventCommand.MESSAGE_ALL_SUCCESS);
		}

		// confirm the list shows all events not completed.
		assertTrue(eventListPanel.isListMatching(currentList));
	}

	private void assertListTaskSuccess(boolean showAll, final TestTask[] currentList) {
		if (!showAll) {
			commandBox.runCommand("list -t");

			// confirm result message is correct.
			assertResultMessage(ListTaskCommand.MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			commandBox.runCommand("list -t -a");

			// confirm result message is correct.
			assertResultMessage(ListTaskCommand.MESSAGE_ALL_SUCCESS);
		}

		// confirm the list shows all tasks not completed.
		assertTrue(taskListPanel.isListMatching(currentList));
	}

}
