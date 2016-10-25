package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.UnfavoriteTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.UnfavoriteCommandParser;

//@@author A0143107U
public class UnfavoriteCommandParserTest {
	// Initialized to support the tests
	UnfavoriteCommandParser parser = new UnfavoriteCommandParser();
	
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of invalid formats
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavoriteTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("1unfavorite");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void prepareCommand_unfavoriteTask() {
		/*
		 * Testing valid unfavorite command format
		 */
		String expected = "2";
		
		UnfavoriteTaskCommand command = (UnfavoriteTaskCommand) parser.prepareCommand("2");
		String feedback = command.getIndex();
		assertEquals(feedback, expected);
	}
}
