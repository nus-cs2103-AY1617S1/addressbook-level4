package w15c2.tusk.logic.parser;

import w15c2.tusk.logic.commands.taskcommands.TaskCommand;

/*
 * Defines a generic parser for commands
 */
//@@author A0143107U
public abstract class CommandParser {
	
	public abstract TaskCommand prepareCommand(String arguments);

}
