package seedu.address.parser;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.FindTaskCommand;
import seedu.address.logic.parser.FindCommandParser;

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
