package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.DeleteTaskCommand;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.TaskManager;

public class DeleteTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;
	
	@Test
	public void deleteTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		setupEmptyTaskList();
		DeleteTaskCommand command = new DeleteTaskCommand(1);
		command.setData(model);
				
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void deleteTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		setupSomeTasksInTaskList();
		DeleteTaskCommand command = new DeleteTaskCommand(4);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void deleteTask_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		setupSomeTasksInTaskList();
		DeleteTaskCommand command = new DeleteTaskCommand(-1);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void deleteTask_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to DeleteTaskCommand constructor is within the range of added tasks.
		 */
		setupSomeTasksInTaskList();
		DeleteTaskCommand command = new DeleteTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, "[Floating Task][Description: Task 1]");
		assertCommandFeedback(command, expected);
	}

	/*
	 * Utility Functions
	 */
	public void setupEmptyTaskList() {
		model = new TaskManager();
	}
	
	// Setting up tasks in the TaskList in order to delete them in the tests
	public void setupSomeTasksInTaskList() throws IllegalValueException {
		model = new TaskManager();
		// Add 3 tasks into the task manager
		for (int i = 0; i < 3; i++) {
			AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
			command.setData(model);
			command.execute();
		}
	}
	
	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(DeleteTaskCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
}
