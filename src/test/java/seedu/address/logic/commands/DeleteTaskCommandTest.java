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
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
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
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
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
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
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
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, "Task 1")));
	}

	/*
	 * Utility Functions
	 */
	public void setupEmptyTaskList() {
		model = new TaskManager();
	}
	
	public void setupSomeTasksInTaskList() throws IllegalValueException {
		model = new TaskManager();
		// Add 3 tasks into the task manager
		for (int i = 0; i < 3; i++) {
			AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
			command.setData(model);
			command.execute();
		}
	}
}
