package w15c2.tusk.parser;

import static org.junit.Assert.*;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.FindTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.parser.FindCommandParser;

//@@author A0143107U
public class FindCommandParserTest {
	FindCommandParser parser = new FindCommandParser();
	
	/**
	 * Testing correct handling of invalid formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	/**
	 * Testing parsing of valid formats
	 */
	@Test
	public void prepareCommand_validFormats() {
		
		FindTaskCommand command = (FindTaskCommand) parser.prepareCommand("meeting task");
        String[] expected = {"task", "meeting"};
		Set<String> keywords = command.getKeywords();
		String[] feedback = keywords.toArray(new String[keywords.size()]);
		assertArrayEquals(feedback, expected);
		
	}
}
