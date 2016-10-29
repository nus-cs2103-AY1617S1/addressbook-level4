package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.UnpinTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.UnpinCommandParser;

//@@author A0143107U
public class UnpinCommandParserTest {
	// Initialized to support the tests
	UnpinCommandParser parser = new UnpinCommandParser();
	
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of invalid formats
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("1unpin");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void prepareCommand_unpinTask() {
		/*
		 * Testing valid unpin command format
		 */
		String expected = "2";
		
		UnpinTaskCommand command = (UnpinTaskCommand) parser.prepareCommand("2");
		String feedback = command.getIndex();
		assertEquals(feedback, expected);
	}
}
