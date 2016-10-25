package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.ReplaceAlias;
import seedu.address.logic.commands.taskcommands.AddAliasCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.TaskManager;

//@@author A0143107U
public class ReplaceAliasTest {
	
	// Initialized to support the tests
	InMemoryTaskList model;

	@Test
	public void replaceAlias_noAliasAdded() {
		/*
		 * ReplaceAlias should return the same commandText(since
		 * there are no alias that have been added).
		 */
		setupEmptyAliasList();
		ReplaceAlias replace = new ReplaceAlias(model);
				
		String expected = "am";
		assertTrue(replace.getAliasCommandText("am").equals(expected));
	}
	

	@Test
	public void replaceAlias_invalidAlias() throws IllegalValueException {
		/*
		 * ReplaceAlias should return the same commandText(since
		 * there is no such alias that have been added).
		 */
		setupSomeAliasInAliasList();
		ReplaceAlias replace = new ReplaceAlias(model);
		
		String expected = "ad";
		assertTrue(replace.getAliasCommandText("ad").equals(expected));
	}
	
	@Test
	public void replaceAlias_validAlias() throws IllegalValueException {
		/*
		 * ReplaceAlias should return the a commandText with the alias shortcut 
		 * replaced with its sentence and concatenated with the rest of the command.
		 */
		setupSomeAliasInAliasList();
		ReplaceAlias replace = new ReplaceAlias(model);
		
		String expected = "add meeting";
		assertTrue(replace.getAliasCommandText("am").equals(expected));
	}
	
	@Test
	public void replaceAlias_validAlias_withExtraText() throws IllegalValueException {
		/*
		 * ReplaceAlias should return the a commandText with the alias shortcut 
		 * replaced with its sentence.
		 */
		setupSomeAliasInAliasList();
		ReplaceAlias replace = new ReplaceAlias(model);
		
		String expected = "add event 7-10";
		assertTrue(replace.getAliasCommandText("ae 7-10").equals(expected));
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
	
}

