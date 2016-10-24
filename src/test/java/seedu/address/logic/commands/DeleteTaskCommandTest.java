package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.DeleteTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

//@@author A0139817U
public class DeleteTaskCommandTest {
	
	// Initialized to support the tests
	InMemoryTaskList model;
	
	@Test
	public void deleteTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		model = TestUtil.setupEmptyTaskList();
		
		CommandResult result = createAndExecuteDelete(1);
		String feedback = result.feedbackToUser;
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void deleteTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		model = TestUtil.setupFloatingTasks(3);
		
		CommandResult result = createAndExecuteDelete(4);
		String feedback = result.feedbackToUser;
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void deleteTask_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		model = TestUtil.setupFloatingTasks(3);
		
		CommandResult result = createAndExecuteDelete(-1);
		String feedback = result.feedbackToUser;
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void deleteTask_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to DeleteTaskCommand constructor is within the range of added tasks.
		 */
		model = TestUtil.setupFloatingTasks(3);
		
		CommandResult result = createAndExecuteDelete(2);
		String feedback = result.feedbackToUser;
		String expected = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, "[Floating Task][Description: Task 1]");
		assertEquals(feedback, expected);
	}


	/**
	 * Utility functions
	 */
	// Create and execute DeleteTaskCommand
	public CommandResult createAndExecuteDelete(int index) {
		DeleteTaskCommand command = new DeleteTaskCommand(index);
		command.setData(model);
		return command.execute();
	}
}
