package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.DeleteAliasCommand;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.parser.DeleteAliasCommandParser;

//@@author A0139817U
public class DeleteAliasCommandParserTest {
	// Initialized to support the tests
	DeleteAliasCommandParser parser = new DeleteAliasCommandParser();
	
	/**
	 * Testing correct handling of invalid formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of invalid formats
		 * 
		 * Invalid format EPs: Empty string, Spaces only, Multiple strings
		 */
		
		// EP: Empty string
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("");
		String expected = "Shortcut to DeleteAliasCommand constructor is empty.\n"+ DeleteAliasCommand.MESSAGE_USAGE;
		String actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Spaces only
		command = (IncorrectTaskCommand) parser.prepareCommand("   ");
		expected = "Shortcut to DeleteAliasCommand constructor is empty.\n"+ DeleteAliasCommand.MESSAGE_USAGE;
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Multiple strings
		command = (IncorrectTaskCommand) parser.prepareCommand("am an");
		expected = "You should only provide 1 alias";
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
	}
	
	/**
	 * Testing parsing of valid formats
	 */
	@Test
	public void prepareCommand_validFormat() {		
		DeleteAliasCommand command = (DeleteAliasCommand) parser.prepareCommand("am");
		String expected = "am";
		String actual = command.shortcut;
		assertEquals(actual, expected);
		
		command = (DeleteAliasCommand) parser.prepareCommand("helloworldhelloworld");
		expected = "helloworldhelloworld";
		actual = command.shortcut;
		assertEquals(actual, expected);
	}
}
