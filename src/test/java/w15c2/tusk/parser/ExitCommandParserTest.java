package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.ExitCommand;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.parser.ExitCommandParser;

//@@author A0143107U
public class ExitCommandParserTest {
	// Initialized to support the tests
	ExitCommandParser parser = new ExitCommandParser();
	
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
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("tusk");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("app");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	/**
	 * Testing valid exit format
	 */
	@Test
	public void prepareCommand_validExitFormat() {
		String expected = ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
		
		ExitCommand command = (ExitCommand) parser.prepareCommand("");
		String feedback = command.toString();
		assertEquals(feedback, expected);
		
	}
}
