package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.UndoTaskCommand;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.parser.UndoCommandParser;

//@@author A0143107U
public class UndoCommandParserTest {
	// Initialized to support the tests
	UndoCommandParser parser = new UndoCommandParser();
				
	/**
	 * Testing correct handling of invalid undo formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoTaskCommand.HELP_MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("previous");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("last");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
				
	/**
	 * Testing valid undo format
	 */
	@Test
	public void prepareCommand_validUndoFormat() {
		String expected = UndoTaskCommand.MESSAGE_UNDO_TASK_SUCCESS;
		
		UndoTaskCommand command = (UndoTaskCommand) parser.prepareCommand("");
		String feedback = command.toString();
		assertEquals(feedback, expected);			
	}	
}
