package w15c2.tusk.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.ClearTaskCommand;
import w15c2.tusk.logic.parser.ClearCommandParser;

//@@author A0139817U
public class ClearCommandParserTest {
	// Initialized to support the tests
	ClearCommandParser parser = new ClearCommandParser();
		
	@Test
	public void prepareCommand() {
		ClearTaskCommand command = (ClearTaskCommand) parser.prepareCommand("");
		assertTrue(command != null);
	}
}
