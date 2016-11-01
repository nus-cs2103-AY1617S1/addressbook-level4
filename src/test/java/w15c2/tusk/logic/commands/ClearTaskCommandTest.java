package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.ClearTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.FindTaskCommand;
import w15c2.tusk.model.task.InMemoryTaskList;
import w15c2.tusk.testutil.TestUtil;

//@@author A0139817U
public class ClearTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;

	@Before
	public void setup() throws IllegalValueException {
		// Creates fresh copy of tasks
		model = TestUtil.setupMixedTasks(6);
	}
	
	@Test
	public void clearTask() throws IllegalValueException {
		int filteredListSize = model.getCurrentFilteredTasks().size();
		ClearTaskCommand command = new ClearTaskCommand();
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(ClearTaskCommand.MESSAGE_CLEAR_TASKS_SUCCESS, filteredListSize)));
	}
	
	@Test
	public void clearTask_afterFindTaskCommand() throws IllegalValueException {
		model = TestUtil.setupTasksWithVariedNames(9);
		HashSet<String> toSearch = new HashSet<String>();
		toSearch.add("carrot");
		
		// Simulate a search for "carrot" which will return 3 tasks: "Carrot 6", "Carrot 7", "Carrot 8"
		FindTaskCommand findCommand = new FindTaskCommand(toSearch);
		findCommand.setData(model);
		findCommand.execute();
		
		// Simulate clearing of the 3 "carrot" tasks
		ClearTaskCommand command = new ClearTaskCommand();
		command = new ClearTaskCommand();
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(ClearTaskCommand.MESSAGE_CLEAR_TASKS_SUCCESS, 3)));
	}
}
