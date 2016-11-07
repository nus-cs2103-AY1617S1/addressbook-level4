package w15c2.tusk.logic.parser;

import w15c2.tusk.logic.commands.Command;

//@@author A0143107U
/**
 * Defines a generic parser for commands
 */
public abstract class CommandParser {
	
	public abstract Command prepareCommand(String arguments);

}
