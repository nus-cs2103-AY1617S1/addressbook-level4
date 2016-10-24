package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.FavoriteTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.FavoriteCommandParser;

public class FavoriteCommandParserTest {
	// Initialized to support the tests
	FavoriteCommandParser parser = new FavoriteCommandParser();

	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of invalid formats
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("1favorite");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void prepareCommand_favoriteTask() {
		/*
		 * Testing correct handling of invalid update types
		 */
		String expected = "2";
		
		FavoriteTaskCommand command = (FavoriteTaskCommand) parser.prepareCommand("2");
		String feedback = command.getIndex();
		assertEquals(feedback, expected);
	}
}
