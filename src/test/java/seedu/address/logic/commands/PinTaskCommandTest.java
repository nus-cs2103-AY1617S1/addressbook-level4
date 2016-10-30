package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.PinTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

//@@author A0138978E
public class PinTaskCommandTest {


	@Test
	public void pinTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupEmptyTaskList();
		PinTaskCommand command = new PinTaskCommand(1);
		command.setData(model);
				
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void pinTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		PinTaskCommand command = new PinTaskCommand(4);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void pinTask_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		PinTaskCommand command = new PinTaskCommand(-1);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void pinTask_duplicatePin() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to pinTaskCommand constructor is within the range of added tasks.
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		PinTaskCommand command = new PinTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(PinTaskCommand.MESSAGE_PIN_TASK_SUCCESS, "Task 1");
		assertCommandFeedback(command, expected);
	}
	
	//@@author
	@Test
	public void pinTask_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that the task 
		 * has already been pinned
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomePinnedTasksInTaskList(3);
		PinTaskCommand command = new PinTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(PinTaskCommand.MESSAGE_TASK_ALR_PINNED);
		assertCommandFeedback(command, expected);
	}

	//@@author A0138978E
	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(PinTaskCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
}
