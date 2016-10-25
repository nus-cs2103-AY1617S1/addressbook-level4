package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.CompleteTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

//@@author A0143107U
public class CompleteTaskCommandTest {


	@Test
	public void completeTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupEmptyTaskList();
		CompleteTaskCommand command = new CompleteTaskCommand(1);
		command.setData(model);
				
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void completeTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomeTasksInTaskList(3);
		CompleteTaskCommand command = new CompleteTaskCommand(4);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void completeTask_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomeTasksInTaskList(3);
		CompleteTaskCommand command = new CompleteTaskCommand(-1);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void completeTask_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to CompleteTaskCommand constructor is within the range of added tasks.
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomeTasksInTaskList(3);
		CompleteTaskCommand command = new CompleteTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(CompleteTaskCommand.MESSAGE_COMPLETE_TASK_SUCCESS, "[Floating Task][Description: Task 1]");
		assertCommandFeedback(command, expected);
	}

	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(CompleteTaskCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
}
