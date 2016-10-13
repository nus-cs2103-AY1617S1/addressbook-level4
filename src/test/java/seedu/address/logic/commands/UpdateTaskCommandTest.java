package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.UpdateTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

public class UpdateTaskCommandTest {


	@Test
	public void updateTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupEmptyTaskList();
		UpdateTaskCommand command = new UpdateTaskCommand(1);
		command.setData(model);
				
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void updateTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomeTasksInTaskList(3);
		UpdateTaskCommand command = new UpdateTaskCommand(4);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(UpdateTaskCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
}
