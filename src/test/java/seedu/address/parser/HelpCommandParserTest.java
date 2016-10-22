package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.HelpTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.HelpCommandParser;

public class HelpCommandParserTest {
	// Initialized to support the tests
	HelpCommandParser parser = new HelpCommandParser();
	
	/**
	 * Testing correct handling of invalid formats, list types
	 * 
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		String expectedTask = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("listing");
		String actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("all");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
	}
	
	/**
	 * Testing valid help format
	 */
	@Test
	public void prepareCommand_validHelpFormat() {
		String expectedTask = HelpTaskCommand.SHOWING_HELP_MESSAGE;
		
		HelpTaskCommand command = (HelpTaskCommand) parser.prepareCommand("");
		String actualTask = command.toString();
		assertEquals(actualTask, expectedTask);
		
	}
}
