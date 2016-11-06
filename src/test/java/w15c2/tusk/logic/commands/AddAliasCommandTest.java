package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.AddAliasCommand;
import w15c2.tusk.model.Model;
import w15c2.tusk.model.ModelManager;

/**
 * Tests AddAlias Command 
 */
//@@author A0143107U
public class AddAliasCommandTest {

	// Initialized to support the tests
	Model model;

	@Before
	public void setup() {
		model = new ModelManager();
	}
	
	@Test
	public void addAlias_successful() throws IllegalValueException {
		/* CommandResult should return a string that denotes success in execution if description 
		 * given to AddAliasCommand constructor is a string with size > 0
		 */
		AddAliasCommand command = new AddAliasCommand("am", "add meeting");
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddAliasCommand.MESSAGE_SUCCESS, "am add meeting")));
	}
	
	@Test(expected=IllegalValueException.class)
	public void addAlias_emptyStringShortcut_emptyStringSentence() throws IllegalValueException {
		// Construction of the AddAliasCommand with an empty strings should lead to an error
		new AddAliasCommand("", "");
	}
	
	@Test(expected=IllegalValueException.class)
	public void addAlias_nullShortcut_nullSentence() throws IllegalValueException {
		// Construction of the AddAliasCommand with null shortcut and null sentence should lead to an error
		new AddAliasCommand(null, null);
	}
	
	@Test(expected=IllegalValueException.class)
	public void addAlias_validShortcut_nullSentence() throws IllegalValueException {
		// Construction of the AddAliasCommand with valid shortcut but null sentence should lead to an error
		new AddAliasCommand("am", null);
	}
	
	@Test(expected=IllegalValueException.class)
	public void addAlias_invalidShortcut_commandWord() throws IllegalValueException {
		// Construction of the AddAliasCommand with a shortcut that is a command word should lead to an error
		new AddAliasCommand("add", "add meeting");
	}
	
	@Test
	public void addAlias_repeated_alias() throws IllegalValueException {
		/* CommandResult should return a string that denotes the alias is a duplicate
		 * 
		 */
		setupSomeAliasInAliasList();

		AddAliasCommand command = new AddAliasCommand("am", "add meeting boss");
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddAliasCommand.MESSAGE_DUPLICATE_ALIAS)));
	}
	
	
	// Setting up alias in the AliasList for testing
		public void setupSomeAliasInAliasList() throws IllegalValueException {
			model = new ModelManager();
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
