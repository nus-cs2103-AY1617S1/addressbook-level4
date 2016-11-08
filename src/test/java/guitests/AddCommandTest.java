package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.logic.commands.AddCommand;
import seedu.testplanner.testutil.TestTask;
import seedu.testplanner.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

//@@author A0140124B
public class AddCommandTest extends DailyPlannerGuiTest {

	@Test
	public void add() {

		// add one task
		TestTask[] currentList = td.getTypicalTasks();
		TestTask taskToAdd = td.learnSpanish;
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);

		// add another task
		taskToAdd = td.GoSkydiving;
		assertAddSuccess(taskToAdd, currentList);
		currentList = TestUtil.addTasksToList(currentList, taskToAdd);

		// add duplicate task
		commandBox.runCommand(td.learnPython.getAddCommand());
		assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
		assertTrue(taskListPanel.isListMatching(currentList));

		// add to empty list
		commandBox.runCommand("clear");
		assertAddSuccess(td.CS2103_Project);

		// invalid command
		commandBox.runCommand("adds Johnny");
		assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
	}
	//@@author
	private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
		commandBox.runCommand(taskToAdd.getAddCommand());

		// confirm the new card contains the right data
		TaskCardHandle addedCard = taskListPanel.navigateToPerson(taskToAdd.getName());
		assertMatching(taskToAdd, addedCard);

		// confirm the list now contains all previous persons plus the new
		// person
		TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
		assertTrue(taskListPanel.isListMatching(expectedList));
	}

}
