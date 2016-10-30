package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.CompleteTaskCommand;
import seedu.address.logic.commands.taskcommands.DeleteTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.DeleteCommandParser;

//@@author A0143107U
public class DeleteCommandParserTest {
	DeleteCommandParser parser = new DeleteCommandParser();
	/**
	 * Testing correct handling of invalid formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of invalid formats
		 * 
		 * Invalid format EPs: Empty string, Spaces only, Non-integers
		 */
		
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

		// EP: Empty string
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("");
		String actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Spaces only
		command = (IncorrectTaskCommand) parser.prepareCommand("   ");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Non-integers
		command = (IncorrectTaskCommand) parser.prepareCommand("task");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
	}
	
	public void prepareCommand_invalidIndex() {
		/*
		 * Testing correct handling of indices
		 * 
		 * Incorrect indices EPs: Negative values, zero
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteTaskCommand.MESSAGE_USAGE);
		
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
	
	/**
	 * Testing parsing of valid formats
	 */
	@Test
	public void prepareCommand_validIndex() {		
		DeleteTaskCommand command = (DeleteTaskCommand) parser.prepareCommand("1");
		assertTrue(command.targetIndex == 1);
	}
}
