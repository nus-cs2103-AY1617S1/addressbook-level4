package seedu.address.logic.parser;

import static org.junit.Assert.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;


public class TMParserTest {

	private TMParser parser = new TMParser();
	private IncorrectCommand incorrectCommand = new IncorrectCommand("test");

	@Test
	public void parseCommand_whitespace_incorrectCommandReturned() {
		String whitespace = "       ";
		Command command = parser.parseUserInput(whitespace);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addNoArgs_incorrectCommandReturned() {
		String whitespace = "add";
		Command command = parser.parseUserInput(whitespace);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineNoArgs_incorrectCommandReturned() {
		String whitespace = "add deadline";
		Command command = parser.parseUserInput(whitespace);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineWhitespaceName_incorrectCommandReturned() {
		String whitespace = "add deadline ' '";
		Command command = parser.parseUserInput(whitespace);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayNoArgs_incorrectCommandReturned() {
		String whitespace = "add someday";
		Command command = parser.parseUserInput(whitespace);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayWhitespaceName_incorrectCommandReturned() {
		String whitespace = "add someday ' '";
		Command command = parser.parseUserInput(whitespace);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayValid_nullReturned() {
		String whitespace = "add someday 'dance'";
		Command command = parser.parseUserInput(whitespace);

		assertEquals(null, command);
	}
	
	@Test
	public void parseCommand_addSomedayValidDifferentOrder_nullReturned() {
		String whitespace = "add 'dance' someday";
		Command command = parser.parseUserInput(whitespace);

		assertEquals(null, command);
	}
}
