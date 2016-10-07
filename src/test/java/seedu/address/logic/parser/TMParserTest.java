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
	public void parseCommand_userInput_incorrectCommandReturned() {
		String userInput = "       ";
		Command command = parser.parseUserInput(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addNoArgs_incorrectCommandReturned() {
		String userInput = "add";
		Command command = parser.parseUserInput(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineNoArgs_incorrectCommandReturned() {
		String userInput = "add deadline";
		Command command = parser.parseUserInput(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineNoDate_incorrectCommandReturned() {
		String userInput = "add deadline 'submission' at 5:00pm";
		Command command = parser.parseUserInput(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineNoTime_incorrectCommandReturned() {
		String userInput = "add deadline 'submission' by 04:00";
		Command command = parser.parseUserInput(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addDeadlineValid_nullReturned() {
		String userInput = "add deadline 'submission' by 04:00 at 25/12/16";
		Command command = parser.parseUserInput(userInput);

		assertEquals(null, command);
	}
	
	@Test
	public void parseCommand_addDeadlineValidOrder2_nullReturned() {
		String userInput = "add deadline 'submission' at 25/12/16 by 04:00";
		Command command = parser.parseUserInput(userInput);

		assertEquals(null, command);
	}
	
	@Test
	public void parseCommand_addDeadlineValidOrder3_nullReturned() {
		String userInput = "add deadline at 25/12/16 'submission' by 04:00";
		Command command = parser.parseUserInput(userInput);

		assertEquals(null, command);
	}
	
	@Test
	public void parseCommand_addDeadlineValidOrder4_nullReturned() {
		String userInput = "add deadline at 25/12/16 by 04:00 'submission' ";
		Command command = parser.parseUserInput(userInput);

		assertEquals(null, command);
	}
	
	@Test
	public void parseCommand_addSomedayNoArgs_incorrectCommandReturned() {
		String userInput = "add someday";
		Command command = parser.parseUserInput(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayWhitespaceName_incorrectCommandReturned() {
		String userInput = "add someday ' '";
		Command command = parser.parseUserInput(userInput);

		assertEquals(incorrectCommand.getClass(), command.getClass());
	}
	
	@Test
	public void parseCommand_addSomedayValid_nullReturned() {
		String userInput = "add someday 'dance'";
		Command command = parser.parseUserInput(userInput);

		assertEquals(null, command);
	}
	
	@Test
	public void parseCommand_addSomedayValidOrder2_nullReturned() {
		String userInput = "add 'dance' someday";
		Command command = parser.parseUserInput(userInput);

		assertEquals(null, command);
	}
}
