package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.RedoTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.RedoCommandParser;

//@@author A0143107U
public class RedoCommandParserTest {
	// Initialized to support the tests
	RedoCommandParser parser = new RedoCommandParser();
				
	/**
	 * Testing correct handling of invalid redo formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoTaskCommand.HELP_MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("previous");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("last");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
				
	/**
	 * Testing valid redo format
	 */
	@Test
	public void prepareCommand_validRedoFormat() {
		String expected = RedoTaskCommand.MESSAGE_REDO_TASK_SUCCESS;
		
		RedoTaskCommand command = (RedoTaskCommand) parser.prepareCommand("");
		String feedback = command.toString();
		assertEquals(feedback, expected);			
	}	
}
