package seedu.address.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.AddAliasCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.AddAliasCommandParser;

//@@author A0139817U
public class AddAliasCommandParserTest {
	// Initialized to support the tests
	AddAliasCommandParser parser = new AddAliasCommandParser();
	
	/**
	 * Testing correct handling of invalid formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of invalid formats
		 * 
		 * Invalid format EPs: No space, Nothing after space, Spaces only, Empty string
		 */
		// EP: No space
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("amaddMeeting");
		String expected = "Sentence to AliasCommand constructor is empty.\n" + AddAliasCommand.MESSAGE_USAGE;
		String actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Nothing after space
		command = (IncorrectTaskCommand) parser.prepareCommand("am ");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Spaces only
		command = (IncorrectTaskCommand) parser.prepareCommand("    ");
		expected = "Shortcut to AddAliasCommand constructor is empty.\n" + AddAliasCommand.MESSAGE_USAGE;
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Empty string
		command = (IncorrectTaskCommand) parser.prepareCommand("");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
	}
	
	/**
	 * Testing parsing of valid formats
	 */
	@Test
	public void prepareCommand_validFormat() {		
		AddAliasCommand command = (AddAliasCommand) parser.prepareCommand("am add Meeting");
		String expected = "am add Meeting";
		String actual = command.getAliasDetails();
		assertEquals(actual, expected);
		
		command = (AddAliasCommand) parser.prepareCommand("a add");
		expected = "a add";
		actual = command.getAliasDetails();
		assertEquals(actual, expected);
	}
}
