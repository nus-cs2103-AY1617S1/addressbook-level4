package seedu.address.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;

import org.junit.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.IncorrectCommandParser;

public class IncorrectCommandParserTest {
	IncorrectCommandParser parser = new IncorrectCommandParser();

	@Test
	public void prepareCommand_validFormat() {
		String expected = String.format(MESSAGE_UNKNOWN_COMMAND);
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("");
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertEquals(feedback, expected);
	}

}
