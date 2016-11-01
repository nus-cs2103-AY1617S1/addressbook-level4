package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.parser.IncorrectCommandParser;

//@@author A0143107U
public class IncorrectCommandParserTest {
	IncorrectCommandParser parser = new IncorrectCommandParser();

	/**
	 * Testing parsing of valid formats
	 */
	@Test
	public void prepareCommand_validFormat() {
		String expected = String.format(MESSAGE_UNKNOWN_COMMAND);
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("");
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}

}
