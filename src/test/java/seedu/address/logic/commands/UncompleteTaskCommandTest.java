package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.UncompleteTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

//@@author A0143107U
public class UncompleteTaskCommandTest {

	@Test
	public void uncompleteTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupEmptyTaskList();
		UncompleteTaskCommand command = new UncompleteTaskCommand(1);
		command.setData(model);
				
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void uncompleteTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomeTasksInTaskList(3);
		UncompleteTaskCommand command = new UncompleteTaskCommand(4);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void uncompleteTask_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomeTasksInTaskList(3);
		UncompleteTaskCommand command = new UncompleteTaskCommand(-1);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void uncompleteTask_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to UncompleteTaskCommand constructor is within the range of added tasks.
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomeCompletedTasksInTaskList(3);
		UncompleteTaskCommand command = new UncompleteTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(UncompleteTaskCommand.MESSAGE_UNCOMPLETE_TASK_SUCCESS, "[Floating Task][Description: Task 1]");
		assertCommandFeedback(command, expected);
	}

	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(UncompleteTaskCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
	
}
