package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.FavoriteTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

public class FavoriteTaskCommandTest {


	@Test
	public void favoriteTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupEmptyTaskList();
		FavoriteTaskCommand command = new FavoriteTaskCommand(1);
		command.setData(model);
				
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void favoriteTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		FavoriteTaskCommand command = new FavoriteTaskCommand(4);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void favoriteTask_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		FavoriteTaskCommand command = new FavoriteTaskCommand(-1);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void favoriteTask_duplicateFavorite() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to favoriteTaskCommand constructor is within the range of added tasks.
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		FavoriteTaskCommand command = new FavoriteTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(FavoriteTaskCommand.MESSAGE_FAVORITE_TASK_SUCCESS, "[Floating Task][Description: Task 1]");
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void favoriteTask_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that the task 
		 * has already been favorited
		 */
		InMemoryTaskList model;
		model = TestUtil.setupSomeFavoritedTasksInTaskList(3);
		FavoriteTaskCommand command = new FavoriteTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(FavoriteTaskCommand.MESSAGE_TASK_ALR_FAVORITED);
		assertCommandFeedback(command, expected);
	}

	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(FavoriteTaskCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
}
