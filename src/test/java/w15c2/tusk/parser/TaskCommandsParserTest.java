package w15c2.tusk.parser;

import static org.junit.Assert.assertTrue;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;
import w15c2.tusk.logic.parser.*;

//@@author A0143107U
public class TaskCommandsParserTest {

	/**
	 * Testing correct handling of invalid formats 
	 */
	@Test
	public void parserSelector_invalidCommand(){
		/* CommandResult should return a string that denotes that 
         * command is invalid.
         */
		TaskCommandsParser parser = new TaskCommandsParser();
		TaskCommand command = parser.parseCommand("");
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
        assertTrue(feedback.equals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Type help if you want to know the list of commands.")));
	}
	
	/**
	 * Testing parsing of valid format
	 */
	@Test
	public void parserSelector_validCommand(){
		/* CommandResult should return a string that denotes that 
         * command is valid.
         */
		TaskCommandsParser parser = new TaskCommandsParser();
		parser.parseCommand("add meeting with boss");	
		String feedback = parser.getCommandWord();
		assertTrue(feedback.equals("add"));
		feedback = parser.getArguments();
		assertTrue(feedback.equals("meeting with boss"));
	}
}
	