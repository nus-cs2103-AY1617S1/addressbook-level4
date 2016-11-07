package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.HelpCommand;
import w15c2.tusk.logic.commands.IncorrectCommand;
import w15c2.tusk.logic.parser.HelpCommandParser;
//@@author A0143107U
/**
 * Tests Help Command Parser
 */
public class HelpCommandParserTest {
	// Initialized to support the tests
	HelpCommandParser parser = new HelpCommandParser();
	
	/**
	 * Testing correct handling of invalid formats
	 * 
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of non-empty strings
		 * 
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
		
		IncorrectCommand command = (IncorrectCommand) parser.prepareCommand("listing");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
		
		command = (IncorrectCommand) parser.prepareCommand("all");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	/**
	 * Testing valid help format
	 */
	@Test
	public void prepareCommand_validHelpFormat() {
		Command command = parser.prepareCommand("");
		assertTrue(command instanceof HelpCommand);
	}
}
