package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.IncorrectCommand;

//@@author A0143107U
/**
 * Parses incorrect commands
 */
public class IncorrectCommandParser extends CommandParser{
	
	public Command prepareCommand(String arguments) {
		return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
	}
}
