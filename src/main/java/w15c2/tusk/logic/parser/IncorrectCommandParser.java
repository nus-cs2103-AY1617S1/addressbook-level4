package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

//@@author A0143107U
/*
 * Parses incorrect commands
 */
public class IncorrectCommandParser extends CommandParser{
	
	public TaskCommand prepareCommand(String arguments) {
		return new IncorrectTaskCommand(MESSAGE_UNKNOWN_COMMAND);
	}
}
