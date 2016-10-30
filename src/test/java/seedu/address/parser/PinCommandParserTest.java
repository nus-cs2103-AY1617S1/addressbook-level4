package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.PinTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.PinCommandParser;

//@@author A0143107U
public class PinCommandParserTest {
	// Initialized to support the tests
	PinCommandParser parser = new PinCommandParser();

	/**
	 * Testing correct handling of invalid indices or formats
	 * 
	 * Invalid EPs: Indices, Formats
	 */
	
	@Test
	public void prepareCommand_invalidIndex() {
		/*
		 * Testing correct handling of indices
		 * 
		 * Incorrect indices EPs: Negative values, zero
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinTaskCommand.MESSAGE_USAGE);
		
		// EP: Negative value
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("-1");
		String actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("-6");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Zero
		command = (IncorrectTaskCommand) parser.prepareCommand("0");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
	}
	
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of formats
		 * 
		 * Incorrect formats EPs: Non-integers, multiple integers, spaces, empty string
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinTaskCommand.MESSAGE_USAGE);
		
		// EP: Non-integers
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("hello world");
		String actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("hello");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Multiple integers
		command = (IncorrectTaskCommand) parser.prepareCommand("123 123");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Spaces
		command = (IncorrectTaskCommand) parser.prepareCommand(" ");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Empty string
		command = (IncorrectTaskCommand) parser.prepareCommand("");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
	}
	
	/**
	 * Testing parsing of valid indices
	 */
	@Test
	public void prepareCommand_validIndex() {
		PinTaskCommand command = (PinTaskCommand) parser.prepareCommand("1");
		assertTrue(command.targetIndex == 1);
	}
}
