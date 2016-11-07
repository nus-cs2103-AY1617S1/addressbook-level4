package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.IncorrectCommand;
import w15c2.tusk.logic.commands.UndoCommand;
import w15c2.tusk.logic.parser.UndoCommandParser;

//@@author A0143107U
/**
 * Tests Undo Command Parser
 */
public class UndoCommandParserTest {
	// Initialized to support the tests
	UndoCommandParser parser = new UndoCommandParser();
				
	/**
	 * Testing correct handling of invalid undo formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE);
		
		IncorrectCommand command = (IncorrectCommand) parser.prepareCommand("previous");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
		
		command = (IncorrectCommand) parser.prepareCommand("last");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
				
	/**
	 * Testing valid undo format
	 */
	@Test
	public void prepareCommand_validUndoFormat() {
		String expected = UndoCommand.MESSAGE_UNDO_TASK_SUCCESS;
		
		UndoCommand command = (UndoCommand) parser.prepareCommand("");
		String feedback = command.toString();
		assertEquals(feedback, expected);			
	}	
}
