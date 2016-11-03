package guitests;

import org.junit.Test;

import guitests.guihandles.DeadlineCardHandle;
import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.Tdoo.testutil.DeadlineBuilder;
import seedu.Tdoo.testutil.EventBuilder;
import seedu.Tdoo.testutil.TaskBuilder;
import seedu.Tdoo.testutil.TestDeadline;
import seedu.Tdoo.testutil.TestEvent;
import seedu.Tdoo.testutil.TestTask;
import seedu.Tdoo.testutil.TestUtil;
import seedu.Tdoo.commons.core.Messages;
import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.logic.commands.FindCommand;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends ListGuiTest {

	@Test
	// @@author A0132157M reused
	public void find_nonEmptyList() throws IllegalValueException {

		TestTask[] currentList = td.getTypicaltasks();
		TestTask taskToAdd = new TaskBuilder().withName("assignment 1").withStartDate("28-11-2016")
				.withEndDate("29-11-2016").withPriority("1").withDone("false").build();
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);
		TestTask taskToAdd1 = new TaskBuilder().withName("assignment 2").withStartDate("29-11-2016")
				.withEndDate("30-11-2016").withPriority("1").withDone("false").build();
		assertAddSuccess(taskToAdd1, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd1);
		// assertFindResult("find to priority 999"); //no results
		assertFindResult("find todo assignment 1", taskToAdd1);

		// find after deleting one result
		// commandBox.runCommand("delete 1");
		// assertFindResult("find project 1",td.a2);
	}

	@Test
	// @@author A0132157M reused
	public void find_nonEmptyEventList() throws IllegalValueException {

		TestEvent[] currentList = ed.getTypicalEvent();
		TestEvent taskToAdd = new EventBuilder().withName("assignment 1").withStartDate("28-11-2016")
				.withEndDate("29-11-2016").withStartTime("01:00").withEndTime("01:30").withDone("false").build();
		assertAddEventSuccess(taskToAdd, currentList);
		currentList = TestUtil.addEventsToList(currentList, taskToAdd);
		TestEvent taskToAdd1 = new EventBuilder().withName("assignment 2").withStartDate("29-11-2016")
				.withEndDate("30-11-2016").withStartTime("01:00").withEndTime("01:30").withDone("false").build();
		assertAddEventSuccess(taskToAdd1, currentList);
		currentList = TestUtil.addEventsToList(currentList, taskToAdd1);
		// assertFindResult("find to priority 999"); //no results
		assertFindEventResult("find event assignment 1", taskToAdd1);
	}

	@Test
	// @@author A0132157M reused
	public void find_nonEmptyDeadlineList() throws IllegalValueException {

		TestDeadline[] currentList = dd.getTypicalDeadline();
		TestDeadline taskToAdd = new DeadlineBuilder().withName("assignment 1").withStartDate("28-11-2016")
				.withEndTime("01:30").withDone("false").build();
		assertAddDeadlineSuccess(taskToAdd, currentList);
		currentList = TestUtil.addDeadlinesToList(currentList, taskToAdd);
		TestDeadline taskToAdd1 = new DeadlineBuilder().withName("assignment 2").withStartDate("29-11-2016")
				.withEndTime("01:30").withDone("false").build();
		assertAddDeadlineSuccess(taskToAdd1, currentList);
		currentList = TestUtil.addDeadlinesToList(currentList, taskToAdd1);
		// assertFindResult("find to priority 999"); //no results
		assertFindDeadlineResult("find deadline assignment 1", taskToAdd1);
	}

	@Test
	public void find_emptyList() {
		commandBox.runCommand("clear event");
		assertFindResult("find assignment 99"); // no results
	}

	@Test
	public void find_invalidCommand_fail() {
		commandBox.runCommand("findassignment");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}

	private void assertFindResult(String command, TestTask... expectedHits) {
		commandBox.runCommand(command);
		assertListSize(expectedHits.length);
		if (expectedHits.length == 0) {
			assertResultMessage("Invalid command format! \n"
					+ "find: Finds all tasks whose names or start date contain any of the specified keywords and displays them as a list with index numbers.\n"
					+ "Parameters: TASK_TYPE KEYWORD [MORE_KEYWORDS]...\n" + "Example: find all homework urgent\n"
					+ "               " + "find date/25th December 2016");
		} else {
			assertResultMessage(expectedHits.length + " tasks listed!");
		}
		// assertTrue(taskListPanel.isListMatching(expectedHits));
	}

	// @@author A0132157M
	private void assertFindEventResult(String command, TestEvent... expectedHits) {
		commandBox.runCommand(command);
		assertEventListSize(expectedHits.length);
		if (expectedHits.length == 0) {
			assertResultMessage("Invalid command format! \n"
					+ "find: Finds all tasks whose names contain any of the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
					+ "Parameters: TASK_TYPE KEYWORD [MORE_KEYWORDS]...\n" + "Example: find all homework urgent");
		} else {
			assertResultMessage(expectedHits.length + " tasks listed!");
		}
		// assertTrue(eventListPanel.isListMatching(expectedHits));
	}

	// @@author A0132157M
	private void assertFindDeadlineResult(String command, TestDeadline... expectedHits) {
		commandBox.runCommand(command);
		assertDeadlineListSize(expectedHits.length);
		if (expectedHits.length == 0) {
			assertResultMessage("Invalid command format! \n"
					+ "find: Finds all tasks whose names contain any of the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
					+ "Parameters: TASK_TYPE KEYWORD [MORE_KEYWORDS]...\n" + "Example: find all homework urgent");
		} else {
			assertResultMessage(expectedHits.length + " tasks listed!");
		}
		// assertTrue(deadlineListPanel.isListMatching(expectedHits));
	}

	// @@author A0132157M reused
	private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
		addAllDummyTodoTasks(currentList);
		commandBox.runCommand(taskToAdd.getAddCommand());
		// confirm the new card contains the right data
		TaskCardHandle addedCard = taskListPanel.navigateTotask(taskToAdd.getName().name);
		assertMatching(taskToAdd, addedCard);
		// confirm the list now contains all previous tasks plus the new task
		TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}

	// @@author A0132157M reused
	private void addAllDummyTodoTasks(TestTask... currentList) {
		for (TestTask t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}

	// @@author A0132157M
	private void assertAddEventSuccess(TestEvent taskToAdd, TestEvent... currentList) {
		addAllDummyEventTasks(currentList);
		commandBox.runCommand(taskToAdd.getAddCommand());
		// confirm the new card contains the right data
		EventCardHandle addedCard = eventListPanel.navigateToevent(taskToAdd.getName().name);
		assertEventMatching(taskToAdd, addedCard);
		// confirm the list now contains all previous tasks plus the new task
		TestEvent[] expectedList = TestUtil.addEventsToList(currentList, taskToAdd);
		assertTrue(eventListPanel.isListMatching(expectedList));
	}

	// @@author A0132157M
	private void addAllDummyEventTasks(TestEvent... currentList) {
		for (TestEvent t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}

	// @@author A0132157M
	private void assertAddDeadlineSuccess(TestDeadline taskToAdd, TestDeadline... currentList) {
		addAllDummyDeadlineTasks(currentList);
		commandBox.runCommand(taskToAdd.getAddCommand());
		// confirm the new card contains the right data
		DeadlineCardHandle addedCard = deadlineListPanel.navigateToDeadline(taskToAdd.getName().name);
		assertDeadlineMatching(taskToAdd, addedCard);
		// confirm the list now contains all previous tasks plus the new task
		TestDeadline[] expectedList = TestUtil.addDeadlinesToList(currentList, taskToAdd);
		assertTrue(deadlineListPanel.isListMatching(expectedList));
	}

	// @@author A0132157M
	private void addAllDummyDeadlineTasks(TestDeadline... currentList) {
		for (TestDeadline t : currentList) {
			commandBox.runCommand(t.getAddCommand());
		}
	}
}
