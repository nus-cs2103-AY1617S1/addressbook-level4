package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.UnpinTaskCommand;
import w15c2.tusk.model.task.InMemoryTaskList;
import w15c2.tusk.testutil.TestUtil;

//@@author A0138978E
public class UnpinTaskCommandTest {

	
	@Test
	public void unpinTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupEmptyTaskList();
		UnpinTaskCommand command = new UnpinTaskCommand(1);
		command.setData(model);
				
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void unpinTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		UnpinTaskCommand command = new UnpinTaskCommand(4);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void unpinTask_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		UnpinTaskCommand command = new UnpinTaskCommand(-1);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void unpinTask_validIndex_alreadyUnpin() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to unpinTaskCommand constructor is within the range of added tasks.
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		UnpinTaskCommand command = new UnpinTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(UnpinTaskCommand.MESSAGE_TASK_ALR_UNPINNED, "[Floating Task][Description: Task 1]");
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void unpinTask_validIndex_isPin() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to unpinTaskCommand constructor is within the range of added tasks.
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		model.getCurrentFilteredTasks().get(1).setAsPin();
		UnpinTaskCommand command = new UnpinTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(UnpinTaskCommand.MESSAGE_UNPIN_TASK_SUCCESS, "Task 1");
		assertCommandFeedback(command, expected);
	}

	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(UnpinTaskCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
}
