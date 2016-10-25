package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.AddAliasCommand;
import seedu.address.logic.commands.taskcommands.DeleteAliasCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.TaskManager;

//@@author A0143107U
public class DeleteAliasCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;
	
	@Test
	public void deleteAlias_noAliasAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no alias that have been added).
		 */
		setupEmptyAliasList();
		DeleteAliasCommand command = new DeleteAliasCommand("meeting");
		command.setData(model);
				
		String expected = DeleteAliasCommand.MESSAGE_ALIAS_NOT_FOUND;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void deleteAlias_invalidAlias() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		setupSomeAliasInAliasList();
		DeleteAliasCommand command = new DeleteAliasCommand("ad");
		command.setData(model);
		
		String expected = DeleteAliasCommand.MESSAGE_ALIAS_NOT_FOUND;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void deleteTask_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if shortcut given 
		 * to DeleteAliasCommand constructor is in alias list.
		 */
		setupSomeAliasInAliasList();
		DeleteAliasCommand command = new DeleteAliasCommand("am");
		command.setData(model);
		
		String expected = String.format(DeleteAliasCommand.MESSAGE_DELETE_ALIAS_SUCCESS, "am add meeting");
		assertCommandFeedback(command, expected);
	}

	/*
	 * Utility Functions
	 */
	public void setupEmptyAliasList() {
		model = new TaskManager();
	}
	
	// Setting up alias in the AliasList in order to delete them in the tests
	public void setupSomeAliasInAliasList() throws IllegalValueException {
		model = new TaskManager();
		// Add 3 tasks into the alias list
		AddAliasCommand command = new AddAliasCommand("am", "add meeting");	
		command.setData(model);
		command.execute();
		
		command = new AddAliasCommand("ae", "add event");
		command.setData(model);
		command.execute();
		
		command = new AddAliasCommand("at", "add task");
		command.setData(model);
		command.execute();
	}
	
	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(DeleteAliasCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
}
