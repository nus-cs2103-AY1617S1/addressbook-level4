package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.PinTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.PinCommandParser;

//@@author A0143107U
public class PinCommandParserTest {
	// Initialized to support the tests
	PinCommandParser parser = new PinCommandParser();

	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of invalid formats
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("1pin");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void prepareCommand_pinTask() {
		/*
		 * Testing valid pin task format
		 */
		String expected = "2";
		
		PinTaskCommand command = (PinTaskCommand) parser.prepareCommand("2");
		String feedback = command.getIndex();
		assertEquals(feedback, expected);
	}
}
